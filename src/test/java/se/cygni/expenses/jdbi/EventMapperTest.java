package se.cygni.expenses.jdbi;

import org.junit.Test;
import org.skife.jdbi.v2.StatementContext;
import se.cygni.expenses.api.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EventMapperTest {

    @Test
    public void shouldMapAnEvent() throws SQLException {
        //given
        String eventName = "The movies";
        int eventId = 17;
        Date eventDate = new Date(0);
        Timestamp eventTimestamp = new Timestamp(0);

        EventMapper target = new EventMapper();

        ResultSet resultSet = mock(ResultSet.class);
        given(resultSet.getString("name")).willReturn(eventName);
        given(resultSet.getInt("id")).willReturn(eventId);
        given(resultSet.getTimestamp("date")).willReturn(eventTimestamp);

        //when
        Event result = target.map(0, resultSet, mock(StatementContext.class));

        //then
        assertThat("Event has name", result.getName(), is(eventName));
        assertThat("Event has id", result.getId(), is(eventId));
        assertThat("Event has date", result.getDate(), is(eventDate));
    }
}
