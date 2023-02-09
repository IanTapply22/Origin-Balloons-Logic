package me.iantapply.segments;

/**
 * Methods contained within this class are originally from Processing(https://processing.org/)
 * This class is to make it easier to move this logic to a separate environment.
 */

import java.io.Serializable;

public class SegmentVector implements Serializable {

    /**
     * Segment vector axis.
     */
    public float x;
    public float y;
    public float z;

    /**
     * Creates a blank segment vector.
     */
    public SegmentVector() {
    }

    /**
     * Creates a segment vector holding a 3D space.
     * @param x X axis.
     * @param y Y axis.
     * @param z Z axis.
     */
    public SegmentVector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a segment vector holding a 2D space.
     * @param x X axis.
     * @param y Y axis.
     */
    public SegmentVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets a 3D's vectors axis.
     * @param x X axis float.
     * @param y Y axis float.
     * @param z Z axis float.
     */
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Sets a 2D's vectors axis.
     * @param x X axis float.
     * @param y Y axis float.
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    /**
     * Copies a segment vector.
     * @return The copy of the vector.
     */
    public SegmentVector copy() {
        return new SegmentVector(x, y, z);
    }

    /**
     * Gets magnitude of a vector.
     * @return The magnitude of a vector's axis.
     */
    public float magnitude() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Creates a new segment vector derived from 2 segment vectors added together.
     * @param v1 Segment vector 1.
     * @param v2 Segment vector 2.
     * @return The combined segment vector.
     */
    static public SegmentVector add(SegmentVector v1, SegmentVector v2) {
        return add(v1, v2, null);
    }

    /**
     * Adds two segment vectors together and sets it to target if provided.
     * @param v1 Segment vector 1.
     * @param v2 Segment vector 2.
     * @param target Target vector.
     * @return The combined vectors.
     */
    static public SegmentVector add(SegmentVector v1, SegmentVector v2, SegmentVector target) {
        if (target == null) {
            target = new SegmentVector(v1.x + v2.x,v1.y + v2.y, v1.z + v2.z);
        } else {
            target.set(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
        }
        return target;
    }

    /**
     * Subtracts two segment vectors from each other.
     * @param v1 Segment vector 1.
     * @param v2 Segment vector 2.
     * @return The result of the two subtracted vectors.
     */
    static public SegmentVector subtract(SegmentVector v1, SegmentVector v2) {
        return subtract(v1, v2, null);
    }

    /**
     * Subtracts two vectors from each other and sets it to a target is provided.
     * @param v1 Segment vector 1.
     * @param v2 Segment vector 2.
     * @param target Target vector.
     * @return The result of the subtraction.
     */
    static public SegmentVector subtract(SegmentVector v1, SegmentVector v2, SegmentVector target) {
        if (target == null) {
            target = new SegmentVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
        } else {
            target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
        }
        return target;
    }

    /**
     * Multiplies a vectors axis by a specified float.
     * @param n Number to multiply by.
     */
    public void multiply(float n) {
        x *= n;
        y *= n;
        z *= n;
    }

    /**
     * Divides a vectors axis by a specified float.
     * @param n Number to divide by.
     */
    public void divide(float n) {
        x /= n;
        y /= n;
        z /= n;
    }

    /**
     * Normalizes a vector's axis.
     */
    public void normalize() {
        float m = magnitude();
        if (m != 0 && m != 1) {
            divide(m);
        }
    }

    /**
     * Sets the magnitude of a vector.
     * @param len Length of segment.
     */
    public void setMagnitude(float len) {
        normalize();
        multiply(len);
    }

    /**
     * Gets the heading of a segment.
     * @return The heading of the segment.
     */
    public float heading() {
        return (float) Math.atan2(y, x);
    }
}
