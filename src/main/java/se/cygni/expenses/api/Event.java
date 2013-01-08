package se.cygni.expenses.api;

import java.util.Date;

public class Event {

    private long id;
    private String name;
    private Date date;

    public Event() {
    }

    public Event(long id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
