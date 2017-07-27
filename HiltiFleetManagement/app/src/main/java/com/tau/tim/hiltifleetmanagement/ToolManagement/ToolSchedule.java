package com.tau.tim.hiltifleetmanagement.ToolManagement;

import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

/**
 * Created by Tim on 11/5/2015.
 */
public class ToolSchedule extends Tool {
    private int rentalID = 0;
    private int projectID = 0;
    private String projectName = "";
    private String startDate = "";
    private String endDate = "";

    public ToolSchedule(int rentalID, int projectID, String projectName, String startDate, String endDate){
        super();
        this.rentalID = rentalID;
        this.projectID = projectID;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }
}
