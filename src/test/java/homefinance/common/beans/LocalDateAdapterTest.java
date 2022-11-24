package homefinance.common.beans;

import static org.assertj.core.api.BDDAssertions.then;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocalDateAdapterTest {

  LocalDateAdapter localDateAdapter = new LocalDateAdapter();

  @Test
  public void now_shouldReturn_LocalDateNow() {
    // given:
    LocalDate date = LocalDate.of(2022, 1, 1);
    LocalDate now;
    try (MockedStatic<LocalDate> localDateMock = Mockito.mockStatic(LocalDate.class)) {
      localDateMock.when(LocalDate::now).thenReturn(date);
      // when:
      now = localDateAdapter.now();
    }
    // then:
    then(now).isEqualTo(date);
  }
}
