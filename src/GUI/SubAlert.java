package GUI;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Modality;


public class SubAlert extends Alert
{
    {
        setHeaderText("");
        initModality(Modality.APPLICATION_MODAL);
/*
        getDialogPane().getStylesheets().add(getClass().getResource("../GUIFX/dialog.css").toExternalForm());
        getDialogPane().getStyleClass().add("dialog");
    */
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    }

    SubAlert(AlertType alertType)
    {
        super(alertType);
    }
    public SubAlert(AlertType type,String title,String content)
    {
        super(type);
        setTitle(title);
        setContentText(content);

    }
}