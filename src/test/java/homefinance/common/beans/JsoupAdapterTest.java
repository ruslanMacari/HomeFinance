package homefinance.common.beans;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class JsoupAdapterTest {

  JsoupAdapter adapter = new JsoupAdapter();

  @Test
  public void parse_shouldReturn_JsoupParse() {
    try (MockedStatic<Jsoup> jsoupMock = Mockito.mockStatic(Jsoup.class)) {
      // given:
      String html = "<html>hello</>";
      jsoupMock.when(() -> Jsoup.parse(html)).thenReturn(mock(Document.class));
      // when:
      Document actual = adapter.parse(html);
      // then:
      then(actual).isEqualTo(Jsoup.parse(html));
    }
  }
}
