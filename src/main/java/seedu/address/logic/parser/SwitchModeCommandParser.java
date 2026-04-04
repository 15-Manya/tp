package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.ThemeMode;
import seedu.address.logic.commands.SwitchModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SwitchModeCommand}.
 */
public class SwitchModeCommandParser implements Parser<SwitchModeCommand> {

    @Override
    public SwitchModeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || trimmedArgs.split("\\s+").length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchModeCommand.MESSAGE_USAGE));
        }

        ThemeMode targetThemeMode = ThemeMode.fromUserInput(trimmedArgs)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchModeCommand.MESSAGE_USAGE)));

        return new SwitchModeCommand(targetThemeMode);
    }
}
