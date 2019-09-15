package stevebot.commands;

public class CommandTest {


//	public static void main(String[] javaArgs) throws WrongUsageException {
//
//		ICommandSender senderMock = new ICommandSender() {
//
//
//			@Override
//			public String getName() {
//				return "mock";
//			}
//
//
//
//
//			@Override
//			public boolean canUseCommand(int permLevel, String commandName) {
//				return true;
//			}
//
//
//
//
//			@Override
//			public World getEntityWorld() {
//				return null;
//			}
//
//
//
//
//			@Nullable
//			@Override
//			public MinecraftServer getServer() {
//				return null;
//			}
//		};
//
//		CustomCommand commandMulti = new CommandBuilder("path")
//				.addToken(new MultiCommandToken("pathMulti")
//						.addCommand(new CommandBuilder("path")
//								.addToken(new ValueToken.BlockPosToken("from", true))
//								.addToken(new ValueToken.BlockPosToken("to", true))
//								.setListener((sender, name, args) -> System.out.println("execute path from to: " + printArgsMap(args)))
//								.build())
//						.addCommand(new CommandBuilder("path")
//								.addToken(new ValueToken.BlockPosToken("to", true))
//								.addOptional(new ValueToken.BooleanToken("follow"))
//								.addOptional(new ValueToken.BooleanToken("freelook"))
//								.setListener((sender, name, args) -> System.out.println("execute path to: " + printArgsMap(args)))
//								.build())
//						.addCommand(new CommandBuilder("path")
//								.addToken(new ValueToken.IntegerToken("dist"))
//								.addOptional(new ValueToken.BooleanToken("follow"))
//								.addOptional(new ValueToken.BooleanToken("freelook"))
//								.setListener((sender, name, args) -> System.out.println("execute path dir: " + printArgsMap(args)))
//								.build())
//				)
//				.build();
//
//		commandMulti.execute(senderMock, new String[]{    "~-2", "~", "~-1",    "0", "~1", "~2"    });
//
//		commandMulti.execute(senderMock, new String[]{    "0", "~1", "~2",     "true"});
//		commandMulti.execute(senderMock, new String[]{    "0", "~1", "~2",     "true",     "false"});
//
//		commandMulti.execute(senderMock, new String[]{    "200",     "true"});
//		commandMulti.execute(senderMock, new String[]{    "200",     "true",     "false"});
//
//	}
//
//
//
//
//	static String printArgsMap(Map<String, CommandArgument<?>> args) {
//		StringBuilder builder = new StringBuilder();
//		builder.append(System.lineSeparator());
//		for (Map.Entry<String, CommandArgument<?>> entry : args.entrySet()) {
//			builder.append("  - " + entry.getKey() + ": " + (entry.getValue().existsValue() ? entry.getValue().getValue() : "-") + System.lineSeparator());
//		}
//		return builder.toString();
//	}


}
