package de.hbt.hackathon2015;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import de.hbt.hackathon2015.geofox.LineTrackParser;

@Component
public class Hackathon2015WebSocketHandler extends BinaryWebSocketHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(Hackathon2015WebSocketHandler.class);
	
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		super.handleBinaryMessage(session, message);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOGGER.info("Connected!");
		// OSMParser parser = new OSMParser();
		LineTrackParser parser = new LineTrackParser();
		LOGGER.info("Parsing...");
		Mesh m = parser.parseMesh();
		LOGGER.info("Complete!");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("at position " + m.positions.position() + ", capacity: " + m.positions.capacity());
		}
		ByteBuffer bb = ByteBuffer.allocate(m.positions.remaining() + 1).order(ByteOrder.nativeOrder());
		bb.put((byte) 0);
		bb.put(m.positions);
		bb.flip();
		BinaryMessage message = new BinaryMessage(bb);
		session.sendMessage(message);
		bb = ByteBuffer.allocate(m.indices.remaining() + 1).order(ByteOrder.nativeOrder());
		bb.put((byte) 1);
		bb.put(m.indices);
		bb.flip();
		message = new BinaryMessage(bb);
		session.sendMessage(message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		LOGGER.info("Disconnected!" + status.getReason());
	}

}
