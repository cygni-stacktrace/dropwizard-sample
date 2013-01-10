package se.cygni.expenses.resources;

import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;
import se.cygni.expenses.api.Expense;
import se.cygni.expenses.jdbi.ExpensesRepository;

import javax.ws.rs.core.MediaType;

import java.util.Date;

import static org.mockito.Matchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ExpensesResourceTest extends ResourceTest {

    private ExpensesRepository expensesRepository;

    @Override
    protected void setUpResources() throws Exception {
        expensesRepository = mock(ExpensesRepository.class);
        addResource(new ExpensesResource(expensesRepository));
    }


    @Test
    public void shouldListExpenses() {

    }

    @Test
    public void shouldListFilteredExpenses() {

    }

    @Test
    public void shouldAddAnExpense() {
        //given
        Expense expense = new Expense(0, "Hotshot", "Anna", new Date(), 545, 2);
        Expense expenseCreated = new Expense(1, "Hotshot", "Anna", expense.getDate(), 545, 2);
        when(expensesRepository.add(expense)).thenReturn(1L);
        when(expensesRepository.findById(1L)).thenReturn(expenseCreated);

        //when
        Expense result = client().resource("/expense").type(MediaType.APPLICATION_JSON_TYPE).post(Expense.class, expense);

        //then
        verify(expensesRepository).add(expense);
        assertThat("Expense created and returned", result, is(expense));
    }
}
