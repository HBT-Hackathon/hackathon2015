package de.hbt.hackathon2015;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DynamicByteBuffer {

	public ByteBuffer bb;

	public DynamicByteBuffer() {
		bb = ByteBuffer.allocate(1024 * 1024).order(ByteOrder.nativeOrder());
	}

	private void grow() {
		ByteBuffer newbb = ByteBuffer.allocate((int) (bb.capacity() * 1.5)).order(ByteOrder.nativeOrder());
		bb.flip();
		newbb.put(bb);
		bb = newbb;
	}

	public DynamicByteBuffer putFloat(float v) {
		if (bb.remaining() < 4) {
			grow();
		}
		bb.putFloat(v);
		return this;
	}

	public DynamicByteBuffer putLong(long v) {
		if (bb.remaining() < 8) {
			grow();
		}
		bb.putLong(v);
		return this;
	}

	public DynamicByteBuffer putInt(int v) {
		if (bb.remaining() < 4) {
			grow();
		}
		bb.putInt(v);
		return this;
	}

	public int position() {
		return bb.position();
	}

	public int capacity() {
		return bb.capacity();
	}

	public void flip() {
		bb.flip();
	}

	public int remaining() {
		return bb.remaining();
	}

	public DynamicByteBuffer put(byte b) {
		if (bb.remaining() < 1) {
			grow();
		}
		bb.put(b);
		return this;
	}

}
