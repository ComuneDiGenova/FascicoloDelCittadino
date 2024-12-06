package it.liguriadigitale.ponmetro.portale.business.commissionimensa.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.commissionimensa.model.Allegato;
import it.liguriadigitale.ponmetro.commissionimensa.model.Audit;
import it.liguriadigitale.ponmetro.commissionimensa.model.Istituto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface CommissioniMensaService {

  List<Istituto> getIstituti() throws BusinessException, ApiException, IOException;

  List<Audit> getAllAudit(String codiceScuola) throws BusinessException, ApiException, IOException;

  Allegato getPdfVerbale(BigDecimal idVerbale) throws BusinessException, ApiException, IOException;
}
