package de.hbt.hackathon2015.geofox.rest;

import java.io.Serializable;
import java.util.List;

import de.hbt.coordinate.BoundingBox;
import de.hbt.coordinate.Coordinate;

public class Track implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Coordinate> coordinates;
	private BoundingBox boundingBox;

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

}
