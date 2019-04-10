package Service;

import Domain.Tema;

import Repository.RepositoryTeme;
import Validation.FileException;
import Validation.TemaValidation;
import Validation.ValidationException;
import Validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Clasa retine o un obiect de tipul Repo si un obiect de tipul validator
 * Mediaza relatiile dintre UI si Repo
 */
public class TemaService {

    public RepositoryTeme repositoryTeme;
    public Validator<Tema> validator=new TemaValidation();

    public TemaService(RepositoryTeme repositoryTeme) {
        this.repositoryTeme=repositoryTeme;
    }

    /**
     * @throws ValidationException
     * daca apari exceptii la validarea entitatilor(apar de mai multe ori)
     * @throws FileException
     * daca fisierul are probleme
     */

    public Optional<Tema> findTemacurenta(){

            return StreamSupport.stream( repositoryTeme.findAll().spliterator(),false).filter(x->x.getTermen().equals(String.valueOf(getSaptamana()))).findFirst();
    }

    /**
     * @return lista cu temele
     * @throws NullPointerException
     * in cazul in care lista este vida
     */
    public Iterable<Tema> getTeme()throws NullPointerException{

            return repositoryTeme.findAll();

    }
    /**
     * @param t
     * -the homework that we want to edit
     * @param saptamana
     * -the new deadline for the homework
     */
    public void prelungireTema(Tema t, int saptamana){


        t.setTermen(Integer.toString(saptamana));
        repositoryTeme.update(t);
    }
    public void addTema(Tema t) throws Exception {
        repositoryTeme.save(t);
    }
    /**
     * @param id id-ul temei
     * @return
     * Tema
     */
    public Tema find(String id){
        return repositoryTeme.findOne(id);
    }

    /**
     * @return
     * saptamana curenta
     */
    public int getSaptamana(){

        LocalDate i=LocalDate.of(2018,10,1);
        LocalDate now=LocalDate.now();
        Period p=i.until(now);
        int z=i.until(now).getDays();
        int l=i.until(now).getMonths();
        double zile=p.getDays()+l*30+2;
        int aux=(int)Math.round(zile/7.0);
        if(zile/7.0>aux) {
            return aux-1;
        } else
        {
            return aux-2;
        }
    }
}
