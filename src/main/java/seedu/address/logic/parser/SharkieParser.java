package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliPrefix.GLOBAL_COMMAND_TYPE;
import static seedu.address.logic.parser.CliPrefix.PEOPLE_COMMAND_TYPE;
import static seedu.address.logic.parser.CliPrefix.WALLET_COMMAND_TYPE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.global.ExitCommand;
import seedu.address.logic.commands.global.HelpCommand;
import seedu.address.logic.commands.people.PeopleAddCommand;
import seedu.address.logic.commands.people.PeopleClearCommand;
import seedu.address.logic.commands.people.PeopleDeleteCommand;
import seedu.address.logic.commands.people.PeopleEditCommand;
import seedu.address.logic.commands.people.PeopleFilterCommand;
import seedu.address.logic.commands.people.PeopleFindCommand;
import seedu.address.logic.commands.people.PeopleLendCommand;
import seedu.address.logic.commands.people.PeopleListCommand;
import seedu.address.logic.commands.people.PeopleOweCommand;
import seedu.address.logic.commands.people.PeopleReceivedCommand;
import seedu.address.logic.commands.people.PeopleRemindAllCommand;
import seedu.address.logic.commands.people.PeopleRemindCommand;
import seedu.address.logic.commands.people.PeopleReturnedCommand;
import seedu.address.logic.commands.wallet.WalletBudgetCommand;
import seedu.address.logic.commands.wallet.WalletDeleteCommand;
import seedu.address.logic.commands.wallet.WalletEditCommand;
import seedu.address.logic.commands.wallet.WalletExpenseCommand;
import seedu.address.logic.commands.wallet.WalletFindCommand;
import seedu.address.logic.commands.wallet.WalletIncomeCommand;
import seedu.address.logic.commands.wallet.WalletListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.people.PeopleAddCommandParser;
import seedu.address.logic.parser.people.PeopleDeleteCommandParser;
import seedu.address.logic.parser.people.PeopleEditCommandParser;
import seedu.address.logic.parser.people.PeopleFilterCommandParser;
import seedu.address.logic.parser.people.PeopleFindCommandParser;
import seedu.address.logic.parser.people.PeopleLendCommandParser;
import seedu.address.logic.parser.people.PeopleOweCommandParser;
import seedu.address.logic.parser.people.PeopleReceivedCommandParser;
import seedu.address.logic.parser.people.PeopleRemindCommandParser;
import seedu.address.logic.parser.people.PeopleReturnedCommandParser;
import seedu.address.logic.parser.wallet.WalletBudgetCommandParser;
import seedu.address.logic.parser.wallet.WalletDeleteCommandParser;
import seedu.address.logic.parser.wallet.WalletEditCommandParser;
import seedu.address.logic.parser.wallet.WalletExpenseCommandParser;
import seedu.address.logic.parser.wallet.WalletFindCommandParser;
import seedu.address.logic.parser.wallet.WalletIncomeCommandParser;

/**
 * Parses user input.
 */
public class SharkieParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandType>\\S+ )?(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandType = matcher.group("commandType");
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandType == GLOBAL_COMMAND_TYPE) {
            return getGlobalCommand(commandWord, arguments);
        }

        switch (commandType.trim()) {
        case PEOPLE_COMMAND_TYPE:
            return getPeopleCommand(commandWord, arguments);
        case WALLET_COMMAND_TYPE:
            return getWalletCommand(commandWord, arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }

    private Command getPeopleCommand(String commandWord, String arguments) throws ParseException {
        if (commandWord == null) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        switch (commandWord) {
        case PeopleAddCommand.COMMAND_WORD:
            return new PeopleAddCommandParser().parse(arguments);

        case PeopleEditCommand.COMMAND_WORD:
            return new PeopleEditCommandParser().parse(arguments);

        case PeopleDeleteCommand.COMMAND_WORD:
            return new PeopleDeleteCommandParser().parse(arguments);

        case PeopleClearCommand.COMMAND_WORD:
            return new PeopleClearCommand();

        case PeopleFindCommand.COMMAND_WORD:
            return new PeopleFindCommandParser().parse(arguments);

        case PeopleListCommand.COMMAND_WORD:
            return new PeopleListCommand();

        case PeopleOweCommand.COMMAND_WORD:
            return new PeopleOweCommandParser().parse(arguments);

        case PeopleFilterCommand.COMMAND_WORD:
            return new PeopleFilterCommandParser().parse(arguments);

        case PeopleReturnedCommand.COMMAND_WORD:
            return new PeopleReturnedCommandParser().parse(arguments);

        case PeopleLendCommand.COMMAND_WORD:
            return new PeopleLendCommandParser().parse(arguments);

        case PeopleReceivedCommand.COMMAND_WORD:
            return new PeopleReceivedCommandParser().parse(arguments);

        case PeopleRemindCommand.COMMAND_WORD:
            return new PeopleRemindCommandParser().parse(arguments);

        case PeopleRemindAllCommand.COMMAND_WORD:
            return new PeopleRemindAllCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command getWalletCommand(String commandWord, String arguments) throws ParseException {
        if (commandWord == null) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        switch (commandWord) {
        case WalletBudgetCommand.COMMAND_WORD:
            return new WalletBudgetCommandParser().parse(arguments);
        case WalletExpenseCommand.COMMAND_WORD:
            return new WalletExpenseCommandParser().parse(arguments);

        case WalletIncomeCommand.COMMAND_WORD:
            return new WalletIncomeCommandParser().parse(arguments);

        case WalletDeleteCommand.COMMAND_WORD:
            return new WalletDeleteCommandParser().parse(arguments);

        case WalletListCommand.COMMAND_WORD:
            return new WalletListCommand();

        case WalletEditCommand.COMMAND_WORD:
            return new WalletEditCommandParser().parse(arguments);

        case WalletFindCommand.COMMAND_WORD:
            return new WalletFindCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command getGlobalCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
