package it.liguriadigitale.ponmetro.portale.business.mieiverbali.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatiCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatoIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AvvisoBonario;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiGeneraAvviso;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiISEE;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.EsitoOperazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.GeneraAvvisoResult;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.IstanzeCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.PuntiPatente;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RichiestaDPP;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.VerbaleCollection;
import java.io.IOException;
import java.util.List;

public interface ServiziMieiVerbaliService {

  VerbaleCollection getVerbaliByCF(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<Verbale> getVerbaliByTargaDataInizioProprieta(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<Verbale> getTuttiVerbali(Utente utente) throws BusinessException, ApiException, IOException;

  List<Verbale> getVerbaleByNumeroProtocollo(Utente utente, String numeroProtocollo)
      throws BusinessException, ApiException, IOException;

  List<Verbale> getVerbaleByNumeroProtocolloSenzaAnagrafica(Utente utente, String numeroProtocollo)
      throws BusinessException, ApiException, IOException;

  List<Verbale> getRicercaVerbaleByNumeroAvviso(Utente utente, Verbale verbale)
      throws BusinessException, ApiException, IOException;

  DettaglioVerbale getDettaglioVerbale(String numeroProtocollo, Utente utente)
      throws BusinessException, ApiException, IOException;

  List<Verbale> getListaTuttiVerbaliConFiltroStatoDaBadge(Utente utente, String statoVerbale)
      throws BusinessException, ApiException, IOException;

  FileAllegato getFileAllegatoAlVerbaleByAllegatoId(
      Utente utente, String numeroProtocollo, String allegatoId)
      throws BusinessException, ApiException, IOException;

  FileAllegato getFileAllegatoAlVerbaleByAllegatoUri(
      Utente utente, String numeroProtocollo, String allegatoUri)
      throws BusinessException, ApiException, IOException;

  GeneraAvvisoResult setGeneraAvviso(DatiGeneraAvviso datiGeneraAvviso)
      throws BusinessException, ApiException, IOException;

  // ISTANZE PL
  IstanzeCollection getIstanzeByVerbale(Verbale verbale)
      throws BusinessException, ApiException, IOException;

  Istanza inserisciIstanza(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException;

  EsitoOperazione inserisciAllegatoAdIstanza(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl, AllegatoIstanza allegatoIstanza)
      throws BusinessException, ApiException, IOException;

  Istanza getDatiIstanza(Verbale verbale, String istanzaId, Integer anno)
      throws BusinessException, ApiException, IOException;

  EsitoOperazione deleteIstanza(Verbale verbale, String istanzaId, Integer anno)
      throws BusinessException, ApiException, IOException;

  FileAllegato getAllegatoIstanza(
      Verbale verbale, String istanzaId, Integer anno, String idDocumentoIstanza)
      throws BusinessException, ApiException, IOException;

  EsitoOperazione deleteAllegatoIstanza(
      Verbale verbale, String istanzaId, Integer anno, String idDocumentoIstanza)
      throws BusinessException, ApiException, IOException;

  EsitoOperazione concludiIstanza(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException;

  AllegatiCollection getAllegatiIstanza(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException;

  AllegatiCollection getAllegatiIstanza(String numeroProtocollo, String istanzaId, Integer anno)
      throws BusinessException, ApiException, IOException;

  List<String> getMarcheVeicoli() throws BusinessException, ApiException, IOException;

  EsitoOperazione inserimentoISEE(
      String numeroProtocollo, String istanzaId, Integer anno, DatiISEE datiISEE)
      throws BusinessException, ApiException, IOException;

  EsitoOperazione inserisciConcludiIstanza(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException;

  // non usero'
  // POST /verbali/{numeroProtocollo}/istanze/{istanzaId}/anno/{anno}/allegati
  // aggiunge un allegato all'istanza (solo metadati)

  // DPP

  PuntiPatente dichiarazionePuntiPatenteConducente(
      Utente utente, String numeroAvviso, RichiestaDPP richiestaDpp)
      throws BusinessException, ApiException, IOException;

  PuntiPatente dichiarazionePuntiPatenteProprietario(
      Utente utente, String numeroAvviso, RichiestaDPP richiestaDpp)
      throws BusinessException, ApiException, IOException;

  List<StepFdC> getListaStep();

  List<BreadcrumbFdC> getListaBreadcrumbRichiestaRate();

  List<BreadcrumbFdC> getListaBreadcrumbRichiestaRimborso();

  AvvisoBonario getAvvisoBonario(String id) throws BusinessException, ApiException;
}
