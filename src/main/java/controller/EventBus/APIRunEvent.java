package controller.EventBus;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class APIRunEvent {
    private MenuItem myRuns;
    private MenuItem apiRuns;
    private Label myRunText;
    private AnchorPane runList;

    public APIRunEvent(MenuItem myRuns, MenuItem apiRuns, Label myRunText, AnchorPane runList) {
        this.myRuns = myRuns;
        this.apiRuns = apiRuns;
        this.myRunText = myRunText;
        this.runList = runList;
    }

    public MenuItem getMyRuns() {
        return myRuns;
    }

    public MenuItem getApiRuns() {
        return apiRuns;
    }

    public Label getMyRunText() {
        return myRunText;
    }

    public AnchorPane getRunList() {
        return runList;
    }

    public void setMyRuns(MenuItem myRuns) {
        this.myRuns = myRuns;
    }

    public void setApiRuns(MenuItem apiRuns) {
        this.apiRuns = apiRuns;
    }

    public void setMyRunText(Label myRunText) {
        this.myRunText = myRunText;
    }

    public void setRunList(AnchorPane runList) {
        this.runList = runList;
    }
}
