package se.cygni.expenses.api;

import java.util.Date;

public class Expense {

    private long id;
    private String description;
    private String person;
    private Date date;
    private int amount;
    private long eventId;

    public Expense() {
    }

    public Expense(long id, String description, String person, Date date, int amount, long event) {
        this.id = id;
        this.description = description;
        this.person = person;
        this.date = date;
        this.amount = amount;
        this.eventId = event;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPerson() {
        return person;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public long getEventId() {
        return eventId;
    }
}
