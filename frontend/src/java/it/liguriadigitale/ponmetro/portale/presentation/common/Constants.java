package it.liguriadigitale.ponmetro.portale.presentation.common;

import it.liguriadigitale.ponmetro.portale.presentation.common.Constants.TIPO_DEPLOY;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Constants {

  private static Log log = LogFactory.getLog(Constants.class);

  public static final String DATE_PATTERN = "dd/MM/yyyy";
  public static boolean LAZY_PANEL = false;

  /***** Costanti per file di properties **/
  private static final String JBOSS_SERVER_CONFIG_DIR = "jboss.server.config.dir";

  private static final String FILE_CONFIGURAZIONE = "ambiente.properties";
  private static final String PATH_DELIMITER = "/";
  private static final String DEPLOY_CONTEXT = "deployContext";
  private static final String DIR_PROPERTIES = "/properties";

  /******************************************/

  public enum TIPO_DEPLOY {
    ESERCIZIO,
    SVILUPPO,
    MANUTENZIONE,
    TEST_LOGIN,
    TEST_IAM,
    INTEGRAZIONE,
    DEMO
  }

  public static TIPO_DEPLOY DEPLOY;

  static {
    try {
      Properties propertiesFile = getPropertyFile();

      if (propertiesFile == null) {
        throw new Error("Constants errore inizializzazione: " + FILE_CONFIGURAZIONE);
      }
      log.debug("propertiesFile: " + propertiesFile);
      String deployContext = StringUtils.upperCase(propertiesFile.getProperty("DEPLOY_CONTEXT"));
      log.debug("deployContext: " + deployContext);
      if (StringUtils.isNotBlank(deployContext)) {
        for (TIPO_DEPLOY tipo : TIPO_DEPLOY.values()) {
          if (tipo.name().equalsIgnoreCase(deployContext)) {
            DEPLOY = tipo;
            log.debug("tipo.name=" + tipo.name());
          }
        }
      } else {
        DEPLOY = TIPO_DEPLOY.ESERCIZIO;
      }
    } catch (Exception ioe) {
      throw new Error("Errore inizializzazione business: " + ioe.getMessage());
    }
  }

  private static final Properties getPropertyFile() {

    Properties prop = new Properties();
    log.debug("[Constants] FILE_CONFIGURAZIONE:" + FILE_CONFIGURAZIONE);
    String path = getConfigDirPath();

    String fileName = path + PATH_DELIMITER + FILE_CONFIGURAZIONE;
    log.debug("[Constants] fileName:" + fileName);

    try (FileInputStream settingsStream = new FileInputStream(fileName)) {
      prop.load(settingsStream);
    } catch (final IOException e) {
      log.error("[FDC Frontend] Errore nella lettura del file di properties", e);
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
      if (deployContext.equalsIgnoreCase(TIPO_DEPLOY.INTEGRAZIONE.name())) {
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

  public enum Componente {
    GESTORE("Fascicolo del Cittadino", "portale");

    private String nomeComponente;
    private String webContext;

    Componente(String nomeComponente, String webContext) {
      setNomeComponente(nomeComponente);
      setWebContext(webContext);
    }

    public String getNomeComponente() {
      return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
      this.nomeComponente = nomeComponente;
    }

    public String getWebContext() {
      return webContext;
    }

    public void setWebContext(String webContext) {
      this.webContext = webContext;
    }
  }
}
