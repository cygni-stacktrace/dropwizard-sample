package se.cygni.expenses.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Expense {

    private long id;
    private String description;
    private String person;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssz")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Expense expense = (Expense) o;

        return id == expense.id;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + (int) (eventId ^ (eventId >>> 32));
        return result;
    }
}
