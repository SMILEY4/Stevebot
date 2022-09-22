package stevebot.mod.adapter;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import stevebot.core.math.vectors.vec3.Vector3d;
import stevebot.core.minecraft.OpenGLAdapter;

public class OpenGLAdapterImpl implements OpenGLAdapter {

    private final Tessellator TESSELLATOR = Tessellator.getInstance();
    private final BufferBuilder BUFFER = TESSELLATOR.getBuffer();

    @Override
    public void prepare(final Vector3d position) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.translate(-position.x, -position.y, -position.z);
    }

    @Override
    public void reset() {
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
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
