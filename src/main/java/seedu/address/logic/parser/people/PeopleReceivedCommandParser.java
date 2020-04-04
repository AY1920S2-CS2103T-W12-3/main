package seedu.address.logic.parser.people;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.people.PeopleReceivedCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PeoplePaidCommand object
 */
public class PeopleReceivedCommandParser implements Parser<PeopleReceivedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PeoplePaidCommand
     * and returns a PeoplePaidCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PeopleReceivedCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TRANSACTION_INDEX);

        Index personIndex;
        Index loanIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (argMultimap.getValue(PREFIX_TRANSACTION_INDEX).isPresent()) {
            try {
                loanIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TRANSACTION_INDEX).get());
            } catch (ParseException pe) {
                throw new ParseException(MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
            }
        } else {
            loanIndex = null;
        }

        return new PeopleReceivedCommand(personIndex, loanIndex);
    }
}

