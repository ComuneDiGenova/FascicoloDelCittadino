package it.liguriadigitale.ponmetro.portale.business.mieistanze.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import java.io.IOException;
import java.util.List;

public interface ServiziMieIstanzeService {

  List<Istanza> getIstanze(Utente utente) throws BusinessException, ApiException, IOException;

  List<Istanza> getListaTutteIstanzeConFiltroStatoDaBadge(Utente utente, String statoIstanza)
      throws BusinessException, ApiException, IOException;

  /*
   * List<Istanza> getIstanzeByNumeroProtocollo(Utente utente, String
   * numeroProtocollo) throws BusinessException, ApiException, IOException;
   */

  List<Istanza> getIstanzeByNumeroProtocollo(Utente utente, RicercaIstanza ricercaIstanza)
      throws BusinessException, ApiException, IOException;

  Istanza getIstanzaByNumeroProtocolloNumeroIstanza(
      Utente utente, String numeroProcollo, String numerIstanza)
      throws BusinessException, ApiException, IOException;

  /*
   * List<Istanza> getIstanzeByNumeroIstanzaNumeroProcolloSenzaDoppioni(Utente
   * utente, String numeroProcollo, String numeroIstanza) throws
   * BusinessException, ApiException, IOException;
   */

  public DatiRichiestaIstanzaPl popolaMappaMultiThread(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl, Utente utente)
      throws BusinessException, ApiException, IOException;

  public DatiRichiestaIstanzaPl popolaMappaUnicoServizio(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl, Utente utente)
      throws BusinessException, ApiException, IOException;
}
