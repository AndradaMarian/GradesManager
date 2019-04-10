package Domain;

/**
 * Interfata pentru obiecte identificabile dupa id
 *  @param <ID>
 *      tipul id-ului dupa care vor fi identificate obiectele care vor implementa interfata
 */
public interface HasID<ID> {
    /**
     * @return id-ul unui obiect de acest tip
     */
    ID getID();

    /**
     *
     * @param id
     *seteaza id-ul unui obiect
     */
    void setID(ID id);
}
