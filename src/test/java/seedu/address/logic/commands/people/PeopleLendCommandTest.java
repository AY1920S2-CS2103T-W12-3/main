package seedu.address.logic.commands.people;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLoans.SHOPPING;
import static seedu.address.testutil.TypicalLoans.TRAVEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PeopleLendCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personUserLends = lastShownList.get(INDEX_FIRST.getZeroBased());
        Person addedLoanPerson = new PersonBuilder(personUserLends).withLoans(SHOPPING).build();

        PeopleLendCommand peopleLendCommand = new PeopleLendCommand(INDEX_FIRST, SHOPPING);

        String expectedMessage = String.format(PeopleLendCommand.MESSAGE_LEND_SUCCESS,
                addedLoanPerson.getName(), SHOPPING.getAmount(), addedLoanPerson.getLoans().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), addedLoanPerson);

        assertCommandSuccess(peopleLendCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {

        showPersonAtIndex(model, INDEX_FIRST);

        Person personUserLends = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person addedLoanPerson = new PersonBuilder(personUserLends).withLoans(SHOPPING).build();

        PeopleLendCommand peopleLendCommand = new PeopleLendCommand(INDEX_FIRST, SHOPPING);

        String expectedMessage = String.format(PeopleLendCommand.MESSAGE_LEND_SUCCESS,
                addedLoanPerson.getName(), SHOPPING.getAmount(), addedLoanPerson.getLoans().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), addedLoanPerson);

        assertCommandSuccess(peopleLendCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PeopleLendCommand peopleLendCommand = new PeopleLendCommand(outOfBoundIndex, SHOPPING);
        assertCommandFailure(peopleLendCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PeopleLendCommand peopleLendCommand = new PeopleLendCommand(outOfBoundIndex, SHOPPING);

        assertCommandFailure(peopleLendCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {

        final PeopleLendCommand standardCommand = new PeopleLendCommand(INDEX_FIRST, SHOPPING);

        // same values -> returns true
        PeopleLendCommand commandWithSameValues = new PeopleLendCommand(INDEX_FIRST, SHOPPING);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new PeopleClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new PeopleLendCommand(INDEX_SECOND, SHOPPING)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new PeopleLendCommand(INDEX_FIRST, TRAVEL)));
    }

}

