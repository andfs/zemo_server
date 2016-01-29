package org.jboss.tools.example.forge.testeForge.model;

import java.math.BigDecimal;

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
	private Long id;

	@Column
	private String nome;

	@Column
	private String cnpj;

	@Column
	private String endereco;
	
	@Column
	private String n;

	@Column
	private BigDecimal valorHora;

	@Column
	private BigDecimal valorFracao;

	@Column
	private Long latitude;

	@Column
	private Long longitude;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public BigDecimal getValorHora() {
		return valorHora;
	}

	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	public BigDecimal getValorFracao() {
		return valorFracao;
	}

	public void setValorFracao(BigDecimal valorFracao) {
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
