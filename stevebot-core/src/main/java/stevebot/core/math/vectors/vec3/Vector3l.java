package stevebot.core.math.vectors.vec3;


public class Vector3l implements IVector3 {


    /**
     * creates a new vector from point a to point b
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the created vector
     */
    public static Vector3l createVectorAB(IVector3 a, IVector3 b) {
        return Vector3l.createVectorAB(a.getLongX(), a.getLongY(), a.getLongZ(), b.getLongX(), b.getLongY(), b.getLongZ());
    }


    /**
     * creates a new vector from point a to point b
     *
     * @param ax the x-position of the first point
     * @param ay the y-position of the first point
     * @param az the z-position of the first point
     * @param bx the x-position of the second point
     * @param by the y-position of the second point
     * @param bz the z-position of the second point
     * @return the created vector
     */
    public static Vector3l createVectorAB(long ax, long ay, long az, long bx, long by, long bz) {
        return new Vector3l(bx - ax, by - ay, bz - az);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector3l setVectorAB(IVector3 a, IVector3 b, Vector3l dst) {
        return Vector3l.setVectorAB(a.getLongX(), a.getLongY(), a.getLongZ(), b.getLongX(), b.getLongY(), b.getLongZ(), dst);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param ax the x-position of the first point
     * @param ay the y-position of the first point
     * @param az the z-position of the first point
     * @param bx the x-position of the second point
     * @param by the y-position of the second point
     * @param bz the z-position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector3l setVectorAB(long ax, long ay, long az, long bx, long by, long bz, Vector3l dst) {
        return dst.set(bx - ax, by - ay, bz - az);
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
     * creates a zero-vector
     */
    public Vector3l() {
        this(0);
    }


    /**
     * creates a vector with the same values for x, y and z
     */
    public Vector3l(long xyz) {
        this(xyz, xyz, xyz);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector3l(IVector3 vec) {
        this(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    /**
     * creates a vector with the given x, y and z values
     */
    public Vector3l(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public int getDimensions() {
        return 3;
    }


    @Override
    public VectorType getVectorType() {
        return VectorType.LONG;
    }


    @Override
    public Vector3l setAt(int index, Number value) {
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
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getDimensions());
    }


    @Override
    public Vector3l negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }


    @Override
    public Vector3l normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        return this;
    }


    @Override
    public Vector3l copy() {
        return new Vector3l(this);
    }


    @Override
    public Vector3l set(IVector3 v) {
        return this.set(v.getLongX(), v.getLongY(), v.getLongZ());
    }


    @Override
    public Vector3l set(Number x, Number y, Number z) {
        return this.set(x.longValue(), y.longValue(), z.longValue());
    }


    @Override
    public Vector3l set(Number xyz) {
        return this.set(xyz.longValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xyz the value of the x- y- and z-component
     * @return this vector for chaining
     */
    public Vector3l set(long xyz) {
        return this.set(xyz, xyz, xyz);
    }


    /**
     * Sets the components of this vector.
     *
     * @param x the value of the x-component
     * @param y the value of the y-component
     * @param z the value of the z-component
     * @return this vector for chaining
     */
    public Vector3l set(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    public Vector3i toIntVector() {
        return new Vector3i(this);
    }


    @Override
    public Vector3l toLongVector() {
        return this.copy();
    }


    @Override
    public Vector3f toFloatVector() {
        return new Vector3f(this);
    }


    @Override
    public Vector3d toDoubleVector() {
        return new Vector3d(this);
    }


    @Override
    public String toString() {
        return "Vector3l." + this.hashCode() + "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }


    @Override
    public Vector3l add(IVector3 vec) {
        return this.add(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    @Override
    public Vector3l add(Number x, Number y, Number z) {
        return this.add(x.longValue(), y.longValue(), z.longValue());
    }


    @Override
    public Vector3l add(Number xyz) {
        return this.add(xyz.longValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3l add(long xyz) {
        return this.add(xyz, xyz, xyz);
    }


    /**
     * Adds the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector3l add(long x, long y, long z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }


    @Override
    public Vector3l sub(IVector3 vec) {
        return this.sub(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    @Override
    public Vector3l sub(Number x, Number y, Number z) {
        return this.sub(x.longValue(), y.longValue(), z.longValue());
    }


    @Override
    public Vector3l sub(Number xyz) {
        return this.sub(xyz.longValue());
    }


    /**
     * Subtracts the given component from this vector.
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3l sub(long xyz) {
        return this.sub(xyz, xyz, xyz);
    }


    /**
     * Subtracts the given components from this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector3l sub(long x, long y, long z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }


    @Override
    public Vector3l mul(IVector3 vec) {
        return this.mul(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    @Override
    public Vector3l mul(Number x, Number y, Number z) {
        return this.mul(x.longValue(), y.longValue(), z.longValue());
    }


    @Override
    public Vector3l scale(Number xyz) {
        return this.scale(xyz.floatValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyz the scalar
     * @return this vector for chaining
     */
    public Vector3l scale(long xyz) {
        return this.mul(xyz, xyz, xyz);
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyz the scalar
     * @return this vector for chaining
     */
    public Vector3l scale(float xyz) {
        return this.mul(xyz, xyz, xyz);
    }


    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector3l mul(long x, long y, long z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }


    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector3l mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }


    @Override
    public Vector3l div(IVector3 vec) {
        return this.div(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    @Override
    public Vector3l div(Number x, Number y, Number z) {
        return this.div(x.longValue(), y.longValue(), z.longValue());
    }


    @Override
    public Vector3l div(Number xyz) {
        return this.div(xyz.longValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3l div(long xyz) {
        return this.div(xyz, xyz, xyz);
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3l div(float xyz) {
        return this.div(xyz, xyz, xyz);
    }


    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector3l div(long x, long y, long z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }


    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
    public Vector3l div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }


    @Override
    public Vector3l crossSetGen(IVector3 vec) {
        return this.crossSet(vec);
    }

    @Override
    public Vector3l crossSetGen(Number x, Number y, Number z) {
        return this.crossSet(x.longValue(), y.longValue(), z.longValue());
    }

    @Override
    public Vector3l crossGen(IVector3 vec) {
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
    public Vector3l crossSet(IVector3 vec) {
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
    public Vector3l crossSet(long x, long y, long z) {
        final long rx = this.y * z - this.z * y;
        final long ry = this.z * x - this.x * z;
        final long rz = this.x * y - this.y * x;
        return this.set(rx, ry, rz);
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a new vector
     */
    public Vector3l cross(IVector3 vec) {
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
    public Number dotGen(IVector3 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y, Number z) {
        return this.dot(x.longValue(), y.longValue(), z.longValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public long dot(IVector3 vec) {
        return this.dot(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result
     */
    public long dot(long x, long y, long z) {
        return (this.x * x) + (this.y * y) + (this.z * z);
    }


    @Override
    public Number dist2Gen(IVector3 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y, Number z) {
        return this.dist2(x.longValue(), y.longValue(), z.longValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public long dist2(IVector3 vec) {
        return this.dist2(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @return the result
     */
    public long dist2(long x, long y, long z) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) + (z - this.z) * (z - this.z);
    }


    @Override
    public Number distGen(IVector3 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y, Number z) {
        return this.dist(x.longValue(), y.longValue(), z.longValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector3 vec) {
        return this.dist(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @return the result
     */
    public float dist(long x, long y, long z) {
        return (float) Math.sqrt(this.dist2(x, y, z));
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
        return x * x + y * y + z * z;
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
    public Vector3l setLength(Number length) {
        return this.setLength(length.longValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector3l setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector3l limitLength(Number maxLength) {
        return this.limitLength(maxLength.longValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector3l limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public IVector3 clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.longValue(), maxLength.longValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector3l clampLength(float minLength, float maxLength) {
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
    public Number angleRadGen(IVector3 vec) {
        return this.angleRad(vec);
    }

    @Override
    public Number angleRadGen(Number x, Number y, Number z) {
        return this.angleRad(x.longValue(), y.longValue(), z.longValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector3 vec) {
        return this.angleRad(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @return the result
     */
    public float angleRad(long x, long y, long z) {
        final double lenAdd = Math.sqrt((this.x + x) * (this.x + x) + (this.y + y) * (this.y + y) + (this.z + z) * (this.z + z));
        final double lenSub = Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y) + (this.z - z) * (this.z - z));
        return (float) (2.0 * Math.atan((lenSub) / (lenAdd)));
    }


    @Override
    public Number angleDegGen(IVector3 vec) {
        return this.angleDeg(vec);
    }


    @Override
    public Number angleDegGen(Number x, Number y, Number z) {
        return this.angleDeg(x.longValue(), y.longValue(), z.longValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector3 vec) {
        return this.angleDeg(vec.getLongX(), vec.getLongY(), vec.getLongZ());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @return the result
     */
    public float angleDeg(long x, long y, long z) {
        return (float) Math.toDegrees(angleRad(x, y, z));
    }


    @Override
    public IVector3 project(IVector3 vec) {
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
    public Vector3l project(Vector3l vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public IVector3 reflect(IVector3 vec) {
        // I - 2.0 * dot(N, I) * N
        final float dotN = this.dot(vec);
        float rx = (2.0f * dotN * vec.getLongX());
        float ry = (2.0f * dotN * vec.getLongY());
        float rz = (2.0f * dotN * vec.getLongZ());
        this.sub(rx, ry, rz);
        return this;
    }


    @Override
    public IVector3 refract(IVector3 vec, float eta) {
        final float dotN = this.dot(vec);
        final float k = 1.0f - eta * eta * (1.0f - dotN * dotN);
        if (k < 0.0) {
            this.set(0f, 0f, 0f);
        } else {
            final double sqrtK = Math.sqrt(k);
            float rx = (float) (eta * this.x - (eta * dotN + sqrtK) * vec.getLongX());
            float ry = (float) (eta * this.y - (eta * dotN + sqrtK) * vec.getLongY());
            float rz = (float) (eta * this.z - (eta * dotN + sqrtK) * vec.getLongZ());
            this.set(rx, ry, rz);
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
        return x + y + z;
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
        return Math.min(Math.min(x, y), z);
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
        return Math.max(Math.max(x, y), z);
    }


    @Override
    public Vector3l clampComponents(Number min, Number max) {
        return this.clampComponents(min.longValue(), max.longValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector3l clampComponents(long min, long max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        this.z = Math.min(max, Math.max(this.z, min));
        return this;
    }


    @Override
    public boolean compare(IVector3 vec) {
        if (Math.abs(x - vec.getLongX()) > 0) {
            return false;
        }
        if (Math.abs(y - vec.getLongY()) > 0) {
            return false;
        }
		return Math.abs(z - vec.getLongZ()) <= 0;
	}


    @Override
    public boolean isUnit() {
		return !(Math.abs(length2() - 1f) > 0);
    }


    @Override
    public boolean isZero() {
		return (Math.abs(x) < 0) && (Math.abs(y) < 0) && (Math.abs(z) < 0);
    }


    @Override
    public boolean isPerpendicular(IVector3 vec) {
		return dot(vec) < 0;
    }

}
