package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.Ordinario;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaContributoTariNetribeResult;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.tarinetribe.model.AgevolazioneTariffariaTariInformazioni;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiAgevolazioneTariffariaTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiModalitaPagamentoAgevolazioneTariffariaTari.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import java.io.IOException;
import java.util.List;

public interface ServiziTariContributo {

  List<StepFdC> getListaStep();

  List<DatiAgevolazioneTariffariaTari> getDomandeContributoTari(String codiceFiscale, Integer anno)
      throws ApiException, BusinessException;

  ConsultazioneAttestazioneCF200 getAttestazioneISEEModi(Utente utente);

  Ordinario getIseeOrdinario(Utente utente);

  void setDatiDomanda(Utente utente, DatiDomandaContributoTari datiDomanda);

  AgevolazioneTariffariaTariInformazioni getInformazioni(Long anno)
      throws ApiException, BusinessException;

  FileAllegato getDocumento(String codiceIdentificativoFile)
      throws ApiException, BusinessException, IOException;

  List<ModalitaPagamentoEnum> getListaModalitaPagamento();

  DatiRichiestaContributoTariNetribeResult richiediContributoTari(
      DatiDomandaContributoTari datiDomanda) throws ApiException, BusinessException, IOException;
}
