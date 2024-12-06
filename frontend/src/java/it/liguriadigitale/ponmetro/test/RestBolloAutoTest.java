package it.liguriadigitale.ponmetro.test;

import de.agilecoders.wicket.jquery.util.Json;
import it.liguriadigitale.ponmetro.tassaauto.model.DettagliBolloMezzi;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioVeicolo;
import it.liguriadigitale.ponmetro.tassaauto.model.PagamentoBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.VeicoliAttivi;
import java.util.Properties;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class RestBolloAutoTest {

  private static Log log = LogFactory.getLog(RestBolloAutoTest.class);

  public static void main(String[] args) {
    configuraLog4j();
    log.debug("Inizio RestBolloAutoTest");

    // listaVeicoliAttiviService();

    // dettagliVeicoloAttivoService();

    dettagliListaBolloAuto();

    // dettagliPagamentoBolloAuto();

  }

  @SuppressWarnings({"unused", "static-access"})
  private static void listaVeicoliAttiviService() {
    Client client = ClientBuilder.newBuilder().newClient();

    /*
     * WebTarget target = client.target(
     * "http://tausvi.datasiel.net:11210/tributi/tassaAuto/sta/starest/veicoliAttivi/"
     * );
     */

    WebTarget target =
        client.target(
            "http://wfly12h.liguriadigitale.it:8280/tributi/tassaAuto/sta/starest/veicoliAttivi/");

    Invocation.Builder builder =
        target.path("PRPRRT62A06D969V").request(MediaType.APPLICATION_JSON);

    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    instance.registerProvider(ResteasyJackson2Provider.class);
    MultivaluedMap<String, Object> headers = response.getHeaders();
    for (String key : headers.keySet()) {
      log.debug("chiave: " + key + " value: " + headers.get(key));
    }
    log.debug("Response bollo:" + response.getStatus());
    if (response.getStatus() == 200) {
      String outputString = response.readEntity(String.class);
      log.debug("json outputString bollo:" + outputString);
      VeicoliAttivi parse = Json.fromJson(outputString, VeicoliAttivi.class);
      log.debug("parse" + parse);

      // VeicoliAttivi outputVA =
      // response.readEntity(VeicoliAttivi.class);
      // log.debug("json outputVA bollo:" + outputVA);
    }
  }

  @SuppressWarnings({"unused", "static-access"})
  private static void dettagliVeicoloAttivoService() {
    Client client = ClientBuilder.newBuilder().newClient();

    /*
     * WebTarget target = client.target(
     * "http://tausvi.datasiel.net:11210/tributi/tassaAuto/sta/starest/veicoloAttivo/"
     * );
     */

    WebTarget target =
        client.target(
            "http://wfly12h.liguriadigitale.it:8280/tributi/tassaAuto/sta/starest/veicoloAttivo");

    Invocation.Builder builder = target.path("2992066").request(MediaType.APPLICATION_JSON);

    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    instance.registerProvider(ResteasyJackson2Provider.class);
    MultivaluedMap<String, Object> headers = response.getHeaders();
    for (String key : headers.keySet()) {
      log.debug("chiave: " + key + " value: " + headers.get(key));
    }
    log.debug("Response bollo:" + response.getStatus());
    if (response.getStatus() == 200) {
      // String outputString = response.readEntity(String.class);
      // log.debug("json outputString bollo:" + outputString);

      /*
       * DettaglioVeicolo parse = Json.fromJson(outputString,
       * DettaglioVeicolo.class); log.debug("parse" + parse);
       */

      DettaglioVeicolo outputVA = response.readEntity(DettaglioVeicolo.class);
      log.debug("json outputVA bollo:" + outputVA);
    }
  }

  @SuppressWarnings("static-access")
  private static void dettagliListaBolloAuto() {
    Client client = ClientBuilder.newBuilder().newClient();

    /*
     * WebTarget target = client.target(
     * "http://tausvi.datasiel.net:11210/tributi/tassaAuto/sta/starest/calcolaSituazione/"
     * );
     */

    WebTarget target =
        client.target(
            "http://wfly12h.liguriadigitale.it:8280/tributi/tassaAuto/sta/starest/calcolaSituazione/");

    Invocation.Builder builder =
        target.path("2019").path("337140").path("752073").request(MediaType.APPLICATION_JSON);

    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    instance.registerProvider(ResteasyJackson2Provider.class);
    MultivaluedMap<String, Object> headers = response.getHeaders();
    for (String key : headers.keySet()) {
      log.debug("chiave: " + key + " value: " + headers.get(key));
    }
    log.debug("Response bollo:" + response.getStatus());
    if (response.getStatus() == 200) {
      DettagliBolloMezzi outputVA = response.readEntity(DettagliBolloMezzi.class);
      log.debug("json outputVA bollo:" + outputVA);
    }

    if (response.getStatus() == 500) {
      String outputString = response.readEntity(String.class);
      log.debug("json outputString bollo:" + outputString);
      DettaglioVeicolo parse = Json.fromJson(outputString, DettaglioVeicolo.class);
      log.debug("parse" + parse);
    }
  }

  @SuppressWarnings({"unused", "static-access"})
  private static void dettagliPagamentoBolloAuto() {
    Client client = ClientBuilder.newBuilder().newClient();

    /*
     * WebTarget target = client.target(
     * "http://tausvi.datasiel.net:11210/tributi/tassaAuto/sta/starest/pagamento/"
     * );
     */

    WebTarget target =
        client.target(
            "http://wfly12h.liguriadigitale.it:8280/tributi/tassaAuto/sta/starest/pagamento/");

    Invocation.Builder builder =
        target.path("2017").path("2992066").request(MediaType.APPLICATION_JSON);

    Response response = builder.get();
    ResteasyProviderFactory instance = ResteasyProviderFactory.getInstance();
    RegisterBuiltin.register(instance);
    instance.registerProvider(ResteasyJackson2Provider.class);
    MultivaluedMap<String, Object> headers = response.getHeaders();
    for (String key : headers.keySet()) {
      log.debug("chiave: " + key + " value: " + headers.get(key));
    }
    log.debug("Response pagamento bollo:" + response.getStatus());
    if (response.getStatus() == 200) {
      String outputString = response.readEntity(String.class);
      log.debug("json outputString pagamento bollo:" + outputString);
      PagamentoBollo parse = Json.fromJson(outputString, PagamentoBollo.class);
      log.debug("parse bollo pagamento " + parse);

      // VeicoliAttivi outputVA =
      // response.readEntity(VeicoliAttivi.class);
      // log.debug("json outputVA bollo:" + outputVA);
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
