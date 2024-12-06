package it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.Ordinario;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Esito;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Protocollo;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.RicercaDato;
import java.io.IOException;
import java.util.List;

public interface ServiziTeleriscaldamento {

  List<BreadcrumbFdC> getListaBreadcrumb();

  List<BreadcrumbFdC> getListaBreadcrumbRegistrazione();

  List<MessaggiInformativi> popolaListaMessaggi();

  List<DomandaTeleriscaldamento> getDomande(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  Protocollo getProtocollo() throws BusinessException, ApiException, IOException;

  Esito inviaDomandaTeleriscaldamento(DatiDomandaTeleriscaldamento datiDomanda)
      throws BusinessException, ApiException, IOException;

  List<StepFdC> getListaStep();

  // void getAttestazioniISEE(Utente utente);

  ConsultazioneAttestazioneCF200 getAttestazioneISEEModi(Utente utente);

  Ordinario getIseeOrdinario(Utente utente);

  void setDatiIseeModiInDomanda(Utente utente, DatiDomandaTeleriscaldamento datiDomanda);

  RicercaDato isClientePresenteInLista(String numeroCliente)
      throws BusinessException, ApiException, IOException;

  RicercaDato isContrattoPresenteInLista(String numeroContratto)
      throws BusinessException, ApiException, IOException;

  String getValoreDaDbByChiave(String chiave);

  byte[] getHelpTeleriscaldamentoPDF(Utente utente) throws BusinessException;
  ;
}
