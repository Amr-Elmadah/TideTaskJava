package com.amr.tidetaskjava.data.models;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

@Parcel
public class Geometry {
    @Expose
    Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
