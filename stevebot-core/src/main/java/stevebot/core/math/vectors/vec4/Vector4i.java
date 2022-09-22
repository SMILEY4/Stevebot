package stevebot.core.math.vectors.vec4;


import stevebot.core.math.vectors.vec3.Vector3i;

public class Vector4i implements IVector4 {


    /**
     * creates a new vector from point a to point b
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the created vector
     */
    public static Vector4i createVectorAB(IVector4 a, IVector4 b) {
        return Vector4i.createVectorAB(a.getIntX(), a.getIntY(), a.getIntZ(), a.getIntW(), b.getIntX(), b.getIntY(), b.getIntZ(), b.getIntW());
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
    public static Vector4i createVectorAB(int ax, int ay, int az, int aw, int bx, int by, int bz, int bw) {
        return new Vector4i(bx - ax, by - ay, bz - az, bw - aw);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector4i setVectorAB(IVector4 a, IVector4 b, Vector4i dst) {
        return Vector4i.setVectorAB(a.getIntX(), a.getIntY(), a.getIntZ(), a.getIntW(), b.getIntX(), b.getIntY(), b.getIntZ(), b.getIntW(), dst);
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
    public static Vector4i setVectorAB(int ax, int ay, int az, int aw, int bx, int by, int bz, int bw, Vector4i dst) {
        return dst.set(bx - ax, by - ay, bz - az, bw - aw);
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
     * the z-component of this vector (index=2)
     */
    public int z;

    /**
     * the w-component of this vector (index=3)
     */
    public int w;


    /**
     * creates a zero-vector
     */
    public Vector4i() {
        this(0);
    }


    /**
     * creates a vector with the same values for x, y , z and w
     */
    public Vector4i(int xyzw) {
        this(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector4i(IVector4 vec) {
        this(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
    }


    /**
     * creates a vector with the given x, y, z and w values
     */
    public Vector4i(int x, int y, int z, int w) {
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
        return VectorType.INT;
    }


    @Override
    public Vector4i setAt(int index, Number value) {
        if (index == 0) {
            this.x = value.intValue();
            return this;
        }
        if (index == 1) {
            this.y = value.intValue();
            return this;
        }
        if (index == 2) {
            this.z = value.intValue();
            return this;
        }
        if (index == 3) {
            this.w = value.intValue();
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
        if (index == 2) {
            return this.z;
        }
        if (index == 3) {
            return this.w;
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
    public Vector4i negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        this.w = -w;
        return this;
    }


    @Override
    public Vector4i normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        this.w /= len;
        return this;
    }


    @Override
    public Vector4i copy() {
        return new Vector4i(this);
    }


    @Override
    public Vector4i set(IVector4 v) {
        return this.set(v.getIntX(), v.getIntY(), v.getIntZ(), v.getIntW());
    }


    @Override
    public Vector4i set(Number x, Number y, Number z, Number w) {
        return this.set(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    @Override
    public Vector4i set(Number xyzw) {
        return this.set(xyzw.intValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xyzw the value of the x- y- z- and w-component
     * @return this vector for chaining
     */
    public Vector4i set(int xyzw) {
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
    public Vector4i set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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
    public int getIntZ() {
        return this.z;
    }

    @Override
    public int getIntW() {
        return this.w;
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
        return this.copy();
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
        return new Vector4d(this);
    }


    @Override
    public String toString() {
        return "Vector4i." + this.hashCode() + "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }


    @Override
    public Vector4i add(IVector4 vec) {
        return this.add(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
    }


    @Override
    public Vector4i add(Number x, Number y, Number z, Number w) {
        return this.add(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    @Override
    public Vector4i add(Number xyzw) {
        return this.add(xyzw.intValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xyzw the x-, y-, z- and wcomponent
     * @return this vector for chaining
     */
    public Vector4i add(int xyzw) {
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
    public Vector4i add(int x, int y, int z, int w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }


    @Override
    public Vector4i sub(IVector4 vec) {
        return this.sub(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
    }


    @Override
    public Vector4i sub(Number x, Number y, Number z, Number w) {
        return this.sub(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    @Override
    public Vector4i sub(Number xyzw) {
        return this.sub(xyzw.intValue());
    }


    /**
     * Subtracts the given component from this vector.
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4i sub(int xyzw) {
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
    public Vector4i sub(int x, int y, int z, int w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }


    @Override
    public Vector4i mul(IVector4 vec) {
        return this.mul(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
    }

    @Override
    public Vector4i mul(Number x, Number y, Number z, Number w) {
        return this.mul(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }

    @Override
    public Vector4i scale(Number xyzw) {
        return this.scale(xyzw.intValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyzw the scalar
     * @return this vector for chaining
     */
    public Vector4i scale(int xyzw) {
        return this.mul(xyzw, xyzw, xyzw, xyzw);
    }

    /**
     * Scales this vector by the given scalar.
     *
     * @param xyzw the scalar
     * @return this vector for chaining
     */
    public Vector4i scale(float xyzw) {
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
    public Vector4i mul(int x, int y, int z, int w) {
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
    public Vector4i mul(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }


    @Override
    public Vector4i div(IVector4 vec) {
        return this.div(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
    }


    @Override
    public Vector4i div(Number x, Number y, Number z, Number w) {
        return this.div(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    @Override
    public Vector4i div(Number xyzw) {
        return this.div(xyzw.intValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4i div(int xyzw) {
        return this.div(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4i div(float xyzw) {
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
    public Vector4i div(int x, int y, int z, int w) {
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
    public Vector4i div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
    }


    @Override
    public Vector4i crossSetGen(IVector4 vec) {
        return this.crossSet(vec);
    }

    @Override
    public Vector4i crossSetGen(Number x, Number y, Number z) {
        return this.crossSet(x.intValue(), y.intValue(), z.intValue());
    }

    @Override
    public Vector3i crossGen(IVector4 vec) {
        return this.cross(vec);
    }

    @Override
    public Vector3i crossGen(Number x, Number y, Number z) {
        return this.cross(x.intValue(), y.intValue(), z.intValue());
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector4i crossSet(IVector4 vec) {
        return this.crossSet(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector4i crossSet(int x, int y, int z) {
        final int rx = this.y * z - this.z * y;
        final int ry = this.z * x - this.x * z;
        final int rz = this.x * y - this.y * x;
        final int rw = w;
        return this.set(rx, ry, rz, rw);
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a new vector
     */
    public Vector3i cross(IVector4 vec) {
        return this.cross(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result as a new vector
     */
    public Vector3i cross(int x, int y, int z) {
        return new Vector3i(
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
        return this.dot(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public int dot(IVector4 vec) {
        return this.dot(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
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
    public int dot(int x, int y, int z, int w) {
        return (this.x * x) + (this.y * y) + (this.z * z) + (this.w * w);
    }


    @Override
    public Number dist2Gen(IVector4 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y, Number z, Number w) {
        return this.dist2(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public int dist2(IVector4 vec) {
        return this.dist2(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
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
    public int dist2(int x, int y, int z, int w) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) + (z - this.z) * (z - this.z) + (w - this.w) * (w - this.w);
    }


    @Override
    public Number distGen(IVector4 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y, Number z, Number w) {
        return this.dist(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector4 vec) {
        return this.dist(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
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
    public float dist(int x, int y, int z, int w) {
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
    public int length2() {
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
    public Vector4i setLength(Number length) {
        return this.setLength(length.intValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector4i setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector4i limitLength(Number maxLength) {
        return this.limitLength(maxLength.intValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector4i limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public IVector4 clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.intValue(), maxLength.intValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector4i clampLength(float minLength, float maxLength) {
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
        return this.angleRad(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector4 vec) {
        return this.angleRad(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
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
    public float angleRad(int x, int y, int z, int w) {
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
        return this.angleDeg(x.intValue(), y.intValue(), z.intValue(), w.intValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector4 vec) {
        return this.angleDeg(vec.getIntX(), vec.getIntY(), vec.getIntZ(), vec.getIntW());
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
    public float angleDeg(int x, int y, int z, int w) {
        return (float) Math.toDegrees(angleRad(x, y, z, w));
    }


    @Override
    public IVector4 project(IVector4 vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2Gen().intValue();
        this.set(vec).scale(dot / len2);
        return this;
    }


    /**
     * Projects this vector on the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector4i project(Vector4i vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public IVector4 reflect(IVector4 vec) {
        // I - 2.0 * dot(N, I) * N
        final float dotN = this.dot(vec);
        float rx = (2.0f * dotN * vec.getIntX());
        float ry = (2.0f * dotN * vec.getIntY());
        float rz = (2.0f * dotN * vec.getIntZ());
        float rw = (2.0f * dotN * vec.getIntW());
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
            int rx = (int) (eta * this.x - (eta * dotN + sqrtK) * vec.getIntX());
            int ry = (int) (eta * this.y - (eta * dotN + sqrtK) * vec.getIntY());
            int rz = (int) (eta * this.z - (eta * dotN + sqrtK) * vec.getIntZ());
            int rw = (int) (eta * this.z - (eta * dotN + sqrtK) * vec.getIntW());
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
    public int componentSum() {
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
    public int componentMin() {
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
    public int componentMax() {
        return Math.max(Math.max(x, y), Math.max(z, w));
    }


    @Override
    public Vector4i clampComponents(Number min, Number max) {
        return this.clampComponents(min.intValue(), max.intValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector4i clampComponents(int min, int max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        this.z = Math.min(max, Math.max(this.z, min));
        this.w = Math.min(max, Math.max(this.w, min));
        return this;
    }


    @Override
    public boolean compare(IVector4 vec) {
        if (Math.abs(x - vec.getIntX()) > 0) {
            return false;
        }
        if (Math.abs(y - vec.getIntY()) > 0) {
            return false;
        }
        if (Math.abs(z - vec.getIntZ()) > 0) {
            return false;
        }
		return Math.abs(w - vec.getIntW()) <= 0;
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
