package seedu.address.logic.commands.wallet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWallet.getTypicalWallet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.Transaction;

public class WalletDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalWallet(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {

        Transaction transactionToDelete =
                model.getFilteredTransactionList().get(INDEX_FIRST.getZeroBased());
        WalletDeleteCommand walletDeleteCommand = new WalletDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(WalletDeleteCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getWallet(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(walletDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        WalletDeleteCommand walletDeleteCommand = new WalletDeleteCommand(outOfBoundsIndex);

        assertCommandFailure(walletDeleteCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTransactionAtIndex(model, INDEX_FIRST);

        Transaction transactionToDelete =
                model.getFilteredTransactionList().get(INDEX_FIRST.getZeroBased());
        WalletDeleteCommand walletDeleteCommand = new WalletDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(WalletDeleteCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getWallet(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);
        showNoTransaction(expectedModel);

        assertCommandSuccess(walletDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTransactionAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWallet().getTransactionList().size());

        WalletDeleteCommand walletDeleteCommand = new WalletDeleteCommand(outOfBoundIndex);

        assertCommandFailure(walletDeleteCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        WalletDeleteCommand deleteFirstCommand = new WalletDeleteCommand(INDEX_FIRST);
        WalletDeleteCommand deleteSecondCommand = new WalletDeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        WalletDeleteCommand deleteFirstCommandCopy = new WalletDeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no transaction.
     */
    private void showNoTransaction(Model model) {
        model.updateFilteredTransactionList(p -> false);

        assertTrue(model.getFilteredTransactionList().isEmpty());
    }
}
