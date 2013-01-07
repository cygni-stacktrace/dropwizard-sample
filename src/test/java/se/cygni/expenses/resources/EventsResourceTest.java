package se.cygni.expenses.resources;

import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventsResourceTest extends ResourceTest {
    @Override
    protected void setUpResources() throws Exception {
        addResource(new EventsResource());
    }

    @Test
    public void shouldReturnActionLinks() {
        //given

        //when
        String result = client().resource("/").get(String.class);

        //then
        assertThat("Result contains links element", result, is("{\"links\":\"/listEvents\"}"));

    }

}
