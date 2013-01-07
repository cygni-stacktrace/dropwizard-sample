package se.cygni.expenses.api;

import java.util.Date;

public class Event {

    private int id;
    private String name;
    private Date date;

    public Event() {
    }

    public Event(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
