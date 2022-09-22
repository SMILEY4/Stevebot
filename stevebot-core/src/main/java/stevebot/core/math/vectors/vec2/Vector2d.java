package stevebot.core.math.vectors.vec2;

public class Vector2d implements IVector2 {


    /**
     * Used when comparing two double values.
     */
    public static final double EPSILON = 0.0000001f;


    /**
     * creates a new vector from point a to point b
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the created vector
     */
    public static Vector2d createVectorAB(IVector2 a, IVector2 b) {
        return Vector2d.createVectorAB(a.getDoubleX(), a.getDoubleY(), b.getDoubleX(), b.getDoubleY());
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
    public static Vector2d createVectorAB(double ax, double ay, double bx, double by) {
        return new Vector2d(bx - ax, by - ay);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector2d setVectorAB(IVector2 a, IVector2 b, Vector2d dst) {
        return Vector2d.setVectorAB(a.getDoubleX(), a.getDoubleY(), b.getDoubleX(), b.getDoubleY(), dst);
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
    public static Vector2d setVectorAB(double ax, double ay, double bx, double by, Vector2d dst) {
        return dst.set(bx - ax, by - ay);
    }


    /**
     * the x-component of this vector (index=0)
     */
    public double x;

    /**
     * the y-component of this vector (index=1)
     */
    public double y;


    /**
     * creates a zero-vector
     */
    public Vector2d() {
        this(0);
    }


    /**
     * creates a vector with the same values for x and y
     */
    public Vector2d(double xy) {
        this(xy, xy);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector2d(IVector2 vec) {
        this(vec.getDoubleX(), vec.getDoubleY());
    }


    /**
     * creates a vector with the given x and y values
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public int getDimensions() {
        return 2;
    }


    @Override
    public VectorType getVectorType() {
        return VectorType.DOUBLE;
    }


    @Override
    public Vector2d setAt(int index, Number value) {
        if (index == 0) {
            this.x = value.doubleValue();
            return this;
        }
        if (index == 1) {
            this.y = value.doubleValue();
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
    public Vector2d negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }


    @Override
    public Vector2d normalize() {
        final double len = length();
        this.x /= len;
        this.y /= len;
        return this;
    }


    @Override
    public Vector2d copy() {
        return new Vector2d(this);
    }


    @Override
    public Vector2d set(IVector2 v) {
        return this.set(v.getDoubleX(), v.getDoubleY());
    }


    @Override
    public Vector2d set(Number x, Number y) {
        return this.set(x.doubleValue(), y.doubleValue());
    }


    @Override
    public Vector2d set(Number xy) {
        return this.set(xy.doubleValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xy the value of the x- and y-component
     * @return this vector for chaining
     */
    public Vector2d set(double xy) {
        return this.set(xy, xy);
    }


    /**
     * Sets the components of this vector.
     *
     * @param x the value of the x-component
     * @param y the value of the y-component
     * @return this vector for chaining
     */
    public Vector2d set(double x, double y) {
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
        return new Vector2i(this);
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
        return copy();
    }


    @Override
    public ConstVector2<Vector2d> toConstVector() {
        return new ConstVector2<Vector2d>(this);
    }


    @Override
    public String toString() {
        return "Vector2d." + this.hashCode() + "(" + this.x + ", " + this.y + ")";
    }


    @Override
    public Vector2d add(IVector2 vec) {
        return this.add(vec.getDoubleX(), vec.getDoubleY());
    }


    @Override
    public Vector2d add(Number x, Number y) {
        return this.add(x.doubleValue(), y.doubleValue());
    }


    @Override
    public Vector2d add(Number xy) {
        return this.add(xy.doubleValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2d add(double xy) {
        return this.add(xy, xy);
    }


    /**
     * Adds the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2d add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }


    @Override
    public Vector2d sub(IVector2 vec) {
        return this.sub(vec.getDoubleX(), vec.getDoubleY());
    }


    @Override
    public Vector2d sub(Number x, Number y) {
        return this.sub(x.doubleValue(), y.doubleValue());

    }


    @Override
    public Vector2d sub(Number xy) {
        return this.sub(xy.doubleValue());
    }


    /**
     * Subtracts the given component to this vector.
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2d sub(double xy) {
        return this.sub(xy, xy);
    }


    /**
     * Subtracts the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2d sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }


    @Override
    public Vector2d mul(IVector2 vec) {
        return this.mul(vec.getDoubleX(), vec.getDoubleY());
    }


    @Override
    public Vector2d mul(Number x, Number y) {
        return this.mul(x.doubleValue(), y.doubleValue());
    }


    @Override
    public Vector2d scale(Number xy) {
        return this.scale(xy.doubleValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xy the scalar
     * @return this vector for chaining
     */
    public Vector2d scale(double xy) {
        return this.mul(xy, xy);
    }


    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2d mul(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }


    @Override
    public Vector2d div(IVector2 vec) {
        return this.div(vec.getDoubleX(), vec.getDoubleY());
    }


    @Override
    public Vector2d div(Number x, Number y) {
        return this.div(x.doubleValue(), y.doubleValue());
    }


    @Override
    public Vector2d div(Number xy) {
        return this.div(xy.doubleValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xy the x- and y-component
     * @return this vector for chaining
     */
    public Vector2d div(double xy) {
        return this.div(xy, xy);
    }


    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @return this vector for chaining
     */
    public Vector2d div(double x, double y) {
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
        return this.cross(x.doubleValue(), y.doubleValue());
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double cross(IVector2 vec) {
        return this.cross(vec.getDoubleX(), vec.getDoubleY());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @return the result
     */
    public double cross(double x, double y) {
        return (this.x * y) - (this.y * x);
    }


    @Override
    public Number dotGen(IVector2 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y) {
        return this.dot(x.doubleValue(), y.doubleValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dot(IVector2 vec) {
        return this.dot(vec.getDoubleX(), vec.getDoubleY());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @return the result
     */
    public double dot(double x, double y) {
        return (this.x * x) + (this.y * y);
    }


    @Override
    public Number dist2Gen(IVector2 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y) {
        return this.dist2(x.doubleValue(), y.doubleValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dist2(IVector2 vec) {
        return this.dist2(vec.getDoubleX(), vec.getDoubleY());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @return the result
     */
    public double dist2(double x, double y) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y);
    }


    @Override
    public Number distGen(IVector2 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y) {
        return this.dist(x.doubleValue(), y.doubleValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dist(IVector2 vec) {
        return this.dist(vec.getDoubleX(), vec.getDoubleY());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @return the result
     */
    public double dist(double x, double y) {
        return Math.sqrt(this.dist2(x, y));
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
    public double length2() {
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
    public double length() {
        return Math.sqrt(length2());
    }


    @Override
    public Vector2d setLength(Number length) {
        return this.setLength(length.doubleValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector2d setLength(double length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector2d limitLength(Number maxLength) {
        return limitLength(maxLength.doubleValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector2d limitLength(double maxLength) {
        double len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public Vector2d clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.doubleValue(), maxLength.doubleValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector2d clampLength(double minLength, double maxLength) {
        double len = length();
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
        return this.angleRad(x.doubleValue(), y.doubleValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians.
     *
     * @param vec the vector
     * @return the result
     */
    public double angleRad(IVector2 vec) {
        return this.angleRad(vec.getDoubleX(), vec.getDoubleY());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @return the result
     */
    public double angleRad(double x, double y) {
        final double cross = cross(x, y);
        final double dot = dot(x, y);
        return Math.atan2(cross, dot);
    }


    @Override
    public Number angleDegGen(IVector2 vec) {
        return this.angleDeg(vec);
    }


    @Override
    public Number angleDegGen(Number x, Number y) {
        return this.angleDeg(x.doubleValue(), y.doubleValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees.
     *
     * @param vec the vector
     * @return the result
     */
    public double angleDeg(IVector2 vec) {
        return this.angleDeg(vec.getDoubleX(), vec.getDoubleY());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @return the result
     */
    public double angleDeg(double x, double y) {
        return Math.toDegrees(angleRad(x, y));
    }


    @Override
    public Vector2d rotateRad(Number angleRad) {
        return this.rotateRad(angleRad.doubleValue());
    }


    /**
     * Rotates this vector by the given angle in radians.
     *
     * @param angleRad the angle in radians
     * @return this vector for chaining
     */
    public Vector2d rotateRad(double angleRad) {
        final double cos = Math.cos(angleRad);
        final double sin = Math.sin(angleRad);
        final double newX = (x * cos - y * sin);
        final double newY = (x * sin + y * cos);
        return this.set(newX, newY);
    }


    @Override
    public Vector2d rotateDeg(Number angleDeg) {
        return this.rotateDeg(angleDeg.doubleValue());
    }


    /**
     * Rotates this vector by the given angle in degrees.
     *
     * @param angleDeg the angle in degrees
     * @return this vector for chaining
     */
    public Vector2d rotateDeg(double angleDeg) {
        return this.rotateRad(Math.toRadians(angleDeg));
    }


    @Override
    public Vector2d project(IVector2 vec) {
        final double dot = dot(vec);
        final double len2 = vec.length2Gen().doubleValue();
        this.set(vec).scale(dot / len2);
        return this;
    }


    /**
     * Projects this vector on the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector2d project(Vector2d vec) {
        final double dot = dot(vec);
        final double len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public Vector2d reflect(IVector2 n) {  // n should be normalized before
        // I - 2.0 * dot(N, I) * N
        final double dotN = this.dot(n);
        double rx = (2.0f * dotN * n.getDoubleX());
        double ry = (2.0f * dotN * n.getDoubleY());
        this.sub(rx, ry);
        return this;
    }


    @Override
    public Vector2d refract(IVector2 n, float eta) { // https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/refract.xhtml
        final double dotN = this.dot(n);
        final double k = 1.0f - eta * eta * (1.0f - dotN * dotN);
        if (k < 0.0) {
            this.set(0f, 0f);
        } else {
            final double sqrtK = Math.sqrt(k);
            double rx = (eta * this.x - (eta * dotN + sqrtK) * n.getDoubleX());
            double ry = (eta * this.y - (eta * dotN + sqrtK) * n.getDoubleY());
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
    public double componentSum() {
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
    public double componentMin() {
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
    public double componentMax() {
        return Math.max(x, y);
    }


    @Override
    public Vector2d clampComponents(Number min, Number max) {
        return this.clampComponents(min.doubleValue(), max.doubleValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector2d clampComponents(double min, double max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        return this;
    }


    @Override
    public boolean compare(IVector2 vec) {
        if (Math.abs(x - vec.getDoubleX()) > EPSILON) {
            return false;
        }
		return !(Math.abs(y - vec.getDoubleY()) > EPSILON);
	}


    @Override
    public boolean isUnit() {
		return !(Math.abs(length2() - 1.0) > EPSILON);
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
