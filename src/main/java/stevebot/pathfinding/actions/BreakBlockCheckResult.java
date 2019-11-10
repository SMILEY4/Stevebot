package stevebot.pathfinding.actions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.wrapper.ItemWrapper;

public class BreakBlockCheckResult {


	public static BreakBlockCheckResult invalid(BaseBlockPos blockPos) {
		return new BreakBlockCheckResult(blockPos, false, ItemLibrary.INVALID_ITEM, -1);
	}




	public static BreakBlockCheckResult valid(BaseBlockPos blockPos, ItemWrapper bestTool, float ticksToBreak) {
		return new BreakBlockCheckResult(blockPos, true, bestTool, ticksToBreak);
	}




	public final boolean breakable;
	public final BaseBlockPos blockPos;
	public final ItemWrapper bestTool;
	public final float ticksToBreak;




	private BreakBlockCheckResult(BaseBlockPos blockPos, boolean breakable, ItemWrapper bestTool, float ticksToBreak) {
		this.blockPos = blockPos;
		this.breakable = breakable;
		this.bestTool = bestTool;
		this.ticksToBreak = ticksToBreak;
	}

}
