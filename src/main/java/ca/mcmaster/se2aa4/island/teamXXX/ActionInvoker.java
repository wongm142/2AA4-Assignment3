package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class ActionInvoker {
    private ActionCommand command;

    public void setCommand(ActionCommand command) {
        this.command = command;
    }

    public void executeCommand() {
        if (command instanceof ActionNoParam) {
            ((ActionNoParam) command).execute();
        } 

        else {
            throw new IllegalStateException("Invalid command type");
        }
    }

    public void executeCommand(Direction direction){
        if (command instanceof ActionWithParam) {
            ((ActionWithParam) command).execute(direction);
        }

        else {
            throw new IllegalStateException("Invalid command type");
        }
    }
}
