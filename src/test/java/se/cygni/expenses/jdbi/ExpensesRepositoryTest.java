package se.cygni.expenses.jdbi;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import se.cygni.expenses.api.Event;
import se.cygni.expenses.api.Expense;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExpensesRepositoryTest {

    @Test
    public void shouldListExpensesForEvent() {
        //given
        JdbcConnectionPool ds = JdbcConnectionPool.create("jdbc:h2:mem:test3", "sa", "");

        DBI dbi = new DBI(ds);

        createTables(dbi);

        insertEvent(dbi, new Event(1, "Cancun", new Date(0)));
        insertEvent(dbi, new Event(2, "New Delhi", new Date(1554)));

        insertExpense(dbi, new Expense(3, "Dinner at O Learys", "Kalle", new Date(342432432), 670, 2));
        insertExpense(dbi, new Expense(4, "Taxi Home", "Anders", new Date(342432432), 345, 2));

        ExpensesRepository target = dbi.open(ExpensesRepository.class);

        //when
        List<Expense> result = target.findExpensesForEventId(2);
        //then

        assertThat("result contains two elements", result.size(), is(2));

        ds.dispose();


    }

    private void insertEvent(DBI dbi, Event event) {
        Handle handle = dbi.open();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        handle.execute("insert into event " +
                "(id, name, date) " +
                "values " +
                "(" + event.getId() + "," +
                "'" + event.getName() + "'," +
                "'" + sdf.format(event.getDate()) + "')");
    }

    private void insertExpense(DBI dbi, Expense expense) {
        Handle handle = dbi.open();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        handle.execute("insert into expense " +
                "(id, description, person, date, amount, eventId) " +
                "values " +
                "(" + expense.getId() + "," +
                "'" + expense.getDescription() + "'," +
                "'" + expense.getPerson() + "'," +
                "'" + sdf.format(expense.getDate()) + "', " +
                expense.getAmount() + "," +
                expense.getEventId() + "," +
                ")");
    }

    private void createTables(DBI dbi) {

        Handle handle = dbi.open();
        handle.execute(EventsRepository.CREATE_TABLE_STATEMENT);
        handle.execute(ExpensesRepository.CREATE_TABLE_STATEMENT);
        handle.close();
    }

}
