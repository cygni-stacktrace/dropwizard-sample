package se.cygni.expenses.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.cygni.expenses.api.Expense;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ExpenseMapper implements ResultSetMapper<Expense> {
    @Override
    public Expense map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Expense(
                resultSet.getLong("id"),
                resultSet.getString("description"),
                resultSet.getString("person"),
                new Date(resultSet.getTimestamp("date").getTime()),
                resultSet.getInt("amount"),
                resultSet.getLong("eventId")

        );
    }
}
