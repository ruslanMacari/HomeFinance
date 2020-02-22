package homefinance.money.currency;

import homefinance.money.currency.impl.CurrencyRatesServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Component
public class ExchangeRatesApi {

  public static final String TAG_NAME_NUM_CODE = "NumCode";
  public static final String TAG_NAME_CHAR_CODE = "CharCode";
  public static final String TAG_NAME_NAME = "Name";
  public static final String TAG_NAME_VALUE = "Value";
  public static final short ELEMENT_NODE = Node.ELEMENT_NODE;
  public static final String TAG_NAME_VALUTE = "Valute";
  public static final String URL = "http://www.bnm.md/ro/official_exchange_rates?get_xml=1&date=";
  public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  public static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory
      .newInstance();
  private static final Logger logger = LoggerFactory.getLogger(CurrencyRatesServiceImpl.class);

  public List<CurrencyRates> getCurrencyRatesByDate(LocalDate date) {
    this.checkDate(date);
    HttpURLConnection connection = this.getOkConnection(date);
    if (Objects.isNull(connection)) {
      return new ArrayList<>();
    }
    Document document = this.getDocument(connection);
    if (Objects.isNull(document)) {
      return new ArrayList<>();
    }
    return this.getCurrencyRates(document);
  }

  private void checkDate(LocalDate date) {
    if (Objects.isNull(date)) {
      logger.error("date is null");
      throw new IllegalArgumentException("Date must be filled");
    }
  }

  private HttpURLConnection getOkConnection(LocalDate date) {
    HttpURLConnection connection;
    try {
      connection = (HttpURLConnection) new URL(this.getUrl(date)).openConnection();
      connection.setRequestMethod("GET");
    } catch (IOException e) {
      logger.error(e.getMessage());
      return null;
    }
    if (this.responseCodeIsOk(connection)) {
      return connection;
    }
    return null;
  }

  private String getUrl(LocalDate date) {
    return URL + date.format(FORMATTER);
  }

  private boolean responseCodeIsOk(HttpURLConnection connection) {
    try {
      return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    } catch (IOException e) {
      logger.warn(e.getMessage());
      return false;
    }
  }

  private Document getDocument(HttpURLConnection connection) {
    try {
      return DOCUMENT_BUILDER_FACTORY.newDocumentBuilder()
          .parse(new InputSource(new StringReader(this.getRatesXML(connection))));
    } catch (Exception e) {
      logger.error(e.getMessage());
      return null;
    }
  }

  private String getRatesXML(HttpURLConnection connection) throws IOException {
    String xml = "";
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream()))) {
      String line;
      while ((line = in.readLine()) != null) {
        xml = xml.concat(line);
      }
    }
    return xml;
  }

  private List<CurrencyRates> getCurrencyRates(Document document) {
    document.getDocumentElement().normalize();
    NodeList nodeList = document.getElementsByTagName(TAG_NAME_VALUTE);
    List<CurrencyRates> list = new ArrayList<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == ELEMENT_NODE) {
        list.add(this.getCurrencyRates((Element) node));
      }
    }
    return list;
  }

  private CurrencyRates getCurrencyRates(Element element) {
    CurrencyRates rates = new CurrencyRates();
    rates.setNumCode(this.getText(element, TAG_NAME_NUM_CODE));
    rates.setCharCode(this.getText(element, TAG_NAME_CHAR_CODE));
    rates.setCurrency(this.getText(element, TAG_NAME_NAME));
    rates.setRate(new BigDecimal(this.getText(element, TAG_NAME_VALUE)));
    return rates;
  }

  private String getText(Element e, String tagName) {
    return e.getElementsByTagName(tagName)
        .item(0)
        .getTextContent();
  }

  // TODO: 22.02.2020 RMACARI: add getRateByCurrency

}
