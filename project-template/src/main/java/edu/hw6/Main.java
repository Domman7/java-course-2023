package edu.hw6;

import edu.hw6.task1.DiskMap;
import edu.hw6.task3.AbstractFilter;
import edu.hw6.task5.HackerNews;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        checkSockets();
    }

    //Задание 2
    public static void cloneFile(Path path) {
        String fileName = path.getFileName().toString();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String copiedFileName = fileName;
        int copyNumber = 1;

        while (Files.exists(path.resolveSibling(copiedFileName))) {
            if (copyNumber == 1) {
                copiedFileName = fileName.substring(0, fileName.lastIndexOf(".")) +
                    " - копия" + extension;
            } else {
                copiedFileName = fileName.substring(0, fileName.lastIndexOf(".")) +
                    " - копия (" + copyNumber + ")" + extension;
            }

            copyNumber++;
        }

        try {
            Files.copy(path, path.resolveSibling(copiedFileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Задание 4
    public static Path streamCombinationWrite(String message) {
        Path path = null;

        try {
            path = Files.createTempFile("output", ".txt");

            try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE)) {
                try (CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new Adler32())) {
                    try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream)) {
                        try (OutputStreamWriter outputStreamWriter =
                                 new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8)) {
                            try (PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {
                                printWriter.println(message);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    //Задание 6
    public final static TreeMap<Integer, String> KNOWN_SOCKETS = new TreeMap<>() {{
        put(22, "SSH");
        put(53, "DNS");
        put(80, "HTTP");
        put(443, "HTTPS");
        put(3306, "MySQL");
        put(5432, "PostgreSQL");
        put(137, "Служба имен NetBIOS");
        put(138, "Служба датаграмм NetBIOS");
        put(139, "Служба сеансов NetBIOS");
        put(1900, "Simple Service Discovery Protocol (SSDP)");
        put(3702, "Динамическое обнаружение веб-служб");
        put(5353, "Многоадресный DNS");
        put(5355, "Link-Local Multicast Name Resolution (LLMNR)");
    }};

    public static void checkSockets() {
        System.out.println("Протокол\tПорт\tСервис");
        for (var port : KNOWN_SOCKETS.keySet()) {
            //for (int port = 0; port <= 49151; port++) {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.close();
                System.out.println("TCP\t\t\t" + port);
            } catch (IOException e) {
                System.out.println("TCP\t\t\t" + port + "  \t" + KNOWN_SOCKETS.get(port));
            }
            try {
                DatagramSocket datagramSocket = new DatagramSocket(port);
                datagramSocket.close();
                System.out.println("UDP\t\t\t" + port);
            } catch (SocketException e) {
                System.out.println("UDP\t\t\t" + port + "  \t" + KNOWN_SOCKETS.get(port));
            }
        }
    }
}
