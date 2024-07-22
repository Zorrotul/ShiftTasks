package cft.common;

public enum GameType {
    NOVICE("NOVICE",10, 10, 10),
    MEDIUM("MEDIUM",16, 16, 40),
    EXPERT("EXPERT",30, 16, 99);
    final String text;
    final int width;
    final int high;
    final int minesCount;

    GameType(String text, int x, int y, int minesCount) {
        this.text = text;
        this.width = x;
        this.high = y;
        this.minesCount = minesCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHigh() {
        return high;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public String getText() {
        return text;
    }
}
