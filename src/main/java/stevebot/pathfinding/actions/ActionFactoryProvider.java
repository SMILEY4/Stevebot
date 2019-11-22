package stevebot.pathfinding.actions;

import stevebot.pathfinding.actions.playeractions.*;

import java.util.ArrayList;
import java.util.List;

public class ActionFactoryProvider {


	private final List<ActionFactory> factories = new ArrayList<>();


	private final ImpossibleActionHandler impossibleActionHandler = new ImpossibleActionHandler();




	public ActionFactoryProvider() {

		// walk-factories
		registerFactory(new ActionWalk.WalkFactoryNorth());
		registerFactory(new ActionWalk.WalkFactoryNorthEast());
		registerFactory(new ActionWalk.WalkFactoryEast());
		registerFactory(new ActionWalk.WalkFactorySouthEast());
		registerFactory(new ActionWalk.WalkFactorySouth());
		registerFactory(new ActionWalk.WalkFactorySouthWest());
		registerFactory(new ActionWalk.WalkFactoryWest());
		registerFactory(new ActionWalk.WalkFactoryNorthWest());

		impossibleActionHandler.makesImpossible(ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionStepDown.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionExitWater.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);

		// step-down-factories
		registerFactory(new ActionStepDown.StepDownFactoryNorth());
		registerFactory(new ActionStepDown.StepDownFactoryNorthEast());
		registerFactory(new ActionStepDown.StepDownFactoryEast());
		registerFactory(new ActionStepDown.StepDownFactorySouthEast());
		registerFactory(new ActionStepDown.StepDownFactorySouth());
		registerFactory(new ActionStepDown.StepDownFactorySouthWest());
		registerFactory(new ActionStepDown.StepDownFactoryWest());
		registerFactory(new ActionStepDown.StepDownFactoryNorthWest());

		impossibleActionHandler.makesImpossible(ActionStepDown.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionExitWater.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);

		// step-up-factories
		registerFactory(new ActionStepUp.StepUpFactoryNorth());
		registerFactory(new ActionStepUp.StepUpFactoryNorthEast());
		registerFactory(new ActionStepUp.StepUpFactoryEast());
		registerFactory(new ActionStepUp.StepUpFactorySouthEast());
		registerFactory(new ActionStepUp.StepUpFactorySouth());
		registerFactory(new ActionStepUp.StepUpFactorySouthWest());
		registerFactory(new ActionStepUp.StepUpFactoryWest());
		registerFactory(new ActionStepUp.StepUpFactoryNorthWest());

		impossibleActionHandler.makesImpossible(ActionStepUp.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepDown.class,
				ActionSprintJump.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionExitWater.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);

		// drop-down-factories
		registerFactory(new ActionDropDown.DropDownFactoryNorth());
		registerFactory(new ActionDropDown.DropDownFactoryNorthEast());
		registerFactory(new ActionDropDown.DropDownFactoryEast());
		registerFactory(new ActionDropDown.DropDownFactorySouthEast());
		registerFactory(new ActionDropDown.DropDownFactorySouth());
		registerFactory(new ActionDropDown.DropDownFactorySouthWest());
		registerFactory(new ActionDropDown.DropDownFactoryWest());
		registerFactory(new ActionDropDown.DropDownFactoryNorthWest());

		impossibleActionHandler.makesImpossible(ActionDropDown.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionStepDown.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionMineDown.class,
				ActionExitWater.class,
				ActionEnterWater.class
		);

		// enter water
		registerFactory(new ActionEnterWater.EnterWaterFactoryNorth());
		registerFactory(new ActionEnterWater.EnterWaterFactoryNorthEast());
		registerFactory(new ActionEnterWater.EnterWaterFactoryEast());
		registerFactory(new ActionEnterWater.EnterWaterFactorySouthEast());
		registerFactory(new ActionEnterWater.EnterWaterFactorySouth());
		registerFactory(new ActionEnterWater.EnterWaterFactorySouthWest());
		registerFactory(new ActionEnterWater.EnterWaterFactoryWest());
		registerFactory(new ActionEnterWater.EnterWaterFactoryNorthWest());

		impossibleActionHandler.makesImpossible(ActionEnterWater.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionStepDown.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionMineDown.class,
				ActionExitWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);

		// exit water
		registerFactory(new ActionExitWater.ExitWaterFactoryNorth());
		registerFactory(new ActionExitWater.ExitWaterFactoryNorthEast());
		registerFactory(new ActionExitWater.ExitWaterFactoryEast());
		registerFactory(new ActionExitWater.ExitWaterFactorySouthEast());
		registerFactory(new ActionExitWater.ExitWaterFactorySouth());
		registerFactory(new ActionExitWater.ExitWaterFactorySouthWest());
		registerFactory(new ActionExitWater.ExitWaterFactoryWest());
		registerFactory(new ActionExitWater.ExitWaterFactoryNorthWest());

		impossibleActionHandler.makesImpossible(ActionExitWater.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionStepDown.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionMineDown.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);

		// swim
		registerFactory(new ActionSwim.SwimFactoryNorth());
		registerFactory(new ActionSwim.SwimFactoryNorthEast());
		registerFactory(new ActionSwim.SwimFactoryEast());
		registerFactory(new ActionSwim.SwimFactorySouthEast());
		registerFactory(new ActionSwim.SwimFactorySouth());
		registerFactory(new ActionSwim.SwimFactorySouthWest());
		registerFactory(new ActionSwim.SwimFactoryWest());
		registerFactory(new ActionSwim.SwimFactoryNorthWest());

		impossibleActionHandler.makesImpossible(ActionSwim.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionStepDown.class,
				ActionSprintJump.class,
				ActionPillarUpMine.class,
				ActionPillarUp.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionMineDown.class,
				ActionFall.class,
				ActionJump.class,
				ActionExitWater.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionDigDown.class,
				ActionBridgeFree.class
		);

		// fall-factory
		registerFactory(new ActionFall.FallActionFactory());

		impossibleActionHandler.makesImpossible(ActionFall.class,
				ActionDigDown.class
		);

		// jump-factories
		registerFactory(new ActionJump.JumpFactoryNorth());
		registerFactory(new ActionJump.JumpFactoryNorthEast());
		registerFactory(new ActionJump.JumpFactoryEast());
		registerFactory(new ActionJump.JumpFactorySouthEast());
		registerFactory(new ActionJump.JumpFactorySouth());
		registerFactory(new ActionJump.JumpFactorySouthWest());
		registerFactory(new ActionJump.JumpFactoryWest());
		registerFactory(new ActionJump.JumpFactoryNorthWest());

		impossibleActionHandler.makesImpossible(ActionJump.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionMineDown.class,
				ActionExitWater.class
		);

		// sprint-jump-factories
		registerFactory(new ActionSprintJump.SprintJumpFactoryNorth());
		registerFactory(new ActionSprintJump.SprintJumpFactoryEast());
		registerFactory(new ActionSprintJump.SprintJumpFactorySouth());
		registerFactory(new ActionSprintJump.SprintJumpFactoryWest());

		impossibleActionHandler.makesImpossible(ActionSprintJump.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionMineDown.class,
				ActionExitWater.class
		);

		// pillar up
		registerFactory(new ActionPillarUp.PillarUpFactory());

		impossibleActionHandler.makesImpossible(ActionPillarUp.class,
				ActionPillarUpMine.class
		);

		// pillar mine
		registerFactory(new ActionPillarUpMine.PillarUpMineFactory());

		impossibleActionHandler.makesImpossible(ActionPillarUpMine.class,
				ActionPillarUp.class
		);

		// dig down
		registerFactory(new ActionDigDown.DigDownFactory());

		impossibleActionHandler.makesImpossible(ActionDigDown.class,
				ActionFall.class
		);

		// mine straight
		registerFactory(new ActionMineStraight.MineStraightFactoryNorth());
		registerFactory(new ActionMineStraight.MineStraightFactoryEast());
		registerFactory(new ActionMineStraight.MineStraightFactorySouth());
		registerFactory(new ActionMineStraight.MineStraightFactoryWest());

		impossibleActionHandler.makesImpossible(ActionMineStraight.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepDown.class,
				ActionSprintJump.class,
				ActionJump.class,
				ActionExitWater.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);

		// mine down
		registerFactory(new ActionMineDown.MineDownFactoryNorth());
		registerFactory(new ActionMineDown.MineDownFactoryEast());
		registerFactory(new ActionMineDown.MineDownFactorySouth());
		registerFactory(new ActionMineDown.MineDownFactoryWest());

		impossibleActionHandler.makesImpossible(ActionMineDown.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionStepDown.class,
				ActionExitWater.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);

		// mine up
		registerFactory(new ActionMineUp.MineUpFactoryNorth());
		registerFactory(new ActionMineUp.MineUpFactoryEast());
		registerFactory(new ActionMineUp.MineUpFactorySouth());
		registerFactory(new ActionMineUp.MineUpFactoryWest());

		impossibleActionHandler.makesImpossible(ActionMineUp.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepDown.class,
				ActionSprintJump.class,
				ActionJump.class,
				ActionExitWater.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);

		// bridge
		registerFactory(new ActionBridgeFree.BrideFreeFactoryNorth());
		registerFactory(new ActionBridgeFree.BrideFreeFactoryEast());
		registerFactory(new ActionBridgeFree.BrideFreeFactorySouth());
		registerFactory(new ActionBridgeFree.BrideFreeFactoryWest());

		impossibleActionHandler.makesImpossible(ActionBridgeFree.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionPassDoor.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionMineDown.class,
				ActionExitWater.class,
				ActionEnterWater.class
		);

		// door
		registerFactory(new ActionPassDoor.PassDoorFactoryNorth());
		registerFactory(new ActionPassDoor.PassDoorFactoryEast());
		registerFactory(new ActionPassDoor.PassDoorFactorySouth());
		registerFactory(new ActionPassDoor.PassDoorFactoryWest());

		impossibleActionHandler.makesImpossible(ActionPassDoor.class,
				ActionWalk.class,
				ActionSwim.class,
				ActionStepUp.class,
				ActionStepDown.class,
				ActionSprintJump.class,
				ActionMineUp.class,
				ActionMineStraight.class,
				ActionMineDown.class,
				ActionJump.class,
				ActionExitWater.class,
				ActionEnterWater.class,
				ActionDropDown.class,
				ActionBridgeFree.class
		);
	}




	/**
	 * Register the given factory
	 *
	 * @param factory the factory
	 */
	private void registerFactory(ActionFactory factory) {
		factories.add(factory);
	}




	/**
	 * @return the list of all registered {@link ActionFactory}
	 */
	public List<ActionFactory> getAllFactories() {
		return factories;
	}




	/**
	 * @return the handler for impossible actions
	 */
	public ImpossibleActionHandler getImpossibleActionHandler() {
		return impossibleActionHandler;
	}


}
