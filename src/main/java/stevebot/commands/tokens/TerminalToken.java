package stevebot.commands.tokens;

import net.minecraft.command.ICommandSender;

public class TerminalToken implements ICommandToken {


	private final String terminalID;




	public TerminalToken(String terminalID) {
		this.terminalID = terminalID;
	}




	@Override
	public String getID() {
		return terminalID;
	}




	@Override
	public String getUsage() {
		return terminalID;
	}




	@Override
	public int requiredArguments(int nReminaing) {
		return 1;
	}




	@Override
	public TokenParseResult parse(ICommandSender sender, String[] args) {
		final boolean success = args[0].equals(getID());
		if (success) {
			return TokenParseResult.SUCCESS;
		} else {
			return TokenParseResult.FAILED;
		}
	}


}
