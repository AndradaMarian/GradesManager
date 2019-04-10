package Repository;

import Domain.Nota;
import Domain.Student;
import Domain.Tema;
import Validation.ValidationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryNote extends AbstractRepoDB<String, Nota> {
    RepositoryTeme repositoryTeme;
    RepositoryStudenti repositoryStudenti;

    public RepositoryNote(RepositoryTeme repositoryTeme, RepositoryStudenti repositoryStudenti) {
        super("Note");
        this.repositoryTeme = repositoryTeme;
        this.repositoryStudenti = repositoryStudenti;
        readDataBase();
    }


    @Override
    public Nota citireEntity(ResultSet resultSet) throws ValidationException {
        try {
            String id=resultSet.getString("id");
            String idStudent=resultSet.getString("idStudent");
            String idTema=resultSet.getString("idTema");
            String predata=resultSet.getString("Predata");
            String deadline=resultSet.getString("Deadline");
            String feedback=resultSet.getString("Feedback");
            String nota=resultSet.getString("Nota");
            Student student=repositoryStudenti.findOne(idStudent);
            Tema tema=repositoryTeme.findOne(idTema);
            return new Nota(student,tema,predata,deadline,feedback,nota);
        } catch (SQLException e) {
            throw new  ValidationException(e.getMessage());
        }
    }

    @Override
    public PreparedStatement addEntity(Nota nota, Connection connection1) {
        try {
            PreparedStatement preparedStatement = connection1.prepareStatement("insert into  Catalog.Note values ( ?, ?, ?, ?,?,?,?)");
            preparedStatement.setString(1, nota.getID());
            preparedStatement.setString(2, nota.getStudent().getID());
            preparedStatement.setString(3, nota.getIdTema());
            preparedStatement.setString(4, nota.getPredata());
            preparedStatement.setString(5, nota.getDeadline());
            preparedStatement.setString(6, nota.getFeedback());
            preparedStatement.setString(7, nota.getNota());


            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PreparedStatement updateEntity(Nota nota, Connection connection1) {
        try {
            PreparedStatement preparedStatement = connection1.prepareStatement("UPDATE Note set Predatata= ?,Deadline= ? ,Nota =? ,Feedback= ? where id = ?");
            preparedStatement.setString(5, nota.getID());
            preparedStatement.setString(1, nota.getPredata());
            preparedStatement.setString(2, nota.getDeadline());
            preparedStatement.setString(4, nota.getFeedback());
            preparedStatement.setString(3, nota.getNota());

            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
