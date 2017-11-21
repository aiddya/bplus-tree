package com.iddya.trees;

import java.util.ArrayList;

/**
 * Record class of the B+ tree. Capable of holding multiple values per key.
 */
class KeyValue {
private final double key;
private final ArrayList<String> values;

/**
 * Initialized a KeyValue class with a key and a value.
 *
 * @param key: New key
 * @param value: New value
 */
KeyValue(double key, String value)
{
    this.key    = key;
    this.values = new ArrayList<>();
    this.values.add(value);
}

/**
 * Gets the key associated with the KeyValue class
 *
 * @return double: Key associated with the KeyValue class
 */
double getKey()
{
    return key;
}

/**
 * Gets the values associated with the key in a String with multiple
 * values separated by a comma.
 *
 * @return String: All values associated with the key
 */
String getValues()
{
    StringBuilder sb = new StringBuilder();
    sb.append(values.get(0));
    for (int i = 1; i < values.size(); i++) {
        sb.append(", ");
        sb.append(values.get(i));
    }
    return sb.toString();
}

/**
 * Adds a value to an existing KeyValue object
 *
 * @param value: Value to be added
 */
void addValue(String value)
{
    values.add(value);
}

/**
 * Gets the key and value(s) associated with the key in a string surrounded
 * by parenthesis with comma separator.
 *
 * @return String: Key and value(s) associated with the key
 */
@Override public String toString()
{
    StringBuilder sb = new StringBuilder();
    String prefix    = "(" + key + ",";
    String suffix    = ")";
    sb.append(prefix);
    sb.append(values.get(0));
    sb.append(suffix);
    for (int i = 1; i < values.size(); i++) {
        sb.append(", ");
        sb.append(prefix);
        sb.append(values.get(i));
        sb.append(suffix);
    }
    return sb.toString();
}
}
