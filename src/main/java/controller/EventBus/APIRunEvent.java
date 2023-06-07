package controller.EventBus;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public record APIRunEvent(MenuItem myRuns, MenuItem apiRuns, Label myRunText, AnchorPane runList) {
}
