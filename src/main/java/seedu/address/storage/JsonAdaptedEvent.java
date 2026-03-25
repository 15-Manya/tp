package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;

/**
 * Jackson-friendly version of {@link Event}
 */
class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    private final String name;
    private final String date;
    private final String location;
    private final String description;

    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("date") String date,
                            @JsonProperty("location") String location,
                            @JsonProperty("description") String description) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public JsonAdaptedEvent(Event source) {
        name = source.getName().fullName;
        date = source.getDate().toString();
        location = source.getLocation().orElse(null);
        description = source.getDescription().orElse(null);
    }

    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName()));
        }
        if (!EventName.isValidEventName(name)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        if (date == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDate.class.getSimpleName()));
        }
        if (!EventDate.isValidEventDate(date)) {
            throw new IllegalValueException(EventDate.MESSAGE_CONSTRAINTS);
        }
        return new Event(new EventName(name), new EventDate(date),
                Optional.ofNullable(location), Optional.ofNullable(description));
    }
}
