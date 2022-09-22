package stevebot.core.math.vectors.vec2;

public class Vector2f implements IVector2 {


    /**
     * Used when comparing two float values.
     */
    public static final float EPSILON = 0.000001f;


    /**
     * creates a new vector from point a to point b
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the created vector
     */
    public static Vector2f createVectorAB(IVector2 a, IVector2 b) {
        return Vector2f.createVectorAB(a.getFloatX(), a.getFloatY(), b.getFloatX(), b.getFloatY());
    }


    /**
     * creates a new vector from point a to point b
     *
     * @param ax the x-position of the first point
     * @param ay the y-position of the first point
     * @param bx the x-position of the second point
     * @param by the y-position of the second point
     * @return the created vector
     */
    public static Vector2f createVectorAB(float ax, float ay, float bx, float by) {
        return new Vector2f(bx - ax, by - ay);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector2f setVectorAB(IVector2 a, IVector2 b, Vector2f dst) {
        return Vector2f.setVectorAB(a.getFloatX(), a.getFloatY(), b.getFloatX(), b.getFloatY(), dst);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param ax the x-position of the first point
     * @param ay the y-position of the first point
     * @param bx the x-position of the second point
     * @param by the y-position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector2f setVectorAB(float ax, float ay, float bx, float by, Vector2f dst) {
        return dst.set(bx - ax, by - ay);
    }


    /**
     * the x-component of this vector (index=0)
     */
    public float x;

    /**
     * the y-component of this vector (index=1)
     */
    public float y;


    /**
     * creates a zero-vector
     */
    public Vector2f() {
        this(0);
    }


    /**
     * creates a vector with the same values for x and y
     */
    public Vector2f(float xy) {
        this(xy, xy);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector2f(IVector2 vec) {
        this(vec.getFloatX(), vec.getFloatY());
    }


    /**
     * creates a vector with the given x and y values
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public int getDimensions() {
        return 2;
    }


    @Override
    public VectorType getVectorType() {
        return VectorType.FLOAT;
    }


    @Override
    public Vector2f setAt(int index, Number value) {
        if (index == 0) {
            this.x = value.floatValue();
            return this;
        }
        if (index == 1) {
            this.y = value.floatValue();
            return this;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public int getInt(int index) {
        if (index == 0) {
            return (int) this.x;
        }
        if (index == 1) {
            return (int) this.y;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public long getLong(int index) {
        if (index == 0) {
            return (long) this.x;
        }
        if (index == 1) {
            return (long) this.y;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public float getFloat(int index) {
        if (index == 0) {
            return this.x;
        }
        if (index == 1) {
            return this.y;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public double getDouble(int index) {
        if (index == 0) {
            return this.x;
        }
        if (index == 1) {
            return this.y;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public Vector2f negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }


    @Override
    public Vector2f normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        return this;
    }


    @Override
    public Vector2f copy() {
        return new Vector2f(this);
    }


    @Override
    public Vector2f set(IVector2 v) {
        return this.set(v.getFloatX(), v.getFloatY());
    }


    @Override
    public Vector2f set(Number x, Number y) {
        return this.set(x.floatValue(), y.floatValue());
    }


    @Override
    public Vector2f set(Number xy) {
        return this.set(xy.floatValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xy the value of the x- and y-component
     * @return this vector for chaining
     */
    public Vector2f set(float xy) {
        return this.set(xy, xy);
    }


    /**
     * Sets the components of this vector.
     *
     * @param x the value of the x-component
     * @param y the value of the y-component
     * @return this vector for chaining
     */
    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }


    @Override
    public int getIntX() {
        return (int) this.x;
    }

    @Override
    public int getIntY() {
        return (int) this.y;
    }

    @Override
    public long getLongX() {
        return (long) this.x;
    }

    @Override
    public long getLongY() {
        return (long) this.y;
    }

    @Override
    public float getFloatX() {
        return this.x;
    }

    @Override
    public float getFloatY() {
        return this.y;
    }

    @Override
    public double getDoubleX() {
        return this.x;
    }

    @Override
    public double getDoubleY() {
        return this.y;
    }


    @Override
    public Vector2i toIntVector() {
        return new Vector2i(this);
    }


    @Override
    public Vector2l toLongVector() {
        return new Vector2l(this);
    }


    @Override
    public Vector2f toFloatVector() {
        return this.copy();
    }


    @Override
    public Vector2d toDoubleVector() {
        return new Vector2d(this);
    }


    @Override
    public ConstVector2<Vector2f> toConstVector() {
        return new ConstVector2<Vector2f>(this);
    }


    @Override
    public String toString() {
        return "Vector2f." + this.hashCode() + "(" + this.x + ", " + this.y + ")";
    }


    @Override
    public Vector2f add(IVector2 vec) {
        return this.add(vec.getFloatX(), vec.getFloatY());
    }


    @Override
    public Vector2f add(Number x, Number y) {
        return this.add(x.floatValue(), y.floatValue());
    }


    @Override
    public Vector2f add(Number xy) {
        return this.add(xy.floatValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2f add(float xy) {
        return this.add(xy, xy);
    }


    /**
     * Adds the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2f add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }


    @Override
    public Vector2f sub(IVector2 vec) {
        return this.sub(vec.getFloatX(), vec.getFloatY());
    }


    @Override
    public Vector2f sub(Number x, Number y) {
        return this.sub(x.floatValue(), y.floatValue());

    }


    @Override
    public Vector2f sub(Number xy) {
        return this.sub(xy.floatValue());
    }


    /**
     * Subtracts the given component to this vector.
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2f sub(float xy) {
        return this.sub(xy, xy);
    }


    /**
     * Subtracts the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2f sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }


    @Override
    public Vector2f mul(IVector2 vec) {
        return this.mul(vec.getFloatX(), vec.getFloatY());
    }


    @Override
    public Vector2f mul(Number x, Number y) {
        return this.mul(x.floatValue(), y.floatValue());
    }


    @Override
    public Vector2f scale(Number xy) {
        return this.scale(xy.floatValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xy the scalar
     * @return this vector for chaining
     */
    public Vector2f scale(float xy) {
        return this.mul(xy, xy);
    }


    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2f mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }


    @Override
    public Vector2f div(IVector2 vec) {
        return this.div(vec.getFloatX(), vec.getFloatY());
    }


    @Override
    public Vector2f div(Number x, Number y) {
        return this.div(x.floatValue(), y.floatValue());
    }


    @Override
    public Vector2f div(Number xy) {
        return this.div(xy.floatValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2f div(float xy) {
        return this.div(xy, xy);
    }


    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2f div(float x, float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }


    @Override
    public Number crossGen(IVector2 vec) {
        return this.cross(vec);
    }


    @Override
    public Number crossGen(Number x, Number y) {
        return this.cross(x.floatValue(), y.floatValue());
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float cross(IVector2 vec) {
        return this.cross(vec.getFloatX(), vec.getFloatY());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @return the result
     */
    public float cross(float x, float y) {
        return (this.x * y) - (this.y * x);
    }


    @Override
    public Number dotGen(IVector2 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y) {
        return this.dot(x.floatValue(), y.floatValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dot(IVector2 vec) {
        return this.dot(vec.getFloatX(), vec.getFloatY());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @return the result
     */
    public float dot(float x, float y) {
        return (this.x * x) + (this.y * y);
    }


    @Override
    public Number dist2Gen(IVector2 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y) {
        return this.dist2(x.floatValue(), y.floatValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist2(IVector2 vec) {
        return this.dist2(vec.getFloatX(), vec.getFloatY());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @return the result
     */
    public float dist2(float x, float y) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y);
    }


    @Override
    public Number distGen(IVector2 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y) {
        return this.dist(x.floatValue(), y.floatValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector2 vec) {
        return this.dist(vec.getFloatX(), vec.getFloatY());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @return the result
     */
    public float dist(float x, float y) {
        return (float) Math.sqrt(this.dist2(x, y));
    }


    @Override
    public Number length2Gen() {
        return length2();
    }


    /**
     * Calculates the squared length of this vector.
     *
     * @return the result
     */
    public float length2() {
        return x * x + y * y;
    }


    @Override
    public Number lengthGen() {
        return length();
    }


    /**
     * Calculates the length of this vector.
     *
     * @return the result
     */
    public float length() {
        return (float) Math.sqrt(length2());
    }


    @Override
    public Vector2f setLength(Number length) {
        return this.setLength(length.floatValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector2f setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector2f limitLength(Number maxLength) {
        return limitLength(maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector2f limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public Vector2f clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.floatValue(), maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector2f clampLength(float minLength, float maxLength) {
        float len = length();
        if (len < minLength) {
            div(len);
            scale(minLength);
        }
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public Number angleRadGen(IVector2 vec) {
        return this.angleRad(vec);
    }


    @Override
    public Number angleRadGen(Number x, Number y) {
        return this.angleRad(x.floatValue(), y.floatValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector2 vec) {
        return this.angleRad(vec.getFloatX(), vec.getFloatY());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @return the result
     */
    public float angleRad(float x, float y) {
        final float cross = cross(x, y);
        final float dot = dot(x, y);
        return (float) Math.atan2(cross, dot);
    }


    @Override
    public Number angleDegGen(IVector2 vec) {
        return this.angleDeg(vec);
    }


    @Override
    public Number angleDegGen(Number x, Number y) {
        return this.angleDeg(x.floatValue(), y.floatValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector2 vec) {
        return this.angleDeg(vec.getFloatX(), vec.getFloatY());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @return the result
     */
    public float angleDeg(float x, float y) {
        return (float) Math.toDegrees(angleRad(x, y));
    }


    @Override
    public Vector2f rotateRad(Number angleRad) {
        return this.rotateRad(angleRad.floatValue());
    }


    /**
     * Rotates this vector by the given angle in radians.
     *
     * @param angleRad the angle in radians
     * @return this vector for chaining
     */
    public Vector2f rotateRad(float angleRad) {
        final double cos = Math.cos(angleRad);
        final double sin = Math.sin(angleRad);
        this.x = (float) (x * cos - y * sin);
        this.y = (float) (x * sin + y * cos);
        return this;
    }


    @Override
    public Vector2f rotateDeg(Number angleDeg) {
        return this.rotateDeg(angleDeg.floatValue());
    }


    /**
     * Rotates this vector by the given angle in degrees.
     *
     * @param angleDeg the angle in degrees
     * @return this vector for chaining
     */
    public Vector2f rotateDeg(float angleDeg) {
        return this.rotateRad(Math.toRadians(angleDeg));
    }


    @Override
    public Vector2f project(IVector2 vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2Gen().floatValue();
        this.set(vec).scale(dot / len2);
        return this;
    }


    /**
     * Projects this vector on the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector2f project(Vector2f vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public Vector2f reflect(IVector2 n) {  // n should be normalized before
        // I - 2.0 * dot(N, I) * N
        final float dotN = this.dot(n);
        float rx = (2.0f * dotN * n.getFloatX());
        float ry = (2.0f * dotN * n.getFloatY());
        this.sub(rx, ry);
        return this;
    }


    @Override
    public IVector2 refract(IVector2 n, float eta) { // https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/refract.xhtml
        final float dotN = this.dot(n);
        final float k = 1.0f - eta * eta * (1.0f - dotN * dotN);
        if (k < 0.0) {
            this.set(0f, 0f);
        } else {
            final double sqrtK = Math.sqrt(k);
            float rx = (float) (eta * this.x - (eta * dotN + sqrtK) * n.getFloatX());
            float ry = (float) (eta * this.y - (eta * dotN + sqrtK) * n.getFloatY());
            this.set(rx, ry);
        }
        return this;
    }


    @Override
    public Number componentSumGen() {
        return componentSum();
    }


    /**
     * Calculates the sum of the components.
     *
     * @return the result
     */
    public float componentSum() {
        return x + y;
    }


    @Override
    public Number componentMinGen() {
        return this.componentMin();
    }


    /**
     * Calculates the smallest component.
     *
     * @return the result
     */
    public float componentMin() {
        return Math.min(x, y);
    }


    @Override
    public Number componentMaxGen() {
        return this.componentMax();
    }


    /**
     * Calculates the largest component.
     *
     * @return the result
     */
    public float componentMax() {
        return Math.max(x, y);
    }


    @Override
    public Vector2f clampComponents(Number min, Number max) {
        return this.clampComponents(min.floatValue(), max.floatValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector2f clampComponents(float min, float max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        return this;
    }


    @Override
    public boolean compare(IVector2 vec) {
        if (Math.abs(x - vec.getFloatX()) > EPSILON) {
            return false;
        }
		return !(Math.abs(y - vec.getFloatY()) > EPSILON);
	}


    @Override
    public boolean isUnit() {
		return !(Math.abs(length2() - 1f) > EPSILON);
    }


    @Override
    public boolean isZero() {
		return (Math.abs(x) < EPSILON) && (Math.abs(y) < EPSILON);
    }


    @Override
    public boolean isPerpendicular(IVector2 vec) {
		return dot(vec) < EPSILON;
    }

}
