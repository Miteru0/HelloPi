package ch.fhnw.hellopi;

import java.util.HashMap;

public enum Data {
    INSTANCE;

    private HashMap<String, Clothing> map = new HashMap<>();

    public Clothing getClothing(String hashString) {
        return map.get(hashString);
    }

    public void addClothing(String hashString, Clothing clothing) {
        map.put(hashString, clothing);
    }
}
