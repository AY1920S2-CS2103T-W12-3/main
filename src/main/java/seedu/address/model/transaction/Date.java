package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Date in Sharkie.
 * Guarantees: immutable; date is valid as declared in {@link #isValidDate(String)}
 */

public class Date {

    public static final String DATE_PATTERN = "dd/MM/uuuu";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String MESSAGE_CONSTRAINTS = "Dates should be in form: " + DATE_FORMAT;

    private LocalDate date;

    /**
     * Constructs a valid Date object.
     * @param date A valid date.
     */
    public Date(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    /**
     * Returns true if a given String is a valid date format.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, DateTimeFormatter.ofPattern(DATE_PATTERN).withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    /**
     * Returns a default date set to today's date.
     */
    public static Date getDefault() {
        return new Date(LocalDate.now());
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the String of the date in "dd/MM/yyyy" format.
     */
    public String getInputFormat() {
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Date
                && date.toString().equals(((Date) other).toString()));
    }
}
