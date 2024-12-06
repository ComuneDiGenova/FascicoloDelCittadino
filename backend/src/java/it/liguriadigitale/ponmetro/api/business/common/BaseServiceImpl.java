package it.liguriadigitale.ponmetro.api.business.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseServiceImpl {

  private static Log log = LogFactory.getLog(BaseServiceImpl.class);

  /***** Costanti per file di properties **/
  private static final String JBOSS_SERVER_CONFIG_DIR = "jboss.server.config.dir";

  private static final String FILE_CONFIGURAZIONE = "fdc_backend_configurazione.properties";
  private static final String PATH_DELIMITER = "/";
  private static final String DEPLOY_CONTEXT = "deployContext";
  private static final String DIR_PROPERTIES = "/properties";
  private static final String INTEGRAZIONE = "integrazione";
  private static final String SVILUPPO = "sviluppo";

  /******************************************/

  public static final String FDCT_DATASOURCE;

  public static final String PRIV_DATASOURCE = "java:jboss/jdbc/privDataSource";
  public static final String PERS_DATASOURCE = "java:jboss/jdbc/persDataSource";
  public static final String JBOSS_VERSION;
  public static final String COD_APP = "FCITT";
  public static final String TOKEN_COMGE;
  public static final String API_COM_GE_DEMOGRAFICO;
  public static final String API_COM_GE_TASSA_AUTO;
  public static final String API_COM_GE_ASSICURAZIONE_VEICOLO;
  public static final String API_BIBLIOTECHE;
  public static final String API_ABBONAMENTI_AMT;

  static {
    try {
      Properties propertiesFile = getPropertyFile();
      if (propertiesFile == null)
        throw new Error("Errore inizializzazione business: " + "ejb-location");
      FDCT_DATASOURCE = propertiesFile.getProperty("DATA_SOURCE");
      JBOSS_VERSION = propertiesFile.getProperty("JBOSS_VERSION");
      API_COM_GE_DEMOGRAFICO = propertiesFile.getProperty("API_COM_GE_DEMOGRAFICO");
      API_COM_GE_TASSA_AUTO = propertiesFile.getProperty("API_COM_GE_TASSA_AUTO");
      API_COM_GE_ASSICURAZIONE_VEICOLO =
          propertiesFile.getProperty("API_COM_GE_ASSICURAZIONE_VEICOLO");
      TOKEN_COMGE = propertiesFile.getProperty("TOKEN");
      API_BIBLIOTECHE = propertiesFile.getProperty("API_BIBLIOTECHE");
      API_ABBONAMENTI_AMT = propertiesFile.getProperty("API_ABBONAMENTI_AMT");

    } catch (Exception ioe) {
      throw new Error("Errore inizializzazione business: " + ioe.getMessage());
    }
  }

  private static final Properties getPropertyFile() {

    Properties prop = new Properties();
    log.debug("[Constants] FILE_CONFIGURAZIONE:" + FILE_CONFIGURAZIONE);

    String path;
    path = getConfigDirPath();

    String fileName = path + PATH_DELIMITER + FILE_CONFIGURAZIONE;
    log.debug("[Constants] fileName:" + fileName);

    try (FileInputStream settingsStream = new FileInputStream(fileName)) {
      prop.load(settingsStream);
    } catch (final IOException e) {
      log.error("[FDC Backend] Errore nella lettura del file di properties", e);
    }
    return prop;
  }

  private static String getConfigDirPath() {
    log.debug("getConfigDirPath BaseServiceImpl BackEnd START");
    String path = System.getProperty(JBOSS_SERVER_CONFIG_DIR);
    try {
      if (System.getProperty(DEPLOY_CONTEXT) == null) {
        return path;
      }
      String deployContext = System.getProperty(DEPLOY_CONTEXT);
      if (deployContext.equalsIgnoreCase(INTEGRAZIONE)) {
        path += DIR_PROPERTIES;
        log.debug("Properties default directory cambiata a : " + path);
      }
    } catch (Exception e) {
      log.debug(
          "ERRORE in getConfigDirPath BaseServiceImpl BackEnd, Utilizzo "
              + path
              + "\n errore = "
              + e);
    }
    return path;
  }
}
