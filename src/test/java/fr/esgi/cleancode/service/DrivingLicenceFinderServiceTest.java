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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DrivingLicenceFinderServiceTest {
    @Mock
    private InMemoryDatabase database;

    @InjectMocks
    private DrivingLicenceFinderService service;


    @Test
    @DisplayName("Should find a driving licence by id")
    void should_find() {
        final var id = UUID.randomUUID();
        final var drivingLicence = DrivingLicence.builder().id(id)
                .driverSocialSecurityNumber("123456789012345")
                .build();
        when(database.findById(id)).thenReturn(Optional.of(drivingLicence));
        final var actuel = service.findById(id);
        assertThat(actuel).containsSame(drivingLicence);
        verify(database).findById(id);
        verifyNoMoreInteractions(database);
    }

    @Test
    @DisplayName("Should not find a driving licence by id")
    void should_not_find() {
        final var  id = UUID.randomUUID();
       when(database.findById(id)).thenReturn(Optional.empty());
        final var actuel = service.findById(id);
        assertThat(actuel).isEmpty();

        verify(database).findById(id);
        verifyNoMoreInteractions(database);
    }
}
