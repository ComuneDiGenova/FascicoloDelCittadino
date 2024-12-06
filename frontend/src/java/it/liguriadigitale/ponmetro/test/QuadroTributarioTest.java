package it.liguriadigitale.ponmetro.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.openjson.JSONObject;

public class QuadroTributarioTest {

  public static void main(String[] args) {
    System.out.println("QuadroTributarioTest");
    String json = getJson();

    JSONObject quadrotributario = new JSONObject(json.toString());
    System.out.println("Quadro tributario = \n " + quadrotributario);

    System.out.println("Fine");
  }

  public static String getJson() {

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    String json =
        "{\r\n"
            + "    \"tasse\": [\r\n"
            + "        {\r\n"
            + "            \"descrizione\": \"TASI;TARI\",\r\n"
            + "            \"anni_precedenti\": [\r\n"
            + "                {\r\n"
            + "                    \"anno\": 1567545462,\r\n"
            + "                    \"verificato\": 3\r\n"
            + "                }\r\n"
            + "            ]\r\n"
            + "        },\r\n"
            + "        {\r\n"
            + "            \"descrizione\": \"IMU\",\r\n"
            + "            \"anni_precedenti\": [\r\n"
            + "                {\r\n"
            + "                    \"anno\": -647223026,\r\n"
            + "                    \"verificato\": 3\r\n"
            + "                },\r\n"
            + "                {\r\n"
            + "                    \"anno\": 231375012,\r\n"
            + "                    \"verificato\": 2\r\n"
            + "                }\r\n"
            + "            ]\r\n"
            + "        }\r\n"
            + "    ]\r\n"
            + "}";
    System.out.println("Json " + json);
    return json;
  }
}
