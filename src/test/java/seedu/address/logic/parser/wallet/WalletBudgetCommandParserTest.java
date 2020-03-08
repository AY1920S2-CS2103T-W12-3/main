package seedu.address.logic.parser.wallet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.wallet.WalletBudgetCommand;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;

public class WalletBudgetCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, WalletBudgetCommand.MESSAGE_USAGE);
    private WalletBudgetCommandParser parser = new WalletBudgetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no arguments
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no amount
        assertParseFailure(parser, PREFIX_DATE + VALID_DATE_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArguments_failure() {
        // invalid amount, no date
        assertParseFailure(parser, INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        // invalid amount, valid date
        assertParseFailure(parser, INVALID_AMOUNT_DESC + VALID_DATE_DESC,
                Amount.MESSAGE_CONSTRAINTS);

        // valid amount, invalid date
        assertParseFailure(parser, VALID_AMOUNT_DESC + INVALID_DATE_DESC,
                Date.MESSAGE_CONSTRAINTS);

        // invalid amount, invalid date
        assertParseFailure(parser, INVALID_AMOUNT_DESC + INVALID_DATE_DESC,
                Amount.MESSAGE_CONSTRAINTS);
    }
}