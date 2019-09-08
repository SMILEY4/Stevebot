package stevebot.commands.tokens;

import stevebot.commands.CommandArgument;

public class TokenParseResult {


	public static final TokenParseResult FAILED = new TokenParseResult(false);
	public static final TokenParseResult SUCCESS = new TokenParseResult(true);

	public final boolean success;
	public final CommandArgument<?> argument;




	public TokenParseResult(boolean success) {
		this.success = success;
		this.argument = null;
	}




	public TokenParseResult(CommandArgument<?> argument) {
		this.success = true;
		this.argument = argument;
	}

}
