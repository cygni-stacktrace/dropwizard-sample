package se.cygni.expenses.resources;

import se.cygni.expenses.api.Expense;
import se.cygni.expenses.jdbi.ExpensesRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/expense")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpensesResource {

    private final ExpensesRepository expensesRepository;

    public ExpensesResource(ExpensesRepository eventsRepository) {

        this.expensesRepository = eventsRepository;
    }

    @GET
    public Response listExpenses() {
        return null;
    }

    @POST
    public Response addExpense(Expense expense) {
        long expenseId = expensesRepository.add(expense);

        return Response.status(Response.Status.CREATED).
                entity(expensesRepository.findById(expenseId)).
                build();
    }


}
