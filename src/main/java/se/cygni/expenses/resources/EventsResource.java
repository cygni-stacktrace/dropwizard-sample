package se.cygni.expenses.resources;

import se.cygni.expenses.api.Event;
import se.cygni.expenses.api.Expense;
import se.cygni.expenses.jdbi.EventsRepository;
import se.cygni.expenses.jdbi.ExpensesRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventsResource {

    public static final String LIST_EVENTS = "listEvents";
    public static final String ADD_EVENT = "addEvent";
    private final EventsRepository eventsRepository;
    private final ExpensesRepository expensesRepository;

    public EventsResource(EventsRepository eventsRepository, ExpensesRepository expensesRepository) {

        this.eventsRepository = eventsRepository;
        this.expensesRepository = expensesRepository;
    }

    @GET
    public Map getEvents(@Context UriInfo uriInfo) {

        String baseUrl = uriInfo.getBaseUriBuilder().path(EventsResource.class).build().toString();
        String listEvents = baseUrl + LIST_EVENTS;
        String addEvent = baseUrl + ADD_EVENT;

        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("links", Arrays.asList(listEvents, addEvent));
        return hashMap;
    }

    @GET
    @Path(LIST_EVENTS)
    public List<Event> listEvents() {
        return eventsRepository.findAll();
    }

    @PUT
    @Path(ADD_EVENT)
    public Response addEvent(Event event) {
        eventsRepository.add(event);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("event/{id}")
    public Map<String, Object> showEvent(@PathParam("id") long id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Event event = eventsRepository.findById(id);
        List<Expense> expensesForEventId = expensesRepository.findExpensesForEventId(event.getId());

        map.put("event", event);
        map.put("expenses", expensesForEventId);

        return map;
    }
}
