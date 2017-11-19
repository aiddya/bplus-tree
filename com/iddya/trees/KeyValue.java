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

protected String getValues()
{
    StringBuilder sb = new StringBuilder();
    sb.append(values.get(0));
    for (int i = 1; i < values.size(); i++) {
        sb.append(", ");
        sb.append(values.get(i));
    }
    return sb.toString();
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
