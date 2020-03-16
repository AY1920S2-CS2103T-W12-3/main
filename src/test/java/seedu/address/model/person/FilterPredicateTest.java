package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.FilterType.FILTER_BY_DEBT;
import static seedu.address.model.FilterType.FILTER_BY_LOAN;
import static seedu.address.testutil.TypicalDebts.MOVIE;
import static seedu.address.testutil.TypicalLoans.BREAKFAST;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FilterPredicateTest {

    @Test
    public void equals() {

        FilterPredicate filterDebtPredicate = new FilterPredicate(FILTER_BY_DEBT);
        FilterPredicate filterLoanPredicate = new FilterPredicate(FILTER_BY_LOAN);

        // same object -> returns true
        assertTrue(filterDebtPredicate.equals(filterDebtPredicate));
        assertTrue(filterLoanPredicate.equals(filterLoanPredicate));

        // same values -> returns true
        FilterPredicate filterDebtPredicateCopy = new FilterPredicate(FILTER_BY_DEBT);
        assertTrue(filterDebtPredicate.equals(filterDebtPredicateCopy));

        // different types -> returns false
        assertFalse(filterDebtPredicate.equals(1));

        // null -> returns false
        assertFalse(filterDebtPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(filterDebtPredicate.equals(filterLoanPredicate));
    }

    @Test
    public void test_filterDebt_returnsTrue() {
        FilterPredicate predicate = new FilterPredicate(FILTER_BY_DEBT);
        assertTrue(predicate.test(new PersonBuilder().withDebts(MOVIE).build()));
    }

    @Test
    public void test_filterDebt_returnsFalse() {
        FilterPredicate predicate = new FilterPredicate(FILTER_BY_DEBT);
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_filterLoan_returnsTrue() {
        FilterPredicate predicate = new FilterPredicate(FILTER_BY_LOAN);
        assertTrue(predicate.test(new PersonBuilder().withLoans(BREAKFAST).build()));
    }

    @Test
    public void test_filterLoan_returnsFalse() {
        FilterPredicate predicate = new FilterPredicate(FILTER_BY_LOAN);
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

}
