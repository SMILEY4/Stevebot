package stevebot.minecraft;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import stevebot.math.vectors.vec3.Vector3d;

public class OpenGLAdapterImpl implements OpenGLAdapter {

    private final Tessellator TESSELLATOR = Tessellator.getInstance();
    private final BufferBuilder BUFFER = TESSELLATOR.getBuffer();

    @Override
    public void prepare(final Vector3d position) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void beginLines(final float lineWidth) {
        GlStateManager.glLineWidth(lineWidth);
        BUFFER.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
    }

    @Override
    public void beginLineStrip(final float lineWidth) {
        GlStateManager.glLineWidth(lineWidth);
        BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
    }

    @Override
    public void beginBoxes(final float lineWidth) {
        GlStateManager.glLineWidth(lineWidth);
        BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
    }

    @Override
    public void beginPoints(final float pointSize) {
        GL11.glPointSize(pointSize);
        BUFFER.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
    }

    @Override
    public void end() {
        TESSELLATOR.draw();
    }

    @Override
    public void addVertex(final double x, final double y, final double z, final float red, final float green, final float blue, final float alpha) {
        BUFFER.pos(x, y, z).color(red, green, blue, alpha).endVertex();
    }

}
