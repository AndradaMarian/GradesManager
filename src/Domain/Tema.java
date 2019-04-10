package Domain;

/**
Tema class
 */
public class Tema implements HasID<String> {
    String idTema;
    String descriere;
    String termen;
    String primire;
    String cadruIndrumator;

    /**
     The method returns a Tema Object with its attributes:
     String idNota; The unique registration number
     String descriere; The description of a homework (cand be null)
     String termen; The last week when the homework can be handed in (an Integer between 1-14)
     String primire; The week that the homework was given to the students
     * @param idTema -The unique registration number
     * @param descriere -The description of a homework (cand be null)
     * @param termen -The last week when the homework can be handed in (an Integer between 1-14)
     * @param primire -The week that the homework was given to the students
     * @param cadruIndrumator -The teacher of the homework
     */
    public Tema(String idTema, String descriere, String termen, String primire, String cadruIndrumator) {
        this.idTema = idTema;
        this.descriere = descriere;
        this.termen = termen;
        this.primire = primire;
        this.cadruIndrumator = cadruIndrumator;
    }

    /**
    The method returns the registration number of a homework
     * @return -The id of the homework
     */
    public String getIdTema() {
        return idTema;
    }
    /**
     The method sets the registration number for the homework
     * @param idTema -The id of the homework
     */
    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }
    /**
     The method returns the description for the homework
     * @return The description of the homework
     */
    public String getDescriere() {
        return descriere;
    }
    /**
     The method sets the description for the homework
     * @param descriere -The id of the homework
     */
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    /**
     The method gets the deadline for the homework
     * @return The deadline of the homework
     */
    public String getTermen() {
        return termen;
    }
    /**
     The method sets the deadline for the homework
     * @param termen -
     *               The deadline of the homework
     */
    public void setTermen(String termen) {
        this.termen = termen;
    }
    /**
     The method gets the start week for the homework
     * @return -The week of start of the homework
     */
    public String getPrimire() {
        return primire;
    }
    /**
     The method gets the start week for the homework
     * @param primire- The Startweek of the homework
     */
    public void setPrimire(String primire) {
        this.primire = primire;
    }
    /**
     The override method from the HasId class.It gets the registration number for a homework
     * @return the id
     */
    @Override

    public String getID() {
        return getIdTema();
    }
    /**
     The override method from the HasId class.It sets the registration number for a homework
     */
    @Override
    public void setID(String s) {
        setIdTema(s);
    }
    /**
     * The override method for printing a homework
     */
    @Override
    public String toString() {
        return "Tema{" +
                "idNota='" + idTema + '\'' +
                ", descriere='" + descriere + '\'' +
                ", termen='" + termen + '\'' +
                ", primire='" + primire + '\'' +
                ", cadruIndrumator='" + cadruIndrumator + '\'' +
                '}';
    }

    /**
     * @return cadrul indrumator al acestei teme
     */
    public String getCadruIndrumator() {
        return cadruIndrumator;
    }

    /**
     * @param cadruIndrumator
     * seteaza cadrul indrumator pentru aceasta tema
     */
    public void setCadruIndrumator(String cadruIndrumator) {
        this.cadruIndrumator = cadruIndrumator;
    }
}
