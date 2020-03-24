package seedu.address.logic.commands.wallet;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWallet.getTypicalWallet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.wallet.WalletEditCommand.EditTransactionDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.Wallet;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Income;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class WalletEditCommandTest {

    @Test
    public void execute_incomeAllFieldsSpecifiedUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalWallet(), new UserPrefs());

        Income editedTransaction = new TransactionBuilder().buildIncome(); //returns 1 income obj
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
        //stores edited income obj
        WalletEditCommand walletEditCommand = new WalletEditCommand(INDEX_THIRD_PERSON, descriptor); //edit with
        // command -> copy from descrip.

        String expectedMessage = String.format(WalletEditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalWallet(), new UserPrefs());

        expectedModel.setIncome(model.getFilteredIncomeList().get(0), editedTransaction); //add edited income back

        assertCommandSuccess(walletEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_expenseAllFieldsSpecifiedUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalWallet(), new UserPrefs());
        Expense editedTransaction = new TransactionBuilder().buildExpense();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction).build();
        WalletEditCommand walletEditCommand = new WalletEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(WalletEditCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new Wallet(model.getWallet()),
                new UserPrefs());
        expectedModel.setExpense(model.getFilteredExpenseList().get(0), editedTransaction);

        assertCommandSuccess(walletEditCommand, model, expectedMessage, expectedModel);
    }

}
