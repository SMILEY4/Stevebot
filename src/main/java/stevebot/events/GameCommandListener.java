package stevebot.events;

import net.minecraftforge.event.CommandEvent;

public interface GameCommandListener extends EventListener {


	void onCommand(CommandEvent event);

}
