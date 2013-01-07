package se.cygni.expenses.resources;

import se.cygni.expenses.api.Event;
import se.cygni.expenses.jdbi.EventsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {

    private final EventsRepository eventsRepository;

    public EventsResource(EventsRepository eventsRepository) {

        this.eventsRepository = eventsRepository;
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
}
