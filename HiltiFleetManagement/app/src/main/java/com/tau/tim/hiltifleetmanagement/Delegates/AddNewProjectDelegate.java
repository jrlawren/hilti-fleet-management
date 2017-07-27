package com.tau.tim.hiltifleetmanagement.Delegates;

import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;

/**
 * Created by Tim on 11/30/2015.
 */
public interface AddNewProjectDelegate {
    public void populateProjectName(Project project);
    public void saveProjectThread(String errorMessage);
    public String preSaveProjectCheck();
    public void showErrorMessage(String errorMessage);
    public String getProjectName();
    public String getStartDate();
    public String getEndDate();
    public boolean getUpdate();
    public void finishActivity();
}
