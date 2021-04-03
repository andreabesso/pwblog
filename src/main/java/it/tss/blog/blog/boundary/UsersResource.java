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
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.json.JsonObject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author andre
 */
@Path("/users")
public class UsersResource {

    @Inject
    UserStore store;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<JsonObject> search() {
        return store.search()
                .stream()
                .map(v -> v.toJson())
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject find(@PathParam("id") Long id) {
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        return user.toJson();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject create(JsonObject json) {
        String fname = json.getString("fname");
        String lname = json.getString("lname");
        String email = json.getString("email");
        String pwd = json.getString("pwd");
        User user = new User(fname, lname, email, pwd, User.Role.USER);
        User usr = store.create(user);
        return usr.toJson();
    }

   @PATCH
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject update(@PathParam("id") Long id, JsonObject json) {
        String fname = json.getString("fname");
        String lname = json.getString("lname");
        String pwd = json.getString("pwd");
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        User usr = new User(fname, lname, user.getEmail(), pwd, user.getRole());
        User usrupdate = store.update(usr, json);
        return usrupdate.toJson();

    }
    @PATCH
    @Path("{id}/ban")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject ban(@PathParam("id") Long id, JsonObject json) {
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        User userblock = store.ban(user, json);
        return userblock.toJson();
    }
}