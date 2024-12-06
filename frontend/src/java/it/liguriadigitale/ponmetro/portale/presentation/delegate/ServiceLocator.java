package it.liguriadigitale.ponmetro.portale.presentation.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Problem;
import it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.impl.ServiziControlliAlbiImpl;
import it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.impl.ServiziIscrizioniAlbiImpl;
import it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.service.ServiziControlliAlbiService;
import it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.service.ServiziIscrizioniAlbiService;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.impl.ServiziDomandeMatrimonioImpl;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.service.ServiziDomandeMatrimonioService;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazionedannibeniprivati.impl.ServiziSegnalazioneDanniBeniPrivatiImpl;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazionedannibeniprivati.service.ServiziSegnalazioneDanniBeniPrivati;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.impl.ServiziRichiestaAssistenzaImpl;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.impl.ServiziSegnalazioniCzrmImpl;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.service.ServiziRichiestaAssistenzaService;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.service.ServiziSegnalazioniCzrmService;
import it.liguriadigitale.ponmetro.portale.business.accenture.trasportobambinidisabili.impl.ServiziTrasportoBambiniDisabiliImpl;
import it.liguriadigitale.ponmetro.portale.business.accenture.trasportobambinidisabili.service.ServiziTrasportoBambiniDisabiliService;
import it.liguriadigitale.ponmetro.portale.business.accessoaivarchi.impl.ServiziAccessoAiVarchiImpl;
import it.liguriadigitale.ponmetro.portale.business.accessoaivarchi.service.ServiziAccessoAiVarchiService;
import it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.impl.ServiziAllerteCortesiaImpl;
import it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.impl.ServiziGeorefToponomasticaImpl;
import it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.service.ServiziAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.business.allerte.cortesia.service.ServiziGeorefToponomastica;
import it.liguriadigitale.ponmetro.portale.business.allerte.pericolosita.impl.ServiziPericolositaImpl;
import it.liguriadigitale.ponmetro.portale.business.allerte.pericolosita.service.ServiziPericolosita;
import it.liguriadigitale.ponmetro.portale.business.allerte.zonarossa.impl.ServiziAllerteZonaRossaImpl;
import it.liguriadigitale.ponmetro.portale.business.allerte.zonarossa.service.ServiziAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.business.anagrafici.impl.ServiziAnagraficiImpl;
import it.liguriadigitale.ponmetro.portale.business.anagrafici.service.ServiziAnagraficiService;
import it.liguriadigitale.ponmetro.portale.business.arpal.impl.ServiziArpalImpl;
import it.liguriadigitale.ponmetro.portale.business.arpal.service.ServiziArpal;
import it.liguriadigitale.ponmetro.portale.business.arte.impl.ServiziArteImpl;
import it.liguriadigitale.ponmetro.portale.business.arte.service.ServiziArte;
import it.liguriadigitale.ponmetro.portale.business.audit.ServiziAuditImpl;
import it.liguriadigitale.ponmetro.portale.business.audit.ServiziAuditService;
import it.liguriadigitale.ponmetro.portale.business.biblioteche.impl.ServiziBibliotecheImpl;
import it.liguriadigitale.ponmetro.portale.business.biblioteche.service.ServiziBibliotecheService;
import it.liguriadigitale.ponmetro.portale.business.borsedistudio.impl.ServiziBorseDiStudioImpl;
import it.liguriadigitale.ponmetro.portale.business.borsedistudio.service.ServiziBorseDiStudioService;
import it.liguriadigitale.ponmetro.portale.business.cedole.GestioneCedoleLibrarieImpl;
import it.liguriadigitale.ponmetro.portale.business.cedole.GestioneCedoleLibrarieService;
import it.liguriadigitale.ponmetro.portale.business.certificati.impl.anagrafe.CertificatiAnagrafeImpl;
import it.liguriadigitale.ponmetro.portale.business.certificati.impl.statocivile.CertificatiStatoCivileImpl;
import it.liguriadigitale.ponmetro.portale.business.certificati.service.ServiziCertificatiAnagrafe;
import it.liguriadigitale.ponmetro.portale.business.certificati.service.ServiziCertificatiStatoCivile;
import it.liguriadigitale.ponmetro.portale.business.commissionimensa.impl.CommissioniMensaImpl;
import it.liguriadigitale.ponmetro.portale.business.commissionimensa.service.CommissioniMensaService;
import it.liguriadigitale.ponmetro.portale.business.configurazione.impl.ConfigurazioneHomePageImpl;
import it.liguriadigitale.ponmetro.portale.business.configurazione.impl.ConfigurazioneImpl;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneHomePageInterface;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.business.demografico.impl.DemograficoImpl;
import it.liguriadigitale.ponmetro.portale.business.demografico.service.DemograficoInterface;
import it.liguriadigitale.ponmetro.portale.business.domandealloggio.impl.ServiziDomandeAlloggioImpl;
import it.liguriadigitale.ponmetro.portale.business.domandealloggio.service.ServiziDomandeAlloggioService;
import it.liguriadigitale.ponmetro.portale.business.edilizia.abitabilita.impl.ServiziEdiliziaAbitabilitaImpl;
import it.liguriadigitale.ponmetro.portale.business.edilizia.abitabilita.service.ServiziEdiliziaAbitabilita;
import it.liguriadigitale.ponmetro.portale.business.edilizia.condono.impl.ServiziEdiliziaCondonoImpl;
import it.liguriadigitale.ponmetro.portale.business.edilizia.condono.service.ServiziEdiliziaCondono;
import it.liguriadigitale.ponmetro.portale.business.edilizia.pratiche.impl.ServiziEdiliziaPraticheImpl;
import it.liguriadigitale.ponmetro.portale.business.edilizia.pratiche.service.ServiziEdiliziaPratiche;
import it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.impl.ServiziGenovaParcheggiHelperImpl;
import it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.impl.ServiziGenovaParcheggiImpl;
import it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.service.ServiziGenovaParcheggi;
import it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.service.ServiziGenovaParcheggiHelperService;
import it.liguriadigitale.ponmetro.portale.business.geoserver.impl.GeoserverImpl;
import it.liguriadigitale.ponmetro.portale.business.geoserver.service.GeoserverInterface;
import it.liguriadigitale.ponmetro.portale.business.geoworks.impl.ServiziGeoworksHelperImpl;
import it.liguriadigitale.ponmetro.portale.business.geoworks.impl.ServiziGeoworksImpl;
import it.liguriadigitale.ponmetro.portale.business.geoworks.service.ServiziGeoworksHelperService;
import it.liguriadigitale.ponmetro.portale.business.geoworks.service.ServiziGeoworksService;
import it.liguriadigitale.ponmetro.portale.business.globo.GloboFrontendImpl;
import it.liguriadigitale.ponmetro.portale.business.globo.GloboFrontendInterface;
import it.liguriadigitale.ponmetro.portale.business.helpCzRm.HelpCzRMImpl;
import it.liguriadigitale.ponmetro.portale.business.helpCzRm.IHelpCzRMService;
import it.liguriadigitale.ponmetro.portale.business.impiantitermici.impl.ServiziImpiantiTermiciImpl;
import it.liguriadigitale.ponmetro.portale.business.impiantitermici.service.ServiziImpiantiTermici;
import it.liguriadigitale.ponmetro.portale.business.imu.impl.ServiziImuImpl;
import it.liguriadigitale.ponmetro.portale.business.imu.service.ServiziImu;
import it.liguriadigitale.ponmetro.portale.business.inps.impl.ServiziInpsImpl;
import it.liguriadigitale.ponmetro.portale.business.inps.service.ServiziInpsService;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.ServiziInpsModiImpl;
import it.liguriadigitale.ponmetro.portale.business.login.impl.GestioneLoginImpl;
import it.liguriadigitale.ponmetro.portale.business.login.services.GestioneLoginInterface;
import it.liguriadigitale.ponmetro.portale.business.messaggi.impl.ServiziMessaggiImpl;
import it.liguriadigitale.ponmetro.portale.business.messaggi.service.ServiziMessaggiService;
import it.liguriadigitale.ponmetro.portale.business.mieiabbonamentiamt.impl.ServiziAbbonamentiAmtImpl;
import it.liguriadigitale.ponmetro.portale.business.mieiabbonamentiamt.service.ServiziAbbonamentiAmtService;
import it.liguriadigitale.ponmetro.portale.business.mieimezzi.impl.ServiziMieiMezziImpl;
import it.liguriadigitale.ponmetro.portale.business.mieimezzi.service.ServiziMieiMezziService;
import it.liguriadigitale.ponmetro.portale.business.mieistanze.impl.ServiziMieIstanzeImpl;
import it.liguriadigitale.ponmetro.portale.business.mieistanze.service.ServiziMieIstanzeService;
import it.liguriadigitale.ponmetro.portale.business.mieiverbali.impl.ServiziMieiVerbaliImpl;
import it.liguriadigitale.ponmetro.portale.business.mieiverbali.service.ServiziMieiVerbaliService;
import it.liguriadigitale.ponmetro.portale.business.mipGlobali.impl.ServiziMipGlobaliImpl;
import it.liguriadigitale.ponmetro.portale.business.mipGlobali.service.ServiziMipGlobaliService;
import it.liguriadigitale.ponmetro.portale.business.mipgestionaliverticali.impl.ServiziMipGestionaliVerticaliImpl;
import it.liguriadigitale.ponmetro.portale.business.mipgestionaliverticali.service.ServiziMipGestionaliVerticaliService;
import it.liguriadigitale.ponmetro.portale.business.oggettismarriti.impl.ServiziOggettiSmarritiImpl;
import it.liguriadigitale.ponmetro.portale.business.oggettismarriti.service.ServiziOggettiSmarritiService;
import it.liguriadigitale.ponmetro.portale.business.pagopa.GestionePagoPAImpl;
import it.liguriadigitale.ponmetro.portale.business.pagopa.GestionePagoPAInterface;
import it.liguriadigitale.ponmetro.portale.business.permessipersonalizzati.impl.ServiziPermessiPersonalizzatiImpl;
import it.liguriadigitale.ponmetro.portale.business.permessipersonalizzati.service.ServiziPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.business.plastipremia.impl.ServiziPlasTiPremiaImpl;
import it.liguriadigitale.ponmetro.portale.business.plastipremia.service.ServiziPlasTiPremiaService;
import it.liguriadigitale.ponmetro.portale.business.puntitari.impl.ServiziPuntiTariImpl;
import it.liguriadigitale.ponmetro.portale.business.puntitari.service.ServiziPuntiTariService;
import it.liguriadigitale.ponmetro.portale.business.ricorsialprefetto.impl.ServiziRicorsiAlPrefettoImpl;
import it.liguriadigitale.ponmetro.portale.business.ricorsialprefetto.service.ServiziRicorsiAlPrefettoService;
import it.liguriadigitale.ponmetro.portale.business.ristorazione.impl.ServiziRistorazioneImpl;
import it.liguriadigitale.ponmetro.portale.business.ristorazione.service.ServiziRistorazioneService;
import it.liguriadigitale.ponmetro.portale.business.scadenze.impl.ServiziScadenzeImpl;
import it.liguriadigitale.ponmetro.portale.business.scadenze.service.ServiziScadenzeService;
import it.liguriadigitale.ponmetro.portale.business.seggielettorali.impl.ServiziSeggiElettoraliImpl;
import it.liguriadigitale.ponmetro.portale.business.seggielettorali.service.ServiziSeggiElettorali;
import it.liguriadigitale.ponmetro.portale.business.segnalazioni.impl.ServiziSegnalazioniImpl;
import it.liguriadigitale.ponmetro.portale.business.segnalazioni.service.ServiziSegnalazioniService;
import it.liguriadigitale.ponmetro.portale.business.segnalazionitraffico.impl.ServiziSegnalazioniTrafficoImpl;
import it.liguriadigitale.ponmetro.portale.business.segnalazionitraffico.service.ServiziSegnalazioniTrafficoService;
import it.liguriadigitale.ponmetro.portale.business.supportoistanzeverbalipl.impl.ServiziSupportoIstanzeVerbaliPLImpl;
import it.liguriadigitale.ponmetro.portale.business.supportoistanzeverbalipl.service.ServiziSupportoIstanzeVerbaliPLInterface;
import it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.impl.ServiziTeleriscaldamentoImpl;
import it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.impl.ServiziTeleriscaldamentoIrenImpl;
import it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.service.ServiziTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.service.ServiziTeleriscaldamentoIren;
import it.liguriadigitale.ponmetro.portale.business.tributi.impl.ServiziTributiImpl;
import it.liguriadigitale.ponmetro.portale.business.tributi.imu.impl.ServiziImuEngImpl;
import it.liguriadigitale.ponmetro.portale.business.tributi.imu.service.ServiziImuEngService;
import it.liguriadigitale.ponmetro.portale.business.tributi.service.ServiziTributiInterface;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarieng.impl.ServiziTariEngImpl;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarieng.service.ServiziTariEngService;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.impl.ServiziTariContributoImpl;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.impl.ServiziTariImpl;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.impl.ServiziTariRimborsiImpl;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service.ServiziTariContributo;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service.ServiziTariRimborsiService;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service.ServiziTariService;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziConStatusPage;
import javax.ws.rs.core.Response;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiceLocator {
  public static ServiceLocator getInstance() {
    return new ServiceLocator();
  }

  public GestioneLoginInterface getGestioneLogin() throws BusinessException {
    return new GestioneLoginImpl();
  }

  public DemograficoInterface getServizioDemografico() throws BusinessException {
    return new DemograficoImpl();
  }

  public ServiziRistorazioneService getServiziRistorazione() {
    return new ServiziRistorazioneImpl();
  }

  public ConfigurazioneInterface getServiziConfigurazione() {
    return new ConfigurazioneImpl();
  }

  public ServiziInpsService getServiziINPS() {
    return new ServiziInpsImpl();
  }

  public ServiziInpsModiImpl getServiziINPSModi() {
    return new ServiziInpsModiImpl();
  }

  public ServiziSegnalazioniTrafficoService getServiziSegnalazioneTraffico() {
    return new ServiziSegnalazioniTrafficoImpl();
  }

  public ServiziMessaggiService getServiziMessaggi() {
    return new ServiziMessaggiImpl();
  }

  public ServiziMieiMezziService getServiziMieiMezzi() {
    return new ServiziMieiMezziImpl();
  }

  public ServiziMieiVerbaliService getServiziMieiVerbali() {
    return new ServiziMieiVerbaliImpl();
  }

  public ServiziMieIstanzeService getServiziMieIstanze() {
    return new ServiziMieIstanzeImpl();
  }

  public ServiziScadenzeService getServiziScadenze() {
    return new ServiziScadenzeImpl();
  }

  public ServiziMipGestionaliVerticaliService getServiziMipVerticali() {
    return new ServiziMipGestionaliVerticaliImpl();
  }

  public ServiziMipGlobaliService getServiziMipGlobali() {
    return new ServiziMipGlobaliImpl();
  }

  public ServiziAuditService getServiziAudit() {
    return new ServiziAuditImpl();
  }

  public ServiziTariService getServiziTariNetribe() {
    return new ServiziTariImpl();
  }

  public ServiziTariRimborsiService getServiziTariRimborsiNetribe() {
    return new ServiziTariRimborsiImpl();
  }

  public GestionePagoPAInterface getServiziPagoPA() {
    return new GestionePagoPAImpl();
  }

  public ServiziBibliotecheService getServiziBiblioteche() {
    return new ServiziBibliotecheImpl();
  }

  public ServiziTributiInterface getServiziQuadroTributario() {
    return new ServiziTributiImpl();
  }

  public ServiziSegnalazioniService getServiziSegnalazioni() {
    return new ServiziSegnalazioniImpl();
  }

  public ServiziAnagraficiService getServiziAnagrafici() {
    return new ServiziAnagraficiImpl();
  }

  public CommissioniMensaService getServiziCommissioniMensa() {
    return new CommissioniMensaImpl();
  }

  public GestioneCedoleLibrarieService getCedoleLibrarie() {
    return new GestioneCedoleLibrarieImpl();
  }

  public ServiziCertificatiAnagrafe getCertificatiAnagrafe() {
    return new CertificatiAnagrafeImpl();
  }

  public ServiziCertificatiStatoCivile getCertificatiStatoCivile() {
    return new CertificatiStatoCivileImpl();
  }

  public ConfigurazioneHomePageInterface getServiziHomePage() {
    return new ConfigurazioneHomePageImpl();
  }

  public GeoserverInterface getServiziGeoserver() {
    return new GeoserverImpl();
  }

  public ServiziSupportoIstanzeVerbaliPLInterface getServiziSupportoIstanzeVerbaliPL() {
    return new ServiziSupportoIstanzeVerbaliPLImpl();
  }

  public ServiziAbbonamentiAmtService getServiziAbbonamentiAmt() {
    Problem problem = new Problem();

    // per prova ora
    // problem.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
    problem.setStatus(Response.Status.OK.getStatusCode());
    problem.setDetail("Details...");
    problem.setTitle("Title...");
    //

    if (problem.getStatus() == Response.Status.OK.getStatusCode()) {
      return new ServiziAbbonamentiAmtImpl();
    } else {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziConStatusPage(
              "abbonamenti AMT", problem.getTitle(), problem.getDetail()));
    }
  }

  public ServiziPuntiTariService getServiziPuntiTari() {
    return new ServiziPuntiTariImpl();
  }

  public ServiziPlasTiPremiaService getServiziPlasTiPremia() {
    return new ServiziPlasTiPremiaImpl();
  }

  public ServiziPermessiPersonalizzati getServiziPermessiPersonalizzati() {
    return new ServiziPermessiPersonalizzatiImpl();
  }

  public GloboFrontendInterface getGlobo() {
    return new GloboFrontendImpl();
  }

  public ServiziGenovaParcheggi getServiziGenovaParcheggi() {
    return new ServiziGenovaParcheggiImpl();
  }

  public ServiziSeggiElettorali getServiziSeggiElettorali() {
    return new ServiziSeggiElettoraliImpl();
  }

  public ServiziAllerteZonaRossa getServiziAllerteZonaRossa() {
    return new ServiziAllerteZonaRossaImpl();
  }

  public ServiziAllerteCortesia getServiziAllerteCortesia() {
    return new ServiziAllerteCortesiaImpl();
  }

  public ServiziGeorefToponomastica getServiziGeorefToponomastica() {
    return new ServiziGeorefToponomasticaImpl();
  }

  public ServiziArte getServiziArte() {
    return new ServiziArteImpl();
  }

  public ServiziArpal getServiziArpal() {
    return new ServiziArpalImpl();
  }

  public ServiziImu getServiziImu() {
    return new ServiziImuImpl();
  }

  public ServiziEdiliziaAbitabilita getServiziEdiliziaAbitabilita() {
    return new ServiziEdiliziaAbitabilitaImpl();
  }

  public ServiziEdiliziaCondono getServiziEdiliziaCondono() {
    return new ServiziEdiliziaCondonoImpl();
  }

  public ServiziEdiliziaPratiche getServiziEdiliziaPratiche() {
    return new ServiziEdiliziaPraticheImpl();
  }

  public ServiziImpiantiTermici getServiziImpiantiTermici() {
    return new ServiziImpiantiTermiciImpl();
  }

  public ServiziTeleriscaldamento getServiziTeleriscaldamento() {
    return new ServiziTeleriscaldamentoImpl();
  }

  public ServiziTeleriscaldamentoIren getServiziTeleriscaldamentoIren() {
    return new ServiziTeleriscaldamentoIrenImpl();
  }

  public ServiziPericolosita getServiziPericolosita() {
    return new ServiziPericolositaImpl();
  }

  public ServiziTariEngService getServiziTariEng() {
    return new ServiziTariEngImpl();
  }

  public ServiziBorseDiStudioService getServiziBorseDiStudio() {
    return new ServiziBorseDiStudioImpl();
  }

  public ServiziImuEngService getServiziImuEng() {
    return new ServiziImuEngImpl();
  }

  public IHelpCzRMService getHelpCzRMService() {
    return new HelpCzRMImpl();
  }

  public ServiziTariContributo getServiziContributoTari() {
    return new ServiziTariContributoImpl();
  }

  public ServiziOggettiSmarritiService getServiziOggettiSmarriti() {
    return new ServiziOggettiSmarritiImpl();
  }

  public ServiziSegnalazioniCzrmService getSegnalazioniAccenture() {
    return new ServiziSegnalazioniCzrmImpl();
  }

  public ServiziDomandeMatrimonioService getMatrimoniAccenture() {
    return new ServiziDomandeMatrimonioImpl();
  }

  public ServiziRichiestaAssistenzaService getRichiestaAssistenza() {
    return new ServiziRichiestaAssistenzaImpl();
  }

  public ServiziRicorsiAlPrefettoService getRicorsiAlPrefetto() {
    return new ServiziRicorsiAlPrefettoImpl();
  }

  public ServiziGeoworksService getServiziGeoworks() {
    return new ServiziGeoworksImpl();
  }

  public ServiziGeoworksHelperService getServiziGeoworksHelper() {
    return new ServiziGeoworksHelperImpl();
  }

  public ServiziGenovaParcheggiHelperService getServiziGenovaParcheggiHelper() {
    return new ServiziGenovaParcheggiHelperImpl();
  }

  public ServiziAccessoAiVarchiService getServiziAccessoAiVarchi() {
    return new ServiziAccessoAiVarchiImpl();
  }

  public ServiziDomandeAlloggioService getServiziDomandeAlloggio() {
    return new ServiziDomandeAlloggioImpl();
  }

  public ServiziTrasportoBambiniDisabiliService getTrasportoDisabiliAccenture() {
    return new ServiziTrasportoBambiniDisabiliImpl();
  }

  public ServiziIscrizioniAlbiService getIscrizioniAlbi() {
    return new ServiziIscrizioniAlbiImpl();
  }

  public ServiziControlliAlbiService getControlloAlbi() {
    return new ServiziControlliAlbiImpl();
  }

  public ServiziSegnalazioneDanniBeniPrivati getSegnalazioneDanniBeniPrivati() {
    return new ServiziSegnalazioneDanniBeniPrivatiImpl();
  }
}
