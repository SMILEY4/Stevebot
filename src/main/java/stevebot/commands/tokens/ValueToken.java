package stevebot.commands.tokens;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.math.BlockPos;
import stevebot.commands.CommandArgument;

public abstract class ValueToken implements CommandToken {


	private final Class<?> type;
	private final String valueID;




	/**
	 * @param valueID the name of this value.
	 * @param type    the datatype of this value-token. Valid types are: Integer, Float, Boolean, String, BlockPos.
	 */
	public ValueToken(String valueID, Class<?> type) {
		this.type = type;
		this.valueID = valueID;
	}




	/**
	 * @return the data-type of this token.
	 */
	public Class<?> getType() {
		return type;
	}




	@Override
	public String getID() {
		return valueID;
	}




	/**
	 * A value-token of the type Integer. Takes a single positive or negative number.
	 */
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






	/**
	 * A value-token of the type Float. Takes a positive or negative floating point value.
	 */
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






	/**
	 * A value-token of the type Float. Takes a "true", "1", "false", or "0" as input.
	 */
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






	/**
	 * A value-token of the type String. Takes one (or more) word(s) as input.
	 */
	public static class TextToken extends ValueToken {


		public final boolean joinAll;




		public TextToken(String valueID) {
			this(valueID, false);
		}




		/**
		 * @param joinAll false to use only one word, true to use all remaining words for this value.
		 */
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
			return new TokenParseResult(new CommandArgument<>(String.join(" ", args)));
		}

	}






	/**
	 * A value-token of the type String/Enum. Takes one of the predefined words as input.
	 */
	public static class EnumToken extends ValueToken {


		private final String[] values;




		/**
		 * @param values the valid values/words for this token.
		 */
		public EnumToken(String valueID, String... values) {
			super(valueID, String.class);
			this.values = values;
		}




		@Override
		public String getUsage() {
			return "<" + String.join("|", values) + ">";
		}




		@Override
		public int requiredArguments(int nRemaining) {
			return 1;
		}




		@Override
		public TokenParseResult parse(ICommandSender sender, String[] args) {
			for (String value : values) {
				if (value.equalsIgnoreCase(args[0])) {
					return new TokenParseResult(new CommandArgument<>(value));
				}
			}
			return TokenParseResult.FAILED;
		}


	}






	/**
	 * A value-token of the type BlockPos. Takes three coordinates as input. A coordinate can be positive or negative. The number can be replaced by a "~".
	 */
	public static class BlockPosToken extends ValueToken {


		public final boolean center;




		/**
		 * @param center whether or not the result should be the exact coordinate or the center of the block.
		 */
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
