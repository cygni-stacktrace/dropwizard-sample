package se.cygni.expenses.jdbi;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.cygni.expenses.api.Event;

import java.util.List;

@RegisterMapper(EventMapper.class)
public interface EventsRepository {

    public static String CREATE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS event (" +
                "id BIGINT, " +
                "name VARCHAR(40), " +
                "date TIMESTAMP, " +
                "PRIMARY KEY(id)" +
                ")";

    @SqlQuery("select * from event")
    List<Event> findAll();

    @SqlUpdate("insert into event (id, name, date) values (:id, :name, :date)")
    void add(@BindBean Event event);
}
