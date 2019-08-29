package modtools.commands.tokens;

import net.minecraft.command.ICommandSender;

public class OptionalToken implements ICommandToken {


	private final ICommandToken token;




	public OptionalToken(ICommandToken token) {
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




	public ICommandToken getToken() {
		return token;
	}

}
