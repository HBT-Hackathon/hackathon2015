package de.hbt.hackathon2015.geofox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hbt.hackathon2015.DynamicByteBuffer;
import de.hbt.hackathon2015.Mesh;

public class LineTrackParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(LineTrackParser.class);
	
	public Mesh parseMesh() {
		final DynamicByteBuffer positions = new DynamicByteBuffer();
		final DynamicByteBuffer indexBuffer = new DynamicByteBuffer();
		int lastIndex = -1;
		int index = 0;
		File file = new File("lineTracExport.exp");
		LOGGER.info("Parsing Geofox line tracks from file " + file);
		try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
			int lineCount = 1;
			String line = fileReader.readLine();
			while (line != null) {
				String[] cols = line.split(";");
				if (cols.length >= 7) {
					String coords = cols[6].substring(1, cols[6].length() - 1);
					if (!coords.isEmpty()) {
						StringTokenizer tok = new StringTokenizer(coords, ", ");
						while (tok.hasMoreTokens()) {
							String x = tok.nextToken();
							String y = tok.nextToken();
							positions.putFloat(Integer.parseInt(x)).putFloat(Integer.parseInt(y));
							if (lastIndex != -1) {
								indexBuffer.putInt(lastIndex);
								indexBuffer.putInt(index);
							}
							lastIndex = index;
							index++;
						}
					}
				}
				line = fileReader.readLine();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(String.valueOf(lineCount++));
				}
				lastIndex = -1;
			}
			LOGGER.info("Successfully parsed Geofox line tracks, found " + lineCount + " lines");
		} catch (IOException e) {
			LOGGER.error("Failed to parse Geofox line tracks", e);
		}
		positions.flip();
		indexBuffer.flip();
		Mesh mesh = new Mesh();
		mesh.positions = positions.bb;
		mesh.indices = indexBuffer.bb;
		return mesh;
	}
}
