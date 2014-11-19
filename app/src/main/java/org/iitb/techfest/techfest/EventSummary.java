package org.iitb.techfest.techfest;

import android.os.Parcel;
import android.os.Parcelable;

public class EventSummary implements Parcelable{
    int id,
        image_id,
        description_layout;
    String title,
           description,
           venue,
           time,
           date;

    public EventSummary(int id, int image_id, int description_layout, String title, String description, String venue, String time, String date){
        this.id=id;
        this.image_id=image_id;
        this.description_layout=description_layout;
        this.title=title;
        this.description=description;
        this.venue=venue;
        this.time=time;
        this.date=date;
    }

    private EventSummary(Parcel in){
        date=in.readString();
        time=in.readString();
        venue=in.readString();
        description=in.readString();
        title=in.readString();
        description_layout=in.readInt();
        image_id=in.readInt();
        id=in.readInt();
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
        out.writeString(title);
        out.writeString(description);
        out.writeString(venue);
        out.writeString(time);
        out.writeString(date);
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
