package se.cygni.expenses.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class EventsResource {

    @GET
    public Map getEvents() {
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        hashMap.put("links", "/listEvents");
        return hashMap;
    }
}
