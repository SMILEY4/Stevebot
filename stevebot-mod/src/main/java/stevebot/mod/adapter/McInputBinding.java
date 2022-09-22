package stevebot.mod.adapter;

import net.minecraft.client.settings.KeyBinding;
import stevebot.core.minecraft.InputBinding;

class McInputBinding implements InputBinding {

    private final KeyBinding binding;

    public McInputBinding(final KeyBinding binding) {
        this.binding = binding;
    }

    @Override
    public int getKeyCode() {
        return binding.getKeyCode();
    }

    @Override
    public boolean isDown() {
        return binding.isKeyDown();
    }

}