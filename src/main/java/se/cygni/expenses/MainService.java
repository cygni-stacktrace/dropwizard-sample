package se.cygni.expenses;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import se.cygni.expenses.resources.EventsResource;

public class MainService extends Service<MainConfiguration> {
    public static void main(String[] args) throws Exception {
        new MainService().run(args);
    }

    @Override
    public void initialize(Bootstrap<MainConfiguration> mainConfigurationBootstrap) {

    }

    @Override
    public void run(MainConfiguration mainConfiguration, Environment environment) throws Exception {
        environment.addResource(new EventsResource());
    }
}
