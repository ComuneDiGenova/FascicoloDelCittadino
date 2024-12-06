package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.apiclient.AmtApi;
import it.liguriadigitale.ponmetro.allertecortesia.apiclient.GeorefToponomasticaApi;
import it.liguriadigitale.ponmetro.allertecortesia.apiclient.RegistrazioneServiziCortesiaApi;
import it.liguriadigitale.ponmetro.allertezonarossa.apiclient.AllerteareearischioApi;
import it.liguriadigitale.ponmetro.anonim.apiclient.BackendApi;
import it.liguriadigitale.ponmetro.api.business.rest.util.ApiRestLoggingFilter;
import it.liguriadigitale.ponmetro.api.business.rest.util.OAuth2Authenticator;
import it.liguriadigitale.ponmetro.api.presentation.rest.audit.service.AuditRestInterface;
import it.liguriadigitale.ponmetro.api.presentation.rest.certificati.service.CertificatiRestInterface;
import it.liguriadigitale.ponmetro.api.presentation.rest.family.service.FamilyRestInterface;
import it.liguriadigitale.ponmetro.api.presentation.rest.helpczrm.service.HelpCzRMApi;
import it.liguriadigitale.ponmetro.api.presentation.rest.home.service.HomeRestInterface;
import it.liguriadigitale.ponmetro.api.presentation.rest.privacy.service.PrivacyRestInterface;
import it.liguriadigitale.ponmetro.api.presentation.rest.scadenze.service.ScadenzeRestInterface;
import it.liguriadigitale.ponmetro.arpal.apiclient.AlimsApi;
import it.liguriadigitale.ponmetro.arpal.apiclient.SimpaApi;
import it.liguriadigitale.ponmetro.arte.apiclient.ArteApi;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.apiclient.AssicurazioneVeicoloApi;
import it.liguriadigitale.ponmetro.borsestudio.apiclient.BorsedistudioApi;
import it.liguriadigitale.ponmetro.catastoimpianti.apiclient.CaitelApi;
import it.liguriadigitale.ponmetro.commissionimensa.apiclient.AuditApi;
import it.liguriadigitale.ponmetro.configurazione.apiclient.ConfigAvatarApi;
import it.liguriadigitale.ponmetro.contattiutente.apiclient.ContattiUtenteApi;
import it.liguriadigitale.ponmetro.controlli.albi.apiclient.ControlliAlbiElettoraliApi;
import it.liguriadigitale.ponmetro.demografico.apiclient.PortaleApi;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.apiclient.EntitaConfigurazioneUtenteApi;
import it.liguriadigitale.ponmetro.genovaparcheggi.apiclient.GeparkApi;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.apiclient.GenovaParcheggiHelperApi;
import it.liguriadigitale.ponmetro.geoserver.apiclient.GeoserverRestControllerApi;
import it.liguriadigitale.ponmetro.geoworks.apiclient.AppRequestFormsApi;
import it.liguriadigitale.ponmetro.geoworks.apiclient.AppRequestFormsSearchApi;
import it.liguriadigitale.ponmetro.geoworks.apiclient.DownloadApi;
import it.liguriadigitale.ponmetro.geoworkshelper.apiclient.GeoworksHelperApi;
import it.liguriadigitale.ponmetro.inps.modi.apiclient.InpsmodiApi;
import it.liguriadigitale.ponmetro.messaggi.utente.apiclient.MessaggiUtenteApi;
import it.liguriadigitale.ponmetro.oggettismarriti.apiclient.OggettiSmarritiApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.apiclient.DebitoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.apiclient.RiepilogoPagamentiPagoPaApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.AvvisoPagamentoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.RicevutaPagamentoApi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.TipologiaEntrataApi;
import it.liguriadigitale.ponmetro.pericolosita.apiclient.PericolositaApi;
import it.liguriadigitale.ponmetro.permessipersonalizzati.apiclient.DomandaApi;
import it.liguriadigitale.ponmetro.permessipersonalizzati.apiclient.UtilsApi;
import it.liguriadigitale.ponmetro.plastipremia.apiclient.PlastipremiaApi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.GeoserverRestControllerApiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.allerte.cortesia.ApiAllerteCortesiaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.allerte.cortesia.ApiGeorefToponomasticaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.allerte.zonerosse.ApiAllerteZoneRosseImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici.ApiCertificatiAnagrafe;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici.ApiCertificatiStatoCivile;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici.ApiFamigliaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici.ApiIndividuoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici.ApiPingImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici.ApiPraticaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.anagrafici.ApiToponomasticaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.arpal.ApiAlims;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.arpal.ApiSimpa;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.arte.ApiArteImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.abbonamentiamt.ApiAbbonamentiAmtImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.assicurazione.ApiAssicurazioneVeicoloImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.bollo.ApiVeicoloImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.genovaparcheggi.ApiGenovaParcheggiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.permessipersonalizzati.ApiDomandaPermessiPersonalizzatiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.permessipersonalizzati.ApiUtilsPermessiPersonalizzatiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.traffico.ApiServiziSegnalazioniTrafficoPortaleImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali.ApiAllegatiAlVerbaleImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali.ApiAllegatiAllIstanzaRicorsoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali.ApiDichiarazionePuntiPatenteImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali.ApiDocumentiVerbaliImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali.ApiIstanzeRicorsiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali.ApiRicorsiAlPrefettoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali.ApiVerbaliAvvisoBonarioImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali.ApiVerbaliImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiAnonimizzaNonResidentiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiConfigurazioneAvatarImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiContattiUtenteImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiEntitaConfigurazioneUtenteImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiGenovaParcheggiHelperImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiGeoworksHelperImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiHelpCzRMImp;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziAuditImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziCertificatiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziConfigurazionePrivacyImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziDichiarazioneGenitoreImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziGloboBackend;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziHomePageImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziMessaggiUtentePortaleImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziPersonImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziScadenzeImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.ApiServiziSupportoIstanzeVerbaliPlImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.GloboBackendInterface;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.borsedistudio.ApiBorseDiStudioImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.caitel.ApiCaitelImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.commissionimensa.ApiCommissioniMensa;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.controlliAlbi.ApiControlliAlbiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.czrm.ApiSegnalazioniCzrm;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.ApiServiziDemograficoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.geoworks.ApiGeoworksAppRequestFormImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.geoworks.ApiGeoworksAppRequestFormsSearchImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.geoworks.ApiGeoworksDownloadImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.inpsmodi.ApiInpsModiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.ApiServiziPagamentiMipVerticaliAvvisoPagamento;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.ApiServiziPagamentiMipVerticaliDebito;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.ApiServiziPagamentiMipVerticaliRicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.ApiServiziPagamentiMipVerticaliTipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.ApiTipologieEntrateImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.globali.ApiDebitoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.mip.globali.ApiRiepilogoPagamentiPagoPaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.oggettismarriti.ApiOggettiSmarritiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.pericolosita.ApiPericolositaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.cedole.ApiCedoleLibrarieImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.cedole.ApiUtilitaCedoleImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.ristorazione.ApiServiziRistorazionePortaleImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.seggi.ApiSeggiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazionedannibeniprivati.ApiSegnalazioneDanniBeniPrivatiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazioni.ApiPostsImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazioni.ApiStatsImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.teleriscaldamento.ApiTeleriscaldamentoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.teleriscaldamento.ApiTeleriscaldamentoIrenImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.TributiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariAgevolazioneTariffariaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariBollettinoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariRimborsoImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari.TariSintesiImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tarieng.ApiTariEngImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tarieng.ApiTariRimborsiEngImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.virtuoso.plastipremia.ApiPlasTiPremiaImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.virtuoso.puntitari.ApiPuntiTariImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.noyaml.InpsNoYamlImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.noyaml.InpsNoYamlService;
import it.liguriadigitale.ponmetro.portale.business.rest.noyaml.allertecortesia.AllerteCortesiaNoYamlImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.noyaml.allertecortesia.AllerteCortesiaNoYamlService;
import it.liguriadigitale.ponmetro.portale.business.rest.util.ErrorResponseFilter;
import it.liguriadigitale.ponmetro.portale.business.rest.util.JSON;
import it.liguriadigitale.ponmetro.portale.business.rest.util.LogChiamateInUscitaInterceptor;
import it.liguriadigitale.ponmetro.puntitari.apiclient.AmiuApi;
import it.liguriadigitale.ponmetro.scuola.cedole.apiclient.CedoleFdCApi;
import it.liguriadigitale.ponmetro.scuola.cedole.apiclient.PortaleFdcApi;
import it.liguriadigitale.ponmetro.seggi.apiclient.SeggielettoraliApi;
import it.liguriadigitale.ponmetro.segnalazionedannibeniprivati.apiclient.DefaultApi;
import it.liguriadigitale.ponmetro.segnalazioni.apiclient.PostsApi;
import it.liguriadigitale.ponmetro.segnalazioni.apiclient.StatisticsApi;
import it.liguriadigitale.ponmetro.segnalazioni.czrm.apiclient.SegnalazioniCzrmApi;
import it.liguriadigitale.ponmetro.segnalazionitraffico.apiclient.RetrieveSegnalazioniTrafficoApi;
import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.CertificatoAnagrafeApi;
import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.CertificatoStatoCivileApi;
import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.FamigliaApi;
import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.IndividuoApi;
import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.PingApi;
import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.PraticaApi;
import it.liguriadigitale.ponmetro.servizianagrafici.apiclient.ToponomasticaApi;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.apiclient.IstanzeVerbaliPlApi;
import it.liguriadigitale.ponmetro.tarieng.apiclient.FascicoloDelContribuenteTariRestControllerApi;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.AgevolazioneTariffariaTariApi;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.BollettinoApi;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.RimborsoApi;
import it.liguriadigitale.ponmetro.tarinetribe.apiclient.SintesiApi;
import it.liguriadigitale.ponmetro.taririmborsieng.apiclient.RimborsiApi;
import it.liguriadigitale.ponmetro.tassaauto.apiclient.TassaAutoApi;
import it.liguriadigitale.ponmetro.teleriscaldamento.apiclient.TeleriscaldamentoApi;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.apiclient.InserimentoRichiestaBonusApi;
import it.liguriadigitale.ponmetro.tributi.apiclient.FascicoloDelContribuenteRestControllerApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.AllegatiAlVerbaleApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.AllegatiAllIstanzaRicorsoApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.DichiarazionePuntiPatenteApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.DocumentiApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.IstanzeRicorsiApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.RicorsiAlPrefettoApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.VerbaliApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.VerbaliAvvisoBonarioApi;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ServiceLocatorLivelloUno {

  private static final CharSequence URL_API_PROD_COMUNE_GENOVA = "apiprod.comune.genova.it:28243";

  protected Log log = LogFactory.getLog(getClass());

  private ResteasyClient client;

  public static ServiceLocatorLivelloUno getInstance() {
    return new ServiceLocatorLivelloUno();
  }

  protected ResteasyWebTarget createResteasyWebTarget(String urlApi, Long timeout, String token)
      throws BusinessException {

    // ClientConnectionManager cm = new PoolingClientConnectionManager();
    // HttpClient httpClient = new DefaultHttpClient(cm);
    // ApacheHttpClient4Engine engine = new
    // ApacheHttpClient4Engine(httpClient);

    try {
      JSON json = new JSON();
      client =
          new ResteasyClientBuilder()
              // .httpEngine(engine)
              .socketTimeout(timeout, TimeUnit.SECONDS)
              .establishConnectionTimeout(timeout, TimeUnit.SECONDS)
              .register(json)
              .register(ApiRestLoggingFilter.class)
              .register(ErrorResponseFilter.class)
              .register(new OAuth2Authenticator(token))
              .register(LogChiamateInUscitaInterceptor.class)
              .connectionPoolSize(200)
              .connectionTTL(5, TimeUnit.SECONDS)
              .build();
      ResteasyWebTarget target = client.target(UriBuilder.fromPath(urlApi));
      if (target.getUri() != null) log.debug("target: " + target.getUri());
      return target;

    } catch (Exception e) {
      log.error("[ServiceLocatorLivelloUno] Errore durante la chiamata delle API Rest ", e);
      throw new BusinessException("Errore di connessione API");
    }
  }

  public void closeConnection() {
    if (client != null) {
      log.debug("[ServiceLocatorLivelloUno] CHIUDO SOCKET HTTP");
      client.close();
    }
  }

  private ResteasyWebTarget createWebTargetCallProduzione(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 30L, BaseServiceImpl.TOKEN_CALL_PRODUZIONE_TO_TEST);
  }

  private ResteasyWebTarget createWebTarget(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 30L, BaseServiceImpl.TOKEN_COMGE);
  }

  private ResteasyWebTarget createApiWSO2RL(String urlApi) throws BusinessException {
    return this.createResteasyWebTarget(urlApi, 30L, BaseServiceImpl.TOKEN_RL);
  }

  private PrivacyRestInterface createProxyPrivacyApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_CONFIGURAZIONE);
    return target.proxy(PrivacyRestInterface.class);
  }

  private PortaleApi createProxyDemograficoApi() throws BusinessException {
    ResteasyWebTarget target =
        createResteasyWebTarget(
            BaseServiceImpl.API_COM_GE_DEMOGRAFICO, 30L, BaseServiceImpl.TOKEN_COMGE);
    return target.proxy(PortaleApi.class);
  }

  private it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi
      createProxyRistorazioneApiPortale() throws BusinessException {
    ResteasyWebTarget target = getResteasyWebTargetByUrl(BaseServiceImpl.API_COM_GE_RISTORAZIONE);
    return target.proxy(it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi.class);
  }

  private it.liguriadigitale.ponmetro.anonim.apiclient.PortaleApi createProxyPersonApi()
      throws BusinessException {
    return createWebTarget(BaseServiceImpl.API_COM_GE_CONFIGURAZIONE)
        .proxy(it.liguriadigitale.ponmetro.anonim.apiclient.PortaleApi.class);
  }

  private BackendApi createProxyBackendApi() throws BusinessException {
    return createWebTarget(BaseServiceImpl.API_COM_GE_CONFIGURAZIONE + "anonym")
        .proxy(BackendApi.class);
  }

  private FamilyRestInterface createProxyFamilyApi() throws BusinessException {
    return createWebTarget(BaseServiceImpl.API_COM_GE_CONFIGURAZIONE)
        .proxy(FamilyRestInterface.class);
  }

  private RetrieveSegnalazioniTrafficoApi createProxySegnalazioniTrafficoApi()
      throws BusinessException {
    String urlapi = BaseServiceImpl.API_COM_GE_SEGNALAZIONITRAFFICO;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(RetrieveSegnalazioniTrafficoApi.class);
  }

  public FamilyRestInterface getApiFamily() throws BusinessException {
    return new ApiServiziDichiarazioneGenitoreImpl(this.createProxyFamilyApi());
  }

  public RetrieveSegnalazioniTrafficoApi getProxyApiSegnalazioniTraffico()
      throws BusinessException {
    return new ApiServiziSegnalazioniTrafficoPortaleImpl(this.createProxySegnalazioniTrafficoApi());
  }

  private TassaAutoApi createTassaAutoApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_COM_GE_TASSA_AUTO;
    ResteasyWebTarget target = createApiWSO2RL(urlapi);
    return target.proxy(TassaAutoApi.class);
  }

  public TassaAutoApi getApiTassaAuto() throws BusinessException {
    return new ApiVeicoloImpl(this.createTassaAutoApi());
  }

  private TassaAutoApi createTassaAutoRLApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_BOLLO;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(TassaAutoApi.class);
  }

  public TassaAutoApi getApiTassaAutoRL() throws BusinessException {
    return new ApiVeicoloImpl(this.createTassaAutoRLApi());
  }

  private AssicurazioneVeicoloApi createAssicurazioneVeicoloApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_COM_GE_ASSICURAZIONE_VEICOLO;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AssicurazioneVeicoloApi.class);
  }

  public AssicurazioneVeicoloApi getApiAssicurazioneVeicolo() throws BusinessException {
    return new ApiAssicurazioneVeicoloImpl(this.createAssicurazioneVeicoloApi());
  }

  public VerbaliApi createVerbaliApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_VERBALI_CONTRAVVENZIONI;
    log.debug("CP urlApi verbali = " + urlapi);
    ResteasyWebTarget target = createWebTarget(urlapi);

    return target.proxy(VerbaliApi.class);
  }

  public VerbaliAvvisoBonarioApi createVerbaliAvvisoBonarioApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_VERBALI_CONTRAVVENZIONI;
    log.debug("CP urlApi verbali = " + urlapi);
    ResteasyWebTarget target = createWebTarget(urlapi);

    return target.proxy(VerbaliAvvisoBonarioApi.class);
  }

  public VerbaliAvvisoBonarioApi getVerbaliAvvisoBonarioApi() throws BusinessException {

    return new ApiVerbaliAvvisoBonarioImpl(createVerbaliAvvisoBonarioApi());
  }

  public VerbaliApi getApiVerbaliContravvenzioni() throws BusinessException {
    return new ApiVerbaliImpl(this.createVerbaliApi());
  }

  public DocumentiApi createDocumentiVerbaliApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_VERBALI_CONTRAVVENZIONI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(DocumentiApi.class);
  }

  public DocumentiApi getApiDocumentiVerbali() throws BusinessException {
    return new ApiDocumentiVerbaliImpl(this.createDocumentiVerbaliApi());
  }

  public AllegatiAlVerbaleApi createAllegatiAlVerbaleApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_VERBALI_CONTRAVVENZIONI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AllegatiAlVerbaleApi.class);
  }

  public AllegatiAlVerbaleApi getApiAllegatiAlVerbale() throws BusinessException {
    return new ApiAllegatiAlVerbaleImpl(this.createAllegatiAlVerbaleApi());
  }

  public IstanzeRicorsiApi createIstanzeRicorsiApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_VERBALI_CONTRAVVENZIONI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(IstanzeRicorsiApi.class);
  }

  public IstanzeRicorsiApi getApiIstanzeRicorsi() throws BusinessException {
    return new ApiIstanzeRicorsiImpl(this.createIstanzeRicorsiApi());
  }

  public IstanzeRicorsiApi createIstanzeRicorsiMockApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_VERBALI_MOCK;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(IstanzeRicorsiApi.class);
  }

  public IstanzeRicorsiApi getApiIstanzeRicorsiMock() throws BusinessException {
    return new ApiIstanzeRicorsiImpl(this.createIstanzeRicorsiMockApi());
  }

  public AllegatiAllIstanzaRicorsoApi createAllegatiAllIstanzaRicorsoApi()
      throws BusinessException {
    String urlapi = BaseServiceImpl.API_VERBALI_CONTRAVVENZIONI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AllegatiAllIstanzaRicorsoApi.class);
  }

  public AllegatiAllIstanzaRicorsoApi getApiAllegatiAllIstanzaRicorso() throws BusinessException {
    return new ApiAllegatiAllIstanzaRicorsoImpl(this.createAllegatiAllIstanzaRicorsoApi());
  }

  public DichiarazionePuntiPatenteApi createDichiarazionePuntiPatenteApi()
      throws BusinessException {
    String urlapi = BaseServiceImpl.API_VERBALI_CONTRAVVENZIONI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(DichiarazionePuntiPatenteApi.class);
  }

  public DichiarazionePuntiPatenteApi getApiDichiarazionePuntiPatente() throws BusinessException {
    return new ApiDichiarazionePuntiPatenteImpl(this.createDichiarazionePuntiPatenteApi());
  }

  /*
   * public RiepilogoPagamentiPagoPaApi createRiepilogoPagamentiPagoPaApi() throws
   * BusinessException { String urlApi = BaseServiceImpl.API_MIP_GLOBALI;
   * ResteasyWebTarget target = createWebTarget(urlApi); return
   * target.proxy(RiepilogoPagamentiPagoPaApi.class); }
   *
   * public RiepilogoPagamentiPagoPaApi getApiRiepilogoPagamentiPagoPa() throws
   * BusinessException { return new
   * ApiRiepilogoPagamentiPagoPaImpl(this.createRiepilogoPagamentiPagoPaApi()) ; }
   *
   * public DebitoApi createDebitoApi() throws BusinessException { String urlApi =
   * BaseServiceImpl.API_MIP_GLOBALI; ResteasyWebTarget target =
   * createWebTarget(urlApi); return target.proxy(DebitoApi.class); }
   *
   * public DebitoApi getApiDebito() throws BusinessException { return new
   * ApiDebitoImpl(this.createDebitoApi()); }
   */

  public it.liguriadigitale.ponmetro.anonim.apiclient.PortaleApi getApiPerson()
      throws BusinessException {
    return new ApiServiziPersonImpl(this.createProxyPersonApi());
  }

  public BackendApi getApiAnonym() throws BusinessException {
    return new ApiAnonimizzaNonResidentiImpl(this.createProxyBackendApi());
  }

  public it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi
      getApiRistorazionePortale() throws BusinessException {
    return new ApiServiziRistorazionePortaleImpl(this.createProxyRistorazioneApiPortale());
  }

  public PortaleApi getApiDemografico() throws BusinessException {
    return new ApiServiziDemograficoImpl(this.createProxyDemograficoApi());
  }

  public PrivacyRestInterface getApiPrivacy() throws BusinessException {
    return new ApiServiziConfigurazionePrivacyImpl(this.createProxyPrivacyApi());
  }

  private ScadenzeRestInterface createScadenzeApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_SCADENZE);
    return target.proxy(ScadenzeRestInterface.class);
  }

  public ScadenzeRestInterface getApiScadenze() throws BusinessException {
    return new ApiServiziScadenzeImpl(this.createScadenzeApi());
  }

  private MessaggiUtenteApi createMessaggiUtenteApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_CONFIGURAZIONE);
    return target.proxy(MessaggiUtenteApi.class);
  }

  public MessaggiUtenteApi getApiMessaggiUtente() throws BusinessException {
    return new ApiServiziMessaggiUtentePortaleImpl(this.createMessaggiUtenteApi());
  }

  public GeoserverRestControllerApi getApiGeoserver() throws BusinessException {
    return new GeoserverRestControllerApiImpl(this.createGeoserverApi());
  }

  public GeoserverRestControllerApi createGeoserverApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_GEOSERVER;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(GeoserverRestControllerApi.class);
  }

  // TEMPORANEO: chiama GoaDev
  public it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi
      createRistorazioneApiPostToGoadev() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_RISTORAZIONE_POST);
    log.debug("URL:" + target.getUri().toASCIIString());
    return target.proxy(it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi.class);
  }

  public it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi
      getApiRistorazioneGoadev() throws BusinessException {
    return new ApiServiziRistorazionePortaleImpl(this.createRistorazioneApiPostToGoadev());
  }

  private ResteasyWebTarget getResteasyWebTargetByUrl(String urlApi) throws BusinessException {
    ResteasyWebTarget target;
    if (urlApi.contains(URL_API_PROD_COMUNE_GENOVA)) target = createWebTargetCallProduzione(urlApi);
    else target = createWebTarget(urlApi);
    return target;
  }

  private ResteasyWebTarget getResteasyWebTargetMipGestionaliVerticali() throws BusinessException {
    return getResteasyWebTargetByUrl(BaseServiceImpl.API_MIP_GESTIONALI_VERTICALI);
  }

  public AvvisoPagamentoApi createAvvisoPagamentoApi() throws BusinessException {
    return getResteasyWebTargetMipGestionaliVerticali().proxy(AvvisoPagamentoApi.class);
  }

  public AvvisoPagamentoApi getAvvisoPagamentoApi() throws BusinessException {
    return new ApiServiziPagamentiMipVerticaliAvvisoPagamento(this.createAvvisoPagamentoApi());
  }

  public it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.DebitoApi
      createDebitoVerticaliApi() throws BusinessException {
    return getResteasyWebTargetMipGestionaliVerticali()
        .proxy(it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.DebitoApi.class);
  }

  public it.liguriadigitale.ponmetro.pagamenti.mip.verticali.apiclient.DebitoApi getDebitoApi()
      throws BusinessException {
    return new ApiServiziPagamentiMipVerticaliDebito(this.createDebitoVerticaliApi());
  }

  public RicevutaPagamentoApi createRicevutaPagamentoApi() throws BusinessException {
    return getResteasyWebTargetMipGestionaliVerticali().proxy(RicevutaPagamentoApi.class);
  }

  public RicevutaPagamentoApi getRicevutaPagamentoApi() throws BusinessException {
    return new ApiServiziPagamentiMipVerticaliRicevutaPagamento(this.createRicevutaPagamentoApi());
  }

  public TipologiaEntrataApi createTipologiaEntrataApi() throws BusinessException {
    return getResteasyWebTargetMipGestionaliVerticali().proxy(TipologiaEntrataApi.class);
  }

  public TipologiaEntrataApi getTipologiaEntrataApi() throws BusinessException {
    return new ApiServiziPagamentiMipVerticaliTipologiaEntrata(this.createTipologiaEntrataApi());
  }

  /* CP MIP GLOBALI */
  public RiepilogoPagamentiPagoPaApi createRiepilogoPagamentiPagoPaGlobaliApi()
      throws BusinessException {
    String urlApi = BaseServiceImpl.API_MIP_GLOBALI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(RiepilogoPagamentiPagoPaApi.class);
  }

  public RiepilogoPagamentiPagoPaApi getApiRiepilogoPagamentiPagoPaGlobali()
      throws BusinessException {
    return new ApiRiepilogoPagamentiPagoPaImpl(this.createRiepilogoPagamentiPagoPaGlobaliApi());
  }

  public DebitoApi createDebitoGlobaliApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_MIP_GLOBALI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(DebitoApi.class);
  }

  public DebitoApi getApiDebitoGlobali() throws BusinessException {
    return new ApiDebitoImpl(this.createDebitoGlobaliApi());
  }

  public RiepilogoPagamentiPagoPaApi createRiepilogoPagamentiPagoPaGlobaliApiMock()
      throws BusinessException {
    String urlApi = BaseServiceImpl.API_MIP_GLOBALI_MOCK;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(RiepilogoPagamentiPagoPaApi.class);
  }

  public RiepilogoPagamentiPagoPaApi getApiRiepilogoPagamentiPagoPaGlobaliMock()
      throws BusinessException {
    return new ApiRiepilogoPagamentiPagoPaImpl(this.createRiepilogoPagamentiPagoPaGlobaliApiMock());
  }

  public DebitoApi createDebitoApiGlobaliMock() throws BusinessException {
    String urlApi = BaseServiceImpl.API_MIP_GLOBALI_MOCK;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(DebitoApi.class);
  }

  public DebitoApi getApiDebitoGlobaliMock() throws BusinessException {
    return new ApiDebitoImpl(this.createDebitoApiGlobaliMock());
  }

  public TipologiaEntrataApi createTipologiaEntrataGlobaliApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_MIP_GESTIONALI_VERTICALI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(TipologiaEntrataApi.class);
  }

  public TipologiaEntrataApi getApiTipologiaEntrataGlobali() throws BusinessException {
    return new ApiTipologieEntrateImpl(this.createTipologiaEntrataGlobaliApi());
  }

  //

  // TEMPORANEO: non usa lo YAML
  public InpsNoYamlService getApiInpsSenzaYaml() throws BusinessException {
    return new InpsNoYamlImpl();
  }

  public EntitaConfigurazioneUtenteApi createEntitaConfigurazioneUtenteApi()
      throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_CONFIGURAZIONE);
    return target.proxy(EntitaConfigurazioneUtenteApi.class);
  }

  public EntitaConfigurazioneUtenteApi getApiEntitaConfigurazioneUtente() throws BusinessException {
    return new ApiEntitaConfigurazioneUtenteImpl(this.createEntitaConfigurazioneUtenteApi());
  }

  public AuditRestInterface getApiAudit() throws BusinessException {
    return new ApiServiziAuditImpl(this.createProxyAuditApi());
  }

  private AuditRestInterface createProxyAuditApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_AUDIT);
    return target.proxy(AuditRestInterface.class);
  }

  public SintesiApi getApiTariNetribeSintesi() throws BusinessException {
    return new TariSintesiImpl(this.createProxyTariNetribeSintesiApi());
  }

  private SintesiApi createProxyTariNetribeSintesiApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(SintesiApi.class);
  }

  public BollettinoApi getApiTariNetribeBollettino() throws BusinessException {
    return new TariBollettinoImpl(this.createProxyTariNtribeBollettinoApi());
  }

  private BollettinoApi createProxyTariNtribeBollettinoApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(BollettinoApi.class);
  }

  public AgevolazioneTariffariaTariApi getApiTariAgevolazioneTariffaria() throws BusinessException {
    return new TariAgevolazioneTariffariaImpl(this.createProxyTariAgevolazioneTariffaria());
  }

  private AgevolazioneTariffariaTariApi createProxyTariAgevolazioneTariffaria()
      throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(AgevolazioneTariffariaTariApi.class);
  }

  public RimborsoApi getApiTariNetribeRimborso() throws BusinessException {
    return new TariRimborsoImpl(this.createProxyTariNetribeRimborsoApi());
  }
  ;

  private RimborsoApi createProxyTariNetribeRimborsoApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_TARI_NETRIBE);
    return target.proxy(RimborsoApi.class);
  }

  public ConfigAvatarApi getApiConfigurazioneAvatar() throws BusinessException {
    return new ApiConfigurazioneAvatarImpl(this.createProxyCongfigurazioneAvatarApi());
  }

  private ConfigAvatarApi createProxyCongfigurazioneAvatarApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_AVATAR);
    return target.proxy(ConfigAvatarApi.class);
  }

  public ContattiUtenteApi getApiContattiUtente() throws BusinessException {
    return new ApiContattiUtenteImpl(this.createProxyCongfigurazioneContattiUtenteApi());
  }

  private ContattiUtenteApi createProxyCongfigurazioneContattiUtenteApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_CONTATTI_UTENTE);
    return target.proxy(ContattiUtenteApi.class);
  }

  public GeoworksHelperApi getApiGeoworksHelper() throws BusinessException {
    return new ApiGeoworksHelperImpl(this.createProxyCongfigurazioneGeoworksHelperApi());
  }

  private GeoworksHelperApi createProxyCongfigurazioneGeoworksHelperApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_CONTATTI_UTENTE);
    return target.proxy(GeoworksHelperApi.class);
  }

  public GenovaParcheggiHelperApi getApiGenovaParcheggiHelper() throws BusinessException {
    return new ApiGenovaParcheggiHelperImpl(
        this.createProxyCongfigurazioneGenovaParcheggiHelperApi());
  }

  private GenovaParcheggiHelperApi createProxyCongfigurazioneGenovaParcheggiHelperApi()
      throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_CONTATTI_UTENTE);
    return target.proxy(GenovaParcheggiHelperApi.class);
  }

  public it.liguriadigitale.ponmetro.tributiArgo.apiclient.DefaultApi createDefaultApiMock()
      throws BusinessException {
    String urlApi = BaseServiceImpl.API_TRIBUTI_ARGO_MOCK;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(it.liguriadigitale.ponmetro.tributiArgo.apiclient.DefaultApi.class);
  }

  public PostsApi getApiSegnalazioniPost() throws BusinessException {
    return new ApiPostsImpl(this.createProxyPostApi());
  }

  private PostsApi createProxyPostApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_SEGNALAZIONI);
    return target.proxy(PostsApi.class);
  }

  public StatisticsApi getApiSegnalazioniStat() throws BusinessException {
    return new ApiStatsImpl(this.createProxyStatApi());
  }

  private StatisticsApi createProxyStatApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_SEGNALAZIONI);
    return target.proxy(StatisticsApi.class);
  }

  public FascicoloDelContribuenteRestControllerApi getApiTributi() throws BusinessException {
    return new TributiImpl(this.createProxyTributiApi());
  }

  private FascicoloDelContribuenteRestControllerApi createProxyTributiApi()
      throws BusinessException {
    ResteasyWebTarget target = getResteasyWebTargetByUrl(BaseServiceImpl.API_IMU_RIMBORSI_ENG);
    return target.proxy(FascicoloDelContribuenteRestControllerApi.class);
  }

  public IstanzeVerbaliPlApi getApiIstanzeVerbaliPl() throws BusinessException {
    return new ApiServiziSupportoIstanzeVerbaliPlImpl(this.createProxyIstanzeVerbaliPlApi());
  }

  private IstanzeVerbaliPlApi createProxyIstanzeVerbaliPlApi() throws BusinessException {
    ResteasyWebTarget target =
        getResteasyWebTargetByUrl(BaseServiceImpl.API_SUPPORTO_ISTANZE_VERBALI_PL);
    return target.proxy(IstanzeVerbaliPlApi.class);
  }

  // MOCK per diete speciali

  public it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi
      getApiDieteSpecialiMock() throws BusinessException {
    return new ApiServiziRistorazionePortaleImpl(this.createProxyDieteSpecialiMockApi());
  }

  private it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi
      createProxyDieteSpecialiMockApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_DIETE_MOCK);
    return target.proxy(it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi.class);
  }

  public it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi getApiDieteSpeciali()
      throws BusinessException {
    return new ApiServiziRistorazionePortaleImpl(this.createProxyDieteSpecialiApi());
  }

  private it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi
      createProxyDieteSpecialiApi() throws BusinessException {
    ResteasyWebTarget target = getResteasyWebTargetByUrl(BaseServiceImpl.API_COM_GE_RISTORAZIONE);
    return target.proxy(it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi.class);
  }

  // APKAPPA
  public FamigliaApi getApiFamiglia() throws BusinessException {
    return new ApiFamigliaImpl(this.createProxyFamigliaApi());
  }

  private FamigliaApi createProxyFamigliaApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_ANAGRAFICI);
    return target.proxy(FamigliaApi.class);
  }

  public IndividuoApi getApiIndividuo() throws BusinessException {
    return new ApiIndividuoImpl(this.createProxyIndividuoApi());
  }

  private IndividuoApi createProxyIndividuoApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_ANAGRAFICI);
    return target.proxy(IndividuoApi.class);
  }

  public PingApi getApiPing() throws BusinessException {
    return new ApiPingImpl(this.createProxyPingApi());
  }

  private PingApi createProxyPingApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_ANAGRAFICI);
    return target.proxy(PingApi.class);
  }

  public PraticaApi getApiPratica() throws BusinessException {
    return new ApiPraticaImpl(this.createProxyPraticaApi());
  }

  private PraticaApi createProxyPraticaApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_ANAGRAFICI);
    return target.proxy(PraticaApi.class);
  }

  public ToponomasticaApi getApiToponomastica() throws BusinessException {
    return new ApiToponomasticaImpl(this.createProxyToponomasticaApi());
  }

  private ToponomasticaApi createProxyToponomasticaApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_ANAGRAFICI);
    return target.proxy(ToponomasticaApi.class);
  }

  public CedoleFdCApi getApiCedoleLibrarie() throws BusinessException {
    return new ApiCedoleLibrarieImpl(this.createProxyCedoleLibrarieApi());
  }

  private CedoleFdCApi createProxyCedoleLibrarieApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_CEDOLE_LIBRARIE);
    return target.proxy(CedoleFdCApi.class);
  }

  public PortaleFdcApi getApiUtilitaCedoleLibrarie() throws BusinessException {
    return new ApiUtilitaCedoleImpl(this.createProxyUtilitaCedoleLibrarieApi());
  }

  private PortaleFdcApi createProxyUtilitaCedoleLibrarieApi() throws BusinessException {
    ResteasyWebTarget target = createWebTarget(BaseServiceImpl.API_COM_GE_CEDOLE_LIBRARIE);
    return target.proxy(PortaleFdcApi.class);
  }

  private AuditApi createProxyCommissioniMensaApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_COMMISSIONI_MENSA;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(AuditApi.class);
  }

  public AuditApi getApiCommissioniMensa() throws BusinessException {
    return new ApiCommissioniMensa(createProxyCommissioniMensaApi());
  }

  private CertificatoStatoCivileApi createProxyCertificatoStatoCivileApi()
      throws BusinessException {
    String urlApi = BaseServiceImpl.API_ANAGRAFICI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(CertificatoStatoCivileApi.class);
  }

  public CertificatoStatoCivileApi getApiCertificatoStatoCivile() throws BusinessException {
    return new ApiCertificatiStatoCivile(createProxyCertificatoStatoCivileApi());
  }

  private CertificatoAnagrafeApi createProxyCertificatoAnagrafeApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_ANAGRAFICI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(CertificatoAnagrafeApi.class);
  }

  public CertificatoAnagrafeApi getApiCertificatoAnagrafe() throws BusinessException {
    return new ApiCertificatiAnagrafe(createProxyCertificatoAnagrafeApi());
  }

  public HomeRestInterface getApiHomePage() throws BusinessException {
    return new ApiServiziHomePageImpl(createProxyHomePageApi());
  }

  private HomeRestInterface createProxyHomePageApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_COM_GE_SCADENZE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(HomeRestInterface.class);
  }

  public CertificatiRestInterface getApiCertificatiBackend() throws BusinessException {
    return new ApiServiziCertificatiImpl(createProxyCertificatiBackendApi());
  }

  private CertificatiRestInterface createProxyCertificatiBackendApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BACKEND_CERTIFICATI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(CertificatiRestInterface.class);
  }

  private AmtApi createAbbonamentiAmtApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_ABBONAMENTI_AMT;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AmtApi.class);
  }

  public AmtApi getApiAbbonamentiAmt() throws BusinessException {
    return new ApiAbbonamentiAmtImpl(this.createAbbonamentiAmtApi());
  }

  private AmiuApi createPuntiTariApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_PUNTI_TARI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AmiuApi.class);
  }

  public AmiuApi getApiPuntiTari() throws BusinessException {
    return new ApiPuntiTariImpl(this.createPuntiTariApi());
  }

  PlastipremiaApi createPlastipremiaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_PLASTIPREMIA;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(PlastipremiaApi.class);
  }

  public PlastipremiaApi getApiPlastipremia() throws BusinessException {
    return new ApiPlasTiPremiaImpl(this.createPlastipremiaApi());
  }

  GeparkApi createGenovaParcheggiApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_GENOVA_PARCHEGGI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(GeparkApi.class);
  }

  public GeparkApi getApiGenovaParcheggi() throws BusinessException {
    return new ApiGenovaParcheggiImpl(this.createGenovaParcheggiApi());
  }

  private GloboBackendInterface createProxyGloboBackendApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_BACKEND_CERTIFICATI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(GloboBackendInterface.class);
  }

  public GloboBackendInterface getApiServiziGloboBackend() throws BusinessException {
    return new ApiServiziGloboBackend(this.createProxyGloboBackendApi());
  }

  DomandaApi createDomandaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_PERMESSI_PERSONALIZZATI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(DomandaApi.class);
  }

  public DomandaApi getApiDomanda() throws BusinessException {
    return new ApiDomandaPermessiPersonalizzatiImpl(this.createDomandaApi());
  }

  SeggielettoraliApi createSeggi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_SEGGI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(SeggielettoraliApi.class);
  }

  public ApiSeggiImpl getApiSeggi() throws BusinessException {
    return new ApiSeggiImpl(this.createSeggi());
  }

  UtilsApi createUtilsApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_PERMESSI_PERSONALIZZATI;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(UtilsApi.class);
  }

  public UtilsApi getApiUtils() throws BusinessException {
    return new ApiUtilsPermessiPersonalizzatiImpl(this.createUtilsApi());
  }

  private AllerteareearischioApi createAllerteZonaRossaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_ALLERTE_ZONA_ROSSA;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AllerteareearischioApi.class);
  }

  public AllerteareearischioApi getApiAllerteZonaRossa() throws BusinessException {
    return new ApiAllerteZoneRosseImpl(this.createAllerteZonaRossaApi());
  }

  private RegistrazioneServiziCortesiaApi createAllerteCortesiaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_ALLERTE_CORTESIA;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(RegistrazioneServiziCortesiaApi.class);
  }

  private GeorefToponomasticaApi createGeorefToponomasticaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_GEOREF_TOPONOMASTICA;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(GeorefToponomasticaApi.class);
  }

  public GeorefToponomasticaApi getApiGeorefToponomastica() throws BusinessException {
    return new ApiGeorefToponomasticaImpl(this.createGeorefToponomasticaApi());
  }

  public RegistrazioneServiziCortesiaApi getApiAllerteCortesia() throws BusinessException {
    return new ApiAllerteCortesiaImpl(this.createAllerteCortesiaApi());
  }

  public AllerteCortesiaNoYamlService getApiAllerteCortesiaNoYaml() throws BusinessException {
    return new AllerteCortesiaNoYamlImpl();
  }

  private ArteApi createArteApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_ARTE;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(ArteApi.class);
  }

  public ArteApi getApiArte() throws BusinessException {
    return new ApiArteImpl(this.createArteApi());
  }

  private CaitelApi createCaitelApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_CAITEL;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(CaitelApi.class);
  }

  public CaitelApi getApiCaitel() throws BusinessException {
    return new ApiCaitelImpl(this.createCaitelApi());
  }

  private TeleriscaldamentoApi createTeleriscaldamentoApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_TELERISCALDAMENTO;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(TeleriscaldamentoApi.class);
  }

  public TeleriscaldamentoApi getApiTeleriscaldamento() throws BusinessException {
    return new ApiTeleriscaldamentoImpl(this.createTeleriscaldamentoApi());
  }

  private InserimentoRichiestaBonusApi createTeleriscaldamentoIrenApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_TELERISCALDAMENTO_IREN;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(InserimentoRichiestaBonusApi.class);
  }

  public InserimentoRichiestaBonusApi getApiTeleriscaldamentoIren() throws BusinessException {
    return new ApiTeleriscaldamentoIrenImpl(this.createTeleriscaldamentoIrenApi());
  }

  private PericolositaApi createPericolositaApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_PERICOLOSITA;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(PericolositaApi.class);
  }

  public PericolositaApi getApiPericolosita() throws BusinessException {
    return new ApiPericolositaImpl(this.createPericolositaApi());
  }

  public AlimsApi getApiArpalAlims() throws BusinessException {
    return new ApiAlims(this.createApiArpalAlims());
  }

  private AlimsApi createApiArpalAlims() throws BusinessException {
    String urlapi = BaseServiceImpl.API_ARPAL;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(AlimsApi.class);
  }

  public SimpaApi getApiArpalSimpa() throws BusinessException {
    return new ApiSimpa(this.createApiArpalSimpa());
  }

  private SimpaApi createApiArpalSimpa() throws BusinessException {
    String urlapi = BaseServiceImpl.API_ARPAL;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(SimpaApi.class);
  }

  private FascicoloDelContribuenteTariRestControllerApi createTariEngApi()
      throws BusinessException {
    String urlapi = BaseServiceImpl.API_TARI_ENG;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(FascicoloDelContribuenteTariRestControllerApi.class);
  }

  public FascicoloDelContribuenteTariRestControllerApi getApiTariEng() throws BusinessException {
    return new ApiTariEngImpl(this.createTariEngApi());
  }

  private RimborsiApi createTariRimborsiEngApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_TARI_RIMBORSI_ENG;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(RimborsiApi.class);
  }

  public RimborsiApi getApiTariRimborsiEng() throws BusinessException {
    return new ApiTariRimborsiEngImpl(this.createTariRimborsiEngApi());
  }

  private BorsedistudioApi createBorseDiStudioApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_BORSE_STUDIO;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(BorsedistudioApi.class);
  }

  public BorsedistudioApi getApiBorseDiStudio() throws BusinessException {
    return new ApiBorseDiStudioImpl(this.createBorseDiStudioApi());
  }

  private InpsmodiApi createInpsModiApi() throws BusinessException {
    String urlapi = BaseServiceImpl.API_COM_GE_INPS;
    ResteasyWebTarget target = createWebTarget(urlapi);
    return target.proxy(InpsmodiApi.class);
  }

  public InpsmodiApi getApiInpsModi() throws BusinessException {
    return new ApiInpsModiImpl(this.createInpsModiApi());
  }

  public HelpCzRMApi getApiHelpCzRM() throws BusinessException {
    return new ApiHelpCzRMImp(createProxyHelpCzRMApi());
  }

  private HelpCzRMApi createProxyHelpCzRMApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_COM_GE_CONFIGURAZIONE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(HelpCzRMApi.class);
  }

  public SegnalazioniCzrmApi getApiSegnalazioniCzRM() throws BusinessException {
    return new ApiSegnalazioniCzrm(createProxySegnalazioniCzrmApi());
  }

  // TODO fare api con end point
  private SegnalazioniCzrmApi createProxySegnalazioniCzrmApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_COM_GE_CONFIGURAZIONE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(SegnalazioniCzrmApi.class);
  }

  public OggettiSmarritiApi getApiOggettiSmarriti() throws BusinessException {
    return new ApiOggettiSmarritiImpl(createProxyOggettiSmarritiApi());
  }

  private OggettiSmarritiApi createProxyOggettiSmarritiApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_OGGETTI_SMARRITI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(OggettiSmarritiApi.class);
  }

  public DefaultApi getApiSegnalazioneDanniBeniPrivati() throws BusinessException {
    return new ApiSegnalazioneDanniBeniPrivatiImpl(createProxySegnalazioneDanniBeniPrivati());
  }

  // TODO: spostare in ServiceLocatorUnoAccenture ??
  private DefaultApi createProxySegnalazioneDanniBeniPrivati() throws BusinessException {
    String urlApi = BaseServiceImpl.API_ACCENTURE;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(DefaultApi.class);
  }

  public AppRequestFormsApi getApiAppRequestForms() throws BusinessException {
    return new ApiGeoworksAppRequestFormImpl(createProxyAppRequestFormsApi());
  }

  private AppRequestFormsApi createProxyAppRequestFormsApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_GEOWORKS;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(AppRequestFormsApi.class);
  }

  public AppRequestFormsSearchApi getApiAppRequestFormsSearch() throws BusinessException {
    return new ApiGeoworksAppRequestFormsSearchImpl(createProxyAppRequestFormsSearchApi());
  }

  private AppRequestFormsSearchApi createProxyAppRequestFormsSearchApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_GEOWORKS;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(AppRequestFormsSearchApi.class);
  }

  public DownloadApi getApiDownload() throws BusinessException {
    return new ApiGeoworksDownloadImpl(createProxyDownloadApi());
  }

  private DownloadApi createProxyDownloadApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_GEOWORKS;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(DownloadApi.class);
  }

  public RicorsiAlPrefettoApi getApiRicorsiAlPrefetto() throws BusinessException {
    return new ApiRicorsiAlPrefettoImpl(createProxyRicorsiAlPrefettoApi());
  }

  private RicorsiAlPrefettoApi createProxyRicorsiAlPrefettoApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_VERBALI_CONTRAVVENZIONI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(RicorsiAlPrefettoApi.class);
  }

  /* CONTROLLI ALBI DA VEDERE */
  public ControlliAlbiElettoraliApi createServiziControlliAlbiApi() throws BusinessException {
    String urlApi = BaseServiceImpl.API_CONTROLLI_ALBI;
    ResteasyWebTarget target = createWebTarget(urlApi);
    return target.proxy(ControlliAlbiElettoraliApi.class);
  }

  public ControlliAlbiElettoraliApi getApiControlliAlbi() throws BusinessException {
    return new ApiControlliAlbiImpl(this.createServiziControlliAlbiApi());
  }
}
