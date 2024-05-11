import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/* Names: Ji Young Kim & Sarah McCrie
* Class: PROG24178 1231_18326, Winter 2023
* Assignment: Project
* Date: April 10th, 2023
* Program: Controller.java
*/

//this is the file to control the program
public class Controller {
    
    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private CheckBox fullTime;

    @FXML
    private ComboBox<String> jobTitle;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextField deleteId;

    @FXML
    private Button deleteEmployeeButton;

    @FXML
    private Button showEmployeeListButton;
    
    @FXML
    private ListView<String> employeeListDisplay;

    @FXML
    private TextField displayNumberOfEmployees;

    @FXML
    private RadioButton femaleCheck;

    @FXML
    private RadioButton maleCheck;

    @FXML
    private RadioButton otherCheck;

    private FileManager file = new FileManager();
    File textFile = new File(FileManager.FILENAME);
    List<Employee> employees = file.getAllEmployees();
    String employmentStatusString = "";
    String genderString = "";
    Boolean employmentStatusBoolean;

    //code that runs when program opens
    public void initialize() {
        ObservableList<String> jobTitles = FXCollections.observableArrayList("Director", "Manager", "Developer", "Tester", "Salesman");
        jobTitle.setItems(jobTitles);
        if (textFile.exists()) {
            showEmployeeList(null);
        }
    } //end of intialize method
    
    //private helper method to throw an alert - takes string variable
    void throwAlert(String errorTitle, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(errorTitle);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    } //end of throwAlert method

    //private method to determine if employee is part time or full time depending on if checkbox selected
    public boolean getEmploymentStatus() {
        if(fullTime.isSelected()) {
            employmentStatusString = "Full Time";
            employmentStatusBoolean = true;
        }
        else {
            employmentStatusString = "Part Time";
            employmentStatusBoolean = false;
        }
        return employmentStatusBoolean;
    }

    //private method to determine gender based on radio button selected
    public String getGender() {
        if(femaleCheck.isSelected()) {
            genderString = "Female";
        }
        else if(maleCheck.isSelected()) {
            genderString = "Male";
        }
        else if(otherCheck.isSelected()) {
            genderString = "Other";
        }
        else {
            throwAlert("Radio Button Not Selected","You must select an option!");
        }
        return genderString;
    }


    @FXML //event when addEmployee button is pressed - includes validation for adding a valid employee
    void addEmployee(ActionEvent event) {
        if(!checkIfValidId()){
            return;
        }
        if(!checkIfValidName()) {
            throwAlert("Invalid Name", "Name cannot contain any numbers or special characters!");
            return;
        }
        if(!jobTitlePicked()) {
            throwAlert("Job Title Required", "Please input the employee's job title!");
            return;
        } 
        if(!requiredFieldsFilled()) {
            throwAlert("Missing Fields","Could not proceed with adding Employee. Please ensure all fields are filled!");
            return;
        }
        if (requiredFieldsFilled() && checkIfValidId() && checkIfValidName() && jobTitlePicked()) {
            getEmploymentStatus();
            getGender();
            file.addEmployee(new Employee(Integer.valueOf(id.getText()), name.getText(), 
                                          jobTitle.getValue(), employmentStatusBoolean, genderString));
            setTextToBlank();
            showEmployeeList(event);
        }
    } //end of addEmployee method

    //checks if jobTitle has been selected
    public boolean jobTitlePicked() {
        if(jobTitle.getValue() == null) {
            return false;
        }
        else {
            return true;
        } 
    } //end of jobTitlePicked method
    
    //checks if the ID is a valid number
    public boolean checkIfValidId() {
        boolean isIdValid = false;
        try {  
            int idInt = Integer.parseInt(id.getText());
            //check ID is positive int
            if ( idInt < 1) {
                isIdValid = false;
                throwAlert("Invalid ID", "ID must be positive! Please try again using a positive integer.");
                return isIdValid;
            }
            //check if ID is already in use
            for (Employee employee : employees) {
                if(idInt == employee.getId()) {
                    isIdValid = false;
                    throwAlert("Invalid ID", "ID is already in use. Please try again using a different ID number.");
                    return isIdValid;
                }
            }
            isIdValid = true;
        } catch(NumberFormatException e) {  
            throwAlert("Invalid ID", "ID must be a positive integer! Please try again.");
            isIdValid = false;  
        }  
        return isIdValid;
    } //end of checkIfValidId method

    //check that name does not contain any special characters or numbers
    public boolean checkIfValidName() {
        for(int i = 0; i < name.getText().length(); i++) {
            char ch = name.getText().charAt(i);
            if (Character.isLetter(ch) || ch == ' ') {
                continue;
            }
            return false;
        }
        return true;
    } //end of checkIfValidName method

    //check if all fields have been filled
    private boolean requiredFieldsFilled() {
        if(id.getText()=="" || name.getText()=="") {
            return false;
        }
        else{
            return true;
        } 
    } //end of requiredFieldsFilled method

    @FXML //event handler for clearInput button
    void clearInput(ActionEvent event) {
        setTextToBlank();
    }

    //set fields to blank/empty when called
    private void setTextToBlank() {
        id.setText(null);
        name.setText(null);
        jobTitle.valueProperty().set(null);
        fullTime.setSelected(false);
        femaleCheck.setSelected(false);
        maleCheck.setSelected(false);
        otherCheck.setSelected(true);
    }  //end of setTextToBlank method

    @FXML //event when deleteEmployee button is pressed
    void deleteEmployee(ActionEvent event) {
        try{
            file.removeEmployeeByID(deleteId.getText());
        }
        catch(IllegalArgumentException err) {
            deleteId.setPromptText("Illegal ID - Please enter valid employee ID");
        }
        deleteId.setText("");
        showEmployeeList(event);
    } //end of deleteEmployee method

    @FXML //showEmployeeList button press event
    void showEmployeeList(ActionEvent event) {
        if(textFile.exists()){
            employeeListDisplay();
        }
        else{
            throwAlert("No File Found","File does not exist. Please enter employee data to create a new file");
        }
    } //end of showEmployeeList method/event handler

    //shows contents of file in list format
    private void employeeListDisplay() {
        ObservableList<String> doList = FXCollections.observableArrayList();
        int numberOfEmployees = 0;
        
        for ( Employee employee : employees) {
            doList.add(employee.toString());
            numberOfEmployees++;
        }

        employeeListDisplay.setItems(doList);
        displayNumberOfEmployees.setText(String.valueOf(numberOfEmployees));
    } //end of employeeListDisplay method
} //end of Controller class