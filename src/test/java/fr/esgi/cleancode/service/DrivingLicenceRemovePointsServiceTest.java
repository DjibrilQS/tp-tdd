package fr.esgi.cleancode.service;


import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceRemovePointsServiceTest {


    @Mock
    private DrivingLicenceRemovePointsService service;




    @Test
    @DisplayName("test removing points valid")
    void should_remove_points(){
        final var driverSocialSecurityNumber = "123456789012345";
        final var points = 3;
        final var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber(driverSocialSecurityNumber).build();
        final var drivingLicenceReturn = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber(driverSocialSecurityNumber).availablePoints(9).build();
        when(service.removeDrivingPoints(points, drivingLicence)).thenReturn(drivingLicenceReturn);
        final var actuel = service.removeDrivingPoints(points, drivingLicence);
        assertEquals(actuel.getAvailablePoints(), 9 );
        verify(service).removeDrivingPoints(points, drivingLicence);
    }

    @Test
    @DisplayName("test removing points invalid")
    void should_not_remove_more_points(){
        final var driverSocialSecurityNumber = "123456789012345";
        final var points = 13;
        final var pointsReturn = 0;
        final var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber(driverSocialSecurityNumber).build();
        final var drivingLicenceReturn = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber(driverSocialSecurityNumber).availablePoints(pointsReturn).build();
        when(service.removeDrivingPoints(points, drivingLicence)).thenReturn(drivingLicenceReturn);
        final var actuel = service.removeDrivingPoints(points, drivingLicence);
        assertEquals(actuel.getAvailablePoints(), pointsReturn );
        verify(service).removeDrivingPoints(points, drivingLicence);
    }

    @Test
    @DisplayName("test removing negative points")
    void should_not_remove_any_points(){
        final var driverSocialSecurityNumber = "123456789012345";
        final var points = -3;
        final var pointsReturn = 12;
        final var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber(driverSocialSecurityNumber).build();
        final var drivingLicenceReturn = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber(driverSocialSecurityNumber).availablePoints(pointsReturn).build();
        when(service.removeDrivingPoints(points, drivingLicence)).thenReturn(drivingLicenceReturn);
        final var actuel = service.removeDrivingPoints(points, drivingLicence);
        assertEquals(actuel.getAvailablePoints(), pointsReturn );
        verify(service).removeDrivingPoints(points, drivingLicence);
    }



}
