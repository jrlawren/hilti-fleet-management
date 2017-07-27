package com.tau.tim.hiltifleetmanagement.ProjectManagement;

/**
 * Created by Tim on 11/3/2015.
 */
public class Project {
    int projectID;

    String projectName;
    String startDate;
    String endDate;

    public Project(){}

    public Project(int projectID, String projectName, String startDate, String endDate){
        this. projectID = projectID;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
