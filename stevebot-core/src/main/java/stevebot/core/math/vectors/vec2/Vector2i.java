package stevebot.core.math.vectors.vec2;

public class Vector2i implements IVector2 {


    /**
     * creates a new vector from point a to point b
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the created vector
     */
    public static Vector2i createVectorAB(IVector2 a, IVector2 b) {
        return Vector2i.createVectorAB(a.getIntX(), a.getIntY(), b.getIntX(), b.getIntY());
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
    public static Vector2i createVectorAB(int ax, int ay, int bx, int by) {
        return new Vector2i(bx - ax, by - ay);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector2i setVectorAB(IVector2 a, IVector2 b, Vector2i dst) {
        return Vector2i.setVectorAB(a.getIntX(), a.getIntY(), b.getIntX(), b.getIntY(), dst);
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
    public static Vector2i setVectorAB(int ax, int ay, int bx, int by, Vector2i dst) {
        return dst.set(bx - ax, by - ay);
    }


    /**
     * the x-component of this vector (index=0)
     */
    public int x;

    /**
     * the y-component of this vector (index=1)
     */
    public int y;


    /**
     * creates a zero-vector
     */
    public Vector2i() {
        this(0);
    }


    /**
     * creates a vector with the same values for x and y
     */
    public Vector2i(int xy) {
        this(xy, xy);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector2i(IVector2 vec) {
        this(vec.getIntX(), vec.getIntY());
    }


    /**
     * creates a vector with the given x and y values
     */
    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public int getDimensions() {
        return 2;
    }


    @Override
    public VectorType getVectorType() {
        return VectorType.INT;
    }


    @Override
    public Vector2i setAt(int index, Number value) {
        if (index == 0) {
            this.x = value.intValue();
            return this;
        }
        if (index == 1) {
            this.y = value.intValue();
            return this;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public int getInt(int index) {
        if (index == 0) {
            return this.x;
        }
        if (index == 1) {
            return this.y;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public long getLong(int index) {
        if (index == 0) {
            return this.x;
        }
        if (index == 1) {
            return this.y;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public float getFloat(int index) {
        if (index == 0) {
            return (float) this.x;
        }
        if (index == 1) {
            return (float) this.y;
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
    public Vector2i negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }


    @Override
    public Vector2i normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        return this;
    }


    @Override
    public Vector2i copy() {
        return new Vector2i(this);
    }


    @Override
    public Vector2i set(IVector2 v) {
        return this.set(v.getIntX(), v.getIntY());
    }


    @Override
    public Vector2i set(Number x, Number y) {
        return this.set(x.intValue(), y.intValue());
    }


    @Override
    public Vector2i set(Number xy) {
        return this.set(xy.intValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xy the value of the x- and y-component
     * @return this vector for chaining
     */
    public Vector2i set(int xy) {
        return this.set(xy, xy);
    }


    /**
     * Sets the components of this vector.
     *
     * @param x the value of the x-component
     * @param y the value of the y-component
     * @return this vector for chaining
     */
    public Vector2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }


    @Override
    public int getIntX() {
        return this.x;
    }

    @Override
    public int getIntY() {
        return this.y;
    }

    @Override
    public long getLongX() {
        return this.x;
    }

    @Override
    public long getLongY() {
        return this.y;
    }

    @Override
    public float getFloatX() {
        return (float) this.x;
    }

    @Override
    public float getFloatY() {
        return (float) this.y;
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
        return copy();
    }


    @Override
    public Vector2l toLongVector() {
        return new Vector2l(this);
    }


    @Override
    public Vector2f toFloatVector() {
        return new Vector2f(this);
    }


    @Override
    public Vector2d toDoubleVector() {
        return new Vector2d(this);
    }


    @Override
    public ConstVector2<Vector2i> toConstVector() {
        return new ConstVector2<Vector2i>(this);
    }


    @Override
    public String toString() {
        return "Vector2i." + this.hashCode() + "(" + this.x + ", " + this.y + ")";
    }


    @Override
    public Vector2i add(IVector2 vec) {
        return this.add(vec.getIntX(), vec.getIntY());
    }


    @Override
    public Vector2i add(Number x, Number y) {
        return this.add(x.intValue(), y.intValue());
    }


    @Override
    public Vector2i add(Number xy) {
        return this.add(xy.intValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2i add(int xy) {
        return this.add(xy, xy);
    }


    /**
     * Adds the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2i add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }


    @Override
    public Vector2i sub(IVector2 vec) {
        return this.sub(vec.getIntX(), vec.getIntY());
    }


    @Override
    public Vector2i sub(Number x, Number y) {
        return this.sub(x.intValue(), y.intValue());

    }


    @Override
    public Vector2i sub(Number xy) {
        return this.sub(xy.intValue());
    }


    /**
     * Subtracts the given component to this vector.
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2i sub(int xy) {
        return this.sub(xy, xy);
    }


    /**
     * Subtracts the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2i sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }


    @Override
    public Vector2i mul(IVector2 vec) {
        return this.mul(vec.getIntX(), vec.getIntY());
    }


    @Override
    public Vector2i mul(Number x, Number y) {
        return this.mul(x.intValue(), y.intValue());
    }


    @Override
    public Vector2i scale(Number xy) {
        return this.scale(xy.intValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xy the scalar
     * @return this vector for chaining
     */
    public Vector2i scale(int xy) {
        return this.mul(xy, xy);
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xy the scalar
     * @return this vector for chaining
     */
    public Vector2i scale(float xy) {
        return this.mul(xy, xy);
    }


    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2i mul(int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }


    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2i mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }


    @Override
    public Vector2i div(IVector2 vec) {
        return this.div(vec.getIntX(), vec.getIntY());
    }


    @Override
    public Vector2i div(Number x, Number y) {
        return this.div(x.intValue(), y.intValue());
    }


    @Override
    public Vector2i div(Number xy) {
        return this.div(xy.intValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2i div(int xy) {
        return this.div(xy, xy);
    }


    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2i div(float x, float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }


    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2i div(int x, int y) {
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
        return this.cross(x.intValue(), y.intValue());
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public int cross(IVector2 vec) {
        return this.cross(vec.getIntX(), vec.getIntY());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @return the result
     */
    public int cross(int x, int y) {
        return (this.x * y) - (this.y * x);
    }


    @Override
    public Number dotGen(IVector2 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y) {
        return this.dot(x.intValue(), y.intValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public int dot(IVector2 vec) {
        return this.dot(vec.getIntX(), vec.getIntY());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @return the result
     */
    public int dot(int x, int y) {
        return (this.x * x) + (this.y * y);
    }


    @Override
    public Number dist2Gen(IVector2 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y) {
        return this.dist2(x.intValue(), y.intValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public int dist2(IVector2 vec) {
        return this.dist2(vec.getIntX(), vec.getIntY());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @return the result
     */
    public int dist2(int x, int y) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y);
    }


    @Override
    public Number distGen(IVector2 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y) {
        return this.dist(x.intValue(), y.intValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector2 vec) {
        return this.dist(vec.getIntX(), vec.getIntY());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @return the result
     */
    public float dist(int x, int y) {
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
    public int length2() {
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
    public Vector2i setLength(Number length) {
        return this.setLength(length.floatValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector2i setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector2i limitLength(Number maxLength) {
        return limitLength(maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector2i limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public Vector2i clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.floatValue(), maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector2i clampLength(float minLength, float maxLength) {
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
        return this.angleRad(x.intValue(), y.intValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector2 vec) {
        return this.angleRad(vec.getIntX(), vec.getIntY());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @return the result
     */
    public float angleRad(int x, int y) {
        final int cross = cross(x, y);
        final int dot = dot(x, y);
        return (float) Math.atan2(cross, dot);
    }


    @Override
    public Number angleDegGen(IVector2 vec) {
        return this.angleDeg(vec);
    }


    @Override
    public Number angleDegGen(Number x, Number y) {
        return this.angleDeg(x.intValue(), y.intValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector2 vec) {
        return this.angleDeg(vec.getIntX(), vec.getIntY());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @return the result
     */
    public float angleDeg(int x, int y) {
        return (float) Math.toDegrees(angleRad(x, y));
    }


    @Override
    public Vector2i rotateRad(Number angleRad) {
        return this.rotateRad(angleRad.floatValue());
    }


    /**
     * Rotates this vector by the given angle in radians.
     *
     * @param angleRad the angle in radians
     * @return this vector for chaining
     */
    public Vector2i rotateRad(float angleRad) {
        final double cos = Math.cos(angleRad);
        final double sin = Math.sin(angleRad);
        this.x = (int) (x * cos - y * sin);
        this.y = (int) (x * sin + y * cos);
        return this;
    }


    @Override
    public Vector2i rotateDeg(Number angleDeg) {
        return this.rotateDeg(angleDeg.intValue());
    }


    /**
     * Rotates this vector by the given angle in degrees.
     *
     * @param angleDeg the angle in degrees
     * @return this vector for chaining
     */
    public Vector2i rotateDeg(int angleDeg) {
        return this.rotateRad(Math.toRadians(angleDeg));
    }


    @Override
    public Vector2i project(IVector2 vec) {
        final int dot = dot(vec);
        final int len2 = vec.length2Gen().intValue();
        this.set(vec).scale(dot / len2);
        return this;
    }


    /**
     * Projects this vector on the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector2i project(Vector2i vec) {
        final int dot = dot(vec);
        final int len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public Vector2i reflect(IVector2 n) {  // n should be normalized before
        // I - 2.0 * dot(N, I) * N
        final int dotN = this.dot(n);
        double rx = (2.0f * dotN * n.getIntX());
        double ry = (2.0f * dotN * n.getIntY());
        this.sub(rx, ry);
        return this;
    }


    @Override
    public Vector2i refract(IVector2 n, float eta) { // https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/refract.xhtml
        final int dotN = this.dot(n);
        final float k = 1.0f - eta * eta * (1.0f - dotN * dotN);
        if (k < 0.0) {
            this.set(0f, 0f);
        } else {
            final double sqrtK = Math.sqrt(k);
            double rx = (eta * this.x - (eta * dotN + sqrtK) * n.getIntX());
            double ry = (eta * this.y - (eta * dotN + sqrtK) * n.getIntY());
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
    public int componentSum() {
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
    public int componentMin() {
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
    public int componentMax() {
        return Math.max(x, y);
    }


    @Override
    public Vector2i clampComponents(Number min, Number max) {
        return this.clampComponents(min.intValue(), max.intValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector2i clampComponents(int min, int max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        return this;
    }


    @Override
    public boolean compare(IVector2 vec) {
        if (Math.abs(x - vec.getDoubleX()) > 0) {
            return false;
        }
		return !(Math.abs(y - vec.getDoubleY()) > 0);
	}


    @Override
    public boolean isUnit() {
		return Math.abs(length2() - 1) <= 0;
    }


    @Override
    public boolean isZero() {
		return (Math.abs(x) < 0) && (Math.abs(y) < 0);
    }


    @Override
    public boolean isPerpendicular(IVector2 vec) {
		return dot(vec) < 0;
    }


}
