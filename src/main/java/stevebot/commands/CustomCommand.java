package stevebot.commands;

import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import stevebot.data.blocks.BlockUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomCommand extends CommandBase {


	private final String name;
	private List<CommandTemplate> templates = new ArrayList<>();




	/**
	 * @param name the name and first argument of this command "/name ..."
	 */
	public CustomCommand(String name) {
		this.name = name;
	}




	/**
	 * @param template the {@link CommandTemplate} to register
	 */
	protected void registerTemplate(CommandTemplate template) {
		templates.add(template);
	}




	@Override
	public String getName() {
		return name;
	}




	@Override
	public String getUsage(ICommandSender sender) {
		StringBuilder builder = new StringBuilder().append("\n");
		for (int i = 0; i < templates.size(); i++) {
			builder.append(templates.get(i).usage);
			if (i != templates.size() - 1) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}




	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		HashMap<String, Object> parameters = new HashMap<>();
		boolean foundCommand = false;

		for (CommandTemplate template : templates) {
			parameters.clear();
			if (parseTemplate(template, sender, args, parameters)) {
				template.listener.onCommand(template.templateId, parameters);
				foundCommand = true;
				break;
			}
		}

		if (!foundCommand) {
			throw new WrongUsageException(getUsage(sender), new Object[0]);
		}

	}




	/**
	 * @param template      the template
	 * @param sender        the sender of this command
	 * @param cmdArgs       the command arguments
	 * @param parametersOut the map to put parameters into
	 * @return whether the given arguments are valid for the given {@link CommandTemplate}
	 */
	private boolean parseTemplate(CommandTemplate template, ICommandSender sender, String[] cmdArgs, Map<String, Object> parametersOut) {

		if (template.tokens.length - 1 != cmdArgs.length) {
			return false;
		}

		if (template.tokens.length == 1 && cmdArgs.length == 0) {
			return true;
		}

		for (int i = 1; i < template.tokens.length; i++) {
			final String token = template.tokens[i];
			final String arg = cmdArgs[i - 1];

			if (token.startsWith("<") && token.endsWith(">")) {
				final String varToken = token.substring(1, token.length() - 1);
				final String varname = varToken.split(":")[0];
				final String strVartype = varToken.split(":")[1];
				final CommandTemplate.TokenVarType vartype = strVartype.startsWith("{") ?
						CommandTemplate.TokenVarType.ENUM : CommandTemplate.TokenVarType.valueOf(strVartype);

				switch (vartype) {
					case STRING: {
						parametersOut.put(varname, arg);
						break;
					}
					case COORDINATE: {
						Integer coordinate = parseBlockPos((sender == null ? 0 : sender.getPosition().getX()), arg);
						if (coordinate != null) {
							parametersOut.put(varname, coordinate);
						} else {
							return false;
						}
						break;
					}
					case INTEGER: {
						try {
							parametersOut.put(varname, Integer.parseInt(arg));
						} catch (NumberFormatException e) {
							return false;
						}
						break;
					}
					case FLOAT: {
						try {
							parametersOut.put(varname, Float.parseFloat(arg));
						} catch (NumberFormatException e) {
							return false;
						}
						break;
					}
					case BOOLEAN: {
						if ("true".equalsIgnoreCase(arg)) {
							parametersOut.put(varname, true);
							break;
						} else if ("false".equalsIgnoreCase(arg)) {
							parametersOut.put(varname, false);
							break;
						} else {
							return false;
						}
					}
					case ENUM: {
						String[] enumTokens = strVartype.substring(1, strVartype.length() - 1).split(",");
						boolean foundEnum = false;
						for (String et : enumTokens) {
							if (et.trim().equalsIgnoreCase(arg)) {
								parametersOut.put(varname, et);
								foundEnum = true;
							}
						}
						return foundEnum;
					}
					default: {
						throw new IllegalArgumentException("Unknown token-type: " + varToken.split(":")[1]);
					}
				}

			} else {
				if (!token.equalsIgnoreCase(arg)) {
					return false;
				}
			}

		}

		return true;
	}




	/**
	 * Converts the value in "input" to a blockpos-coordinate. If the input starts with "~", the result will be relative the the given origin
	 *
	 * @param origin the origin, if input is a relative value
	 * @param input  the coordinate as string. Starts with a "~" if it is a relative position.
	 * @return the result as an integer or null
	 */
	private static Integer parseBlockPos(int origin, String input) {
		try {
			if (input.startsWith("~")) {
				double value = parseDouble(input.substring(1));
				return BlockUtils.toBlockPos(value) + origin;
			} else {
				double value = parseDouble(input);
				return BlockUtils.toBlockPos(value);
			}
		} catch (NumberInvalidException e) {
			return null;
		}
	}


}
