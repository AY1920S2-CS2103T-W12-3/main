package seedu.address.logic.commands.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliPrefix.PEOPLE_COMMAND_TYPE;
import static seedu.address.logic.parser.CliPrefix.WALLET_COMMAND_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Budget;

public class WalletBudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a budget for the user. "
            + "Parameters: "
            + PREFIX_AMOUNT + "<amount> "
            + "[" + PREFIX_DATE + "<date:dd/mm/yyyy>]\n"
            + "Example: " + WALLET_COMMAND_TYPE + " "
            + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "1000 "
            + PREFIX_DATE + "10/10/2020 \n";

    public static final String MESSAGE_SUCCESS = "Budget has been set at %1$s.";

    private final Budget budget;

    public WalletBudgetCommand(Budget budget) {
        requireNonNull(budget);
        this.budget = budget;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setBudget(budget);

        return new CommandResult(String.format(MESSAGE_SUCCESS, budget.getAmount()));
    }
}