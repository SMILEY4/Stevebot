package stevebot.core.player;

import java.util.HashMap;
import java.util.Map;
import stevebot.core.minecraft.InputBinding;
import stevebot.core.minecraft.MinecraftAdapter;

public class PlayerInputConfig {


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




    private final Map<InputType, InputBinding> bindingMap = new HashMap<>();


    PlayerInputConfig(MinecraftAdapter minecraftAdapter) {
        bindingMap.put(InputType.WALK_FORWARD, minecraftAdapter.getKeyBinding(InputType.WALK_FORWARD));
        bindingMap.put(InputType.WALK_BACKWARD, minecraftAdapter.getKeyBinding(InputType.WALK_BACKWARD));
        bindingMap.put(InputType.WALK_LEFT, minecraftAdapter.getKeyBinding(InputType.WALK_LEFT));
        bindingMap.put(InputType.WALK_RIGHT, minecraftAdapter.getKeyBinding(InputType.WALK_RIGHT));

        bindingMap.put(InputType.SPRINT, minecraftAdapter.getKeyBinding(InputType.SPRINT));
        bindingMap.put(InputType.SNEAK, minecraftAdapter.getKeyBinding(InputType.SNEAK));
        bindingMap.put(InputType.JUMP, minecraftAdapter.getKeyBinding(InputType.JUMP));

        bindingMap.put(InputType.PLACE_BLOCK, minecraftAdapter.getKeyBinding(InputType.PLACE_BLOCK));
        bindingMap.put(InputType.BREAK_BLOCK, minecraftAdapter.getKeyBinding(InputType.BREAK_BLOCK));
        bindingMap.put(InputType.INTERACT, minecraftAdapter.getKeyBinding(InputType.INTERACT));
    }


    /**
     * @param type the {@link InputType}
     * @return the key-code of the given {@link InputType}
     */
    public InputBinding getBinding(InputType type) {
        return bindingMap.get(type);
    }

}
