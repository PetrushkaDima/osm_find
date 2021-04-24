package com.osm.findarea.jsonModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MultipolygonStructure {
    private MultipolygonGeometry geometry;

    public MultipolygonStructure() {
    }

    public MultipolygonGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(MultipolygonGeometry geometry) {
        this.geometry = geometry;
    }
}
