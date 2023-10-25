package persistence;

import org.json.JSONObject;

// Interface that requires any class that implements it to be convertible to a JSON object
// Code heavily inspired by JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
