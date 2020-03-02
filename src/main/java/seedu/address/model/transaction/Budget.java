package seedu.address.model.transaction;

import java.util.Date;

public class Budget {

    private Amount budget;
    private Date resetDate;

    public Budget(Amount budget, Date resetDate) {
        this.budget = budget;
        this.resetDate = resetDate;
    }

    public Budget setBudget(Amount budget, Date resetDate) {
        return new Budget(budget, resetDate);
    }
}
