package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import seedu.address.logic.statistics.StatisticsSummary;

/**
 * Panel containing summary statistics for event participants.
 */
public class StatisticsPanel extends UiPart<Region> {

    private static final String FXML = "StatisticsPanel.fxml";

    @FXML
    private TextArea eventSummaryText;

    @FXML
    private VBox rsvpLegendBox;

    @FXML
    private TextArea tagDistributionText;

    @FXML
    private HBox rsvpChartPlaceholder;

    @FXML
    private HBox checkinChartPlaceholder;

    @FXML
    private VBox checkinLegendBox;

    public StatisticsPanel() {
        super(FXML);
    }

    /**
     * Updates the statistics panel display from the given summary.
     */
    public void update(StatisticsSummary summary) {
        requireNonNull(summary);
        eventSummaryText.setText(new StringBuilder()
                .append("Total participants: ").append(summary.getTotalCount()).append("\n")
                .append("RSVP Yes: ").append(summary.getRsvpYesCount()).append("\n")
                .append("Check-ins: ").append(summary.getCheckedInCount()).append("\n")
                .append("Teams: ").append(summary.getTeamCount())
                .toString());

        updateRsvpLegend(summary);
        updateCheckinLegend(summary);

        updateRsvpChart(summary);
        updateCheckinChart(summary);

        if (summary.getTagCounts().isEmpty()) {
            tagDistributionText.setText("No tags found.");
        } else {
            StringBuilder tagsBuilder = new StringBuilder();
            summary.getTagCounts().forEach((tag, count) ->
                    tagsBuilder.append(formatMetric(tag, count, summary.getTagPercentage(tag))));
            tagDistributionText.setText(tagsBuilder.toString());
        }
    }

    private String formatMetric(String label, int count, double percentage) {
        return String.format("%s: %d (%.1f%%)%n", label, count, percentage);
    }

    private void updateRsvpLegend(StatisticsSummary summary) {
        rsvpLegendBox.getChildren().clear();

        addLegendRow(rsvpLegendBox, "legend-yes",
                String.format("Yes (%,.1f%%)", summary.getRsvpYesPercentage()));
        addLegendRow(rsvpLegendBox, "legend-no",
                String.format("No (%,.1f%%)", summary.getRsvpNoPercentage()));
        addLegendRow(rsvpLegendBox, "legend-pending",
                String.format("Pending (%,.1f%%)", summary.getRsvpPendingPercentage()));
    }

    private void addLegendRow(VBox container, String colorStyleClass, String text) {
        HBox row = new HBox(8);

        javafx.scene.shape.Rectangle colorBox = new javafx.scene.shape.Rectangle(14, 14);
        colorBox.getStyleClass().add(colorStyleClass);

        Label label = new Label(text);
        label.getStyleClass().add("stats-legend-label");
        row.getChildren().addAll(colorBox, label);
        container.getChildren().add(row);
    }

    private void updateCheckinLegend(StatisticsSummary summary) {
        checkinLegendBox.getChildren().clear();

        addLegendRow(checkinLegendBox, "legend-yes",
                String.format("Checked in (%.1f%%)", summary.getCheckedInPercentage()));
        addLegendRow(checkinLegendBox, "legend-no",
                String.format("Not checked in (%.1f%%)", summary.getNotCheckedInPercentage()));
    }

    private void updateRsvpChart(StatisticsSummary summary) {
        rsvpChartPlaceholder.getChildren().clear();

        if (summary.getTotalCount() == 0) {
            return;
        }

        PieChart rsvpChart = new PieChart();
        rsvpChart.setLegendVisible(false);
        rsvpChart.getStyleClass().add("donut-chart");

        // Always add slices in fixed order so colors match legend.
        rsvpChart.getData().add(new PieChart.Data("Yes", summary.getRsvpYesCount()));
        rsvpChart.getData().add(new PieChart.Data("No", summary.getRsvpNoCount()));
        rsvpChart.getData().add(new PieChart.Data("Pending", summary.getRsvpPendingCount()));

        if (!rsvpChart.getData().isEmpty()) {
            String percentageLabel = String.format("%.0f%%", summary.getRsvpYesPercentage());
            StackPane donut = createDonutContainer(rsvpChart, percentageLabel);
            rsvpChartPlaceholder.getChildren().add(donut);
        }
    }

    private void updateCheckinChart(StatisticsSummary summary) {
        checkinChartPlaceholder.getChildren().clear();

        if (summary.getTotalCount() == 0) {
            return;
        }

        PieChart checkinChart = new PieChart();
        checkinChart.setLegendVisible(false);
        checkinChart.getStyleClass().add("donut-chart");

        int checkedIn = summary.getCheckedInCount();
        int notCheckedIn = summary.getNotCheckedInCount();

        // Always add slices in fixed order so colors match legend.
        checkinChart.getData().add(new PieChart.Data("Checked in", checkedIn));
        checkinChart.getData().add(new PieChart.Data("Not checked in", notCheckedIn));

        if (!checkinChart.getData().isEmpty()) {
            String percentageLabel = String.format("%.0f%%", summary.getCheckedInPercentage());
            StackPane donut = createDonutContainer(checkinChart, percentageLabel);
            checkinChartPlaceholder.getChildren().add(donut);
        }
    }

    private StackPane createDonutContainer(PieChart chart, String centerText) {
        StackPane container = new StackPane();
        container.setPrefSize(180, 180);
        container.getChildren().add(chart);

        double holeRadius = 50;
        Circle hole = new Circle(holeRadius);
        hole.setFill(Color.web("#2b2b2b"));

        Label label = new Label(centerText);
        label.getStyleClass().add("donut-center-label");

        container.getChildren().addAll(hole, label);

        RotateTransition spin = new RotateTransition(Duration.seconds(4), chart);
        spin.setFromAngle(0);
        spin.setToAngle(360);
        spin.setCycleCount(1);
        spin.setAutoReverse(false);
        spin.playFromStart();

        return container;
    }
}
