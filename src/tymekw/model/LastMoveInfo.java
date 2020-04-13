package model;

public class LastMoveInfo {
    private Field source, destination, captured;
    private boolean isCaptured;

    public Field getSource() {
        return source;
    }

    public Field getDestination() {
        return destination;
    }

    public Field getCaptured() {
        return captured;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setSource(Field source) {
        this.source = source;
    }

    public void setDestination(Field destination) {
        this.destination = destination;
    }

    public void setCaptured(Field captured) {
        this.captured = captured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }
}
