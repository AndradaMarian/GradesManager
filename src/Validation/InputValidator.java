package Validation;

public class InputValidator{
    public void validareInt(String s)throws ValidationException {
        try {
            int i=Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ValidationException("Textul introdus nu este un intreg");
        }
    }
}
