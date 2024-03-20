package Tests;

import com.lab2.models.Owner;
import com.lab2.repositories.interfaces.OwnerRepository;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class OwnerRepositoryTest {

    @Mock
    private final OwnerRepository ownerRepository = mock(OwnerRepository.class);

    @Test
    public void getOwnerByIdTest() {
        Owner owner = Owner.builder()
                .id(UUID.fromString("815a2210-580b-4779-8f78-345026772065"))
                .name("KTO ENTO")
                .birthday(new Date())
                .build();
        when(ownerRepository.getById(owner.getId())).thenReturn(Optional.of(new Owner()));
        Owner resievedOwner = ownerRepository.getById(owner.getId()).orElse(null);

        verify(ownerRepository, times(1)).getById(owner.getId());
    }

    @Test
    public void updateOwnerTest() {
        Owner owner = Owner.builder()
                .id(UUID.fromString("815a2210-580b-4779-8f78-345026772065"))
                .name("KTO ENTO")
                .birthday(new Date())
                .build();

        when(ownerRepository.update(owner)).thenReturn(new Owner());
        Owner resievedOwner = ownerRepository.update(owner);

        verify(ownerRepository, times(1)).update(owner);
    }
}
