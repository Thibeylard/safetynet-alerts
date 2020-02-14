package com.safetynet.safetynetAlerts.factories;

import com.safetynet.safetynetAlerts.models.Firestation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.safetynet.safetynetAlerts.factories.UtilsFactory.assignAddress;
import static com.safetynet.safetynetAlerts.factories.UtilsFactory.getRandom;

public class FirestationFactory {
    /**
     * Generates a Firestation with randomly generated values for empty optional parameters.
     *
     * @param address Firestation attribute value (Optional)
     * @param station Firestation attribute value (Optional)
     * @return new Firestation
     */
    public Firestation getFirestation(Optional<String> address,
                                      Optional<Integer> station) {
        if (address.isEmpty()) {
            address = Optional.of(assignAddress().getName());
        }

        if (station.isEmpty()) {
            station = Optional.of(getRandom().nextInt(10) + 1);
        }

        return new Firestation(address.get(), station.get());
    }

    /**
     * Generates a List of Firestation with randomly generated values for empty optional parameters.
     *
     * @param count   number of Firestation to generate
     * @param station Firestation attribute value for all Firestations (Optional)
     * @return new Firestation List
     */
    public List<Firestation> getFirestations(int count, Optional<Integer> station) {
        List<Firestation> firestations = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            firestations.add(getFirestation(Optional.empty(), station));
        }

        return firestations;
    }
}
