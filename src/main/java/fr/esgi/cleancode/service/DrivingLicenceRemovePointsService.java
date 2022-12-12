package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicenceRemovePointsService {

    private final InMemoryDatabase database;
    public DrivingLicence removeDrivingPoints(int pointRemove, UUID id)throws ResourceNotFoundException{
        final var drivingLicenceOptional= database.findById(id);
        if(drivingLicenceOptional.isEmpty()) throw  new ResourceNotFoundException("Enter a driving licence");
        final var drivingLicence = drivingLicenceOptional.get();
        if(pointRemove < 0) return drivingLicence;
        val  drinvingLicenceBuilder = DrivingLicence.builder().id(drivingLicence.getId()).driverSocialSecurityNumber(drivingLicence.getDriverSocialSecurityNumber());
        if(pointRemove> drivingLicence.getAvailablePoints()){
            drinvingLicenceBuilder.availablePoints(0);
        } else if (pointRemove < drivingLicence.getAvailablePoints()) {
            drinvingLicenceBuilder.availablePoints(drivingLicence.getAvailablePoints() - pointRemove);
        }
        return drinvingLicenceBuilder.build();

    }



}
