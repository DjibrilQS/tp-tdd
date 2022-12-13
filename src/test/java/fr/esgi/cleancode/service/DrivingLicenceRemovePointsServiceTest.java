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

    @Mock
    private InMemoryDatabase database;

    @InjectMocks
    private DrivingLicenceRemovePointsService service;




    @Test
    @DisplayName("test removing points valid")
    void should_remove_points(){
        final  var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .build();
        when(database.findById(id)).thenReturn(Optional.of(drivingLicence));
        final  var actuel = service.removeDrivingPoints(3, id);
        assertThat(actuel.getAvailablePoints()).isEqualTo(9);
        verify(database).findById(id);
        verifyNoMoreInteractions(database);
    }

    @Test
    @DisplayName("test removing points invalid")
    void should_not_remove_more_points(){
        final  var id = UUID.randomUUID();
        final var points = 13;
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .build();
        when(database.findById(id)).thenReturn(Optional.of(drivingLicence));
        final  var actuel = service.removeDrivingPoints(points, id);
        assertThat(actuel.getAvailablePoints()).isEqualTo(0);
        verify(database).findById(id);
        verifyNoMoreInteractions(database);
    }

    @Test
    @DisplayName("test removing negative points")
    void should_not_remove_any_points(){
        final  var id = UUID.randomUUID();
        final var points = -3;
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .build();
        when(database.findById(id)).thenReturn(Optional.of(drivingLicence));
        final  var actuel = service.removeDrivingPoints(points, id);
        assertThat(actuel.getAvailablePoints()).isEqualTo(12);
        verify(database).findById(id);
        verifyNoMoreInteractions(database);
    }

    @Test
    @DisplayName("test no drivingLicence")
    void should_have_driving_licence(){
        final  var id = UUID.randomUUID();
        final var points = 10;
        when(database.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class ,
                ()->service.removeDrivingPoints(points, id));
        verify(database).findById(id);
        verifyNoMoreInteractions(database);
    }



}
