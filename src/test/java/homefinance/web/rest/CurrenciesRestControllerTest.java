package homefinance.web.rest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import homefinance.money.currency.CurrencyRates;
import homefinance.money.currency.CurrenciesRestController;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CurrenciesRestControllerTest {

    private CurrenciesRestController instance;
    private String _01012019 = "01.01.2019";

    public CurrenciesRestControllerTest() {
        instance = new CurrenciesRestController();
    }

    @Test
    public void testGetAllRates() throws Exception {
        String date = "2018.11.11";
        try {
            instance.getAllRates(date);
            fail("must throw NumberFormatException");
        } catch (NumberFormatException e) {
        }
        date = _01012019;
        ResponseEntity<List<CurrencyRates>> allRates = instance.getAllRates(date);
        assertEquals(allRates.getStatusCode(), HttpStatus.OK);
        List<CurrencyRates> body = allRates.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JavaType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, CurrencyRates.class);
        List<CurrencyRates> list = mapper.readValue(getJsonString(), collectionType);
        assertEquals(body.size(), list.size());
        CurrencyRates item = list.get(0);
        assertEquals(item.getNumCode(), "978");
        assertEquals(item.getCharCode(), "EUR");
        assertEquals(item.getRate(), new BigDecimal("19.5212"));
        
    }

    private String getJsonString() {
        String s = "[\n"
                + "    {\n"
                + "        \"numCode\": \"978\",\n"
                + "        \"charCode\": \"EUR\",\n"
                + "        \"currency\": \"Euro\",\n"
                + "        \"rate\": 19.5212\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"840\",\n"
                + "        \"charCode\": \"USD\",\n"
                + "        \"currency\": \"Dolar S.U.A.\",\n"
                + "        \"rate\": 17.1427\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"643\",\n"
                + "        \"charCode\": \"RUB\",\n"
                + "        \"currency\": \"Rubla ruseasca\",\n"
                + "        \"rate\": 0.2469\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"946\",\n"
                + "        \"charCode\": \"RON\",\n"
                + "        \"currency\": \"Leu romanesc\",\n"
                + "        \"rate\": 4.2001\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"980\",\n"
                + "        \"charCode\": \"UAH\",\n"
                + "        \"currency\": \"Hrivna ucraineana\",\n"
                + "        \"rate\": 0.6239\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"784\",\n"
                + "        \"charCode\": \"AED\",\n"
                + "        \"currency\": \"Dirham E.A.U.\",\n"
                + "        \"rate\": 4.6675\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"008\",\n"
                + "        \"charCode\": \"ALL\",\n"
                + "        \"currency\": \"Lek albanez\",\n"
                + "        \"rate\": 1.587\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"051\",\n"
                + "        \"charCode\": \"AMD\",\n"
                + "        \"currency\": \"Dram armenesc\",\n"
                + "        \"rate\": 0.354\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"036\",\n"
                + "        \"charCode\": \"AUD\",\n"
                + "        \"currency\": \"Dolar australian\",\n"
                + "        \"rate\": 12.0805\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"944\",\n"
                + "        \"charCode\": \"AZN\",\n"
                + "        \"currency\": \"Manat azer\",\n"
                + "        \"rate\": 10.1137\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"975\",\n"
                + "        \"charCode\": \"BGN\",\n"
                + "        \"currency\": \"Leva bulgara\",\n"
                + "        \"rate\": 9.9815\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"933\",\n"
                + "        \"charCode\": \"BYN\",\n"
                + "        \"currency\": \"Rubla belarusa\",\n"
                + "        \"rate\": 7.9578\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"124\",\n"
                + "        \"charCode\": \"CAD\",\n"
                + "        \"currency\": \"Dolar canadian\",\n"
                + "        \"rate\": 12.6008\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"756\",\n"
                + "        \"charCode\": \"CHF\",\n"
                + "        \"currency\": \"Franc elvetian\",\n"
                + "        \"rate\": 17.2853\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"156\",\n"
                + "        \"charCode\": \"CNY\",\n"
                + "        \"currency\": \"Yuan Renminbi chinezesc\",\n"
                + "        \"rate\": 2.4969\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"203\",\n"
                + "        \"charCode\": \"CZK\",\n"
                + "        \"currency\": \"Coroana ceha\",\n"
                + "        \"rate\": 0.7544\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"208\",\n"
                + "        \"charCode\": \"DKK\",\n"
                + "        \"currency\": \"Coroana daneza\",\n"
                + "        \"rate\": 2.6151\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"826\",\n"
                + "        \"charCode\": \"GBP\",\n"
                + "        \"currency\": \"Lira sterlina\",\n"
                + "        \"rate\": 21.6511\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"981\",\n"
                + "        \"charCode\": \"GEL\",\n"
                + "        \"currency\": \"Lari georgian\",\n"
                + "        \"rate\": 6.4235\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"344\",\n"
                + "        \"charCode\": \"HKD\",\n"
                + "        \"currency\": \"Dolar Hong Kong\",\n"
                + "        \"rate\": 2.1891\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"191\",\n"
                + "        \"charCode\": \"HRK\",\n"
                + "        \"currency\": \"Kuna croata\",\n"
                + "        \"rate\": 2.6334\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"348\",\n"
                + "        \"charCode\": \"HUF\",\n"
                + "        \"currency\": \"Forint ungar\",\n"
                + "        \"rate\": 6.074\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"376\",\n"
                + "        \"charCode\": \"ILS\",\n"
                + "        \"currency\": \"Shekel israelian\",\n"
                + "        \"rate\": 4.5377\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"356\",\n"
                + "        \"charCode\": \"INR\",\n"
                + "        \"currency\": \"Rupia indiana\",\n"
                + "        \"rate\": 2.4366\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"352\",\n"
                + "        \"charCode\": \"ISK\",\n"
                + "        \"currency\": \"Coroana islandeza\",\n"
                + "        \"rate\": 1.4676\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"392\",\n"
                + "        \"charCode\": \"JPY\",\n"
                + "        \"currency\": \"Yen japonez\",\n"
                + "        \"rate\": 15.4599\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"417\",\n"
                + "        \"charCode\": \"KGS\",\n"
                + "        \"currency\": \"Som kirghiz\",\n"
                + "        \"rate\": 2.4542\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"410\",\n"
                + "        \"charCode\": \"KRW\",\n"
                + "        \"currency\": \"Won sud-coreean\",\n"
                + "        \"rate\": 1.5301\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"414\",\n"
                + "        \"charCode\": \"KWD\",\n"
                + "        \"currency\": \"Dinar kuweitian\",\n"
                + "        \"rate\": 56.4202\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"398\",\n"
                + "        \"charCode\": \"KZT\",\n"
                + "        \"currency\": \"Tenghe kazah\",\n"
                + "        \"rate\": 0.4543\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"807\",\n"
                + "        \"charCode\": \"MKD\",\n"
                + "        \"currency\": \"Denar macedonian\",\n"
                + "        \"rate\": 3.1722\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"458\",\n"
                + "        \"charCode\": \"MYR\",\n"
                + "        \"currency\": \"Ringgit malayezian\",\n"
                + "        \"rate\": 4.1161\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"578\",\n"
                + "        \"charCode\": \"NOK\",\n"
                + "        \"currency\": \"Coroana norvegiana\",\n"
                + "        \"rate\": 1.9575\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"554\",\n"
                + "        \"charCode\": \"NZD\",\n"
                + "        \"currency\": \"Dolar neozeelandez\",\n"
                + "        \"rate\": 11.5045\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"985\",\n"
                + "        \"charCode\": \"PLN\",\n"
                + "        \"currency\": \"Zlot polonez\",\n"
                + "        \"rate\": 4.5528\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"941\",\n"
                + "        \"charCode\": \"RSD\",\n"
                + "        \"currency\": \"Dinar sirb\",\n"
                + "        \"rate\": 16.5136\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"752\",\n"
                + "        \"charCode\": \"SEK\",\n"
                + "        \"currency\": \"Coroana suedeza\",\n"
                + "        \"rate\": 1.8976\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"972\",\n"
                + "        \"charCode\": \"TJS\",\n"
                + "        \"currency\": \"Somoni tadjic\",\n"
                + "        \"rate\": 1.8196\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"934\",\n"
                + "        \"charCode\": \"TMT\",\n"
                + "        \"currency\": \"Manat turkmen\",\n"
                + "        \"rate\": 4.8979\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"949\",\n"
                + "        \"charCode\": \"TRY\",\n"
                + "        \"currency\": \"Lira turceasca\",\n"
                + "        \"rate\": 3.2417\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"860\",\n"
                + "        \"charCode\": \"UZS\",\n"
                + "        \"currency\": \"Sum uzbek\",\n"
                + "        \"rate\": 0.2044\n"
                + "    },\n"
                + "    {\n"
                + "        \"numCode\": \"960\",\n"
                + "        \"charCode\": \"XDR\",\n"
                + "        \"currency\": \"D.S.T.\",\n"
                + "        \"rate\": 23.771\n"
                + "    }\n"
                + "]";
        return s;
    }

    @Test
    public void testGetRates() throws Exception {
        List<CurrencyRates> ratesList = new ArrayList<>();
        ResponseEntity<List<CurrencyRates>> response = instance.getRates(_01012019, ratesList);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        CurrencyRates rates = new CurrencyRates();
        ratesList.add(rates);
        rates.setCharCode("EUR");
        rates.setNumCode("978");
        response = instance.getRates(_01012019, ratesList);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        List<CurrencyRates> responseList = response.getBody();
        assertEquals(responseList.size(), 1);
        assertEquals(responseList.get(0).getRate(), new BigDecimal("19.5212"));
    }
    
}
