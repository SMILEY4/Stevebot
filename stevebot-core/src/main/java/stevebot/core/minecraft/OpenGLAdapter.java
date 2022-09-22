package stevebot.core.minecraft;

import stevebot.core.math.vectors.vec3.Vector3d;

public interface OpenGLAdapter {

    /**
     * Prepare the opengl state for drawing shapes.
     *
     * @param position the current position of the player/camera
     */
    void prepare(Vector3d position);

    /**
     * Resets the opengl state to the default state.
     */
    void reset();

    /**
     * Start drawing lines.
     *
     * @param lineWidth the with of the lines in pixels.
     */
    void beginLines(float lineWidth);


    /**
     * Start drawing line-strips.
     *
     * @param lineWidth the with of the line-strips in pixels.
     */
    void beginLineStrip(float lineWidth);

    /**
     * Start drawing boxes.
     *
     * @param lineWidth the with of the outline in pixels.
     */
    void beginBoxes(float lineWidth);

    /**
     * Start drawing points.
     *
     * @param pointSize the size of the points in pixels.
     */
    void beginPoints(float pointSize);

    /**
     * Stop drawing and flush.
     */
    void end();

    /**
     * @param x     the x-position of the vertex
     * @param y     the y-position of the vertex
     * @param z     the z-position of the vertex
     * @param red   the red-component of the color of the vertex
     * @param green the green-component of the color of the vertex
     * @param blue  the blue-component of the color of the vertex
     * @param alpha the alpha-component of the color of the vertex
     */
    void addVertex(double x, double y, double z, float red, float green, float blue, float alpha);

}
