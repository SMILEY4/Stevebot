package stevebot.commands.tokens;

import net.minecraft.command.ICommandSender;

public class OptionalToken implements CommandToken {


	private final CommandToken token;




	/**
	 * Wraps the given (non-optional) token making it optional.
	 *
	 * @param token the (non-optiona) token
	 */
	public OptionalToken(CommandToken token) {
		if (token instanceof OptionalToken) {
			throw new IllegalArgumentException("Token of an optional token can not be optional.");
		}
		this.token = token;
	}




	@Override
	public String getID() {
		return token.getID();
	}




	@Override
	public String getUsage() {
		return "[" + token.getUsage() + "]";
	}




	@Override
	public int requiredArguments(int nRemaining) {
		return token.requiredArguments(nRemaining);
	}




	@Override
	public TokenParseResult parse(ICommandSender sender, String[] args) {
		if (args == null) {
			return TokenParseResult.FAILED;
		} else {
			return token.parse(sender, args);
		}
	}




	/**
	 * @return the (non-optional) token
	 */
	public CommandToken getToken() {
		return token;
	}

}
