package controller.EventBus;


import com.google.common.eventbus.Subscribe;

public class EventListener {

    private boolean myRunTurns = false;

    @Subscribe
    public void APIRunClicked(APIRunEvent apiRunEvent){
        System.out.println("Subscriber take api event");
        apiRunEvent.myRuns().setDisable(false);
        apiRunEvent.apiRuns().setDisable(true);
        myRunTurns = false;
        apiRunEvent.myRunText().setText("");
        apiRunEvent.runList().getChildren().clear();
    }

    @Subscribe
    public void MyRunClicked(MyRunEvent myRunEvent){
        System.out.println("Subscriber take no api event");
        myRunEvent.myRuns().setDisable(true);
        myRunEvent.apiRuns().setDisable(false);
        myRunTurns = true;
        myRunEvent.myRunText().setText("My runs");
        myRunEvent.runList().getChildren().clear();
    }

    public boolean isMyRunTurns() {
        return myRunTurns;
    }
}
