package stevebot.core.rendering.renderables;

import stevebot.core.math.vectors.vec3.Vector3d;
import stevebot.core.rendering.Color;
import stevebot.core.rendering.Renderable;
import stevebot.core.rendering.Renderer;

public class PointRenderObject implements Renderable {


    private final Vector3d pos;
    private float size;
    private final Color color;


    /**
     * @param pos   the position of the point
     * @param color the color of the point
     */
    public PointRenderObject(Vector3d pos, Color color) {
        this(pos, DEFAULT_POINT_SIZE, color);
    }


    /**
     * @param pos   the position of the point
     * @param size  the size of this point in pixels
     * @param color the color of the point
     */
    public PointRenderObject(Vector3d pos, float size, Color color) {
        this.pos = pos;
        this.size = size;
        this.color = color;
    }


    @Override
    public void render(Renderer renderer) {
        renderer.drawPoint(pos, size, color);
    }


    /**
     * @return the position of this point
     */
    public Vector3d getPos() {
        return pos;
    }


    /**
     * @return the color of this point
     */
    public Color getColor() {
        return color;
    }


    /**
     * @return the size in pixels of this point
     */
    public float getSize() {
        return size;
    }


    /**
     * @param size the new size in pixels of this point
     */
    public void setSize(float size) {
        this.size = size;
    }


}
