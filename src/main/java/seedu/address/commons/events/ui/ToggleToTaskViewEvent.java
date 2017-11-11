package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * Indicates a request to toggle the view to TaskPanel.
 */
public class ToggleToTaskViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
