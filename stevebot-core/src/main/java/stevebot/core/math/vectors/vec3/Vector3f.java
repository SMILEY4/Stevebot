package stevebot.core.math.vectors.vec3;


public class Vector3f implements IVector3 {


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
    public static Vector3f createVectorAB(IVector3 a, IVector3 b) {
        return Vector3f.createVectorAB(a.getFloatX(), a.getFloatY(), a.getFloatZ(), b.getFloatX(), b.getFloatY(), b.getFloatZ());
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
    public static Vector3f createVectorAB(float ax, float ay, float az, float bx, float by, float bz) {
        return new Vector3f(bx - ax, by - ay, bz - az);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector3f setVectorAB(IVector3 a, IVector3 b, Vector3f dst) {
        return Vector3f.setVectorAB(a.getFloatX(), a.getFloatY(), a.getFloatZ(), b.getFloatX(), b.getFloatY(), b.getFloatZ(), dst);
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
    public static Vector3f setVectorAB(float ax, float ay, float az, float bx, float by, float bz, Vector3f dst) {
        return dst.set(bx - ax, by - ay, bz - az);
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
     * creates a zero-vector
     */
    public Vector3f() {
        this(0);
    }


    /**
     * creates a vector with the same values for x, y and z
     */
    public Vector3f(float xyz) {
        this(xyz, xyz, xyz);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector3f(IVector3 vec) {
        this(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    /**
     * creates a vector with the given x, y and z values
     */
    public Vector3f(float x, float y, float z) {
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
        return VectorType.FLOAT;
    }


    @Override
    public Vector3f setAt(int index, Number value) {
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
            return (long) this.x;
        }
        if (index == 1) {
            return (long) this.y;
        }
        if (index == 2) {
            return (long) this.z;
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
    public Vector3f negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }


    @Override
    public Vector3f normalize() {
        final float len = length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        return this;
    }


    @Override
    public Vector3f copy() {
        return new Vector3f(this);
    }


    @Override
    public Vector3f set(IVector3 v) {
        return this.set(v.getFloatX(), v.getFloatY(), v.getFloatZ());
    }


    @Override
    public Vector3f set(Number x, Number y, Number z) {
        return this.set(x.floatValue(), y.floatValue(), z.floatValue());
    }


    @Override
    public Vector3f set(Number xyz) {
        return this.set(xyz.floatValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xyz the value of the x- y- and z-component
     * @return this vector for chaining
     */
    public Vector3f set(float xyz) {
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
    public Vector3f set(float x, float y, float z) {
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
        return new Vector3i(this);
    }


    @Override
    public Vector3l toLongVector() {
        return new Vector3l(this);
    }


    @Override
    public Vector3f toFloatVector() {
        return this.copy();
    }


    @Override
    public Vector3d toDoubleVector() {
        return new Vector3d(this);
    }


    @Override
    public String toString() {
        return "Vector3f." + this.hashCode() + "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }


    @Override
    public Vector3f add(IVector3 vec) {
        return this.add(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    @Override
    public Vector3f add(Number x, Number y, Number z) {
        return this.add(x.floatValue(), y.floatValue(), z.floatValue());
    }


    @Override
    public Vector3f add(Number xyz) {
        return this.add(xyz.floatValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3f add(float xyz) {
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
    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }


    @Override
    public Vector3f sub(IVector3 vec) {
        return this.sub(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    @Override
    public Vector3f sub(Number x, Number y, Number z) {
        return this.sub(x.floatValue(), y.floatValue(), z.floatValue());
    }


    @Override
    public Vector3f sub(Number xyz) {
        return this.sub(xyz.floatValue());
    }


    /**
     * Subtracts the given component from this vector.
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3f sub(float xyz) {
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
    public Vector3f sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }


    @Override
    public Vector3f mul(IVector3 vec) {
        return this.mul(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }

    @Override
    public Vector3f mul(Number x, Number y, Number z) {
        return this.mul(x.floatValue(), y.floatValue(), z.floatValue());
    }

    @Override
    public Vector3f scale(Number xyz) {
        return this.scale(xyz.floatValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyz the scalar
     * @return this vector for chaining
     */
    public Vector3f scale(float xyz) {
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
    public Vector3f mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }


    @Override
    public Vector3f div(IVector3 vec) {
        return this.div(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    @Override
    public Vector3f div(Number x, Number y, Number z) {
        return this.div(x.floatValue(), y.floatValue(), z.floatValue());
    }


    @Override
    public Vector3f div(Number xyz) {
        return this.div(xyz.floatValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3f div(float xyz) {
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
    public Vector3f div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }


    @Override
    public Vector3f crossSetGen(IVector3 vec) {
        return this.crossSet(vec);
    }

    @Override
    public Vector3f crossSetGen(Number x, Number y, Number z) {
        return this.crossSet(x.floatValue(), y.floatValue(), z.floatValue());
    }

    @Override
    public Vector3f crossGen(IVector3 vec) {
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
    public Vector3f crossSet(IVector3 vec) {
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
    public Vector3f crossSet(float x, float y, float z) {
        final float rx = this.y * z - this.z * y;
        final float ry = this.z * x - this.x * z;
        final float rz = this.x * y - this.y * x;
        return this.set(rx, ry, rz);
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a new vector
     */
    public Vector3f cross(IVector3 vec) {
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
    public Number dotGen(IVector3 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y, Number z) {
        return this.dot(x.floatValue(), y.floatValue(), z.floatValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dot(IVector3 vec) {
        return this.dot(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result
     */
    public float dot(float x, float y, float z) {
        return (this.x * x) + (this.y * y) + (this.z * z);
    }


    @Override
    public Number dist2Gen(IVector3 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y, Number z) {
        return this.dist2(x.floatValue(), y.floatValue(), z.floatValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist2(IVector3 vec) {
        return this.dist2(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @return the result
     */
    public float dist2(float x, float y, float z) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) + (z - this.z) * (z - this.z);
    }


    @Override
    public Number distGen(IVector3 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y, Number z) {
        return this.dist(x.floatValue(), y.floatValue(), z.floatValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public float dist(IVector3 vec) {
        return this.dist(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @return the result
     */
    public float dist(float x, float y, float z) {
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
    public float length2() {
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
    public Vector3f setLength(Number length) {
        return this.setLength(length.floatValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector3f setLength(float length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector3f limitLength(Number maxLength) {
        return this.limitLength(maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector3f limitLength(float maxLength) {
        float len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public IVector3 clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.floatValue(), maxLength.floatValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector3f clampLength(float minLength, float maxLength) {
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
        return this.angleRad(x.floatValue(), y.floatValue(), z.floatValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleRad(IVector3 vec) {
        return this.angleRad(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @return the result
     */
    public float angleRad(float x, float y, float z) {
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
        return this.angleDeg(x.floatValue(), y.floatValue(), z.floatValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public float angleDeg(IVector3 vec) {
        return this.angleDeg(vec.getFloatX(), vec.getFloatY(), vec.getFloatZ());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @return the result
     */
    public float angleDeg(float x, float y, float z) {
        return (float) Math.toDegrees(angleRad(x, y, z));
    }


    @Override
    public IVector3 project(IVector3 vec) {
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
    public Vector3f project(Vector3f vec) {
        final float dot = dot(vec);
        final float len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public IVector3 reflect(IVector3 vec) {
        // I - 2.0 * dot(N, I) * N
        final float dotN = this.dot(vec);
        float rx = (2.0f * dotN * vec.getFloatX());
        float ry = (2.0f * dotN * vec.getFloatY());
        float rz = (2.0f * dotN * vec.getFloatZ());
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
            float rx = (float) (eta * this.x - (eta * dotN + sqrtK) * vec.getFloatX());
            float ry = (float) (eta * this.y - (eta * dotN + sqrtK) * vec.getFloatY());
            float rz = (float) (eta * this.z - (eta * dotN + sqrtK) * vec.getFloatZ());
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
    public float componentSum() {
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
    public float componentMin() {
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
    public float componentMax() {
        return Math.max(Math.max(x, y), z);
    }


    @Override
    public Vector3f clampComponents(Number min, Number max) {
        return this.clampComponents(min.floatValue(), max.floatValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector3f clampComponents(float min, float max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        this.z = Math.min(max, Math.max(this.z, min));
        return this;
    }


    @Override
    public boolean compare(IVector3 vec) {
        if (Math.abs(x - vec.getFloatX()) > EPSILON) {
            return false;
        }
        if (Math.abs(y - vec.getFloatY()) > EPSILON) {
            return false;
        }
		return !(Math.abs(z - vec.getFloatZ()) > EPSILON);
	}


    @Override
    public boolean isUnit() {
		return !(Math.abs(length2() - 1f) > EPSILON);
    }


    @Override
    public boolean isZero() {
		return (Math.abs(x) < EPSILON) && (Math.abs(y) < EPSILON) && (Math.abs(z) < EPSILON);
    }


    @Override
    public boolean isPerpendicular(IVector3 vec) {
		return dot(vec) < EPSILON;
    }

}
