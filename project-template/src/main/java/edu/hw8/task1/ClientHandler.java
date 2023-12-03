package edu.hw8.task1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final static Map<String, String> RESPONSES = new HashMap<>() {{
        put("личности", "Не переходи на личности там, где их нет");
        put(
            "оскорбления",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами\n"
        );
        put(
            "глупый",
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
        );
        put("интеллект", "Чем ниже интеллект, тем громче оскорбления");
    }};

    private final Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientHandler(Socket clientSocket) {

        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);

            if (bytesRead != -1) {
                String message = new String(buffer, 0, bytesRead);
                String response = getResponse(message);

                outputStream.write(response.getBytes());
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                clientSocket.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private String getResponse(String message) {
        if (RESPONSES.containsKey(message)) {

            return RESPONSES.get(message);
        }

        return "Invalid keyword";
    }
}
