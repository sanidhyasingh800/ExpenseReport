package model.eventlog;

import java.util.Calendar;
import java.util.Date;


// Represents an event with its own date and description
// Inspired heavily by AlarmSystem:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    /**
     * Creates an event with the given description
     * and the current date/time stamp.
     * @param description  a description of the event
     */
    // EFFECTS: constructs a new event with the current date/time and given description
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /**
     * Gets the date of this event (includes time).
     * @return  the date of the event
     */
    // EFFECTS; returns the date
    public Date getDate() {
        return dateLogged;
    }

    /**
     * Gets the description of this event.
     * @return  the description of the event
     */
    // EFFECTS: returns the description
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns true if two events are the same, false otherwise:
    //          occurs when date and description of the two events are the same
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }


    //EFFECTS: returns the hashcode for an event
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns each event in a fixed string format:
    //          date.toString()
    //          description
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}

