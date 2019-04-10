package Repository;

import Domain.Student;
import Validation.ValidationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryStudenti extends AbstractRepoDB<String, Student>{


    public RepositoryStudenti() {
        super("Studenti");
        readDataBase();
    }

    @Override
    public Student citireEntity(ResultSet resultSet) throws ValidationException {
        try {
            String id=resultSet.getString("id");
            String nume=resultSet.getString("Nume");
            String grupa=resultSet.getString("Grupa");
            String email=resultSet.getString("Email");
            return new Student(id,nume,grupa,email);
        } catch (SQLException e) {
            throw new  ValidationException(e.getMessage());
        }

    }

    @Override
    public PreparedStatement addEntity(Student student, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into  Catalog.Studenti values ( ?, ?, ?, ?)");
            preparedStatement.setString(1,student.getID());
            preparedStatement.setString(2,student.getNume());
            preparedStatement.setString(3,student.getGrupa());
            preparedStatement.setString(4,student.getEmail());
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public PreparedStatement updateEntity(Student student, Connection connection1) {
        try{
            PreparedStatement preparedStatement=connection1.prepareStatement("update Studenti set Nume= ? ,Grupa= ?,Email = ? where id= ? " );
            preparedStatement.setString(4,student.getID());
            preparedStatement.setString(1,student.getNume());
            preparedStatement.setString(2,student.getGrupa());
            preparedStatement.setString(3,student.getEmail());
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
