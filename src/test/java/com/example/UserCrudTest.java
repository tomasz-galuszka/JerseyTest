package com.example;

import com.example.user.User;
import com.sun.jersey.api.client.ClientResponse;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static junit.framework.Assert.assertEquals;

public class UserCrudTest {
 
    private HttpServer server;
    private WebTarget target;
 
    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
 
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }
 
    @After
    public void tearDown() throws Exception {
        server.stop();
    }
 
    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testCrud() {
        User user = target.path("user/add").queryParam("name", "Tomasz").request().get(User.class);
        assertEquals(user.getName(), "Tomasz");

        user = target.path("user/" + user.getId()).request().get(User.class);
        assertEquals(user.getName(), "Tomasz");

        user = target.path("user/edit/" + user.getId()).queryParam("name", "Marek").request().get(User.class);
        assertEquals(user.getName(), "Marek");

        user = target.path("user/" + user.getId()).request().get(User.class);
        assertEquals(user.getName(), "Marek");

        user = target.path("user/delete/" + user.getId()).request().get(User.class);
        assertEquals(user.getName(), "Marek");

        int status = target.path("user/" + user.getId()).request().get().getStatus();
        assertEquals(status, 204);
    }
}