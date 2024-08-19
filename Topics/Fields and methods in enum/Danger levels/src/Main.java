enum Direction {
    EAST("E"),
    WEST("W"),
    NORTH("N"),
    SOUTH("S");

    private final String shortCode;

    Direction(String code) {
        this.shortCode = code;
    }

    public String getShortCode() {
        return this.shortCode;
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println(Direction.valueOf("N"));
    }
}