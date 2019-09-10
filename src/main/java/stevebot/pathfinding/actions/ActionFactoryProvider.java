package stevebot.pathfinding.actions;

import stevebot.pathfinding.actions.playeractions.*;

import java.util.ArrayList;
import java.util.List;

public class ActionFactoryProvider {


	private final List<ActionFactory> factories = new ArrayList<>();




	public ActionFactoryProvider() {

		// walk.factories
		registerFactory(new ActionWalk.WalkFactoryNorth());
		registerFactory(new ActionWalk.WalkFactoryNorthEast());
		registerFactory(new ActionWalk.WalkFactoryEast());
		registerFactory(new ActionWalk.WalkFactorySouthEast());
		registerFactory(new ActionWalk.WalkFactorySouth());
		registerFactory(new ActionWalk.WalkFactorySouthWest());
		registerFactory(new ActionWalk.WalkFactoryWest());
		registerFactory(new ActionWalk.WalkFactoryNorthWest());

		// step-down-factories
		registerFactory(new ActionStepDown.StepDownFactoryNorth());
		registerFactory(new ActionStepDown.StepDownFactoryNorthEast());
		registerFactory(new ActionStepDown.StepDownFactoryEast());
		registerFactory(new ActionStepDown.StepDownFactorySouthEast());
		registerFactory(new ActionStepDown.StepDownFactorySouth());
		registerFactory(new ActionStepDown.StepDownFactorySouthWest());
		registerFactory(new ActionStepDown.StepDownFactoryWest());
		registerFactory(new ActionStepDown.StepDownFactoryNorthWest());

		// step-up-factories
		registerFactory(new ActionStepUp.StepUpFactoryNorth());
		registerFactory(new ActionStepUp.StepUpFactoryNorthEast());
		registerFactory(new ActionStepUp.StepUpFactoryEast());
		registerFactory(new ActionStepUp.StepUpFactorySouthEast());
		registerFactory(new ActionStepUp.StepUpFactorySouth());
		registerFactory(new ActionStepUp.StepUpFactorySouthWest());
		registerFactory(new ActionStepUp.StepUpFactoryWest());
		registerFactory(new ActionStepUp.StepUpFactoryNorthWest());

		// drop-down-factories
		registerFactory(new ActionDropDown.DropDownFactoryNorth());
		registerFactory(new ActionDropDown.DropDownFactoryNorthEast());
		registerFactory(new ActionDropDown.DropDownFactoryEast());
		registerFactory(new ActionDropDown.DropDownFactorySouthEast());
		registerFactory(new ActionDropDown.DropDownFactorySouth());
		registerFactory(new ActionDropDown.DropDownFactorySouthWest());
		registerFactory(new ActionDropDown.DropDownFactoryWest());
		registerFactory(new ActionDropDown.DropDownFactoryNorthWest());

		// fall-factory
		registerFactory(new ActionFall.FallActionFactory());

		// jump-factories
		registerFactory(new ActionJump.JumpFactoryNorth());
		registerFactory(new ActionJump.JumpFactoryNorthEast());
		registerFactory(new ActionJump.JumpFactoryEast());
		registerFactory(new ActionJump.JumpFactorySouthEast());
		registerFactory(new ActionJump.JumpFactorySouth());
		registerFactory(new ActionJump.JumpFactorySouthWest());
		registerFactory(new ActionJump.JumpFactoryWest());
		registerFactory(new ActionJump.JumpFactoryNorthWest());

		// spring-jump-factories
		registerFactory(new ActionSprintJump.SprintJumpFactoryNorth());
		registerFactory(new ActionSprintJump.SprintJumpFactoryEast());
		registerFactory(new ActionSprintJump.SprintJumpFactorySouth());
		registerFactory(new ActionSprintJump.SprintJumpFactoryWest());

	}




	private void registerFactory(ActionFactory factory) {
		factories.add(factory);
	}




	public List<ActionFactory> getAllFactories() {
		return factories;
	}

}