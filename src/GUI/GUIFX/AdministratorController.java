package GUI.GUIFX;

import Domain.TipUtilizator;
import GUI.CatalogEvent;
import GUI.EntityType;
import GUI.Observer;
import GUI.SubAlert;
import Profiles.Utilizator;
import Service.CatalogService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdministratorController implements Observer<CatalogEvent> {
    private Utilizator user;
    private CatalogService service;
    private ObservableList<Utilizator>utilizatorObservableList;
    @FXML TableView<Utilizator> utilizatoriTableView;
    @FXML Button buttonAdd;
    @FXML Button buttonDelete;
    @FXML Button buttonUpdate;
    @FXML TextField nume;
    @FXML TextField numar;
    @FXML PasswordField parola;
    @FXML ComboBox<TipUtilizator>tipUtilizatorComboBox;
    @FXML Stage stage;
    @FXML Label welcome;
    @FXML Stage parentStage;
    @FXML Text numarText;




    @Override
    public void update(CatalogEvent catalogEvent) {
        if(catalogEvent.getChangeEventType()== EntityType.Utilizator){
            switch (catalogEvent.getUtilizatorEvent().getChangeEventType().toString()){
                case "ADD":{
                    utilizatorObservableList.add(catalogEvent.getUtilizatorEvent().getNewData());
                    break;
                }
                case "UPDATE":{

                    utilizatorObservableList.remove(catalogEvent.getUtilizatorEvent().getOldData());
                    utilizatorObservableList.add(catalogEvent.getUtilizatorEvent().getNewData());

                    break;
                }
                case "DELETE":{
                    utilizatorObservableList.remove(catalogEvent.getUtilizatorEvent().getOldData());
                    break;
                }
            }
        }
    }
    @FXML
    public void close(){
        stage.close();
    }
    @FXML
    void logOut(){
        stage.close();
        parentStage.show();
    }
    public void set(Utilizator user, CatalogService service,Stage stage1,Stage parentStage) {
        this.user=user;
        this.service=service;
        stage=stage1;
        this.parentStage=parentStage;
        utilizatorObservableList= FXCollections.observableList(StreamSupport.stream(service.getUtilizatori().spliterator(), false).collect(Collectors.toList()));
        setUtilizatoriTableView();
        setTipUtilizatorComboBox();
        welcome.setText("Bine ai venit, "+user.getNume());
    }
    public void setUtilizatoriTableView(){
        utilizatoriTableView.getColumns().clear();
        TableColumn<Utilizator,String> unu=new TableColumn<>("Nume");
        unu.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("Nume"));
        TableColumn<Utilizator,String>doi=new TableColumn<>("ID*");
        doi.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("NrMatricol"));
        TableColumn<Utilizator,String>trei=new TableColumn<>("Tip utilizator");
        trei.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("TipUtilizator"));
        unu.prefWidthProperty().bind(utilizatoriTableView.widthProperty().divide(100/40));
        doi.prefWidthProperty().bind(utilizatoriTableView.widthProperty().divide(100/20));
        trei.prefWidthProperty().bind(utilizatoriTableView.widthProperty().divide(100/40));

        utilizatoriTableView.getColumns().addAll(unu,doi,trei);

        utilizatoriTableView.setItems(FXCollections.observableList(StreamSupport.stream(service.filtru(nume.getText(),tipUtilizatorComboBox.getSelectionModel().getSelectedItem()).spliterator(),false).collect(Collectors.toList())));
        utilizatoriTableView.requestLayout();
    }
    public void selectionChanged(){
        if(utilizatoriTableView.getSelectionModel().getSelectedItem()!=null){
            Utilizator utilizator=utilizatoriTableView.getSelectionModel().getSelectedItem();
            nume.setText(utilizator.getNume());
            numar.setText(utilizator.getNrMatricol());
            tipUtilizatorComboBox.getSelectionModel().select(utilizator.getTipUtilizator());


        }
        else
        {
            nume.setText("");
            numar.setText("");
            tipUtilizatorComboBox.getSelectionModel().clearSelection();
        }
    }
    public void setTipUtilizatorComboBox(){
        ObservableList<TipUtilizator>list=FXCollections.observableList(new ArrayList<>());
        for (TipUtilizator value : TipUtilizator.values()) {
            list.add(value);
        }
        list.add(null);
        tipUtilizatorComboBox.setItems(list);
    }
    public void tipUtilizatorChanged(){
        if(tipUtilizatorComboBox.getSelectionModel().getSelectedItem()==null){
            numar.setVisible(false);
            numarText.setVisible(false);
        }
        else {
            if(tipUtilizatorComboBox.getSelectionModel().getSelectedItem().equals(TipUtilizator.STUDENT)){
                numarText.setVisible(true);
                numar.setVisible(true);
            }
            else
            {
                numar.setVisible(false);
                numarText.setVisible(false);
            }
        }
        setUtilizatoriTableView();
    }
    public void setButtonAdd(){
        try {
            String numeT=nume.getText();
            String numarT=numar.getText();
            String pass=parola.getText();
            String tip=tipUtilizatorComboBox.getSelectionModel().getSelectedItem().toString();
            service.addUtilizator(numeT,numarT,pass,tip);
            setUtilizatoriTableView();
            deselect();
        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            e.printStackTrace();
            s.show();
        }
    }
    public void setButtonDelete (){
        try {
            String numeT=nume.getText();
            service.deleteUtilizator(numeT);
            setUtilizatoriTableView();
            deselect();
        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            s.show();
        }
    }
    public void setButtonUpdate(){
        try {
            String numeT=nume.getText();
            String numarT=numar.getText();
            String pass=parola.getText();
            String tip=tipUtilizatorComboBox.getSelectionModel().getSelectedItem().toString();
            if(pass==null)
                pass=service.findUtilizator(numeT).getParola();
            service.updateUtilizator(numeT,pass,numarT,tip);
            setUtilizatoriTableView();
            deselect();
        } catch (Exception e) {
            SubAlert s=new SubAlert(Alert.AlertType.ERROR,"Eroare","Verificati datele introduse!");
            s.show();
        }
    }
    void deselect(){
        nume.clear();
        tipUtilizatorComboBox.getSelectionModel().clearSelection();
        parola.clear();
        numar.clear();
    }
}
