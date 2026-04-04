package seedu.address.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.MainApp;
import seedu.address.commons.core.ThemeMode;

/**
 * Applies the selected theme stylesheet to UI roots that expose a stylesheet list.
 */
public final class ThemeStyles {

    private static final String VIEW_STYLESHEET_FOLDER = "/view/";
    private static final List<String> THEME_STYLESHEETS = Arrays.stream(ThemeMode.values())
            .map(ThemeStyles::resolveThemeStylesheet)
            .toList();

    private ThemeStyles() {}

    /**
     * Replaces any existing app theme stylesheet with the selected theme.
     */
    public static void applyTheme(ObservableList<String> stylesheets, ThemeMode themeMode) {
        Objects.requireNonNull(stylesheets);
        Objects.requireNonNull(themeMode);

        stylesheets.removeAll(THEME_STYLESHEETS);
        stylesheets.add(0, resolveThemeStylesheet(themeMode));
    }

    private static String resolveThemeStylesheet(ThemeMode themeMode) {
        return Objects.requireNonNull(
                MainApp.class.getResource(VIEW_STYLESHEET_FOLDER + themeMode.getStylesheetFileName()))
                .toExternalForm();
    }
}
