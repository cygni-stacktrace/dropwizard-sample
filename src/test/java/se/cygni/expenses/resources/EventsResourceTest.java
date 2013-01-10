package se.cygni.expenses.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;
import se.cygni.expenses.api.Event;
import se.cygni.expenses.jdbi.EventsRepository;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventsResourceTest extends ResourceTest {

    private EventsRepository eventsRepository;

    @Override
    protected void setUpResources() throws Exception {
        eventsRepository = mock(EventsRepository.class);
        addResource(new EventsResource(eventsRepository));
    }

    @Test
    public void shouldListEvents() {
        // given
        Event event = new Event(2, "Trip", new Date(0));
        when(eventsRepository.findAll()).thenReturn(Arrays.asList(event));

        // when
        List<Event> result = client().resource("/event").get(new GenericType<List<Event>>(){});

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
        ClientResponse result = client().resource("/event").type(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class, event);

        //then
        Event expectedEvent = new Event(eventId, "Big bird", null);
        assertThat("Response contains location of the created event", result.getLocation().toString(), is("http://localhost:9998/event/" + eventId));
        assertThat("Response contains location of the created event", result.getEntity(Event.class), is(expectedEvent));
    }

    @Test
    public void shouldShowOneEvent() throws IOException {
        //given
        Event event = new Event(1, "An event", null);

        given(eventsRepository.findById(event.getId())).willReturn(event);

        //when
        Event result = client().resource("/event/1").get(Event.class);

        //then
        assertThat("event should be returned", result, is(event));
    }
}
