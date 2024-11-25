package ch.fhnw.hellopi;

import java.util.HashMap;

public enum Data {
    INSTANCE;

    private HashMap<String, Clothing> map = new HashMap<>();

    public Clothing getClothing(String hashString) {
        if (map.containsKey(hashString)) {
            return map.get(hashString);
        }
        else {
            return new Clothing("404", "not found", "src/images/notFound.png");
        }
    }

    public void addClothing(String hashString, Clothing clothing) {
        map.put(hashString, clothing);
    }
}
