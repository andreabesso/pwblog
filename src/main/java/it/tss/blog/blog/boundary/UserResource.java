/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.blog.blog.boundary;

import it.tss.blog.blog.control.UserStore;
import it.tss.blog.blog.entity.User;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author andre
 */
@DenyAll
public class UserResource {

    @Inject
    UserStore store;

    @Context
    ResourceContext resource;

    private Long userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public JsonObject find(@PathParam("id") Long id) {
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        return user.toJson();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public JsonObject update(@PathParam("id") Long id, JsonObject json) {
        String fname = json.getString("fname");
        String lname = json.getString("lname");
        String pwd = json.getString("pwd");
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        user.setFname(fname);
        user.setLname(lname);
        user.setPwd(pwd);
        User usrupdate = store.update(user, json);
        return usrupdate.toJson();

    }

    @PATCH
    @Path("/coadmin")//creo un coAdmin che solo un ADMIN può promuovere
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public JsonObject coAdmin(@PathParam("id") Long id, JsonObject json) {
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        User usrCoAdmin = store.coAdmin(user, json);
        return usrCoAdmin.toJson();
    }

    @PATCH
    @Path("/ban")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})//solo l'admin puo usare il metodo
    public JsonObject ban(@PathParam("id") Long id, JsonObject json) {
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        User userblock = store.ban(user, json);
        return userblock.toJson();
    }

    @DELETE
    @RolesAllowed({"ADMIN", "USER"})
    public Response delete(@PathParam("userId") Long id) {
        User user = store.find(userId).orElseThrow(() -> new NotFoundException());
        store.delete(userId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
