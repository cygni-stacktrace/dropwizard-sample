package se.cygni.expenses.resources;

import se.cygni.expenses.api.Expense;
import se.cygni.expenses.jdbi.ExpensesRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/expense")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpensesResource {

    private final ExpensesRepository expensesRepository;

    public ExpensesResource(ExpensesRepository eventsRepository) {

        this.expensesRepository = eventsRepository;
    }

    @GET
    public List<Expense> listExpenses(
            @DefaultValue("-1") @QueryParam("eventId") long eventId) {

        if (eventId >= 0) {
            return expensesRepository.findExpensesForEventId(eventId);
        } else {
            return expensesRepository.findAll();
        }
    }

    @POST
    public Response addExpense(Expense expense, @Context UriInfo uriInfo) throws URISyntaxException {
        long expenseId = expensesRepository.add(expense);

        URI baseUrl = uriInfo.getBaseUriBuilder().path(ExpensesResource.class).
                segment(Long.toString(expenseId)).build();

        return Response.status(Response.Status.CREATED).
                location(baseUrl).
                entity(expensesRepository.findById(expenseId)).
                build();
    }
}
