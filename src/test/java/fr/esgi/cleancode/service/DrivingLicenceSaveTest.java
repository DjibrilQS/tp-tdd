package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DrivingLicenceCreationServiceTest {
    @Mock
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    @InjectMocks
    private DrivingLicenceCreationService service = new DrivingLicenceCreationService(database);




    @Test
    @DisplayName("Verification UUID is 15 digit number")
    void  should_create_UUID_with_15_number(){
        Assertions.assertDoesNotThrow(
                () -> service.save("123456789012345")
        );
    }

    @Test
    @DisplayName("Has not good size UUID")
    void should_create_drivingLicence_with_not_good_size(){
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.save("12345")
        );
    }

    @Test
    @DisplayName("Verification UUID have not letter")
    void  shoud_create_with_letter(){
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () -> service.save("12345678901234A")
        );
    }

    @Test
    @DisplayName("Verification UUID is not null")
    void shoud_create_not_be_null(){
        Assertions.assertThrows(
                InvalidDriverSocialSecurityNumberException.class,
                () ->service.save(null)
        );
    }

    @Test
    @DisplayName("Verification number of points 12")
    void should_create_with_12_points(){
        DrivingLicence drivingLicence = DrivingLicence.builder().driverSocialSecurityNumber("123456789012345").build();
        Assertions.assertEquals(12, drivingLicence.getAvailablePoints());

    }

    @Test
    @DisplayName("Verification is security of social is valid")
    void should_create_with_ssn(){
        DrivingLicence drivingLicence = DrivingLicence.builder().driverSocialSecurityNumber("123456789012345").build();
        Assertions.assertEquals("123456789012345", drivingLicence.getDriverSocialSecurityNumber());
    }

}
