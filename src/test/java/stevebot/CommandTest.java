package stevebot;

import net.minecraft.command.CommandException;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stevebot.commands.CommandListener;
import stevebot.commands.CommandSystem;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandTest {


	@AfterEach
	void cleanup() {
		CommandSystem.clearCommands();
	}




	@Test
	void testSimpleCommand() {

		final boolean[] flag = new boolean[]{true};

		CommandSystem.addCommand("testCommand", "test command", "", (templateId, parameters) -> {
			if (flag[0]) {
				assertThat(templateId).isNotNull();
				assertThat(templateId).isEqualTo("testCommand");
				assertThat(parameters).isNotNull();
				assertThat(parameters.size()).isEqualTo(0);
			} else {
				Assertions.fail();
			}
		});

		try {
			CommandSystem.getCommand("test").execute(null, null, new String[]{"command"});
		} catch (CommandException e) {
			e.printStackTrace();
		}

		flag[0] = false;

		try {
			CommandSystem.getCommand("test").execute(null, null, new String[]{"something"});
		} catch (CommandException e) {
			e.printStackTrace();
		}

		try {
			CommandSystem.getCommand("test").execute(null, null, new String[]{"something", "else"});
		} catch (CommandException e) {
			e.printStackTrace();
		}

	}




	@Test
	void testMultipleCommand() {

		final int[] counter = new int[]{1};

		CommandSystem.addCommand("testCommand_0", "test a", "", (templateId, parameters) -> {
			if (counter[0] == 1) {
				assertThat(templateId).isNotNull();
				assertThat(templateId).isEqualTo("testCommand_0");
				assertThat(parameters).isNotNull();
				assertThat(parameters.size()).isEqualTo(0);
			} else {
				Assertions.fail();
			}
		});

		CommandSystem.addCommand("testCommand_1", "test b", "", (templateId, parameters) -> {
			if (counter[0] == 2) {
				assertThat(templateId).isNotNull();
				assertThat(templateId).isEqualTo("testCommand_1");
				assertThat(parameters).isNotNull();
				assertThat(parameters.size()).isEqualTo(0);
			} else {
				Assertions.fail();
			}
		});

		CommandSystem.addCommand("testCommand_1", "test b other", "", (templateId, parameters) -> {
			if (counter[0] == 3) {
				assertThat(templateId).isNotNull();
				assertThat(templateId).isEqualTo("testCommand_1");
				assertThat(parameters).isNotNull();
				assertThat(parameters.size()).isEqualTo(0);
			} else {
				Assertions.fail();
			}
		});

		try {
			CommandSystem.getCommand("test").execute(null, null, new String[]{"a"});
		} catch (CommandException e) {
			e.printStackTrace();
		}

		counter[0]++;

		try {
			CommandSystem.getCommand("test").execute(null, null, new String[]{"b"});
		} catch (CommandException e) {
			e.printStackTrace();
		}

		counter[0]++;

		try {
			CommandSystem.getCommand("test").execute(null, null, new String[]{"b", "other"});
		} catch (CommandException e) {
			e.printStackTrace();
		}
	}




	@Test
	void testCommandNumber() {

		final float VALUE = 1.526f;

		CommandSystem.addCommand("testCommand", "test <number:FLOAT>", "", (templateId, parameters) -> {
			assertThat(templateId).isNotNull();
			assertThat(templateId).isEqualTo("testCommand");
			assertThat(parameters).isNotNull();
			assertThat(parameters.size()).isEqualTo(1);
			assertThat(parameters.get("number")).isNotNull();
			assertThat(CommandListener.getAsFloat("number", parameters)).isCloseTo(VALUE, Percentage.withPercentage(0.01));
		});

		try {
			CommandSystem.getCommand("test").execute(null, null, new String[]{Float.toString(VALUE)});
		} catch (CommandException e) {
			e.printStackTrace();
		}

	}




	@Test
	void testCommandEnum() {

		final String ENUM_VALUE = "c";

		CommandSystem.addCommand("testCommand", "test <enum:{a,b,c,d}>", "", (templateId, parameters) -> {
			assertThat(templateId).isNotNull();
			assertThat(templateId).isEqualTo("testCommand");
			assertThat(parameters).isNotNull();
			assertThat(parameters.size()).isEqualTo(1);
			assertThat(parameters.get("enum")).isNotNull();
			assertThat(parameters.get("enum")).isEqualTo(ENUM_VALUE);
		});

		try {
			CommandSystem.getCommand("test").execute(null, null, new String[]{ENUM_VALUE});
		} catch (CommandException e) {
			e.printStackTrace();
		}

	}


}
