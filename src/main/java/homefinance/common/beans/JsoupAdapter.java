package homefinance.common.beans;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class JsoupAdapter {

  public Document parse(String html) {
    return Jsoup.parse(html);
  }

}
