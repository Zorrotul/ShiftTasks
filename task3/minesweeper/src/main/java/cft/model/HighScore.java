package cft.model;

public class HighScore {

    private String name;
    private int value;

    public HighScore(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public HighScore() {
        this.value = 999;
        this.name = "NoName";
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
