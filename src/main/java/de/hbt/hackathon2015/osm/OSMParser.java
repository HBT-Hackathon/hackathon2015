package de.hbt.hackathon2015.osm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crosby.binary.BinaryParser;
import crosby.binary.Osmformat.DenseNodes;
import crosby.binary.Osmformat.HeaderBlock;
import crosby.binary.Osmformat.Node;
import crosby.binary.Osmformat.Relation;
import crosby.binary.Osmformat.Way;
import crosby.binary.file.BlockInputStream;
import de.hbt.coordinate.gk.GKCoordinate;
import de.hbt.coordinate.gk.GaussKruger;
import de.hbt.coordinate.wgs84.Wgs84Coordinate;
import de.hbt.hackathon2015.DynamicByteBuffer;
import de.hbt.hackathon2015.Mesh;

public class OSMParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(OSMParser.class);
	
	public Mesh parseMesh() throws Exception {
		InputStream input = new FileInputStream(new File("hamburg-latest.osm.pbf"));
		final DynamicByteBuffer positions = new DynamicByteBuffer();
		final DynamicByteBuffer indexBuffer = new DynamicByteBuffer();
		BinaryParser brad = new BinaryParser() {
			int nodeCount = 0;
			Map<Long, Long> nodeIdToIndex = new HashMap<Long, Long>();
			long index = 0;

			@Override
			protected void parseRelations(List<Relation> rels) {
			}

			@Override
			protected void parseDense(DenseNodes nodes) {
				long lastId = 0;
				long lastLat = 0;
				long lastLon = 0;

				for (int i = 0; i < nodes.getIdCount(); i++) {
					lastId += nodes.getId(i);
					lastLat += nodes.getLat(i);
					lastLon += nodes.getLon(i);
					float lat = (float) parseLat(lastLat);
					float lon = (float) parseLon(lastLon);
					GKCoordinate gk = new Wgs84Coordinate(lat, lon).transformCoordinate(GaussKruger.INSTANCE_ZONE_3);
					positions.putFloat(gk.getEasting()).putFloat(gk.getNorthing());
					nodeIdToIndex.put(lastId, index++);
				}
				nodeCount += nodes.getIdCount();
			}

			@Override
			protected void parseNodes(List<Node> nodes) {
				nodeCount += nodes.size();
			}

			@Override
			protected void parseWays(List<Way> ways) {
				for (Way w : ways) {
					long lastRef = 0;
					long prevRef = 0;
					int i = 0;
					for (Long ref : w.getRefsList()) {
						lastRef += ref;
						if (i > 0) {
							indexBuffer.putInt(nodeIdToIndex.get(prevRef).intValue());
							indexBuffer.putInt(nodeIdToIndex.get(lastRef).intValue());
						}
						i++;
						prevRef = lastRef;
					}
				}
			}

			@Override
			protected void parse(HeaderBlock header) {
				LOGGER.info("Got header block.");
			}

			@Override
			public void complete() {
				LOGGER.info("Complete!");
				LOGGER.info("# nodes: " + nodeCount);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("at position " + positions.position() + ", capacity " + positions.capacity());
				}
			}
		};
		new BlockInputStream(input, brad).process();
		positions.flip();
		indexBuffer.flip();
		Mesh mesh = new Mesh();
		mesh.positions = positions.bb;
		mesh.indices = indexBuffer.bb;
		return mesh;
	}

}
