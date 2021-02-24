package server;

import commands.Command;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private ServerSocket server;
    private Socket socket;
    private final int PORT = 8189;
    private List<ClientHandler> clients;

    public Server() {
        clients = new CopyOnWriteArrayList<>();

        try {
            SQLiteAuthService.classForname();
            server = new ServerSocket(PORT);
            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                System.out.println("Client connected");
                new ClientHandler(this, socket);
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(ClientHandler clientHandler, String msg) {
        String message = String.format("[ %s ]: %s", clientHandler.getNickname(), msg);
        for (ClientHandler c : clients) {
            c.sendMsg(message);
            c.writeHistory(message);
        }
    }

    public void privateMsg(ClientHandler sender, String reciever, String msg) {
        String message = String.format("[ %s ] to [ %s ]: %s", sender.getNickname(), reciever, msg);
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(reciever)) {
                c.sendMsg(message);
                c.writeHistory(message);
                if (!c.equals(sender)) {
                    sender.sendMsg(message);
                    sender.writeHistory(message);
                }
                return;
            }
        }
        sender.sendMsg(String.format("user %s not found", reciever));
    }

    void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public boolean isLoginAuthenticated(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) return true;
        }
        return false;
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder(Command.CLIENT_LIST);

        for (ClientHandler c : clients) {
            sb.append(" ").append(c.getNickname());
        }

        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }


}
