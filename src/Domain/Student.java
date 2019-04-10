package Domain;


import java.util.Objects;

/**
The Student class
 */
public class Student implements HasID<String> {
    String idStudent;
    String nume;
    String grupa;
    String email;

    /**
The method returns a Student Object with its attributes:
    String id : The unique registration number;
    String nume: The name of the student (it should be made of first and last name);
    String grupa: The group which the student takes part of;
    String email: The student email;
    String cadruLaborator: The student's teacher;
 * @param idStudent The unique registration number
 * @param nume The name of the student (it should be made of first and last name)
 * @param grupa The group which the student takes part of
 * @param email
 * The student email
 */
    public Student(String idStudent, String nume, String grupa, String email) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
    }
    /**
    The method returns the registration number of a student
     * @return
     * The ID of the Student
     */
    public String getIdStudent() {
        return idStudent;
    }
    /**
    The method sets the registration number for the student
     * @param idStudent
     * The registration number
     */
    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }
    /**
     The method returns the name of a student
     * @return -The name
     */
    public String getNume() {
        return nume;
    }
    /**
     The method sets the name for a student
     * @param nume
     * The name of the student
     */

    public void setNume(String nume) {
        this.nume = nume;
    }
    /**
     The method returns the group of a student
     * @return -The group
     */
    public String getGrupa() {
        return grupa;
    }
    /**
     The method sets the group for a student
     * @param grupa
     * -The group for a student
     */

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }
    /**
     The method returns the email of a student
     * @return -The email
     */
    public String getEmail() {
        return email;
    }
    /**
     The method sets the email for a student
     * @param email
     * The email for a student
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     The method returns the registration number of a student as the ID when called through the interface
     */

    @Override
    public String getID() {
        return this.getIdStudent();
    }
    /**
     The method sets the registration number of a student as the ID when called through the interface
     */
    @Override
    public void setID(String s) {
        this.setIdStudent(s);
    }
    /**
     The method returns a string made of all the attributes of a Student
     */
    @Override
    public String toString() {
        return "Student{" +
                "idStudent='" + idStudent + '\'' +
                ", nume='" + nume + '\'' +
                ", grupa='" + grupa + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(idStudent, student.idStudent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudent);
    }
}
