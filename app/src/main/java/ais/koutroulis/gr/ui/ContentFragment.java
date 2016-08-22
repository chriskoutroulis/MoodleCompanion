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
import ais.koutroulis.gr.model.Message;
import ais.koutroulis.gr.model.Messages;
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
            if (getArguments() != null && getArguments().getSerializable(ServiceCaller.BUNDLE_COURSES_KEY) != null) {
                Courses courses = (Courses) getArguments().getSerializable(ServiceCaller.BUNDLE_COURSES_KEY);
                List<Course> courseList = courses.getCourses();
                for (Course oneCourse : courseList) {
                    List<Assignment> assignmentsList = oneCourse.getAssignments();

                    for (Assignment oneAssignment : assignmentsList) {
                        StringBuilder assignmentStringBuuilder = new StringBuilder();
                        assignmentStringBuuilder.append(oneAssignment.getName() + "\n\n");
                        assignmentStringBuuilder.append(oneAssignment.getIntro().substring(3, oneAssignment.getIntro().length() - 4));
                        items.add( assignmentStringBuuilder.toString());
                    }
                }

                //if needed there is code implemented for distinguishing the current assignments
                //in the RetrofitMoodleClient.scanForCurrentAssignments(...).
                //That method is intented to be used for background scanning and providing a notification.

            } else {
                items.add("No data were found.");
            }
        } else if (ServiceCaller.itemsToShow.equals(ServiceCaller.ITEM_MESSAGES)) {
            //TODO add one more condition below (... && getArguments().getSerializable(..))
            if (getArguments() != null && getArguments().getSerializable(ServiceCaller.BUNDLE_UNREAD_MESSAGES_KEY) != null) {
                //TODO implement this
                Messages unReadMessages = (Messages) getArguments().getSerializable(ServiceCaller.BUNDLE_UNREAD_MESSAGES_KEY);
                List<Message> unreadMessageList = unReadMessages.getMessages();

                Messages readMessages = (Messages) getArguments().getSerializable(ServiceCaller.BUNDLE_READ_MESSAGES_KEY);
                List<Message> readMessageList = readMessages.getMessages();

                if (unreadMessageList.size() == 0) {
                    items.add("There are no Unread Messages");
                } else {
                    for (Message oneMessage : unreadMessageList) {

                        items.add(oneMessage.getSmallmessage());
                    }
                }
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
