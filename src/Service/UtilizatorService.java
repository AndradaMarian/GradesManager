package Service;

import Domain.TipUtilizator;
import Profiles.Utilizator;
import Repository.RepositoryUtilizatori;
import org.mindrot.jbcrypt.BCrypt;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorService {
    RepositoryUtilizatori repositoryUtilizatori;

    public UtilizatorService(RepositoryUtilizatori repositoryUtilizatori) {
        this.repositoryUtilizatori = repositoryUtilizatori;
    }

    public void addUser(String nume,String parola,String numar,String tipul) throws Exception {
        TipUtilizator tip=TipUtilizator.STUDENT;
        switch (tipul){
            case "STUDENT":{
                tip= TipUtilizator.STUDENT;
                break;
            }
            case "PROFESOR":{
                tip=TipUtilizator.PROFESOR;
                break;
            }
            case "ADMINISTRATOR":{
                tip=TipUtilizator.ADMINISTRATOR;
                break;
            }

        }
        repositoryUtilizatori.save(new Utilizator(nume, BCrypt.hashpw(parola,BCrypt.gensalt(12)),numar,tip));

    }


    public Utilizator findUtilizator(String nume){
        return repositoryUtilizatori.findOne(nume);
    }

    public Iterable<Utilizator>getAll(){
        return repositoryUtilizatori.findAll();
    }
    public void updateUtilizator(String id,String parola,String numar,String tipul){
        TipUtilizator tip=TipUtilizator.STUDENT;
        switch (tipul){
            case "STUDENT":{
                tip= TipUtilizator.STUDENT;
                break;
            }
            case "PROFESOR":{
                tip=TipUtilizator.PROFESOR;
                break;
            }
            case "ADMINISTRATOR":{
                tip=TipUtilizator.ADMINISTRATOR;
                break;
            }

        }
        repositoryUtilizatori.update(new Utilizator(id,BCrypt.hashpw(parola,BCrypt.gensalt(12)),numar,tip));
    }
    Iterable<Utilizator>filtre(String nume , TipUtilizator tipUtilizator)
    {
        return StreamSupport.stream(getAll().spliterator(),false).filter(x->{
            if(tipUtilizator!=null)
                return tipUtilizator.equals(x.getTipUtilizator()) && x.getNume().contains(nume);
            else
                return x.getNume().contains(nume);
        }).collect(Collectors.toList());
    }

    public void delete(String id) {
        repositoryUtilizatori.delete(id);
    }
}
