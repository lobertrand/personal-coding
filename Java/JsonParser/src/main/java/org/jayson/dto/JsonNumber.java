package org.jayson.dto;

public class JsonNumber implements JsonElement {

    private double doubleValue;
    private long longValue;
    private final boolean isDouble;

    public JsonNumber(double value) {
        this.isDouble = true;
        this.doubleValue = value;
    }

    public JsonNumber(long value) {
        this.isDouble = false;
        this.longValue = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public boolean isLong() {
        return !isDouble;
    }

    public double getDouble() {
        return isDouble ? doubleValue : longValue;
    }

    public long getLong() {
        return isDouble ? (long) doubleValue : longValue;
    }

    @Override
    public String toString() {
        if (isDouble) return String.valueOf(doubleValue);
        return String.valueOf(longValue);
    }
}