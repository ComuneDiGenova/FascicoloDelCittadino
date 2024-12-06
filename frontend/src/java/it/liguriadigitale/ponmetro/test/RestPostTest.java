package it.liguriadigitale.ponmetro.test;

import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class RestPostTest {
  private static Log log = LogFactory.getLog(RestPostTest.class);

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    configuraLog4j();
    final String path = "http://127.0.0.1:8180/ponmetroweb/rest";

    // JSON json = new JSON();
    // ResteasyClient client = new
    // ResteasyClientBuilder().register(ResteasyJackson2Provider.class).register(json)
    // .register(Log.class).build();
    // ResteasyWebTarget target = client.target(UriBuilder.fromPath(path));
    // PrivacyRestInterface proxy =
    // target.proxy(PrivacyRestInterface.class);
    //
    // PrivacyResponse esito =
    // proxy.presaVisionePrivacy(BaseServiceImpl.COD_APP, 21L, 1L);
    // log.debug("esito:" + esito);
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
