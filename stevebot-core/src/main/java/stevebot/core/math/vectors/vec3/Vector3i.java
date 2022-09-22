package stevebot.core.math.vectors.vec3;


public class Vector3i implements IVector3 {


    /**
     * creates a new vector from point a to point b
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the created vector
     */
    public static Vector3i createVectorAB(IVector3 a, IVector3 b) {
        return Vector3i.createVectorAB(a.getIntX(), a.getIntY(), a.getIntZ(), b.getIntX(), b.getIntY(), b.getIntZ());
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
    public static Vector3i createVectorAB(int ax, int ay, int az, int bx, int by, int bz) {
        return new Vector3i(bx - ax, by - ay, bz - az);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector3i setVectorAB(IVector3 a, IVector3 b, Vector3i dst) {
        return Vector3i.setVectorAB(a.getIntX(), a.getIntY(), a.getIntZ(), b.getIntX(), b.getIntY(), b.getIntZ(), dst);
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
    public static Vector3i setVectorAB(int ax, int ay, int az, int bx, int by, int bz, Vector3i dst) {
        return dst.set(bx - ax, by - ay, bz - az);
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
     * creates a zero-vector
     */
    public Vector3i() {
        this(0);
    }


    /**
     * creates a vector with the same values for x, y and z
     */
    public Vector3i(int xyz) {
        this(xyz, xyz, xyz);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector3i(IVector3 vec) {
        this(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    /**
     * creates a vector with the given x, y and z values
     */
    public Vector3i(int x, int y, int z) {
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
        return VectorType.INT;
    }


    @Override
    public Vector3i setAt(int index, Number value) {
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
    public Vector3i negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }


    @Override
    public Vector3i normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        return this;
    }


    @Override
    public Vector3i copy() {
        return new Vector3i(this);
    }


    @Override
    public Vector3i set(IVector3 v) {
        return this.set(v.getIntX(), v.getIntY(), v.getIntZ());
    }


    @Override
    public Vector3i set(Number x, Number y, Number z) {
        return this.set(x.intValue(), y.intValue(), z.intValue());
    }


    @Override
    public Vector3i set(Number xyz) {
        return this.set(xyz.intValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xyz the value of the x- y- and z-component
     * @return this vector for chaining
     */
    public Vector3i set(int xyz) {
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
    public Vector3i set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    public Vector3i toIntVector() {
        return this.copy();
    }


    @Override
    public Vector3l toLongVector() {
        return new Vector3l(this);
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
        return "Vector3i." + this.hashCode() + "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }


    @Override
    public Vector3i add(IVector3 vec) {
        return this.add(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    @Override
    public Vector3i add(Number x, Number y, Number z) {
        return this.add(x.intValue(), y.intValue(), z.intValue());
    }


    @Override
    public Vector3i add(Number xyz) {
        return this.add(xyz.intValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3i add(int xyz) {
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
    public Vector3i add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }


    @Override
    public Vector3i sub(IVector3 vec) {
        return this.sub(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    @Override
    public Vector3i sub(Number x, Number y, Number z) {
        return this.sub(x.intValue(), y.intValue(), z.intValue());
    }


    @Override
    public Vector3i sub(Number xyz) {
        return this.sub(xyz.intValue());
    }


    /**
     * Subtracts the given component from this vector.
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3i sub(int xyz) {
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
    public Vector3i sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }


    @Override
    public Vector3i mul(IVector3 vec) {
        return this.mul(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }

    @Override
    public Vector3i mul(Number x, Number y, Number z) {
        return this.mul(x.intValue(), y.intValue(), z.intValue());
    }

    @Override
    public Vector3i scale(Number xyz) {
        return this.scale(xyz.intValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyz the scalar
     * @return this vector for chaining
     */
    public Vector3i scale(int xyz) {
        return this.mul(xyz, xyz, xyz);
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyz the scalar
     * @return this vector for chaining
     */
    public Vector3i scale(float xyz) {
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
    public Vector3i mul(int x, int y, int z) {
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
    public Vector3i mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }


    @Override
    public Vector3i div(IVector3 vec) {
        return this.div(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    @Override
    public Vector3i div(Number x, Number y, Number z) {
        return this.div(x.intValue(), y.intValue(), z.intValue());
    }


    @Override
    public Vector3i div(Number xyz) {
        return this.div(xyz.intValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3i div(int xyz) {
        return this.div(xyz, xyz, xyz);
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3i div(float xyz) {
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
    public Vector3i div(int x, int y, int z) {
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
    public Vector3i div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }


    @Override
    public Vector3i crossSetGen(IVector3 vec) {
        return this.crossSet(vec);
    }

    @Override
    public Vector3i crossSetGen(Number x, Number y, Number z) {
        return this.crossSet(x.intValue(), y.intValue(), z.intValue());
    }

    @Override
    public Vector3i crossGen(IVector3 vec) {
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
    public Vector3i crossSet(IVector3 vec) {
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
    public Vector3i crossSet(int x, int y, int z) {
        final int rx = this.y * z - this.z * y;
        final int ry = this.z * x - this.x * z;
        final int rz = this.x * y - this.y * x;
        return this.set(rx, ry, rz);
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a new vector
     */
    public Vector3i cross(IVector3 vec) {
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
    public Number dotGen(IVector3 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y, Number z) {
        return this.dot(x.intValue(), y.intValue(), z.intValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public int dot(IVector3 vec) {
        return this.dot(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result
     */
    public int dot(int x, int y, int z) {
        return (this.x * x) + (this.y * y) + (this.z * z);
    }


    @Override
    public Number dist2Gen(IVector3 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y, Number z) {
        return this.dist2(x.intValue(), y.intValue(), z.intValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public int dist2(IVector3 vec) {
        return this.dist2(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @return the result
     */
    public int dist2(int x, int y, int z) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) + (z - this.z) * (z - this.z);
    }


    @Override
    public Number distGen(IVector3 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y, Number z) {
        return this.dist(x.intValue(), y.intValue(), z.intValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector3 vec) {
        return this.dist(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @return the result
     */
    public float dist(int x, int y, int z) {
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
    public int length2() {
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
    public Vector3i setLength(Number length) {
        return this.setLength(length.intValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector3i setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector3i limitLength(Number maxLength) {
        return this.limitLength(maxLength.intValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector3i limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public IVector3 clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.intValue(), maxLength.intValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector3i clampLength(float minLength, float maxLength) {
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
        return this.angleRad(x.intValue(), y.intValue(), z.intValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector3 vec) {
        return this.angleRad(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @return the result
     */
    public float angleRad(int x, int y, int z) {
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
        return this.angleDeg(x.intValue(), y.intValue(), z.intValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector3 vec) {
        return this.angleDeg(vec.getIntX(), vec.getIntY(), vec.getIntZ());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @return the result
     */
    public float angleDeg(int x, int y, int z) {
        return (float) Math.toDegrees(angleRad(x, y, z));
    }


    @Override
    public IVector3 project(IVector3 vec) {
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
    public Vector3i project(Vector3i vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public IVector3 reflect(IVector3 vec) {
        // I - 2.0 * dot(N, I) * N
        final float dotN = this.dot(vec);
        float rx = (2.0f * dotN * vec.getIntX());
        float ry = (2.0f * dotN * vec.getIntY());
        float rz = (2.0f * dotN * vec.getIntZ());
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
            float rx = (float) (eta * this.x - (eta * dotN + sqrtK) * vec.getIntX());
            float ry = (float) (eta * this.y - (eta * dotN + sqrtK) * vec.getIntY());
            float rz = (float) (eta * this.z - (eta * dotN + sqrtK) * vec.getIntZ());
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
    public int componentSum() {
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
    public int componentMin() {
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
    public int componentMax() {
        return Math.max(Math.max(x, y), z);
    }


    @Override
    public Vector3i clampComponents(Number min, Number max) {
        return this.clampComponents(min.intValue(), max.intValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector3i clampComponents(int min, int max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        this.z = Math.min(max, Math.max(this.z, min));
        return this;
    }


    @Override
    public boolean compare(IVector3 vec) {
        if (Math.abs(x - vec.getIntX()) > 0) {
            return false;
        }
        if (Math.abs(y - vec.getIntY()) > 0) {
            return false;
        }
		return Math.abs(z - vec.getIntZ()) <= 0;
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
