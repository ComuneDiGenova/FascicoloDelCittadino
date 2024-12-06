package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiFileAllegatoNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.RataDaPagareTariNetribe;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import it.liguriadigitale.ponmetro.tarinetribe.model.Rata;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIResult;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;

public interface ServiziTariService {

  List<TARIResult> getSintesiTariAnnoCorrente(String codiceFiscale, Integer anno)
      throws BusinessException, ApiException, IOException;

  FileAllegato getPdfNetribe(String codiceIdentificativoFile)
      throws BusinessException, ApiException, IOException;

  DatiFileAllegatoNetribe datiFileAllegatoNetribe(String codiceIdentificativoFile)
      throws BusinessException, ApiException, IOException;

  Map<LocalDate, List<Rata>> getListaRateRaggruppatePerDataScadenza(List<Rata> rateizzazione);

  Rata getRataUnica(List<Rata> rateizzazione, String iuvTotaleDocumento);

  Debito getDebitoMIPDaIUV(String cf, String iuv) throws BusinessException, ApiException;

  RicevutaPagamento getRicevutaMipDaIUV(Utente utente, String iuv)
      throws BusinessException, ApiException;

  AbstractLink creaBtnPaga(RataDaPagareTariNetribe rataDaPagare, Utente utente, String wicketID);

  MipData creaMipData(RataDaPagareTariNetribe rataDaPagare, Utente utente);

  Link<Void> creaBottoneMipPagoPA(
      RataDaPagareTariNetribe rataDaPagare, String wicketID, MipData data);

  String getValoreDaDb(String chiave);
}
