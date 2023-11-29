package model.eventlog;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


// Represents a log of expense report events such as
// creating an expense report, adding/deleting expenses, and saving
// Inspired heavily by AlarmSystem:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
public class EventLog implements Iterable<Event> {
    /** the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<Event> events;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    // EFFECTS: constructs an EventLog with an empty list of events
    private EventLog() {
        events = new ArrayList<Event>();
    }

    /**
     * Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     * @return  instance of EventLog
     */
    // MODIFIES: this
    // EFFECTS: returns the single instance of EventLog,
    //          constructs it if first time accessing
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    /**
     * Adds an event to the event log.
     * @param e the event to be added
     */
    // MODIFIES: this
    // EFFECTS: adds event e to the log
    public void logEvent(Event e) {
        events.add(e);
    }

    /**
     * Clears the event log and logs the event.
     */
    // MODIFIES: this
    // EFFECTS: clears the event Log
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns an iterable over all events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}

