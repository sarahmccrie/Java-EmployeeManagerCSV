/* Names: Ji Young Kim & Sarah McCrie
* Class: PROG24178 1231_18326, Winter 2023
* Assignment: Project
* Date: April 10th, 2023
* Program: Employee.java
*/

//Employee class represents an employee
public class Employee {
    private int id;
    private String name;
    private String jobTitle;
    private Boolean fulltime;
    private String gender;
    
    public Employee(int id, String name, String jobTitle, Boolean fullTime, String gender) {
        this.id = id;
        this.name = name;
        this.jobTitle = jobTitle;
        this.fulltime = fullTime;
        this.gender = gender;
    } //end of Employee constructor


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Boolean isFulltime() {
        return fulltime;
    }

    public void setFulltime(Boolean fulltime) {
        this.fulltime = fulltime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    @Override
    public String toString() {
        return "Employee:\n " +
                "ID: " + id +
                ", Name: " + name + 
                ", Job Title: " + jobTitle +
                ", Status: " + determineEmploymentStatus(fulltime) +
                ", Gender: " + gender;
    }


    private String determineEmploymentStatus(Boolean isFullTime) {
        if(isFullTime){
            return "Full Time";
        }
        return "Part Time";
    }
} //end of Employee class
