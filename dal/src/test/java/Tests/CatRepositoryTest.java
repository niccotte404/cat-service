package Tests;

import com.lab2.models.Cat;
import com.lab2.models.Color;
import com.lab2.models.Owner;
import com.lab2.repositories.interfaces.CatRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CatRepositoryTest {

    @Mock
    private final CatRepository catRepository = mock(CatRepository.class);

    @Test
    public void getOwnerByCatTest() {
        Color color = Color.builder().id(1L).color("BLACK").build();
        Owner owner = Owner.builder()
                .id(UUID.fromString("815a2210-580b-4779-8f78-345026772065"))
                .name("KTO ENTO")
                .birthday(new Date())
                .build();
        Cat cat = Cat.builder()
                .id(UUID.fromString("d111084b-0677-4926-a749-01460141879a"))
                .name("BEGEMOOOOOOOOOOOOOOOT")
                .breed("DEVIL AAAAAAAAAAAAAAAAAAAAAAAAAAA")
                .color(color)
                .birthday(new Date())
                .owner(owner)
                .build();

        when(catRepository.getOwnerByCat(cat)).thenReturn(new Owner());
        Owner resievedOwner = catRepository.getOwnerByCat(cat);

        verify(catRepository, times(1)).getOwnerByCat(cat);
    }

    @Test
    public void getCatByIdTest() {
        Color colorBlack = Color.builder().id(1L).color("BLACK").build();
        Color colorGinger = Color.builder().id(2L).color("GINGER").build();
        Owner owner = Owner.builder()
                .id(UUID.fromString("815a2210-580b-4779-8f78-345026772065"))
                .name("KTO ENTO")
                .birthday(new Date())
                .build();
        Cat cat = Cat.builder()
                .id(UUID.fromString("d111084b-0677-4926-a749-01460141879a"))
                .name("BEGEMOOOOOOOOOOOOOOOT")
                .breed("DEVIL AAAAAAAAAAAAAAAAAAAAAAAAAAA")
                .color(colorBlack)
                .birthday(new Date())
                .owner(owner)
                .build();

        when(catRepository.getById(cat.getId())).thenReturn(Optional.of(new Cat()));
        Cat resievedCat = catRepository.getById(cat.getId()).orElse(null);

        verify(catRepository, times(1)).getById(cat.getId());
    }
}
