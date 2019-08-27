package modtools.rendering;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import modtools.ModBase;
import modtools.ModModule;
import modtools.events.GameRenderListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class MTRenderer extends ModModule {


	private final Tessellator TESSELLATOR = Tessellator.getInstance();
	private final BufferBuilder BUFFER = TESSELLATOR.getBuffer();

	private List<Renderable> renderables = new ArrayList<>();




	public MTRenderer(ModBase modHandler) {
		super(modHandler);
		modHandler.getEventHandler().addListener(new GameRenderListener() {
			@Override
			public void onRenderWorldLast(RenderWorldLastEvent event) {
				if (modHandler.getPlayerController().getPlayer() != null) {

					// setup
					Vec3d playerPos = Minecraft.getMinecraft().player.getPositionVector();
					setup(playerPos);

					// draw
					for (int i = 0, n = renderables.size(); i < n; i++) {
						Renderable renderable = renderables.get(i);
						renderable.render(MTRenderer.this);
					}

					// reset
					reset();
				}
			}
		});
	}




	public void addRenderable(Renderable renderable) {
		this.renderables.add(renderable);
	}




	public void removeRenderable(Renderable renderable) {
		this.renderables.remove(renderable);
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




	public void beginLines(float width) {
		GlStateManager.glLineWidth(width);
		BUFFER.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
	}




	public void beginLineStrip(float width) {
		GlStateManager.glLineWidth(width);
		BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	}




	public void beginBoxes(float width) {
		GlStateManager.glLineWidth(width);
		BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	}




	public void beginPoints(float size) {
		GL11.glPointSize(size);
		BUFFER.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
	}




	public void end() {
		TESSELLATOR.draw();
	}




	public void drawLine(BlockPos start, BlockPos end, float width, Color color) {
		drawLine(
				new Vector3d(start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5),
				new Vector3d(end.getX() + 0.5, end.getY() + 0.5, end.getZ() + 0.5),
				width,
				color);
	}




	public void drawLine(Vector3d start, Vector3d end, float width, Color color) {
		beginLines(width);
		drawLineOpen(start, end, color);
		end();
	}




	public void drawLineOpen(Vector3d start, Vector3d end, Color color) {
		BUFFER.pos(start.x, start.y, start.z).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(end.x, end.y, end.z).color(color.x, color.y, color.z, 1f).endVertex();
	}




	public void drawBox(BlockPos pos, float width, Color color) {
		drawBox(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), width, color);
	}




	public void drawBoxOpen(BlockPos pos, Color color) {
		drawBoxOpen(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), color);
	}




	public void drawBox(Vector3d pos, float width, Color color) {
		beginLineStrip(width);
		drawBoxOpen(pos, color);
		end();
	}




	@SuppressWarnings ("Duplicates")
	public void drawBoxOpen(Vector3d pos, Color color) {

		final double minX = pos.x;
		final double maxX = pos.x + 1;
		final double minY = pos.y;
		final double maxY = pos.y + 1;
		final double minZ = pos.z;
		final double maxZ = pos.z + 1;

		BUFFER.pos(maxX, minY, maxZ).color(1, 1, 1, 0f).endVertex();

		BUFFER.pos(maxX, minY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, minY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();

		BUFFER.pos(maxX, maxY, minZ).color(1, 1, 1, 0f).endVertex();

		BUFFER.pos(maxX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, maxY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, minY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();

		BUFFER.pos(minX, maxY, maxZ).color(1, 1, 1, 0f).endVertex();

		BUFFER.pos(minX, maxY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, maxY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, maxY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, minY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();

	}




	public void drawPoint(BlockPos pos, float size, Color color) {
		drawPoint(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), size, color);
	}




	public void drawPoint(Vector3d pos, float size, Color color) {
		beginPoints(size);
		BUFFER.pos(pos.x, pos.y, pos.z).color(color.x, color.y, color.z, 1f).endVertex();
		end();
	}




	public void drawPointOpen(BlockPos pos, Color color) {
		drawPointOpen(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), color);
	}




	public void drawPointOpen(Vector3d pos, Color color) {
		BUFFER.pos(pos.x, pos.y, pos.z).color(color.x, color.y, color.z, 1f).endVertex();
	}

}