package com.example.examen2eventos.app1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.examen2eventos.R;

import java.util.List;
import java.util.Map;

public class ScheduleAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<String> daysList;
    private final Map<String, List<Subject>> subjectsMap;

    public ScheduleAdapter(Context context, List<String> daysList, Map<String, List<Subject>> subjectsMap) {
        this.context = context;
        this.daysList = daysList;
        this.subjectsMap = subjectsMap;
    }

    @Override
    public int getGroupCount() {
        return daysList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String day = daysList.get(groupPosition);
        return subjectsMap.get(day).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return daysList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String day = daysList.get(groupPosition);
        return subjectsMap.get(day).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String day = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
        }
        TextView tvGroup = convertView.findViewById(R.id.tv_group);
        tvGroup.setText(day);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Subject subject = (Subject) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.child_item, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tv_child_name);
        TextView tvDetails = convertView.findViewById(R.id.tv_child_details);

        tvName.setText(subject.getName());
        tvDetails.setText("Horario: " + subject.getHours());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
