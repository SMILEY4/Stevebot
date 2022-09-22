package stevebot.core.math.vectors;

public interface IVector {

    enum VectorType {
        FLOAT,
        DOUBLE,
        INT,
        LONG
    }


    /**
     * @return the number of dimensions of this vector
     */
	int getDimensions();


    /**
     * @return the type of this vector
     */
	VectorType getVectorType();


    /**
     * Sets the value at the given index of the this vector.
     *
     * @return this vector for chaining
     */
	IVector setAt(int index, Number value);

    /**
     * @return the value of the component at the given index as an integer
     */
	int getInt(int index);

    /**
     * @return the value of the component at the given index as a long
     */
	long getLong(int index);

    /**
     * @return the value of the component at the given index as a float
     */
	float getFloat(int index);

    /**
     * @return the value of the component at the given index as a double
     */
	double getDouble(int index);


    /**
     * Negates all components of this vector.
     *
     * @return this vector for chaining
     */
	IVector negate();

    /**
     * Normalizes this vector.
     *
     * @return this vector for chaining
     */
	IVector normalize();


    /**
     * Creates a new vector with the same values as this vector.
     *
     * @return the created vector
     */
	IVector copy();


    /**
     * Calculates the squared length of this vector.
     *
     * @return the result as a {@link java.lang.Number}
     */
	Number length2Gen();

    /**
     * Calculates the length of this vector.
     *
     * @return the result as a {@link java.lang.Number}
     */
	Number lengthGen();

    /**
     * Sets the length of this vector.
     *
     * @param length the new length
     * @return this vector for chaining
     */
	IVector setLength(Number length);

    /**
     * Limits the length of this vector to the given maximum length.
     *
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
	IVector limitLength(Number maxLength);

    /**
     * Limits the length of this vector to the given minimum and maximum length.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     * @return this vector for chaining
     */
	IVector clampLength(Number minLength, Number maxLength);


    /**
     * Calculates the sum of the components.
     *
     * @return the result as a {@link java.lang.Number}
     */
	Number componentSumGen();

    /**
     * Calculates the smallest component.
     *
     * @return the result as a {@link java.lang.Number}
     */
	Number componentMinGen();

    /**
     * Calculates the largest component.
     *
     * @return the result as a {@link java.lang.Number}
     */
	Number componentMaxGen();

    /**
     * Clamps the components between the given minimum and maximum values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the result as a {@link java.lang.Number}
     */
	IVector clampComponents(Number min, Number max);


    /**
     * @return true, if this vector has a length of 1
     */
	boolean isUnit();


    /**
     * @return true, if all components of this vector are zero
     */
	boolean isZero();


}

