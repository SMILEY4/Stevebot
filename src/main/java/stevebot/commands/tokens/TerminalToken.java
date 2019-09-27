package stevebot.commands.tokens;

import net.minecraft.command.ICommandSender;

public class TerminalToken implements CommandToken {


	private final String terminalID;




	/**
	 * Creates a new terminal token. This token can only be one fixed word in the command.
	 *
	 * @param terminalID the name of this terminal
	 */
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
