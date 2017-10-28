package seedu.address.logic.commands.person;

import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_PINNED;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PinPersonEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Unpins a person identified using the list of pinned person from the address book.
 */
public class UnpinCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unpins the person identified by the index number in the pinned person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPIN_PERSON_SUCCESS = "Unpinned Person: %1$s";

    private final Index targetIndex;

    public UnpinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> pinnedList = model.getFilteredPersonList().filtered(PREDICATE_SHOW_ONLY_PINNED);

        if (targetIndex.getZeroBased() >= pinnedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnpin = pinnedList.get(targetIndex.getZeroBased());

        if (!personToUnpin.isPinned()) {
            throw new CommandException(Messages.MESSAGE_PERSON_ALREADY_UNPINNED);
        }

        try {
            model.unpinPerson(personToUnpin);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        EventsCenter.getInstance().post(new PinPersonEvent());
        return new CommandResult(String.format(MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnpinCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnpinCommand) other).targetIndex)); // state check
    }
}
