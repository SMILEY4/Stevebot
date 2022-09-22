package stevebot.core.math.vectors.vec4;


import stevebot.core.math.vectors.vec3.Vector3l;

public class Vector4l implements IVector4 {


    /**
     * creates a new vector from point a to point b
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the created vector
     */
    public static Vector4l createVectorAB(IVector4 a, IVector4 b) {
        return Vector4l.createVectorAB(a.getLongX(), a.getLongY(), a.getLongZ(), a.getLongW(), b.getLongX(), b.getLongY(), b.getLongZ(), b.getLongW());
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
    public static Vector4l createVectorAB(long ax, long ay, long az, long aw, long bx, long by, long bz, long bw) {
        return new Vector4l(bx - ax, by - ay, bz - az, bw - aw);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector4l setVectorAB(IVector4 a, IVector4 b, Vector4l dst) {
        return Vector4l.setVectorAB(a.getLongX(), a.getLongY(), a.getLongZ(), a.getLongW(), b.getLongX(), b.getLongY(), b.getLongZ(), b.getLongW(), dst);
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
    public static Vector4l setVectorAB(long ax, long ay, long az, long aw, long bx, long by, long bz, long bw, Vector4l dst) {
        return dst.set(bx - ax, by - ay, bz - az, bw - aw);
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
     * the z-component of this vector (index=2)
     */
    public long z;

    /**
     * the w-component of this vector (index=3)
     */
    public long w;


    /**
     * creates a zero-vector
     */
    public Vector4l() {
        this(0);
    }


    /**
     * creates a vector with the same values for x, y , z and w
     */
    public Vector4l(long xyzw) {
        this(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector4l(IVector4 vec) {
        this(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
    }


    /**
     * creates a vector with the given x, y, z and w values
     */
    public Vector4l(long x, long y, long z, long w) {
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
        return VectorType.LONG;
    }


    @Override
    public Vector4l setAt(int index, Number value) {
        if (index == 0) {
            this.x = value.longValue();
            return this;
        }
        if (index == 1) {
            this.y = value.longValue();
            return this;
        }
        if (index == 2) {
            this.z = value.longValue();
            return this;
        }
        if (index == 3) {
            this.w = value.longValue();
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
            return (double) this.x;
        }
        if (index == 1) {
            return (double) this.y;
        }
        if (index == 2) {
            return (double) this.z;
        }
        if (index == 3) {
            return (double) this.w;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public Vector4l negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        this.w = -w;
        return this;
    }


    @Override
    public Vector4l normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        this.w /= len;
        return this;
    }


    @Override
    public Vector4l copy() {
        return new Vector4l(this);
    }


    @Override
    public Vector4l set(IVector4 v) {
        return this.set(v.getLongX(), v.getLongY(), v.getLongZ(), v.getLongW());
    }


    @Override
    public Vector4l set(Number x, Number y, Number z, Number w) {
        return this.set(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    @Override
    public Vector4l set(Number xyzw) {
        return this.set(xyzw.longValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xyzw the value of the x- y- z- and w-component
     * @return this vector for chaining
     */
    public Vector4l set(long xyzw) {
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
    public Vector4l set(long x, long y, long z, long w) {
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
        return this.x;
    }

    @Override
    public long getLongY() {
        return this.y;
    }

    @Override
    public long getLongZ() {
        return this.z;
    }

    @Override
    public long getLongW() {
        return this.w;
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
        return (double) this.x;
    }

    @Override
    public double getDoubleY() {
        return (double) this.y;
    }

    @Override
    public double getDoubleZ() {
        return (double) this.z;
    }

    @Override
    public double getDoubleW() {
        return (double) this.w;
    }


    @Override
    public Vector4i toIntVector() {
        return new Vector4i(this);
    }


    @Override
    public Vector4l toLongVector() {
        return this.copy();
    }


    @Override
    public Vector4f toFloatVector() {
        return new Vector4f(this);
    }


    @Override
    public Vector4d toDoubleVector() {
        return new Vector4d(this);
    }


    @Override
    public String toString() {
        return "Vector4l." + this.hashCode() + "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }


    @Override
    public Vector4l add(IVector4 vec) {
        return this.add(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
    }


    @Override
    public Vector4l add(Number x, Number y, Number z, Number w) {
        return this.add(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    @Override
    public Vector4l add(Number xyzw) {
        return this.add(xyzw.longValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xyzw the x-, y-, z- and wcomponent
     * @return this vector for chaining
     */
    public Vector4l add(long xyzw) {
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
    public Vector4l add(long x, long y, long z, long w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }


    @Override
    public Vector4l sub(IVector4 vec) {
        return this.sub(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
    }


    @Override
    public Vector4l sub(Number x, Number y, Number z, Number w) {
        return this.sub(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    @Override
    public Vector4l sub(Number xyzw) {
        return this.sub(xyzw.longValue());
    }


    /**
     * Subtracts the given component from this vector.
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4l sub(long xyzw) {
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
    public Vector4l sub(long x, long y, long z, long w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }


    @Override
    public Vector4l mul(IVector4 vec) {
        return this.mul(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
    }

    @Override
    public Vector4l mul(Number x, Number y, Number z, Number w) {
        return this.mul(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }

    @Override
    public Vector4l scale(Number xyzw) {
        return this.scale(xyzw.longValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyzw the scalar
     * @return this vector for chaining
     */
    public Vector4l scale(long xyzw) {
        return this.mul(xyzw, xyzw, xyzw, xyzw);
    }

    /**
     * Scales this vector by the given scalar.
     *
     * @param xyzw the scalar
     * @return this vector for chaining
     */
    public Vector4l scale(float xyzw) {
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
    public Vector4l mul(long x, long y, long z, long w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
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
    public Vector4l mul(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }


    @Override
    public Vector4l div(IVector4 vec) {
        return this.div(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
    }


    @Override
    public Vector4l div(Number x, Number y, Number z, Number w) {
        return this.div(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    @Override
    public Vector4l div(Number xyzw) {
        return this.div(xyzw.longValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4l div(long xyzw) {
        return this.div(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4l div(float xyzw) {
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
    public Vector4l div(long x, long y, long z, long w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
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
    public Vector4l div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
    }


    @Override
    public Vector4l crossSetGen(IVector4 vec) {
        return this.crossSet(vec);
    }

    @Override
    public Vector4l crossSetGen(Number x, Number y, Number z) {
        return this.crossSet(x.longValue(), y.longValue(), z.longValue());
    }

    @Override
    public Vector3l crossGen(IVector4 vec) {
        return this.cross(vec);
    }

    @Override
    public Vector3l crossGen(Number x, Number y, Number z) {
        return this.cross(x.longValue(), y.longValue(), z.longValue());
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector4l crossSet(IVector4 vec) {
        return this.crossSet(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector4l crossSet(long x, long y, long z) {
        final long rx = this.y * z - this.z * y;
        final long ry = this.z * x - this.x * z;
        final long rz = this.x * y - this.y * x;
        final long rw = w;
        return this.set(rx, ry, rz, rw);
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a new vector
     */
    public Vector3l cross(IVector4 vec) {
        return this.cross(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result as a new vector
     */
    public Vector3l cross(long x, long y, long z) {
        return new Vector3l(
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
        return this.dot(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public long dot(IVector4 vec) {
        return this.dot(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
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
    public long dot(long x, long y, long z, long w) {
        return (this.x * x) + (this.y * y) + (this.z * z) + (this.w * w);
    }


    @Override
    public Number dist2Gen(IVector4 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y, Number z, Number w) {
        return this.dist2(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public long dist2(IVector4 vec) {
        return this.dist2(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
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
    public long dist2(long x, long y, long z, long w) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) + (z - this.z) * (z - this.z) + (w - this.w) * (w - this.w);
    }


    @Override
    public Number distGen(IVector4 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y, Number z, Number w) {
        return this.dist(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector4 vec) {
        return this.dist(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
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
    public float dist(long x, long y, long z, long w) {
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
    public long length2() {
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
    public float length() {
        return (float) Math.sqrt(length2());
    }


    @Override
    public Vector4l setLength(Number length) {
        return this.setLength(length.longValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector4l setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector4l limitLength(Number maxLength) {
        return this.limitLength(maxLength.longValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector4l limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public IVector4 clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.longValue(), maxLength.longValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector4l clampLength(float minLength, float maxLength) {
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
    public Number angleRadGen(IVector4 vec) {
        return this.angleRad(vec);
    }

    @Override
    public Number angleRadGen(Number x, Number y, Number z, Number w) {
        return this.angleRad(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector4 vec) {
        return this.angleRad(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
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
    public float angleRad(long x, long y, long z, long w) {
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
        return this.angleDeg(x.longValue(), y.longValue(), z.longValue(), w.longValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector4 vec) {
        return this.angleDeg(vec.getLongX(), vec.getLongY(), vec.getLongZ(), vec.getLongW());
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
    public float angleDeg(long x, long y, long z, long w) {
        return (float) Math.toDegrees(angleRad(x, y, z, w));
    }


    @Override
    public IVector4 project(IVector4 vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2Gen().longValue();
        this.set(vec).scale(dot / len2);
        return this;
    }


    /**
     * Projects this vector on the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector4l project(Vector4l vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public IVector4 reflect(IVector4 vec) {
        // I - 2.0 * dot(N, I) * N
        final float dotN = this.dot(vec);
        float rx = (2.0f * dotN * vec.getLongX());
        float ry = (2.0f * dotN * vec.getLongY());
        float rz = (2.0f * dotN * vec.getLongZ());
        float rw = (2.0f * dotN * vec.getLongW());
        this.sub(rx, ry, rz, rw);
        return this;
    }


    @Override
    public IVector4 refract(IVector4 vec, float eta) {
        final float dotN = this.dot(vec);
        final float k = 1.0f - eta * eta * (1.0f - dotN * dotN);
        if (k < 0.0) {
            this.set(0f, 0f, 0f, 0f);
        } else {
            final double sqrtK = Math.sqrt(k);
            long rx = (long) (eta * this.x - (eta * dotN + sqrtK) * vec.getLongX());
            long ry = (long) (eta * this.y - (eta * dotN + sqrtK) * vec.getLongY());
            long rz = (long) (eta * this.z - (eta * dotN + sqrtK) * vec.getLongZ());
            long rw = (long) (eta * this.z - (eta * dotN + sqrtK) * vec.getLongW());
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
    public long componentSum() {
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
    public long componentMin() {
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
    public long componentMax() {
        return Math.max(Math.max(x, y), Math.max(z, w));
    }


    @Override
    public Vector4l clampComponents(Number min, Number max) {
        return this.clampComponents(min.longValue(), max.longValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector4l clampComponents(long min, long max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        this.z = Math.min(max, Math.max(this.z, min));
        this.w = Math.min(max, Math.max(this.w, min));
        return this;
    }


    @Override
    public boolean compare(IVector4 vec) {
        if (Math.abs(x - vec.getLongX()) > 0) {
            return false;
        }
        if (Math.abs(y - vec.getLongY()) > 0) {
            return false;
        }
        if (Math.abs(z - vec.getLongZ()) > 0) {
            return false;
        }
		return Math.abs(w - vec.getLongW()) <= 0;
	}


    @Override
    public boolean isUnit() {
		return !(Math.abs(length2() - 1f) > 0);
    }


    @Override
    public boolean isZero() {
		return (Math.abs(x) < 0) && (Math.abs(y) < 0) && (Math.abs(z) < 0) && (Math.abs(w) < 0);
    }


    @Override
    public boolean isPerpendicular(IVector4 vec) {
		return dot(vec) < 0;
    }

}
