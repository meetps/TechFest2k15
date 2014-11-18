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

public class EventDetailsFragment extends Fragment {

    public EventDetailsFragment(){
    }

    public static final EventDetailsFragment newInstance(int section_number, int image_id, int description_layout, String venue, String time, String date){
        EventDetailsFragment el = new EventDetailsFragment();
        Bundle args = new Bundle();

        args.putInt("section_number", section_number);
        args.putInt("image_id", image_id);
        args.putInt("description_layout", description_layout);
        args.putString("venue",venue);
        args.putString("time",time);
        args.putString("date",date);

        el.setArguments(args);
        return el;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args=getArguments();

        int layout=R.layout.template_event_list;

        View rootView = inflater.inflate(layout, container, false);
        ((ImageView) rootView.findViewById(R.id.header_image)).setImageResource(args.getInt("image_id"));
        ViewGroup content = (LinearLayout) rootView.findViewById(R.id.content);
        View descriptionLayout = inflater.inflate(args.getInt("description_layout"), content, false);

        content.addView(descriptionLayout);

        ((TextView)rootView.findViewById(R.id.venue)).setText(args.getString("venue"));
        ((TextView)rootView.findViewById(R.id.time)).setText(args.getString("time"));
        ((TextView)rootView.findViewById(R.id.date)).setText(args.getString("date"));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt("section_number"));
    }
}
