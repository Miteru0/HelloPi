package ch.fhnw.hellopi;

public class Clothing {

    private String name;
    private String description;
    private String imagePath;

    public Clothing(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath(){
        return imagePath;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
