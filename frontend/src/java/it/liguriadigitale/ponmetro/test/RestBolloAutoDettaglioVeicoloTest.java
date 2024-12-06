package it.liguriadigitale.ponmetro.test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioVeicolo;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class RestBolloAutoDettaglioVeicoloTest {

  private static Log log = LogFactory.getLog(RestBolloAutoDettaglioVeicoloTest.class);

  private static String JSON =
      "{\"categoriaEuro\": \"EURO 4\", \"destinazione\": \"AUTOVETTURA PER TRASPORTO DI PERSONE\", \"uso\": \"PROPRIO (cod. 1)\", \"trasportoMerci\": \"\", \"carrozzeria\": \"\", \"massaComplessiva\": \"1425\", \"portata\": \"370\", \"PotenzaEffettiva\": \"44.00\", \"cavalliFiscali\": \"14\", \"alimentazione\": \"BENZ\", \"cilindrata\": \"1242\", \"sospensionePneumatica\": \"NO\", \"flagEcoDiesel\": \"NO\", \"numeroPosti\": \"5\", \"numeroAssi\": \"2\", \"presenzaGancioTraino\": \"NO\", \"rimorchiabilita\": \"NO\", \"massaRimorchiabile\": \"\", \"emissioneCO2\": \"\"}";

  public static void main(String args[])
      throws JsonParseException, JsonMappingException, IOException {

    configuraLog4j();
    log.debug("Inizio");
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    DettaglioVeicolo dv = objectMapper.readValue(JSON, DettaglioVeicolo.class);

    log.debug("DettaglioVeicolo: " + dv);
  }

  private static void configuraLog4j() {
    final Properties log4jProperties = new Properties();
    log4jProperties.setProperty("log4j.rootLogger", "DEBUG, myConsoleAppender");
    log4jProperties.setProperty(
        "log4j.appender.myConsoleAppender", "org.apache.log4j.ConsoleAppender");
    log4jProperties.setProperty(
        "log4j.appender.myConsoleAppender.layout", "org.apache.log4j.PatternLayout");
    log4jProperties.setProperty(
        "log4j.appender.myConsoleAppender.layout.ConversionPattern", "%-5p %c %x - %m%n");
    PropertyConfigurator.configure(log4jProperties);
    log.debug("LOG4J configurato!!!");
  }
}
