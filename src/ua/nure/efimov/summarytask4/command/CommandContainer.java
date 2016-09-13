package ua.nure.efimov.summarytask4.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;

/**
 * Container for all commands.
 * 
 * @author Alexandr Efimov
 *
 */
public class CommandContainer {
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	/**
	 * Storage for commands.
	 */
	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		LOG.trace("CommandContainer init started");

		/**
		 * Common commands.
		 */
		commands.put(ComonConstants.COMMAND_NOT_COMMAND.getValue(), new NoSuchCommand());
		commands.put(ComonConstants.COMMAND_LANGUAGE.getValue(), new LanguageCommand());
		commands.put(ComonConstants.COMMAND_LOGOUT.getValue(), new LogoutCommand());
		commands.put(ComonConstants.COMMAND_SUBCJECT_CHOOSE.getValue(), new SubjectChooseCommand());
		commands.put(ComonConstants.COMMAND_START_TEST.getValue(), new StartTestCommand());
		commands.put(ComonConstants.COMMAND_STOP_TEST.getValue(), new StopTestCommand());
		commands.put(ComonConstants.COMMAND_CHECK_RESULT.getValue(), new CheckResultCommand());
		commands.put(ComonConstants.COMMAND_MY_PAGE.getValue(), new MyPageCommand());
		commands.put(ComonConstants.COMMAND_CHANGE_SORT_TYPE.getValue(), new ChangeSortTypeCommand());
		commands.put(ComonConstants.COMMAND_REGISTRATION.getValue(), new RegistrationCommand());

		commands.put(ComonConstants.TESTS_MODIFY_COMMAND.getValue(), new TestsModifyCommand());
		commands.put(ComonConstants.MODIFY_TEST_COMMAND.getValue(), new ModifyOneTestCommand());
		commands.put(ComonConstants.MODIFY_USERS_COMMAND.getValue(), new ModifyUsersCommand());
		commands.put(ComonConstants.MAIL_CONFIRM_COMMAND.getValue(), new MailVerifyCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("All commands --> " + commands);
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            is name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}
}
