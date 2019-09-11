package stevebot.rendering;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import stevebot.events.GameRenderListener;
import stevebot.events.ModEventHandler;

import java.util.ArrayList;
import java.util.List;

public class RendererImpl implements Renderer {


	private final Tessellator TESSELLATOR = Tessellator.getInstance();
	private final BufferBuilder BUFFER = TESSELLATOR.getBuffer();
	private final List<Renderable> renderables = new ArrayList<>();




	public RendererImpl(ModEventHandler eventHandler) {
		eventHandler.addListener(new GameRenderListener() {
			@Override
			public void onRenderWorldLast(RenderWorldLastEvent event) {
				EntityPlayerSP player = Minecraft.getMinecraft().player;
				if (player != null) {

					// setup
					Vec3d playerPos = player.getPositionVector();
					setup(playerPos);

					// draw
					for (int i = 0, n = renderables.size(); i < n; i++) {
						Renderable renderable = renderables.get(i);
						renderable.render(RendererImpl.this);
					}

					// reset
					reset();
				}
			}
		});
	}




	private void setup(Vec3d playerPos) {
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.depthMask(false);
		GlStateManager.disableDepth();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.translate(-playerPos.x, -playerPos.y, -playerPos.z);
	}




	private void reset() {
		GlStateManager.glLineWidth(1.0f);
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}




	private void addVertex(Vector3d pos, Color color) {
		addVertex(pos.x, pos.y, pos.z, color.x, color.y, color.z, 1f);
	}




	private void addVertex(Vector3d pos, Color color, float alpha) {
		addVertex(pos.x, pos.y, pos.z, color.x, color.y, color.z, alpha);

	}




	private void addVertex(double x, double y, double z, Color color) {
		addVertex(x, y, z, color.x, color.y, color.z, 1f);
	}




	private void addVertex(double x, double y, double z, Color color, float alpha) {
		addVertex(x, y, z, color.x, color.y, color.z, alpha);
	}




	private void addVertex(double x, double y, double z, float red, float green, float blue) {
		addVertex(x, y, z, red, green, blue, 1f);
	}




	private void addVertex(double x, double y, double z, float red, float green, float blue, float alpha) {
		BUFFER.pos(x, y, z).color(red, green, blue, alpha).endVertex();
	}




	@Override
	public void addRenderable(Renderable renderable) {
		this.renderables.add(renderable);
	}




	@Override
	public void removeRenderable(Renderable renderable) {
		this.renderables.remove(renderable);
	}




	@Override
	public void clearRenderables() {
		this.renderables.clear();
	}




	@Override
	public void beginLines(float width) {
		GlStateManager.glLineWidth(width);
		BUFFER.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
	}




	@Override
	public void beginLineStrip(float width) {
		GlStateManager.glLineWidth(width);
		BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	}




	@Override
	public void beginBoxes(float width) {
		GlStateManager.glLineWidth(width);
		BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	}




	@Override
	public void beginPoints(float size) {
		GL11.glPointSize(size);
		BUFFER.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
	}




	@Override
	public void end() {
		TESSELLATOR.draw();
	}




	@Override
	public void drawLine(BlockPos start, BlockPos end, float width, Color color) {
		drawLine(
				new Vector3d(start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5),
				new Vector3d(end.getX() + 0.5, end.getY() + 0.5, end.getZ() + 0.5),
				width,
				color);
	}




	@Override
	public void drawLine(Vector3d start, Vector3d end, float width, Color color) {
		beginLines(width);
		drawLineOpen(start, end, color);
		end();
	}




	@Override
	public void drawLineOpen(Vector3d start, Vector3d end, Color color) {
		addVertex(start, color);
		addVertex(end, color);
	}




	@Override
	public void drawBox(BlockPos pos, float width, Color color) {
		drawBox(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), width, color);
	}




	@Override
	public void drawBoxOpen(BlockPos pos, Color color) {
		drawBoxOpen(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), color);
	}




	@Override
	public void drawBox(Vector3d pos, float width, Color color) {
		beginLineStrip(width);
		drawBoxOpen(pos, color);
		end();
	}




	@Override
	public void drawBoxOpen(Vector3d pos, Color color) {
		drawBoxOpen(pos, new Vector3d(pos).add(1), color);
	}




	@Override
	public void drawBoxOpen(Vector3d posMin, Vector3d posMax, Color color) {

		final double minX = posMin.x;
		final double maxX = posMax.x;
		final double minY = posMin.y;
		final double maxY = posMax.y;
		final double minZ = posMin.z;
		final double maxZ = posMax.z;

		addVertex(maxX, minY, maxZ, color, 0f);

		addVertex(maxX, minY, maxZ, color, 1f);
		addVertex(minX, minY, maxZ, color, 1f);

		addVertex(maxX, maxY, minZ, color, 0f);

		addVertex(maxX, maxY, minZ, color, 1f);
		addVertex(minX, maxY, minZ, color, 1f);
		addVertex(minX, minY, minZ, color, 1f);
		addVertex(maxX, minY, minZ, color, 1f);
		addVertex(maxX, maxY, minZ, color, 1f);
		addVertex(maxX, maxY, maxZ, color, 1f);
		addVertex(maxX, minY, maxZ, color, 1f);
		addVertex(maxX, minY, minZ, color, 1f);

		addVertex(minX, maxY, maxZ, color, 0f);

		addVertex(minX, maxY, maxZ, color, 1f);
		addVertex(maxX, maxY, maxZ, color, 1f);
		addVertex(maxX, maxY, minZ, color, 1f);
		addVertex(minX, maxY, minZ, color, 1f);
		addVertex(minX, maxY, maxZ, color, 1f);
		addVertex(minX, minY, maxZ, color, 1f);
		addVertex(minX, minY, minZ, color, 1f);
		addVertex(minX, maxY, minZ, color, 1f);
		addVertex(minX, minY, minZ, color, 1f);

	}




	@Override
	public void drawPoint(BlockPos pos, float size, Color color) {
		drawPoint(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), size, color);
	}




	@Override
	public void drawPoint(Vector3d pos, float size, Color color) {
		beginPoints(size);
		drawPointOpen(pos, color);
		end();
	}




	@Override
	public void drawPointOpen(BlockPos pos, Color color) {
		drawPointOpen(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), color);
	}




	@Override
	public void drawPointOpen(Vector3d pos, Color color) {
		addVertex(pos, color);
	}

}