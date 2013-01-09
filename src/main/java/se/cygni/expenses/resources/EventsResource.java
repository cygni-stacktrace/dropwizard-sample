package se.cygni.expenses.resources;

import se.cygni.expenses.api.Event;
import se.cygni.expenses.api.Expense;
import se.cygni.expenses.jdbi.EventsRepository;
import se.cygni.expenses.jdbi.ExpensesRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventsResource {

    private final EventsRepository eventsRepository;
    private final ExpensesRepository expensesRepository;

    public EventsResource(EventsRepository eventsRepository, ExpensesRepository expensesRepository) {

        this.eventsRepository = eventsRepository;
        this.expensesRepository = expensesRepository;
    }

    @GET
    public Map getEvents() {
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        hashMap.put("links", "/listEvents");
        return hashMap;
    }

    @GET
    @Path("/listEvents")
    public List<Event> listEvents() {
        return eventsRepository.findAll();
    }

    @PUT
    @Path("/addEvent")
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
