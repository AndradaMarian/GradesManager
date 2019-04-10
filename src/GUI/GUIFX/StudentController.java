package GUI.GUIFX;

import Domain.Nota;
import Domain.Tema;
import GUI.CatalogEvent;
import GUI.Observer;
import Profiles.Utilizator;
import Service.CatalogService;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.sampled.Line;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



public class StudentController implements Observer<CatalogEvent> {
    @FXML TabPane tabPane;
    Utilizator user;
    CatalogService service;
    @FXML Stage stage;
    @FXML Stage parentStage;


    ObservableList<Tema>temaObservableList;
    @FXML TableView<Tema>temaTableView;
    @FXML Label notaObtinutaText;
    @FXML TextField notaObtinutaField;
    @FXML TextArea feedbackField;
    @FXML TextField mediaCurenta;
    /**-----------------------------*/
    @FXML NumberAxis yAxis;
    @FXML CategoryAxis xAxis;
    @FXML LineChart<String,Number>LineChart;
    @FXML Label welcome;

    public StudentController() {
    }

    public void set(Utilizator user, CatalogService service, Stage stage,Stage parentStage1)
    {
        this.user=user;
        this.service=service;
        this.stage=stage;
        parentStage=parentStage1;

        temaObservableList = FXCollections.observableList(StreamSupport.stream(service.getTeme().spliterator(), false).collect(Collectors.toList()));
        temeTable();
        selectedTema();
        setMediaCurenta();
        setLineChart();
        welcome.setText("Bine ai venit, "+user.getNume());


    }
    @FXML public void close(){
        stage.close();
    }
    @FXML
    void logOut(){
        stage.close();
        parentStage.show();
    }
    @FXML
    void temeTable(){
        temaTableView.getColumns().clear();
        TableColumn<Tema,String>unu=new TableColumn<>("ID");
        unu.setCellValueFactory(new PropertyValueFactory<Tema,String>("ID"));
        TableColumn<Tema,String>doi=new TableColumn<>("Deadline");
        doi.setCellValueFactory(new PropertyValueFactory<Tema,String>("Termen"));
        TableColumn<Tema,String>trei=new TableColumn<>("Primita");
        trei.setCellValueFactory(new PropertyValueFactory<Tema,String>("Primire"));
        TableColumn<Tema,String>patru=new TableColumn<>("Descriere");
        patru.setCellValueFactory(new PropertyValueFactory<Tema,String>("Descriere"));
        TableColumn<Tema,String>cinci=new TableColumn<>("Profesor");
        cinci.setCellValueFactory(new PropertyValueFactory<Tema,String>("CadruIndrumator"));
        unu.prefWidthProperty().bind(temaTableView.widthProperty().divide(100/15));
        doi.prefWidthProperty().bind(temaTableView.widthProperty().divide(100/20));
        trei.prefWidthProperty().bind(temaTableView.widthProperty().divide(100/20));
        patru.prefWidthProperty().bind(temaTableView.widthProperty().divide(100/25));
        cinci.prefWidthProperty().bind(temaTableView.widthProperty().divide(100/20));


        temaTableView.getColumns().addAll(unu,trei,doi,patru,cinci);
        temaTableView.setItems(temaObservableList);
        //temaTableView.requestLayout();
    }
    @FXML
    void selectedTema(){
        Tema tema=temaTableView.getSelectionModel().getSelectedItem();
        if(tema==null){

            feedbackField.clear();
            notaObtinutaField.clear();

        }
        else {

            Nota nota=service.findNota(user.getNrMatricol()+"$"+tema.getIdTema());
            if(nota==null)
            {

                try {

                    notaObtinutaField.setText(service.notaMaxima(tema.getID(),0).toString());

                    notaObtinutaField.setVisible(true);
                    notaObtinutaText.setText("Nota maxima \n poate fi");
                } catch (ValidationException e) {

                    notaObtinutaText.setText("Aceasta tema nu se mai poate preda \n" );
                    notaObtinutaField.setVisible(false);
                }

            }
            else
            {
                feedbackField.setText(nota.getFeedback());
                notaObtinutaText.setText("Nota obtinuta");
                notaObtinutaField.setVisible(true);
                notaObtinutaField.setText(nota.getNota());
            }
        }
    }
    void setMediaCurenta(){
        double media=service.mediaLaborator(service.getNoteStudent().get(service.findStudent(user.getNrMatricol())));
        mediaCurenta.setText(String.valueOf(media));
    }

    void setLineChart(){
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        LineChart.getData().clear();
        series1.setName("Note");
        if(service.getNoteStudent().get(service.findStudent(user.getNrMatricol()))!=null)
        { StreamSupport.stream(service.getNoteStudent().get(service.findStudent(user.getNrMatricol())).spliterator(),false).sorted(Comparator.comparing(Nota::getIDTemaIntreg)).forEach(x->{
            series1.getData().add(new XYChart.Data<>("Tema"+x.getIdTema(),Double.valueOf(x.getNota())));

        });

            LineChart.getData().add(series1);}


    }


    @Override
    public void update(CatalogEvent catalogEvent) {

    }
}
