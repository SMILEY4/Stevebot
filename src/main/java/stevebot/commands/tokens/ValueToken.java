package stevebot.commands.tokens;

import stevebot.commands.CommandArgument;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.math.BlockPos;

public abstract class ValueToken implements CommandToken {


	private final Class<?> type;
	private final String valueID;




	public ValueToken(String valueID, Class<?> type) {
		this.type = type;
		this.valueID = valueID;
	}




	public Class<?> getType() {
		return type;
	}




	@Override
	public String getID() {
		return valueID;
	}




	public static class IntegerToken extends ValueToken {


		public IntegerToken(String valueID) {
			super(valueID, Integer.class);
		}




		@Override
		public String getUsage() {
			return "<" + getID() + ">";
		}




		@Override
		public int requiredArguments(int nRemaining) {
			return 1;
		}




		@Override
		public TokenParseResult parse(ICommandSender sender, String[] args) {
			try {
				int value = CommandBase.parseInt(args[0]);
				return new TokenParseResult(new CommandArgument<Integer>(value));
			} catch (NumberInvalidException e) {
				return TokenParseResult.FAILED;
			}
		}

	}






	public static class FloatToken extends ValueToken {


		public FloatToken(String valueID) {
			super(valueID, Float.class);
		}




		@Override
		public String getUsage() {
			return "<" + getID() + ">";
		}




		@Override
		public int requiredArguments(int nRemaining) {
			return 1;
		}




		@Override
		public TokenParseResult parse(ICommandSender sender, String[] args) {
			try {
				float value = (float) CommandBase.parseDouble(args[0]);
				return new TokenParseResult(new CommandArgument<Float>(value));
			} catch (NumberInvalidException e) {
				return TokenParseResult.FAILED;
			}
		}

	}






	public static class BooleanToken extends ValueToken {


		public BooleanToken(String valueID) {
			super(valueID, Boolean.class);
		}




		@Override
		public String getUsage() {
			return "<" + getID() + ">";
		}




		@Override
		public int requiredArguments(int nRemaining) {
			return 1;
		}




		@Override
		public TokenParseResult parse(ICommandSender sender, String[] args) {
			try {
				boolean value = CommandBase.parseBoolean(args[0]);
				return new TokenParseResult(new CommandArgument<Boolean>(value));
			} catch (CommandException e) {
				return TokenParseResult.FAILED;
			}
		}

	}






	public static class TextToken extends ValueToken {


		public final boolean joinAll;




		public TextToken(String valueID) {
			this(valueID, false);
		}




		public TextToken(String valueID, boolean joinAll) {
			super(valueID, String.class);
			this.joinAll = joinAll;
		}




		@Override
		public String getUsage() {
			return "<" + getID() + ">";
		}




		@Override
		public int requiredArguments(int nRemaining) {
			return joinAll ? nRemaining : 1;
		}




		@Override
		public TokenParseResult parse(ICommandSender sender, String[] args) {
			return new TokenParseResult(new CommandArgument<String>(String.join(" ", args)));
		}

	}






	public static class BlockPosToken extends ValueToken {


		public final boolean center;




		public BlockPosToken(String valueID, boolean center) {
			super(valueID, BlockPos.class);
			this.center = center;
		}




		@Override
		public String getUsage() {
			return "<" + getID() + ".x" + "> "
					+ "<" + getID() + ".y" + "> "
					+ "<" + getID() + ".z" + ">";
		}




		@Override
		public int requiredArguments(int nRemaining) {
			return 3;
		}




		@Override
		public TokenParseResult parse(ICommandSender sender, String[] args) {
			try {
				BlockPos value = CommandBase.parseBlockPos(sender, args, 0, center);
				return new TokenParseResult(new CommandArgument<>(value));
			} catch (NumberInvalidException e) {
				return TokenParseResult.FAILED;
			}
		}


	}


}
