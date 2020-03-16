package seedu.address.logic.commands.people;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUser.getTypicalUserData;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class PeopleRemindCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_emptyUserData_failure() {

        PeopleRemindCommand peopleRemindCommand = new PeopleRemindCommand(INDEX_SECOND);

        assertCommandFailure(peopleRemindCommand, model, Messages.MESSAGE_EMPTY_USER_DATA);
    }

    @Test
    public void execute_unfilteredList_success() {
        model.setUserData(getTypicalUserData());
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personUserReminds = lastShownList.get(INDEX_SECOND.getZeroBased());

        PeopleRemindCommand peopleRemindCommand = new PeopleRemindCommand(INDEX_SECOND);

        String expectedMessage = String.format(PeopleRemindCommand.MESSAGE_REMIND_SUCCESS,
                personUserReminds.getName(), personUserReminds.getLoans().getTotal());

        assertCommandSuccess(peopleRemindCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        model.setUserData(getTypicalUserData());
        Person personUserReminds = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());

        PeopleRemindCommand peopleRemindCommand = new PeopleRemindCommand(INDEX_SECOND);

        String expectedMessage = String.format(PeopleRemindCommand.MESSAGE_REMIND_SUCCESS,
                personUserReminds.getName(), personUserReminds.getLoans().getTotal());

        assertCommandSuccess(peopleRemindCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personWithZeroDebt_failure() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personUserReminds = lastShownList.get(INDEX_FIRST.getZeroBased());

        PeopleRemindCommand peopleRemindCommand = new PeopleRemindCommand(INDEX_FIRST);

        String expectedMessage = String.format("%1$s does not owe you money :(",
                personUserReminds.getName());

        assertCommandFailure(peopleRemindCommand, model, expectedMessage);
    }

    @Test
    public void equals() {

        final PeopleRemindCommand standardCommand = new PeopleRemindCommand(INDEX_SECOND);

        // same values -> returns true
        PeopleRemindCommand commandWithSameValues = new PeopleRemindCommand(INDEX_SECOND);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new PeopleClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new PeopleRemindCommand(INDEX_THIRD)));
    }

}
