package se.cygni.expenses.jdbi;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.cygni.expenses.api.Expense;

import java.util.List;

@RegisterMapper(ExpenseMapper.class)
public interface ExpensesRepository {

    String TABLE_NAME = "expense";

    String CREATE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id SERIAL, " +
                    "description VARCHAR(40), " +
                    "person VARCHAR(55), " +
                    "date TIMESTAMP, " +
                    "amount INT, " +
                    "eventId BIGINT, " +
                    "PRIMARY KEY(id), " +
                    "FOREIGN KEY(eventId) REFERENCES event(id)" +
                    ")";

    @SqlQuery("select * from expense")
    List<Expense> findAll();

    @SqlQuery("select * from expense where eventId = :eventId")
    List<Expense> findExpensesForEventId(@Bind("eventId") long eventId);

    @GetGeneratedKeys
    @SqlUpdate("insert into expense (description, person, date, amount, eventId) " +
            "values (:description, :person, :date, :amount, :eventId)")
    long add(@BindBean Expense expense);

    @SqlQuery("select * from expense where id = :expenseId")
    Expense findById(@Bind("expenseId") long expenseId);
}
