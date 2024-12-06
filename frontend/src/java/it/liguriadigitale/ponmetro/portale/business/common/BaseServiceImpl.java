package it.liguriadigitale.ponmetro.portale.business.common;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants.TIPO_DEPLOY;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.annotation.ScanAnnotationMain;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class BaseServiceImpl {

  private static Log log = LogFactory.getLog(BaseServiceImpl.class);

  /***** Costanti per file di properties **/
  private static final String JBOSS_SERVER_CONFIG_DIR = "jboss.server.config.dir";

  private static final String FILE_CONFIGURAZIONE = "fdc_frontend_configurazione.properties";
  private static final String FILE_CONFIGURAZIONE_PACKAGE = "fdc_package_finder.properties";
  private static final String PATH_DELIMITER = "/";
  private static final String DEPLOY_CONTEXT = "deployContext";
  private static final String DIR_PROPERTIES = "/properties";

  /******************************************/

  public static String[] searchPackagesFunction;

  public static String[] searchPackagesWidget;
  public static String[] searchPackages;

  public static final String BUILD_VERSION;
  public static final String COD_APP = "FCITT";
  public static final String COD_SEBINA = "SEBINA";
  public static final String COD_SERVIZI_DI_CORTESIA = "CORTESIA";
  public static final String COD_TELERISCALDAMENTO = "TELERISCALDAMENTO";
  public static final String COD_CEDOLE = "CEDOLELIBRARIE";
  public static final String COD_HELP_RIMBORSO_TARI = "HELP";
  public static final String COD_HELP_TELERISCALDAMENTO = "HELP";
  public static final String COD_BRAV = "BRAV";
  public static final String COD_CANONEIDRICO = "CANONEIDRICO";

  public static final String API_COM_GE_RISTORAZIONE_POST;
  public static final String API_MIP_GESTIONALI_VERTICALI;
  public static final String API_COM_GE_RISTORAZIONE;
  public static final String API_COM_GE_DEMOGRAFICO;
  public static final String API_COM_GE_DEMOGRAFICO_GRAPHQL;
  public static final String API_COM_GE_CONFIGURAZIONE;

  public static final String API_COM_GE_INPS;
  public static final String API_COM_GE_SEGNALAZIONITRAFFICO;
  public static final String API_COM_GE_SCADENZE;
  public static final String API_COM_GE_AUDIT;
  public static final String TOKEN_COMGE;
  public static final String TOKEN_CALL_PRODUZIONE_TO_TEST;

  public static final String TOKEN_RL;
  public static final String API_COM_GE_TASSA_AUTO;
  public static final String API_COM_GE_ASSICURAZIONE_VEICOLO;
  public static final String API_COM_GE_CEDOLE_LIBRARIE;

  public static final String API_VERBALI_CONTRAVVENZIONI;
  public static final String API_VERBALI_MOCK;

  public static final String API_BOLLO;

  public static final String AMBIENTE_PAGAMENTO_BOLLO;
  public static final String URL_PAGOPA_BOLLO_OK;
  public static final String URL_PAGOPA_BOLLO_KO;

  public static final String API_MIP_GLOBALI;
  public static final String API_MIP_GLOBALI_MOCK;
  public static final String URL_PAGOPA_MIP_OK;
  public static final String URL_PAGOPA_MIP_KO;

  public static final String EMAIL_PAGAMENTO_DEFAULT;

  public static final String API_TARI_NETRIBE;

  public static final String URI_SERVER_PAGAMENTO;

  public static final String API_AVATAR;

  public static final String API_CONTATTI_UTENTE;

  public static final String API_BIBLIOTECHE;

  public static final String API_TRIBUTI_ARGO_MOCK;

  public static final String API_SEGNALAZIONI;
  public static final String TOKEN_SEGNALAZIONI;

  public static final String NOME_UTENTE_MAIL;
  public static final String FROM_ADDRESS;
  public static final String FROM_NAME;
  public static final String HOST_SMTP;
  public static final String TO_ADDRESS;
  public static final String CC_ADDRESS;
  public static final String TO_STATOCIVLE_ADDRESS;
  public static final String CC_STATOCIVLE_ADDRESS;
  public static final String TO_ANAGRAFE_ADDRESS;
  public static final String CC_ANAGRAFE_ADDRESS;

  public static final String CC_ERRORE_ADDRESS;

  public static final String MINORE_DI;

  public static final String API_TRIBUTI;

  public static final String API_DIETE_MOCK;

  public static final String API_ANAGRAFICI;

  public static final String API_COMMISSIONI_MENSA;

  public static final String API_GEOSERVER;

  public static final String API_SUPPORTO_ISTANZE_VERBALI_PL;
  public static final String API_BACKEND_CERTIFICATI;

  public static final String API_ABBONAMENTI_AMT;

  public static final String API_PUNTI_TARI;

  public static final String API_PLASTIPREMIA;

  public static final String API_PERMESSI_PERSONALIZZATI;

  public static final String API_SEGGI;

  public static final String API_GENOVA_PARCHEGGI;

  public static final String API_ALLERTE_ZONA_ROSSA;

  public static final String API_ALLERTE_CORTESIA;

  public static final String API_GEOREF_TOPONOMASTICA;

  public static final String API_ARTE;

  public static final String API_ARPAL;

  public static final String API_EDILIZIA_ABITABILITA;

  public static final String API_EDILIZIA_CONDONO;

  public static final String API_EDILIZIA_PRATICHE;

  public static final String API_CAITEL;

  public static final String API_TELERISCALDAMENTO;

  public static final String API_TELERISCALDAMENTO_IREN;

  public static final String API_TARI_ENG;

  public static final String API_TARI_RIMBORSI_ENG;

  public static final String API_IMU_RIMBORSI_ENG;

  public static final String API_BORSE_STUDIO;

  public static final String SALVATAGGIO_CONTATTI_ACCESO;

  public static final String API_OGGETTI_SMARRITI;

  public static final String CHATBOT_SALESFORCE;
  public static final String CHATBOT_SALESFORCE_PARTNER;
  public static final String CHATBOT_ID_INIT;
  public static final String CHATBOT_CRISTOFOROCOLOMBO;
  public static final String CHATBOT_SALESFORCE_LIVEAGENT_CONTENT;
  public static final String CHATBOT_ID_DEPLOYMENT;
  public static final String CHATBOT_ID_BUTTON;
  public static final String CHATBOT_SALESFORCE_LIVEAGENT_CHAT;
  public static final String CHATBOT_DEVNAME;

  public static final String API_GEOWORKS;
  public static final String API_GEOWORKS_DETTAGLI;
  public static final String API_GEOWORKS_DOMANDA;

  public static final String API_PAGAMENTO_CANONE_IDRICO;
  public static final String API_DOMANDE_ALLOGGIO;
  public static final String API_DOMANDE_LOCAZIONE;

  public static final String API_ACCENTURE;
  public static final String TOKEN_ACCENTURE;

  public static final String API_CONTROLLI_ALBI;

  public static final String FDC2_RICHIESTA_PRENOTAZIONE_APPUNTAMENTO;

  public static final String FDC2_RICHIESTA_PAGAMENTO_CANONE_IDRICO;
  public static final String FDC2_RICHIESTA_DOMANDA_UNIONI_CIVILI;
  public static final String FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_ALBO_PRESIDENTI;
  public static final String FDC2_RICHIESTA_DOMANDA_RINNOVO_ALBO_PRESIDENTI;
  public static final String FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_ALBO_SCRUTATORI;
  public static final String FDC2_RICHIESTA_DOMANDA_RINNOVO_ALBO_SCRUTATORI;
  public static final String FDC2_RICHIESTA_DOMANDA_CANCELLAZIONE_ALBO_SCRUTATORI;
  public static final String FDC2_RICHIESTA_DOMANDA_SEPARAZIONE_DIVORZI;
  public static final String FDC2_RICHIESTA_DOMANDA_TRASPORTO_BAMBINI_DISABILI_A_SCUOLA;
  public static final String FDC2_RICHIESTA_DOMANDA_MATRIMONIO;
  public static final String FDC2_RICHIESTA_DOMANDA_ALLOGGIO;
  public static final String FDC2_RICHIESTA_DOMANDA_LOCAZIONE;
  public static final String FDC2_RICHIESTA_PERMESSO_AREABLU;
  public static final String FDC2_RICHIESTA_PERMESSO_CUDE;
  public static final String FDC2_RICHIESTA_PERMESSO_ZTL;
  public static final String FDC2_RICHIESTA_RICORSO_AL_PREFETTO;
  public static final String FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_MENSA_RESIDENTE;
  public static final String FDC2_RICHIESTA_DOMANDA_AGEVOLAZIONE_TARIFFARIA_MENSA;
  public static final String FDC2_PAGAMENTO_VERBALE_PL;
  public static final String FDC2_RICHIESTA_DOMANDA_TRASCRIZIONI_MATRIMONIO;
  public static final String FDC2_RICHIESTA_DOMANDA_TRASCRIZIONI_SCIOGLIMENTO_MATRIMONIO;
  public static final String FDC2_RICHIESTA_AUTODICHIARAZIONE_FIGLI;
  public static final String FDC2_RICHIESTA_BOLLETTAZIONE_MENSA;
  public static final String FDC2_RICHIESTA_SEGNALAZIONE_DANNI_BENI_PRIVATI;

  // "https://apitest.comune.genova.it:28243/sptel_sportello_telem/"
  public static final String API_SPORTELLO_GLOBO;
  public static final String API_PERICOLOSITA;
  // = "https://smart.comune.genova.it/";
  public static final String URN_SMART_COMUNE_GENOVA;
  public static final String URL_LOGOUT_GLOBO;
  public static final String URL_SSO_GLOBO;
  public static final String URL_LOGOUT_FDC;

  @SuppressWarnings("unused")
  private static final int DIRECT_STACK_TRACE_INDEX = 0;

  @SuppressWarnings("unused")
  private static final int DIRECT_FIRST_CODE_STACK_INDEX = 1;

  private static final int METHOD_FIRST_CODE_STACK_INDEX = 2;

  public static final String IO_GENITORE_VISIBILE_FINO_A;

  public static final String URL_PAGOPA_GEPARK_OK;
  public static final String URL_PAGOPA_GEPARK_KO;
  public static final String URI_SERVER_PAGAMENTO_GEPARK;

  public static final String INPS_IDENTITY_USER_ID;
  public static final String INPS_CODICE_UFFICIO;

  public static final String TOKEN_SCAI_REALGIM;
  public static final String API_REALGIM_BANDI;

  static {
    try {
      Properties propertiesFile = getPropertyFile();
      getPropertyPackage();
      if (propertiesFile == null) {
        throw new Error("Errore inizializzazione business: " + "ejb-location");
      }
      // Versione applicazione presa da SVN
      BUILD_VERSION = getBuildVersionDaEar();

      // TOKEN WSO2
      TOKEN_SEGNALAZIONI = propertiesFile.getProperty("TOKEN_SEGNALAZIONI");
      TOKEN_COMGE = propertiesFile.getProperty("TOKEN");
      TOKEN_CALL_PRODUZIONE_TO_TEST = propertiesFile.getProperty("TOKEN_CALL_PRODUZIONE_TO_TEST");
      TOKEN_RL = propertiesFile.getProperty("TOKEN_RL");

      // URL API BACKEND
      API_COM_GE_CONFIGURAZIONE = propertiesFile.getProperty("API_COM_GE_CONFIGURAZIONE");
      API_AVATAR = propertiesFile.getProperty("API_AVATAR");
      API_COM_GE_AUDIT = propertiesFile.getProperty("API_COM_GE_AUDIT");
      API_BACKEND_CERTIFICATI = propertiesFile.getProperty("API_BACKEND_CERTIFICATI");
      API_SUPPORTO_ISTANZE_VERBALI_PL =
          propertiesFile.getProperty("API_SUPPORTO_ISTANZE_VERBALI_PL");
      API_CONTATTI_UTENTE = propertiesFile.getProperty("API_CONTATTI_UTENTE");

      // URL API
      API_COM_GE_RISTORAZIONE = propertiesFile.getProperty("API_COM_GE_RISTORAZIONE");
      API_COM_GE_RISTORAZIONE_POST = propertiesFile.getProperty("API_COM_GE_RISTORAZIONE_POST");
      API_MIP_GESTIONALI_VERTICALI = propertiesFile.getProperty("API_MIP_GESTIONALI_VERTICALI");
      API_COM_GE_DEMOGRAFICO = propertiesFile.getProperty("API_COM_GE_DEMOGRAFICO");
      API_COM_GE_DEMOGRAFICO_GRAPHQL = propertiesFile.getProperty("API_COM_GE_DEMOGRAFICO_GRAPHQL");
      API_COM_GE_INPS = propertiesFile.getProperty("API_COM_GE_INPS");
      API_COM_GE_SEGNALAZIONITRAFFICO =
          propertiesFile.getProperty("API_COM_GE_SEGNALAZIONITRAFFICO");
      API_COM_GE_SCADENZE = propertiesFile.getProperty("API_COM_GE_SCADENZE");
      API_COM_GE_TASSA_AUTO = propertiesFile.getProperty("API_COM_GE_TASSA_AUTO");
      API_COM_GE_ASSICURAZIONE_VEICOLO =
          propertiesFile.getProperty("API_COM_GE_ASSICURAZIONE_VEICOLO");
      API_VERBALI_CONTRAVVENZIONI = propertiesFile.getProperty("API_VERBALI_CONTRAVVENZIONI");
      API_BOLLO = propertiesFile.getProperty("API_BOLLO");
      AMBIENTE_PAGAMENTO_BOLLO = propertiesFile.getProperty("AMBIENTE_PAGAMENTO_BOLLO");
      URL_PAGOPA_BOLLO_OK = propertiesFile.getProperty("URL_PAGOPA_BOLLO_OK");
      URL_PAGOPA_BOLLO_KO = propertiesFile.getProperty("URL_PAGOPA_BOLLO_KO");
      API_MIP_GLOBALI = propertiesFile.getProperty("API_MIP_GLOBALI");
      API_MIP_GLOBALI_MOCK = propertiesFile.getProperty("API_MIP_GLOBALI_MOCK");
      URL_PAGOPA_MIP_OK = propertiesFile.getProperty("URL_PAGOPA_MIP_OK");
      URL_PAGOPA_MIP_KO = propertiesFile.getProperty("URL_PAGOPA_MIP_KO");
      API_TARI_NETRIBE = propertiesFile.getProperty("API_TARI_NETRIBE");
      API_BIBLIOTECHE = propertiesFile.getProperty("API_BIBLIOTECHE");
      API_SEGNALAZIONI = propertiesFile.getProperty("API_SEGNALAZIONI");
      API_TRIBUTI = propertiesFile.getProperty("API_TRIBUTI");
      API_ANAGRAFICI = propertiesFile.getProperty("API_ANAGRAFICI");
      API_COMMISSIONI_MENSA = propertiesFile.getProperty("API_COMMISSIONI_MENSA");
      API_COM_GE_CEDOLE_LIBRARIE = propertiesFile.getProperty("API_COM_GE_CEDOLE_LIBRARIE");
      API_GEOSERVER = propertiesFile.getProperty("API_GEOSERVER");
      API_ABBONAMENTI_AMT = propertiesFile.getProperty("API_ABBONAMENTI_AMT");
      API_PUNTI_TARI = propertiesFile.getProperty("API_PUNTI_TARI");
      API_PLASTIPREMIA = propertiesFile.getProperty("API_PLASTIPREMIA");
      API_PERMESSI_PERSONALIZZATI = propertiesFile.getProperty("API_PERMESSI_PERSONALIZZATI");
      API_SEGGI = propertiesFile.getProperty("API_SEGGI");
      API_GENOVA_PARCHEGGI = propertiesFile.getProperty("API_GENOVA_PARCHEGGI");
      API_ALLERTE_ZONA_ROSSA = propertiesFile.getProperty("API_ALLERTE_ZONA_ROSSA");
      API_ALLERTE_CORTESIA = propertiesFile.getProperty("API_ALLERTE_CORTESIA");
      API_GEOREF_TOPONOMASTICA = propertiesFile.getProperty("API_GEOREF_TOPONOMASTICA");
      API_ARTE = propertiesFile.getProperty("API_ARTE");
      API_ARPAL = propertiesFile.getProperty("API_ARPAL");
      API_EDILIZIA_ABITABILITA = propertiesFile.getProperty("API_EDILIZIA_ABITABILITA");
      API_EDILIZIA_CONDONO = propertiesFile.getProperty("API_EDILIZIA_CONDONO");
      API_EDILIZIA_PRATICHE = propertiesFile.getProperty("API_EDILIZIA_PRATICHE");

      API_CAITEL = propertiesFile.getProperty("API_CAITEL");
      API_TELERISCALDAMENTO = propertiesFile.getProperty("API_TELERISCALDAMENTO");
      API_TELERISCALDAMENTO_IREN = propertiesFile.getProperty("API_TELERISCALDAMENTO_IREN");

      API_TARI_ENG = propertiesFile.getProperty("API_TARI_ENG");

      API_TARI_RIMBORSI_ENG = propertiesFile.getProperty("API_TARI_RIMBORSI_ENG");
      API_IMU_RIMBORSI_ENG = propertiesFile.getProperty("API_IMU_RIMBORSI_ENG");

      API_BORSE_STUDIO = propertiesFile.getProperty("API_BORSE_STUDIO");

      API_SPORTELLO_GLOBO = propertiesFile.getProperty("API_SPORTELLO_GLOBO");
      API_PERICOLOSITA = propertiesFile.getProperty("API_PERICOLOSITA");
      API_DIETE_MOCK = "";
      propertiesFile.getProperty("API_DIETE_MOCK");
      API_VERBALI_MOCK = propertiesFile.getProperty("API_VERBALI_MOCK");
      API_TRIBUTI_ARGO_MOCK = propertiesFile.getProperty("API_TRIBUTI_ARGO_MOCK");

      URL_PAGOPA_GEPARK_OK = propertiesFile.getProperty("URL_PAGOPA_GEPARK_OK");
      URL_PAGOPA_GEPARK_KO = propertiesFile.getProperty("URL_PAGOPA_GEPARK_KO");
      URI_SERVER_PAGAMENTO_GEPARK = propertiesFile.getProperty("URI_SERVER_PAGAMENTO_GEPARK");
      // Configurazioni E-MAIL
      NOME_UTENTE_MAIL = propertiesFile.getProperty("NOME_UTENTE_MAIL");
      FROM_ADDRESS = propertiesFile.getProperty("FROM_ADDRESS");
      FROM_NAME = propertiesFile.getProperty("FROM_NAME");
      HOST_SMTP = propertiesFile.getProperty("HOST_SMTP");
      TO_ADDRESS = propertiesFile.getProperty("TO_ADDRESS");
      CC_ADDRESS = propertiesFile.getProperty("CC_ADDRESS");
      TO_STATOCIVLE_ADDRESS = propertiesFile.getProperty("TO_STATOCIVLE_ADDRESS");
      CC_STATOCIVLE_ADDRESS = propertiesFile.getProperty("CC_STATOCIVLE_ADDRESS");
      TO_ANAGRAFE_ADDRESS = propertiesFile.getProperty("TO_ANAGRAFE_ADDRESS");
      CC_ANAGRAFE_ADDRESS = propertiesFile.getProperty("CC_ANAGRAFE_ADDRESS");
      CC_ERRORE_ADDRESS = propertiesFile.getProperty("CC_ERRORE_ADDRESS");

      // MISCELLANEA
      MINORE_DI = propertiesFile.getProperty("MINORE_DI");
      URI_SERVER_PAGAMENTO = propertiesFile.getProperty("URI_SERVER_PAGAMENTO");
      URN_SMART_COMUNE_GENOVA = propertiesFile.getProperty("URN_SMART_COMUNE_GENOVA");
      URL_LOGOUT_GLOBO = propertiesFile.getProperty("URL_LOGOUT_GLOBO");
      URL_SSO_GLOBO = propertiesFile.getProperty("URL_SSO_GLOBO");
      URL_LOGOUT_FDC = propertiesFile.getProperty("URL_LOGOUT_FDC");

      EMAIL_PAGAMENTO_DEFAULT = propertiesFile.getProperty("EMAIL_PAGAMENTO_DEFAULT");

      // DB
      IO_GENITORE_VISIBILE_FINO_A =
          ServiceLocator.getInstance()
              .getServiziHomePage()
              .getStringaRisorsaDalDb("IO_GENITORE_VISIBILE_FINO_A");

      INPS_IDENTITY_USER_ID = propertiesFile.getProperty("INPS_IDENTITY_USER_ID");
      INPS_CODICE_UFFICIO = propertiesFile.getProperty("INPS_CODICE_UFFICIO");

      SALVATAGGIO_CONTATTI_ACCESO = propertiesFile.getProperty("SALVATAGGIO_CONTATTI_ACCESO");

      API_OGGETTI_SMARRITI = propertiesFile.getProperty("API_OGGETTI_SMARRITI");

      CHATBOT_SALESFORCE = propertiesFile.getProperty("CHATBOT_SALESFORCE");
      CHATBOT_SALESFORCE_PARTNER = propertiesFile.getProperty("CHATBOT_SALESFORCE_PARTNER");
      CHATBOT_ID_INIT = propertiesFile.getProperty("CHATBOT_ID_INIT");
      CHATBOT_CRISTOFOROCOLOMBO = propertiesFile.getProperty("CHATBOT_CRISTOFOROCOLOMBO");
      CHATBOT_SALESFORCE_LIVEAGENT_CONTENT =
          propertiesFile.getProperty("CHATBOT_SALESFORCE_LIVEAGENT_CONTENT");
      CHATBOT_ID_DEPLOYMENT = propertiesFile.getProperty("CHATBOT_ID_DEPLOYMENT");
      CHATBOT_ID_BUTTON = propertiesFile.getProperty("CHATBOT_ID_BUTTON");
      CHATBOT_SALESFORCE_LIVEAGENT_CHAT =
          propertiesFile.getProperty("CHATBOT_SALESFORCE_LIVEAGENT_CHAT");
      CHATBOT_DEVNAME = propertiesFile.getProperty("CHATBOT_DEVNAME");

      API_GEOWORKS = propertiesFile.getProperty("API_GEOWORKS");
      API_GEOWORKS_DETTAGLI = propertiesFile.getProperty("API_GEOWORKS_DETTAGLI");
      API_GEOWORKS_DOMANDA = propertiesFile.getProperty("API_GEOWORKS_DOMANDA");

      API_PAGAMENTO_CANONE_IDRICO = propertiesFile.getProperty("API_PAGAMENTO_CANONE_IDRICO");
      API_DOMANDE_ALLOGGIO = propertiesFile.getProperty("API_DOMANDE_ALLOGGIO");
      API_DOMANDE_LOCAZIONE = propertiesFile.getProperty("API_DOMANDE_LOCAZIONE");

      API_ACCENTURE = propertiesFile.getProperty("API_ACCENTURE");
      TOKEN_ACCENTURE = propertiesFile.getProperty("TOKEN_ACCENTURE");

      API_CONTROLLI_ALBI = propertiesFile.getProperty("API_CONTROLLI_ALBI");

      FDC2_RICHIESTA_PRENOTAZIONE_APPUNTAMENTO =
          propertiesFile.getProperty("FDC2_RICHIESTA_PRENOTAZIONE_APPUNTAMENTO");

      FDC2_RICHIESTA_DOMANDA_SEPARAZIONE_DIVORZI =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_SEPARAZIONE_DIVORZI");
      FDC2_RICHIESTA_DOMANDA_UNIONI_CIVILI =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_UNIONI_CIVILI");
      FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_ALBO_PRESIDENTI =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_ALBO_PRESIDENTI");
      FDC2_RICHIESTA_DOMANDA_RINNOVO_ALBO_PRESIDENTI =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_RINNOVO_ALBO_PRESIDENTI");
      FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_ALBO_SCRUTATORI =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_ALBO_SCRUTATORI");
      FDC2_RICHIESTA_DOMANDA_RINNOVO_ALBO_SCRUTATORI =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_RINNOVO_ALBO_SCRUTATORI");
      FDC2_RICHIESTA_DOMANDA_CANCELLAZIONE_ALBO_SCRUTATORI =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_CANCELLAZIONE_ALBO_SCRUTATORI");
      FDC2_RICHIESTA_DOMANDA_TRASPORTO_BAMBINI_DISABILI_A_SCUOLA =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_TRASPORTO_BAMBINI_DISABILI_A_SCUOLA");

      FDC2_RICHIESTA_PAGAMENTO_CANONE_IDRICO =
          propertiesFile.getProperty("FDC2_RICHIESTA_PAGAMENTO_CANONE_IDRICO");
      FDC2_RICHIESTA_DOMANDA_MATRIMONIO =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_MATRIMONIO");
      FDC2_RICHIESTA_DOMANDA_ALLOGGIO =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_ALLOGGIO");
      FDC2_RICHIESTA_DOMANDA_LOCAZIONE =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_LOCAZIONE");
      FDC2_RICHIESTA_PERMESSO_AREABLU =
          propertiesFile.getProperty("FDC2_RICHIESTA_PERMESSO_AREABLU");
      FDC2_RICHIESTA_PERMESSO_CUDE = propertiesFile.getProperty("FDC2_RICHIESTA_PERMESSO_CUDE");
      FDC2_RICHIESTA_PERMESSO_ZTL = propertiesFile.getProperty("FDC2_RICHIESTA_PERMESSO_ZTL");
      FDC2_RICHIESTA_RICORSO_AL_PREFETTO =
          propertiesFile.getProperty("FDC2_RICHIESTA_RICORSO_AL_PREFETTO");
      FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_MENSA_RESIDENTE =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_ISCRIZIONE_MENSA_RESIDENTE");
      FDC2_RICHIESTA_DOMANDA_AGEVOLAZIONE_TARIFFARIA_MENSA =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_AGEVOLAZIONE_TARIFFARIA_MENSA");
      FDC2_PAGAMENTO_VERBALE_PL = propertiesFile.getProperty("FDC2_PAGAMENTO_VERBALE_PL");

      FDC2_RICHIESTA_DOMANDA_TRASCRIZIONI_MATRIMONIO =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_TRASCRIZIONI_MATRIMONIO");
      FDC2_RICHIESTA_DOMANDA_TRASCRIZIONI_SCIOGLIMENTO_MATRIMONIO =
          propertiesFile.getProperty("FDC2_RICHIESTA_DOMANDA_TRASCRIZIONI_SCIOGLIMENTO_MATRIMONIO");
      FDC2_RICHIESTA_SEGNALAZIONE_DANNI_BENI_PRIVATI =
          propertiesFile.getProperty("FDC2_RICHIESTA_SEGNALAZIONE_DANNI_BENI_PRIVATI");
      FDC2_RICHIESTA_AUTODICHIARAZIONE_FIGLI =
          propertiesFile.getProperty("FDC2_RICHIESTA_AUTODICHIARAZIONE_FIGLI");

      FDC2_RICHIESTA_BOLLETTAZIONE_MENSA =
          propertiesFile.getProperty("FDC2_RICHIESTA_BOLLETTAZIONE_MENSA");

      TOKEN_SCAI_REALGIM = propertiesFile.getProperty("TOKEN_SCAI_REALGIM");
      API_REALGIM_BANDI = propertiesFile.getProperty("API_REALGIM_BANDI");

    } catch (Exception ioe) {
      throw new Error("Errore inizializzazione business: " + ioe.getMessage());
    }
  }

  protected String getMethodName() {
    return getMethodName(METHOD_FIRST_CODE_STACK_INDEX + 1);
  }

  private static String getBuildVersionDaEar() {

    ResourceBundle resource =
        ResourceBundle.getBundle(BaseServiceImpl.class.getPackage().getName() + ".build-version");

    if (resource == null) {
      throw new Error("Errore inizializzazione business: " + "build-version.properties");
    }
    return resource.getString("BUILD_VERSION");
  }

  protected String getMethodName(int index) {
    return Thread.currentThread().getStackTrace()[index].getMethodName();
  }

  protected <T> void manageException(
      T e,
      String exceptionMessage,
      String errorMessage,
      String methodName,
      String className,
      Class<? extends Page> classErrorPage)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    if (e instanceof BusinessException) {
      log.error("Errore 1");
      throw new BusinessException(errorMessage);
    } else if (e instanceof ServiceUnavailableException) {
      log.error("Errore 2");
      log.error(
          className
              + " -- "
              + methodName
              + ": errore ServiceUnavailableException: "
              + exceptionMessage);
      throw new ApiException(
          Response.serverError().status(500).tag(exceptionMessage).build(), errorMessage);
    } else if (e instanceof ResponseProcessingException || e instanceof WebApplicationException) {
      log.error("Errore 3");
      log.error(
          className
              + " -- "
              + methodName
              + " errore ResponseProcessingException ! WebApplicationException: "
              + exceptionMessage);
      throw new ApiException(
          Response.serverError().status(400).tag(exceptionMessage).build(), exceptionMessage);
    } else if (e instanceof RuntimeException) {
      log.error("Errore 4");
      log.error(
          className
              + " -- "
              + methodName
              + ": errore "
              + e.getClass().getName()
              + ":"
              + exceptionMessage);
      throw new RestartResponseAtInterceptPageException(classErrorPage);
    } else {
      log.error("Errore 5");
      log.error("Errore Innested: " + className + " - " + methodName + " - " + errorMessage);
      throw new BusinessException(errorMessage);
    }
  }

  private static final Properties getPropertyFile() {

    Properties prop = new Properties();
    log.debug("[FDC Frontend] FILE_CONFIGURAZIONE:" + FILE_CONFIGURAZIONE);
    String path = getConfigDirPath();

    String fileName = path + PATH_DELIMITER + FILE_CONFIGURAZIONE;
    log.debug("[FDC Frontend] fileName:" + fileName);

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

  private static final Properties getPropertyPackage() {

    Properties prop = new Properties();
    log.debug("[FDC Frontend] FILE_CONFIGURAZIONE:" + FILE_CONFIGURAZIONE_PACKAGE);

    String path = System.getProperty(JBOSS_SERVER_CONFIG_DIR);
    log.debug("[FDC Frontend] Path config:" + path);

    retrivePackageFinderFromScanAnnotation(path);

    /*
    String fileName = path + PATH_DELIMITER + FILE_CONFIGURAZIONE_PACKAGE;
    log.debug("[FDC Frontend] fileName:" + fileName);
    try (FileInputStream settingsStream = new FileInputStream(fileName)) {
    	prop.load(settingsStream);
    	valorizzaListArrayPackage(prop);
    } catch (final IOException e) {
    	log.error("[FDC Frontend] Errore nella lettura del file di properties", e);
    }*/

    return prop;
  }

  private static Properties retrivePackageFinderFromScanAnnotation(String path) {

    Properties propertiesFile = new Properties();
    propertiesFile = new ScanAnnotationMain().getPackageFinder();

    String searchPackagesFunctionProperty =
        propertiesFile.getProperty("searchPackagesFunctionProperty");
    String searchPackagesWidgetProperty =
        propertiesFile.getProperty("searchPackagesWidgetProperty");
    String searchPackagesProperty = propertiesFile.getProperty("searchPackagesProperty");

    if (LabelFdCUtil.checkIfNotNull(searchPackagesFunctionProperty)) {
      searchPackagesFunction = StringUtils.split(searchPackagesFunctionProperty, ";");
    }

    if (LabelFdCUtil.checkIfNotNull(searchPackagesWidgetProperty)) {
      searchPackagesWidget = StringUtils.split(searchPackagesWidgetProperty, ";");
    }

    if (LabelFdCUtil.checkIfNotNull(searchPackagesProperty)) {
      searchPackages = StringUtils.split(searchPackagesProperty, ";");
    }
    return propertiesFile;
  }
}
