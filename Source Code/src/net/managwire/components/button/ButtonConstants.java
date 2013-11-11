package net.managwire.components.button;

/**
 *
 * @author Thievery
 */
public enum ButtonConstants {

    ROUND_BUTTON(0.95);
    private final double roundedPrecentage;

    private ButtonConstants(double roundEdge) {
        this.roundedPrecentage = roundEdge;
    }

    public int getCalculatedRoundEdge(int height) {
        return ((int) (roundedPrecentage * height)) - (((int) roundedPrecentage * height) % 2);
    }
}
