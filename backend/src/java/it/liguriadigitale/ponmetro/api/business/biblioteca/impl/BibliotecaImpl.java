package it.liguriadigitale.ponmetro.api.business.biblioteca.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.biblioteca.service.BibliotecaInterface;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate.ServiceLocatorLivelloUnoBiblioteche;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.MovimentoBibliotecaDto;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaRecord;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BibliotecaImpl implements BibliotecaInterface {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_BIBLIOTECHE = "Errore di connessione alle API Biblioteca";

  private static final Locale localeItaly = Locale.ITALY;

  @Override
  public LocalDate getDataScadenzaMovimento(MovimentoBibliotecaDto movimentoBibliotecaDto)
      throws BusinessException {
    log.debug("[BibliotecaImpl] getDataScadenzaMovimento --- INIZIO " + movimentoBibliotecaDto);

    try {
      LocalDate dataFine = null;
      if (movimentoBibliotecaDto.getDtStimaFine() != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataFine = LocalDate.parse(movimentoBibliotecaDto.getDtStimaFine(), formatter);
      }
      return dataFine;
    } catch (WebApplicationException e) {
      log.error(
          "BibliotecaImpl -- getDataScadenzaMovimento: errore nella Response:" + e.getMessage());
      throw new BusinessException(e.getResponse() + "  " + e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "BibliotecaImpl -- getDataScadenzaMovimento: errore durante la chiamata delle API biblioteche: ",
          e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    }
  }

  @Override
  public List<TabellaRecord> getListaTabellaTmov() throws BusinessException, IOException {
    try {
      List<TabellaRecord> listaTipoMovimenti = new ArrayList<TabellaRecord>();
      listaTipoMovimenti =
          ServiceLocatorLivelloUnoBiblioteche.getInstance()
              .getApiTabelleCircolazione()
              .listaTabellaTmov(localeItaly.toString());
      return listaTipoMovimenti;
    } catch (BusinessException e) {
      log.error("BibliotecaImpl -- getListaTabellaTmov: errore API biblioteche:", e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    } catch (RuntimeException e) {
      log.debug(
          "BibliotecaImpl -- getListaTabellaTmov: errore durante la chiamata delle API biblioteche ",
          e);
      throw new BusinessException(ERRORE_API_BIBLIOTECHE);
    }
  }

  @Override
  public String getDescrizioneTipoMovimento(MovimentoBibliotecaDto movimentoBibliotecaDto)
      throws BusinessException, IOException {
    TabellaRecord recordTipo =
        getListaTabellaTmov().stream()
            .filter(elem -> elem.getCd().equalsIgnoreCase(movimentoBibliotecaDto.getDsTipo()))
            .findAny()
            .orElse(null);
    String descrizioneMovimento = "";
    if (recordTipo != null) {
      descrizioneMovimento = recordTipo.getDscr();
    }
    return descrizioneMovimento;
  }
}
