package seedu.address.model.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's Description in the Wallet.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Descriptions should not be blank";

    public final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test != null && !test.trim().isEmpty();
    }


    @Override
    public String toString() {
        return description;
    }

}
