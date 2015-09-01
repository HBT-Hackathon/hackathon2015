package de.hbt.hackathon2015.geofox.rest;

import java.util.ArrayList;
import java.util.List;

import hbt.geofox.mixins.requests.VMRequest.Journey;

/**
 * Builder/converter that creates a Hackathon-2015 vehicle object from a Geofox {@link hbt.geofox.mixins.requests.VMRequest.Journey Journey}.

 * @author Andre Hegerath
 */
public class VehicleBuilder {

	private List<Vehicle> vehicles;
	
	public VehicleBuilder() {
		vehicles = new ArrayList<>();
	}
	
	public void addJourney(Journey journey) {
		Vehicle vehicle = new Vehicle();
		vehicle.setDirection(journey.getDirection());
		vehicle.setLineKey(journey.getLineKey());
		vehicle.setLineName(journey.getLineName());
		vehicle.setModel(journey.getModel());
		vehicle.setOrigin(journey.getOrigin());
		vehicle.setRealtime(journey.isRealtime());
		vehicle.setServiceID(journey.getServiceID());
		vehicle.setVehicleType(journey.getVehicleType());
		vehicle.setVehicleTypeString(journey.getVehicleTypeString());
		
		PathSegmentBuilder segmentBuilder = new PathSegmentBuilder();
		for (hbt.geofox.mixins.requests.VMRequest.PathSegment pathSegment: journey.getSegments()) {
			segmentBuilder.addPathSegment(pathSegment);
		}
		vehicle.setPathSegments(segmentBuilder.getPathSegments());
		
		vehicles.add(vehicle);
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

}
