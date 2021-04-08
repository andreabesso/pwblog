/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.boundary;

import it.tss.blog.blog.control.UserStore;
import it.tss.blog.blog.entity.User;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.stream.Collectors;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.json.JsonObject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author andre
 */
@Path("/users")
@DenyAll
public class UsersResource {

    @Inject
    UserStore store;

    @Context
    ResourceContext resource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<JsonObject> search() {
        return store.search()
                .stream()
                .map(v -> v.toJson())
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll//permesso a tutti perchè chi non è utente deve potersi registrare
    public JsonObject create(JsonObject json) {
        String fname = json.getString("fname");
        String lname = json.getString("lname");
        String email = json.getString("email");
        String pwd = json.getString("pwd");
        //String role = json.getString("role");
        //Role r=User.Role.valueOf(role);
        //User user = new User(fname, lname, email, pwd, r);// qui assegnavo come valore di defaulr Role.role.USER
        User user = new User(fname, lname, email, pwd);
        User usr = store.create(user);
        return usr.toJson();
    }

    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserResource find(@PathParam("id") Long id) {
        UserResource sub = resource.getResource(UserResource.class);
        sub.setUserId(id);
        return sub;
    }

}
