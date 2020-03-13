package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests whether a {@code Person} has any debts or loans.
 */
public class FilterPredicate implements Predicate<Person> {

    private final String filter;

    public FilterPredicate(String filter) {
        this.filter = filter;
    }

    @Override
    public boolean test(Person person) {
        assert filter.equals("debt") || filter.equals("loan");
        if (filter.equals("debt")) {
            return !person.getDebts().asUnmodifiableObservableList().isEmpty();
        } else {
            // filter loans
            return !person.getLoans().asUnmodifiableObservableList().isEmpty();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterPredicate // instanceof handles nulls
                && filter.equals(((FilterPredicate) other).filter)); // state check
    }
}
