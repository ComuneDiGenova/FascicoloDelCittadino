package it.liguriadigitale.ponmetro.test;

import it.liguriadigitale.ponmetro.portale.presentation.util.JaxbMarshaller;
import it.liguriadigitale.riuso.mip.PaymentRequest;
import java.io.IOException;
import java.util.Properties;
import javax.xml.bind.JAXBException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class MipTest {

  private static Log log = LogFactory.getLog(RestInpsMain.class);

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static void main(String args[]) throws IOException {

    configuraLog4j();
    log.debug("Inizio");
    PaymentRequest request = new PaymentRequest();
    request.setEmailPortale("io@me.com");
    JaxbMarshaller marshallerRequest;
    try {
      marshallerRequest = new JaxbMarshaller(request.getClass());

      String marshallRequest = marshallerRequest.marshallRequest(request);
      marshallRequest = removeHeader(marshallRequest);
      log.debug("request ricevuta: " + marshallRequest);
    } catch (JAXBException e) {
      log.error("Errore: ", e);
    }
  }

  @SuppressWarnings("unused")
  private static String removeHeader(String marshalledPayementRequest) {
    String headerXml = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>";
    int endIndex;
    marshalledPayementRequest =
        marshalledPayementRequest.substring(headerXml.length(), marshalledPayementRequest.length());
    log.debug("marshalledPayementRequest=" + marshalledPayementRequest);
    return marshalledPayementRequest;
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
