package stevebot.player;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import stevebot.minecraft.MinecraftAdapter;

import java.util.HashMap;
import java.util.Map;

class PlayerInputConfig {


	public enum InputType {
		WALK_FORWARD,
		WALK_BACKWARD,
		WALK_LEFT,
		WALK_RIGHT,
		SPRINT,
		SNEAK,
		JUMP,
		PLACE_BLOCK,
		BREAK_BLOCK,
		INTERACT
	}






	private final Map<InputType, KeyBinding> bindingMap = new HashMap<>();




	PlayerInputConfig() {

		GameSettings settings = MinecraftAdapter.get().getGameSettings();

		bindingMap.put(InputType.WALK_FORWARD, settings.keyBindForward);
		bindingMap.put(InputType.WALK_BACKWARD, settings.keyBindBack);
		bindingMap.put(InputType.WALK_LEFT, settings.keyBindLeft);
		bindingMap.put(InputType.WALK_RIGHT, settings.keyBindRight);

		bindingMap.put(InputType.SPRINT, settings.keyBindSprint);
		bindingMap.put(InputType.SNEAK, settings.keyBindSneak);
		bindingMap.put(InputType.JUMP, settings.keyBindJump);

		bindingMap.put(InputType.PLACE_BLOCK, settings.keyBindUseItem);
		bindingMap.put(InputType.BREAK_BLOCK, settings.keyBindAttack);
		bindingMap.put(InputType.INTERACT, settings.keyBindUseItem);
	}




	/**
	 * @param type the {@link InputType}
	 * @return the {@link KeyBinding} of the given {@link InputType}
	 */
	KeyBinding getBinding(InputType type) {
		return bindingMap.get(type);
	}


}
