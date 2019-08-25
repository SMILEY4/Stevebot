package modtools.commands;

import modtools.ModBase;
import modtools.ModModule;
import modtools.events.GameInitListener;
import net.minecraftforge.client.ClientCommandHandler;

import java.util.ArrayList;
import java.util.List;

public class MTCommandHandler extends ModModule implements GameInitListener {


	private List<MTCommand> commandList = new ArrayList<>();




	public MTCommandHandler(ModBase modHandler) {
		super(modHandler);
		modHandler.getEventHandler().addListener(this);
	}




	@Override
	public void onPreInit() {
		for (MTCommand command : commandList) {
			ClientCommandHandler.instance.registerCommand(command.getCommandBase());
		}
	}




	public void registerCommand(MTCommand command) {
		commandList.add(command);
	}


}
