package seedu.address.commons.core;

import java.util.Arrays;
import java.util.Optional;

/**
 * Supported application theme modes.
 */
public enum ThemeMode {
    DARK("Dark", "DarkTheme.css"),
    LIGHT("Light", "LightTheme.css");

    private final String displayName;
    private final String stylesheetFileName;

    ThemeMode(String displayName, String stylesheetFileName) {
        this.displayName = displayName;
        this.stylesheetFileName = stylesheetFileName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getStylesheetFileName() {
        return stylesheetFileName;
    }

    /**
     * Returns the matching theme mode for user input, ignoring case.
     */
    public static Optional<ThemeMode> fromUserInput(String value) {
        return Arrays.stream(values())
                .filter(mode -> mode.name().equalsIgnoreCase(value)
                        || mode.displayName.equalsIgnoreCase(value))
                .findFirst();
    }
}
