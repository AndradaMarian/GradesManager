package Service;

import Domain.Student;

import Repository.RepositoryStudenti;
import Validation.FileException;
import Validation.StudentValidation;
import Validation.ValidationException;
import Validation.Validator;

import java.util.ArrayList;

/**
 * * Clasa retine o un obiect de tipul Repo si un obiect de tipul validator
 *  * Mediaza relatiile dintre UI si Repo
 */
public class StudentService {
    public RepositoryStudenti repositoryStudenti;
    public Validator<Student> validator=new StudentValidation();

    public StudentService(RepositoryStudenti repositoryStudenti) {
        this.repositoryStudenti = repositoryStudenti;
    }

    /**
     * @throws ValidationException
     * in cazul in care elementele continute apar in duplicate sau nu sunt valide
     * @throws FileException
     * in cazul in care fisierul are probleme(la deschidere sau de pattern)
     */

    public Iterable<Student>getStudentiNume(String nume){
        ArrayList<Student>l=new ArrayList<>();
        repositoryStudenti.findAll().forEach(x->{
            if(x.getNume().equals(nume)){
                l.add(x);
            }
        });
        return l;
    }
    public Iterable<Student>getStudentiPartialNume(String nume){
        ArrayList<Student>l=new ArrayList<>();
        repositoryStudenti.findAll().forEach(x->{
            if(x.getNume().contains(nume)){
                l.add(x);
            }
        });
        return l;
    }

    /**
     * @return
     * o lista cu toti studentii
     */
    public Iterable<Student> getStudenti(){
        return repositoryStudenti.findAll();
    }

    /**
     * @param s1 id-ul Studentului
     * @param s2 Numele Studentului
     * @param s3 Grupa Studentului
     * @param s4 Email-ul Studentului
     * @throws ValidationException
     * in cazul in care Studentul exista deja
     */
    public void addStudent(String s1,String s2,String s3,String s4) throws Exception {
        Student s=new Student(s1,s2,s3,s4);
        validator.validate(s);
        if(repositoryStudenti.save(s)!=null)
        {
            throw new ValidationException("Studentul exista deja");
        }

    }
    public Student update(String id, String nume, String grupa, String email) throws ValidationException {

        Student s=repositoryStudenti.findOne(id);
        if(s==null){
            throw new ValidationException("Studentul nu exista");
        }
        s.setNume(nume);
        s.setGrupa(grupa);
        s.setEmail(email);
        return repositoryStudenti.update(s);
    }

    /**
     * @param id id-ul Studentului
     * @return o entitate de tipul Student
     */
    public Student find(String id){
        return repositoryStudenti.findOne(id);
    }
    public Student deleteStudent(String id) throws ValidationException {
        Student s=repositoryStudenti.delete(id);
        if(s==null){
            throw new ValidationException("Studentul nu exista");
        }
        return s;
    }
}
