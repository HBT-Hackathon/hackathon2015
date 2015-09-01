package de.hbt.hackathon2015.geofox.rest.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.hbt.coordinate.wgs84.Wgs84Coordinate;

public class Wgs84CoordinateSerializer extends JsonSerializer<Wgs84Coordinate> {

	@Override
	public void serialize(Wgs84Coordinate coordinate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,
			JsonProcessingException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("lat", coordinate.getLat());
		jsonGenerator.writeNumberField("lon", coordinate.getLon());
		jsonGenerator.writeEndObject();
	}

}
