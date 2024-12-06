package it.liguriadigitale.ponmetro.portale.presentation.util.annotation;

import it.liguriadigitale.ponmetro.portale.presentation.common.FdCPackagePropertiesAnnotation;
import it.liguriadigitale.ponmetro.portale.presentation.common.FdCWidgetPropertiesAnnotation;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.joda.time.LocalDateTime;
import org.reflections.Reflections;

public class ScanAnnotationMain {

  private static Log log = LogFactory.getLog(ScanAnnotationMain.class);

  private static final String NOME_FILE_PROPERTIES = "fdc_package_finder.properties";

  public static void main(String[] args) {

    configuraLog4j();

    log.debug("INIZIO : " + LocalDateTime.now());
    String cartella = "D:/butta/log/";
    if (args.length > 0) {
      cartella = args[0];
    }
    String path = cartella + NOME_FILE_PROPERTIES;
    StringBuilder builder = scanAnnotation(FdCPackagePropertiesAnnotation.class);
    StringBuilder builderWidget = scanAnnotation(FdCWidgetPropertiesAnnotation.class);
    Properties props = createProperties(builder, builderWidget);
    savePropertiesFile(path, props);
    log.debug("path dove trovare il file: " + path);
    log.debug("FINE : " + LocalDateTime.now());
  }

  private static Properties createProperties(StringBuilder builder, StringBuilder builderWidget) {
    Properties props = new Properties();
    props.put("searchPackagesFunctionProperty", builder.toString());
    props.put("searchPackagesProperty", builder.toString());
    props.put("searchPackagesWidgetProperty", builderWidget.toString());
    return props;
  }

  private static void savePropertiesFile(String path, Properties props) {
    try (FileOutputStream outputStrem = new FileOutputStream(path)) {
      props.store(outputStrem, "File di properties GENERATO, non modificare manualmente!");
      log.debug("creato il file");
    } catch (FileNotFoundException e) {
      log.error("Errore FileNotFoundException:", e);
    } catch (IOException e) {
      log.error("Errore IOException:", e);
    }
  }

  private static StringBuilder scanAnnotation(Class<? extends Annotation> annotationClazz) {
    Reflections reflections = new Reflections("it.liguriadigitale.ponmetro.portale");
    Set<Class<?>> clazzSet = reflections.getTypesAnnotatedWith(annotationClazz);

    int sizeAnnotationSet = clazzSet.size();
    log.debug("set.size : " + sizeAnnotationSet);
    int index = 0;
    StringBuilder builder = new StringBuilder();
    for (Class<?> classe : clazzSet) {
      index++;
      Package currentPackage = classe.getPackage();
      log.debug("classe=" + currentPackage.getName());
      log.debug("index=" + index);
      builder.append(currentPackage.getName());
      if (index < sizeAnnotationSet) {
        builder.append(";");
      }
    }
    return builder;
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

  public Properties getPackageFinder() {
    log.debug("ScanAnnotationMain > getPackageFinder");
    StringBuilder builder = scanAnnotation(FdCPackagePropertiesAnnotation.class);
    StringBuilder builderWidget = scanAnnotation(FdCWidgetPropertiesAnnotation.class);
    Properties props = createProperties(builder, builderWidget);
    return props;
  }
}
