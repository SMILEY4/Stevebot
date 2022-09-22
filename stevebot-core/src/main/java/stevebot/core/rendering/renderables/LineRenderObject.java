package stevebot.core.rendering.renderables;

import stevebot.core.math.vectors.vec3.Vector3d;
import stevebot.core.rendering.Color;
import stevebot.core.rendering.Renderable;
import stevebot.core.rendering.Renderer;

public class LineRenderObject implements Renderable {


    private final Vector3d start, end;
    private float width;
    private final Color color;


    /**
     * @param start the start position of this line
     * @param end   the end position of this line
     * @param color the color of this line
     */
    public LineRenderObject(Vector3d start, Vector3d end, Color color) {
        this(start, end, DEFAULT_LINE_WIDTH, color);
    }


    /**
     * @param start the start position of this line
     * @param end   the end position of this line
     * @param width the with of this line
     * @param color the color of this line
     */
    public LineRenderObject(Vector3d start, Vector3d end, float width, Color color) {
        this.start = start;
        this.end = end;
        this.width = width;
        this.color = color;
    }


    @Override
    public void render(Renderer renderer) {
        renderer.drawLine(getStart(), getEnd(), getWidth(), getColor());
    }


    /**
     * @return the start position of this line
     */
    public Vector3d getStart() {
        return start;
    }


    /**
     * @return the end position of this line
     */
    public Vector3d getEnd() {
        return end;
    }


    /**
     * @return the color of this line
     */
    public Color getColor() {
        return color;
    }


    /**
     * @return the with in pixels of this line
     */
    public float getWidth() {
        return width;
    }


    /**
     * @param width the new with in pixels of this line
     */
    public void setWidth(float width) {
        this.width = width;
    }


}
