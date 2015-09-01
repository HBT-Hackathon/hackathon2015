package de.hbt.hackathon2015.geofox.rest.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.hbt.coordinate.wgs84.Wgs84;

public class Wgs84Serializer extends JsonSerializer<Wgs84> {

	@Override
	public void serialize(Wgs84 wgs84, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,
			JsonProcessingException {
		jsonGenerator.writeStartObject();
		// no properties to be saved.
		jsonGenerator.writeEndObject();
	}

}
