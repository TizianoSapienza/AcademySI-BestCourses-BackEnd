package com.academysi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.academysi.dto.CorsoDto;
import com.academysi.dto.CorsoRegistrazioneDto;
import com.academysi.dto.CorsoUpdateDto;
import com.academysi.jwt.JWTTokenNeeded;
import com.academysi.jwt.Secured;
import com.academysi.mapper.CorsoMapper;
import com.academysi.model.Corso;
import com.academysi.service.CorsoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RestController
@Path("/corsi")
public class CorsoController {

    @Autowired
    private CorsoService corsoService;
    
    @Secured(role = "Admin")
    @JWTTokenNeeded
    @POST
    @Path("/registrazione")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerCorso(CorsoRegistrazioneDto corsoRegistrazioneDto) {
        try {
            corsoService.registerCorso(corsoRegistrazioneDto);
            return Response.status(Response.Status.OK).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsi() {
        List<CorsoDto> corsi = corsoService.getCorsi();
        return Response.status(Response.Status.OK).entity(corsi).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsoById(@PathParam("id") int id) {
    	
        Corso corso = corsoService.getCorsoById(id);
        if (corso == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        CorsoDto corsoDto = CorsoMapper.toDto(corso);
        return Response.status(Response.Status.OK).entity(corsoDto).build();
    }

    @Secured(role = "Admin")
    @JWTTokenNeeded
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCorso(@PathParam("id") int id, CorsoUpdateDto corsoDto) {
        try {
            corsoService.updateCorso(id, corsoDto);
            return Response.ok().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Secured(role = "Admin")
    @JWTTokenNeeded
    @DELETE
    @Path("/{id}")
    public Response deleteCorsoById(@PathParam("id") int id) {
        corsoService.deleteCorsoById(id);
        return Response.status(Response.Status.OK).build();
    }
    
    @GET
    @Path("/searchName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsiByNome(@QueryParam("nome") String nome) {
        try {
            List<CorsoDto> corsi = corsoService.findCorsiByNome(nome);
            return Response.status(Response.Status.OK).entity(corsi).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/searchLength")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsiByDurata(@QueryParam("durata") int durata) {
        try {
            List<CorsoDto> corsi = corsoService.findCorsiByDurata(durata);
            return Response.status(Response.Status.OK).entity(corsi).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("/searchCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCorsiByCategoriaId(@QueryParam("categoryId") int categoriaId) {
        try {
            List<CorsoDto> corsi = corsoService.findCorsiByCategoriaId(categoriaId);
            return Response.status(Response.Status.OK).entity(corsi).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}