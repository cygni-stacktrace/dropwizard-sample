package se.cygni.expenses.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.yammer.dropwizard.testing.ResourceTest;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import se.cygni.expenses.api.Event;
import se.cygni.expenses.api.Expense;
import se.cygni.expenses.api.Link;
import se.cygni.expenses.api.OperationResult;
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
        Link listEventsLink = new Link("listEvents", "http://localhost:9998/listEvents", "GET", "Lists all events");
        Link addEventsLink = new Link("addEvent", "http://localhost:9998/addEvent", "POST", "Adds a new event");

        //when
        OperationResult result = client().resource("/").get(OperationResult.class);

        //then
        List<Link> links = result.getLinks();

        assertThat("Result contains links element", getLink("listEvents", links), is(listEventsLink));
        assertThat("Result contains links element", getLink("addEvent", links), is(addEventsLink));
    }

    private Link getLink(String operationId, List<Link> links) {
        for (Link link : links) {
            if (StringUtils.equals(link.getOperationId(), operationId)) {
                return link;
            }
        }
        return new Link(null, null, null, null);

    }

    @Test
    public void shouldListEvents() {
        // given

        when(eventsRepository.findAll()).thenReturn(Arrays.asList(new Event(0, "Trip", new Date(0))));

        // when
        List<Event> result = client().resource("/listEvents").get(new GenericType<List<Event>>() {
        });

        //then
        assertThat("Result should contain one element", result.size(), is(1));
        assertThat("First element should have Trip as name", result.get(0).getName(), is("Trip"));
    }

    @Test
    public void shouldAddEvent() {
        //given
        Event event = new Event(-1, "Big bird", null);
        long eventId = 1;

        given(eventsRepository.add(event)).willReturn(eventId);

        //when
        ClientResponse result = client().resource("/addEvent").type(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class, event);

        //then
        assertThat("Response contains location of the created event", result.getLocation().toString(), is("http://localhost:9998/event/" + eventId));
    }

    @Test
    public void shouldShowOneEvent() throws IOException {
        //given
        Event event = new Event(1, "An event", null);

        given(eventsRepository.findById(event.getId())).willReturn(event);
        given(expensesRepository.findExpensesForEventId(event.getId())).willReturn(Arrays.asList(new Expense(), new Expense()));

        //when
        Map<String, Object> result = client().resource("/event/1").get(new GenericType<Map<String, Object>>() {
        });

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
