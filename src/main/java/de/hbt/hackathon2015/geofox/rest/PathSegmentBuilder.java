package de.hbt.hackathon2015.geofox.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder/converter that creates a Hackathon-2015 path segment object from a Geofox {@link hbt.geofox.mixins.requests.VMRequest.PathSegment path segment}.
 *  
 * @author Andre Hegerath
 */
public class PathSegmentBuilder {

	private TrackConverter trackConverter;
	
	private List<PathSegment> pathSegments;
	
	public PathSegmentBuilder() {
		pathSegments = new ArrayList<>();
		trackConverter = new TrackConverter();
	}
	
	public List<PathSegment> getPathSegments() {
		return pathSegments;
	}

	public void addPathSegment(hbt.geofox.mixins.requests.VMRequest.PathSegment geofoxPathSegment) {
		PathSegment pathSegment = new PathSegment();
		pathSegment.setDestination(geofoxPathSegment.getDestination());
		pathSegment.setEndDateTime(geofoxPathSegment.getEndDateTime());
		pathSegment.setEndStationKey(geofoxPathSegment.getEndStationKey());
		pathSegment.setEndStationName(geofoxPathSegment.getEndStationName());
		pathSegment.setEndStopPointKey(geofoxPathSegment.getEndStationKey());
		pathSegment.setFirst(geofoxPathSegment.isFirst());
		pathSegment.setLast(geofoxPathSegment.isLast());
		pathSegment.setRealtimeDelay(geofoxPathSegment.getRealtimeDelay());
		pathSegment.setStartDateTime(geofoxPathSegment.getStartDateTime());
		pathSegment.setStartStationKey(geofoxPathSegment.getStartStationKey());
		pathSegment.setStartStationName(geofoxPathSegment.getStartStationName());
		pathSegment.setStartStopPointKey(geofoxPathSegment.getStartStopPointKey());
		pathSegment.setTrack(trackConverter.convertTrack(geofoxPathSegment.getTrack()));
		pathSegments.add(pathSegment);
	}

}
