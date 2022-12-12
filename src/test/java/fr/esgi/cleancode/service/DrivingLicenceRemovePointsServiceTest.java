package fr.esgi.cleancode.service;


import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceRemovePointsServiceTest {

    @InjectMocks
    private InMemoryDatabase database;

    @Mock
    private DrivingLicenceRemovePointsService service;




    @Test
    @DisplayName("test removing points valid")
    void should_remove_points(){
        final var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .build();
        database.save(id, drivingLicence);
        final var drivingLicenceReturn = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345").availablePoints(9)
                .build();
        when(service.removeDrivingPoints(3, id)).thenReturn(drivingLicenceReturn);
        final var actuel = service.removeDrivingPoints(3, id);
        assertThat(actuel.getAvailablePoints()).isEqualTo(drivingLicenceReturn.getAvailablePoints());
        verify(service).removeDrivingPoints(3, id);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("test removing points invalid")
    void should_not_remove_more_points(){
        final var id = UUID.randomUUID();
        final var points = 13;
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .build();
        database.save(id, drivingLicence);
        when(service.removeDrivingPoints(points, id)).thenReturn(drivingLicence);
        final var actuel = service.removeDrivingPoints(points, id);
        assertThat(actuel.getAvailablePoints()).isEqualTo(drivingLicence.getAvailablePoints());
        verify(service).removeDrivingPoints(13, id);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("test removing negative points")
    void should_not_remove_any_points(){
        final var id = UUID.randomUUID();
        final var points = -3;
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .build();
        database.save(id, drivingLicence);
        when(service.removeDrivingPoints(points, id)).thenReturn(drivingLicence);
        final var actuel = service.removeDrivingPoints(points, id);
        assertThat(actuel.getAvailablePoints()).isEqualTo(drivingLicence.getAvailablePoints());
        verify(service).removeDrivingPoints(-3, id);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("test no drivingLicence")
    void should_have_driving_licence(){
        final var id = UUID.randomUUID();
        final  var points = 3;
        when(service.removeDrivingPoints(points, id)).thenThrow( new ResourceNotFoundException("Enter a driving licence"));
        assertThrows(ResourceNotFoundException.class ,
                ()->service.removeDrivingPoints(points, id));
        verify(service).removeDrivingPoints(points, id);
        verifyNoMoreInteractions(service);
    }



}
