package com.tau.tim.hiltifleetmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tau.tim.hiltifleetmanagement.R;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;
import com.tau.tim.hiltifleetmanagement.ToolManagement.ToolSchedule;

import java.util.ArrayList;

/**
 * Created by Tim on 11/5/2015.
 */
public class ToolAdapter extends ArrayAdapter<Tool>{
    public static final String LIST_TOOL_DETAILS = "List Tool Details";
    public static final String LIST_PROJECTS = "List Projects";
    public static final String LIST_TOOLS = "List Tools";

    private String layoutType = "";

    public ToolAdapter(Context context, ArrayList<Tool> tools, String layoutType) {
        super(context, 0, tools);
        this.layoutType = layoutType;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tool toolRow = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null && layoutType.equals(LIST_TOOL_DETAILS)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tool_detail_adapter_layout, parent, false);
        }else if(convertView == null && layoutType.equals(LIST_PROJECTS)){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tool_project_detail_adapter_layout, parent, false);
        }else if (convertView == null && layoutType.equals(LIST_TOOLS)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tool_list_adapter_layout, parent, false);
        }

        if(layoutType.equals(LIST_TOOLS)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tool_list_adapter_layout, parent, false);
            TextView toolNameLabel = (TextView) convertView.findViewById(R.id.toolNameLabel);
            TextView toolModelLabel = (TextView) convertView.findViewById(R.id.toolModelLabel);
            TextView toolDescrLabel = (TextView) convertView.findViewById(R.id.toolDescrLabel);
            TextView toolQtyLabel = (TextView) convertView.findViewById(R.id.toolQtyLabel);

            toolNameLabel.setText(toolRow.getToolName().toString());
            toolModelLabel.setText(" v." + toolRow.getModelNumber().toString());
            toolDescrLabel.setText(toolRow.getDescription().toString());
            toolQtyLabel.setText("Qty: " + Integer.toString(toolRow.getToolCount()));
        }else if(layoutType.equals(LIST_PROJECTS)){
            ToolSchedule scheduleRow = (ToolSchedule) toolRow;

            TextView projectIDTextView = (TextView) convertView.findViewById(R.id.projectIDTextView);
            TextView projectNameTextView = (TextView) convertView.findViewById(R.id.projectNameTextView);
            TextView startDateTextView = (TextView) convertView.findViewById(R.id.startDateTextView);
            TextView endDateTextView = (TextView) convertView.findViewById(R.id.endDateTextView);

            projectIDTextView.setText(Integer.toString(scheduleRow.getRentalID()));
            projectNameTextView.setText(scheduleRow.getProjectName());
            startDateTextView.setText("Rental Start: " + scheduleRow.getStartDate());
            endDateTextView.setText("Rental End: " + scheduleRow.getEndDate());
        }else if(layoutType.equals(LIST_TOOL_DETAILS)){
            TextView toolIDText = (TextView) convertView.findViewById(R.id.toolIDText);
            TextView toolNameText = (TextView) convertView.findViewById(R.id.toolNameText);
            TextView modelNoText = (TextView) convertView.findViewById(R.id.modelNoText);
            TextView ownershipText = (TextView) convertView.findViewById(R.id.ownershipText);
            TextView statusText = (TextView) convertView.findViewById(R.id.stausText);
            TextView purchaseDateText = (TextView) convertView.findViewById(R.id.purchaseDateText);
            TextView toolReturnDate = (TextView) convertView.findViewById(R.id.toolReturnDate);

            toolIDText.setText(Integer.toString(toolRow.getToolID()));
            toolNameText.setText(toolRow.getToolName());
            modelNoText.setText("v." + toolRow.getModelNumber());
            ownershipText.setText(toolRow.getOwnership());
            statusText.setText(toolRow.getStatus());
            if(toolRow.getOwnership().equals("Leased")) {
                purchaseDateText.setText("Leased From: " + toolRow.getPurchaseDate());
                toolReturnDate.setText(("Lease End:      " + toolRow.getReturnDate()));
                toolReturnDate.setVisibility(View.VISIBLE);
            }else {
                purchaseDateText.setText("Purchased: " + toolRow.getPurchaseDate());
                toolReturnDate.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}
