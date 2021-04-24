package com.osm.findarea.jsonModel;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Structure {

    @JsonAlias(value = "place_id")
    private String placeId;

    @JsonAlias(value = "admin_level")
    private String adminLevel;

    private Hierarchy[] hierarchy;

    public Structure() {
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Hierarchy[] getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Hierarchy[] hierarchy) {
        this.hierarchy = hierarchy;
    }
}
