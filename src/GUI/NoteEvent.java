package GUI;

import Domain.Nota;

public class NoteEvent implements Event {

    public Nota oldData;
    public Nota newData;
    public ChangeEventType type;

    public Nota getOldData() {
        return oldData;
    }

    public void setOldData(Nota oldData) {
        this.oldData = oldData;
    }

    public Nota getNewData() {
        return newData;
    }

    public void setNewData(Nota newData) {
        this.newData = newData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public void setType(ChangeEventType type) {
        this.type = type;
    }

    public NoteEvent(Nota oldData, Nota newData, ChangeEventType type) {
        this.oldData = oldData;
        this.newData = newData;
        this.type = type;
    }
}

