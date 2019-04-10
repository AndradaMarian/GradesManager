package Service;


import Domain.*;

import GUI.*;
import GUI.Observable;
import GUI.Observer;
import Profiles.Utilizator;
import Validation.ValidationException;
import javafx.util.Pair;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class CatalogService implements Observable<CatalogEvent> {
     StudentService studentService;
     NoteService noteService;
     TemaService temaService;
     UtilizatorService utilizatorService;
     ArrayList<Observer<CatalogEvent>> observers=new ArrayList<>();

    public CatalogService(StudentService studentService , TemaService temaService,NoteService noteService,UtilizatorService utilizatorService) {
        this.studentService = studentService;
        this.noteService = noteService;
        this.temaService = temaService;
        this.utilizatorService=utilizatorService;
       // utilizatorService.addUser("Andrada","parola","12340","STUDENT");
       //utilizatorService.addUser("Ion Vlad","parola"," ","PROFESOR");
        //utilizatorService.addUser("Andrada3","parola"," ","ADMINISTRATOR");

    }

    /**Studenti-----------------------------------------------------------------------------------------*/

    public Student cautaStudentNume(String nume){
        for (Student s: studentService.getStudenti()
             ) {
            if(s.getNume().equals(nume))
                return s;
        }
        return null;
    }
    public void addStudent(String s1,String s2,String s3,String s4) throws Exception {

        try {
            studentService.addStudent(s1,s2,s3,s4);
            notifyObservers(new CatalogEvent(new StudentEvent(null,new Student(s1,s2,s3,s4), ChangeEventType.ADD),null,null,EntityType.Student, null));
        } catch (ValidationException e) {
            throw new Exception(" ");
        }

    }



    public Iterable<String>getGrupe(){
        ArrayList<String> grupe =new ArrayList<>();
        studentService.getStudenti().forEach(x->{
            if(!grupe.contains(x.getGrupa()))
                grupe.add(x.getGrupa());
        });
        return grupe;
    }



    public Student findStudent(String id){
        return studentService.find(id);
    }


    public void deleteStudent(String id) throws Exception {
        try {
            Student e=studentService.deleteStudent(id);
            //noteService.stergeStudent(id);
            notifyObservers(new CatalogEvent(new StudentEvent(e,null, ChangeEventType.DELETE),null,null,EntityType.Student,null ));
        } catch (ValidationException e1) {
            throw new Exception(" ");
        }
    }




    public void updateStudent(String id , String nume ,String grupa , String email) throws Exception {
        try {
            getStudenti().forEach(x->{
                if(x.getIdStudent().equals(id)){
                    //noteService.stergeFisier(x.getNume());
                }
            });
            Student aux=studentService.find(id);
            Student o=new Student(aux.getIdStudent(),aux.getNume(),aux.getGrupa(),aux.getEmail());
            Student n=studentService.update(id,nume,grupa,email);
            noteService.noteStudent(id).forEach(x->{
                        noteService.updateNota(x.getIdNota(),x.getNota(),x.getFeedback());
                    }
            );
            notifyObservers(new CatalogEvent(new StudentEvent(o,n,ChangeEventType.UPDATE),null,null,EntityType.Student,null ));
        } catch (ValidationException e) {
            throw new Exception(" ");
        }

    }




    public Iterable<Student>getStudenti(){
        return studentService.getStudenti();
    }

    public Iterable<Nota>filtruGeneral(Student student, Tema tema, String grupa){
        Iterable<Nota>filtrata=noteService.getNote();
        Predicate<Nota>s=(x->x.getStudent().equals(student));
        Predicate<Nota>t=(x->x.getTema().equals(tema));
        Predicate<Nota>g=(x->x.getStudent().getGrupa().equals(grupa));
        if(student!=null){
            filtrata=noteService.filtru(filtrata,s);
        }
        if(tema!=null){
            filtrata=noteService.filtru(filtrata,t);
        }
        if(grupa!=null){
            filtrata=noteService.filtru(filtrata,g);
        }
        return filtrata;
    }

    public Iterable<Student>studentiFaraNotaGrupa(String grupa, Tema tema){
        List<Nota>note=noteService.getListaNote(grupa);
        List<Student>l=new ArrayList<>();
        studentService.getStudenti().forEach(x->{
            if(noteService.findStudentNota(x,tema)==null)
                l.add(x);
        });
        return l;
    }


    public Iterable<Student> getStudentsByName(String nume){

        return studentService.getStudentiPartialNume(nume);

    }




    /** Teme-----------------------------------------------------------------------------------*/


    public Optional<Tema> findTemaCurenta(){

        return temaService.findTemacurenta();
    }



    public Iterable<Tema>getTeme()throws NullPointerException{
        return temaService.getTeme();
    }


    public void prelungireTema(String id,int saptamana)throws ValidationException {
        if(temaService.getSaptamana()>saptamana) {
            throw new ValidationException("The deadline is not valid");}
        temaService.getTeme().forEach( x->{if (x.getIdTema().equals(id)) {
            temaService.prelungireTema(x,saptamana);

        }
        });
        notifyObservers(new CatalogEvent(null,new TemaEvent(this.findTemaFromID(id),this.findTemaFromID(id),ChangeEventType.UPDATE),null,EntityType.Tema, null));

    }
    public void addTema(Tema tema) throws Exception {
        try{
        temaService.addTema(tema);
        notifyObservers(new CatalogEvent(null,new TemaEvent(null,tema,ChangeEventType.ADD),null,EntityType.Tema, null));
        }
        catch (Exception e)
        {throw new Exception(" ");}
    }



    public Tema findTemaFromID(String id){

        return temaService.find(id);
    }


    public int getSaptamana(){

        return temaService.getSaptamana();
    }




    /** Note-----------------------------------------------------------------------------------*/



    public void addNota(String idStudent,String s2,String feedback,String nota) throws Exception {
        String s3;
        String s4;
        Student s=studentService.find(idStudent);
        if(s==null){
            throw new ValidationException("Studentul nu exista");
        }
        Tema t=temaService.find(s2);
        if(t==null){
            throw new ValidationException("Tema nu exista");
        }
        s3=Integer.toString(temaService.getSaptamana());
        s4=t.getTermen();
       try{ Nota nota1=noteService.save(s,t,s3,s4,feedback,nota);
           notifyObservers(new CatalogEvent(null,null,new NoteEvent(null,nota1,ChangeEventType.ADD),EntityType.Nota,null ));}
       catch (Exception e){throw new Exception(e);}

    }

    public Nota findNota(String id){
        return noteService.findNota(id);
    }

    public Double notaMaxima(String id,int absent)throws ValidationException {
        Tema t=temaService.find(id);
        int dif=Integer.parseInt(t.getTermen())-temaService.getSaptamana();
        dif=dif+absent;
        if (dif>=0){
            return 10.0;
        }
        if(dif==-1)
        {
            return 7.5;
        }
        if(dif==-2)
            return 5.0;
        throw new ValidationException( "Tema nu mai poate fi predata");
    }

    public int nrNote(){

        return noteService.getDimensiune();
    }
    public Iterable<Nota>getNote(){

        Iterable<Nota>notes=noteService.getNote();

        return notes;
    }
    public double mediaLaborator(List<Nota> notaList){
        double suma;
        if(notaList==null)
            return 1;
        suma=notaList.stream().mapToDouble(x->{ return Double.valueOf(x.getNota())*(Double.valueOf(x.getDeadline())-Double.valueOf(x.getTema().getPrimire())+1);}).sum();
            return suma/14;

    }
    public double medieTema(List<Nota>list){
        double suma;
        suma=list.stream().mapToDouble(x->{return Double.valueOf(x.getNota());}).sum();
        return suma/list.size();

    }
    public Map<Tema,Double>getMediiTeme(){
        Map<Tema,Double>lista=new HashMap<>();
        getNoteTema().keySet().forEach(x->{
            lista.putIfAbsent(x,medieTema(getNoteTema().get(x)));
        });
        return lista;
    }
    public Pair<Tema,Double>getTemaGrea(){
        Map<Tema,Double>lista=getMediiTeme();
        double media=lista.values().stream().min(Comparator.comparingDouble(Double::doubleValue)).orElse(-1.0);
        for (Map.Entry<Tema, Double> entry : lista.entrySet()) {
            Tema x = entry.getKey();
            Double y = entry.getValue();
            if (y == media) {
                return new Pair<>(x,y);

            }
        }
        return null;
    }
    public Map<Tema,List<Nota>>getNoteTema(){
        return StreamSupport.stream(getNote().spliterator(),false).collect(Collectors.groupingBy(Nota::getTema));
    }
    public Map<Student,List<Nota>>getNoteStudent(){
         return StreamSupport.stream(getNote().spliterator(),false).collect(Collectors.groupingBy(Nota::getStudent));

    }
    public List<StudentMedie>getToateMediile(){
        List<StudentMedie>lista=new ArrayList<>();

        Map<Student,List<Nota>>note=getNoteStudent();

        note.keySet().forEach(x->{
            lista.add(new StudentMedie(x,mediaLaborator(note.get(x))));
        });
        getStudenti().forEach(x->{
            if(!note.containsKey(x)){
                lista.add(new StudentMedie(x,1.0));
            }
        });
        return lista;
    }
    public List<StudentMedie>getToateMediileNume(String nume){
        List<StudentMedie>lista=new ArrayList<>();

        Map<Student,List<Nota>>note=getNoteStudent();

        getStudentsByName(nume).forEach(x->{
            if(!note.containsKey(x)){
                lista.add(new StudentMedie(x,1.0));
            }
            else
            {lista.add(new StudentMedie(x,mediaLaborator(note.get(x))));}
        });

        return lista;
    }


    /** Utilizator-----------------------------------------------------------------------------------*/



    public void addUtilizator(String id, String numar,String parola,String tip) throws Exception {
        if(tip.equals(TipUtilizator.STUDENT.toString())&&findStudent(numar)==null)
            throw new Exception("Studentul nu exista.");
        try {
            utilizatorService.addUser(id, parola, numar, tip);
            notifyObservers(new CatalogEvent(null, null, null, EntityType.Utilizator, new UtilizatorEvent(null, new Utilizator(id, parola, numar, TipUtilizator.valueOf(tip)), ChangeEventType.ADD)));
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }


    public Utilizator findUtilizator(String nume){

        return utilizatorService.findUtilizator(nume);
    }
    public void updateUtilizator(String id,String parola,String numar,String tipul) throws Exception {
        try {
            utilizatorService.updateUtilizator(id, parola, numar, tipul);
            notifyObservers(new CatalogEvent(null, null, null, EntityType.Utilizator, new UtilizatorEvent(new Utilizator(id, parola, numar, TipUtilizator.valueOf(tipul)), new Utilizator(id, parola, numar, TipUtilizator.valueOf(tipul)), ChangeEventType.UPDATE)));
        }
        catch (Exception e){
            throw new Exception("Studentul nu exista.");
        }
    }
    public void deleteUtilizator(String id) throws Exception {
        try {
            Utilizator u = utilizatorService.findUtilizator(id);
            utilizatorService.delete(id);
            notifyObservers(new CatalogEvent(null, null, null, EntityType.Utilizator, new UtilizatorEvent(u, null, ChangeEventType.DELETE)));
        }
        catch (Exception e)
        {throw new Exception("Studentul nu exista.");}
    }

    public Utilizator verificaUtilizator(String nume, String parola)
    {
        Utilizator utilizator=findUtilizator(nume);
        if(BCrypt.checkpw(parola,utilizator.getParola())){
            return utilizator;

        }
        else {
            return null;
        }

    }
    public Iterable<Utilizator>getUtilizatori(){
        return utilizatorService.getAll();
    }
    public Iterable<Utilizator>filtru(String nume , TipUtilizator tipUtilizator){
        return utilizatorService.filtre(nume , tipUtilizator);
    }






    @Override
    public void addObserver(Observer<CatalogEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<CatalogEvent> e) {
        observers.remove(e);
    }

    @Override


    public void notifyObservers(CatalogEvent event) {
    observers.forEach(x->x.update(event));
    }




}
