package it.liguriadigitale.ponmetro.api.business.abbonamentiamt.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Tessera;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.AbbonamentoAmtDto;
import java.time.LocalDate;
import java.util.List;

public interface AbbonamentiAmtInterface {

  List<Tessera> getAbbonamentiAmtDelLoggato(AbbonamentoAmtDto abbonamentoAmtDto)
      throws BusinessException;

  LocalDate getDataFineValidita(AbbonamentoAmtDto abbonamentoAmtDto) throws BusinessException;
}
