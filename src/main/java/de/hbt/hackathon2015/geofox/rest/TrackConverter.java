package de.hbt.hackathon2015.geofox.rest;

import java.util.ArrayList;
import java.util.List;

import de.hbt.coordinate.BoundingBox;
import de.hbt.coordinate.Coordinate;
import de.hbt.coordinate.SpatialReferenceSystem;
import de.hbt.coordinate.gk.GKCoordinate;
import de.hbt.coordinate.wgs84.Wgs84;
import de.hbt.coordinate.wgs84.Wgs84Coordinate;

/**
 * Stateless converter that creates a Hackathon-2015 track object from a Geofox {@link hbt.geofox.mixins.requests.VMRequest.Track track}.
 *  
 * @author Andre Hegerath
 */
public class TrackConverter {
	
	private static final SpatialReferenceSystem<Wgs84Coordinate> WGS84 = Wgs84.INSTANCE;

	public Track convertTrack(hbt.geofox.mixins.requests.VMRequest.Track geofoxTrack) {
		Track track = new Track();

		// poor design to return a plain sequence of integers and call it "coordinates" :-(
		// we have to guess that it is a mixed sequence of GK easting and northing values...
		int[] coordinates = geofoxTrack.getCoordinates();
		
		List<Coordinate> trackCoordinates = new ArrayList<>();
		for (int i = 0; i < coordinates.length; i += 2) {
			GKCoordinate gkCoodinate = new GKCoordinate(coordinates[i], coordinates[i + 1]);
			Wgs84Coordinate wgs84Coordinate = gkCoodinate.transformCoordinate(WGS84);
			trackCoordinates.add(wgs84Coordinate);
		}
		track.setCoordinates(trackCoordinates);
		track.setBoundingBox(BoundingBox.createBoundingBox(WGS84, trackCoordinates));
		
		return track;
	}

}
