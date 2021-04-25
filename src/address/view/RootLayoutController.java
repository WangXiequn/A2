package address.view;

import address.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


/**
 * @author 11383
 */
public class RootLayoutController {


    private MainApp mainApp;
    static int count=0;
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
    @FXML
    private void handleNew() {

        mainApp.getPersonOverviewController().addTab("Table "+count);
        count++;
    }
    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.getPersonOverviewController().loadPersonDataFromFile(file);
        }
    }
    @FXML
    private void handleSave(){
       try {
           mainApp.getPersonOverviewController().handleSaveAs(mainApp.getPersonOverviewController().getpT().getPersonData());
       }catch (NullPointerException e){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("No Table");
           alert.setHeaderText("No table Selected");
           alert.setContentText("Please create a table first.");
           alert.showAndWait();
       }
    }
    @FXML
    private void handleSearch(){
try {

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(MainApp.class.getResource("view/Search.fxml"));
    AnchorPane page = (AnchorPane) loader.load();

    // Create the dialog Stage.
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Edit Person");
    dialogStage.initModality(Modality.WINDOW_MODAL);
    dialogStage.initOwner(mainApp.getPrimaryStage());
    Scene scene = new Scene(page);
    dialogStage.setScene(scene);

    // Set the person into the controller.
    SearchController controller = loader.getController();
    controller.tableView=mainApp.getPersonOverviewController().getpT().getTableView();
    controller.personData=mainApp.getPersonOverviewController().getpT().getPersonData();
    // Show the dialog and wait until the user closes it
    dialogStage.showAndWait();
}catch (IOException e){
    e.printStackTrace();
}

    }


}
