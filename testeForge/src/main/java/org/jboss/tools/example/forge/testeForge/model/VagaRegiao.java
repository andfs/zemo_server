package org.jboss.tools.example.forge.testeForge.model;

public class VagaRegiao 
{
	private LatLng centerNorm;
	private BoundsNorm boundsNorm;
	private Integer zoom;
	private Double boundingRadius;
	
	public LatLng getCenterNorm() {
		return centerNorm;
	}
	public void setCenterNorm(LatLng centerNorm) {
		this.centerNorm = centerNorm;
	}
	public BoundsNorm getBoundsNorm() {
		return boundsNorm;
	}
	public void setBoundsNorm(BoundsNorm boundsNorm) {
		this.boundsNorm = boundsNorm;
	}
	public Integer getZoom() {
		return zoom;
	}
	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	public Double getBoundingRadius() {
		return boundingRadius;
	}
	public void setBoundingRadius(Double boundingRadius) {
		this.boundingRadius = boundingRadius;
	}
}
