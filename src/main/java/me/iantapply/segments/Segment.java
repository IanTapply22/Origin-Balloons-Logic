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

        float angle = 0;
        float dx = length * cos(angle);
        float dy = length * sin(angle);
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

        float angle = 0;
        float dx = length * cos(angle);
        float dy = length * sin(angle);
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

        double angle = dir.heading();
        if (this.child != null){
            double child_angle = child.heading();

            double a = angle;
            double b = child_angle;
            double d = Math.abs(a - b) % Math.toRadians(360);

            int sign = (a - b >= 0 && a - b <= Math.toRadians(180)) || (a - b <=Math.toRadians(-180) && a- b>= Math.toRadians(-360)) ? 1 : -1;

            double max_angle = Math.toRadians(35);
            if (d > max_angle){
                angle = (child_angle + (max_angle * sign)) % (Math.PI * 2.0);
            }
        }
        float dx = length * cos((float)angle);
        float dy = length * sin((float)angle);

        pointA = target;
        this.pointB.set(this.pointA.x - dx, this.pointA.y - dy);

//        pointB = SegmentVector.add(pointA, dir);
//        System.out.println("wanted: " + angle + " got: " + this.heading());
    }

    public float heading(){
        return SegmentVector.subtract(pointA, pointB).heading();
    }


    /**
     * Updates position of point b (closest to cursor).
     */
    public void update() {
//        this.calculatePointB();
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
//        pg.point(this.pointB.x, this.pointB.y);
        pg.line(this.pointA.x, this.pointA.y, this.pointB.x, this.pointB.y);
    }
}
