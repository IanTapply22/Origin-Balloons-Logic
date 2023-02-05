package me.iantapply;

import processing.core.PGraphics;
import processing.core.PVector;

import static processing.core.PApplet.*;

public class Segment {

    PVector pointA;
    float angle = 0;
    float length;
    PVector pointB = new PVector();
    Segment parent = null;
    Segment child = null;
    float index;
    PGraphics pg;

    public Segment(float x, float y, float length, float i, PGraphics pg) {
        this.pointA = new PVector(x, y);
        this.length = length;
        this.index = i;
        this.pg = pg;

        this.calculateB();
    }

    public Segment(Segment parent, float length, float i, PGraphics pg) {
        this.parent = parent;
        this.pointA = parent.pointB.copy();
        this.length = length;
        this.index = i;
        this.pg = pg;

        this.calculateB();
    }

    public void follow() {
        float targetX = this.child.pointA.x;
        float targetY = this.child.pointA.y;
        follow(targetX, targetY);
    }

    public void follow(float targetX, float targetY) {
        PVector target = new PVector(targetX, targetY);
        PVector dir = PVector.sub(target, pointA);
        angle = dir.heading();
        dir.setMag(length);
        dir.mult(-1);
        pointA = PVector.add(target, dir);
    }


    /**
     * Calculates and sets position of point b
     */
    public void calculateB() {
        float dx = length * cos(this.angle);
        float dy = length * sin(this.angle);
        //System.out.println(dx + ", " + dy);
        this.pointB.set(this.pointA.x + dx, this.pointA.y + dy);
    }

    /**
     * Updates position of point b (closest to cursor)
     */
    public void update() {
        this.calculateB();
    }

    public void show() {
        // First segment
        if (index == Main.numberOfSegments - 1) {
            pg.stroke(255, 0, 0);
        // Last segment
        } else if (index == 0) {
            pg.stroke(255, 165, 0);
        // Every other segment
        } else {
            pg.stroke(255);
        }
        // Width (redundant)
        pg.strokeWeight(3);
        pg.line(this.pointA.x, this.pointA.y, this.pointB.x, this.pointB.y);
    }
}
