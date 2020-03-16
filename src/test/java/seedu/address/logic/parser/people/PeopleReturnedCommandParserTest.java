package seedu.address.logic.parser.people;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TRANSACTION_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.RETURNED_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.people.PeopleReturnedCommand;

public class PeopleReturnedCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PeopleReturnedCommand.MESSAGE_USAGE);

    private PeopleReturnedCommandParser parser = new PeopleReturnedCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // nothing specified
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // no person's index specified
        assertParseFailure(parser, "i/1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + RETURNED_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + RETURNED_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + RETURNED_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 p/ string" + RETURNED_DESC_AMY , MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid debt index
        assertParseFailure(parser, "1" + INVALID_TRANSACTION_INDEX, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noDebtIndexSpecified_success() {
        Index targetPersonIndex = INDEX_SECOND;
        String userInput = String.valueOf(targetPersonIndex.getOneBased());
        PeopleReturnedCommand expectedCommand = new PeopleReturnedCommand(targetPersonIndex, null);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetPersonIndex = INDEX_SECOND;
        Index targetDebtIndex = INDEX_FIRST;
        String userInput = targetPersonIndex.getOneBased() + " " + RETURNED_DESC_AMY;

        PeopleReturnedCommand expectedCommand = new PeopleReturnedCommand(targetPersonIndex, targetDebtIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
