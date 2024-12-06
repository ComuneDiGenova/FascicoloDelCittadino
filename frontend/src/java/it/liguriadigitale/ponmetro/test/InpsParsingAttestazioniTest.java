package it.liguriadigitale.ponmetro.test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni.AttestazioniISEE;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class InpsParsingAttestazioniTest {

  private static Log log = LogFactory.getLog(RestInpsMain.class);
  private static String JSON =
      "{\"EsitoAttestazione\":{\"AttestazioneCodiceFiscaleDichiarante\":\"FTTSRN73E43D969U\", \"AttestazioneNumeroProtocolloDSU\":\"INPS-ISEE-2016-05005492G-00\", \"AttestazioneDataPresentazione\":\"2016-09-26\", \"AttestazioneDataValidita\":\"2017-01-15\", \"ISEEOrdinarioISEE\":31886.06, \"NucleoFamiliare\":{\"ComponenteNucleo\":[{\"RapportoConDichiarante\":\"D\", \"Cognome\":\"FITTABILE\", \"Nome\":\"SERENA\", \"CodiceFiscale\":\"FTTSRN73E43D969U\"}, {\"RapportoConDichiarante\":\"F\", \"Cognome\":\"MENICAGLI\", \"Nome\":\"MATTEO\", \"CodiceFiscale\":\"MNCMTT09T18D969U\"}, {\"RapportoConDichiarante\":\"F\", \"Cognome\":\"MENICAGLI\", \"Nome\":\"TOMMASO\", \"CodiceFiscale\":\"MNCTMS12R31D969Z\"}, {\"RapportoConDichiarante\":\"P\", \"Cognome\":\"MENICAGLI\", \"Nome\":\"ILIO\", \"CodiceFiscale\":\"MNCLII70T22D969H\"}]}, \"ISEEMin\":{\"ComponenteMinorenne\":[{\"Cognome\":\"MENICAGLI\", \"Nome\":\"MATTEO\", \"CodiceFiscale\":\"MNCMTT09T18D969U\", \"ISEE\":\"31886.06\", \"ISEEOrd\":null}, {\"Cognome\":\"MENICAGLI\", \"Nome\":\"TOMMASO\", \"CodiceFiscale\":\"MNCTMS12R31D969Z\", \"ISEE\":\"31886.06\", \"ISEEOrd\":null}]}}}";

  public static void main(String args[])
      throws JsonParseException, JsonMappingException, IOException {

    configuraLog4j();
    log.debug("Inizio");
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    AttestazioniISEE c = objectMapper.readValue(JSON, AttestazioniISEE.class);

    log.debug("EsitoAttestazione: " + c.getEsitoAttestazione());
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
