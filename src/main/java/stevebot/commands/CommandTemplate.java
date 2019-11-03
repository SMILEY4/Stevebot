package stevebot.commands;

public class CommandTemplate {


	public enum TokenVarType {
		STRING,
		COORDINATE,
		INTEGER,
		FLOAT,
		BOOLEAN,
		ENUM
	}






	public final String templateId;
	public final String[] tokens;
	public final CommandListener listener;
	public final String usage;




	public CommandTemplate(String templateId, String template, String usage, CommandListener listener) {
		this(templateId, template.split(" "), usage, listener);
	}




	public CommandTemplate(String templateId, String[] tokens, String usage, CommandListener listener) {
		this.templateId = templateId;
		this.tokens = tokens;
		this.listener = listener;
		this.usage = usage;
	}

}
