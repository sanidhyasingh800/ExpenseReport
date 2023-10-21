package persistence;

import org.json.JSONObject;

// Interface that requires any class that implements it to be convertible to a JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
