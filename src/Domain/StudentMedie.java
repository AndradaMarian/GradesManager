package Domain;

public class StudentMedie {
    Student student;
    Double medie;

    public StudentMedie(Student student, Double medie) {
        this.student = student;
        this.medie = medie;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getMedie() {
        return medie;
    }

    public void setMedie(Double medie) {
        this.medie = medie;
    }
    public String getNumeStudent(){

        return student.getNume();
    }
    public String getStringMedie(){

        return medie.toString();
    }
    public String getGrupaStudent(){
        return student.getGrupa();
    }
}
