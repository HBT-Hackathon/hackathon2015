package de.hbt.hackathon2015.geofox.rest;

import java.io.Serializable;

public class PathSegment implements Serializable {

	private static final long serialVersionUID = 1L;

    /** the name of the startstation of this segment */
    private String startStationName;
    /**  the key of the startstation of this segment */
    private String startStationKey;
    /** UTC timestamp - this segment begins at the first coordinate 
     * of the track at this start date/time */
    private long startDateTime;

    /** the name of the endstation of this segment */
    private String endStationName;
    /**  the key of the endstation of this segment */
    private String endStationKey;
    /** UTC timestamp - this segment ends at the last coordinate 
     * of the track at this end date/time */
    private long endDateTime;

    /** geographic track */
    private Track track;
    /** realtime delay informations in minutes */
    private int realtimeDelay;
    /**determins if this is the First segment of a Journey*/
    private boolean isFirst;
    /**determins if this is the Last segment of a Journey*/
    private boolean isLast;
    
    /** key of the segment's start stoppoint */
    private String startStopPointKey;
    
    /** key of the segment's destination stoppoint */
    private String endStopPointKey;

    private String destination;
    
    
    public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	public String getStartStationKey() {
		return startStationKey;
	}

	public void setStartStationKey(String startStationKey) {
		this.startStationKey = startStationKey;
	}

	public long getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(long startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}

	public String getEndStationKey() {
		return endStationKey;
	}

	public void setEndStationKey(String endStationKey) {
		this.endStationKey = endStationKey;
	}

	public long getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(long endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public int getRealtimeDelay() {
		return realtimeDelay;
	}

	public void setRealtimeDelay(int realtimeDelay) {
		this.realtimeDelay = realtimeDelay;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public String getStartStopPointKey() {
		return startStopPointKey;
	}

	public void setStartStopPointKey(String startStopPointKey) {
		this.startStopPointKey = startStopPointKey;
	}

	public String getEndStopPointKey() {
		return endStopPointKey;
	}

	public void setEndStopPointKey(String endStopPointKey) {
		this.endStopPointKey = endStopPointKey;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	} 

}
