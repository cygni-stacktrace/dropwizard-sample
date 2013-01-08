package se.cygni.expenses.jdbi;

import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.cygni.expenses.api.Event;

import java.util.List;

@RegisterMapper(EventMapper.class)
public interface EventsRepository {

    @SqlQuery("select * from event")
    List<Event> findAll();
}
