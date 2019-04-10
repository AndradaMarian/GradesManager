package Validation;

import Domain.Tema;

public class TemaValidation implements Validator<Tema> {
    @Override
    public void validate(Tema entity) throws ValidationException {
        String error="";
        try{
            validateNR(entity);
            validatePrimire(entity);
            validateTermen(entity);
            validateProfesor(entity);
        }
        catch (ValidationException e){
            error+=e.getMessage()+"\n";
        }
        if(!error.isEmpty()){
            throw new ValidationException(error);
        }
    }
    public void validateNR(Tema entity) throws ValidationException {
        String nrS=entity.getIdTema();
        if(nrS.isEmpty()){
            throw new ValidationException("ID-ul nu poate fi null.");
        }
    }
    public void validateTermen(Tema entity) throws ValidationException {
        String nrS=entity.getTermen();
        if(nrS.isEmpty()){
            throw new ValidationException("Termenul nu poate fi null.");
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
    public void validatePrimire(Tema entity) throws ValidationException {
        String nrS=entity.getPrimire();
        if(nrS.isEmpty()){
            throw new ValidationException("Termenul nu poate fi null.");
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
    /**
     * @param entity
     *-The Student whose teacher to validate
     * @throws ValidationException
     * -if the teacher is null
     */
    public void validateProfesor(Tema entity)throws ValidationException {
        String pr=entity.getCadruIndrumator();
        if(pr.isEmpty()){
            throw new ValidationException("Campul profesor nu poate fi null.");
        }
        if(!pr.contains(" "))
        {
            throw new ValidationException("Numele trebuie sa contina nume+prenume.");
        }
    }
}
