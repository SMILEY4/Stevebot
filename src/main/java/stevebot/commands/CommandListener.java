package stevebot.commands;

import java.util.Map;

public interface CommandListener {


	void onCommand(String templateId, Map<String, Object> parameter);

}
