package Repository;

import Domain.HasID;
import Validation.ValidationException;

import java.sql.*;

/**
 * @param <ID>
 *     reprezinta id-ul unic de identificare a fiecarei entitati
 * @param <E>
 *     reprezinta entitatea
 */
public abstract class AbstractRepoDB<ID,E extends HasID<ID>> extends AbstractCrudRepo<ID,E> {
    String tableName;
     Connection connection=null;
   Statement statement=null;
    PreparedStatement preparedStatement=null;
     ResultSet resultSet=null;

    public AbstractRepoDB(String tableName) {
        this.tableName = tableName;


    }


    public void readDataBase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); connection= DriverManager.getConnection("jdbc:mysql://localhost/catalog?"+
                    "user=USER1&password=USER1SQL");
            statement=connection.createStatement();
            resultSet=statement.executeQuery("select *from Catalog."+tableName);
            while(resultSet.next())
            {
                E entity= citireEntity(resultSet);
                list.putIfAbsent(entity.getID(),entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public abstract E citireEntity(ResultSet resultSet) throws ValidationException;

    /**
     * @param e
     * entitatea pe care vrem sa o scriem in fisier
     * @return
     * returneaza string-ul care contine datele entitatii pregatite pentru a fi scrise in fisier
     */
    public abstract PreparedStatement addEntity(E e, Connection connection1);
    public abstract PreparedStatement updateEntity(E e,Connection connection1);

    /**
     * face suprascrierea fisierului dupa fiecare adaugare a unui element
     * @param entity an entity of type E
     * @return
     * entitatea in cazul in care nu a putut fi salvata
     * null in cazul in care entitatea a fost adaugata
     */
    @Override
    public E save(E entity) throws Exception {
        E e=super.save(entity);
        try {
            addEntity(entity,connection).executeUpdate();
        } catch (SQLException e1) {
            throw new Exception(e1.getMessage());
        }
        return e;
    }

    /**
     * suprascrie fisierul la fiecare stergere
     * @param id -an ID to indentify the E type
     * @return
     * entitatea in cazul in care a fost stearsa
     * null in cazul in care stergerea nu a reusit
     */
    @Override
    public E delete(ID id) {
        E e= super.delete(id);
        try {
            preparedStatement=connection.prepareStatement("delete from catalog."+tableName+" where id= ? ;");
            preparedStatement.setString(1,id.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return e;
    }

    /**
     * suprascrie fisierul la fiecare update
     * @param entity - an entity to update the old one with the same ID
     * @return
     * entitatea veche in cazul in care aceasta a modificata
     * null in cazul in care modificarea a esuat
     *
     */
    @Override
    public E update(E entity)
    {   E e =super.update(entity);

        try {
            updateEntity(e,connection).executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return e;
    }
}
