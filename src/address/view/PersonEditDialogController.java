package address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



import address.model.Person;
import address.util.DateUtil;

import java.util.regex.Pattern;

/**
 * Dialog to edit details of a person.
 *
 * @author Marco Jakob
 */
public class PersonEditDialogController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField GPAField;
    @FXML
    private TextField CreditEarnedField;
    @FXML
    private TextField IDField;
    @FXML
    private TextField departmentField;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private DatePicker birthDate;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;
    Pattern integerPattern = Pattern.compile("[0-9]*");
    Pattern GPAPattern = Pattern.compile("([4][.][0])|([0-3][.][0-9][0-9])|([0-3][.][0-9])");
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        gender.getItems().add("Male");
        gender.getItems().add("Female");
        gender.getSelectionModel().selectFirst();

    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;
        IDField.setText(person.getID()+"");
        GPAField.setText(person.getGPA()+"");
        CreditEarnedField.setText(person.getCreditEarned()+"");
        nameField.setText(person.getName());
        departmentField.setText(person.getDepartment());
        birthDate.setValue(person.getBirthday());
        gender.setValue(person.getGender());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setName(nameField.getText());
            person.setCreditEarned(Integer.parseInt(CreditEarnedField.getText()));
            person.setID(Integer.parseInt(IDField.getText()));
            person.setDepartment(departmentField.getText());
            person.setGPA(Double.parseDouble(GPAField.getText()));
            person.setBirthday(birthDate.getValue());
            birthDate.getValue();
            person.setGender(gender.getValue());
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (IDField.getText() == null || IDField.getText().length() == 0||!integerPattern.matcher(IDField.getText()).matches()) {
            errorMessage += "No valid ID!\n";
        }
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }
        if (GPAField.getText() == null || GPAField.getText().length() == 0||!GPAPattern.matcher(GPAField.getText()).matches()) {
            errorMessage += "No valid GPA!\n";
        }
        if (CreditEarnedField.getText() == null || CreditEarnedField.getText().length() == 0||!integerPattern.matcher(CreditEarnedField.getText()).matches()) {
            errorMessage += "No valid credit!\n";
        }
        if (birthDate.getEditor().getText() == null || birthDate.getEditor().getText().length() == 0) {
            errorMessage += "No valid Birthday!\n";
        }
        if (departmentField.getText() == null || departmentField.getText().length() == 0) {
            errorMessage += "No valid department!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}