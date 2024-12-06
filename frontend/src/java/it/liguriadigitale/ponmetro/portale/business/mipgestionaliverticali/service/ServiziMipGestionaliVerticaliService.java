package it.liguriadigitale.ponmetro.portale.business.mipgestionaliverticali.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.DatiPagamentiBollettiniRiepilogativiEstesi;
import java.util.List;

public interface ServiziMipGestionaliVerticaliService {

  public List<AvvisoPagamento> getListaTipologieEntrateByCf(String cf)
      throws BusinessException, ApiException;

  public Debito getListaTipologieEntrateNonAttualizzaByCfIuv(String cf, String iuv)
      throws BusinessException, ApiException;

  public List<RicevutaPagamento> getListaRicevutePagamentoPositivePersonaByCfAndEsito(
      String cf, boolean esito) throws BusinessException, ApiException;

  public List<RicevutaPagamento> getListaRicevutePagamentoPositivePersonaByCf(String cf)
      throws BusinessException, ApiException;

  public List<RicevutaPagamento> getListaRicevutePagamentoNegativePersonaByCf(String cf)
      throws BusinessException, ApiException;

  public List<TipologiaEntrata> getListaTipologieEntrate() throws BusinessException, ApiException;

  public List<AvvisoPagamento> getListaAvvisiPagamentiPerCf(Utente utente)
      throws BusinessException, ApiException;

  public AvvisoPagamento getAvvisoPagamentoDaCfECodiceAvviso(
      Utente utente, String codiceAvviso, boolean pdf, boolean attualizza)
      throws BusinessException, ApiException;

  public AvvisoPagamento getAvvisoPagamentoDaCfAnonimoECodiceAvviso(
      String codiceAvviso, boolean pdf, boolean attualizza) throws BusinessException, ApiException;

  public Debito getDebitoByCfServizioAnnoNumeroDocumento(
      String cf, String servizio, Long anno, String numeroDocumento, boolean attualizzaPagamento)
      throws BusinessException, ApiException;

  public AvvisoPagamento getAvvisoPagamento(
      String cf,
      String servizio,
      Long anno,
      String numeroDocumento,
      boolean ottieniPdf,
      boolean attualizza)
      throws BusinessException, ApiException;

  public List<RicevutaPagamento> getRicevutaDaIuv(Utente utente, String iuv, boolean esitoPositivo)
      throws BusinessException, ApiException;

  public List<RicevutaPagamento> getRicevutaDaCodiceAvviso(
      Utente utente, String codiceAvviso, boolean esitoPositivo)
      throws BusinessException, ApiException;

  public List<RicevutaPagamento> getRicevutaPerChiave(
      Utente utente, String servizio, Long anno, String numeroDocumento, boolean esitoPositivo)
      throws BusinessException, ApiException;

  public List<RicevutaPagamento> getRicevutaPerBollettinoMensa(
      Utente utente, DatiPagamentiBollettiniRiepilogativiEstesi datiBollettino)
      throws BusinessException, ApiException;
}
