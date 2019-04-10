package Repository;

import Domain.Tema;
import Validation.ValidationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryTeme extends AbstractRepoDB<String, Tema>{
    public RepositoryTeme() {
        super("Teme");
        readDataBase();
    }

    @Override
    public Tema citireEntity(ResultSet resultSet) throws ValidationException {
        try {
            String id=resultSet.getString("id");
            String descriere=resultSet.getString("Descriere");
            String termen=resultSet.getString("Termen");
            String primire=resultSet.getString("Primire");
            String cadru=resultSet.getString("CadruIndrumator");
            return new Tema(id,descriere,termen,primire,cadru);
        } catch (SQLException e) {
            throw new  ValidationException(e.getMessage());
        }
    }

    @Override
    public PreparedStatement addEntity(Tema tema, Connection connection1) {
        try {
            PreparedStatement preparedStatement = connection1.prepareStatement("insert into  Catalog.Teme values ( ?, ?, ?, ?,?)");
            preparedStatement.setString(1,tema.getID());
            preparedStatement.setString(2,tema.getDescriere());
            preparedStatement.setString(3,tema.getTermen());
            preparedStatement.setString(4,tema.getPrimire());
            preparedStatement.setString(5,tema.getCadruIndrumator());
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PreparedStatement updateEntity(Tema tema, Connection connection1) {
        try {
            PreparedStatement preparedStatement = connection1.prepareStatement("update Teme SET Descriere= ? ,Termen= ? ,Primire= ? ,CadruIndrumator = ? where id= ? ");
            preparedStatement.setString(5,tema.getID());
            preparedStatement.setString(1,tema.getDescriere());
            preparedStatement.setString(2,tema.getTermen());
            preparedStatement.setString(3,tema.getPrimire());
            preparedStatement.setString(4,tema.getCadruIndrumator());
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
