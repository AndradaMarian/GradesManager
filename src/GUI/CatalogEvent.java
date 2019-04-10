package GUI;

public class CatalogEvent implements Event{
    public NoteEvent noteEvent ;
    public StudentEvent studentEvent;
    public TemaEvent temaEvent;
    public UtilizatorEvent utilizatorEvent;
    public EntityType changeEventType;


    public CatalogEvent(StudentEvent studentEvent, TemaEvent temaEvent, NoteEvent noteEvent, EntityType changeEventType, UtilizatorEvent utilizatorEvent) {
        this.noteEvent = noteEvent;
        this.studentEvent = studentEvent;
        this.temaEvent = temaEvent;
        this.changeEventType = changeEventType;
        this.utilizatorEvent=utilizatorEvent;
    }

    public NoteEvent getNoteEvent() {
        return noteEvent;
    }

    public void setNoteEvent(NoteEvent noteEvent) {
        this.noteEvent = noteEvent;
    }

    public StudentEvent getStudentEvent() {
        return studentEvent;
    }

    public void setStudentEvent(StudentEvent studentEvent) {
        this.studentEvent = studentEvent;
    }

    public TemaEvent getTemaEvent() {
        return temaEvent;
    }

    public void setTemaEvent(TemaEvent temaEvent) {
        this.temaEvent = temaEvent;
    }

    public EntityType getChangeEventType() {
        return changeEventType;
    }

    public void setChangeEventType(EntityType changeEventType) {
        this.changeEventType = changeEventType;
    }

    public UtilizatorEvent getUtilizatorEvent() {
        return utilizatorEvent;
    }

    public void setUtilizatorEvent(UtilizatorEvent utilizatorEvent) {
        this.utilizatorEvent = utilizatorEvent;
    }
}
