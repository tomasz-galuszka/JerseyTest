package com.example.user;

import com.example.general.HibernateUtil;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserCrud {

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User user = (User) session.get(User.class, id);

        session.getTransaction().commit();
        session.close();

        if (user != null) {
            return Response.ok(user).type(MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/add")
    public Response add(@QueryParam("name") String name) {
        User user = new User();
        user.setName(name);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();

        return Response.ok(user).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/edit/{id}")
    public Response edit(@PathParam("id") int id, @DefaultValue("") @QueryParam("name") String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User user = (User) session.get(User.class, id);
        user.setName(name);

        session.save(user);

        session.getTransaction().commit();
        session.close();

        return Response.ok(user).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User user = (User) session.get(User.class, id);
        session.delete(user);

        session.getTransaction().commit();
        session.close();

        return Response.ok(user).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
