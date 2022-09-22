package stevebot.core.math.vectors.vec4;


import stevebot.core.math.vectors.vec3.Vector3f;

public class Vector4f implements IVector4 {


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
    public static Vector4f createVectorAB(IVector4 a, IVector4 b) {
        return Vector4f.createVectorAB(a.getFloatX(), a.getFloatY(), a.getFloatZ(), a.getFloatW(), b.getFloatX(), b.getFloatY(), b.getFloatZ(), b.getFloatW());
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
    public static Vector4f createVectorAB(float ax, float ay, float az, float aw, float bx, float by, float bz, float bw) {
        return new Vector4f(bx - ax, by - ay, bz - az, bw - aw);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector4f setVectorAB(IVector4 a, IVector4 b, Vector4f dst) {
        return Vector4f.setVectorAB(a.getFloatX(), a.getFloatY(), a.getFloatZ(), a.getFloatW(), b.getFloatX(), b.getFloatY(), b.getFloatZ(), b.getFloatW(), dst);
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
    public static Vector4f setVectorAB(float ax, float ay, float az, float aw, float bx, float by, float bz, float bw, Vector4f dst) {
        return dst.set(bx - ax, by - ay, bz - az, bw - aw);
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
     * the z-component of this vector (index=2)
     */
    public float z;

    /**
     * the w-component of this vector (index=3)
     */
    public float w;


    /**
     * creates a zero-vector
     */
    public Vector4f() {
        this(0);
    }


    /**
     * creates a vector with the same values for x, y , z and w
     */
    public Vector4f(float xyzw) {
        this(xyzw, xyzw, xyzw, xyzw);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector4f(IVector4 vec) {
        this(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
    }


    /**
     * creates a vector with the given x, y, z and w values
     */
    public Vector4f(float x, float y, float z, float w) {
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
        return VectorType.FLOAT;
    }


    @Override
    public Vector4f setAt(int index, Number value) {
        if (index == 0) {
            this.x = value.floatValue();
            return this;
        }
        if (index == 1) {
            this.y = value.floatValue();
            return this;
        }
        if (index == 2) {
            this.z = value.floatValue();
            return this;
        }
        if (index == 3) {
            this.w = value.floatValue();
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
    public Vector4f negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        this.w = -w;
        return this;
    }


    @Override
    public Vector4f normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        this.w /= len;
        return this;
    }


    @Override
    public Vector4f copy() {
        return new Vector4f(this);
    }


    @Override
    public Vector4f set(IVector4 v) {
        return this.set(v.getFloatX(), v.getFloatY(), v.getFloatZ(), v.getFloatW());
    }


    @Override
    public Vector4f set(Number x, Number y, Number z, Number w) {
        return this.set(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    @Override
    public Vector4f set(Number xyzw) {
        return this.set(xyzw.floatValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xyzw the value of the x- y- z- and w-component
     * @return this vector for chaining
     */
    public Vector4f set(float xyzw) {
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
    public Vector4f set(float x, float y, float z, float w) {
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
        return this.x;
    }

    @Override
    public float getFloatY() {
        return this.y;
    }

    @Override
    public float getFloatZ() {
        return this.z;
    }

    @Override
    public float getFloatW() {
        return this.w;
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
        return this.copy();
    }


    @Override
    public Vector4d toDoubleVector() {
        return new Vector4d(this);
    }


    @Override
    public String toString() {
        return "Vector4f." + this.hashCode() + "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
    }


    @Override
    public Vector4f add(IVector4 vec) {
        return this.add(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
    }


    @Override
    public Vector4f add(Number x, Number y, Number z, Number w) {
        return this.add(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    @Override
    public Vector4f add(Number xyzw) {
        return this.add(xyzw.floatValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xyzw the x-, y-, z- and wcomponent
     * @return this vector for chaining
     */
    public Vector4f add(float xyzw) {
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
    public Vector4f add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }


    @Override
    public Vector4f sub(IVector4 vec) {
        return this.sub(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
    }


    @Override
    public Vector4f sub(Number x, Number y, Number z, Number w) {
        return this.sub(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    @Override
    public Vector4f sub(Number xyzw) {
        return this.sub(xyzw.floatValue());
    }


    /**
     * Subtracts the given component from this vector.
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4f sub(float xyzw) {
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
    public Vector4f sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }


    @Override
    public Vector4f mul(IVector4 vec) {
        return this.mul(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
    }

    @Override
    public Vector4f mul(Number x, Number y, Number z, Number w) {
        return this.mul(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }

    @Override
    public Vector4f scale(Number xyzw) {
        return this.scale(xyzw.floatValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyzw the scalar
     * @return this vector for chaining
     */
    public Vector4f scale(float xyzw) {
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
    public Vector4f mul(float x, float y, float z, float w) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        this.w *= w;
        return this;
    }


    @Override
    public Vector4f div(IVector4 vec) {
        return this.div(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
    }


    @Override
    public Vector4f div(Number x, Number y, Number z, Number w) {
        return this.div(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    @Override
    public Vector4f div(Number xyzw) {
        return this.div(xyzw.floatValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
    public Vector4f div(float xyzw) {
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
    public Vector4f div(float x, float y, float z, float w) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        this.w /= w;
        return this;
    }


    @Override
    public Vector4f crossSetGen(IVector4 vec) {
        return this.crossSet(vec);
    }

    @Override
    public Vector4f crossSetGen(Number x, Number y, Number z) {
        return this.crossSet(x.floatValue(), y.floatValue(), z.floatValue());
    }

    @Override
    public Vector3f crossGen(IVector4 vec) {
        return this.cross(vec);
    }

    @Override
    public Vector3f crossGen(Number x, Number y, Number z) {
        return this.cross(x.floatValue(), y.floatValue(), z.floatValue());
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
    public Vector4f crossSet(IVector4 vec) {
        return this.crossSet(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector4f crossSet(float x, float y, float z) {
        final float rx = this.y * z - this.z * y;
        final float ry = this.z * x - this.x * z;
        final float rz = this.x * y - this.y * x;
        final float rw = w;
        return this.set(rx, ry, rz, rw);
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a new vector
     */
    public Vector3f cross(IVector4 vec) {
        return this.cross(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    /**
     * Calculates the cross product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result as a new vector
     */
    public Vector3f cross(float x, float y, float z) {
        return new Vector3f(
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
        return this.dot(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dot(IVector4 vec) {
        return this.dot(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
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
    public float dot(float x, float y, float z, float w) {
        return (this.x * x) + (this.y * y) + (this.z * z) + (this.w * w);
    }


    @Override
    public Number dist2Gen(IVector4 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y, Number z, Number w) {
        return this.dist2(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist2(IVector4 vec) {
        return this.dist2(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
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
    public float dist2(float x, float y, float z, float w) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) + (z - this.z) * (z - this.z) + (w - this.w) * (w - this.w);
    }


    @Override
    public Number distGen(IVector4 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y, Number z, Number w) {
        return this.dist(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector4 vec) {
        return this.dist(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
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
    public float dist(float x, float y, float z, float w) {
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
    public float length2() {
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
    public Vector4f setLength(Number length) {
        return this.setLength(length.floatValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector4f setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector4f limitLength(Number maxLength) {
        return this.limitLength(maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector4f limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public IVector4 clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.floatValue(), maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector4f clampLength(float minLength, float maxLength) {
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
        return this.angleRad(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector4 vec) {
        return this.angleRad(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
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
    public float angleRad(float x, float y, float z, float w) {
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
        return this.angleDeg(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector4 vec) {
        return this.angleDeg(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ(), vec.getFloatW());
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
    public float angleDeg(float x, float y, float z, float w) {
        return (float) Math.toDegrees(angleRad(x, y, z, w));
    }


    @Override
    public IVector4 project(IVector4 vec) {
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
    public Vector4f project(Vector4f vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public IVector4 reflect(IVector4 vec) {
        // I - 2.0 * dot(N, I) * N
        final float dotN = this.dot(vec);
        float rx = (2.0f * dotN * vec.getFloatX());
        float ry = (2.0f * dotN * vec.getFloatY());
        float rz = (2.0f * dotN * vec.getFloatZ());
        float rw = (2.0f * dotN * vec.getFloatW());
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
            float rx = (float) (eta * this.x - (eta * dotN + sqrtK) * vec.getFloatX());
            float ry = (float) (eta * this.y - (eta * dotN + sqrtK) * vec.getFloatY());
            float rz = (float) (eta * this.z - (eta * dotN + sqrtK) * vec.getFloatZ());
            float rw = (float) (eta * this.z - (eta * dotN + sqrtK) * vec.getFloatW());
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
    public float componentSum() {
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
    public float componentMin() {
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
    public float componentMax() {
        return Math.max(Math.max(x, y), Math.max(z, w));
    }


    @Override
    public Vector4f clampComponents(Number min, Number max) {
        return this.clampComponents(min.floatValue(), max.floatValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector4f clampComponents(float min, float max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        this.z = Math.min(max, Math.max(this.z, min));
        this.w = Math.min(max, Math.max(this.w, min));
        return this;
    }


    @Override
    public boolean compare(IVector4 vec) {
        if (Math.abs(x - vec.getFloatX()) > EPSILON) {
            return false;
        }
        if (Math.abs(y - vec.getFloatY()) > EPSILON) {
            return false;
        }
        if (Math.abs(z - vec.getFloatZ()) > EPSILON) {
            return false;
        }
		return !(Math.abs(w - vec.getFloatW()) > EPSILON);
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
