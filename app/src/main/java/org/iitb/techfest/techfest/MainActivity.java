package org.iitb.techfest.techfest;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Stack;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    ImageView tf_logo;

    ArrayList<EventSummary> events = new ArrayList<EventSummary>();
    Stack<Fragment> fragStack = new Stack<Fragment>();

    HashMap<String,Integer> layout_id = new HashMap<String, Integer>();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int mActionBarColor=R.color.actionbar_section1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(findViewById(R.id.navigation_drawer));

        tf_logo = new ImageView(this);
        tf_logo.setImageResource(R.drawable.tf_logo);

        events = getIntent().getParcelableArrayListExtra("events");

        layout_id.put("Technocalypse", R.layout.details_technocalypse);
        layout_id.put("Codeblitz",R.layout.details_codeblitz);
        layout_id.put("Vortex",R.layout.details_vortex);
        layout_id.put("Mechatron",R.layout.details_mechatron);

        layout_id.put("International Challenge", R.layout.details_international_challenge);
        layout_id.put("Robowars",R.layout.details_robowars);
        layout_id.put("International Robotics Competition",R.layout.details_irc);
        layout_id.put("Techfest International Coding Challenge",R.layout.details_ticc);
        layout_id.put("Techfest International Student Conference", R.layout.details_tisc);

        addLayoutIDs();

        restoreActionBar();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag;

        position++;

        if(position==2)
            frag= EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP,getString(R.string.title_section2),R.color.actionbar_section2,R.layout.fragment_competitions,filterEvents(getString(R.string.title_section2)));
        else
            frag= EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP,getString(R.string.title_section1),R.color.actionbar_section1,R.layout.fragment_main,filterEvents(getString(R.string.title_section1)));

        fragStack.push(frag);

        fragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commit();
    }

    public ArrayList<EventSummary> filterEvents(String title){
        ArrayList<EventSummary> temp = new ArrayList<EventSummary>();

        for(EventSummary es : events){
            if(es.category.equals(title)){
                temp.add(es);
            }
        }

        return temp;
    }

    public void loadDetails(View v){
        EventDetailsFragment eventDetails = EventDetailsFragment.newInstance(events.get(v.getId()));

        fragStack.push(eventDetails);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, eventDetails)
                .commit();
    }

    public void loadList(View v){
        String title = (String)v.getTag();

        EventListFragment eventList = EventListFragment.newInstance(
                EventListFragment.TYPE_LIST,
                title,
                ((EventFragment)fragStack.peek()).getActionBarColor(),
                layout_id.get(title),
                filterEvents(title));

        fragStack.push(eventList);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, eventList)
                .commit();
    }

    public void addLayoutIDs(){
        for(EventSummary es: events){
            Integer temp_id = layout_id.get(es.title);
            if(temp_id!=null){
                es.description_layout=temp_id;
            } else {
                es.description_layout=R.layout.details_robowars;
            }
        }
    }

    public void setReminder(View v){
        EventSummary es = events.get((Integer) v.getTag());

        int hours=Integer.valueOf(es.time.split(":")[0]);
        int minutes=Integer.valueOf(es.time.split(":")[1]);
        int year=Integer.valueOf(es.date.split("/")[2]);
        int month=Integer.valueOf(es.date.split("/")[1])-1;
        int day=Integer.valueOf(es.date.split("/")[0]);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hours, minutes);

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", false);
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", es.title + " @ "+ es.venue);
        intent.putExtra("description",es.description);
        startActivity(intent);
    }

    public String getLatLangVersion(String place){
        if(place.equals("LCH"))
            return "19.130739,72.917208";
        else if(place.equals("SAC"))
            return "19.135369,72.913769";
        else if(place.equals("Footer Field"))
            return "19.134354,72.912133";
        else if(place.equals("VMCC"))
            return "19.132506,72.917260";
        else if(place.equals("FCK"))
            return "19.130484,72.915719";
        else if(place.equals("H8 Road"))
            return "19.133731,72.911319";
        else if(place.equals("KV Grounds"))
            return "19.129144,72.918190";
        else if(place.equals("SOM"))
            return "19.1317237,72.9157796";
        else if(place.equals("Convocation Hall"))
            return "19.1319587,72.914763";
        else
            return "19.1279852,72.914763";
    }

    public void getDirections(View v){
        EventSummary es = events.get((Integer) v.getTag());

        String venue = es.venue;
//        int radioButtonID = list.getCheckedRadioButtonId();
        String latlang=getLatLangVersion(venue.toString());

        Intent intentMap = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?mode=walking&daddr=" + latlang));
        startActivity(intentMap);
    }

    public void onSectionAttached(String title, int actionbar_color) {
        mTitle=title;
        mActionBarColor=actionbar_color;

        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(mActionBarColor)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            fragStack.pop();

            if(fragStack.empty())
                super.onBackPressed();
            else
            {
                EventFragment currFrag = (EventFragment)fragStack.peek();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, currFrag)
                        .commit();
                onSectionAttached(currFrag.getTitle(), currFrag.getActionBarColor());
            }
        }
    }
}
