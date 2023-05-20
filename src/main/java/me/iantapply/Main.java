package me.iantapply;

import me.iantapply.segments.Segment;
import processing.core.PApplet;

public class Main extends PApplet {
    private Segment tentacle;

    /**
     * Segment properties
     */
    public static int numberOfSegments = 10;
    int segmentLength = 40;
    public static boolean indicateStartEnd = true;

    // Color settings
    public static int leadingColor = 0xFF00FF00;
    public static int endColor = 0xFFFF0000;
    public static int neutralColor = 0xFFFFFFFF;

    public static void main(String[] args) {
        PApplet.main("me.iantapply.Main");
    }

    public void settings() {
        // Size of window
        size(1200, 800);
    }

    public void setup() {
        // Window title
        surface.setTitle("Inverse Kinematics");

        // Leading segment following mouse (player)
        Segment current = new Segment(0, 200, segmentLength, 0, g);

        // Start index at 1 to account for leading segment
        // Creates a new segment that follows each other
        for (int i = 1; i < numberOfSegments; i++) {
            Segment next = new Segment(current, segmentLength, i, g);
            current.child = next;
            current = next;
        }
        tentacle = current;
    }

    public void draw() {
        // Background color
        background(51);

        // Follow, update, and show the leading segment
        tentacle.follow(mouseX, mouseY);
        tentacle.update();
        tentacle.show();

        // Make the other segments follow
        Segment next = tentacle.parent;
        while (next != null) {
            next.follow();
            next.update();
            next.show();

            next = next.parent;
        }
    }
}

