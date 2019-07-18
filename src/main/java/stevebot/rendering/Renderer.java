package stevebot.rendering;

import com.sun.javafx.geom.Vec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import stevebot.pathfinding.Path;
import stevebot.pathfinding.actions.ActionMove;
import stevebot.pathfinding.actions.IAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Renderer {


	private static final List<Path> PATH_LIST = new ArrayList<>();
	private static List<IAction> ACTIONS_LIST = new ArrayList<>();




	public static void add(Path path) {
		PATH_LIST.add(path);
	}




	public static void clearPathList() {
		PATH_LIST.clear();
	}




	public static void add(List<IAction> actions) {
		ACTIONS_LIST = actions;
	}




	public static void clearActionList() {
		ACTIONS_LIST = Collections.emptyList();
	}




	private final Tessellator TESSELLATOR = Tessellator.getInstance();
	private final BufferBuilder BUFFER = TESSELLATOR.getBuffer();

	public static Vec3d targetPos = new Vec3d(0, 0, 0);




	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if (Minecraft.getMinecraft().player == null) {
			return;
		}

		// setup
		Vec3d playerPos = Minecraft.getMinecraft().player.getPositionVector();
		setup(playerPos);

		// draw paths
		Vec3f colorStart = new Vec3f(0f, 0f, 1f);
		Vec3f colorPath = new Vec3f(0f, 1f, 0f);
		Vec3f colorEnd = new Vec3f(1f, 0f, 0f);

		for (Path path : Renderer.PATH_LIST) {
			if (!path.nodes.isEmpty()) {
				for (int i = 0; i < path.nodes.size(); i++) {
					Vec3f color = colorPath;
					if (i == 0) {
						color = colorStart;
					}
					if (i == path.nodes.size() - 1) {
						color = colorEnd;
					}
					drawBox(new Vec3d(path.nodes.get(i).pos), 3, color);
				}
			}
		}

		// draw actions
		Vec3f colorOrigin = new Vec3f(1f, 1f, 1f);
		Vec3f colorMove = new Vec3f(1f, 0f, 0f);

		for(IAction action : ACTIONS_LIST) {
			drawBox(new Vec3d(action.getFrom().pos), 3, colorOrigin);
			if(action instanceof ActionMove) {
				drawLine(new Vec3d(action.getFrom().pos), new Vec3d(action.getTo().pos), 3, colorMove);
			}
		}

		// reset
		reset();
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




	private void drawLine(Vec3d start, Vec3d end, float width, Vec3f color) {
		BUFFER.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		drawLineOpen(start, end, width, color);
		TESSELLATOR.draw();
	}




	private void drawLineOpen(Vec3d start, Vec3d end, float width, Vec3f color) {
		GlStateManager.glLineWidth(width);
		BUFFER.pos(start.x + 0.49, start.y + 0.49, start.z + 0.49).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(end.x + 0.49, end.y + 0.49, end.z + 0.49).color(color.x, color.y, color.z, 1f).endVertex();
	}




	private void drawBox(Vec3d pos, float width, Vec3f color) {

		final double minX = pos.x;
		final double maxX = pos.x + 1;
		final double minY = pos.y;
		final double maxY = pos.y + 1;
		final double minZ = pos.z;
		final double maxZ = pos.z + 1;

		GlStateManager.glLineWidth(width);
		BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		BUFFER.pos(minX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, minY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, minY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		TESSELLATOR.draw();
		BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		BUFFER.pos(minX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, maxY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, maxY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		TESSELLATOR.draw();
		BUFFER.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		BUFFER.pos(minX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, minY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, maxY, minZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, minY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(maxX, maxY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, minY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(minX, maxY, maxZ).color(color.x, color.y, color.z, 1f).endVertex();
		TESSELLATOR.draw();

	}

}
