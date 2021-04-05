package server;

import org.apache.commons.io.input.ReversedLinesFileReader;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class HistoryService {

    public static void writeHistory(String login, String msg) {
        String path = String.format("server/src/main/resources/history_[%s].txt", login);
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(path, true), true)) {
            printWriter.println(msg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getHistory(String login, int numberOfLines) {
        String path = String.format("server/src/main/resources/history_[%s].txt", login);
        File file = new File(path);
        ArrayList<String> historyList = new ArrayList<>();
        try (ReversedLinesFileReader linesFileReader = new ReversedLinesFileReader(file, Charset.defaultCharset())) {
            String line;
            int counter = 0;
            while (((line = linesFileReader.readLine()) != null) && counter <= numberOfLines) {
                historyList.add(line);
                counter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historyList;
    }


}
