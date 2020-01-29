package com.safetynet.safetynetAlerts.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"address", "number"})
public class Firestation {

    /**
     * address used as identifier. Final attribute.
     */
    private final String address;
    private int number;

    /**
     * Constructor used for JSON serialization and deserialization.
     *
     * @param address value to initialize address attribute
     * @param number value to initialize number attribute
     */
    public Firestation(@JsonProperty("address") final String address,
                       @JsonProperty("number") final int number) {
        this.address = address;
        this.number = number;
    }

    //    -------------------------------------------------------------------- SETTERS

    /**
     * number attributer setter.
     *
     * @param number new value for this.number
     */
    public void setNumber(int number) {
        this.number = number;
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
    public int getNumber() {
        return this.number;
    }
}
