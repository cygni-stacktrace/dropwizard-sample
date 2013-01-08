package se.cygni.expenses.jdbi;

import org.junit.Test;
import org.skife.jdbi.v2.StatementContext;
import se.cygni.expenses.api.Expense;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ExpenseMapperTest {

    @Test
    public void shouldMapAnExpense() throws SQLException {
        //given
        String description = "Dinner at O'Learys";
        String person = "Kalle";

        long id = 3;
        long eventId = 17;
        int amount = 450;
        Date expenseDate = new Date(55804894);
        Timestamp eventTimestamp = new Timestamp(55804894);

        ExpenseMapper target = new ExpenseMapper();

        ResultSet resultSet = mock(ResultSet.class);
        given(resultSet.getString("description")).willReturn(description);
        given(resultSet.getString("person")).willReturn(person);
        given(resultSet.getInt("amount")).willReturn(amount);

        given(resultSet.getLong("id")).willReturn(id);
        given(resultSet.getLong("eventId")).willReturn(eventId);
        given(resultSet.getTimestamp("date")).willReturn(eventTimestamp);

        //when
        Expense result = target.map(0, resultSet, mock(StatementContext.class));

        //then
        assertThat("Event has description", result.getDescription(), is(description));
        assertThat("Event has person", result.getPerson(), is(person));
        assertThat("Event has id", result.getId(), is(id));
        assertThat("Event has eventId", result.getEventId(), is(eventId));
        assertThat("Event has date", result.getDate(), is(expenseDate));
        assertThat("Event has amount", result.getAmount(), is(amount));
    }
}
