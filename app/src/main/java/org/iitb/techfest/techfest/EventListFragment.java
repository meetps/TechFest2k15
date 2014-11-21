package org.iitb.techfest.techfest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EventListFragment extends Fragment {

    public EventListFragment(){
    }

    public static final EventListFragment newInstance(String title, int actionbar_color, int description_layout, ArrayList<EventSummary> event_list){
        EventListFragment el = new EventListFragment();
        Bundle args = new Bundle();

        args.putString("title", title);
        args.putInt("actionbar_color", actionbar_color);
        args.putInt("description_layout", description_layout);
        args.putParcelableArrayList("event_list", event_list);

        el.setArguments(args);
        return el;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args=getArguments();

        int layout=R.layout.template_event_list;

        View rootView = inflater.inflate(layout, container, false);
        ViewGroup content = (LinearLayout) rootView.findViewById(R.id.content);
        View descriptionLayout = inflater.inflate(args.getInt("description_layout"), content, false);

        ArrayList<EventSummary> event_list = args.getParcelableArrayList("event_list");

        content.addView(descriptionLayout);

        for(EventSummary es: event_list){
            LinearLayout summary_container = (LinearLayout)inflater.inflate(R.layout.container_event_summary, null);

            ((ImageView)summary_container.findViewById(R.id.event_image)).setImageResource(es.image_id);
            ((TextView)summary_container.findViewById(R.id.event_title)).setText(es.title);
            ((TextView)summary_container.findViewById(R.id.event_description)).setText(es.description);
            summary_container.setId(es.id);

            content.addView(summary_container);
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getString("title"),
                getArguments().getInt("actionbar_color"));
    }
}
