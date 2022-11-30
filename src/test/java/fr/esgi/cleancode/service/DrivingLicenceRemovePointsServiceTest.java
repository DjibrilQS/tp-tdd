package fr.esgi.cleancode.service;


import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class DrivingLicenceRemovePointsServiceTest {

    @Mock
    private InMemoryDatabase database = InMemoryDatabase.getInstance();

    @InjectMocks
    private DrivingLicenceRemovePointsService service = new DrivingLicenceRemovePointsService(database);

    DrivingLicence fakeDrivingLicence;
    @BeforeEach
    void setUpDatabase() {
        DrivingLicenceCreationService creationService = new DrivingLicenceCreationService(database);

        this.fakeDrivingLicence = creationService.save("123456789012345");
    }


    @Test
    @DisplayName("test removing points valid")
    void should_remove_points(){
        DrivingLicence drivingLicence = service.removeDrivingPoints(3, fakeDrivingLicence);
        Assertions.assertEquals(9, drivingLicence.getAvailablePoints() );
    }

    @Test
    @DisplayName("test removing points invalid")
    void should_not_remove_more_points(){
        DrivingLicence drivingLicence = service.removeDrivingPoints(13, fakeDrivingLicence);
        Assertions.assertEquals(0,drivingLicence.getAvailablePoints() );
    }

    @Test
    @DisplayName("test removing negative points")
    void should_not_remove_any_points(){
        DrivingLicence drivingLicence = service.removeDrivingPoints(-3, fakeDrivingLicence);
        Assertions.assertEquals(12,drivingLicence.getAvailablePoints() );
    }



}
