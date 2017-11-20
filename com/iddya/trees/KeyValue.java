package com.iddya.trees;

import java.util.ArrayList;

class KeyValue {
private final double key;
private final ArrayList<String> values;

KeyValue(double key, String value)
{
    this.key    = key;
    this.values = new ArrayList<>();
    this.values.add(value);
}

double getKey()
{
    return key;
}

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

void addValue(String value)
{
    values.add(value);
}

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
