package com.academysi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "utente")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID_U")
	private int id;
	
	@Column(name ="Nome")
	private String firstname;
	
	@Column(name ="Cognome")
	private String lastname;
	
	@Column(name ="email")
	private String email;
	
	@Column(name ="password")
	private String password;
	
	
	//Un utente puo avere piu corsi e viceversa
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable
	(	
		name = "utenti_corsi", joinColumns = @JoinColumn(name = "FK_UC", referencedColumnName = "ID_U"),
		inverseJoinColumns = @JoinColumn(name = "FK_CU", referencedColumnName = "ID_C")
	)
	private List<Corso> corsi = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
        name = "utente_ruolo",
        joinColumns = @JoinColumn(name = "FK_U", referencedColumnName = "ID_U"),
        inverseJoinColumns = @JoinColumn(name = "FK_R", referencedColumnName = "ID_R")
    )
    private List<Ruolo> ruoli = new ArrayList<>();
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public List<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Corso> getCorsi() {
		return corsi;
	}

	public void setCorsi(List<Corso> corsi) {
		this.corsi = corsi;
	}
}
