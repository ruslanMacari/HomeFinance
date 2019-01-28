package ruslan.macari.web.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

@RestController
@RequestMapping("/currencies/rates")
public class CurrenciesRestController {
    
    private final String url = "http://www.bnm.md/ro/official_exchange_rates?get_xml=1&date=13.01.2018";
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CurrenciesRates>> getRates() {
        List<CurrenciesRates> list = new ArrayList<>();
        CurrenciesRates rates = new CurrenciesRates("Eur", new BigDecimal("18.50"));
        list.add(rates);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    private void sendGET() throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuffer response;
            String xmlString;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuffer();
                xmlString = "";
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    xmlString = xmlString.concat(inputLine);
                }
            }
            
            //String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><a><b></b><c></c></a>";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(xmlString)));
                System.out.println(document.toString());
                
                Element root = document.getDocumentElement();
                NamedNodeMap attributes = root.getAttributes();
                for(int i = 0; i < attributes.getLength(); i++) {
                    Node node = attributes.item(i);
                    
                    System.out.println(node);
                    System.out.println(node.getNodeName());
                }
                
//                TransformerFactory tf = TransformerFactory.newInstance();
//                Transformer transformer = tf.newTransformer();
//                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//                StringWriter writer = new StringWriter();
//                transformer.transform(new DOMSource(document), new StreamResult(writer));
//                String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
//                System.out.println(output);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

    
}

