package it.liguriadigitale.ponmetro.portale.business.mipGlobali.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.globali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicolo;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicoloDatiGenerali;
import java.io.IOException;
import java.util.List;

public interface ServiziMipGlobaliService {

  List<Debito> getRiepilogoPagamentiUtente(Utente utente, boolean flagSoloNonPagati)
      throws BusinessException, ApiException, IOException;

  String getDescrizioneServizio(Utente utente, String servizio)
      throws BusinessException, ApiException;

  List<TipologiaEntrata> getListaTipologieEntrateServizi() throws BusinessException, ApiException;

  List<TipologiaEntrata> getListaTipologieEntrateServiziFiltrata(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<TipologiaEntrata>
      getListaTipologieEntrateServiziFiltrataSenzaVerbaliSenzaTariEngSenzaTariNetribe(Utente utente)
          throws BusinessException, ApiException, IOException;

  List<RicevutaPagamento> getListaRicevuteByCF(Utente utente)
      throws BusinessException, ApiException, IOException;

  List<RicevutaPagamento> getListaRicevuteByIUV(Utente utente, String iuv, boolean esitoPositivo)
      throws BusinessException, ApiException, IOException;

  RicevutaPagamento getRicevutaByIUVConEsitoOk(Utente utente, String iuv, boolean esitoPositivo)
      throws BusinessException, ApiException, IOException;

  List<RicevutaPagamento> getListaRicevuteByCodiceAvviso(
      Utente utente, String codiceAvviso, boolean esitoPositivo)
      throws BusinessException, ApiException, IOException;

  List<RicevutaPagamento> getListaRicevuteByChiave(
      Utente utente, String servizio, Long anno, String numeroDocumento)
      throws BusinessException, ApiException, IOException;

  it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito getDebitoDaPagareDaCodiceAvviso(
      Utente utente, String codiceAvviso, boolean attualizza)
      throws BusinessException, ApiException, IOException;

  List<DebitoMipFascicoloDatiGenerali> getRiepilogoPagamentiDatiGeneraliUtenteFascicolo(
      Utente utente, boolean flagSoloNonPagati) throws BusinessException, ApiException, IOException;

  List<DebitoMipFascicoloDatiGenerali>
      getRiepilogoPagamentiDatiGeneraliUtenteFascicoloOrdinatiPerDataCreazione(
          Utente utente, boolean flagSoloNonPagati)
          throws BusinessException, ApiException, IOException;

  List<DebitoMipFascicoloDatiGenerali> getRiepilogoPagamentiDatiGeneraliUtenteFascicoloFiltrati(
      Utente utente, boolean flagSoloNonPagati) throws BusinessException, ApiException, IOException;

  List<DebitoMipFascicoloDatiGenerali>
      getRiepilogoPagamentiDatiGeneraliUtenteFascicoloFiltratiSenzaVerbaliSenzaTariEngSenzaTriNetribe(
          Utente utente, boolean flagSoloNonPagati)
          throws BusinessException, ApiException, IOException;

  List<DebitoMipFascicoloDatiGenerali> getListaDebitiGeneraliPerServizio(
      Utente utente, TipologiaEntrata tipologiaEntrata)
      throws BusinessException, ApiException, IOException;

  List<DebitoMipFascicoloDatiGenerali> getListaDebitiGeneraliPerServizioOrdinatiPerDataCreazione(
      Utente utente, TipologiaEntrata tipologiaEntrata)
      throws BusinessException, ApiException, IOException;

  DebitoMipFascicolo getDebitoMipFascicolo(
      Utente utente, DebitoMipFascicoloDatiGenerali debitoGenerale)
      throws BusinessException, ApiException, IOException;

  AvvisoPagamento getAvvisoPagamento(
      Utente utente, String codiceAvviso, boolean ottieniPdf, boolean attualizza)
      throws BusinessException, ApiException, IOException;

  AvvisoPagamento getAvvisoPagamentoDaChiave(
      Utente utente,
      String servizio,
      Long anno,
      String numeroDocumento,
      boolean ottieniPdf,
      boolean attualizza)
      throws BusinessException, ApiException, IOException;

  List<RicevutaPagamento> getTutteRicevute(
      Utente utente,
      String iuv,
      String codiceAvviso,
      String servizio,
      Long anno,
      String numeroDocumento,
      boolean esitoPositivo)
      throws BusinessException, ApiException, IOException;

  List<RicevutaPagamento> getRicevuteByChiave(
      Utente utente, String servizio, Long anno, String numeroDocumento, boolean esitoPositivo)
      throws BusinessException, ApiException, IOException;
}
