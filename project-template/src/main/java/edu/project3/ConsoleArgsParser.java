package edu.project3;

import java.util.HashMap;
import java.util.Map;

public class ConsoleArgsParser implements ArgsParser {
    @Override
    public Map<String, String> parse(String[] args) {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--path" -> result.put("path", args[i + 1]);
                case "--from" -> result.put("from", args[i + 1]);
                case "--to" -> result.put("to", args[i + 1]);
                case "--format" -> result.put("format", args[i + 1]);
            }
        }

        if (!result.containsKey("path")) {
            throw new RuntimeException("Path is not defined");
        }
        if (!result.containsKey("format")) {
            result.put("format", "");
        }
        if (!result.containsKey("from")) {
            result.put("from", "");
        }
        if (!result.containsKey("to")) {
            result.put("to", "");
        }

        return result;
    }
}
