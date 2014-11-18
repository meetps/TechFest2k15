package org.iitb.techfest.techfest;

import android.os.Parcel;
import android.os.Parcelable;

public class EventSummary implements Parcelable{
    int image_id;
    String title,
           description;

    public EventSummary(int image_id, String title, String description){
        this.image_id=image_id;
        this.title=title;
        this.description=description;
    }

    private EventSummary(Parcel in){
        description=in.readString();
        title=in.readString();
        image_id=in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(image_id);
        out.writeString(title);
        out.writeString(description);
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
