package com.tau.tim.hiltifleetmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;
import com.tau.tim.hiltifleetmanagement.R;

import java.util.ArrayList;

/**
 * Created by Tim on 11/3/2015.
 */
public class ProjectAdapter extends ArrayAdapter<Project> {
    public ProjectAdapter(Context context, ArrayList<Project> project) {
        super(context, 0, project);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project projectRow = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.project_adapter_layout, parent, false);
        }

        TextView projectLabel = (TextView) convertView.findViewById(R.id.projectLabel);
        TextView startLabel = (TextView) convertView.findViewById(R.id.startLabel);
        TextView endLabel = (TextView) convertView.findViewById(R.id.endLabel);

        projectLabel.setText(projectRow.getProjectName());
        startLabel.setText(projectRow.getStartDate());
        endLabel.setText(projectRow.getEndDate());

        return convertView;
    }


}
