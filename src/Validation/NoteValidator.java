package Validation;

import Domain.Nota;

public class NoteValidator implements Validator<Nota> {
    @Override
    public void validate(Nota entity) throws ValidationException {
            validatePredata(entity);
            validateDeadline(entity);
            validateGrupa(entity);

    }
    public void validatePredata(Nota entity) throws ValidationException {
        String nrS=entity.getPredata();
        if(nrS.isEmpty()){
            throw new ValidationException("Saptamana de predare nu poate fi nula.");
        }
        try{
            int i=Integer.parseInt(nrS);
            if(i<1|i>14){
                throw new ValidationException("Saptamana de predare trebuie sa fie un intreg cuprins intre 1-14");
            }
        }
        catch (NumberFormatException n){
            throw new ValidationException("Saptamana de predare trebuie sa fie un intreg.");
        }
    }
    public void validateDeadline(Nota entity) throws ValidationException {
        String nrS=entity.getDeadline();
        if(nrS.isEmpty()){
            throw new ValidationException("Deadline-ul nu poate fi null.");
        }
        try{
            int i=Integer.parseInt(nrS);
            if(i<1|i>14){
                throw new ValidationException("Deadline-ul trebuie sa fie un intreg cuprins intre 1-14");
            }
        }
        catch (NumberFormatException n){
            throw new ValidationException("Deadline-ul trebuie sa fie un intreg.");
        }
    }
    public void validateGrupa(Nota entity)throws ValidationException {
        int nota= 0;
        try {
            nota = Integer.parseInt(entity.getNota());
        } catch (NumberFormatException e) {
            throw new ValidationException("Nota trebuie sa fie un intreg cuprins intre 1 si 10.");
        }
        if(nota<1 || nota>10){
            throw new ValidationException("Nota trebuie sa fie un intreg cuprins intre 1 si 10.");
        }
    }
}
