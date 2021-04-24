package com.osm.findarea.jsonModel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MultipolygonGeometry {
    @JsonAlias(value = "coordinates")
    private List<List<List<List<Double>>>> coordinates;

    public MultipolygonGeometry() {
    }

    public List<List<List<List<Double>>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<List<Double>>>> coordinates) {
        this.coordinates = coordinates;
    }
}
