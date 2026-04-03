package seedu.address.logic.onboarding;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGN_TEAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.logic.commands.EnterEventCommand;
import seedu.address.logic.commands.SearchCommand;

/**
 * Tracks first-launch onboarding steps and produces supplemental UI messages.
 * Command matching uses the first word of input (same convention as {@link seedu.address.logic.parser.AddressBookParser}).
 */
public class OnboardingCoordinator {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final int TOTAL_STEPS = 5;

    private OnboardingStep currentStep = OnboardingStep.ADD_EVENT;
    private boolean flowFinishedInSession;

    /**
     * Shown once when the main window loads and onboarding is still active.
     */
    public String getWelcomeMessage() {
        return "Welcome! This short tutorial walks you through five commands.\n\n"
                + currentStepReminder();
    }

    /**
     * Reminder for the current step (used after parse/execution errors).
     */
    public String getCurrentStepReminder() {
        return currentStepReminder();
    }

    private String currentStepReminder() {
        return "Onboarding — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n" + currentStep.getInstruction();
    }

    /**
     * @param commandText raw user input
     * @param commandSucceeded whether parsing and execution succeeded
     * @return supplemental text to append under the command feedback (empty if none)
     */
    public Optional<String> onCommandExecuted(String commandText, boolean commandSucceeded) {
        if (flowFinishedInSession) {
            return Optional.empty();
        }
        if (!commandSucceeded) {
            return Optional.of(currentStepReminder());
        }

        Optional<String> commandWordOpt = extractCommandWord(commandText);
        if (commandWordOpt.isEmpty()) {
            return Optional.of(currentStepReminder());
        }
        String commandWord = commandWordOpt.get();

        if (!currentStep.matchesCommandWord(commandWord)) {
            return Optional.of("You've completed another action, but the tutorial still expects this step:\n"
                    + currentStepReminder());
        }

        switch (currentStep) {
        case ADD_EVENT:
            currentStep = OnboardingStep.ENTER_EVENT;
            return Optional.of("Event created.\n\nNext — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                    + currentStep.getInstruction());
        case ENTER_EVENT:
            currentStep = OnboardingStep.ADD_PARTICIPANT;
            return Optional.of("You're now viewing this event's participants.\n\n"
                    + "Next — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                    + currentStep.getInstruction());
        case ADD_PARTICIPANT:
            currentStep = OnboardingStep.ASSIGN_TEAM;
            return Optional.of("Participant added.\n\nNext — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                    + currentStep.getInstruction());
        case ASSIGN_TEAM:
            currentStep = OnboardingStep.SEARCH;
            return Optional.of("Team assigned.\n\nLast step — Step " + currentStep.stepNumber() + "/" + TOTAL_STEPS + ":\n"
                    + currentStep.getInstruction());
        case SEARCH:
            flowFinishedInSession = true;
            return Optional.of("Tutorial complete! You're ready to use the app.\n"
                    + "(You can dismiss this message with your next command.)");
        default:
            return Optional.empty();
        }
    }

    public boolean isFlowFinishedInSession() {
        return flowFinishedInSession;
    }

    /**
     * Extracts the first word of trimmed input, if any.
     */
    public static Optional<String> extractCommandWord(String userInput) {
        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            return Optional.empty();
        }
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmed);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(matcher.group("commandWord"));
    }

    private enum OnboardingStep {
        ADD_EVENT(AddEventCommand.COMMAND_WORD) {
            @Override
            String getInstruction() {
                return "Create an event:\n"
                        + AddEventCommand.COMMAND_WORD + " "
                        + PREFIX_NAME + "Orientation Day "
                        + PREFIX_DATE + "2026-08-20 "
                        + PREFIX_LOCATION + "COM1 "
                        + PREFIX_DESCRIPTION + "Welcome session";
            }
        },
        ENTER_EVENT(EnterEventCommand.COMMAND_WORD) {
            @Override
            String getInstruction() {
                return "Open that event's participant list:\n"
                        + EnterEventCommand.COMMAND_WORD + " event 1\n"
                        + "(Use the index shown in the event list if it is not 1.)";
            }
        },
        ADD_PARTICIPANT(AddCommand.COMMAND_WORD) {
            @Override
            String getInstruction() {
                return "Add someone to this event:\n"
                        + AddCommand.COMMAND_WORD + " "
                        + PREFIX_NAME + "Jane Doe "
                        + "p/91234567 e/jane@example.com a/Blk 123";
            }
        },
        ASSIGN_TEAM(AssignTeamCommand.COMMAND_WORD) {
            @Override
            String getInstruction() {
                return "Put them on a team:\n"
                        + AssignTeamCommand.COMMAND_WORD + " 1 " + PREFIX_ASSIGN_TEAM + "Alpha";
            }
        },
        SEARCH(SearchCommand.COMMAND_WORD) {
            @Override
            String getInstruction() {
                return "Try searching by keyword (matches name, phone, email, etc.):\n"
                        + SearchCommand.COMMAND_WORD + " Jane";
            }
        };

        private final String expectedCommandWord;

        OnboardingStep(String expectedCommandWord) {
            this.expectedCommandWord = expectedCommandWord;
        }

        int stepNumber() {
            return ordinal() + 1;
        }

        boolean matchesCommandWord(String commandWord) {
            return expectedCommandWord.equals(commandWord);
        }

        abstract String getInstruction();
    }
}
