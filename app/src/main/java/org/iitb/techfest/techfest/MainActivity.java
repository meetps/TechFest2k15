package org.iitb.techfest.techfest;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

    HashMap<String, Integer[]> layout_desc = new HashMap<String, Integer[]>();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int mActionBarColor = R.color.actionbar_home;

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

        layout_desc.put("ThinkerNet", new Integer[]{R.layout.details_thinkernet, R.drawable.thinkernet, R.color.actionbar_competitions});
        layout_desc.put("NextGen TV", new Integer[]{R.layout.details_nextgen_tv, R.drawable.nextgentv, R.color.actionbar_competitions});

        layout_desc.put("Technocalypse", new Integer[]{R.layout.details_technocalypse, R.drawable.technocalypse, R.color.actionbar_competitions});
        layout_desc.put("Codeblitz", new Integer[]{R.layout.details_codeblitz, R.drawable.codeblitz, R.color.actionbar_competitions});
        layout_desc.put("Vortex", new Integer[]{R.layout.details_vortex, R.drawable.vortex, R.color.actionbar_competitions});
        layout_desc.put("Mechatron", new Integer[]{R.layout.details_mechatron, R.drawable.mechatron, R.color.actionbar_competitions});

        layout_desc.put("International Challenge", new Integer[]{R.layout.details_international_challenge, R.drawable.international_challenge, R.color.actionbar_competitions});
        layout_desc.put("Robowars", new Integer[]{R.layout.details_robowars, R.drawable.robowars, R.color.actionbar_competitions});
        layout_desc.put("International Robotics Competition", new Integer[]{R.layout.details_irc, R.drawable.irc, R.color.actionbar_competitions});
        layout_desc.put("Techfest International Coding Challenge", new Integer[]{R.layout.details_ticc, R.drawable.ticc, R.color.actionbar_competitions});
        layout_desc.put("Techfest International Student Conference", new Integer[]{R.layout.details_tisc, R.drawable.tisc, R.color.actionbar_competitions});

        layout_desc.put("Dimensions", new Integer[]{R.layout.details_dimensions, R.drawable.dimensions, R.color.actionbar_competitions});
        layout_desc.put("Hydranoid", new Integer[]{R.layout.details_hydranoid, R.drawable.hydranoid, R.color.actionbar_competitions});
        layout_desc.put("Cantilivo", new Integer[]{R.layout.details_cantilivo, R.drawable.cantilivo, R.color.actionbar_competitions});
        layout_desc.put("Technocrane", new Integer[]{R.layout.details_technocrane, R.drawable.technocrane, R.color.actionbar_competitions});

        layout_desc.put("Robotron", new Integer[]{R.layout.details_robotron, R.drawable.robotron, R.color.actionbar_competitions});
        layout_desc.put("Pixelate", new Integer[]{R.layout.details_pixelate, R.drawable.pixelate, R.color.actionbar_competitions});
        layout_desc.put("Magneto", new Integer[]{R.layout.details_magneto, R.drawable.magneto, R.color.actionbar_competitions});
        layout_desc.put("Scholastic", new Integer[]{R.layout.details_scholastic, R.drawable.scholastic, R.color.actionbar_competitions});

        layout_desc.put("Xtreme Machines", new Integer[]{R.layout.details_xtreme_machines, R.drawable.xtrememachines, R.color.actionbar_competitions});
        layout_desc.put("Full Throttle", new Integer[]{R.layout.details_full_throttle, R.drawable.fullthrottle, R.color.actionbar_competitions});
        layout_desc.put("Combat Nautica", new Integer[]{R.layout.details_combat_nautica, R.drawable.combatnautica, R.color.actionbar_competitions});
        layout_desc.put("Aviator Design Challenge", new Integer[]{R.layout.details_aviator_design, R.drawable.aviatordesign, R.color.actionbar_competitions});
        layout_desc.put("Aviator Flying Challenge", new Integer[]{R.layout.details_aviator_flying, R.drawable.aviatorflying, R.color.actionbar_competitions});

        layout_desc.put("Moneyball", new Integer[]{R.layout.details_moneyball, R.drawable.moneyball, R.color.actionbar_competitions});
        layout_desc.put("The 23rd Yard", new Integer[]{R.layout.details_23_yard, R.drawable.the_23rd_yard, R.color.actionbar_competitions});
        layout_desc.put("Striker", new Integer[]{R.layout.details_striker, R.drawable.striker, R.color.actionbar_competitions});

        layout_desc.put("Algorhythm", new Integer[]{R.layout.details_algorhythm, R.drawable.algorhythm, R.color.actionbar_competitions});
        layout_desc.put("Fermat", new Integer[]{R.layout.details_fermat, R.drawable.fermat, R.color.actionbar_competitions});

        layout_desc.put("Technoholix", new Integer[]{R.layout.details_technoholix, R.drawable.technoholix, R.color.actionbar_technoholix});
        layout_desc.put("Lectures", new Integer[]{R.layout.details_lectures, R.drawable.lectures, R.color.actionbar_lectures});
        layout_desc.put("Exhibitions", new Integer[]{R.layout.details_exhibitions, R.drawable.exhibitions, R.color.actionbar_exhibitions});
        layout_desc.put("Ozone", new Integer[]{R.layout.details_ozone, R.drawable.ozone, R.color.actionbar_ozone});

        layout_desc.put("Sportstech", new Integer[]{R.layout.details_sportstech, R.drawable.sportstech, R.color.actionbar_initiatives});
        layout_desc.put("Game Changer", new Integer[]{R.layout.details_game_changer, R.drawable.game_changer, R.color.actionbar_initiatives});
        layout_desc.put("ROAR", new Integer[]{R.layout.details_roar, R.drawable.roar, R.color.actionbar_initiatives});
        layout_desc.put("Take Action", new Integer[]{R.layout.details_take_action, R.drawable.take_action, R.color.actionbar_initiatives});
        layout_desc.put("Panel Discussion", new Integer[]{R.layout.details_panel_discussion, R.drawable.panel, R.color.actionbar_initiatives});

        layout_desc.put("Parishram", new Integer[]{R.layout.details_parishram, R.drawable.parishram, R.color.actionbar_ideate});
        layout_desc.put("Ujjwal", new Integer[]{R.layout.details_ujjwal, R.drawable.ujjwal, R.color.actionbar_ideate});

        layout_desc.put("IRC Workshop", new Integer[]{R.layout.details_irc_workshop, R.drawable.irc, R.color.actionbar_workshops});
        layout_desc.put("Augmented Reality", new Integer[]{R.layout.details_augmented_reality, R.drawable.augmented, R.color.actionbar_workshops});
        layout_desc.put("Zero Energy Buildings", new Integer[]{R.layout.details_zero_energy_buildings, R.drawable.zero_energy_buildings, R.color.actionbar_workshops});
        layout_desc.put("Arduped", new Integer[]{R.layout.details_arduped, R.drawable.arduped, R.color.actionbar_workshops});
        layout_desc.put("Cloud Computing", new Integer[]{R.layout.details_cloud_computing, R.drawable.cloud_computing, R.color.actionbar_workshops});
        layout_desc.put("Gyrocopter", new Integer[]{R.layout.details_gyrocopter, R.drawable.gyrocopter, R.color.actionbar_workshops});
        layout_desc.put("Hacktricks Level 1", new Integer[]{R.layout.details_hacktricks_level_1, R.drawable.hacktricks, R.color.actionbar_workshops});
        layout_desc.put("Hacktricks Level 2", new Integer[]{R.layout.details_hacktricks_level_2, R.drawable.hacktricks, R.color.actionbar_workshops});
        layout_desc.put("Web Development", new Integer[]{R.layout.details_web_development, R.drawable.web_development, R.color.actionbar_workshops});
        layout_desc.put("Swarm Robotics", new Integer[]{R.layout.details_swarm_robotics, R.drawable.swarm_robotics, R.color.actionbar_workshops});
        layout_desc.put("PSoC", new Integer[]{R.layout.details_psoc, R.drawable.psoc, R.color.actionbar_workshops});
        layout_desc.put("Propeller Clock", new Integer[]{R.layout.details_propeller_clock, R.drawable.propeller_clock, R.color.actionbar_workshops});
        layout_desc.put("Unmanned Vehicle", new Integer[]{R.layout.details_unmanned_vehicle, R.drawable.unmanned_vehicle, R.color.actionbar_workshops});
        layout_desc.put("Robotic Navigation", new Integer[]{R.layout.details_robotic_navigation, R.drawable.robotic_navigation, R.color.actionbar_workshops});
        layout_desc.put("Sixth Sense Robotics", new Integer[]{R.layout.details_sixth_sense_robotics, R.drawable.sixthsense, R.color.actionbar_workshops});
        layout_desc.put("Android App Devpt", new Integer[]{R.layout.details_android_app_devpt, R.drawable.android, R.color.actionbar_workshops});
        layout_desc.put("Brainwave Robotics", new Integer[]{R.layout.details_brainwave_robotics, R.drawable.brainwave, R.color.actionbar_workshops});
        layout_desc.put("Robo Speech", new Integer[]{R.layout.details_robo_speech, R.drawable.voice, R.color.actionbar_workshops});
        layout_desc.put("Automotive", new Integer[]{R.layout.details_automotive, R.drawable.automotive, R.color.actionbar_workshops});
        layout_desc.put("Click", new Integer[]{R.layout.details_click, R.drawable.click, R.color.actionbar_workshops});

        addLayoutIDs();

        restoreActionBar();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag = null;

        position++;

        switch (position) {
            case 2:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_competitions), R.color.actionbar_competitions, R.layout.fragment_competitions, filterEvents(getString(R.string.title_competitions)));
                break;
            case 3:
                for (EventSummary es : events)
                    if (es.title.equals(getString(R.string.title_technoholix))) {
                        frag = EventDetailsFragment.newInstance(es);
                    }
                break;
            case 4:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_initiatives), R.color.actionbar_initiatives, R.layout.fragment_initiatives, filterEvents(getString(R.string.title_initiatives)));
                break;
            case 5:
                for (EventSummary es : events)
                    if (es.title.equals(getString(R.string.title_lectures))) {
                        frag = EventDetailsFragment.newInstance(es);
                    }
                break;
            case 6:
                for (EventSummary es : events)
                    if (es.title.equals(getString(R.string.title_exhibitions))) {
                        frag = EventDetailsFragment.newInstance(es);
                    }
                break;
            case 7:
                for (EventSummary es : events)
                    if (es.title.equals(getString(R.string.title_ozone))) {
                        frag = EventDetailsFragment.newInstance(es);
                    }
                break;
            case 8:
                for (EventSummary es : events)
                    if (es.title.equals("Techfest International Student Conference")) {
                        frag = EventDetailsFragment.newInstance(es);
                    }
                break;
            case 9:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST, getString(R.string.title_ideate), R.color.actionbar_ideate, R.layout.details_ideate, filterEvents(getString(R.string.title_ideate)));
                break;
            case 10:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST, getString(R.string.title_workshops), R.color.actionbar_workshops, R.layout.details_workshops, filterEvents(getString(R.string.title_workshops)));
                break;
            default:
                frag = EventListFragment.newInstance(EventListFragment.TYPE_LIST_GROUP, getString(R.string.title_home), R.color.actionbar_home, R.layout.fragment_main, null);
                break;
        }

        fragStack.push(frag);

        fragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commit();
    }

    public ArrayList<EventSummary> filterEvents(String title) {
        ArrayList<EventSummary> temp = new ArrayList<EventSummary>();

        for (EventSummary es : events) {
            String[] categories = es.category.split("\\+");
            for (String cat : categories) {
                if (cat.equals(title)) {
                    temp.add(es);
                    break;
                }
            }
        }

        return temp;
    }

    public void loadDetails(View v) {
        EventDetailsFragment eventDetails = EventDetailsFragment.newInstance(events.get(v.getId()));

        fragStack.push(eventDetails);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, eventDetails)
                .commit();
    }

    public void loadList(View v) {
        String title = (String) v.getTag();

        EventListFragment eventList = EventListFragment.newInstance(
                EventListFragment.TYPE_LIST,
                title,
                layout_desc.get(title)[2],
                layout_desc.get(title)[0],
                filterEvents(title));

        fragStack.push(eventList);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, eventList)
                .commit();
    }

    public void addLayoutIDs() {
        for (EventSummary es : events) {
            Integer[] temp_desc = layout_desc.get(es.title);
            if (temp_desc != null) {
                es.description_layout = temp_desc[0];
                es.image_id = temp_desc[1];
                es.actionbar_color = temp_desc[2];
            } else {
                es.description_layout = R.layout.details_robowars;
            }
        }
    }

    public void setReminder(View v) {
        EventSummary es = events.get((Integer) v.getTag());

        int hours = Integer.valueOf(es.time.split(":")[0]);
        int minutes = Integer.valueOf(es.time.split(":")[1]);
        int year = Integer.valueOf(es.date.split("/")[2]);
        int month = Integer.valueOf(es.date.split("/")[1]) - 1;
        int day = Integer.valueOf(es.date.split("/")[0]);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hours, minutes);

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", false);
        intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
        intent.putExtra("title", es.title + " @ " + es.venue);
        intent.putExtra("description", es.description);
        startActivity(intent);
    }

    public String getLatLangVersion(String place) {
        if (place.equals("LCH"))
            return "19.130739,72.917208";
        else if (place.equals("SAC"))
            return "19.135369,72.913769";
        else if (place.equals("Footer Field"))
            return "19.134354,72.912133";
        else if (place.equals("VMCC"))
            return "19.132506,72.917260";
        else if (place.equals("FCK"))
            return "19.130484,72.915719";
        else if (place.equals("H8 Road"))
            return "19.133731,72.911319";
        else if (place.equals("KV Grounds"))
            return "19.129144,72.918190";
        else if (place.equals("SOM"))
            return "19.1317237,72.9157796";
        else if (place.equals("Convocation Hall"))
            return "19.1319587,72.914763";
        else
            return "19.1279852,72.914763";
    }

    public void getDirections(View v) {
        EventSummary es = events.get((Integer) v.getTag());

        String venue = es.venue;
//        int radioButtonID = list.getCheckedRadioButtonId();
        String latlang = getLatLangVersion(venue.toString());

        Intent intentMap = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?mode=walking&daddr=" + latlang));
        startActivity(intentMap);
    }

    public void onSectionAttached(String title, int actionbar_color) {
        mTitle = title;
        mActionBarColor = actionbar_color;

        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar==null) return;

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(mActionBarColor)));
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            fragStack.pop();

            if (fragStack.empty())
                super.onBackPressed();
            else {
                EventFragment currFrag = (EventFragment) fragStack.peek();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, currFrag)
                        .commit();
                onSectionAttached(currFrag.getTitle(), currFrag.getActionBarColor());
            }
        }
    }
}
