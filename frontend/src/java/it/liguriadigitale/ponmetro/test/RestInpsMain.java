package it.liguriadigitale.ponmetro.test;

import it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni.EsitoAttestazione;
import java.util.Properties;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class RestInpsMain {

  private static Log log = LogFactory.getLog(RestInpsMain.class);

  @SuppressWarnings("static-access")
  public static void main(String[] args) {
    configuraLog4j();
    log.debug("Inizio");
    Client client = ClientBuilder.newBuilder().newClient();
    WebTarget target =
        client.target(
            "https://apitest.comune.genova.it:28243/inps_isee/servizi-inps/attestazione-isee/");

    Invocation.Builder builder =
        target.path("MNCLII70T22D969H").path("2017-01-01").request(MediaType.APPLICATION_JSON);
    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    instance.registerProvider(ResteasyJackson2Provider.class);
    log.debug("Response:" + response.getStatus());
    if (response.getStatus() == 200) {
      EsitoAttestazione output = response.readEntity(EsitoAttestazione.class);
      log.debug("json:" + output);
    }
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
