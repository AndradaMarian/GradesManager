package GUI;

import Domain.Student;

public class StudentEvent implements Event {

    public Student oldData;
    public Student newData;
    public ChangeEventType type;

    public Student getOldData() {
        return oldData;
    }

    public void setOldData(Student oldData) {
        this.oldData = oldData;
    }

    public Student getNewData() {
        return newData;
    }

    public void setNewData(Student newData) {
        this.newData = newData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public void setType(ChangeEventType type) {
        this.type = type;
    }

    public StudentEvent(Student oldData, Student newData, ChangeEventType type) {
        this.oldData = oldData;
        this.newData = newData;
        this.type = type;
    }
}
