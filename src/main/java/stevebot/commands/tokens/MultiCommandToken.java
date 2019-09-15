package stevebot.commands.tokens;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import stevebot.commands.CustomCommand;

import java.util.ArrayList;
import java.util.List;

public class MultiCommandToken implements CommandToken {


	private final String id;
	private List<CustomCommand> commands = new ArrayList<>();




	public MultiCommandToken(String id) {
		this.id = id;
	}




	public MultiCommandToken addCommand(CustomCommand command) {
		this.commands.add(command);
		return this;
	}




	@Override
	public String getID() {
		return id;
	}




	@Override
	public String getUsage() {
		return "todo";
	}




	@Override
	public int requiredArguments(int nRemaining) {
		return nRemaining;
	}




	@Override
	public TokenParseResult parse(ICommandSender sender, String[] args) {
		if (args == null) {
			return TokenParseResult.FAILED;
		} else {
			for (CustomCommand command : commands) {
				try {
					command.execute(sender, args);
					return TokenParseResult.SUCCESS;
				} catch (WrongUsageException e) {
					continue;
				}
			}
			return TokenParseResult.FAILED;
		}
	}

}
