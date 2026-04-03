package seedu.address.logic.onboarding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AssignTeamCommand;
import seedu.address.logic.commands.EnterEventCommand;
import seedu.address.logic.commands.SearchCommand;

public class OnboardingCoordinatorTest {

    @Test
    public void extractCommandWord_validInput_returnsFirstWord() {
        assertEquals(Optional.of("addevent"), OnboardingCoordinator.extractCommandWord("addevent n/Hi"));
        assertEquals(Optional.of("enter"), OnboardingCoordinator.extractCommandWord("enter event 1"));
    }

    @Test
    public void onCommandExecuted_wrongCommandOnSuccess_showsReminderWithoutAdvancing() {
        OnboardingCoordinator c = new OnboardingCoordinator();
        Optional<String> extra = c.onCommandExecuted("list", true);
        assertTrue(extra.isPresent());
        assertTrue(extra.get().contains("tutorial still expects"));
        assertFalse(c.isFlowFinishedInSession());
        extra = c.onCommandExecuted(AddEventCommand.COMMAND_WORD + " n/Test d/2026-01-01", true);
        assertTrue(extra.isPresent());
        assertTrue(extra.get().contains("Next"));
        assertFalse(c.isFlowFinishedInSession());
    }

    @Test
    public void onCommandExecuted_failure_showsStepReminder() {
        OnboardingCoordinator c = new OnboardingCoordinator();
        Optional<String> extra = c.onCommandExecuted("bad", false);
        assertTrue(extra.isPresent());
        assertTrue(extra.get().contains("Onboarding"));
    }

    @Test
    public void fullSequence_completesFlow() {
        OnboardingCoordinator c = new OnboardingCoordinator();
        assertFalse(c.onCommandExecuted(AddEventCommand.COMMAND_WORD + " n/E d/2026-01-01", true).isEmpty());
        assertFalse(c.onCommandExecuted(EnterEventCommand.COMMAND_WORD + " event 1", true).isEmpty());
        assertFalse(c.onCommandExecuted(AddCommand.COMMAND_WORD + " n/A p/1 e/a@a.com a/addr", true).isEmpty());
        assertFalse(c.onCommandExecuted(AssignTeamCommand.COMMAND_WORD + " 1 team/X", true).isEmpty());
        c.onCommandExecuted(SearchCommand.COMMAND_WORD + " A", true);
        assertTrue(c.isFlowFinishedInSession());
    }
}
