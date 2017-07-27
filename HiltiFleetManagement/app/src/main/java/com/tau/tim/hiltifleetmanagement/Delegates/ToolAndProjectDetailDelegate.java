package com.tau.tim.hiltifleetmanagement.Delegates;

import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;
import com.tau.tim.hiltifleetmanagement.ToolManagement.ToolSchedule;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Tim on 12/1/2015.
 */
public interface ToolAndProjectDetailDelegate {
    public void setToolListView(ArrayList<Tool> toolArrayList);
    public void setScheduleListView(ArrayList<? extends Tool> scheduleArrayList);
}
