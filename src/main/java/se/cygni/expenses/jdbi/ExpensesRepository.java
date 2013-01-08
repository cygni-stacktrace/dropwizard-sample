package se.cygni.expenses.jdbi;

import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.cygni.expenses.api.Expense;

import java.util.List;

@RegisterMapper(ExpenseMapper.class)
public interface ExpensesRepository {

    public static String CREATE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS expense (" +
                    "id BIGINT, " +
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

}
