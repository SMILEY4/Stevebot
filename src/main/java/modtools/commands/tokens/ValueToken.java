package modtools.commands.tokens;

import modtools.commands.CommandArgument;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.math.BlockPos;

public abstract class ValueToken implements ICommandToken {


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
		public int requiredArguments() {
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
		public int requiredArguments() {
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
		public int requiredArguments() {
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


		public TextToken(String valueID) {
			super(valueID, String.class);
		}




		@Override
		public String getUsage() {
			return "<" + getID() + ">";
		}




		@Override
		public int requiredArguments() {
			return 1;
		}




		@Override
		public TokenParseResult parse(ICommandSender sender, String[] args) {
			return new TokenParseResult(new CommandArgument<String>(args[0]));
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
		public int requiredArguments() {
			return 3;
		}




		@Override
		public TokenParseResult parse(ICommandSender sender, String[] args) {
			try {
				BlockPos value = CommandBase.parseBlockPos(sender, args, 0, center);
				return new TokenParseResult(new CommandArgument<BlockPos>(value));
			} catch (NumberInvalidException e) {
				return TokenParseResult.FAILED;
			}
		}


	}


}
