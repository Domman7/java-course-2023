package edu.hw6.task5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    public static long[] hackerNewsTopStories() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://hacker-news.firebaseio.com/v0/topstories.json"))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                String json = response.body();
                String[] resultArray = json.substring(1, json.length() - 1).split(",");
                long[] result = new long[resultArray.length];

                for (int i = 0; i < resultArray.length; i++) {
                    result[i] = Long.parseLong(resultArray[i]);
                }

                return result;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new long[0];
    }

    public static String news(long id) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://hacker-news.firebaseio.com/v0/item/37570037.json"))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                String json = response.body();
                Pattern pattern = Pattern.compile("\"title\"\\s*:\\s*\"(.*?)\"");
                Matcher matcher = pattern.matcher(json);
                if (matcher.find()) {

                    return matcher.group(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
