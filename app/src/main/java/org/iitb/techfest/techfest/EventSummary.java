package org.iitb.techfest.techfest;

import android.os.Parcel;
import android.os.Parcelable;

public class EventSummary implements Parcelable{
    int id,
        image_id,
        description_layout,
        actionbar_color;
    String title,
           category,
           description,
           venue,
           time,
           date;

    public EventSummary(int id, int image_id, int description_layout, int actionbar_color, String title, String category, String description, String time, String date, String venue){
        this.id=id;
        this.image_id=image_id;
        this.description_layout=description_layout;
        this.actionbar_color=actionbar_color;
        this.category=category;
        this.title=title;
        this.description=description;
        this.time=time;
        this.date=date;
        this.venue=venue;
    }

    private EventSummary(Parcel in){
        id=in.readInt();
        image_id=in.readInt();
        description_layout=in.readInt();
        actionbar_color=in.readInt();
        title=in.readString();
        category=in.readString();
        description=in.readString();
        time=in.readString();
        date=in.readString();
        venue=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeInt(image_id);
        out.writeInt(description_layout);
        out.writeInt(actionbar_color);
        out.writeString(title);
        out.writeString(category);
        out.writeString(description);
        out.writeString(time);
        out.writeString(date);
        out.writeString(venue);
    }

    public static final Parcelable.Creator<EventSummary> CREATOR
            = new Parcelable.Creator<EventSummary>() {
        public EventSummary createFromParcel(Parcel in) {
            return new EventSummary(in);
        }

        public EventSummary[] newArray(int size) {
            return new EventSummary[size];
        }
    };
}
