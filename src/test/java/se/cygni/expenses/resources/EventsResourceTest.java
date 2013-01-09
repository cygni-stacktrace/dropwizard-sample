package se.cygni.expenses.resources;

import com.sun.jersey.api.client.GenericType;
import com.yammer.dropwizard.testing.ResourceTest;
import org.joda.time.DateTime;
import org.junit.Test;
import se.cygni.expenses.api.Event;
import se.cygni.expenses.api.Expense;
import se.cygni.expenses.jdbi.EventsRepository;
import se.cygni.expenses.jdbi.ExpensesRepository;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class EventsResourceTest extends ResourceTest {

    private EventsRepository eventsRepository;
    private ExpensesRepository expensesRepository;

    @Override
    protected void setUpResources() throws Exception {
        expensesRepository = mock(ExpensesRepository.class);
        eventsRepository = mock(EventsRepository.class);
        addResource(new EventsResource(eventsRepository, expensesRepository));
    }

    @Test
    public void shouldReturnActionLinks() {
        //given

        //when
        String result = client().resource("/").get(String.class);

        //then
        assertThat("Result contains links element", result, is("{\"links\":\"/listEvents\"}"));

    }

    @Test
    public void shouldListEvents() {
        // given

        when(eventsRepository.findAll()).thenReturn(Arrays.asList(new Event(0, "Trip", new Date(0))));

        // when
        List<Event> result = client().resource("/listEvents").get(new GenericType<List<Event>>() {});

        //then
        assertThat("Result should contain one element", result.size(), is(1));
        assertThat("First element should have Trip as name", result.get(0).getName(), is("Trip"));
    }

    @Test
    public void shouldAddEvent() {
        //given
        DateTime dateTime = new DateTime("2012-11-20");
        Date date = dateTime.toDate();
        Event event = new Event(1, "Big bird", date);

        //when
        client().resource("/addEvent").type(MediaType.APPLICATION_JSON_TYPE).put(Event.class, event);

        //then
        verify(eventsRepository).add(event);
    }

    @Test
    public void shouldShowOneEvent() throws IOException {
        //given
        Event event = new Event(1,"An event", null);

        given(eventsRepository.findById(event.getId())).willReturn(event);
        given(expensesRepository.findExpensesForEventId(event.getId())).willReturn(Arrays.asList(new Expense(), new Expense()));

        //when
        Map<String, Object> result = client().resource("/event/1").get(new GenericType<Map<String, Object>>() {});

        Event resultEvent = getEvent(result);
        List<Expense> resultExpenses = getExpenses(result);

        //then
        assertThat("event should be returned", resultEvent, is(event));
        assertThat("expenses are listed", resultExpenses.size(), is(2));


    }

    private Event getEvent(Map<String, Object> result) throws IOException {
        String eventAsJson = asJson(result.get("event"));
        return fromJson(eventAsJson, Event.class);
    }

    private List<Expense> getExpenses(Map<String, Object> result) throws IOException {
        List expensesMap = (List) result.get("expenses");
        List<Expense> resultExpenses = new ArrayList<Expense>();
        for (Object expenseObject : expensesMap) {
            Expense expense = fromJson(asJson(expenseObject), Expense.class);
            resultExpenses.add(expense);

        }
        return resultExpenses;
    }
}
