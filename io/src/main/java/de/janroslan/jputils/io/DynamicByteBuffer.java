/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.janroslan.jputils.io;

import java.nio.ByteBuffer;

/**
 *
 * @author Jackjan
 */
public class DynamicByteBuffer
{

    private ByteBuffer buffer;
    private final int maximum;

    /**
     * Allocates a buffer with the given capacity and the given maximal capacity
     * @param capacity
     * @param max
     * @return 
     */
    public static DynamicByteBuffer allocate(int capacity, int max) {
        if (max < 1024) {
            return new DynamicByteBuffer(ByteBuffer.allocate(max), max);
        } else {
            return new DynamicByteBuffer(ByteBuffer.allocate(1024), max);
        }
    }

    /**
     * Allocates a Buffer with a standard capacity of 1024 Bytes and no maximum
     * @return 
     */
    public static DynamicByteBuffer allocate() {
        return new DynamicByteBuffer(ByteBuffer.allocate(1024), 0);
    }
    
    /**
     * Allocates a Buffer with a standard capacyity of 1024 Bytes and the given maximum
     * @return 
     */
    public static DynamicByteBuffer allocate(int max) {
        return new DynamicByteBuffer(ByteBuffer.allocate(1024), max);
    }

    private DynamicByteBuffer(ByteBuffer source, int max) {
        buffer = source;
        maximum = max;
    }

    /**
     * Puts the given byte-Array into the buffer. Resizes the buffer if needed
     * @param val 
     */
    public void put(byte[] val) {
        ByteBuffer buff = buffer;

        // Resize buffer if needed
        if (buffer.remaining() < val.length) {
            buff = ByteBuffer.allocate(buff.capacity() + val.length);
            buffer.flip();
            buff.put(buffer);
            buffer = buff;
        } else {
            buffer.put(val);
        }

    }

    /**
     * Returns the underlying buffer
     * @return 
     */
    public ByteBuffer getBuffer() {
        return buffer;
    }

    /**
     * Return the underlying array inside the buffer
     * @return 
     */
    public byte[] array() {
        return buffer.array();

    }

    /**
     * Gets all the bytes from the current position till the limit.
     *
     * @return - The byte array containing all the bytes
     */
    public byte[] getContent() {
        byte[] result = new byte[buffer.remaining()];
        int i = 0;
        while (buffer.hasRemaining()) {
            result[i] = buffer.get();
            i++;
        }
        return result;
    }

    /**
     * Flips the buffer
     */
    public void flip() {
        buffer.flip();
    }
}
