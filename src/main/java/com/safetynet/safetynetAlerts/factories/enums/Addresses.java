package com.safetynet.safetynetAlerts.factories.enums;

/**
 * Enumeration of virtual Addresses used for tests.
 */
public enum Addresses {
    MECHANIC("4 Mechanic Street", Cities.OAKPARK),
    CIRCLE("564 Circle Street", Cities.OAKPARK),
    BRICKYARD("56 Brickyard Ave.", Cities.OAKPARK),
    COURTLAND("747 Courtland Lane", Cities.OAKPARK),
    APPLEGATE("819 Applegate Street", Cities.OAKPARK),
    GOLFCOURT("9833 Golf Court", Cities.OAKPARK),
    FIFTHROAD("479 Fifth Road", Cities.OAKPARK),
    THOMASROAD("8490 W. Thomas Rd.", Cities.OAKPARK),

    HERITAGE("938 Heritage St.", Cities.WALTHAM),
    OLDYORK("570 Old York Ave.", Cities.WALTHAM),
    ELMDRIVE("3 Elm Drive", Cities.WALTHAM),

    BAYMEADOWS("39 Bay Meadows St.", Cities.WILLOUGHBY),
    KIRKLAND("7054 Kirkland Lane", Cities.WILLOUGHBY),
    MARCONI("7981 Marconi Street", Cities.WILLOUGHBY);

    private final String name;
    private final Cities city;

    Addresses(String name, Cities city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public Cities getCity() {
        return city;
    }

    public static Addresses getFromAddress(String address) {
        for (Addresses completeAddress : values()
        ) {
            if (completeAddress.name.equals(address)) {
                return completeAddress;
            }
        }
        return null;
    }
}
