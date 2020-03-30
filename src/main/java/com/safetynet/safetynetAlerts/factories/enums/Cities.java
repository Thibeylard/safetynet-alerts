package com.safetynet.safetynetAlerts.factories.enums;

/**
 * Enumeration of virtual Cities used for tests.
 */
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
