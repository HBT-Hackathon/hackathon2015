package de.hbt.hackathon2015.geofox.rest;

import java.io.Serializable;
import java.util.List;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** Fahrt ID - Unique identifier for the journey of the vehicle*/
    private String serviceID;
    
    /** unique ID of the line on which this vehicle operates */
    private String lineKey;
    private String lineName;
    
    /** the type of this vehicle (int-Representation)*/
    private int vehicleType;
    
    /** the type of this vehicle (String-Representation)*/
    private String vehicleTypeString;
    
    /** the direction of the journey of this vehicle */
    private String direction;
    /** the origin of the journey of this vehicle */
    private String origin;
    /** if data is based on realtime informations */
    private boolean realtime;
    /** each journey of a vehicle is splitted in one segment between two stations */
    private List<PathSegment> pathSegments
    ;
    
    /** extra info like wagon types */
    private String model;

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getLineKey() {
		return lineKey;
	}

	public void setLineKey(String lineKey) {
		this.lineKey = lineKey;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleTypeString() {
		return vehicleTypeString;
	}

	public void setVehicleTypeString(String vehicleTypeString) {
		this.vehicleTypeString = vehicleTypeString;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public boolean isRealtime() {
		return realtime;
	}

	public void setRealtime(boolean realtime) {
		this.realtime = realtime;
	}

	public List<PathSegment> getPathSegments() {
		return pathSegments;
	}

	public void setPathSegments(List<PathSegment> pathSegments) {
		this.pathSegments = pathSegments;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
    
}
