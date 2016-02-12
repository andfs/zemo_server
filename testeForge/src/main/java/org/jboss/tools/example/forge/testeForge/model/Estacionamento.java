package org.jboss.tools.example.forge.testeForge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Estacionamento {
	@Id
	@GeneratedValue
	private String id;

	@Column
	private String nome;

	@Column
	private String cnpj;

	@Column
	private String endereco;
	
	@Column
	private String n;

	@Column
	private Double valorHora;

	@Column
	private Double valorFracao;

	@Column
	private Long latitude;

	@Column
	private Long longitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Double getValorHora() {
		return valorHora;
	}

	public void setValorHora(Double valorHora) {
		this.valorHora = valorHora;
	}

	public Double getValorFracao() {
		return valorFracao;
	}

	public void setValorFracao(Double valorFracao) {
		this.valorFracao = valorFracao;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}
}
