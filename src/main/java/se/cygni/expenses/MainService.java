package se.cygni.expenses;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import se.cygni.expenses.jdbi.EventsRepository;
import se.cygni.expenses.jdbi.ExpensesRepository;
import se.cygni.expenses.resources.EventsResource;

public class MainService extends Service<MainConfiguration> {
    public static void main(String[] args) throws Exception {
        new MainService().run(args);
    }

    @Override
    public void initialize(Bootstrap<MainConfiguration> mainConfigurationBootstrap) {
        mainConfigurationBootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    }

    @Override
    public void run(MainConfiguration config, Environment environment) throws Exception {

        DBI dbi = initDatabaseConnection(config, environment);

        final EventsRepository eventsRepository = dbi.onDemand(EventsRepository.class);

        //final UserDAO dao = jdbi.onDemand(UserDAO.class);
        //environment.addResource(new UserResource(dao));

        environment.addResource(new EventsResource(eventsRepository));
    }

    private DBI initDatabaseConnection(MainConfiguration config, Environment environment) throws ClassNotFoundException {

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, config.getDatabaseConfiguration(), "db");

        createTables(jdbi);

        return jdbi;
    }

    private void createTables(DBI dbi) {

        Handle handle = dbi.open();

        handle.execute(EventsRepository.CREATE_TABLE_STATEMENT);

        handle.execute(ExpensesRepository.CREATE_TABLE_STATEMENT);

        handle.close();
    }
}
