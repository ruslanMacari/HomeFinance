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
    // given:
    String html = "<html>hello</>";
    Document actual;
    Document expected;
    try (MockedStatic<Jsoup> jsoupMock = Mockito.mockStatic(Jsoup.class)) {
      jsoupMock.when(() -> Jsoup.parse(html)).thenReturn(mock(Document.class));
      // when:
      actual = adapter.parse(html);
      expected = Jsoup.parse(html);
    }
    // then:
    then(actual).isEqualTo(expected);
  }
}
