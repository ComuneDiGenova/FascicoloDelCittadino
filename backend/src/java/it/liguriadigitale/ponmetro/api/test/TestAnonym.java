package it.liguriadigitale.ponmetro.api.test;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.FamilyResponse;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.Relative;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class TestAnonym {

  private static Log log = LogFactory.getLog(TestAnonym.class);

  public static void main(String[] args) {

    configuraLog4j();
    chiamaFamigliariNonResidenti();
  }

  private static void chiamaFamigliariNonResidenti() {

    try {
      FamilyResponse response =
          ServiceLocator.getInstance().getFamily().getListaFamigliariNonResidenti(412L);
      for (Relative minore : response.getListaParenti()) {
        log.debug("Minore=" + minore);
      }
    } catch (BusinessException e) {
      log.error("Errore:", e);
    }
  }

  /** */
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
