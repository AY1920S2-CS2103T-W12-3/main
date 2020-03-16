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
import seedu.address.model.transaction.Loan;
import seedu.address.testutil.PersonBuilder;

public class PeopleReceivedCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListReceivedAll_success() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person receivedAllPerson = lastShownList.get(INDEX_SECOND.getZeroBased());
        Amount totalLoan = receivedAllPerson.getLoans().getTotal();
        Person removedLoanPerson = new PersonBuilder(receivedAllPerson).withLoans().build();

        PeopleReceivedCommand peopleReceivedCommand = new PeopleReceivedCommand(INDEX_SECOND, null);

        String expectedMessage = String.format(PeopleReceivedCommand.MESSAGE_RECEIVED_SUCCESS,
                removedLoanPerson.getName(), totalLoan, removedLoanPerson.getLoans().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(1), removedLoanPerson);

        assertCommandSuccess(peopleReceivedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListReceivedAll_success() {

        showPersonAtIndex(model, INDEX_SECOND);

        Person receivedAllPerson = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Amount totalLoan = receivedAllPerson.getLoans().getTotal();
        Person removedLoanPerson = new PersonBuilder(receivedAllPerson).withLoans().build();

        PeopleReceivedCommand peopleReceivedCommand = new PeopleReceivedCommand(INDEX_FIRST, null);

        String expectedMessage = String.format(PeopleReceivedCommand.MESSAGE_RECEIVED_SUCCESS,
                removedLoanPerson.getName(), totalLoan, removedLoanPerson.getLoans().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), removedLoanPerson);

        assertCommandSuccess(peopleReceivedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListReceivedByIndex_success() {

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personUserLend = lastShownList.get(INDEX_THIRD.getZeroBased());

        List<Loan> loans = personUserLend.getLoans().asUnmodifiableObservableList();
        Amount loanReduced = loans.get(0).getAmount();
        Loan[] reducedLoans = new Loan[loans.size() - 1];

        for (int i = 0; i < reducedLoans.length; i++) {
            reducedLoans[i] = loans.get(i + 1);
        }

        Person reducedLoanPerson = new PersonBuilder(personUserLend).withLoans(reducedLoans).build();

        PeopleReceivedCommand peopleReceivedCommand = new PeopleReceivedCommand(INDEX_THIRD,
                INDEX_FIRST);

        String expectedMessage = String.format(PeopleReceivedCommand.MESSAGE_RECEIVED_SUCCESS,
                reducedLoanPerson.getName(), loanReduced, reducedLoanPerson.getLoans().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(2), reducedLoanPerson);

        assertCommandSuccess(peopleReceivedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListReceivedByIndex_success() {

        showPersonAtIndex(model, INDEX_THIRD);

        Person personUserLend = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());

        List<Loan> loans = personUserLend.getLoans().asUnmodifiableObservableList();
        Amount loanReduced = loans.get(0).getAmount();
        Loan[] reducedLoans = new Loan[loans.size() - 1];

        for (int i = 0; i < reducedLoans.length; i++) {
            reducedLoans[i] = loans.get(i + 1);
        }

        Person reducedLoanPerson = new PersonBuilder(personUserLend).withLoans(reducedLoans).build();

        PeopleReceivedCommand peopleReceivedCommand = new PeopleReceivedCommand(INDEX_FIRST, INDEX_FIRST);

        String expectedMessage = String.format(PeopleReceivedCommand.MESSAGE_RECEIVED_SUCCESS,
                reducedLoanPerson.getName(), loanReduced, reducedLoanPerson.getLoans().getTotal());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), reducedLoanPerson);

        assertCommandSuccess(peopleReceivedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PeopleReceivedCommand peopleReceivedCommand = new PeopleReceivedCommand(outOfBoundIndex, null);
        assertCommandFailure(peopleReceivedCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLoanIndexUnfilteredList_failure() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personUserLend = lastShownList.get(INDEX_SECOND.getZeroBased());
        Index outOfBoundIndex =
                Index.fromOneBased(personUserLend.getLoans().asUnmodifiableObservableList().size() + 1);
        PeopleReceivedCommand peopleReceivedCommand = new PeopleReceivedCommand(INDEX_SECOND, outOfBoundIndex);
        assertCommandFailure(peopleReceivedCommand, model, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noLoan_failure() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person receivedAllPerson = lastShownList.get(INDEX_FIRST.getZeroBased());
        Person removedLoanPerson = new PersonBuilder(receivedAllPerson).withLoans().build();

        PeopleReceivedCommand peopleReceivedCommand = new PeopleReceivedCommand(INDEX_FIRST, null);

        String expectedMessage = String.format(PeopleReceivedCommand.MESSAGE_NO_LOAN, removedLoanPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), removedLoanPerson);

        assertCommandFailure(peopleReceivedCommand, model, expectedMessage);
    }

    @Test
    public void equals() {

        final PeopleReceivedCommand standardCommand =
                new PeopleReceivedCommand(INDEX_SECOND, INDEX_FIRST);

        final PeopleReceivedCommand receiveAllCommand =
                new PeopleReceivedCommand(INDEX_SECOND, null);

        // same values -> returns true
        PeopleReceivedCommand commandWithSameValues =
                new PeopleReceivedCommand(INDEX_SECOND, INDEX_FIRST);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same values with null Loan index
        PeopleReceivedCommand receiveAllCommandWithSameValue =
                new PeopleReceivedCommand(INDEX_SECOND, null);
        assertTrue(receiveAllCommand.equals(receiveAllCommandWithSameValue));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same object -> returns true
        assertTrue(receiveAllCommand.equals(receiveAllCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new PeopleClearCommand()));

        // different person index -> returns false
        assertFalse(standardCommand.equals(new PeopleReceivedCommand(INDEX_THIRD, INDEX_FIRST)));

        // different loan index -> returns false
        assertFalse(standardCommand.equals(new PeopleReceivedCommand(INDEX_SECOND, INDEX_SECOND)));

        // no loan index -> returns false
        assertFalse(standardCommand.equals(new PeopleReceivedCommand(INDEX_THIRD, null)));
    }

}

