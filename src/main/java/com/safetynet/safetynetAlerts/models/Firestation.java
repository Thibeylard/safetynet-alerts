package com.safetynet.safetynetAlerts.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"address", "number"})
public class Firestation {

    /**
     * address used as identifier. Final attribute.
     */
    @JsonProperty("address")
    private final String address;
    @JsonProperty("station")
    private int station;

    /**
     * Constructor used for JSON serialization and deserialization.
     *
     * @param address value to initialize address attribute
     * @param station value to initialize number attribute
     */
    public Firestation(final String address,
                       final int station) {
        this.address = address;
        this.station = station;
    }

    //    -------------------------------------------------------------------- SETTERS

    /**
     * number attributer setter.
     *
     * @param station new value for this.number
     */
    public void setStation(int station) {
        this.station = station;
    }

    //    -------------------------------------------------------------------- GETTERS

    /**
     * address attribute getter.
     *
     * @return this.address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * number attribute getter.
     *
     * @return this.number
     */
    public int getStation() {
        return this.station;
    }
}
