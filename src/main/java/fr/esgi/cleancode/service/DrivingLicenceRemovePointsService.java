package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class DrivingLicenceRemovePointsService {

    private final InMemoryDatabase database;
    public DrivingLicence removeDrivingPoints(int pointRemove, DrivingLicence drivingLicence)throws ResourceNotFoundException{
        if(pointRemove < 0) return drivingLicence;
        if(drivingLicence == null)  throw  new ResourceNotFoundException("Enter a driving licence");
        val  drinvingLicenceBuilder = DrivingLicence.builder().id(drivingLicence.getId()).driverSocialSecurityNumber(drivingLicence.getDriverSocialSecurityNumber());
        if(pointRemove> drivingLicence.getAvailablePoints()){
            drinvingLicenceBuilder.availablePoints(0);
        } else if (pointRemove < drivingLicence.getAvailablePoints()) {
            drinvingLicenceBuilder.availablePoints(drivingLicence.getAvailablePoints() - pointRemove);
        }
        return drinvingLicenceBuilder.build();
    }



}
