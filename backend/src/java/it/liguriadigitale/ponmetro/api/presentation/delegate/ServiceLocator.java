package it.liguriadigitale.ponmetro.api.presentation.delegate;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.anonimizza.impl.AnonimizzaImpl;
import it.liguriadigitale.ponmetro.api.business.anonimizza.service.AnonimizzaInterface;
import it.liguriadigitale.ponmetro.api.business.audit.impl.AuditBackendImpl;
import it.liguriadigitale.ponmetro.api.business.audit.service.AuditBackendInterface;
import it.liguriadigitale.ponmetro.api.business.breadcrumb.impl.FdCBreadcrumbImpl;
import it.liguriadigitale.ponmetro.api.business.breadcrumb.service.FdCBreadcrumbService;
import it.liguriadigitale.ponmetro.api.business.categoriecatastali.impl.CategorieCatastaliImpl;
import it.liguriadigitale.ponmetro.api.business.categoriecatastali.service.ICategorieCatastali;
import it.liguriadigitale.ponmetro.api.business.certificati.CertificatiImpl;
import it.liguriadigitale.ponmetro.api.business.certificati.CertificatiInterface;
import it.liguriadigitale.ponmetro.api.business.configurazione.impl.ConfigurazioneImpl;
import it.liguriadigitale.ponmetro.api.business.configurazione.impl.ContattiImpl;
import it.liguriadigitale.ponmetro.api.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.api.business.configurazione.service.ContattiService;
import it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.impl.EntitaConfigurazioneUtenteImpl;
import it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.impl.ServiziHomePageImpl;
import it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.service.EntitaConfigurazioneUtenteInterface;
import it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.service.ServiziHomePageInterface;
import it.liguriadigitale.ponmetro.api.business.famiglia.impl.GestioneNucleoFamigliareImpl;
import it.liguriadigitale.ponmetro.api.business.famiglia.service.GestioneNucleoFamigliareInterface;
import it.liguriadigitale.ponmetro.api.business.genovaparcheggi.impl.GenovaParcheggiHelperImpl;
import it.liguriadigitale.ponmetro.api.business.genovaparcheggi.service.GenovaParcheggiHelperService;
import it.liguriadigitale.ponmetro.api.business.geoworks.impl.GeoworksImpl;
import it.liguriadigitale.ponmetro.api.business.geoworks.service.GeoworksService;
import it.liguriadigitale.ponmetro.api.business.globo.ServiziGloboImpl;
import it.liguriadigitale.ponmetro.api.business.globo.ServiziGloboInterface;
import it.liguriadigitale.ponmetro.api.business.helpczrm.impl.HelpCzRMService;
import it.liguriadigitale.ponmetro.api.business.helpczrm.service.IHelpCzRMService;
import it.liguriadigitale.ponmetro.api.business.messaggi.impl.MessaggiImpl;
import it.liguriadigitale.ponmetro.api.business.messaggi.impl.MessaggiUtenteImpl;
import it.liguriadigitale.ponmetro.api.business.messaggi.service.MessaggiInterface;
import it.liguriadigitale.ponmetro.api.business.messaggi.service.MessaggiUtenteInterface;
import it.liguriadigitale.ponmetro.api.business.privacy.impl.PrivacyImpl;
import it.liguriadigitale.ponmetro.api.business.privacy.service.PrivacyInterface;
import it.liguriadigitale.ponmetro.api.business.scadenze.impl.ScadenzeImpl;
import it.liguriadigitale.ponmetro.api.business.scadenze.impl.ScadenzePersonalizzateImpl;
import it.liguriadigitale.ponmetro.api.business.scadenze.service.ScadenzeInterface;
import it.liguriadigitale.ponmetro.api.business.scadenze.service.ScadenzePersonalizzateInterface;
import it.liguriadigitale.ponmetro.api.business.supportoistanzeverbalipl.impl.SupportoIstanzeVerbaliPLImpl;
import it.liguriadigitale.ponmetro.api.business.supportoistanzeverbalipl.service.SupportoIstanzeVerbaliPLInterface;
import it.liguriadigitale.ponmetro.api.business.teleriscaldamento.impl.TeleriscaldamentoImpl;
import it.liguriadigitale.ponmetro.api.business.teleriscaldamento.service.TeleriscaldamentoInterface;

public class ServiceLocator {

  public static ServiceLocator getInstance() {
    return new ServiceLocator();
  }

  public AnonimizzaInterface getAnonimizza() throws BusinessException {
    return new AnonimizzaImpl();
  }

  public PrivacyInterface getPrivacy() throws BusinessException {
    return new PrivacyImpl();
  }

  public GestioneNucleoFamigliareInterface getFamily() throws BusinessException {
    return new GestioneNucleoFamigliareImpl();
  }

  public ScadenzeInterface getScadenze() throws BusinessException {
    return new ScadenzeImpl();
  }

  public ScadenzePersonalizzateInterface getScadenzePersonalizzate() throws BusinessException {
    return new ScadenzePersonalizzateImpl();
  }

  public MessaggiUtenteInterface getMessaggiUtente() throws BusinessException {
    return new MessaggiUtenteImpl();
  }

  public EntitaConfigurazioneUtenteInterface getEntitaConfigurazioneUtente()
      throws BusinessException {
    return new EntitaConfigurazioneUtenteImpl();
  }

  public SupportoIstanzeVerbaliPLInterface getSupportoIstanzeVerbaliPL() throws BusinessException {
    return new SupportoIstanzeVerbaliPLImpl();
  }

  public AuditBackendInterface getAuditBackend() {
    return new AuditBackendImpl();
  }

  public ConfigurazioneInterface getConfigurazioneUtenteFdC() throws BusinessException {
    return new ConfigurazioneImpl();
  }

  public ServiziHomePageInterface getServiziHomePage() throws BusinessException {
    return new ServiziHomePageImpl();
  }

  public CertificatiInterface getCertificati() {
    return new CertificatiImpl();
  }

  public ServiziGloboInterface getGlobo() {
    return new ServiziGloboImpl();
  }

  public TeleriscaldamentoInterface getServiziTeleriscaldamento() {
    return new TeleriscaldamentoImpl();
  }

  public MessaggiInterface getServiziMessaggiI() {
    return new MessaggiImpl();
  }

  public ICategorieCatastali getCategorieCatastaliService() {
    return new CategorieCatastaliImpl();
  }

  public IHelpCzRMService getHelpCzRMService() {
    return new HelpCzRMService();
  }

  public ContattiService getContattiUtente() {
    return new ContattiImpl();
  }

  public GeoworksService getGeoworksHelper() {
    return new GeoworksImpl();
  }

  public FdCBreadcrumbService getBreadcrumb() {
    return new FdCBreadcrumbImpl();
  }

  public GenovaParcheggiHelperService getGenovaParcheggiHelper() {
    return new GenovaParcheggiHelperImpl();
  }
}
