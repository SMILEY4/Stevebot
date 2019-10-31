package stevebot.commands;

public class CommandTemplate {


	public enum TokenVarType {
		STRING,
		COORDINATE,
		INTEGER,
		FLOAT,
		BOOLEAN
	}






	public final String templateId;
	public final String[] tokens;
	public final CommandListener listener;




	public CommandTemplate(String templateId, String template, CommandListener listener) {
		this(templateId, template.split(" "), listener);
	}




	public CommandTemplate(String templateId, String[] tokens, CommandListener listener) {
		this.templateId = templateId;
		this.tokens = tokens;
		this.listener = listener;
	}

}
