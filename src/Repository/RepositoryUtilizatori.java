package Repository;

import Domain.TipUtilizator;
import Profiles.Utilizator;
import Validation.ValidationException;

import java.sql.*;

public class RepositoryUtilizatori extends AbstractRepoDB<String, Utilizator> {
    public RepositoryUtilizatori() {
        super("Utilizatori");
        readDataBase();
    }

    @Override
    public Utilizator citireEntity(ResultSet resultSet) throws ValidationException {
        try {
            String id=resultSet.getString("id");
            String numar=resultSet.getString("NumarMatricol");
            String grup=resultSet.getString("Tip");
            String parola=resultSet.getString("Parola");
            TipUtilizator tip=TipUtilizator.STUDENT;
            switch (grup){
                case "STUDENT":{
                    tip=TipUtilizator.STUDENT;
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
            return new Utilizator(id,parola,numar,tip);
        } catch (SQLException e) {
            throw new  ValidationException(e.getMessage());
        }
    }
    @Override
    public void readDataBase(){
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost/catalog?"+
                    "user=USER1&password=USER1SQL");
            statement=connection.createStatement();
            resultSet=statement.executeQuery("   select id,NumarMatricol,Tip,AES_DECRYPT(Parola,'secret') as Parola from "+tableName);
            while(resultSet.next())
            {
                Utilizator entity= citireEntity(resultSet);
                list.putIfAbsent(entity.getID(),entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PreparedStatement addEntity(Utilizator utilizator, Connection connection1) {
        try {
            PreparedStatement preparedStatement = connection1.prepareStatement("insert into  Catalog.Utilizatori values ( ?, ?, ?,AES_ENCRYPT(?,'secret'))");
            preparedStatement.setString(1,utilizator.getID());
            preparedStatement.setString(4,utilizator.getParola());
            if(utilizator.getTipUtilizator()!=null)
            {preparedStatement.setString(3,utilizator.getTipUtilizator().toString());}
            else
            {preparedStatement.setString(3,null);}
            preparedStatement.setString(2,utilizator.getNrMatricolBaza());
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PreparedStatement updateEntity(Utilizator utilizator, Connection connection1) {

        try{
            PreparedStatement preparedStatement=connection1.prepareStatement("update Utilizatori set NumarMatricol=?,Tip=?,parola=aes_encrypt(?,'secret') where id=? " );
            preparedStatement.setString(4,utilizator.getID());
            preparedStatement.setString(1,utilizator.getNrMatricolBaza());
            preparedStatement.setString(2,utilizator.getTipUtilizator().toString());
            preparedStatement.setString(3,utilizator.getParola());
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
