package com.iddya.trees;

import java.util.ArrayList;

class KeyValue {
private double key;
private ArrayList<String> values;

protected KeyValue(double key, String value)
{
    this.key    = key;
    this.values = new ArrayList<String>();
    this.values.add(value);
}

protected double getKey()
{
    return key;
}

protected Boolean isSingle()
{
    return values.size() == 1;
}

protected String getSingleValue()
{
    if (values.size() == 1) {
        return values.get(0);
    } else {
        return null;
    }
}

protected ArrayList<String> getAllValues()
{
    return values;
}

protected void addValue(String value)
{
    values.add(value);
}

public String toString()
{
    String prefix = "(" + key + ";";
    String suffix = ")";
    String mid    = values.get(0);
    for (int i = 1; i < values.size(); i++)
        mid = mid + "," + values.get(i);
    return prefix + mid + suffix;
}
}
