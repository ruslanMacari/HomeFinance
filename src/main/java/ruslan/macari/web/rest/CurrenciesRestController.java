package ruslan.macari.web.rest;

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
import java.util.function.Predicate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@RestController
@RequestMapping("/rest/currencies/rates")
public class CurrenciesRestController {

    @GetMapping()
    public ResponseEntity<List<CurrenciesRates>> getAllRates(@RequestParam(value = "date", defaultValue = "") String date) throws Exception {
        return new ResponseEntity<>(getFilteredRates(date, (item) -> true), HttpStatus.OK );
    }

    private List<CurrenciesRates> getFilteredRates(String date, Predicate<CurrenciesRates> predicate) throws Exception {
        List<CurrenciesRates> list = new ArrayList<>();
        HttpURLConnection con = (HttpURLConnection) new URL(getUrl(date)).openConnection();
        con.setRequestMethod("GET");
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(getRatesXML(con))));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Valute");
            String numCode = "NumCode";
            String charCode = "CharCode";
            String name = "Name";
            String value = "Value";
            short elementNode = Node.ELEMENT_NODE;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == elementNode) {
                    Element element = (Element) node;
                    CurrenciesRates rates = new CurrenciesRates();
                    rates.setNumCode(getText(element, numCode));
                    rates.setCharCode(getText(element, charCode));
                    rates.setCurrency(getText(element, name));
                    rates.setRate(new BigDecimal(getText(element, value)));
                    if(predicate.test(rates)) {
                        list.add(rates);
                    }
                }
            }
        } else {
            throw new Exception("GET request not worked");
        }
        return list;
    }
    
    private String getUrl(String date) {
        if (date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            date = LocalDate.now().format(formatter);
        }
        return "http://www.bnm.md/ro/official_exchange_rates?get_xml=1&date=" + date;
    }

    private String getRatesXML(HttpURLConnection connection) throws IOException {
        String xmlString = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                xmlString = xmlString.concat(inputLine);
            }
        }
        return xmlString;
    }

    private String getText(Element e, String tagName) {
        return e.getElementsByTagName(tagName).item(0).getTextContent();
    }

    @PostMapping
    public ResponseEntity<List<CurrenciesRates>> getRates(@RequestParam(value = "date", defaultValue = "") String date, 
                                                          @RequestBody List<CurrenciesRates> ratesList) throws Exception {
        return new ResponseEntity<>(getFilteredRates(date, (rates) -> ratesList.contains(rates)), HttpStatus.OK );
    }
    
}
