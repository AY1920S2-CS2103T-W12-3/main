package seedu.address.logic.commands.people;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FilterPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code PeopleFilterCommand}.
 */
public class PeopleFilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FilterPredicate debtFilterPredicate = new FilterPredicate("debt");
        FilterPredicate loanFilterPredicate = new FilterPredicate("loan");

        PeopleFilterCommand debtFilterCommand = new PeopleFilterCommand(debtFilterPredicate);
        PeopleFilterCommand loanFilterCommand = new PeopleFilterCommand(loanFilterPredicate);

        // same object -> returns true
        assertTrue(debtFilterCommand.equals(debtFilterCommand));
        assertTrue(loanFilterCommand.equals(loanFilterCommand));

        // same values -> returns true
        PeopleFilterCommand debtFilterCommandCopy = new PeopleFilterCommand(debtFilterPredicate);
        assertTrue(debtFilterCommand.equals(debtFilterCommandCopy));

        // different types -> returns false
        assertFalse(debtFilterCommand.equals(1));

        // null -> returns false
        assertFalse(debtFilterCommand.equals(null));

        // different filters -> returns false
        assertFalse(debtFilterCommand.equals(loanFilterCommand));
    }

    @Test
    public void execute_filter_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6);
        FilterPredicate debtFilterPredicate = new FilterPredicate("debt");
        PeopleFilterCommand filterDebtCommand = new PeopleFilterCommand(debtFilterPredicate);
        expectedModel.updateFilteredPersonList(debtFilterPredicate);
        assertCommandSuccess(filterDebtCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());

        FilterPredicate loanFilterPredicate = new FilterPredicate("loan");
        PeopleFilterCommand filterLoanCommand = new PeopleFilterCommand(loanFilterPredicate);
        expectedModel.updateFilteredPersonList(loanFilterPredicate);
        assertCommandSuccess(filterLoanCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }
}
