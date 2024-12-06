package it.liguriadigitale.ponmetro.api.test;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.configurazione.impl.ConfigurazioneImpl;
import it.liguriadigitale.ponmetro.api.pojo.avatar.CfgTAvatarDef;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class ConfigurazioneAvatarTest {
  private static Log log = LogFactory.getLog(ConfigurazioneAvatarTest.class);

  public static void main(String[] args) throws BusinessException {
    getAvatarTest();
    configuraLog4j();
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

  private static void getAvatarTest() throws BusinessException {
    ImagineCaricata image;
    try {
      ConfigurazioneImpl avatarImpl = new ConfigurazioneImpl();
      CfgTAvatarDef avatarDef = new CfgTAvatarDef();
      LocalDateTime lt = LocalDateTime.now();
      avatarDef.setDataIns(lt);
      avatarDef.setNomeFile("default");
      avatarDef.setIdAvatar(20L);
      avatarDef.setUtenteIns("Scaldaferri");
      image = avatarImpl.getAvatar(BigDecimal.ONE);
      log.debug("image uguale!!!");
      System.out.println("ciao");
      log.debug(image);

    } catch (BusinessException e) {
      log.error(e);
    }
  }
}
