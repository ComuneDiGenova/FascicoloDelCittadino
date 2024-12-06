package it.liguriadigitale.ponmetro.api.business.biblioteca.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.MovimentoBibliotecaDto;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaRecord;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BibliotecaInterface {

  public LocalDate getDataScadenzaMovimento(MovimentoBibliotecaDto movimentoBibliotecaDto)
      throws BusinessException;

  public List<TabellaRecord> getListaTabellaTmov() throws BusinessException, IOException;

  public String getDescrizioneTipoMovimento(MovimentoBibliotecaDto movimentoBibliotecaDto)
      throws BusinessException, IOException;
}
