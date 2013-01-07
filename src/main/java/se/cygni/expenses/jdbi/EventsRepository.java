package se.cygni.expenses.jdbi;

import org.skife.jdbi.v2.sqlobject.SqlQuery;
import se.cygni.expenses.api.Event;

import java.util.List;

public interface EventsRepository {

    @SqlQuery("select * from event")
    List<Event> findAll();
}
