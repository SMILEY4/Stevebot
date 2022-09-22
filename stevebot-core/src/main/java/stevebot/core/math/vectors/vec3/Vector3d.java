package stevebot.core.math.vectors.vec3;


public class Vector3d implements IVector3 {


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
    public static Vector3d createVectorAB(IVector3 a, IVector3 b) {
        return Vector3d.createVectorAB(a.getDoubleX(), a.getDoubleY(), a.getDoubleZ(), b.getDoubleX(), b.getDoubleY(), b.getDoubleZ());
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
    public static Vector3d createVectorAB(double ax, double ay, double az, double bx, double by, double bz) {
        return new Vector3d(bx - ax, by - ay, bz - az);
    }


    /**
     * calculates the vector from point a to point b and stores the values in "dst"
     *
     * @param a the position of the first point
     * @param b the position of the second point
     * @return the vector "dst" with the new values
     */
    public static Vector3d setVectorAB(IVector3 a, IVector3 b, Vector3d dst) {
        return Vector3d.setVectorAB(a.getDoubleX(), a.getDoubleY(), a.getDoubleZ(), b.getDoubleX(), b.getDoubleY(), b.getDoubleZ(), dst);
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
    public static Vector3d setVectorAB(double ax, double ay, double az, double bx, double by, double bz, Vector3d dst) {
        return dst.set(bx - ax, by - ay, bz - az);
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
     * creates a zero-vector
     */
    public Vector3d() {
        this(0);
    }


    /**
     * creates a vector with the same values for x, y and z
     */
    public Vector3d(double xyz) {
        this(xyz, xyz, xyz);
    }


    /**
     * creates a vector with the same values as the given vector
     */
    public Vector3d(IVector3 vec) {
        this(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    /**
     * creates a vector with the given x, y and z values
     */
    public Vector3d(double x, double y, double z) {
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
        return VectorType.DOUBLE;
    }


    @Override
    public Vector3d setAt(int index, Number value) {
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
    public Vector3d negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }


    @Override
    public Vector3d normalize() {
        final double len = length();
        this.x /= len;
        this.y /= len;
        this.z /= len;
        return this;
    }


    @Override
    public Vector3d copy() {
        return new Vector3d(this);
    }


    @Override
    public Vector3d set(IVector3 v) {
        return this.set(v.getDoubleX(), v.getDoubleY(), v.getDoubleZ());
    }


    @Override
    public Vector3d set(Number x, Number y, Number z) {
        return this.set(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    @Override
    public Vector3d set(Number xyz) {
        return this.set(xyz.doubleValue());
    }


    /**
     * Sets the components of this vector.
     *
     * @param xyz the value of the x- y- and z-component
     * @return this vector for chaining
     */
    public Vector3d set(double xyz) {
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
    public Vector3d set(double x, double y, double z) {
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
        return new Vector3i(this);
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
        return this.copy();
    }


    @Override
    public String toString() {
        return "Vector3d." + this.hashCode() + "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }


    @Override
    public Vector3d add(IVector3 vec) {
        return this.add(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    @Override
    public Vector3d add(Number x, Number y, Number z) {
        return this.add(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    @Override
    public Vector3d add(Number xyz) {
        return this.add(xyz.doubleValue());
    }


    /**
     * Adds the given component to this vector.
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3d add(double xyz) {
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
    public Vector3d add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }


    @Override
    public Vector3d sub(IVector3 vec) {
        return this.sub(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    @Override
    public Vector3d sub(Number x, Number y, Number z) {
        return this.sub(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    @Override
    public Vector3d sub(Number xyz) {
        return this.sub(xyz.doubleValue());
    }


    /**
     * Subtracts the given component from this vector.
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3d sub(double xyz) {
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
    public Vector3d sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }


    @Override
    public Vector3d mul(IVector3 vec) {
        return this.mul(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }

    @Override
    public Vector3d mul(Number x, Number y, Number z) {
        return this.mul(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }

    @Override
    public Vector3d scale(Number xyz) {
        return this.scale(xyz.doubleValue());
    }


    /**
     * Scales this vector by the given scalar.
     *
     * @param xyz the scalar
     * @return this vector for chaining
     */
    public Vector3d scale(double xyz) {
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
    public Vector3d mul(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }


    @Override
    public Vector3d div(IVector3 vec) {
        return this.div(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    @Override
    public Vector3d div(Number x, Number y, Number z) {
        return this.div(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    @Override
    public Vector3d div(Number xyz) {
        return this.div(xyz.doubleValue());
    }


    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyz the x-, y- and z-component
     * @return this vector for chaining
     */
    public Vector3d div(double xyz) {
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
    public Vector3d div(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }


    @Override
    public Vector3d crossSetGen(IVector3 vec) {
        return this.crossSet(vec);
    }

    @Override
    public Vector3d crossSetGen(Number x, Number y, Number z) {
        return this.crossSet(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }

    @Override
    public Vector3d crossGen(IVector3 vec) {
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
    public Vector3d crossSet(IVector3 vec) {
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
    public Vector3d crossSet(double x, double y, double z) {
        final double rx = this.y * z - this.z * y;
        final double ry = this.z * x - this.x * z;
        final double rz = this.x * y - this.y * x;
        return this.set(rx, ry, rz);
    }


    /**
     * Calculates the cross product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a new vector
     */
    public Vector3d cross(IVector3 vec) {
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
    public Number dotGen(IVector3 vec) {
        return this.dot(vec);
    }


    @Override
    public Number dotGen(Number x, Number y, Number z) {
        return this.dot(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dot(IVector3 vec) {
        return this.dot(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result
     */
    public double dot(double x, double y, double z) {
        return (this.x * x) + (this.y * y) + (this.z * z);
    }


    @Override
    public Number dist2Gen(IVector3 vec) {
        return this.dist2(vec);
    }


    @Override
    public Number dist2Gen(Number x, Number y, Number z) {
        return this.dist2(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dist2(IVector3 vec) {
        return this.dist2(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @return the result
     */
    public double dist2(double x, double y, double z) {
        return (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) + (z - this.z) * (z - this.z);
    }


    @Override
    public Number distGen(IVector3 vec) {
        return this.dist(vec);
    }


    @Override
    public Number distGen(Number x, Number y, Number z) {
        return this.dist(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result
     */
    public double dist(IVector3 vec) {
        return this.dist(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @return the result
     */
    public double dist(double x, double y, double z) {
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
    public double length2() {
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
    public double length() {
        return Math.sqrt(length2());
    }


    @Override
    public Vector3d setLength(Number length) {
        return this.setLength(length.doubleValue());
    }


    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
    public Vector3d setLength(double length) {
        normalize();
        scale(length);
        return this;
    }


    @Override
    public Vector3d limitLength(Number maxLength) {
        return this.limitLength(maxLength.doubleValue());
    }


    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector3d limitLength(double maxLength) {
        double len = length();
        if (len > maxLength) {
            div(len);
            scale(maxLength);
        }
        return this;
    }


    @Override
    public IVector3 clampLength(Number minLength, Number maxLength) {
        return this.clampLength(minLength.doubleValue(), maxLength.doubleValue());
    }


    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
    public Vector3d clampLength(double minLength, double maxLength) {
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
    public Number angleRadGen(IVector3 vec) {
        return this.angleRad(vec);
    }

    @Override
    public Number angleRadGen(Number x, Number y, Number z) {
        return this.angleRad(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public double angleRad(IVector3 vec) {
        return this.angleRad(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @return the result
     */
    public double angleRad(double x, double y, double z) {
        final double lenAdd = Math.sqrt((this.x + x) * (this.x + x) + (this.y + y) * (this.y + y) + (this.z + z) * (this.z + z));
        final double lenSub = Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y) + (this.z - z) * (this.z - z));
        return (2.0 * Math.atan((lenSub) / (lenAdd)));
    }


    @Override
    public Number angleDegGen(IVector3 vec) {
        return this.angleDeg(vec);
    }


    @Override
    public Number angleDegGen(Number x, Number y, Number z) {
        return this.angleDeg(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result
     */
    public double angleDeg(IVector3 vec) {
        return this.angleDeg(vec.getDoubleX(), vec.getDoubleY(), vec.getDoubleZ());
    }


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param z the z-component of the vector
     * @return the result
     */
    public double angleDeg(double x, double y, double z) {
        return Math.toDegrees(angleRad(x, y, z));
    }


    @Override
    public IVector3 project(IVector3 vec) {
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
    public Vector3d project(Vector3d vec) {
        final double dot = dot(vec);
        final double len2 = vec.length2();
        this.set(vec).scale(dot / len2);
        return this;
    }


    @Override
    public IVector3 reflect(IVector3 vec) {
        // I - 2.0 * dot(N, I) * N
        final double dotN = this.dot(vec);
        double rx = (2.0f * dotN * vec.getDoubleX());
        double ry = (2.0f * dotN * vec.getDoubleY());
        double rz = (2.0f * dotN * vec.getDoubleZ());
        this.sub(rx, ry, rz);
        return this;
    }


    @Override
    public IVector3 refract(IVector3 vec, float eta) {
        final double dotN = this.dot(vec);
        final double k = 1.0f - eta * eta * (1.0f - dotN * dotN);
        if (k < 0.0) {
            this.set(0f, 0f, 0f);
        } else {
            final double sqrtK = Math.sqrt(k);
            double rx = (eta * this.x - (eta * dotN + sqrtK) * vec.getDoubleX());
            double ry = (eta * this.y - (eta * dotN + sqrtK) * vec.getDoubleY());
            double rz = (eta * this.z - (eta * dotN + sqrtK) * vec.getDoubleZ());
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
    public double componentSum() {
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
    public double componentMin() {
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
    public double componentMax() {
        return Math.max(Math.max(x, y), z);
    }


    @Override
    public Vector3d clampComponents(Number min, Number max) {
        return this.clampComponents(min.doubleValue(), max.doubleValue());
    }


    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result
     */
    public Vector3d clampComponents(double min, double max) {
        this.x = Math.min(max, Math.max(this.x, min));
        this.y = Math.min(max, Math.max(this.y, min));
        this.z = Math.min(max, Math.max(this.z, min));
        return this;
    }


    @Override
    public boolean compare(IVector3 vec) {
        if (Math.abs(x - vec.getDoubleX()) > EPSILON) {
            return false;
        }
        if (Math.abs(y - vec.getDoubleY()) > EPSILON) {
            return false;
        }
		return !(Math.abs(z - vec.getDoubleZ()) > EPSILON);
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
