package ais.koutroulis.gr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ais.koutroulis.gr.model.Assignment;
import ais.koutroulis.gr.model.Course;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.service.ServiceCaller;

/**
 * Created by Chris on 08-Aug-16.
 */
public class ContentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<String> items = new ArrayList<>();

        if (ServiceCaller.itemsToShow == null) {
            items.add("No data were found");
        } else if (ServiceCaller.itemsToShow.equals(ServiceCaller.ITEM_ASSIGNMENT)) {
            if (getArguments() != null && getArguments().getSerializable(ServiceCaller.COURSES_KEY) != null) {
                Courses courses = (Courses) getArguments().getSerializable(ServiceCaller.COURSES_KEY);
                List<Course> courseList = courses.getCourses();
                for (Course oneCourse : courseList) {
                    List<Assignment> assignmentsList = oneCourse.getAssignments();

                    for (Assignment oneAssignment : assignmentsList) {
                        items.add(oneAssignment.getIntro());
                    }
                }
            } else {
                items.add("No data were found.");
            }
        } else if (ServiceCaller.itemsToShow.equals(ServiceCaller.ITEM_MESSAGES)) {
            //TODO add one more condition below (... && getArguments().getSerializable(..))
            if (getArguments() != null ) {
                //TODO implement this
                items.add("Inside the Messages fragment");
            } else {
                items.add("No data were found.");
            }
        } else if (ServiceCaller.itemsToShow.equals(ServiceCaller.ITEM_FORUMS)) {
            //TODO add one more condition below (... && getArguments().getSerializable(..))
            if (getArguments() != null) {
                //TODO implement this
                items.add("Inside the Forums fragment");
            } else {
                items.add("No data were found.");
            }
        }


        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new

                LinearLayoutManager(getContext()

        ));
        recyclerView.setAdapter(new

                RecyclerAdapter(items)

        );
        return v;
    }
}
