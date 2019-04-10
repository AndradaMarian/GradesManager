package Service;

import Domain.Nota;
import Domain.Student;
import Domain.Tema;

import Repository.RepositoryNote;
import Validation.NoteValidator;
import Validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Clasa retine o un obiect de tipul Repo si un obiect de tipul validator
 * Mediaza relatiile dintre UI si Repo
 */
public class NoteService {
    NoteValidator validator=new NoteValidator();
    RepositoryNote repositoryNote;

    public NoteService(RepositoryNote repositoryNote) {
        this.repositoryNote = repositoryNote;
    }

    /**
     * @param s Studentul
     * @param t Tema
     * @param s1 ds
     * @param s2 ds
     * @param s3 fd
     * @param s4 fd
     * @throws ValidationException
     * daca nota exista deja
     */
    public Nota save(Student s, Tema t, String s1, String s2, String s3, String s4) throws Exception {
       Nota n=new Nota(s,t,s1,s2,s3,s4);
       //validator.validate(n);
        if(repositoryNote.save(n)!=null)
        {
            throw new ValidationException("Nota exista deja");
        }
        //repositoryNote.scrieFisierNota(n);

        return n;
    }
  /*  public void scrieStudent(String id){
        repositoryNote.findAll().forEach(x->{
            if(x.getIdNota().split("$")[0].equals(id))
            {
                repositoryNote.scrieFisierNota(x);
            }
        });
    }*/



    public int getDimensiune(){
        return repositoryNote.size();
    }

    /*public void stergeStudent(String id){
        repositoryNote.findAll().forEach(x->{
            if(x.getIdNota().split("$")[0].equals(id))
            { repositoryNote.delete(x.getID());
            repositoryNote.stergeFisier(x.getStudent().getNume());;}
        });


    }
    public void stergeFisier(String nume){
        repositoryNote.stergeFisier(nume);
    }*/
    public void updateNota(String id,String nota ,String feedback){
        Nota n= repositoryNote.findOne(id);
        //stergeFisier(n.getStudent().getNume());
        n.setNota(nota);
        n.setFeedback(feedback);
        //scrieStudent(id);
    }
    public Iterable<Nota> noteStudent(String s ){

        ArrayList<Nota> l=new ArrayList<Nota>();
        repositoryNote.findAll().forEach(x->{
            if(x.getStudent().getIdStudent().equals(s))
            {
                l.add(x);
            }
        });
    return l;
    }
    public Nota findNota(String id){
        return repositoryNote.findOne(id);
    }
    public Iterable<Nota>getNote(){
        return repositoryNote.findAll();
    }
    public Iterable<Nota> filtru(Iterable<Nota>all, Predicate<Nota> t){

        ArrayList<Nota> l=new ArrayList<Nota>();
        all.forEach(x->{
            if(t.test(x))
            {
                l.add(x);
            }
        });

        return l;
    }
    public List<Nota>getListaNote(String grupa){
        ArrayList<Nota>l=new ArrayList<>();
        repositoryNote.findAll().forEach(x->
        {if(x.getStudent().getGrupa().equals(grupa))
            l.add(x);});
        return l;
    }
    public Nota findStudentNota(Student student, Tema tema){
       return repositoryNote.findOne(student.getID()+'$'+tema.getIdTema());
    }
}
