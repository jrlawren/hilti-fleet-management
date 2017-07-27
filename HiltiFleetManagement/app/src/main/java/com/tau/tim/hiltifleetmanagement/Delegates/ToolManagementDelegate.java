package com.tau.tim.hiltifleetmanagement.Delegates;

import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

import java.util.ArrayList;

/**
 * Created by Tim on 12/1/2015.
 */
public interface ToolManagementDelegate {
    public void updateToolList(ArrayList<Tool> toolArrayList, String activity, String whichAdapter);
}
