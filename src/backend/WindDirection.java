package backend;

public enum WindDirection {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    @Override
    public String toString() {
        if (name().length() > 5) {
            int scorePosition = name().indexOf("_");
            String start = name().substring(0, scorePosition);
            String end = name().substring(scorePosition + 1);
            return start.charAt(0) + start.substring(1).toLowerCase() + " " + end.charAt(0) + end.substring(1).toLowerCase();
        }
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
