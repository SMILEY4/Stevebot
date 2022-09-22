package stevebot.core.math.vectors.vec4;


import stevebot.core.math.vectors.IVector;
import stevebot.core.math.vectors.vec3.IVector3;

public interface IVector4 extends IVector {


    /**
     * Sets this vector from the given vector.
     *
     * @param v the vector
     * @return this vector for chaining
     */
	IVector4 set(IVector4 v);

    /**
     * Sets the components of this vector.
     *
     * @param x the value of the x-component
     * @param y the value of the y-component
     * @param z the value of the z-component
     * @param w the value of the w-component
     * @return this vector for chaining
     */
	IVector4 set(Number x, Number y, Number z, Number w);

    /**
     * Sets the components of this vector.
     *
     * @param xyzw the value of the x-, y-, z- and w-component
     * @return this vector for chaining
     */
	IVector4 set(Number xyzw);


    /**
     * @return the x-component as an integer
     **/
	int getIntX();

    /**
     * @return the y-component as an integer
     **/
	int getIntY();

    /**
     * @return the z-component as an integer
     **/
	int getIntZ();

    /**
     * @return the w-component as an integer
     **/
	int getIntW();

    /**
     * @return the x-component as a long
     **/
	long getLongX();

    /**
     * @return the y-component as a long
     **/
	long getLongY();

    /**
     * @return the z-component as a long
     **/
	long getLongZ();

    /**
     * @return the w-component as a long
     **/
	long getLongW();

    /**
     * @return the x-component as a float
     **/
	float getFloatX();

    /**
     * @return the y-component as a float
     **/
	float getFloatY();

    /**
     * @return the z-component as a float
     **/
	float getFloatZ();

    /**
     * @return the w-component as a float
     **/
	float getFloatW();

    /**
     * @return the x-component as a double
     **/
	double getDoubleX();

    /**
     * @return the y-component as a doubld
     **/
	double getDoubleY();

    /**
     * @return the z-component as a doubld
     **/
	double getDoubleZ();

    /**
     * @return the w-component as a doubld
     **/
	double getDoubleW();


    /**
     * Converts this vector to an integer vector.
     *
     * @return the created integer vector
     */
	Vector4i toIntVector();

    /**
     * Converts this vector to a long vector.
     *
     * @return the created long vector
     */
	Vector4l toLongVector();

    /**
     * Converts this vector to a float vector.
     *
     * @return the created float vector
     */
	Vector4f toFloatVector();

    /**
     * Converts this vector to a double vector.
     *
     * @return the created double vector
     */
	Vector4d toDoubleVector();


    /**
     * Adds the given vector to this vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
	IVector4 add(IVector4 vec);

    /**
     * Adds the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return this vector for chaining
     */
	IVector4 add(Number x, Number y, Number z, Number w);

    /**
     * Adds the given component to this vector.
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
	IVector4 add(Number xyzw);


    /**
     * Subtracts the given vector from this vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
	IVector4 sub(IVector4 vec);

    /**
     * Subtracts the given components to this vector.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return this vector for chaining
     */
	IVector4 sub(Number x, Number y, Number z, Number w);

    /**
     * Subtracts the given component to this vector.
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
	IVector4 sub(Number xyzw);


    /**
     * Multiplies this vector with the given vector (component-wise).
     *
     * @param vec the vector
     * @return this vector for chaining
     */
	IVector4 mul(IVector4 vec);

    /**
     * Multiplies this vector with the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return this vector for chaining
     */
	IVector4 mul(Number x, Number y, Number z, Number w);

    /**
     * Scales this vector by the given scalar.
     *
     * @param xyzw the scalar
     * @return this vector for chaining
     */
	IVector4 scale(Number xyzw);


    /**
     * Divides this vector by the given vector (component-wise).
     *
     * @param vec the vector
     * @return this vector for chaining
     */
	IVector4 div(IVector4 vec);

    /**
     * Divides this vector by the given components (component-wise).
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return this vector for chaining
     */
	IVector4 div(Number x, Number y, Number z, Number w);

    /**
     * Divides this vector by the given component (component-wise).
     *
     * @param xyzw the x-, y-, z- and w-component
     * @return this vector for chaining
     */
	IVector4 div(Number xyzw);


    /**
     * Calculates the cross product between this vector and the given vector. Ignores the w-component
     *
     * @param vec the vector
     * @return this vector for chaining
     */
	IVector4 crossSetGen(IVector4 vec);

    /**
     * Calculates the cross product between this vector and the given vector-components. Ignores the w-component
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return this vector for chaining
     */
	IVector4 crossSetGen(Number x, Number y, Number z);

    /**
     * Calculates the cross product between this vector and the given vector. Ignores the w-component
     *
     * @param vec the vector
     * @return the result as a new vector
     */
	IVector3 crossGen(IVector4 vec);

    /**
     * Calculates the cross product between this vector and the given vector-components. Ignores the w-component
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @return the result as a new vector
     */
	IVector3 crossGen(Number x, Number y, Number z);


    /**
     * Calculates the dot product between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a {@link java.lang.Number}
     */
	Number dotGen(IVector4 vec);

    /**
     * Calculates the dot product between this vector and the given vector-components.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return the result as a {@link java.lang.Number}
     */
	Number dotGen(Number x, Number y, Number z, Number w);


    /**
     * Calculates the squared distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a {@link java.lang.Number}
     */
	Number dist2Gen(IVector4 vec);

    /**
     * Calculates the squared distance between this vector and the given position.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return the result as a {@link java.lang.Number}
     */
	Number dist2Gen(Number x, Number y, Number z, Number w);


    /**
     * Calculates the distance between this vector and the given vector.
     *
     * @param vec the vector
     * @return the result as a {@link java.lang.Number}
     */
	Number distGen(IVector4 vec);

    /**
     * Calculates the distance between this vector and the given position.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return the result as a {@link java.lang.Number}
     */
	Number distGen(Number x, Number y, Number z, Number w);


    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result as a {@link java.lang.Number}
     */
	Number angleRadGen(IVector4 vec);

    /**
     * Calculates the angle between this vector and the given vector in radians. Both vectors should be normalized before.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return the result as a {@link java.lang.Number}
     */
	Number angleRadGen(Number x, Number y, Number z, Number w);


    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param vec the vector
     * @return the result as a {@link java.lang.Number}
     */
	Number angleDegGen(IVector4 vec);

    /**
     * Calculates the angle between this vector and the given vector in degrees. Both vectors should be normalized before.
     *
     * @param x the x-component
     * @param y the y-component
     * @param z the z-component
     * @param w the w-component
     * @return the result as a {@link java.lang.Number}
     */
	Number angleDegGen(Number x, Number y, Number z, Number w);


    /**
     * Projects this vector on the given vector.
     *
     * @param vec the vector
     * @return this vector for chaining
     */
	IVector4 project(IVector4 vec);

    /**
     * Reflects this vector at the given vector
     *
     * @param vec the (normal) vector. Should be normalized before.
     * @return this vector for chaining
     */
	IVector4 reflect(IVector4 vec);

    /**
     * Refracts this vector at the given vector
     *
     * @param vec the (normal) vector. Should be normalized before.
     * @param eta the ratio of indices of refraction
     * @return this vector for chaining
     */
	IVector4 refract(IVector4 vec, float eta);


    /**
     * Compares this vector with the given vector.
     *
     * @return true, if the two vectors have the same values
     */
	boolean compare(IVector4 vec);


    /**
     * @return true, if this vector is perpendicular with the given vector
     */
	boolean isPerpendicular(IVector4 vec);


}
