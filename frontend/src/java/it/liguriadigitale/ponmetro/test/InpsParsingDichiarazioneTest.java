package it.liguriadigitale.ponmetro.test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni.DichiarazioniISEE;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class InpsParsingDichiarazioneTest {

  private static Log log = LogFactory.getLog(RestInpsMain.class);
  private static String JSON =
      "{\"NucleoFamiliare\":{\"ComponenteNucleo\":[{\"RapportoConDichiarante\":\"D\", \"FlagAssenzaReddito\":\"false\", \"FoglioComponenteNucleo\":{\"CodiceFiscale\":\"FTTSRN73E43D969U\", \"DatiComponente\":{\"FlagConvivenzaAnagrafica\":\"false\", \"AttivitaSoggetto\":\"LAVIND\", \"Anagrafica\":{\"Cognome\":\"FITTABILE\", \"Nome\":\"SERENA\", \"DataNascita\":\"1973-05-03\", \"Cittadinanza\":\"Italiana\", \"Sesso\":\"F\", \"ProvinciaNascita\":\"GE\", \"CodiceComuneNascita\":\"D969\"}, \"Residenza\":{\"Indirizzo\":\"VIA MARCO POLO\", \"Civico\":\"12/12\", \"CodiceComune\":\"D969\", \"Provincia\":\"GE\", \"Cap\":\"16136\", \"Telefono\":\"0102723472\"}}, \"PatrimonioMobiliare\":{\"FlagPossessoRapportoFinanziario\":\"true\", \"Rapporto\":[{\"TipoRapporto\":\"01\", \"Identificativo\":\"IT50R0501801400000000125254\", \"CodiceFiscaleOperatore\":\"01029710280\", \"Saldo3112\":\"1549\", \"ConsistenzaMedia\":\"2288\"}, {\"TipoRapporto\":\"01\", \"Identificativo\":\"IT43B0306901410615294327440\", \"CodiceFiscaleOperatore\":\"00799960158\", \"Saldo3112\":\"501\", \"ConsistenzaMedia\":\"225\"}, {\"TipoRapporto\":\"99\", \"Identificativo\":\"ASSVITA\", \"CodiceFiscaleOperatore\":\"10908160012\", \"Valore\":\"16326\"}]}, \"PatrimonioImmobiliare\":{\"Patrimonio\":{\"TipoPatrimonio\":\"F\", \"CodiceComuneStato\":\"D969\", \"QuotaPosseduta\":\"100\", \"ValoreImu\":\"26040\", \"FlagAbitazione\":\"false\"}}, \"Veicoli\":{\"Veicolo\":{\"TipoVeicolo\":\"A\", \"Targa\":\"EG654NJ\"}}, \"AltriRedditi\":{\"FlagEsonerato\":\"false\", \"FlagIntegrazione\":\"false\", \"FlagRettifica\":\"false\", \"RedditoIRPEF\":\"26184\", \"LavoroDipendente\":\"22674\"}}}, {\"RapportoConDichiarante\":\"P\", \"FlagAssenzaReddito\":\"false\", \"FoglioComponenteNucleo\":{\"CodiceFiscale\":\"MNCLII70T22D969H\", \"DatiComponente\":{\"FlagConvivenzaAnagrafica\":\"false\", \"AttivitaSoggetto\":\"LAVIND\", \"Anagrafica\":{\"Cognome\":\"MENICAGLI\", \"Nome\":\"ILIO\", \"DataNascita\":\"1970-12-22\", \"Cittadinanza\":\"Italiana\", \"Sesso\":\"M\", \"ProvinciaNascita\":\"GE\", \"CodiceComuneNascita\":\"D969\"}, \"Residenza\":{\"Indirizzo\":\"VIA MARCO POLO\", \"Civico\":\"12/12\", \"CodiceComune\":\"D969\", \"Provincia\":\"GE\", \"Cap\":\"16136\", \"Telefono\":\"0102723472\"}}, \"PatrimonioMobiliare\":{\"FlagPossessoRapportoFinanziario\":\"true\", \"Rapporto\":[{\"TipoRapporto\":\"01\", \"Identificativo\":\"IT13H0760101400000043933126\", \"CodiceFiscaleOperatore\":\"97103880585\", \"Saldo3112\":\"44064\", \"ConsistenzaMedia\":\"38948\"}, {\"TipoRapporto\":\"01\", \"Identificativo\":\"IT43B0306901410615294327440\", \"CodiceFiscaleOperatore\":\"00799960158\", \"Saldo3112\":\"501\", \"ConsistenzaMedia\":\"225\"}]}, \"PatrimonioImmobiliare\":{\"Patrimonio\":{\"TipoPatrimonio\":\"F\", \"CodiceComuneStato\":\"D969\", \"QuotaPosseduta\":\"100\", \"ValoreImu\":\"93056\", \"FlagAbitazione\":\"false\"}}, \"RedditiDaDichiarare\":{\"RedditoImpSost\":\"1334\", \"RedditiEsentiImposta\":\"0\", \"RedditiIRAP\":\"0\", \"RedditiFondiari\":\"274\", \"RedditiTassatiEstero\":\"0\", \"RedditiAIRE\":\"0\", \"RedditiFondiariEstero\":\"0\", \"TrattamentiAssistenziali\":\"0\"}, \"Veicoli\":{\"Veicolo\":{\"TipoVeicolo\":\"A\", \"Targa\":\"EG654NJ\"}}, \"AltriRedditi\":{\"FlagEsonerato\":\"false\", \"FlagIntegrazione\":\"false\", \"FlagRettifica\":\"false\", \"RedditoIRPEF\":\"24729\", \"LavoroDipendente\":\"24729\", \"RedditiImpSost\":\"3600\"}}}]}}";

  public static void main(String args[])
      throws JsonParseException, JsonMappingException, IOException {

    configuraLog4j();
    log.debug("Inizio");
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.registerModule(new JavaTimeModule());
    DichiarazioniISEE c = objectMapper.readValue(JSON, DichiarazioniISEE.class);

    log.debug("DichiarazioniISEE: " + c.getNucleoFamiliare());
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
