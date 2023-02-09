package me.iantapply.segments;

import lombok.Getter;
import me.iantapply.Main;
import processing.core.PGraphics;

import static processing.core.PApplet.*;

public class Segment {

    // Segment points
    @Getter
    SegmentVector pointA;
    @Getter
    SegmentVector pointB = new SegmentVector();

    @Getter
    float angle = 0;
    @Getter
    float length;

    // Parent and child of segment
    public Segment parent = null;
    public Segment child = null;

    // Segment index
    @Getter
    float index;

    PGraphics pg;

    /**
     * Builder for creating lead segment.
     * @param x X axis position.
     * @param y Y axis position.
     * @param length Length of segment.
     * @param i Index number of segment.
     * @param pg Main graphics.
     */
    public Segment(float x, float y, float length, float i, PGraphics pg) {
        this.pointA = new SegmentVector(x, y);
        this.length = length;
        this.index = i;
        this.pg = pg;

        this.calculatePointB();
    }

    /**
     * Builder for following segments.
     * @param parent Leading segment.
     * @param length Length of segment.
     * @param i Index number.
     * @param pg Main graphics.
     */
    public Segment(Segment parent, float length, float i, PGraphics pg) {
        this.parent = parent;
        this.pointA = parent.pointB.copy();
        this.length = length;
        this.index = i;
        this.pg = pg;

        this.calculatePointB();
    }

    /**
     * Follows to the child's point A location.
     */
    public void follow() {
        float targetX = this.child.pointA.x;
        float targetY = this.child.pointA.y;
        follow(targetX, targetY);
    }

    /**
     * Makes point A follow to the desired location.
     * @param targetX Target X axis.
     * @param targetY Target Y axis.
     */
    public void follow(float targetX, float targetY) {
        SegmentVector target = new SegmentVector(targetX, targetY);
        SegmentVector dir = SegmentVector.subtract(target, pointA);
        angle = dir.heading();
        dir.setMagnitude(length);
        dir.multiply(-1);
        pointA = SegmentVector.add(target, dir);
    }


    /**
     * Calculates and sets position of point B.
     */
    public void calculatePointB() {
        float dx = length * cos(this.angle);
        float dy = length * sin(this.angle);
        this.pointB.set(this.pointA.x + dx, this.pointA.y + dy);
    }

    /**
     * Updates position of point b (closest to cursor).
     */
    public void update() {
        this.calculatePointB();
    }

    /**
     * Shows a segment with the correct indication.
     */
    public void show() {
        if (Main.indicateStartEnd) {
            // First segment
            if (index == Main.numberOfSegments - 1) {
                pg.stroke(Main.leadingColor);
                // Last segment
            } else if (index == 0) {
                pg.stroke(Main.endColor);
                // Every other segment
            } else {
                pg.stroke(Main.neutralColor);
            }
        } else {
            pg.stroke(Main.neutralColor);
        }
        // Width (redundant)
        pg.strokeWeight(3);
        pg.line(this.pointA.x, this.pointA.y, this.pointB.x, this.pointB.y);
    }
}
