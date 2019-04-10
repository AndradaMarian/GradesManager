package GUI.GUIFX;

import Domain.TipUtilizator;
import GUI.CatalogEvent;
import GUI.Observer;
import GUI.SubAlert;
import Profiles.Utilizator;
import Repository.RepositoryNote;
import Repository.RepositoryStudenti;
import Repository.RepositoryTeme;
import Repository.RepositoryUtilizatori;
import Service.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class MainMenuController implements Observer<CatalogEvent> {
    CatalogService service;
    TipUtilizator tipUtilizator;
    Utilizator user;
    @FXML Button administrator;
    @FXML Text creeaza;
    @FXML AnchorPane panouAlegere;
    @FXML AnchorPane logIn;
    @FXML TextField utilizator;
    @FXML PasswordField parola;
    @FXML Button signIn;
    @FXML Button back;
    @FXML AnchorPane creeazaCont;
    @FXML TextField utilizatorNou;
    @FXML PasswordField parolaNoua;
    @FXML TextField nrMatricol;
    @FXML Button buttonCreeaza;
    @FXML Button back2;
    @FXML ImageView failNumarMatricol;
    @FXML ImageView failParola;
    @FXML ImageView failNume;
    @FXML private Button studenti;
    @FXML private Button profesori;
    @FXML private Button exit;
    @FXML public Stage stage;
    @FXML public ImageView studentImage;
    @FXML public ImageView profesorImage;
    @FXML public ImageView adminImage;



    public MainMenuController() {
        this.load();
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }
    private void load(){
        RepositoryStudenti repositoryStudenti=new RepositoryStudenti();
        RepositoryTeme repositoryTeme=new RepositoryTeme();
        RepositoryNote repositoryNote =new RepositoryNote(repositoryTeme,repositoryStudenti);
        RepositoryUtilizatori repositoryUtilizatori=new RepositoryUtilizatori();
        StudentService studentService=new StudentService(repositoryStudenti);
        TemaService temaService=new TemaService(repositoryTeme);
        NoteService noteService=new NoteService(repositoryNote);
        UtilizatorService utilizatorService=new UtilizatorService(repositoryUtilizatori);
        service=new CatalogService(studentService,temaService,noteService,utilizatorService);
        /*service.addUtilizator("Ion",null,"pa",TipUtilizator.PROFESOR.toString() );
        service.addUtilizator("Marian","12345","pa",TipUtilizator.STUDENT.toString() );
        service.addUtilizator("Pop",null,"pa",TipUtilizator.ADMINISTRATOR.toString());*/


    }
    public void maresteStudenti(){

    }
    @FXML
    public void setExit(){
        stage.close();
    }
    @FXML
    private void stergeIconite(){

        panouAlegere.setVisible(false);
        logIn.setVisible(true);


    }
    @FXML
    private void butonStudenti(){

        stergeIconite();
        tipUtilizator=TipUtilizator.STUDENT;

    }
    @FXML
    private void butonProfesori(){
        stergeIconite();
        tipUtilizator=TipUtilizator.PROFESOR;

    }
    @FXML
    private void butonAdministrator(){

        stergeIconite();
        tipUtilizator=TipUtilizator.ADMINISTRATOR;
    }
    @FXML
    private void setCreeaza(){
        panouAlegere.setVisible(false);
        creeazaCont.setVisible(true);
    }
    @FXML
    private void modificaNrMatricol(){
        if(service.findStudent(nrMatricol.getText())==null){
            failNumarMatricol.setVisible(true);
        }
        else
            failNumarMatricol.setVisible(false);
    }
    @FXML
    private void modificaNume(){
        if(service.findUtilizator(utilizatorNou.getText())!=null)
            failNume.setVisible(false);
        else
            failNume.setVisible(true);
        if(utilizatorNou.getText().length()<4)
            failNume.setVisible(true);
        else
            failNume.setVisible(false);
    }
    @FXML
    private void modificaParola(){
        if(parolaNoua.getText().length()<6)
            failParola.setVisible(true);
        else
            failParola.setVisible(false);
    }
    @FXML
    private void setButtonCreeaza(){
        try {
            service.addUtilizator(utilizatorNou.getText(),nrMatricol.getText(),parolaNoua.getText(),TipUtilizator.STUDENT.toString());
            this.back();
        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            //e.printStackTrace();
            s.show();
        }
    }
    @FXML
    private void setSignIn(){


        try {
            user = service.verificaUtilizator(utilizator.getText(), parola.getText());
            if (user != null & user.getTipUtilizator() == tipUtilizator) {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();

                if (user.getTipUtilizator() == TipUtilizator.STUDENT) {
                    loader.setLocation(MainMenuController.class.getResource("/GUI/GUIFX/Student.fxml"));
                    AnchorPane anchorPane = loader.load();
                    stage.setScene(new Scene(anchorPane));
                    StudentController studentController = loader.getController();
                    studentController.set(user, service, stage,this.stage);
                    stage.initStyle(StageStyle.UNDECORATED);
                } else {
                    if (user.getTipUtilizator() == TipUtilizator.PROFESOR) {
                        loader.setLocation(MainMenuController.class.getResource("/GUI/GUIFX/Profesor.fxml"));

                        AnchorPane anchorPane = loader.load();
                        stage.setScene(new Scene(anchorPane));
                        ProfesorController profesorController = loader.getController();
                        profesorController.set(user, service, stage,this.stage);

                        service.addObserver(profesorController);
                        stage.initStyle(StageStyle.UNDECORATED);

                    } else {
                        loader.setLocation(MainMenuController.class.getResource("/GUI/GUIFX/Administrator.fxml"));

                        AnchorPane anchorPane = loader.load();
                        stage.setScene(new Scene(anchorPane));
                        AdministratorController administratorController = loader.getController();
                        administratorController.set(user, service, stage,this.stage);
                        service.addObserver(administratorController);
                        stage.initStyle(StageStyle.UNDECORATED);
                    }

                }
                stage.show();
                this.stage.hide();
                parola.clear();
                back();
                }
            } catch(Exception e){
           // e.printStackTrace();
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            s.show();
            }

    }
    @FXML
    private void back(){
        creeazaCont.setVisible(false);
        //creeazaCont.setDisable(true);
        logIn.setVisible(false);
        panouAlegere.setVisible(true);
    }

    @Override
    public void update(CatalogEvent catalogEvent) {

    }
}
