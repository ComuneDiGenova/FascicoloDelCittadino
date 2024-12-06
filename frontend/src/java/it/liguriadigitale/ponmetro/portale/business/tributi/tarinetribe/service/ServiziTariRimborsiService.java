package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribeResult;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsi;
import java.util.List;

public interface ServiziTariRimborsiService {

  List<TARIRimborsi> getRimborsiTARIAnnoCorrente(String codiceFiscale)
      throws ApiException, BusinessException;

  DatiRichiestaRimborsoTariNetribeResult richiestaRimborsoTari(
      DatiRichiestaRimborsoTariNetribe datiRimborso) throws ApiException, BusinessException;

  List<ModalitaPagamentoEnum> getListaModalitaPagamento();

  byte[] getHelpRimborsiPDF(Utente utente, String codiceHelp, Long idHelp) throws BusinessException;
}
