package address.view;

import address.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import jdk.management.resource.ResourceType;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

/**
 * @author 11383
 */
public class PersonTable{
    private TableView<Person> tableView;
    private TableColumn<Person,Integer> id;
    private TableColumn<Person,String> name;
    private TableColumn<Person,String> department;
    private TableColumn<Person,Double> GPA;
    private Tab tab;
    private PersonOverviewController personOverviewController;
    public Tab getTab() {
        return tab;
    }
    public File file;

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public PersonOverviewController getPersonOverviewController() {
        return personOverviewController;
    }

    public void setPersonOverviewController(PersonOverviewController personOverviewController) {
        this.personOverviewController = personOverviewController;
    }
    public void setOnClose(File file){
        tab.setOnCloseRequest(t -> {
            if (file!=null){
                try {
                    if (PersonOverviewController.whetherEqual(file,personData)){
                        return;
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
            personOverviewController.handleSaveAs(this.personData);
        });
    }

    PersonTable(String tabName,PersonOverviewController p,ObservableList<Person> personData,File file){
        tableView = new TableView<>();
        id = new TableColumn<>();
        name = new TableColumn<>();
        department = new TableColumn<>();
        GPA = new TableColumn<>();
        creditEarned = new TableColumn<>();
        tableView.getColumns().addAll(id,name,department,GPA,creditEarned);
        tab= new Tab();
        tab.setContent(tableView);
        id.setText("ID");
        name.setText("Name");
        department.setText("Department");
        GPA.setText("GPA");
        creditEarned.setText("Credit Earned");
        tab.setText(tabName);
        this.personOverviewController=p;
        tableView.setItems(personData);
        this.personData=personData;
        this.file=file;
        tab.setOnCloseRequest(t -> {
            if (file!=null){
                try {
                    System.out.println(file.getAbsolutePath());
                    if (PersonOverviewController.whetherEqual(file,personData)){
                        System.out.println("Log from close");
                        return;
                    }else {
                        System.out.println("not equal");
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "save changes?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();if (alert.getResult() == ButtonType.YES) {
                personOverviewController.handleSaveAs(this.personData);
            }
        });
        getId().setCellValueFactory(
                cellData -> cellData.getValue().IDProperty().asObject());
        getName().setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        getCreditEarned().setCellValueFactory(
                cellData -> cellData.getValue().creditEarnedProperty().asObject()
        );
        getDepartment().setCellValueFactory(
                cellData -> cellData.getValue().departmentProperty()
        );
        getGPA().setCellValueFactory(
                cellData -> cellData.getValue().GPAProperty().asObject()
        );
    }
    public TableView<Person> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Person> tableView) {
        this.tableView = tableView;
    }

    public TableColumn<Person, Integer> getId() {
        return id;
    }

    public void setId(TableColumn<Person, Integer> id) {
        this.id = id;
    }

    public TableColumn<Person, String> getName() {
        return name;
    }

    public void setName(TableColumn<Person, String> name) {
        this.name = name;
    }

    public TableColumn<Person, String> getDepartment() {
        return department;
    }

    public void setDepartment(TableColumn<Person, String> department) {
        this.department = department;
    }

    public TableColumn<Person, Double> getGPA() {
        return GPA;
    }

    public void setGPA(TableColumn<Person, Double> GPA) {
        this.GPA = GPA;
    }

    public TableColumn<Person, Integer> getCreditEarned() {
        return creditEarned;
    }

    public void setCreditEarned(TableColumn<Person, Integer> creditEarned) {
        this.creditEarned = creditEarned;
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public void setPersonData(ObservableList<Person> personData) {
        this.personData = personData;
    }

    private TableColumn<Person,Integer> creditEarned;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
}