package de.hbt.hackathon2015.geofox.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hbt.geofox.mixins.proxy.TcpServerProxy;
import hbt.geofox.mixins.requests.VMRequest;
import hbt.geofox.mixins.requests.VMRequest.BoundingBox;
import hbt.geofox.mixins.requests.VMRequest.Journey;
import hbt.geofox.mixins.requests.VMRequest.Period;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.hbt.coordinate.Coordinate;
import de.hbt.coordinate.SpatialReferenceSystem;
import de.hbt.coordinate.gk.GKCoordinate;
import de.hbt.coordinate.gk.GaussKruger;
import de.hbt.coordinate.wgs84.Wgs84;
import de.hbt.coordinate.wgs84.Wgs84Coordinate;
import de.hbt.hackathon2015.geofox.rest.serializers.Wgs84CoordinateSerializer;
import de.hbt.hackathon2015.geofox.rest.serializers.Wgs84Serializer;
import de.hbt.util.geo.int2d.GK;

/**
 * REST service for vehicles on a map.

 * @author Andre Hegerath
 */
@RestController
public class VehicleMapController implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleMapController.class);
	
	// possible vehicle types: "u", "s", "a", "r", "bus", "ship" and some more which you probably don't need...
	private static final String[] VEHICLE_TYPES = { "u", "bus" };
	
	private static final SpatialReferenceSystem<GKCoordinate> GAUSS_KRUEGER = GaussKruger.INSTANCE_ZONE_3; // zone 3 for Hamburg
	
	private TcpServerProxy geofoxProxy;
	
	@Value("${geofoxHostname}")
	private String geofoxHostname;
	
	@Value("${geofoxPort}")
	private int geofoxPort;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		geofoxProxy = new TcpServerProxy(geofoxHostname, geofoxPort);
	}

	/*
	 * Customizes the serialization of coordinates. 
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(Wgs84.class, new Wgs84Serializer());
		module.addSerializer(Wgs84Coordinate.class, new Wgs84CoordinateSerializer());
		objectMapper.registerModule(module);		
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
	
    @RequestMapping("/vehicles")
    public List<Vehicle> vehicles(@RequestParam(value="minLon") double minLon, @RequestParam(value="maxLon") double maxLon,
    		@RequestParam(value="minLat") double minLat, @RequestParam(value="maxLat") double maxLat, 
    		@RequestParam(value="fromUTC") long fromUTC, @RequestParam(value="toUTC") long toUTC) {
    	if (LOGGER.isInfoEnabled()) {
    		DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	    	LOGGER.info(String.format(Locale.US, "Got request for vehicles inside (%4.1f, %4.1f)-(%4.1f, %4.1f) between %s and %s",
	    			minLon, minLat, maxLon, maxLat, dateFormatter.format(new Date(fromUTC)), dateFormatter.format(new Date(toUTC))));
    	}
    	
    	// build request for Geofox
    	Coordinate wgs84LowerLeft = new Wgs84Coordinate(minLat, minLon);
    	Coordinate wgs84UpperRight = new Wgs84Coordinate(maxLat, maxLon);
    	GKCoordinate gkLowerLeft = wgs84LowerLeft.transformCoordinate(GAUSS_KRUEGER); 
    	GKCoordinate gkUpperRight = wgs84UpperRight.transformCoordinate(GAUSS_KRUEGER);
    	LOGGER.info(String.format("GK coordinates: (%d, %d)-(%d, %d)", gkLowerLeft.getEasting(), gkLowerLeft.getNorthing(),
    			gkUpperRight.getEasting(), gkUpperRight.getNorthing()));
    	BoundingBox boundingBox = new BoundingBox(new GK(gkLowerLeft.getEasting(), gkLowerLeft.getNorthing()),
    			new GK(gkUpperRight.getEasting(), gkUpperRight.getNorthing()));
    	Period period = new Period(fromUTC, toUTC);
    	VMRequest vmRequest = new VMRequest(boundingBox, period, false, VEHICLE_TYPES);
    	
    	// have it handled by Geofox
    	if (!geofoxProxy.handleRequest(vmRequest)) {
    		LOGGER.warn("Geofox failed to handle request.");
    		return Arrays.asList();
    	}
    	
    	// Convert the result and return it
    	VehicleBuilder vehicleBuilder = new VehicleBuilder();
    	Journey[] journeys = vmRequest.getJourneys();
		LOGGER.info("Geofox returned " + journeys.length + " vehicles.");
    	for (Journey journey: journeys) {
    		vehicleBuilder.addJourney(journey);
    	}
        return vehicleBuilder.getVehicles();
    }
    
}
