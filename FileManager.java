import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Names: Ji Young Kim & Sarah McCrie
* Class: PROG24178 1231_18326, Winter 2023
* Assignment: Project
* Date: April 10th, 2023
* Program: FileManager.java
*/

public class FileManager {

    static final String FILENAME = "employees.txt";
    private List<Employee> employees = new ArrayList<Employee>();

    public FileManager() {
        readEmployees();
    }
    
    /**
     * Add a Employee object to a data storage
     * @param employee
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
        writeEmployees();
    }
    
    /**
     * Retrieves all Employee objects from storage
     * @return
     */
    public List<Employee> getAllEmployees() {
        return this.employees;
    }

    /**
     * Removes Employee object from the data based on employees' ID
     * @param id
     */
    public void removeEmployeeByID (String id) {
        Employee foundEmployee = null;
        boolean isMatchingID = false;
        for (Employee employee : employees) {
            if (String.valueOf(employee.getId()).equals(id)) {
                foundEmployee = employee;
                isMatchingID = true;
                break;
            }
        }
        if(isMatchingID == false) {
            Controller control = new Controller();
            control.throwAlert("No Matching ID", "The ID you have entered does not match with an employee in the system. Please try again.");
        }
        if (foundEmployee != null) {
            employees.remove(foundEmployee);
            writeEmployees();
        }
    } //end of removeEmployeeByID method
 
    //loads all added Employee objects from file
    public void readEmployees() {
        try {
            Scanner scanner = new Scanner(new File(FILENAME));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String jobTitle = fields[2];
                Boolean fullTime = Boolean.parseBoolean(fields[3]);
                String gender = fields[4];
                employees.add(new Employee(id, name, jobTitle, fullTime, gender));
            }
            scanner.close();
        }
        catch (Exception e) {
            System.out.println("Error: Could not locate '" + FILENAME + "'. Please add a new employee to create a file.");
        }
    }

    //saves whole employees array to file 
    public void writeEmployees() {
        try {
            PrintWriter writer = new PrintWriter(new File(FILENAME));
            for (Employee employee : employees) {

                writer.println(employee.getId() + "," +
                           employee.getName() + "," +
                           employee.getJobTitle() + "," +
                           employee.isFulltime() + "," +
                           employee.getGender());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing file: " + FILENAME);
        }
    } //end of writeEmployees method
} //end of FileManager class


   
