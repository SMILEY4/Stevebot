package stevebot.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import stevebot.Stevebot;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.Path;
import stevebot.pathfinding.actions.*;
import stevebot.utils.GameEventListener;

import java.util.*;


public class Renderer implements GameEventListener {


	private final Tessellator TESSELLATOR = Tessellator.getInstance();
	private final BufferBuilder BUFFER = TESSELLATOR.getBuffer();

	private List<Object> objectList = new ArrayList<>();

	public List<BlockPos> nodes = Collections.synchronizedList(new ArrayList<>());

	public Path path = null;



	public Renderer() {
		Stevebot.EVENT_HANDLER.addListener(this);
	}




	public void addObject(Object object) {
		if (object != null) {
			objectList.add(object);
		}
	}




	public void addObjects(Collection objects) {
		if (objects != null) {
			objectList.addAll(objects);
		}
	}




	public void removeObject(Object object) {
		if (object != null) {
			objectList.remove(object);
		}
	}




	public void removeAll() {
		objectList.clear();
	}




	@Override
	public void onRenderWorld(RenderWorldLastEvent event) {
		if (Minecraft.getMinecraft().player == null) {
			return;
		}

		int i = 0;
		while (!nodes.isEmpty() && i < 20) {
			objectList.add(nodes.remove(0));
			i++;
		}

		// setup
		Vec3d playerPos = Minecraft.getMinecraft().player.getPositionVector();
		setup(playerPos);

		// draw
		render();

		// reset
		reset();
	}




	private void render() {
		for (Object obj : objectList) {
			renderObject(obj);
		}
		if(path != null) {
			renderObject(path);
		}
	}




	private void renderObject(Object obj) {

		if (obj instanceof BlockPos) {
			BlockPos blockPos = (BlockPos) obj;
			drawBox(blockPos, 3, Color.GRAY);
		}

		if (obj instanceof Path) {
			Path path = (Path) obj;
			for (Node node : path.nodes) {
				if (node.action != null) {
					renderObject(node.action);
				}
			}
		}

		if (obj instanceof Vec3d) {
			Vec3d v = (Vec3d) obj;
			drawLine(v, v.addVector(0, 0.5, 0), 5, Color.BLUE);
		}


		if (obj instanceof Marker) {
			Marker marker = (Marker) obj;
			Vec3d pos = marker.pos;
			if(marker.color != null) {
				drawLine(pos, pos.addVector(0, 0.5, 0), 5, marker.color);
			} else {
				drawLine(pos, pos.addVector(0, 0.5, 0), 5, Color.random(marker.action.hashCode()));
			}
		}

		if (obj instanceof Action) {

			if (obj instanceof ActionWalk) {
				ActionWalk action = (ActionWalk) obj;
				drawLine(action.getFrom().pos, action.getTo().pos, 3f, Color.random(action.hashCode()));
//				drawLine(action.getFrom().pos, action.getTo().pos, 3f, Color.BLUE);
			}
			if (obj instanceof ActionStepUp) {
				ActionStepUp action = (ActionStepUp) obj;
				drawLine(action.getFrom().pos, action.getTo().pos, 3f, Color.random(action.hashCode()));
//				drawLine(action.getFrom().pos, action.getTo().pos, 3f, Color.RED);
			}
			if (obj instanceof ActionStepDown) {
				ActionStepDown action = (ActionStepDown) obj;
				drawLine(action.getFrom().pos, action.getTo().pos, 3f, Color.random(action.hashCode()));
//				drawLine(action.getFrom().pos, action.getTo().pos, 3f, Color.YELLOW);
			}

			if (obj instanceof ActionDropDownN) {
				ActionDropDownN action = (ActionDropDownN) obj;
				BlockPos p = new BlockPos(action.getTo().pos.getX(), action.getFrom().pos.getY(), action.getTo().pos.getZ());
				drawLine(action.getFrom().pos, p, 3f, Color.random(action.hashCode()));
				drawLine(p, action.getTo().pos, 3f, Color.random(action.hashCode()));
//				drawLine(action.getFrom().pos, action.getTo().pos, 3f, Color.YELLOW);
			}

		}

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




	private void drawLine(BlockPos start, BlockPos end, float width, Color color) {
		drawLine(new Vec3d(start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5),
				new Vec3d(end.getX() + 0.5, end.getY() + 0.5, end.getZ() + 0.5),
				width, color);
	}




	private void drawLine(Vec3d start, Vec3d end, float width, Color color) {
		BUFFER.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		drawLineOpen(start, end, width, color);
		TESSELLATOR.draw();
	}




	private void drawLineOpen(Vec3d start, Vec3d end, float width, Color color) {
		GlStateManager.glLineWidth(width);
		BUFFER.pos(start.x, start.y, start.z).color(color.x, color.y, color.z, 1f).endVertex();
		BUFFER.pos(end.x, end.y, end.z).color(color.x, color.y, color.z, 1f).endVertex();
	}




	private void drawBox(BlockPos pos, float width, Color color) {
		drawBox(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), width, color);
	}




	private void drawBox(Vec3d pos, float width, Color color) {

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




	Random random = new Random();


}
