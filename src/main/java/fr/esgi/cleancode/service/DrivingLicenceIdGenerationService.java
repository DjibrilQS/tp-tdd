package fr.esgi.cleancode.service;

import java.util.UUID;

public class DrivingLicenceIdGenerationService {

    public static   UUID generateNewDrivingLicenceId() {
        return UUID.randomUUID();
    }
}
