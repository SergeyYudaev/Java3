package server;

import commands.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

import java.util.ArrayList;


@RequiredArgsConstructor
@Log4j2
public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        try {

            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    socket.setSoTimeout(120000);
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith(Command.AUTH)) {
                            String[] token = str.split("\\s");
                            String newNick = null;
                            newNick = SQLiteAuthService.getNicknameByLoginAndPassword(token[1], token[2]);

                            login = token[1];
                            if (newNick != null) {
                                if (!server.isLoginAuthenticated(login)) {
                                    nickname = newNick;
                                    sendMsg(Command.AUTH_OK + " " + nickname);
                                    socket.setSoTimeout(0);
                                    server.subscribe(this);
                                    server.log.info("client {} connected: {}", nickname, socket.getRemoteSocketAddress());
                                    createHistoryFile();
                                    printHistoryToChatByServiceMsg();
                                    break;
                                } else {
                                    sendMsg("Этот логин уже авторизован");
                                }
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }

                        if (str.equals(Command.END)) {
                            sendMsg(Command.END);
                            throw new RuntimeException();
                        }

                        if (str.startsWith(Command.REG)) {
                            String[] token = str.split("\\s");
                            if (token.length < 4) {
                                continue;
                            }
                            boolean isRegistered = SQLiteAuthService.registration(token[1], token[2], token[3]);
                            if (isRegistered) {
                                server.log.info("Пользователь {} зарегистрирован под ником {}", token[1], token[3]);
                                sendMsg(Command.REG_OK);
                            } else {
                                sendMsg(Command.REG_NO);
                            }
                        }

                        //меняем ник тут
                        if (str.startsWith("/changeNick")) {
                            String[] token = str.split("\\s");
                            if (token.length < 4) {
                                continue;
                            }

                            //Посылает служебное сообщение в Controller
                            boolean nickChanged = SQLiteAuthService.changeNick(token[1], token[2], token[3]);
                            if (nickChanged) {
                                server.log.info("Пользователь {} сменил ник на {}", token[1], token[3]);
                                sendMsg("/никЗбс");
                            } else if (!nickChanged) {
                                sendMsg("/никНеЗбс");
                            }
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/")) {
                            if (str.equals(Command.END)) {
                                server.log.info("Client {} disconnected: {}", nickname, socket.getRemoteSocketAddress());
                                sendMsg(Command.END);
                                break;
                            }

                            if (str.startsWith(Command.PRVTMSG)) {
                                String[] token = str.split("\\s", 3);
                                if (token.length < 3) {
                                    continue;
                                }
                                server.privateMsg(this, token[1], token[2]);

                            }

                        } else {
                            server.broadcastMsg(this, str);

                        }
                    }
                } catch (SocketTimeoutException e) {
                    server.log.info("клиент  превысил время ожидания {}", socket.getRemoteSocketAddress());
                    sendMsg(Command.END);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    server.unsubscribe(this);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createHistoryFile() {
        String pathname = String.format("server/src/main/resources/history_[%s].txt", this.login);
        File file = new File(pathname);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHistory(String msg) {
        HistoryService.writeHistory(this.login, msg);
    }

    public void printHistoryToChatByServiceMsg() {
        ArrayList<String> historyList = HistoryService.getHistory(this.login, 100);
        String msg;
        for (int i = historyList.size() - 1; i >= 0; i--) {
            msg = String.format("/history//%s//%s", this.nickname, historyList.get(i));
            sendMsg(msg);
        }
    }


    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }
}
