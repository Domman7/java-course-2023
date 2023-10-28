package edu.hw3.task7;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class NullTolerateComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        if (a == null) {
            if (b == null) {

                return 0;
            } else {

                return -1;
            }
        } else {

            return a.compareTo(b);
        }
    }
}
