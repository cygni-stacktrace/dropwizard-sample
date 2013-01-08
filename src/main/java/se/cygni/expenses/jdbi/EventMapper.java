package se.cygni.expenses.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.cygni.expenses.api.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EventMapper implements ResultSetMapper<Event> {
    @Override
    public Event map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Event(resultSet.getInt("id"), resultSet.getString("name"), new Date(resultSet.getTimestamp("date").getTime()));
    }
}
