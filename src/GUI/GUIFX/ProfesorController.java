package GUI.GUIFX;

import Domain.Nota;
import Domain.Student;
import Domain.StudentMedie;
import Domain.Tema;
import GUI.CatalogEvent;
import GUI.Observer;
import GUI.SubAlert;
import Profiles.Utilizator;
import Service.CatalogService;
import Validation.TemaValidation;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;


import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProfesorController implements Observer<CatalogEvent> {
    Stage parentStage;
    Utilizator user;
    CatalogService service;
    ObservableList<Nota> notaObservableList;
    ObservableList<Student> studentObservableList;
    ObservableList<Tema> temaObservableList;
    @FXML Stage stage;
    @FXML TabPane tabPane;
    @FXML TableView<Tema>temaTableView;
    @FXML TextField idTemaField;
    @FXML TextArea descriereField;
    @FXML TextField deadlineField;
    @FXML Button addButton;
    @FXML Button deselecteaza;
    @FXML PieChart pieChartTema;
    @FXML AnchorPane anchorPaneRapoarteTeme;
    @FXML AnchorPane anchorPaneAdaugaTema;
    @FXML Button backButtonTeme;
    /**----------------------------------------------------------------*/
    @FXML TextField studentTextField;
    @FXML TableView<Student>studentTableView;
    @FXML ComboBox<Tema>temaComboBox;
    @FXML Spinner<Integer>absente;
    @FXML TextField notaTextField;
    @FXML TextArea feedbackTextField;
    @FXML ComboBox<String>grupa;
    @FXML TableView<Nota>notaTableView;
    @FXML PieChart pieChartNote;
    @FXML AnchorPane anchorPaneAdaugaNota;
    @FXML AnchorPane anchorPaneFiltreNota;
    @FXML Text grupaText;
    @FXML Button backButtonNote;
    @FXML ImageView backNote;
    @FXML Text filtre;


    /**-----------------------------------------------------------------*/
    @FXML TextField idStudentTextField;
    @FXML TextField numeStudentTextField;
    @FXML TextField grupaStudentTextField;
    @FXML TextField emailStudentTextField;
    @FXML Button addStudentButton;
    @FXML Button deleteStudentButton;
    @FXML Button updateStudentButton;
    @FXML TableView<Student>panouStudentiTableView;
    @FXML LineChart<String,Number>studentLineChart;
    @FXML AnchorPane anchorPaneAdaugaStudent;
    @FXML AnchorPane anchorPaneRapoarteStudent;
    @FXML Button backButtonStudent;
    @FXML Label welcome;
    @FXML ImageView backStudent;
    @FXML ImageView adaugaStudentImagine;
    @FXML Button adaugaStudent;
    @FXML Label adaugaText;




    public void set(Utilizator user, CatalogService service,Stage stage1,Stage parentStage1) {
        this.user = user;
        this.service = service;
        notaObservableList = FXCollections.observableList(StreamSupport.stream(service.getNote().spliterator(), false).collect(Collectors.toList()));
        studentObservableList = FXCollections.observableList(StreamSupport.stream(service.getStudenti().spliterator(), false).collect(Collectors.toList()));
        temaObservableList = FXCollections.observableList(StreamSupport.stream(service.getTeme().spliterator(), false).collect(Collectors.toList()));
        stage=stage1;
        parentStage=parentStage1;
        temeTable();
       filterLoad();
       filtruStudentCombo();
       setStudentTableView();
       setAbsente();
       filter();
       setPanouStudentiTableView();
        setPieChartTema();
        setPieChartNote();
        setLineChart();
     reset();
        welcome.setText("Bine ai venit, "+user.getNume());
    }
    @FXML
    void close(){
        stage.close();
    }
    @FXML
    void logOut(){
        stage.close();
        parentStage.show();
    }
    @FXML
    void reset(){
        initNote();
        initTeme();
        initStudent();
    }


    @FXML
    void temeTable(){
        temaTableView.getColumns().clear();
        TableColumn<Tema,String>unu=new TableColumn<>("ID");
        unu.setCellValueFactory(new PropertyValueFactory<Tema,String>("ID"));
        TableColumn<Tema,String>doi=new TableColumn<>("Deadline");
        doi.setCellValueFactory(new PropertyValueFactory<Tema,String>("Termen"));
        TableColumn<Tema,String>patru=new TableColumn<>("Descriere");
        patru.setCellValueFactory(new PropertyValueFactory<Tema,String>("Descriere"));
        unu.prefWidthProperty().bind(temaTableView.widthProperty().divide(100/15));
        doi.prefWidthProperty().bind(temaTableView.widthProperty().divide(100/25));
        patru.prefWidthProperty().bind(temaTableView.widthProperty().divide(100/60));

        temaTableView.getColumns().addAll(unu,doi,patru);
        temaTableView.setItems(temaObservableList);
        temaTableView.requestLayout();
        /*if(service.getTemaGrea()!=null){
        temaGrea.setText(service.getTemaGrea().getKey().getIdTema()+service.getTemaGrea().getValue());}*/

    }
    @FXML
    void initTeme(){
        anchorPaneAdaugaTema.setVisible(false);
        anchorPaneRapoarteTeme.setVisible(true);
    }
    @FXML
    void setAnchorPaneAdaugaTema()
    {
        anchorPaneRapoarteTeme.setVisible(false);
        anchorPaneAdaugaTema.setVisible(true);
    }
    @FXML
    void selectedTema(){
        Tema tema=temaTableView.getSelectionModel().getSelectedItem();
        if(tema==null){
            idTemaField.clear();
            descriereField.clear();
            deadlineField.clear();

        }
        else {
            idTemaField.setText(tema.getIdTema());
            descriereField.setText(tema.getDescriere());
            deadlineField.setText(tema.getTermen());

        }
    }
    @FXML
    void setAddButton(){
        try {


            Tema tema=new Tema(idTemaField.getText(),descriereField.getText(),deadlineField.getText(),String.valueOf(service.getSaptamana()),user.getNume());

            if(idTemaField.getText().equals(null)||deadlineField.getText().equals(null))
            {
                throw new Exception("Campurile nu pot fi nule ");
            }

            if(service.findTemaFromID(tema.getID())==null)
            {
                service.addTema(tema);
            }
            else
            {

                    service.prelungireTema(tema.getID(),Integer.valueOf(deadlineField.getText()));

            }
            temeTable();
            initTeme();
            filtruStudentCombo();
        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","");
            s.setContentText("Verficati datele introduse");
            s.show();

        }

    }
    @FXML
    void setDeselecteaza(){
        temaTableView.getSelectionModel().clearSelection();
        selectedTema();
    }
    @FXML
    void setPieChartTema(){
        Map<Student,Integer>studentIntegerMap=new HashMap<>();
        Integer predate=0;
        Tema t;
        if(temaTableView.getSelectionModel().getSelectedItem()==null)
        {   if(service.findTemaCurenta().isPresent())
            t=service.findTemaCurenta().get();
            else
                t=temaObservableList.get(0);

        }
        else
            t=temaTableView.getSelectionModel().getSelectedItem();
        if(service.getNoteTema().containsKey(t))
        {
                service.getNoteTema().get(t).forEach(x->{studentIntegerMap.putIfAbsent(x.getStudent(),1
            );});
                predate=studentIntegerMap.size();
        }

        Integer total=studentObservableList.size();
        pieChartTema.setData(FXCollections.observableArrayList(new PieChart.Data("Predata",predate),new PieChart.Data("In lucru",total-predate)));
    }


    @Override
    public void update(CatalogEvent catalogEvent) {
        switch (catalogEvent.getChangeEventType().toString()){
            case "Tema":{
                switch (catalogEvent.getTemaEvent().getType().toString()){
                    case "UPDATE":{
                        temaObservableList.remove(catalogEvent.getTemaEvent().getOldData());
                        temaObservableList.add(catalogEvent.getTemaEvent().getNewData());

                        break;
                    }
                    case "ADD":{
                        temaObservableList.add(catalogEvent.getTemaEvent().getNewData());

                        break;
                    }
                }
                break;
            }
            case"Nota":{
                switch (catalogEvent.getNoteEvent().getType().toString()){
                    case"ADD":{
                        notaObservableList.add(catalogEvent.getNoteEvent().getNewData());
                        setStudentTableView();
                        break;
                    }
                }
                break;
            }
            case "Student":{
                switch (catalogEvent.getStudentEvent().getType().toString()){
                    case "ADD":{
                        studentObservableList.add(catalogEvent.getStudentEvent().getNewData());
                        break;
                    }
                    case "UPDATE":{

                        studentObservableList.remove(catalogEvent.getStudentEvent().getOldData());
                        studentObservableList.add(catalogEvent.getStudentEvent().getNewData());

                        break;
                    }
                    case "DELETE":{
                        studentObservableList.remove(catalogEvent.getStudentEvent().getOldData());
                        break;
                    }
                }
                break;
            }
        }
    }

/**Note---------------------------------------------------------------*/
    public void initNote(){
        studentTableView.setVisible(false);
        notaTableView.setVisible(true);
        anchorPaneFiltreNota.setVisible(true);

        anchorPaneAdaugaNota.setVisible(false);
        grupa.setVisible(true);
        grupaText.setVisible(true);
       backButtonNote.setVisible(false);
       backNote.setVisible(false);
       filtre.setVisible(true);

    }
    public void setAnchorPaneAdaugaNota(){
        anchorPaneAdaugaNota.setVisible(true);
        anchorPaneFiltreNota.setVisible(false);
        grupa.setVisible(false);
        grupaText.setVisible(false);
        backButtonNote.setVisible(true);
        backNote.setVisible(true);
        filtre.setVisible(false);
    }
    @FXML
    public void switchTableStudentOn(){
        studentTableView.setVisible(true);

    }
    @FXML
    public void switchTableStudentOff(){
        studentTableView.setVisible(false);
    }

    @FXML
    public void setStudentTableView(){
        studentTableView.getColumns().clear();
        TableColumn<Student,String>unu=new TableColumn<>("Nume");
        unu.setCellValueFactory(new PropertyValueFactory<Student,String>("Nume"));

        studentTableView.getColumns().addAll(unu);
        ObservableList<Student>lista=FXCollections.observableList(StreamSupport.stream(service.getStudenti().spliterator(),false).collect(Collectors.toList()));
        studentTableView.setItems(lista);


    }
    @FXML
    public void searchStudent(){
        String nume=studentTextField.getText();
        studentTableView.getItems().clear();
        if((FXCollections.observableList(StreamSupport.stream(service.getStudentsByName(nume).spliterator(),false).collect(Collectors.toList()))).isEmpty())
            {switchTableStudentOff();
            return ;
        }
        studentTableView.setItems(FXCollections.observableList(StreamSupport.stream(service.getStudentsByName(nume).spliterator(),false).collect(Collectors.toList())));
        switchTableStudentOn();

    }
    @FXML
    public Student selectedStudent(){
        Student student1=studentTableView.getSelectionModel().getSelectedItem();
        if(student1!=null)
        {
            studentTextField.setText(student1.getNume());
            grupa.getSelectionModel().select(student1.getGrupa());
        }switchTableStudentOff();
        return student1;

    }
    @FXML
    public void initializeNota(){
        double max;
        try {
            max=service.notaMaxima(temaComboBox.getSelectionModel().getSelectedItem().getID(),absente.getValue());
        } catch (Exception e) {
            max=1;
        }
        notaTextField.setText(String.valueOf(max));
        filter();

    }
    @FXML
    void validateNota(){
        double max;
        try {
            max=service.notaMaxima(temaComboBox.getSelectionModel().getSelectedItem().getID(),absente.getValue());
        } catch (ValidationException e) {
            max=1;
        }
        if(notaTextField.getCharacters().length()!=0) {
            char last = notaTextField.getCharacters().charAt(notaTextField.getCharacters().length()-1);
            if ("0123456789.".indexOf(String.valueOf(last)) == -1) {
                notaTextField.setText(String.valueOf(max));
            }
            try{
                double numar=Double.valueOf(notaTextField.getCharacters().toString());
                if(numar>max){
                    Alert a=new Alert(Alert.AlertType.ERROR,"Nota trebuie cel mult egala cu nota maxima");
                    a.show();
                    notaTextField.setText(String.valueOf(max));

                }
            }
            catch (NumberFormatException e){
                Alert a=new Alert(Alert.AlertType.ERROR,"Nota trebuie sa fie un numar zecimal");
                a.show();
                notaTextField.setText(String.valueOf(max));
            }
        }
        else{
            //Nota.setText(String.valueOf(max));
        }


    }
    @FXML
    public void setAbsente(){

        absente.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,5,0 ,1));
    }
    @FXML
    public void selectedTemaChanged(){
        feedbackTextField.clear();

        String tema=temaComboBox.getSelectionModel().getSelectedItem().getIdTema();
        if(tema!=null)
        {   int ab=absente.getValue();
            try {double max=service.notaMaxima(tema,ab);
                double depunct=10-max;
                if(depunct!=0){
                    feedbackTextField.setText("Nota a fost diminuata cu "+String.valueOf(depunct)+" puncte datorita intarzierii");
                }
                else {
                    feedbackTextField.clear();
                }

            } catch (ValidationException e) {

            }
            initializeNota(); filter();
        }

    }
    @FXML
    public void filter(){
        if(studentTableView.getSelectionModel().getSelectedItem()==null)
        {   Student s=service.cautaStudentNume(studentTextField.getText());
            Iterable<Nota>n=service.filtruGeneral(s,temaComboBox.getSelectionModel().getSelectedItem(),grupa.getSelectionModel().getSelectedItem());
            ObservableList<Nota>filtrata=FXCollections.observableList(StreamSupport.stream(n.spliterator(), false).collect(Collectors.toList()));
            notaTableView.getItems().clear();

            notaTableView.setItems(filtrata);
        }
        else
        {
            Iterable<Nota>n=service.filtruGeneral(studentTableView.getSelectionModel().getSelectedItem(),temaComboBox.getSelectionModel().getSelectedItem(),grupa.getSelectionModel().getSelectedItem());
            ObservableList<Nota>filtrata=FXCollections.observableList(StreamSupport.stream(n.spliterator(), false).collect(Collectors.toList()));
            notaTableView.getItems().clear();

            notaTableView.setItems(filtrata);
        }

    }
    @FXML
    public void filterLoad(){
        TableColumn<Nota,String>unu=new TableColumn<>("Student");
        unu.setCellValueFactory(new PropertyValueFactory<Nota,String>("NumeStudent"));
        TableColumn<Nota,String>doi=new TableColumn<>("Tema");
        doi.setCellValueFactory(new PropertyValueFactory<Nota,String>("IdTema"));
        TableColumn<Nota,String>trei=new TableColumn<>("Nota");
        trei.setCellValueFactory(new PropertyValueFactory<Nota,String>("Nota"));
        TableColumn<Nota,String>patru=new TableColumn<>("Feedback");
        patru.setCellValueFactory(new PropertyValueFactory<Nota,String>("Feedback"));
        trei.prefWidthProperty().bind(notaTableView.widthProperty().divide(100/20));
        unu.prefWidthProperty().bind(notaTableView.widthProperty().divide(100/30));
        doi.prefWidthProperty().bind(notaTableView.widthProperty().divide(100/20));
        patru.prefWidthProperty().bind(notaTableView.widthProperty().divide(100/30));

        notaTableView.getColumns().addAll(unu,doi,trei,patru);
        notaTableView.requestLayout();

    }
    @FXML
    public void filtruStudentCombo(){

        temaComboBox.setItems(FXCollections.observableList(StreamSupport.stream(service.getTeme().spliterator(), false).collect(Collectors.toList())));

        StringConverter<Tema>temaStringConverter=new StringConverter<Tema>() {
            @Override
            public String toString(Tema object) {
                if(object==null)
                    return "";
                return object.getIdTema();
            }

            @Override
            public Tema fromString(String string) {
                if(string.equals(""))
                    return null;
                return service.findTemaFromID(string);
            }
        };
        temaComboBox.getItems().add(null);
        temaComboBox.getSelectionModel().selectLast();
        temaComboBox.setConverter(temaStringConverter);
        service.findTemaCurenta().ifPresent(t->temaComboBox.getSelectionModel().select(t));
        ObservableList<String>grupe=FXCollections.observableList(StreamSupport.stream(service.getGrupe().spliterator(), false).collect(Collectors.toList()));
        grupa.setItems(grupe);
       grupa.getItems().add(null);


    }
    @FXML
    public void finalAddHandler() {
        try {
            if(studentTableView.getSelectionModel().getSelectedItem()==null){

                Student s1=service.cautaStudentNume(studentTextField.getText());
                Tema s2=temaComboBox.getSelectionModel().getSelectedItem();
                String nota=notaTextField.getText();
                String feedback=feedbackTextField.getText();
                service.addNota(s1.getIdStudent(),s2.getIdTema(),feedback,nota);


            }
            else
            {
                Student s1=studentTableView.getSelectionModel().getSelectedItem();
                Tema s2=temaComboBox.getSelectionModel().getSelectedItem();
                String nota=notaTextField.getText();
                String feedback=feedbackTextField.getText();

                    service.addNota(s1.getIdStudent(),s2.getIdTema(),feedback,nota);



            }

        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            s.show();

        }
        try{
            filter();
            setPieChartNote();
            setLineChart();
            setPieChartTema();
            initNote();
        }
        catch (Exception e){

        }
    }


    public void setPieChartNote() {
        pieChartNote.getData().clear();
        long cinci=0;
        long zece=0;
        long sapte=0;
      cinci= StreamSupport.stream(service.getToateMediile().spliterator(),false).filter(x->{return x.getMedie()<5;}).count();
       sapte= StreamSupport.stream(service.getToateMediile().spliterator(),false).filter(x->{return (x.getMedie()>=5 & x.getMedie()<=7);}).count();

       zece= StreamSupport.stream(service.getToateMediile().spliterator(),false).filter(x->{return x.getMedie()>7;}).count();
       pieChartNote.setData(FXCollections.observableArrayList(new PieChart.Data("<5",cinci),new PieChart.Data("5-7",sapte),new PieChart.Data(">7",zece)));

    }

    /**--------------------------------------------------------------------------------------------------*/
    public void initStudent(){
        anchorPaneAdaugaStudent.setVisible(false);
        anchorPaneRapoarteStudent.setVisible(true);
        backButtonStudent.setVisible(false);
        backStudent.setVisible(false);
        adaugaStudent.setVisible(true);
        adaugaStudentImagine.setVisible(true);
        adaugaText.setVisible(true);


    }
    public void setAnchorPaneAdaugaStudent(){
        anchorPaneAdaugaStudent.setVisible(true);
        anchorPaneRapoarteStudent.setVisible(false);
        backButtonStudent.setVisible(true);
        backStudent.setVisible(true);
        adaugaStudentImagine.setVisible(false);
        adaugaStudent.setVisible(false);
        adaugaText.setVisible(false);
    }
    @FXML
    public void setPanouStudentiTableView(){
        TableColumn<Student,String>idColumn=new TableColumn<>("ID");
        TableColumn<Student,String>name=new TableColumn<>("Nume");
        TableColumn<Student,String>group=new TableColumn<>("Grupa");
        TableColumn<Student,String>email=new TableColumn<>("Email");
        panouStudentiTableView.getColumns().addAll(idColumn,name,group,email);
        idColumn.prefWidthProperty().bind(panouStudentiTableView.widthProperty().divide(100/15));
        name.prefWidthProperty().bind(panouStudentiTableView.widthProperty().divide(100/25));
        group.prefWidthProperty().bind(panouStudentiTableView.widthProperty().divide(100/15));
        email.prefWidthProperty().bind(panouStudentiTableView.widthProperty().divide(100/45));


        idColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("idStudent"));
        name.setCellValueFactory(new PropertyValueFactory<Student,String>("nume"));
        group.setCellValueFactory(new PropertyValueFactory<Student,String>("grupa"));
        email.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
        panouStudentiTableView.setItems(studentObservableList);
        panouStudentiTableView.requestLayout();
    }
    @FXML
    public void filtruStudent(){
        String nume=numeStudentTextField.getText();
        panouStudentiTableView.getItems().clear();
        panouStudentiTableView.setItems(FXCollections.observableArrayList(StreamSupport.stream(service.getStudentsByName(nume).spliterator(),false).collect(Collectors.toList())));
    }
    @FXML void deselecteazaStudentTable(){
        panouStudentiTableView.getSelectionModel().clearSelection();


    }
    @FXML
    public void selectedStudentChanged(){
        Student student=panouStudentiTableView.getSelectionModel().getSelectedItem();
        if(student!=null)
        {
            idStudentTextField.setText(student.getIdStudent());
            numeStudentTextField.setText(student.getNume());
            grupaStudentTextField.setText(student.getGrupa());
            emailStudentTextField.setText(student.getEmail());
            setLineChart();
        }
        else
        {
            idStudentTextField.clear();
            numeStudentTextField.clear();
            grupaStudentTextField.clear();
            emailStudentTextField.clear();
        }
    }
    @FXML
    public void setAddStudentButton(){
        try {
            String id=idStudentTextField.getText();
            String nume=numeStudentTextField.getText();
            String grupa=grupaStudentTextField.getText();
            String email=emailStudentTextField.getText();

            service.addStudent(id,nume,grupa,email);

            setPanouStudentiTableView();
            initStudent();
            deselecteazaStudentTable();
        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            s.show();
        }
    }
    @FXML
    public void setDeleteStudentButton(){

        try {
            service.deleteStudent(idStudentTextField.getText());

            deselecteazaStudentTable();
             setPanouStudentiTableView();
            initStudent();
        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            s.show();
        }
    }
    @FXML
    public void setUpdateStudentButton(){
        try {
            String id=idStudentTextField.getText();
            String nume=numeStudentTextField.getText();
            String grupa=grupaStudentTextField.getText();
            String email=emailStudentTextField.getText();

            service.updateStudent(id,nume,grupa,email);

            setPanouStudentiTableView();
            deselecteazaStudentTable();
            initStudent();
        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            s.show();
        }
    }
    public LineChart<String, Number> setLineChart(){
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        studentLineChart.getData().clear();
        series1.setName("Note");
        if(service.getNoteStudent().get(service.findStudent(idStudentTextField.getText()))!=null)
        { StreamSupport.stream(service.getNoteStudent().get(service.findStudent(idStudentTextField.getText())).spliterator(),false).sorted(Comparator.comparing(Nota::getIDTemaIntreg)).forEach(x->{
            series1.getData().add(new XYChart.Data<>("Tema"+x.getIdTema(),Double.valueOf(x.getNota())));
            series2.getData().add(new XYChart.Data<>("Tema"+x.getIdTema(),Double.valueOf(x.getNota())));
        });

        studentLineChart.getData().add(series1);}
        LineChart<String, Number>printL=new LineChart<String, Number>(new CategoryAxis(),new NumberAxis());
        printL.getData().add(series2);
        return  printL;
    }

    @FXML
    void printToPdf(){
        PrinterJob printerJob=PrinterJob.createPrinterJob();
        VBox root=new VBox(5);
        root.getChildren().add(new Label("Note curente"));
        WritableImage writableImage=new WritableImage(300,350);
        pieChartNote.snapshot(null ,writableImage);
        Image image=pieChartNote.snapshot(new SnapshotParameters(),null);
        Image image1=notaTableView.snapshot(new SnapshotParameters(),null);
        ImageView imageView= new ImageView();
        ImageView imageView2= new ImageView();
        imageView.setImage(image);
        imageView2.setImage(image1);
        root.getChildren().add(imageView);
        root.getChildren().add(imageView2);
        printerJob.printPage(root);

        printerJob.endJob();
    }
    @FXML
    void setLogout()
    {

    }


}
