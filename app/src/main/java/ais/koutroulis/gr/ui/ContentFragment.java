package ais.koutroulis.gr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ais.koutroulis.gr.model.Assignment;
import ais.koutroulis.gr.model.Course;
import ais.koutroulis.gr.model.CourseToDisplay;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.DiscussionToDisplay;
import ais.koutroulis.gr.model.ForumToDisplay;
import ais.koutroulis.gr.model.Message;
import ais.koutroulis.gr.model.Messages;
import ais.koutroulis.gr.model.Post;
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
            items.add(getActivity().getString(R.string.no_data));
        } else if (ServiceCaller.itemsToShow.equals(ServiceCaller.ITEM_ASSIGNMENT)) {
            if (getArguments() != null && getArguments().getSerializable(ServiceCaller.BUNDLE_COURSES_KEY) != null) {
                Courses courses = (Courses) getArguments().getSerializable(ServiceCaller.BUNDLE_COURSES_KEY);
                List<Course> courseList = courses.getCourses();
                for (Course oneCourse : courseList) {
                    List<Assignment> assignmentsList = oneCourse.getAssignments();

                    StringBuilder assignmentStringBuilder = new StringBuilder();
                    assignmentStringBuilder.append(getActivity().getString(R.string.course_name)
                            + " " + oneCourse.getFullname() + "\n\n");

                    for (Assignment oneAssignment : assignmentsList) {

                        int dueDateInEpoch = oneAssignment.getDuedate();
                        long dueDateInMillis = dueDateInEpoch * 1000L;
                        Locale myLocale = Locale.forLanguageTag("el_GR");

                        Calendar dueDate = new GregorianCalendar(TimeZone.getDefault(), myLocale);
                        dueDate.setTimeInMillis(dueDateInMillis);


                        assignmentStringBuilder.append(getActivity().getString(R.string.due_date)
                                + " " + DateFormat.getDateInstance().format(dueDate.getTime())
                                + "\n\n");
                        assignmentStringBuilder.append(getActivity().getString(R.string.assignment_title)
                                + " " + oneAssignment.getName() + "\n\n");
                        assignmentStringBuilder.append(html2text(oneAssignment.getIntro()));
                        items.add(assignmentStringBuilder.toString());
                    }
                }

                //if needed there is code implemented for distinguishing the current assignments
                //in the RetrofitMoodleClient.scanForCurrentAssignments(...).
                //That method is intented to be used for background scanning and providing a notification.

            } else {
                items.add(getActivity().getString(R.string.no_data));
            }
        } else if (ServiceCaller.itemsToShow.equals(ServiceCaller.ITEM_MESSAGES)) {
            //TODO add one more condition below (... && getArguments().getSerializable(..))
            if (getArguments() != null && getArguments().getSerializable(ServiceCaller.BUNDLE_UNREAD_MESSAGES_KEY) != null) {
                //TODO implement this
                Messages unReadMessages = (Messages) getArguments().getSerializable(ServiceCaller.BUNDLE_UNREAD_MESSAGES_KEY);
                List<Message> unreadMessageList = unReadMessages.getMessages();

                Messages readMessages = (Messages) getArguments().getSerializable(ServiceCaller.BUNDLE_READ_MESSAGES_KEY);
                List<Message> readMessageList = readMessages.getMessages();

                StringBuilder unreadMessageStringBuilder = new StringBuilder();

                if (unreadMessageList.size() == 0 && readMessageList.size() == 0) {
                    items.add(getActivity().getString(R.string.no_private_messages));
                } else {
                    for (Message oneMessage : unreadMessageList) {

                        unreadMessageStringBuilder.append(getActivity().getString(R.string.new_message_header));
                        unreadMessageStringBuilder.append(oneMessage.getSmallmessage());

                        items.add(unreadMessageStringBuilder.toString());
                    }

                    for (Message oneMessage : readMessageList) {
                        items.add(oneMessage.getSmallmessage());
                    }
                }
            } else {
                items.add(getActivity().getString(R.string.no_data));
            }
        } else if (ServiceCaller.itemsToShow.equals(ServiceCaller.ITEM_FORUMS)) {
            //TODO add one more condition below (... && getArguments().getSerializable(..))
            if (getArguments() != null) {

                List<Post> postList = new ArrayList<>();

                List<CourseToDisplay> courseToDisplayList = (List<CourseToDisplay>) getArguments()
                        .getSerializable(ServiceCaller.BUNDLE_FORUM_POSTS_KEY);

                //Gather all Forum posts in a list
                if (courseToDisplayList != null) {
                    if (courseToDisplayList.size() == 0) {
                        items.add(getActivity().getString(R.string.no_forum_posts));
                    } else {
                        for (CourseToDisplay oneCourse : courseToDisplayList) {
                            List<ForumToDisplay> forumToDisplayList = oneCourse.getForumToDisplayList();
                            for (ForumToDisplay oneForum : forumToDisplayList) {
                                List<DiscussionToDisplay> discussionToDisplayListList = oneForum.getDiscussionToDisplayList();
                                for (DiscussionToDisplay oneDiscussion : discussionToDisplayListList) {
                                    postList.addAll(oneDiscussion.getPostList());
                                }
                            }
                        }
                    }
                    for (Post onePost : postList) {
                        items.add(html2text(onePost.getMessage()));
                    }
                } else {
                    items.add(getActivity().getString(R.string.no_data));
                }

            } else {
                items.add(getActivity().getString(R.string.no_data));
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

    private String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
