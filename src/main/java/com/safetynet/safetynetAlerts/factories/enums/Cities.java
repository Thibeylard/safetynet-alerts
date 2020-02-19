package com.safetynet.safetynetAlerts.factories.enums;

public enum Cities {

    OAKPARK("Oak Park", "48237"),
    WILLOUGHBY("Willoughby", "44094"),
    WALTHAM("Waltham", "02453");

    final private String name;
    final private String zip;

    Cities(String name, String zip) {
        this.name = name;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public String getZip() {
        return zip;
    }
}
