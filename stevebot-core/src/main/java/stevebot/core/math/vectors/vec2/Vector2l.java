package stevebot.core.math.vectors.vec2;

public class Vector2l implements IVector2 {


    /**
     * creates a new vector from point a to point b
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the created vector
     */
    public static Vector2l createVectorAB(IVector2 a, IVector2 b) {
        return Vector2l.createVectorAB(a.getLongX(), a.getLongY(), b.getLongX(), b.getLongY());
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
    public static Vector2l createVectorAB(long ax, long ay, long bx, long by) {
        return new Vector2l(bx - ax, by - ay);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector2l setVectorAB(IVector2 a, IVector2 b, Vector2l dst) {
        return Vector2l.setVectorAB(a.getLongX(), a.getLongY(), b.getLongX(), b.getLongY(), dst);
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
    public static Vector2l setVectorAB(long ax, long ay, long bx, long by, Vector2l dst) {
        return dst.set(bx - ax, by - ay);
    }


    /**
     * the x-component of this vector (index=0)
     */
    public long x;

    /**
     * the y-component of this vector (index=1)
     */
    public long y;


    /**
     * creates a zero-vector
     */
    public Vector2l() {
        this(0);
    }


    /**
     * creates a vector with the same values for x and y
     */
    public Vector2l(long xy) {
        this(xy, xy);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector2l(IVector2 vec) {
        this(vec.getLongX(), vec.getLongY());
    }


    /**
     * creates a vector with the given x and y values
     */
    public Vector2l(long x, long y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public int getDimensions() {
        return 2;
    }


    @Override
    public VectorType getVectorType() {
        return VectorType.LONG;
    }


    @Override
    public Vector2l setAt(int index, Number value) {
        if (index == 0) {
            this.x = value.longValue();
            return this;
        }
        if (index == 1) {
            this.y = value.longValue();
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
            return (double) this.x;
        }
        if (index == 1) {
            return (double) this.y;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public Vector2l negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }


    @Override
    public Vector2l normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        return this;
    }


    @Override
    public Vector2l copy() {
        return new Vector2l(this);
    }


    @Override
    public Vector2l set(IVector2 v) {
        return this.set(v.getLongX(), v.getLongY());
    }


    @Override
    public Vector2l set(Number x, Number y) {
        return this.set(x.longValue(), y.longValue());
    }


    @Override
    public Vector2l set(Number xy) {
        return this.set(xy.longValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xy the value of the x- and y-component
     * @return this vector for chaining
     */
    public Vector2l set(long xy) {
        return this.set(xy, xy);
    }


    /**
     * Sets the components of this vector.
     *
     * @param x the value of the x-component
     * @param y the value of the y-component
     * @return this vector for chaining
     */
    public Vector2l set(long x, long y) {
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
        return (double) this.x;
    }

    @Override
    public double getDoubleY() {
        return (double) this.y;
    }


    @Override
    public Vector2i toIntVector() {
        return new Vector2i(this);
    }


    @Override
    public Vector2l toLongVector() {
        return copy();
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
    public ConstVector2<Vector2l> toConstVector() {
        return new ConstVector2<Vector2l>(this);
    }


    @Override
    public String toString() {
        return "Vector2l." + this.hashCode() + "(" + this.x + ", " + this.y + ")";
    }


    @Override
    public Vector2l add(IVector2 vec) {
        return this.add(vec.getLongX(), vec.getLongY());
    }


    @Override
    public Vector2l add(Number x, Number y) {
        return this.add(x.longValue(), y.longValue());
    }


    @Override
    public Vector2l add(Number xy) {
        return this.add(xy.longValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2l add(long xy) {
        return this.add(xy, xy);
    }


    /**
     * Adds the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2l add(long x, long y) {
        this.x += x;
        this.y += y;
        return this;
    }


    @Override
    public Vector2l sub(IVector2 vec) {
        return this.sub(vec.getLongX(), vec.getLongY());
    }


    @Override
    public Vector2l sub(Number x, Number y) {
        return this.sub(x.longValue(), y.longValue());

    }


    @Override
    public Vector2l sub(Number xy) {
        return this.sub(xy.longValue());
    }


    /**
     * Subtracts the given component to this vector.
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2l sub(long xy) {
        return this.sub(xy, xy);
    }


    /**
     * Subtracts the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2l sub(long x, long y) {
        this.x -= x;
        this.y -= y;
        return this;
    }


    @Override
    public Vector2l mul(IVector2 vec) {
        return this.mul(vec.getLongX(), vec.getLongY());
    }


    @Override
    public Vector2l mul(Number x, Number y) {
        return this.mul(x.longValue(), y.longValue());
    }


    @Override
    public Vector2l scale(Number xy) {
        return this.scale(xy.longValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xy the scalar
     * @return this vector for chaining
     */
    public Vector2l scale(long xy) {
        return this.mul(xy, xy);
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xy the scalar
     * @return this vector for chaining
     */
    public Vector2l scale(float xy) {
        return this.mul(xy, xy);
    }


    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2l mul(long x, long y) {
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
    public Vector2l mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }


    @Override
    public Vector2l div(IVector2 vec) {
        return this.div(vec.getLongX(), vec.getLongY());
    }


    @Override
    public Vector2l div(Number x, Number y) {
        return this.div(x.longValue(), y.longValue());
    }


    @Override
    public Vector2l div(Number xy) {
        return this.div(xy.longValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2l div(long xy) {
        return this.div(xy, xy);
    }


    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2l div(float x, float y) {
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
    public Vector2l div(long x, long y) {
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
        return this.cross(x.longValue(), y.longValue());
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public long cross(IVector2 vec) {
        return this.cross(vec.getLongX(), vec.getLongY());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @return the result
     */
    public long cross(long x, long y) {
        return (this.x * y) - (this.y * x);
    }


    @Override
    public Number dotGen(IVector2 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y) {
        return this.dot(x.longValue(), y.longValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public long dot(IVector2 vec) {
        return this.dot(vec.getLongX(), vec.getLongY());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @return the result
     */
    public long dot(long x, long y) {
        return (this.x * x) + (this.y * y);
    }


    @Override
    public Number dist2Gen(IVector2 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y) {
        return this.dist2(x.longValue(), y.longValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public long dist2(IVector2 vec) {
        return this.dist2(vec.getLongX(), vec.getLongY());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @return the result
     */
    public long dist2(long x, long y) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y);
    }


    @Override
    public Number distGen(IVector2 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y) {
        return this.dist(x.longValue(), y.longValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector2 vec) {
        return this.dist(vec.getLongX(), vec.getLongY());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @return the result
     */
    public float dist(long x, long y) {
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
    public long length2() {
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
    public Vector2l setLength(Number length) {
        return this.setLength(length.floatValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector2l setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector2l limitLength(Number maxLength) {
        return limitLength(maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector2l limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public Vector2l clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.floatValue(), maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector2l clampLength(float minLength, float maxLength) {
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
        return this.angleRad(x.longValue(), y.longValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector2 vec) {
        return this.angleRad(vec.getLongX(), vec.getLongY());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @return the result
     */
    public float angleRad(long x, long y) {
        final long cross = cross(x, y);
        final long dot = dot(x, y);
        return (float) Math.atan2(cross, dot);
    }


    @Override
    public Number angleDegGen(IVector2 vec) {
        return this.angleDeg(vec);
    }


    @Override
    public Number angleDegGen(Number x, Number y) {
        return this.angleDeg(x.longValue(), y.longValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector2 vec) {
        return this.angleDeg(vec.getLongX(), vec.getLongY());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @return the result
     */
    public float angleDeg(long x, long y) {
        return (float) Math.toDegrees(angleRad(x, y));
    }


    @Override
    public Vector2l rotateRad(Number angleRad) {
        return this.rotateRad(angleRad.floatValue());
    }


    /**
     * Rotates this vector by the given angle in radians.
     *
     * @param angleRad the angle in radians
     * @return this vector for chaining
     */
    public Vector2l rotateRad(float angleRad) {
        final double cos = Math.cos(angleRad);
        final double sin = Math.sin(angleRad);
        this.x = (int) (x * cos - y * sin);
        this.y = (int) (x * sin + y * cos);
        return this;
    }


    @Override
    public Vector2l rotateDeg(Number angleDeg) {
        return this.rotateDeg(angleDeg.intValue());
    }


    /**
     * Rotates this vector by the given angle in degrees.
     *
     * @param angleDeg the angle in degrees
     * @return this vector for chaining
     */
    public Vector2l rotateDeg(int angleDeg) {
        return this.rotateRad(Math.toRadians(angleDeg));
    }


    @Override
    public Vector2l project(IVector2 vec) {
        final long dot = dot(vec);
        final long len2 = vec.length2Gen().longValue();
        this.set(vec).scale(dot / len2);
        return this;
    }


    /**
     * Projects this vector on the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector2l project(Vector2l vec) {
        final long dot = dot(vec);
        final long len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public Vector2l reflect(IVector2 n) {  // n should be normalized before
        // I - 2.0 * dot(N, I) * N
        final long dotN = this.dot(n);
        double rx = (2.0f * dotN * n.getLongX());
        double ry = (2.0f * dotN * n.getLongY());
        this.sub(rx, ry);
        return this;
    }


    @Override
    public Vector2l refract(IVector2 n, float eta) { // https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/refract.xhtml
        final long dotN = this.dot(n);
        final float k = 1.0f - eta * eta * (1.0f - dotN * dotN);
        if (k < 0.0) {
            this.set(0f, 0f);
        } else {
            final double sqrtK = Math.sqrt(k);
            long rx = (long) (eta * this.x - (eta * dotN + sqrtK) * n.getLongX());
            long ry = (long) (eta * this.y - (eta * dotN + sqrtK) * n.getLongY());
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
    public long componentSum() {
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
    public long componentMin() {
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
    public long componentMax() {
        return Math.max(x, y);
    }


    @Override
    public Vector2l clampComponents(Number min, Number max) {
        return this.clampComponents(min.longValue(), max.longValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector2l clampComponents(long min, long max) {
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
