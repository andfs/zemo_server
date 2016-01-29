package org.jboss.tools.example.forge.testeForge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Usuario 
{
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String email;
	
	@Column
	private String senha;
	
	@Column
	private String nome;
	
	@Column
	private String token;
	
	@Column
	private Long pontos;
	
	public Usuario(){}

	public Usuario(UsuarioVOCreate entity) 
	{
		this.nome  = entity.getNome();
		this.senha = entity.getSenha();
		this.email = entity.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getPontos() {
		return pontos;
	}

	public void setPontos(Long pontos) {
		this.pontos = pontos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
