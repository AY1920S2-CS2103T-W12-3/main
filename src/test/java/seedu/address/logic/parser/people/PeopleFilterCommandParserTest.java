package seedu.address.logic.parser.people;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.FilterType.FILTER_BY_DEBT;
import static seedu.address.model.FilterType.FILTER_BY_LOAN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.people.PeopleFilterCommand;
import seedu.address.model.person.FilterPredicate;

public class PeopleFilterCommandParserTest {

    private PeopleFilterCommandParser parser = new PeopleFilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PeopleFilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PeopleFilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "test", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PeopleFilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        PeopleFilterCommand debtFilterCommand =
                new PeopleFilterCommand(new FilterPredicate(FILTER_BY_DEBT));
        PeopleFilterCommand loanFilterCommand =
                new PeopleFilterCommand(new FilterPredicate(FILTER_BY_LOAN));
        assertParseSuccess(parser, "debt", debtFilterCommand);
        assertParseSuccess(parser, "loan", loanFilterCommand);

        // with whitespaces
        assertParseSuccess(parser, " debt ", debtFilterCommand);
        assertParseSuccess(parser, " loan ", loanFilterCommand);
    }
}
