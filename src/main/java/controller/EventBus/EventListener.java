package controller.EventBus;


import com.google.common.eventbus.Subscribe;

public class EventListener {

    private boolean myRunTurns = false;

    @Subscribe
    public void APIRunClicked(APIRunEvent apiRunEvent){
        System.out.println("Subscriber take api event");
        apiRunEvent.getMyRuns().setDisable(false);
        apiRunEvent.getApiRuns().setDisable(true);
        myRunTurns = false;
        apiRunEvent.getMyRunText().setText("");
        apiRunEvent.getRunList().getChildren().clear();
    }

    @Subscribe
    public void MyRunClicked(MyRunEvent myRunEvent){
        System.out.println("Subscriber take no api event");
        myRunEvent.getMyRuns().setDisable(true);
        myRunEvent.getApiRuns().setDisable(false);
        myRunTurns = true;
        myRunEvent.getMyRunText().setText("My runs");
        myRunEvent.getRunList().getChildren().clear();
    }

    public boolean isMyRunTurns() {
        return myRunTurns;
    }
}
