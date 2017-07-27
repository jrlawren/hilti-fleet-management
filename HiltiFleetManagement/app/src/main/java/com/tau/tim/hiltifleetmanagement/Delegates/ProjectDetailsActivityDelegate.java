package com.tau.tim.hiltifleetmanagement.Delegates;

import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

import java.util.ArrayList;

/**
 * Created by Tim on 12/1/2015.
 */
public interface ProjectDetailsActivityDelegate {
    public void updateProjectFields(Project project);
    public void populateToolListView(ArrayList<Tool> toolArrayList);
}
