package com.amr.tidetaskjava.data.models;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

@Parcel
public class Bar {
    @Expose
    String id;

    @Expose
    String name;

    @Expose
    double distance;

    @Expose
    Geometry geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public LatLng getLatLng() {
        if (geometry.getLocation() != null) {
            return new LatLng(geometry.getLocation().getLatitude(), geometry.getLocation().getLongitude());
        }
        return null;
    }
}
