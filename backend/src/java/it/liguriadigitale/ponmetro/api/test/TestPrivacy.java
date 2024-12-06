package it.liguriadigitale.ponmetro.api.test;

import static org.junit.Assert.assertEquals;

import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.api.business.privacy.impl.PrivacyImpl;
import it.liguriadigitale.ponmetro.api.pojo.common.EsitoResponse;
import it.liguriadigitale.ponmetro.api.pojo.common.builder.EsitoResponseBuilder;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.PrivacyVersioneRequest;
import it.liguriadigitale.ponmetro.api.pojo.privacy.dto.request.builder.PrivacyVersioneRequestBuilder;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

public class TestPrivacy {

  private static Log log = LogFactory.getLog(TestPrivacy.class);

  @Test
  public void presaVisionePrivacy() {
    configuraLog4j();
    PrivacyImpl service = new PrivacyImpl();
    PrivacyVersioneRequest request =
        new PrivacyVersioneRequestBuilder()
            .setCodApplicazione(BaseServiceImpl.COD_APP)
            .setIdAnonimo(1L)
            .setIdVersionePrivacy(1L)
            .build();
    EsitoResponse esito = service.presaVisionePrivacy(request);

    log.debug("Esito:" + esito);
    EsitoResponse esitoTest = new EsitoResponseBuilder().setEsito(true).build();
    assertEquals(esitoTest, esito);
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
