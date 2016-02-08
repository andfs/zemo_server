package org.jboss.tools.example.forge.testeForge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Vaga 
{
	@Id
	@GeneratedValue
	private String id;
	
	@Column
	private Double latitude;
	
	@Column
	private Double longitude;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private EnumTipoVaga tipoVaga;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private EnumStatusVaga statusVaga;
	
	private Boolean isVaga;
	
	private String info;
	
	public Vaga() {}
	
	public Vaga(Double latitude, Double longitude)
	{
		this.latitude  = latitude;
		this.longitude = longitude;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public EnumTipoVaga getTipoVaga() {
		return tipoVaga;
	}

	public void setTipoVaga(EnumTipoVaga tipoVaga) {
		this.tipoVaga = tipoVaga;
	}

	public EnumStatusVaga getStatusVaga() {
		return statusVaga;
	}

	public void setStatusVaga(EnumStatusVaga statusVaga) {
		this.statusVaga = statusVaga;
	}

	public Boolean getIsVaga() {
		return isVaga;
	}

	public void setIsVaga(Boolean isVaga) {
		this.isVaga = isVaga;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
