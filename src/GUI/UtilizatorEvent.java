package GUI;

import GUI.Event;
import Profiles.Utilizator;

public class UtilizatorEvent implements Event {
    public Utilizator oldData;
    public Utilizator newData;
    ChangeEventType changeEventType;

    public Utilizator getOldData() {
        return oldData;
    }

    public void setOldData(Utilizator oldData) {
        this.oldData = oldData;
    }

    public Utilizator getNewData() {
        return newData;
    }

    public void setNewData(Utilizator newData) {
        this.newData = newData;
    }

    public ChangeEventType getChangeEventType() {
        return changeEventType;
    }

    public void setChangeEventType(ChangeEventType changeEventType) {
        this.changeEventType = changeEventType;
    }

    public UtilizatorEvent(Utilizator oldData, Utilizator newData, ChangeEventType changeEventType) {
        this.oldData = oldData;
        this.newData = newData;
        this.changeEventType = changeEventType;
    }
}
