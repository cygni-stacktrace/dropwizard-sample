package se.cygni.expenses.resources;

import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;
import se.cygni.expenses.api.Expense;
import se.cygni.expenses.jdbi.ExpensesRepository;

import javax.ws.rs.core.MediaType;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExpensesResourceTest extends ResourceTest {

    private ExpensesRepository eventsRepository;

    @Override
    protected void setUpResources() throws Exception {
        eventsRepository = mock(ExpensesRepository.class);
        addResource(new ExpensesResource(eventsRepository));
    }

    @Test
    public void shouldAddAnExpense() {
        //given
        Expense expense = new Expense();

        //when
        client().resource("/expense/add").type(MediaType.APPLICATION_JSON_TYPE).put(Expense.class, expense);

        //then
        verify(eventsRepository).add(expense);
    }
}
