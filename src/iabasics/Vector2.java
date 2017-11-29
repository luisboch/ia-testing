/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics;

/**
 *
 * @author carlos.boch
 */
public class Vector2 {


    /**
     * The x component of the vector.
     */
    public float x;
    /**
     * The y component of the vector.
     */
    public float y;

    /**
     * Create a new {@link Vector2f} and initialize its components to zero.
     */
    public Vector2() {
    }

    /**
     * Create a new {@link Vector2f} and initialize both of its components with
     * the given value.
     *
     * @param d the value of both components
     */
    public Vector2(float d) {
        this(d, d);
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the given
     * values.
     *
     * @param x the x component
     * @param y the y component
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new {@link Vector2f} and initialize its components to the one of
     * the given vector.
     *
     * @param v the {@link Vector2} to copy the values from
     */
    public Vector2(Vector2 v) {
        x = v.x();
        y = v.y();
    }

//#endif

    /* (non-Javadoc)
     * @see org.joml.Vector2#x()
     */
    public float x() {
        return this.x;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#y()
     */
    public float y() {
        return this.y;
    }

    /**
     * Set the x and y components to the supplied value.
     *
     * @param d the value of both components
     * @return this
     */
    public Vector2 set(float d) {
        return set(d, d);
    }

    /**
     * Set the x and y components to the supplied values.
     *
     * @param x the x component
     * @param y the y component
     * @return this
     */
    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set this {@link Vector2} to the values of v.
     *
     * @param v the vector to copy from
     * @return this
     */
    public Vector2 set(Vector2 v) {
        x = v.x();
        y = v.y();
        return this;
    }

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component the component whose value to set, within <tt>[0..1]</tt>
     * @param value the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within
     * <tt>[0..1]</tt>
     */
    public Vector2 setComponent(int component, float value) throws IllegalArgumentException {
        switch (component) {
            case 0:
                x = value;
                break;
            case 1:
                y = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }


    /**
     * Set this vector to be one of its perpendicular vectors.
     *
     * @return this
     */
    public Vector2 perpendicular() {
        return set(y, x * -1);
    }

    /**
     * Subtract <code>v</code> from this vector.
     *
     * @param v the vector to subtract
     * @return this
     */
    public Vector2 sub(Vector2 v) {
        x -= v.x();
        y -= v.y();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#sub(org.joml.Vector2, org.joml.Vector2f)
     */
    public Vector2 sub(Vector2 v, Vector2 dest) {
        dest.x = x - v.x();
        dest.y = y - v.y();
        return dest;
    }

    /**
     * Subtract <tt>(x, y)</tt> from this vector.
     *
     * @param x the x component to subtract
     * @param y the y component to subtract
     * @return this
     */
    public Vector2 sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#sub(float, float, org.joml.Vector2f)
     */
    public Vector2 sub(float x, float y, Vector2 dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#dot(org.joml.Vector2)
     */
    public float dot(Vector2 v) {
        return x * v.x() + y * v.y();
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#angle(org.joml.Vector2)
     */
    public float angle(Vector2 v) {
        float dot = x * v.x() + y * v.y();
        float det = x * v.y() - y * v.x();
        return (float) Math.atan2(det, dot);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#length()
     */
    public float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#lengthSquared()
     */
    public float lengthSquared() {
        return x * x + y * y;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#distance(org.joml.Vector2)
     */
    public float distance(Vector2 v) {
        return distance(v.x(), v.y());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#distanceSquared(org.joml.Vector2)
     */
    public float distanceSquared(Vector2 v) {
        return distanceSquared(v.x(), v.y());
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#distance(float, float)
     */
    public float distance(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#distanceSquared(float, float)
     */
    public float distanceSquared(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return dx * dx + dy * dy;
    }

    /**
     * Normalize this vector.
     *
     * @return this
     */
    public Vector2 normalize() {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y));
        x *= invLength;
        y *= invLength;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#normalize(org.joml.Vector2f)
     */
    public Vector2 normalize(Vector2 dest) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y));
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    /**
     * Scale this vector to have the given length.
     *
     * @param length the desired length
     * @return this
     */
    public Vector2 normalize(float length) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y)) * length;
        x *= invLength;
        y *= invLength;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#normalize(float, org.joml.Vector2f)
     */
    public Vector2 normalize(float length, Vector2 dest) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y)) * length;
        dest.x = x * invLength;
        dest.y = y * invLength;
        return dest;
    }

    /**
     * Add <code>v</code> to this vector.
     *
     * @param v the vector to add
     * @return this
     */
    public Vector2 add(Vector2 v) {
        x += v.x();
        y += v.y();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#add(org.joml.Vector2, org.joml.Vector2f)
     */
    public Vector2 add(Vector2 v, Vector2 dest) {
        dest.x = x + v.x();
        dest.y = y + v.y();
        return dest;
    }

    /**
     * Increment the components of this vector by the given values.
     *
     * @param x the x component to add
     * @param y the y component to add
     * @return this
     */
    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#add(float, float, org.joml.Vector2f)
     */
    public Vector2 add(float x, float y, Vector2 dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        return dest;
    }

    /**
     * Set all components to zero.
     *
     * @return this
     */
    public Vector2 zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        return this;
    }
    /**
     * Negate this vector.
     *
     * @return this
     */
    public Vector2 negate() {
        x = -x;
        y = -y;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#negate(org.joml.Vector2f)
     */
    public Vector2 negate(Vector2 dest) {
        dest.x = -x;
        dest.y = -y;
        return dest;
    }

    /**
     * Multiply the components of this vector by the given scalar.
     *
     * @param scalar the value to multiply this vector's components by
     * @return this
     */
    public Vector2 mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#mul(float, org.joml.Vector2f)
     */
    public Vector2 mul(float scalar, Vector2 dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        return dest;
    }

    /**
     * Multiply the components of this Vector2f by the given scalar values and
     * store the result in <code>this</code>.
     *
     * @param x the x component to multiply this vector by
     * @param y the y component to multiply this vector by
     * @return this
     */
    public Vector2 mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#mul(float, float, org.joml.Vector2f)
     */
    public Vector2 mul(float x, float y, Vector2 dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        return dest;
    }

    /**
     * Multiply this Vector2f component-wise by another Vector2f.
     *
     * @param v the vector to multiply by
     * @return this
     */
    public Vector2 mul(Vector2 v) {
        x *= v.x();
        y *= v.y();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#mul(org.joml.Vector2, org.joml.Vector2f)
     */
    public Vector2 mul(Vector2 v, Vector2 dest) {
        dest.x = x * v.x();
        dest.y = y * v.y();
        return dest;
    }


    /**
     * Linearly interpolate <code>this</code> and <code>other</code> using the
     * given interpolation factor <code>t</code> and store the result in
     * <code>this</code>.
     * <p>
     * If <code>t</code> is <tt>0.0</tt> then the result is <code>this</code>.
     * If the interpolation factor is <code>1.0</code> then the result is
     * <code>other</code>.
     *
     * @param other the other vector
     * @param t the interpolation factor between 0.0 and 1.0
     * @return this
     */
    public Vector2 lerp(Vector2 other, float t) {
        return lerp(other, t, this);
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#lerp(org.joml.Vector2, float, org.joml.Vector2f)
     */
    public Vector2 lerp(Vector2 other, float t, Vector2 dest) {
        dest.x = x + (other.x() - x) * t;
        dest.y = y + (other.y() - y) * t;
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector2 other = (Vector2) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        return true;
    }



    /**
     * Add the component-wise multiplication of <code>a * b</code> to this
     * vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public Vector2 fma(Vector2 a, Vector2 b) {
        x += a.x() * b.x();
        y += a.y() * b.y();
        return this;
    }

    /**
     * Add the component-wise multiplication of <code>a * b</code> to this
     * vector.
     *
     * @param a the first multiplicand
     * @param b the second multiplicand
     * @return this
     */
    public Vector2 fma(float a, Vector2 b) {
        x += a * b.x();
        y += a * b.y();
        return this;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#fma(org.joml.Vector2, org.joml.Vector2, org.joml.Vector2f)
     */
    public Vector2 fma(Vector2 a, Vector2 b, Vector2 dest) {
        dest.x = x + a.x() * b.x();
        dest.y = y + a.y() * b.y();
        return dest;
    }

    /* (non-Javadoc)
     * @see org.joml.Vector2#fma(float, org.joml.Vector2, org.joml.Vector2f)
     */
    public Vector2 fma(float a, Vector2 b, Vector2 dest) {
        dest.x = x + a * b.x();
        dest.y = y + a * b.y();
        return dest;
    }

}
