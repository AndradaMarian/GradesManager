package Validation;

import Domain.Student;

/**
 * Validates a Student Object
 * -Is a particular Validator for a student
 */
public class StudentValidation implements Validator<Student> {

    /**
     * @param entity-The Student to validate
     * @throws ValidationException
     * - if any of the parameters is not valid
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        String error="";
        try{
            validateID(entity);
            validateNume(entity);
            validateGrupa(entity);
            validateMail(entity);
        }
        catch (ValidationException e){
            error+=e.getMessage()+"\n";
        }
        if(!error.isEmpty()){
            throw new ValidationException(error);
        }
    }

    /**
     * @param entity -The student whose ID to validate
     * @throws ValidationException
     * if the ID is null
     */
    public void validateID(Student entity)throws ValidationException {
        String id=entity.getID();
        if(id.isEmpty()){
           throw new ValidationException("ID-ul nu poate fi null");
        }
    }

    /**
     * @param entity
     * - The Student whose name to validate
     * @throws ValidationException
     * -if the name is null or has only one Substring(must be last name+ first name)
     */
    public void validateNume(Student entity)throws ValidationException {
        String nume=entity.getNume();
        if(nume.isEmpty()){
            throw new ValidationException("Numele nu poate fi null");
        }
        if(!nume.contains(" "))
        {
            throw new ValidationException("Numele trebuie sa contina nume+prenume.");
        }
    }

    /**
     * @param entity
     * -The Student whose group to validate
     * @throws ValidationException
     * -if group is not an Integer from the range
     */
    public void validateGrupa(Student entity)throws ValidationException {
        int gr= 0;
        try {
            gr = Integer.parseInt(entity.getGrupa());
        } catch (NumberFormatException e) {
            throw new ValidationException("Grupa trebuie sa fie un intreg cuprins intre 111 si 959.");
        }
        if(gr<111 || gr>959){
            throw new ValidationException("Grupa nu exista.");
        }
    }

    /**
     * @param entity
     * -The student whose mail to validate
     * @throws ValidationException
     * -if the mail is null
     */
    public void validateMail(Student entity)throws ValidationException {
        String mail=entity.getEmail();
        if(mail.isEmpty()){
            throw new ValidationException("Emailul nu poate fi null.");
        }
    }


}
