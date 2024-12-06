package it.liguriadigitale.ponmetro.portale.business.pagopa;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;

public interface GestionePagoPAInterface {

  public String preparaPagamento(MipData input) throws BusinessException;
}
