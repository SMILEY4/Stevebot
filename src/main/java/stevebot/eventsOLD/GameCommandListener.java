package stevebot.eventsOLD;

import net.minecraftforge.event.CommandEvent;

public interface GameCommandListener extends EventListener {


	/**
	 * Called when a command is fired.
	 */
	void onCommand(CommandEvent event);

}
