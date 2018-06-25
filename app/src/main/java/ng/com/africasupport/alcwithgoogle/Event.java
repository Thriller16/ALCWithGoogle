package ng.com.africasupport.alcwithgoogle;

/**
 * Created by lawrene on 6/25/18.
 */

public class Event {
    String date, event;

    public Event(String date, String event) {
        this.date = date;
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public String getDate() {
        return date;
    }

}
