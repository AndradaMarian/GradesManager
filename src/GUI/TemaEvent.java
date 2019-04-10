package GUI;

import Domain.Tema;

public class TemaEvent implements Event{
    public Tema oldData;
    public Tema newData;
    public ChangeEventType type;

    public TemaEvent(Tema oldData, Tema newData, ChangeEventType type) {
        this.oldData = oldData;
        this.newData = newData;
        this.type = type;
    }

    public Tema getOldData() {
        return oldData;
    }

    public void setOldData(Tema oldData) {
        this.oldData = oldData;
    }

    public Tema getNewData() {
        return newData;
    }

    public void setNewData(Tema newData) {
        this.newData = newData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public void setType(ChangeEventType type) {
        this.type = type;
    }
}
