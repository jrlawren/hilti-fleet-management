package com.tau.tim.hiltifleetmanagement.Delegates;

import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;

import java.util.ArrayList;

/**
 * Created by Tim on 11/30/2015.
 */
public interface ProjectManagementMainDelegate {
    public void populateProjectListView(ArrayList<Project> projectArrayList);
}
