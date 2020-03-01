package seedu.address.model.transaction;

public class Budget {

    private Amount budget;

    public Budget(Amount budget) {
        this.budget = budget;
    }

    public Budget setBudget(Amount budget) {
        return new Budget(budget);
    }
}
