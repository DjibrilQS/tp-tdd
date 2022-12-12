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
    private DrivingLicenceCreationService service ;


    @Test
    @DisplayName("Verification Security number is 15 digit number")
    void  should_create_UUID_with_15_number(){
        final var socialNumber =  "123456789012345";
        final var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber(socialNumber)
                .build();
        when(service.save(socialNumber)).thenReturn(drivingLicence);
        final var actualDriving = service.save(socialNumber);
        assertThat(actualDriving.getDriverSocialSecurityNumber())
                .isEqualTo(drivingLicence.getDriverSocialSecurityNumber());
        verify(service).save(socialNumber);
        verifyNoMoreInteractions(service);

    }

    @Test
    @DisplayName("Has not good size SecurityNumber")
    void should_create_drivingLicence_with_not_good_size(){
        final var socialNumber = "123456789";
        final var error = new InvalidDriverSocialSecurityNumberException("Social Number is not good");
        when(service.save(socialNumber)).thenThrow(error);
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.save(socialNumber)
        );
        verifyNoMoreInteractions(service);

    }

    @Test
    @DisplayName("Verification SecurityNumber have not letter")
    void  shoud_create_with_letter(){
        final var socialNumber = "12345678901234A";
        final var error = new InvalidDriverSocialSecurityNumberException("Social Number is not good");
        when(service.save(socialNumber)).thenThrow(error);
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.save(socialNumber)
        );
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Verification UUID is not null")
    void shoud_create_not_be_null(){
        final var error = new InvalidDriverSocialSecurityNumberException("Social Number is not good");
        when(service.save(null)).thenThrow(error);
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.save(null)
        );
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Verification number of points 12")
    void should_create_with_12_points(){
        final var socialNumber = "123456789012345";
        final var drivingLicence = DrivingLicence.builder().driverSocialSecurityNumber(socialNumber).build();
        when(service.save(socialNumber)).thenReturn(drivingLicence);
        final var  actuel = service.save(socialNumber);
        Assertions.assertEquals(actuel.getAvailablePoints(), drivingLicence.getAvailablePoints() );
        verifyNoMoreInteractions(service);

    }

    @Test
    @DisplayName("Verification is security of social is valid")
    void should_create_with_ssn(){
        final  var socialNumber = "123456789012345";
        final var drivingLicence = DrivingLicence.builder().driverSocialSecurityNumber(socialNumber).build();
        when(service.save(socialNumber)).thenReturn(drivingLicence);
        final var actuel = service.save(socialNumber);
        Assertions.assertEquals(actuel.getDriverSocialSecurityNumber(), drivingLicence.getDriverSocialSecurityNumber());
        verifyNoMoreInteractions(service);
    }

}
