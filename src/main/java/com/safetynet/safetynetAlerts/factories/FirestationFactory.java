package com.safetynet.safetynetAlerts.factories;

import com.safetynet.safetynetAlerts.models.Firestation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetAlerts.factories.UtilsFactory.assignAddress;
import static com.safetynet.safetynetAlerts.factories.UtilsFactory.getRandom;

@Repository
public class FirestationFactory {

    /**
     * Creates a Firestation with randomly generated values.
     *
     * @return new Firestation
     */
    public static Firestation createFirestation() {
        return createFirestation(null, null);
    }

    /**
     * Creates a Firestation with randomly generated values for null parameters.
     *
     * @param address Firestation attribute value (Optional)
     * @param station Firestation attribute value (Optional)
     * @return new Firestation
     */
    public static Firestation createFirestation(@Nullable String address,
                                                @Nullable Integer station) {
        if (address == null) {
            address = assignAddress().getName();
        }

        if (station == null) {
            station = getRandom().nextInt(10) + 1;
        }

        return new Firestation(address, station);
    }

    /**
     * Creates a List of Firestation with randomly generated values for null parameters.
     *
     * @param count   number of Firestation to generate
     * @param station Firestation attribute value for all Firestations (Optional)
     * @return new Firestation List
     */
    public static List<Firestation> createFirestations(int count, @Nullable Integer station) {
        List<Firestation> firestations = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            firestations.add(createFirestation(null, station));
        }

        return firestations;
    }
}
