package address.view;

import address.*;

import address.model.Person;
import address.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.*;
/**
 * @author 11383
 */
public class PersonOverviewController {
    @FXML
    private Label IDLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label departmentLabel;
    @FXML
    private Label GPALabel;
    @FXML
    private Label creditEarnedLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private TabPane tabPane;
    public MainApp mainApp;
    private ArrayList<PersonTable> personTableArrayList;
    private PersonTable pT;
   static final String[] HEADERS = {"ID", "Name", "Gender", "Department", "GPA", "Credit Earned", "Birthday"};
    @FXML
    private void initialize() {
        personTableArrayList = new ArrayList<>();
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue==null){
             return;
            }
           for (PersonTable p:personTableArrayList){
               if (newValue.getContent()==p.getTableView()){

                   pT=p;
               }
           }

        });

    }
    public void savePersonDataToFile(File file,ObservableList<Person> personData) {
        try {
            FileWriter fw = new FileWriter(file);
            CSVPrinter printer = new CSVPrinter(fw, CSVFormat.RFC4180.withHeader(HEADERS).withQuoteMode(QuoteMode.ALL));
            for (Person p:personData){
                printer.printRecord(p.getID()+"",p.getName(),p.getGender(),p.getDepartment(),p.getGPA()+"",p.getCreditEarned()+"",p.getBirthday().toString());
            }
            pT.file=file;
            pT.setOnClose(file);
            printer.close();
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Could not save data to file:\n" + file.getPath());
            alert.showAndWait();
        }
    }
    public static boolean whetherEqual(File file, ObservableList<Person> personData) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Demo: How to read a csv file
        Reader in = new FileReader(file);
        CSVParser parser = CSVFormat.RFC4180.withHeader(HEADERS)
                .withFirstRecordAsHeader().withQuoteMode(QuoteMode.ALL).parse(in);
        List<CSVRecord> records = parser.getRecords();
        int count=0;
        for (CSVRecord record : records) {
            int ID = Integer.parseInt(record.get("ID"));
            String name = record.get("Name");
            LocalDate birthday = sdf.parse(record.get("Birthday")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int credit = Integer.parseInt(record.get("Credit Earned"));
            double GPA = Double.parseDouble(record.get("GPA"));
            String gender = record.get("Gender");
            String department = record.get("Department");
            Person p =personData.get(count);
            count++;
            if (ID!=p.getID()||!name.equals(p.getName())||!birthday.toString().equals(p.getBirthday().toString())||credit!=p.getCreditEarned()||GPA!=p.getGPA()||!gender.equals(p.getGender())||!department.equals(p.getDepartment())){
                return false;
            }
        }
        return true;
    }
    public void handleSaveAs(ObservableList<Person> personData) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".csv")) {
                file = new File(file.getPath() + ".csv");
            }
            savePersonDataToFile(file,personData);
        }
    }
    public void addTab(String name){
        ObservableList<Person> personData = FXCollections.observableArrayList();
        PersonTable personTable = new PersonTable(name,this,personData,null);
        tabPane.getTabs().add(personTable.getTab());
        personTableArrayList.add(personTable);
        personTable.getTableView().setItems(personData);
        personTable.getTableView().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        personTable.setPersonOverviewController(this);
        pT = personTable;
        tabPane.getSelectionModel().select(pT.getTab());
    }
    public void addTab(String name,ObservableList<Person> personData,File file){
        PersonTable personTable = new PersonTable(name,this,personData,file);
        tabPane.getTabs().add(personTable.getTab());
        personTableArrayList.add(personTable);
        personTable.getTableView().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        personTable.file = file;
        pT = personTable;
        tabPane.getSelectionModel().select(pT.getTab());
    }
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Demo: How to read a csv file
            Reader in = new FileReader(file);
            CSVParser parser = CSVFormat.RFC4180.withHeader(HEADERS)
                    .withFirstRecordAsHeader().withQuoteMode(QuoteMode.ALL).parse(in);
            List<CSVRecord> records = parser.getRecords();
            ObservableList<Person> personData = FXCollections.observableArrayList();

            for (CSVRecord record : records) {
                int ID = Integer.parseInt(record.get("ID"));
                String name = record.get("Name");
                LocalDate birthday = sdf.parse(record.get("Birthday")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int credit = Integer.parseInt(record.get("Credit Earned"));
                double GPA = Double.parseDouble(record.get("GPA"));
                String gender = record.get("Gender");
                String department = record.get("Department");
                personData.add(new Person(ID,name,gender,department,GPA,credit,birthday));
            }
            addTab(file.getName(),personData,file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Could not load data to file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;


    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            IDLabel.setText(person.getID() + "");
            nameLabel.setText(person.getName());
            genderLabel.setText(person.getGender());
            departmentLabel.setText(person.getDepartment());
            GPALabel.setText(person.getGPA() + "");
            creditEarnedLabel.setText(person.getCreditEarned() + "");
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            IDLabel.setText("");
            nameLabel.setText("");
            genderLabel.setText("");
            departmentLabel.setText("");
            GPALabel.setText("");
            creditEarnedLabel.setText("");
        }
    }

    @FXML
    private void handleNewPerson() {
        if(pT==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
            return;
        }
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
                pT.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = pT.getTableView().getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = pT.getTableView().getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            pT.getTableView().getItems().remove(selectedIndex);
        } else {
            // Nothing selected.

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }

    public PersonTable getpT() {
        return pT;
    }

    public void setpT(PersonTable pT) {
        this.pT = pT;
    }
}

