package seedu.address.logic.parser.people;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TRANSACTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEND_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LEND_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.people.PeopleLendCommand;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Loan;

public class PeopleLendCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PeopleLendCommand.MESSAGE_USAGE);
    private PeopleLendCommandParser parser = new PeopleLendCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY + " " + PREFIX_NAME + VALID_DESC_AMY + " "
                + PREFIX_AMOUNT + VALID_AMOUNT_AMY, MESSAGE_INVALID_FORMAT);

        // no description specified
        assertParseFailure(parser, "1 " + PREFIX_AMOUNT + VALID_AMOUNT_AMY, MESSAGE_INVALID_FORMAT);

        // no amount specified
        assertParseFailure(parser, "1 " + PREFIX_NAME + VALID_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no index and description specified
        assertParseFailure(parser, PREFIX_AMOUNT + VALID_AMOUNT_AMY, MESSAGE_INVALID_FORMAT);

        // no index and amount specified
        assertParseFailure(parser, PREFIX_NAME + VALID_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no description and amount specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index, description and amount specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_NAME_AMY + " " + PREFIX_NAME + VALID_DESC_AMY + " "
                + PREFIX_AMOUNT + VALID_AMOUNT_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_NAME_AMY + " " + PREFIX_NAME + VALID_DESC_AMY + " "
                + PREFIX_AMOUNT + VALID_AMOUNT_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + VALID_NAME_AMY + " " + PREFIX_NAME
                + VALID_DESC_AMY + " " + PREFIX_AMOUNT + VALID_AMOUNT_AMY, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + VALID_NAME_AMY + " " + PREFIX_NAME + VALID_DESC_AMY + " "
                + PREFIX_AMOUNT + VALID_AMOUNT_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // valid description, invalid amount without the optional date
        assertParseFailure(parser, "1 " + PREFIX_NAME + VALID_DESC_AMY + " " + INVALID_AMOUNT_DESC,
                Amount.MESSAGE_CONSTRAINTS);
        // valid description, valid amount with invalid date
        assertParseFailure(parser,
                "1 " + PREFIX_NAME + VALID_DESC_AMY + " " + PREFIX_AMOUNT + VALID_AMOUNT_AMY + INVALID_DATE_DESC,
                Date.MESSAGE_CONSTRAINTS);
        // valid description, invalid amount with valid date
        assertParseFailure(parser, "1 " + PREFIX_NAME + VALID_DESC_AMY + INVALID_AMOUNT_DESC + VALID_DATE_AMY,
                Amount.MESSAGE_CONSTRAINTS);
        // valid description, invalid amount and invalid date
        assertParseFailure(parser, "1 " + PREFIX_NAME + VALID_DESC_AMY + INVALID_AMOUNT_DESC + INVALID_DATE_DESC,
                Amount.MESSAGE_CONSTRAINTS);

        // invalid description, invalid amount without the optional date
        assertParseFailure(parser, "1" + INVALID_TRANSACTION_DESC + " " + INVALID_AMOUNT_DESC,
                Description.MESSAGE_CONSTRAINTS);
        // invalid description, valid amount with invalid date
        assertParseFailure(parser,
                "1" + INVALID_TRANSACTION_DESC + " " + PREFIX_AMOUNT + VALID_AMOUNT_AMY + INVALID_DATE_DESC,
                Description.MESSAGE_CONSTRAINTS);
        // invalid description, invalid amount with valid date
        assertParseFailure(parser, "1" + INVALID_TRANSACTION_DESC + INVALID_AMOUNT_DESC + VALID_DATE_AMY,
                Description.MESSAGE_CONSTRAINTS);
        // invalid description, invalid amount and invalid date
        assertParseFailure(parser, "1" + INVALID_TRANSACTION_DESC + INVALID_AMOUNT_DESC + INVALID_DATE_DESC,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + LEND_DESC_AMY;

        Loan loan = new Loan(new Description(VALID_DESC_AMY),
                new Amount(Double.parseDouble(VALID_AMOUNT_AMY)),
                new Date(LocalDate.parse(VALID_DATE_AMY,
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        PeopleLendCommand expectedCommand = new PeopleLendCommand(targetIndex, loan);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_dateNotSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + LEND_DESC_BOB;

        Loan loan = new Loan(new Description(VALID_DESC_BOB),
                new Amount(Double.parseDouble(VALID_AMOUNT_BOB)),
                Date.getDefault());
        PeopleLendCommand expectedCommand = new PeopleLendCommand(targetIndex, loan);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
