package com.academysi.controller;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.academysi.dto.UtenteDto;
import com.academysi.dto.UtenteLoginRequestDto;
import com.academysi.dto.UtenteLoginResponseDto;
import com.academysi.dto.UtenteRegistrazioneDto;
import com.academysi.model.Ruolo;
import com.academysi.model.Utente;
import com.academysi.service.UtenteService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
@Path("/utenti")
public class UtenteController {
	
	@Autowired
	private UtenteService utenteService;
	
	public UtenteLoginResponseDto issueToken(String email) {
		
		byte[] secret = "aufgauvwbfawbv928y429bapfvbp2gavfa7bd78g292g17vr7a0wafhb89wf2".getBytes();
	    Key key = Keys.hmacShaKeyFor(secret);
		
	    Utente infoUtente = utenteService.getUserByEmail(email);
	    Map<String, Object> map = new HashMap<>();
	    
	    map.put("nome", infoUtente.getFirstname());
	    map.put("cognome", infoUtente.getLastname());
	    map.put("email", infoUtente.getEmail());
	    
	    List<String> ruoli = new ArrayList<>();
	    for (Ruolo ruolo : infoUtente.getRuoli()) {
	    	ruoli.add(ruolo.getTipologia().name());
	    	
	    }
	    
	    map.put("ruoli", ruoli);
	    
	    Date creationDate = new Date();
	    Date end = java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(15L));
		
	    String tokenJwt = Jwts.builder()
	    		.setClaims(map)
	    		.setIssuer("http://localhost:8080")
	    		.setIssuedAt(creationDate)
	    		.setExpiration(end)
	    		.signWith(key)
	    		.compact();
	    
	    UtenteLoginResponseDto token = new UtenteLoginResponseDto();
	    token.setToken(tokenJwt);
	    token.setTokenCreationTime(creationDate);
	    token.setTtl(end);
	    
		return token;
	}
	
	
	@POST
	@Path("/registrazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(@Valid @RequestBody UtenteRegistrazioneDto utenteDto) {
		try {
			if(!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}",
					utenteDto.getPassword())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			if(utenteService.existUserByEmail(utenteDto.getEmail())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			utenteService.registerUser(utenteDto);
			
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody UtenteLoginRequestDto utenteLoginRequestDto) {
        
		try {
        	if(utenteService.login(utenteLoginRequestDto)) {
        		return Response.ok(issueToken(utenteLoginRequestDto.getEmail())).build();
        	}
        } catch (Exception e) {
        	return Response.status(Response.Status.BAD_REQUEST).build();
        }
		
		return Response.status(Response.Status.BAD_REQUEST).build();
    }
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
	    try {
	        List<UtenteDto> utenti = utenteService.getUsers();
	        return Response.status(Response.Status.OK).entity(utenti).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.BAD_REQUEST).build();
	    }
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") int id) {
	    try {
	        UtenteDto utenteDto = utenteService.getUserDtoById(id);
	        return Response.status(Response.Status.OK).entity(utenteDto).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.BAD_REQUEST).build();
	    }    
	}
	
	@GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@QueryParam("email") String email) {
        UtenteDto utenteDto = utenteService.getUserDtoByEmail(email);
        if (utenteDto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(utenteDto).build();
    }
	
	@PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@QueryParam("email") String email, @RequestBody UtenteDto utenteDto) {
        try {
            utenteService.updateUserByEmail(email, utenteDto);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
	
	@DELETE
	@Path("/{id}")
	public Response deleteUserById(@PathParam("id") int id) {
		try {
			utenteService.deleteUserById(id);
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@DELETE
	@Path("/email/{email}")
	public Response deleteUserByEmail(@PathParam("email") String email) {
	    try {
	        utenteService.deleteUserByEmail(email);
	        return Response.status(Response.Status.OK).build();
	    } catch (EntityNotFoundException e) {
	        return Response.status(Response.Status.NOT_FOUND).build();
	    }
	}
	
	@POST
    @Path("/{utenteId}/subscribe/{corsoId}")
    public Response subscribeToCorso(@PathParam("utenteId") int utenteId, @PathParam("corsoId") int corsoId) {
        try {
            utenteService.subscribeToCorso(utenteId, corsoId);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{utenteId}/unsubscribe/{corsoId}")
    public Response unsubscribeFromCorso(@PathParam("utenteId") int utenteId, @PathParam("corsoId") int corsoId) {
        try {
            utenteService.unsubscribeFromCorso(utenteId, corsoId);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
