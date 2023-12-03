package edu.hw8.task1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private final int serverPort;

    public Client(int serverPort) {

        this.serverPort = serverPort;
    }

    public void start() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, serverPort);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();

            outputStream.write(message.getBytes());

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);

            if (bytesRead != -1) {
                String response = new String(buffer, 0, bytesRead);
                System.out.println("Server: " + response);
            }

            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
