package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DrivingLicenceCreationServiceTest {

    @Mock
    private InMemoryDatabase database;

    @Mock
    private  DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;

    @InjectMocks
    private DrivingLicenceCreationService service ;




    @Test
    @DisplayName("Verification Security number is 15 digit number")
    void  should_create_UUID_with_15_number(){
        final var id = UUID.randomUUID();
        final var socialSecurityNumber = "123456789012345";
        final var mockDrivingLicence = DrivingLicence
                .builder()
                .id(id)
                .driverSocialSecurityNumber(socialSecurityNumber)
                .build();
        when(drivingLicenceIdGenerationService.generateNewDrivingLicenceId()).thenReturn(id);
        when(database.save(eq(id), any(DrivingLicence.class))).thenReturn(mockDrivingLicence);
        final var actual = service.save(socialSecurityNumber);
        assertThat(actual).isEqualTo(mockDrivingLicence);
        verify(drivingLicenceIdGenerationService).generateNewDrivingLicenceId();
        verify(database).save(eq(id), any(DrivingLicence.class));
        verifyNoMoreInteractions(drivingLicenceIdGenerationService);
        verifyNoMoreInteractions(database);



    }

    @Test
    @DisplayName("Has not good size SecurityNumber")
    void should_create_drivingLicence_with_not_good_size(){
        /*final var id = UUID.randomUUID();
        final var socialSecurityNumber = "1234567890123495";
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.save(socialSecurityNumber)
        );*/


    }

    @Test
    @DisplayName("Verification SecurityNumber have not letter")
    void  shoud_create_with_letter(){
        final var socialSecurityNumber = "12345678901234A";
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.save(socialSecurityNumber)
        );
    }

    @Test
    @DisplayName("Verification UUID is not null")
    void shoud_create_not_be_null(){
        final var error = new InvalidDriverSocialSecurityNumberException("Social Number is not good");
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.save(null)
        );
    }

    @Test
    @DisplayName("Verification number of points 12")
    void should_create_with_12_points(){
        final var id = UUID.randomUUID();
        final var socialSecurityNumber = "123456789012345";
        final var mockDrivingLicence = DrivingLicence
                .builder()
                .id(id)
                .driverSocialSecurityNumber(socialSecurityNumber)
                .build();
        when(drivingLicenceIdGenerationService.generateNewDrivingLicenceId()).thenReturn(id);
        when(database.save(eq(id), any(DrivingLicence.class))).thenReturn(mockDrivingLicence);
        final var actual = service.save(socialSecurityNumber);
        assertThat(actual.getAvailablePoints()).isEqualTo(12);
        verify(drivingLicenceIdGenerationService).generateNewDrivingLicenceId();
        verify(database).save(eq(id), any(DrivingLicence.class));
        verifyNoMoreInteractions(drivingLicenceIdGenerationService);
        verifyNoMoreInteractions(database);

    }

    @Test
    @DisplayName("Verification is security of social is valid")
    void should_create_with_ssn(){
        final var id = UUID.randomUUID();
        final var socialSecurityNumber = "123456789012345";
        final var mockDrivingLicence = DrivingLicence
                .builder()
                .id(id)
                .driverSocialSecurityNumber(socialSecurityNumber)
                .build();
        when(drivingLicenceIdGenerationService.generateNewDrivingLicenceId()).thenReturn(id);
        when(database.save(eq(id), any(DrivingLicence.class))).thenReturn(mockDrivingLicence);
        final var actual = service.save(socialSecurityNumber);
        assertThat(actual).isEqualTo(mockDrivingLicence);
        verify(drivingLicenceIdGenerationService).generateNewDrivingLicenceId();
        verify(database).save(eq(id), any(DrivingLicence.class));
        verifyNoMoreInteractions(drivingLicenceIdGenerationService);
        verifyNoMoreInteractions(database);
    }

}
