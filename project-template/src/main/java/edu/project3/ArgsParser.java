package edu.project3;

import java.util.Map;

public interface ArgsParser {
    Map<String, String> parse(String[] args);
}
