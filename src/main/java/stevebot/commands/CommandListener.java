package stevebot.commands;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;

import java.util.Map;

public interface CommandListener {


	void onCommand(String templateId, Map<String, Object> parameters);


	static BaseBlockPos getAsBaseBlockPos(String nameX, String nameY, String nameZ, Map<String, Object> parameters) {
		return getAsFastBlockPos(nameX, nameY, nameZ, parameters);
	}


	static BaseBlockPos getAsFastBlockPos(String nameX, String nameY, String nameZ, Map<String, Object> parameters) {
		final Integer x = (Integer) parameters.get(nameX);
		final Integer y = (Integer) parameters.get(nameY);
		final Integer z = (Integer) parameters.get(nameZ);
		return new FastBlockPos(x, y, z);
	}

	static String getAsString(String nameValue, Map<String, Object> parameters) {
		return (String) parameters.get(nameValue);
	}

	static int getAsInt(String nameValue, Map<String, Object> parameters) {
		return (int) parameters.get(nameValue);
	}

	static float getAsFloat(String nameValue, Map<String, Object> parameters) {
		return (float) parameters.get(nameValue);
	}

	static boolean getAsBoolean(String nameValue, Map<String, Object> parameters) {
		return (boolean) parameters.get(nameValue);
	}

}
