package Tests;

import com.lab2.models.Color;
import com.lab2.repositories.interfaces.ColorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ColorRepositoryTest {

    @Mock
    private final ColorRepository colorRepository = mock(ColorRepository.class);

    @Test
    public void getColorByIdTest() {
        Long colorId = 1L;
        when(colorRepository.getById(colorId)).thenReturn(Optional.of(new Color()));
        Color color = colorRepository.getById(colorId).orElse(null);
        verify(colorRepository, times(1)).getById(colorId);
    }

    @Test
    public void updateColorTest() {
        Color color = Color.builder().color("GINGER").id(1L).build();
        when(colorRepository.update(color)).thenReturn(new Color());
        Color updatedColor = colorRepository.update(color);
        verify(colorRepository, times(1)).update(color);
    }
}
