package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DrivingLicenceCreationService {

    private final InMemoryDatabase database;

    public DrivingLicence save(String socialSecurityNumber) throws InvalidDriverSocialSecurityNumberException{
        if(!isValidSocialNumber(socialSecurityNumber)) throw  new InvalidDriverSocialSecurityNumberException("Social Number is not good");
        DrivingLicenceIdGenerationService drivingLicenceIdGenerationService = new DrivingLicenceIdGenerationService();
        return DrivingLicence.builder()
                .id(drivingLicenceIdGenerationService.generateNewDrivingLicenceId())
                .driverSocialSecurityNumber(socialSecurityNumber)
                .build();
    }

    private boolean isValidSocialNumber(String socialSecurityNumber){
        if (socialSecurityNumber == null) return false;
        if (socialSecurityNumber.length() != 15) return false;
        return socialSecurityNumber.matches("^[0-9]*$");
    }
}