package Domain;

/**
 * Nota class
 */
public class Nota implements HasID<String> {
    Student student;
    Tema tema;
    String idNota;
    String predata;
    String deadline;
    String feedback;
    String nota;


    /**
     * @param student
     * studentul care a fost evaluat
     * @param tema
     * tema care a fost evaluata
     * @param predata
     * saptamana in care a fost evaluata tema
     * @param deadline
     * ultima zi pentru tema
     * @param feedback
     * feedback ul profesorului
     * @param nota
     * nota acordata
     */
    public Nota(Student student, Tema tema, String predata, String deadline, String feedback, String nota) {
        this.student = student;
        this.tema= tema;
        this.nota=nota;
        this.predata = predata;
        this.deadline = deadline;
        this.feedback = feedback;
        idNota =student.getIdStudent()+"$"+tema.getIdTema();
    }

    public String getNumeStudent(){
        return student.getNume();

    }
    public String getGrupaStudent(){
        return student.getGrupa();

    }

    /**
     * @param idNota
     * modifica ID-ul unic al notei compus din id ul temei si al studentului
     */
    public void setIdNota(String idNota) {
        this.idNota = idNota;
    }

    /**
     * @param nota
     * modifica nota
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * @return
     * returneaza ID-ul unic al notei compus din id ul temei si al studentului
     */
    @Override
    public String getID() {
        return getIdNota();
    }

    /**
     * @param ID
     * modifica ID-ul unic al notei compus din id ul temei si al studentului
     */
    @Override
    public void setID(String ID) {
        idNota =ID;

    }

    /**
     * @return
     * returneaza ID-ul unic al notei compus din id ul temei si al studentului
     */
    public String getIdNota() {
        return idNota;
    }

    /**
     * @return
     * returneaza nota
     */
    public String getNota() {
        return nota;
    }


    /**
     * @return
     * returneaza entitatea student
     */
    public Student getStudent() {
        return student;
    }


    /**
     * @return
     * returneaza entitatea tema
     */
    public Tema getTema() {
        return tema;
    }


    /**
     * @return
     * returneaza saptamana in care a fost predata tema
     */
    public String getPredata() {
        return predata;
    }

    /**
     * @param predata
     * modifica saptamana in care a fost predata tema
     */
    public void setPredata(String predata) {
        this.predata = predata;
    }

    /**
     * @return
     * returneaza deadline-ul pentru tema
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * @param deadline
     * modifica deadline-ul pentru tema
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * @return
     * returneaza feedback-ul pentru tema
     */
    public Integer getIDTemaIntreg()
        {return Integer.valueOf(getIdTema());}
    public String getFeedback() {
        return feedback;
    }

    /**
     * @param feedback
     * modifica feedback-ul temei
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * @return
     * un string cu identitatea nota
     */
    @Override
    public String toString() {
        return  "Tema:" + tema.getIdTema() +'\n'+
                "Nota"+nota+'\n'+
                "Predata in saptamana" + predata + '\n' +

                "Deadline='" + deadline + '\n' +
                "Feedback='" + feedback + '\n' ;
    }
    public String getIdTema(){
        return tema.getIdTema();
    }
}
