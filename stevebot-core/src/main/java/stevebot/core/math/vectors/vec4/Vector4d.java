package stevebot.core.math.vectors.vec4;


import stevebot.core.math.vectors.vec3.Vector3d;

public class Vector4d implements IVector4 {


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
    public static Vector4d createVectorAB(IVector4 a, IVector4 b) {
        return Vector4d.createVectorAB(a.getDoubleX(), a.getDoubleY(), a.getDoubleZ(), a.getDoubleW(), b.getDoubleX(), b.getDoubleY(), b.getDoubleZ(), b.getDoubleW());
    }


    /**
     * creates a new vector from point a to point b
     *
     * @param ax the x-position of the first point
     * @param ay the y-position of the first point
     * @param az the z-position of the first point
     * @param aw the w-position of the first point
     * @param bx the x-position of the second point
     * @param by the y-position of the second point
     * @param bz the z-position of the second point
     * @param bw the w-position of the second point
     * @return the created vector
     */
    public static Vector4d createVectorAB(double ax, double ay, double az, double aw, double bx, double by, double bz, double bw) {
        return new Vector4d(bx - ax, by - ay, bz - az, bw - aw);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector4d setVectorAB(IVector4 a, IVector4 b, Vector4d dst) {
        return Vector4d.setVectorAB(a.getDoubleX(), a.getDoubleY(), a.getDoubleZ(), a.getDoubleW(), b.getDoubleX(), b.getDoubleY(), b.getDoubleZ(), b.getDoubleW(), dst);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param ax the x-position of the first point
     * @param ay the y-position of the first point
     * @param az the z-position of the first point
     * @param aw the w-position of the first point
     * @param bx the x-position of the second point
     * @param by the y-position of the second point
     * @param bz the z-position of the second point
     * @param bw the w-position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector4d setVectorAB(double ax, double ay, double az, double aw, double bx, double by, double bz, double bw, Vector4d dst) {
        return dst.set(bx - ax, by - ay, bz - az, bw - aw);
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
     * the z-component of this vector (index=2)
     */
    public double z;

    /**
     * the w-component of this vector (index=3)
     */
    public double w;


    /**
     * creates a zero-vector
     */
    public Vector4d() {
        this(0);
    }


    /**
     * creates a vector with the same values for x, y , z and w
     */
    public Vector4d(double xyzw) {
        this(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector4d(IVector4 vec) {
        this(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    /**
     * creates a vector with the given x, y, z and w values
     */
    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    @Override
    public int getDimensions() {
        return 4;
    }


    @Override
    public VectorType getVectorType() {
        return VectorType.DOUBLE;
    }


    @Override
    public Vector4d setAt(int index, Number value) {
        if (index == 0) {
            this.x = value.doubleValue();
            return this;
        }
        if (index == 1) {
            this.y = value.doubleValue();
            return this;
        }
        if (index == 2) {
            this.z = value.doubleValue();
            return this;
        }
        if (index == 3) {
            this.w = value.doubleValue();
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
        if (index == 2) {
            return (int) this.z;
        }
        if (index == 3) {
            return (int) this.w;
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
        if (index == 2) {
            return (long) this.z;
        }
        if (index == 3) {
            return (long) this.w;
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
        if (index == 2) {
            return (float) this.z;
        }
        if (index == 3) {
            return (float) this.w;
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
        if (index == 2) {
            return this.z;
        }
        if (index == 3) {
            return this.w;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public Vector4d negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        this.w = -w;
        return this;
    }


    @Override
    public Vector4d normalize() {
        final double len = length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        this.w /= len;
        return this;
    }


    @Override
    public Vector4d copy() {
        return new Vector4d(this);
    }


    @Override
    public Vector4d set(IVector4 v) {
        return this.set(v.getDoubleX(), v.getDoubleY(), v.getDoubleZ(), v.getDoubleW());
    }


    @Override
    public Vector4d set(Number x, Number y, Number z, Number w) {
        return this.set(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    @Override
    public Vector4d set(Number xyzw) {
        return this.set(xyzw.doubleValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xyzw the value of the x- y- z- and w-component
     * @return this vector for chaining
     */
    public Vector4d set(double xyzw) {
        return this.set(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * Sets the components of this vector.
     *
     * @param x the value of the x-component
     * @param y the value of the y-component
     * @param z the value of the z-component
     * @param w the value of the w-component
     * @return this vector for chaining
     */
    public Vector4d set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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
    public int getIntZ() {
        return (int) this.z;
    }

    @Override
    public int getIntW() {
        return (int) this.w;
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
    public long getLongZ() {
        return (long) this.z;
    }

    @Override
    public long getLongW() {
        return (long) this.w;
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
    public float getFloatZ() {
        return (float) this.z;
    }

    @Override
    public float getFloatW() {
        return (float) this.w;
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
    public double getDoubleZ() {
        return this.z;
    }

    @Override
    public double getDoubleW() {
        return this.w;
    }


    @Override
    public Vector4i toIntVector() {
        return new Vector4i(this);
    }


    @Override
    public Vector4l toLongVector() {
        return new Vector4l(this);
    }


    @Override
    public Vector4f toFloatVector() {
        return new Vector4f(this);
    }


    @Override
    public Vector4d toDoubleVector() {
        return this.copy();
    }


    @Override
    public String toString() {
        return "Vector4d." + this.hashCode() + "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }


    @Override
    public Vector4d add(IVector4 vec) {
        return this.add(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    @Override
    public Vector4d add(Number x, Number y, Number z, Number w) {
        return this.add(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    @Override
    public Vector4d add(Number xyzw) {
        return this.add(xyzw.doubleValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xyzw the x-, y-, z- and wcomponent
     * @return this vector for chaining
     */
    public Vector4d add(double xyzw) {
        return this.add(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * Adds the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return this vector for chaining
     */
    public Vector4d add(double x, double y, double z, double w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }


    @Override
    public Vector4d sub(IVector4 vec) {
        return this.sub(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    @Override
    public Vector4d sub(Number x, Number y, Number z, Number w) {
        return this.sub(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    @Override
    public Vector4d sub(Number xyzw) {
        return this.sub(xyzw.doubleValue());
    }


    /**
     * Subtracts the given component from this vector.
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4d sub(double xyzw) {
        return this.sub(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * Subtracts the given components from this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return this vector for chaining
     */
    public Vector4d sub(double x, double y, double z, double w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }


    @Override
    public Vector4d mul(IVector4 vec) {
        return this.mul(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }

    @Override
    public Vector4d mul(Number x, Number y, Number z, Number w) {
        return this.mul(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }

    @Override
    public Vector4d scale(Number xyzw) {
        return this.scale(xyzw.doubleValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyzw the scalar
     * @return this vector for chaining
     */
    public Vector4d scale(double xyzw) {
        return this.mul(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return this vector for chaining
     */
    public Vector4d mul(double x, double y, double z, double w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }


    @Override
    public Vector4d div(IVector4 vec) {
        return this.div(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    @Override
    public Vector4d div(Number x, Number y, Number z, Number w) {
        return this.div(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    @Override
    public Vector4d div(Number xyzw) {
        return this.div(xyzw.doubleValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4d div(double xyzw) {
        return this.div(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return this vector for chaining
     */
    public Vector4d div(double x, double y, double z, double w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
    }


    @Override
    public Vector4d crossSetGen(IVector4 vec) {
        return this.crossSet(vec);
    }

    @Override
    public Vector4d crossSetGen(Number x, Number y, Number z) {
        return this.crossSet(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }

    @Override
    public Vector3d crossGen(IVector4 vec) {
        return this.cross(vec);
    }

    @Override
    public Vector3d crossGen(Number x, Number y, Number z) {
        return this.cross(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector4d crossSet(IVector4 vec) {
        return this.crossSet(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector4d crossSet(double x, double y, double z) {
        final double rx = this.y * z - this.z * y;
        final double ry = this.z * x - this.x * z;
        final double rz = this.x * y - this.y * x;
        final double rw = w;
        return this.set(rx, ry, rz, rw);
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a new vector
     */
    public Vector3d cross(IVector4 vec) {
        return this.cross(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result as a new vector
     */
    public Vector3d cross(double x, double y, double z) {
        return new Vector3d(
                this.y * z - this.z * y,
                this.z * x - this.x * z,
                this.x * y - this.y * x);
    }


    @Override
    public Number dotGen(IVector4 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y, Number z, Number w) {
        return this.dot(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dot(IVector4 vec) {
        return this.dot(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return the result
     */
    public double dot(double x, double y, double z, double w) {
        return (this.x * x) + (this.y * y) + (this.z * z) + (this.w * w);
    }


    @Override
    public Number dist2Gen(IVector4 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y, Number z, Number w) {
        return this.dist2(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dist2(IVector4 vec) {
        return this.dist2(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @param w the w-position
     * @return the result
     */
    public double dist2(double x, double y, double z, double w) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) + (z - this.z) * (z - this.z) + (w - this.w) * (w - this.w);
    }


    @Override
    public Number distGen(IVector4 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y, Number z, Number w) {
        return this.dist(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dist(IVector4 vec) {
        return this.dist(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @param w the w-position
     * @return the result
     */
    public double dist(double x, double y, double z, double w) {
        return (float) Math.sqrt(this.dist2(x, y, z, w));
    }


    @Override
    public Number length2Gen() {
        return this.length2();
    }


    /**
     * Calculates the squared length of this vector.
     *
     * @return the result
     */
    public double length2() {
        return x * x + y * y + z * z + w * w;
    }


    @Override
    public Number lengthGen() {
        return this.length();
    }


    /**
     * Calculates the length of this vector.
     *
     * @return the result
     */
    public double length() {
        return (float) Math.sqrt(length2());
    }


    @Override
    public Vector4d setLength(Number length) {
        return this.setLength(length.doubleValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector4d setLength(double length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector4d limitLength(Number maxLength) {
        return this.limitLength(maxLength.doubleValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector4d limitLength(double maxLength) {
        double len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public IVector4 clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.doubleValue(), maxLength.doubleValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector4d clampLength(double minLength, double maxLength) {
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
    public Number angleRadGen(IVector4 vec) {
        return this.angleRad(vec);
    }

    @Override
    public Number angleRadGen(Number x, Number y, Number z, Number w) {
        return this.angleRad(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public double angleRad(IVector4 vec) {
        return this.angleRad(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @param w the w-component of the vector
     * @return the result
     */
    public double angleRad(double x, double y, double z, double w) {
        final double lenAdd = Math.sqrt((this.x + x) * (this.x + x) + (this.y + y) * (this.y + y) + (this.z + z) * (this.z + z) + (this.w + w) * (this.w + w));
        final double lenSub = Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y) + (this.z - z) * (this.z - z) + (this.w - w) * (this.w - w));
        return (float) (2.0 * Math.atan((lenSub) / (lenAdd)));
    }


    @Override
    public Number angleDegGen(IVector4 vec) {
        return this.angleDeg(vec);
    }


    @Override
    public Number angleDegGen(Number x, Number y, Number z, Number w) {
        return this.angleDeg(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.doubleValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public double angleDeg(IVector4 vec) {
        return this.angleDeg(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ(), vec.getDoubleW());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @param w the w-component of the vector
     * @return the result
     */
    public double angleDeg(double x, double y, double z, double w) {
        return (float) Math.toDegrees(angleRad(x, y, z, w));
    }


    @Override
    public IVector4 project(IVector4 vec) {
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
    public Vector4d project(Vector4d vec) {
        final double dot = dot(vec);
        final double len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public IVector4 reflect(IVector4 vec) {
        // I - 2.0 * dot(N, I) * N
        final double dotN = this.dot(vec);
        double rx = (2.0f * dotN * vec.getDoubleX());
        double ry = (2.0f * dotN * vec.getDoubleY());
        double rz = (2.0f * dotN * vec.getDoubleZ());
        double rw = (2.0f * dotN * vec.getDoubleW());
        this.sub(rx, ry, rz, rw);
        return this;
    }


    @Override
    public IVector4 refract(IVector4 vec, float eta) {
        final double dotN = this.dot(vec);
        final double k = 1.0f - eta * eta * (1.0f - dotN * dotN);
        if (k < 0.0) {
            this.set(0f, 0f, 0f, 0f);
        } else {
            final double sqrtK = Math.sqrt(k);
            double rx = (eta * this.x - (eta * dotN + sqrtK) * vec.getDoubleX());
            double ry = (eta * this.y - (eta * dotN + sqrtK) * vec.getDoubleY());
            double rz = (eta * this.z - (eta * dotN + sqrtK) * vec.getDoubleZ());
            double rw = (eta * this.z - (eta * dotN + sqrtK) * vec.getDoubleW());
            this.set(rx, ry, rz, rw);
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
        return x + y + z + w;
    }


    @Override
    public Number componentMinGen() {
        return componentMin();
    }


    /**
     * Calculates the smallest component.
     *
     * @return the result
     */
    public double componentMin() {
        return Math.min(Math.min(x, y), Math.min(z, w));
    }


    @Override
    public Number componentMaxGen() {
        return componentMax();
    }


    /**
     * Calculates the largest component.
     *
     * @return the result
     */
    public double componentMax() {
        return Math.max(Math.max(x, y), Math.max(z, w));
    }


    @Override
    public Vector4d clampComponents(Number min, Number max) {
        return this.clampComponents(min.doubleValue(), max.doubleValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector4d clampComponents(double min, double max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        this.z = Math.min(max, Math.max(this.z, min));
        this.w = Math.min(max, Math.max(this.w, min));
        return this;
    }


    @Override
    public boolean compare(IVector4 vec) {
        if (Math.abs(x - vec.getDoubleX()) > EPSILON) {
            return false;
        }
        if (Math.abs(y - vec.getDoubleY()) > EPSILON) {
            return false;
        }
        if (Math.abs(z - vec.getDoubleZ()) > EPSILON) {
            return false;
        }
		return !(Math.abs(w - vec.getDoubleW()) > EPSILON);
	}


    @Override
    public boolean isUnit() {
		return !(Math.abs(length2() - 1f) > EPSILON);
    }


    @Override
    public boolean isZero() {
		return (Math.abs(x) < EPSILON) && (Math.abs(y) < EPSILON) && (Math.abs(z) < EPSILON) && (Math.abs(w) < EPSILON);
    }


    @Override
    public boolean isPerpendicular(IVector4 vec) {
		return dot(vec) < EPSILON;
    }

}
