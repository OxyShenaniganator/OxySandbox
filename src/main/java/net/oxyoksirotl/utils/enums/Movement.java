package net.oxyoksirotl.utils.enums;

public enum Movement {
    IDLE(null),
    RIGHT("D"),
    LEFT("A"),
    UP("W"),
    DOWN("S"),
    UP_LEFT("WA"),
    UP_RIGHT("WD"),
    DOWN_LEFT("SA"),
    DOWN_RIGHT("SD");

    public String animationID;

    Movement(String animationID) {
        this.animationID = animationID;
    }

}
