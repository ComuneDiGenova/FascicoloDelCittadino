package it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonusResponse;
import java.io.IOException;

public interface ServiziTeleriscaldamentoIren {

  FileRichiestaBonusResponse serviceRichiestaBonusPost(DatiDomandaTeleriscaldamento datiDomanda)
      throws BusinessException, ApiException, IOException;

  FileRichiestaBonusResponse serviceRichiestaBonusPostEsitoOK(
      DatiDomandaTeleriscaldamento datiDomanda);

  FileRichiestaBonusResponse serviceRichiestaBonusPostEsitoKO(
      DatiDomandaTeleriscaldamento datiDomanda);
}
