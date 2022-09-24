package stevebot.core.minecraft;

/**
 * Object holding information about a bound input (button/key)
 */
public interface InputBinding {

    /**
     * @return the key code of this input
     */
    int getKeyCode();

    /**
     * @return whether the button/key is currently pressed down
     */
    boolean isDown();

}