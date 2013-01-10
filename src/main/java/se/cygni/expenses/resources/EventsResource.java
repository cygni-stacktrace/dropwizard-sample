package se.cygni.expenses.resources;

import se.cygni.expenses.api.Event;
import se.cygni.expenses.jdbi.EventsRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventsResource {

    private final EventsRepository eventsRepository;

    public EventsResource(EventsRepository eventsRepository) {

        this.eventsRepository = eventsRepository;
    }

    @GET
    public List<Event> listEvents() {
        return eventsRepository.findAll();
    }

    @PUT
    public Response addEvent(Event event, @Context UriInfo uriInfo) throws URISyntaxException {
        long id = eventsRepository.add(event);
        Event updatedEvent = new Event(id, event.getName(), event.getDate());
        URI baseUrl = uriInfo.getBaseUriBuilder().path(EventsResource.class).segment(Long.toString(id)).build();
        return Response.status(Response.Status.CREATED).location(baseUrl).entity(updatedEvent).build();
    }

    @GET
    @Path("{id}")
    public Event showEvent(@PathParam("id") long id) {
        return eventsRepository.findById(id);
    }
}
