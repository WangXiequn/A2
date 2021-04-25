package address.model;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * @author 王协群
 * @date 2021/4/8 14:00
 */
public class Person {
    private final IntegerProperty ID;
    private final StringProperty name;
    private final StringProperty gender;
    private final StringProperty department;
    private final DoubleProperty GPA;
    private final IntegerProperty creditEarned;
    private final ObjectProperty<LocalDate> birthday;

    /**
     * Default constructor.
     */
    public Person() {
        this(0);
    }

    public Person(int id) {
        this.ID = new SimpleIntegerProperty(id);
        // Some initial dummy data, just for convenient testing.
        this.department = new SimpleStringProperty("D1");
        this.name = new SimpleStringProperty("ZhangSan");
        this.gender = new SimpleStringProperty("Male");
        this.GPA = new SimpleDoubleProperty(0.00);
        this.creditEarned = new SimpleIntegerProperty(0);
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }

    public Person(int id, String name, String gender, String department, double gpa, int credit, LocalDate birthday) {
        this.ID = new SimpleIntegerProperty(id);
        this.department = new SimpleStringProperty(department);
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.GPA = new SimpleDoubleProperty(gpa);
        this.creditEarned = new SimpleIntegerProperty(credit);
        this.birthday = new SimpleObjectProperty<LocalDate>(birthday);

    }

    public LocalDate getBirthday() {
        return birthday.get();
    }


    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getDepartment() {
        return department.get();
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public double getGPA() {
        return GPA.get();
    }

    public DoubleProperty GPAProperty() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA.set(GPA);
    }

    public int getCreditEarned() {
        return creditEarned.get();
    }

    public IntegerProperty creditEarnedProperty() {
        return creditEarned;
    }

    public void setCreditEarned(int creditEarned) {
        this.creditEarned.set(creditEarned);
    }
}