package org.jboss.tools.example.forge.testeForge.model;

public class PosicaoVO 
{
	public PosicaoVO(){}
	
	public PosicaoVO(Double latitude, Double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public PosicaoVO(String latitude, String longitude)
	{
		this.latitude  = Double.valueOf(latitude.trim());
		this.longitude = Double.valueOf(longitude.trim());
	}
	private Double latitude;
	
	private Double longitude;

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
}
