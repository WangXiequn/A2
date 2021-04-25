package address.view;

import address.MainApp;
import address.model.Person;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * @author 王协群
 * @date 2021/4/18 15:18
 */
public class SearchController {
    TableView<Person> tableView;
    ObservableList<Person> personData;
    int count=0;
    @FXML
    private TextField textField;
    @FXML
    private void handleNext(){
        for (int i = count; i <personData.size(); i++) {
            Person p = personData.get(i);
            String[] list ={p.getID()+"",p.getName(),p.getGender(),p.getDepartment(),p.getGPA()+"",p.getCreditEarned()+"",p.getBirthday().toString()};
            String text =textField.getText();
            for (String s:list){
                if (s.contains(text)){
                    tableView.requestFocus();
                    tableView.getSelectionModel().select(i);
                    tableView.getFocusModel().focus(i);
                    count=i+1;
                    return;
                }
            }
        }
    }
}
