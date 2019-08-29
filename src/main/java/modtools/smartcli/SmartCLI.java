package modtools.smartcli;

import modtools.ModBase;
import modtools.ModModule;
import modtools.commands.CommandBuilder;
import modtools.commands.MTCommandHandler;
import modtools.commands.tokens.ValueToken;
import modtools.events.GameCommandListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;

public class SmartCLI extends ModModule implements GameCommandListener {


	/*
	 * /var <varname> <value>
	 *   saves the value with the given name
	 *
	 * /print <varname>
	 *    prints the value of the variable with the given name
	 *
	 * /copy <varname>
	 *    copies the value of the variable to the clipboard
	 *
	 *
	 *
	 * - use $varname in any command and it will be replaced by the value of the variable
	 *
	 * */

	private Map<String, String> variables = new HashMap<>();

	private final String NAME_POS_PLAYER = "POS_PLAYER";




	public SmartCLI(ModBase modHandler) {
		super(modHandler);
		getModHandler().getEventHandler().addListener(this);
		this.create();
	}




	private void create() {


		MTCommandHandler cmdHandler = getModHandler().getCommandHandler();

		// /var <varname> <value>
		cmdHandler.registerCommand(
				new CommandBuilder("var")
						.addToken(new ValueToken.TextToken("varname"))
						.addToken(new ValueToken.TextToken("value", true))
						.setListener((sender, name, args) -> {
							onSaveVar((String) args.get("varname").getValue(), (String) args.get("value").getValue());
						})
						.build());

		// /print <varname>
		cmdHandler.registerCommand(
				new CommandBuilder("print")
						.addToken(new ValueToken.TextToken("varname"))
						.setListener((sender, name, args) -> {
							onPrintVar((String) args.get("varname").getValue());
						})
						.build());

		// /copy <varname>
		cmdHandler.registerCommand(
				new CommandBuilder("copy")
						.addToken(new ValueToken.TextToken("varname"))
						.setListener((sender, name, args) -> {
							onCopyVar((String) args.get("varname").getValue());
						})
						.build());

	}




	public boolean existsVariable(String name) {
		if (name.equalsIgnoreCase(NAME_POS_PLAYER) && getModHandler().getPlayerController().getPlayer() != null) {
			return true;
		}
		return variables.containsKey(name.toUpperCase());
	}




	public String getValue(String name) {
		if (name.equalsIgnoreCase(NAME_POS_PLAYER) && getModHandler().getPlayerController().getPlayer() != null) {
			BlockPos pos = getModHandler().getPlayerController().getPlayerBlockPos();
			return pos.getX() + " " + pos.getY() + " " + pos.getZ();
		}
		return variables.getOrDefault(name.toUpperCase(), null);
	}




	public void setValue(String name, String value) {
		this.variables.put(name.toUpperCase(), value);
	}




	private void onSaveVar(String name, String value) {
		setValue(name, value);
		getModHandler().getPlayerController().sendMessage("Saved '" + value + "' as '" + name + "'.");
	}




	private void onPrintVar(String name) {
		if (name.equalsIgnoreCase("*")) {
			for (String strName : variables.keySet()) {
				getModHandler().getPlayerController().sendMessage(strName + " = " + getValue(strName));
			}
			getModHandler().getPlayerController().sendMessage(NAME_POS_PLAYER + " = " + getValue(NAME_POS_PLAYER));

		} else {
			if (existsVariable(name)) {
				getModHandler().getPlayerController().sendMessage(name + " = " + getValue(name));
			} else {
				getModHandler().getPlayerController().sendMessage("Variable with name '" + name + "' not found.");
			}
		}
	}




	private void onCopyVar(String name) {
		if (existsVariable(name)) {
			String value = getValue(name);
			StringSelection selection = new StringSelection(value);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			getModHandler().getPlayerController().sendMessage("Variable '" + name + "' copied to clipboard.");
		} else {
			getModHandler().getPlayerController().sendMessage("Variable with name '" + name + "' not found.");
		}
	}




	@Override
	public void onCommand(CommandEvent event) {

		// extract/replace parameters
		List<String> list = new ArrayList<>();
		String[] params = event.getParameters();
		boolean changedCommand = false;

		for (int i = 0; i < params.length; i++) {

			String strParam = params[i];
			if (strParam.startsWith("$") && strParam.length() > 1) {
				String strVarName = strParam.substring(1);
				if (existsVariable(strVarName)) {
					String value = getValue(strVarName);
					list.addAll(Arrays.asList(value.split(" ")));
				}
				changedCommand = true;

			} else {
				list.add(strParam);
			}

		}

		// modify command / build new command
		if(changedCommand) {

			if(list.size() == params.length) {
				for(int i=0; i<params.length; i++) {
					params[i] = list.get(i);
				}

			} else {
				String[] newParams = new String[list.size()];
				for(int i=0; i<list.size(); i++) {
					newParams[i] = list.get(i);
				}
				String newCommand = "/" + event.getCommand().getName() + " " + String.join(" ", newParams);

				event.setCanceled(true);

				ClientCommandHandler.instance.executeCommand(FMLClientHandler.instance().getClientPlayerEntity(), newCommand);
				getModHandler().getPlayerController().getPlayer().sendChatMessage(newCommand);

			}

		}

	}

}
