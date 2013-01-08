package se.cygni.expenses.resources;

import se.cygni.expenses.api.Expense;
import se.cygni.expenses.jdbi.ExpensesRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpensesResource {

    private final ExpensesRepository expensesRepository;

    public ExpensesResource(ExpensesRepository eventsRepository) {

        this.expensesRepository = eventsRepository;
    }

    @PUT
    @Path("/addExpense")
    public Response addExpense(Expense event) {
        expensesRepository.add(event);
        return Response.status(Response.Status.CREATED).build();
    }


}
