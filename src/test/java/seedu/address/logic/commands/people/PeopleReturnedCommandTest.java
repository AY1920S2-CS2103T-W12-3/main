package seedu.address.logic.commands.people;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
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
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Debt;
import seedu.address.testutil.PersonBuilder;

class PeopleReturnedCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListPaidAll_success() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person returnedAllPerson = lastShownList.get(INDEX_SECOND.getZeroBased());
        Amount totalDebt = returnedAllPerson.getDebts().getTotal();
        Person removedDebtPerson = new PersonBuilder(returnedAllPerson).withDebts().build();

        PeopleReturnedCommand peopleReturnedCommand = new PeopleReturnedCommand(INDEX_SECOND, null);

        String expectedMessage = String.format(PeopleReturnedCommand.MESSAGE_RETURNED_SUCCESS,
                removedDebtPerson.getName(), totalDebt, removedDebtPerson.getDebts().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(1), removedDebtPerson);

        assertCommandSuccess(peopleReturnedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListPaidAll_success() {

        showPersonAtIndex(model, INDEX_SECOND);

        Person returnedAllPerson = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Amount totalDebt = returnedAllPerson.getDebts().getTotal();
        Person removedDebtPerson = new PersonBuilder(returnedAllPerson).withDebts().build();

        PeopleReturnedCommand peopleReturnedCommand = new PeopleReturnedCommand(INDEX_FIRST, null);

        String expectedMessage = String.format(PeopleReturnedCommand.MESSAGE_RETURNED_SUCCESS,
                removedDebtPerson.getName(), totalDebt, removedDebtPerson.getDebts().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), removedDebtPerson);

        assertCommandSuccess(peopleReturnedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListPaidByIndex_success() {

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personUserOwe = lastShownList.get(INDEX_THIRD.getZeroBased());

        List<Debt> debts = personUserOwe.getDebts().asUnmodifiableObservableList();
        Amount debtReduced = debts.get(0).getAmount();
        Debt[] reducedDebts = new Debt[debts.size() - 1];

        for (int i = 0; i < reducedDebts.length; i++) {
            reducedDebts[i] = debts.get(i + 1);
        }

        Person reducedDebtPerson = new PersonBuilder(personUserOwe).withDebts(reducedDebts).build();

        PeopleReturnedCommand peopleReturnedCommand = new PeopleReturnedCommand(INDEX_THIRD,
                INDEX_FIRST);

        String expectedMessage = String.format(PeopleReturnedCommand.MESSAGE_RETURNED_SUCCESS,
                reducedDebtPerson.getName(), debtReduced, reducedDebtPerson.getDebts().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(2), reducedDebtPerson);

        assertCommandSuccess(peopleReturnedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListPaidByIndex_success() {

        showPersonAtIndex(model, INDEX_THIRD);

        Person personUserOwe = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());

        List<Debt> debts = personUserOwe.getDebts().asUnmodifiableObservableList();
        Amount debtReduced = debts.get(0).getAmount();
        Debt[] reducedDebts = new Debt[debts.size() - 1];

        for (int i = 0; i < reducedDebts.length; i++) {
            reducedDebts[i] = debts.get(i + 1);
        }

        Person reducedDebtPerson = new PersonBuilder(personUserOwe).withDebts(reducedDebts).build();

        PeopleReturnedCommand peopleReturnedCommand = new PeopleReturnedCommand(INDEX_FIRST, INDEX_FIRST);

        String expectedMessage = String.format(PeopleReturnedCommand.MESSAGE_RETURNED_SUCCESS,
                reducedDebtPerson.getName(), debtReduced, reducedDebtPerson.getDebts().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), reducedDebtPerson);

        assertCommandSuccess(peopleReturnedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PeopleReturnedCommand peopleReturnedCommand = new PeopleReturnedCommand(outOfBoundIndex, null);
        assertCommandFailure(peopleReturnedCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidDebtIndexUnfilteredList_failure() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personUserOwe = lastShownList.get(INDEX_SECOND.getZeroBased());
        Index outOfBoundIndex =
                Index.fromOneBased(personUserOwe.getDebts().asUnmodifiableObservableList().size() + 1);
        PeopleReturnedCommand peopleReturnedCommand = new PeopleReturnedCommand(INDEX_SECOND, outOfBoundIndex);
        assertCommandFailure(peopleReturnedCommand, model, Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noDebt_failure() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person returnedAllPerson = lastShownList.get(INDEX_FIRST.getZeroBased());
        Person removedDebtPerson = new PersonBuilder(returnedAllPerson).withDebts().build();

        PeopleReturnedCommand peopleReturnedCommand = new PeopleReturnedCommand(INDEX_FIRST, null);

        String expectedMessage = String.format(PeopleReturnedCommand.MESSAGE_NO_DEBT, removedDebtPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), removedDebtPerson);

        assertCommandFailure(peopleReturnedCommand, model, expectedMessage);
    }

    @Test
    public void equals() {

        final PeopleReturnedCommand standardCommand =
                new PeopleReturnedCommand(INDEX_SECOND, INDEX_FIRST);

        final PeopleReturnedCommand returnAllCommand =
                new PeopleReturnedCommand(INDEX_SECOND, null);

        // same values -> returns true
        PeopleReturnedCommand commandWithSameValues =
                new PeopleReturnedCommand(INDEX_SECOND, INDEX_FIRST);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same values with null Debt index
        PeopleReturnedCommand returnAllCommandWithSameValue =
                new PeopleReturnedCommand(INDEX_SECOND, null);
        assertTrue(returnAllCommand.equals(returnAllCommandWithSameValue));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same object -> returns true
        assertTrue(returnAllCommand.equals(returnAllCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new PeopleClearCommand()));

        // different person index -> returns false
        assertFalse(standardCommand.equals(new PeopleReturnedCommand(INDEX_THIRD, INDEX_FIRST)));

        // different debt index -> returns false
        assertFalse(standardCommand.equals(new PeopleReturnedCommand(INDEX_SECOND, INDEX_SECOND)));

        // no debt index -> returns false
        assertFalse(standardCommand.equals(new PeopleReturnedCommand(INDEX_THIRD, null)));
    }

}
