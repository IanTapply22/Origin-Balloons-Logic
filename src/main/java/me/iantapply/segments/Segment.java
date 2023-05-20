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
    float length;

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

        float dx = length * cos(0);
        float dy = length * sin(0);
        this.pointB.set(this.pointA.x + dx, this.pointA.y + dy);
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

        float dx = length * cos(0);
        float dy = length * sin(0);
        this.pointB.set(this.pointA.x + dx, this.pointA.y + dy);
    }

    /**
     * Follows to the child's point A location.
     */
    public void follow() {
        float targetX = this.child.pointB.x;
        float targetY = this.child.pointB.y;
        follow(targetX, targetY);
    }

    /**
     * Makes point A follow to the desired location.
     * @param targetX Target X axis.
     * @param targetY Target Y axis.
     */
    public void follow(float targetX, float targetY) {
        SegmentVector target = new SegmentVector(targetX, targetY);
        SegmentVector dir = SegmentVector.subtract(target, pointB);

        double targetAngle = dir.heading();
        if (this.child != null){
            double childAngle = child.heading();

            double difference = Math.abs(targetAngle - childAngle);

            if (difference > Math.PI) {
                difference = 2 * Math.PI - difference;
            }

            int sign = (targetAngle - childAngle >= 0 && targetAngle - childAngle <= Math.toRadians(180)) || (targetAngle - childAngle <= Math.toRadians(-180) && targetAngle - childAngle >= Math.toRadians(-360)) ? 1 : -1;

            double maxAngle = Math.toRadians(35); // Change this based on how much freedom you want (35 is a good number)

            if (difference > maxAngle){
                targetAngle = (childAngle + (maxAngle * sign)) % (Math.PI * 2.0);
            }
        }

        pointA = target;

        // Smoothly interpolate the angle
        double currentAngle = this.heading();
        double interpolatedAngle = lerpAngle(currentAngle, targetAngle, 0.35); // Higher = snappier Lower = less snappy

        float interpolatedDx = length * cos((float) interpolatedAngle);
        float interpolatedDy = length * sin((float) interpolatedAngle);

        pointB.set(pointA.x - interpolatedDx, pointA.y - interpolatedDy);
    }

    /**
     * Linear interpolation function for angles.
     * @param startAngle The starting angle in radians.
     * @param endAngle The ending angle in radians.
     * @param t The interpolation factor, ranging from 0 to 1.
     * @return The interpolated angle between startAngle and endAngle.
     */
    private double lerpAngle(double startAngle, double endAngle, double t) {
        double difference = endAngle - startAngle;
        if (difference > Math.PI) {
            difference -= 2 * Math.PI;
        } else if (difference < -Math.PI) {
            difference += 2 * Math.PI;
        }
        return startAngle + t * difference;
    }

    public float heading(){
        return SegmentVector.subtract(pointA, pointB).heading();
    }

    /**
     * Shows a segment with the correct indication.
     */
    public void show() {
        if (Main.indicateStartEnd) {
            if (index == Main.numberOfSegments - 1) {
                // First segment
                pg.stroke(Main.leadingSegmentColour);
            } else if (index == 0) {
                // Last segment
                pg.stroke(Main.endSegmentColour);
            } else {
                // Every other segment
                pg.stroke(Main.neutralSegmentColour);
            }
        } else {
            // Show everything as the neutral colour
            pg.stroke(Main.neutralSegmentColour);
        }
        pg.strokeWeight(3);
        //pg.point(this.pointB.x, this.pointB.y);
        pg.line(this.pointA.x, this.pointA.y, this.pointB.x, this.pointB.y);
    }
}
