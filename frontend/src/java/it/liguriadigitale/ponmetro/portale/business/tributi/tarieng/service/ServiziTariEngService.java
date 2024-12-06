package it.liguriadigitale.ponmetro.portale.business.tributi.tarieng.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiDocumentiTariEng;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.tarieng.model.DettaglioDocumentoEmesso;
import it.liguriadigitale.ponmetro.tarieng.model.DocumentiPDF;
import it.liguriadigitale.ponmetro.tarieng.model.QuadroTributario;
import it.liguriadigitale.ponmetro.tarieng.model.Rate;
import it.liguriadigitale.ponmetro.tarieng.model.RicevutaTelematica;
import it.liguriadigitale.ponmetro.tarieng.model.SchedaTributoTari;
import it.liguriadigitale.ponmetro.tarieng.model.Tributi;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRimborso;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoGETResponse;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoPOSTResponse;
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;

public interface ServiziTariEngService {

  List<BreadcrumbFdC> getListaBreadcrumb();

  List<BreadcrumbFdC> getListaBreadcrumbDettagli();

  List<Legenda> getListaLegenda();

  List<Legenda> getListaLegendaRimborsi();

  Tributi getQuadroTributarioTARI(String codiceFiscale) throws BusinessException, ApiException;

  SchedaTributoTari getSchedaTributoTARI(String uri) throws BusinessException, ApiException;

  List<DatiDocumentiTariEng> getDatiTariCompletiDellAnno(QuadroTributario elem)
      throws BusinessException, ApiException;

  Map<Long, QuadroTributario> getMappaAnniTributiTari(Tributi tari);

  List<StepFdC> getListaStep();

  List<BreadcrumbFdC> getListaBreadcrumbRichiestaRimborso();

  AbstractLink creaBtnPaga(Rate rate, Utente utente);

  MipData creaMipData(Rate rata, Utente utente);

  Link<Void> creaBottoneMipPagoPA(Rate rata, String wicketID, MipData data);

  Debito getDebitoMIPDaIUV(String cf, String iuv) throws BusinessException, ApiException;

  RicevutaPagamento getRicevutaMipDaIUV(Utente utente, String iuv)
      throws BusinessException, ApiException;

  RicevutaTelematica getStampaRicevutaTelematica(
      Integer idDebitoreGeri, Integer idRicevutaTelematicaGeri)
      throws BusinessException, ApiException;

  DocumentiPDF getStampaDocumentoPDF(Integer idDebitoreGeri, Integer idAllegatoGeri)
      throws BusinessException, ApiException;

  List<BreadcrumbFdC> getListaBreadcrumbRichiestaRimborsoTariErede();

  String getValoreDaDb(String chiave);

  IstanzaRimborsoGETResponse getRimborsi(String codiceFiscale)
      throws BusinessException, ApiException;

  List<MessaggiInformativi> popolaListaMessaggiQuadroDettagli(DatiDocumentiTariEng documento);

  IstanzaRimborsoPOSTResponse setRichiestaRimborsoTARI(DatiRichiestaRimborsoTariEng datiRimborso)
      throws BusinessException, ApiException;

  IstanzaRimborsoPOSTResponse setRichiestaRimborsoTEFA(DatiRichiestaRimborsoTariEng datiRimborso)
      throws BusinessException, ApiException;

  IstanzaRimborsoPOSTResponse setRichiestaRimborsoTARIREALE(
      DatiRichiestaRimborsoTariEng datiRimborso) throws BusinessException, ApiException;

  String decodicaTipoUtenza(String tipoUtz);

  List<DatiDocumentiTariEng> getListaDistinctSuIdDeb(
      List<DatiDocumentiTariEng> listaDatiDocumentiTariEng);

  DatiDocumentiTariEng setInfoSuEccedenze(DatiDocumentiTariEng elementoTari);

  List<ModalitaPagamentoEnum> getListaModalitaPagamento();

  public byte[] getHelpRimborsiPDF(Utente utente, String codiceHelp, Long idHelp)
      throws BusinessException;

  DatiDocumentiTariEng setInfoPosizione(DatiDocumentiTariEng elementoTari);

  void setInfoSuEccedenzeEPosizione(DatiDocumentiTariEng elementoTari);

  DettaglioDocumentoEmesso getDettagliDocumentoTARI(String uri)
      throws BusinessException, ApiException;

  List<DatiRimborso> getRimborsiOrdinati(
      IstanzaRimborsoGETResponse popolaRimborsi, String codiceFiscale);

  boolean checkRimborsoIntestarioRichiedibile(DatiRichiestaRimborsoTariEng datiRimborso);

  String decodificaStatoIstanzaDiRimborso(String statoEng);

  boolean messaggioAccontoPagatoParzialmenteVisibile(DatiDocumentiTariEng documento);

  boolean dataScadenzaRataConguaglioVisible(DatiDocumentiTariEng documento);
}
