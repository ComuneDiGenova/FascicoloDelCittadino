package it.liguriadigitale.ponmetro.api.business.scadenze.thread;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.biblioteca.impl.BibliotecaImpl;
import it.liguriadigitale.ponmetro.api.business.biblioteca.service.BibliotecaInterface;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgKeyValueDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgRFcittScadenzePersonaliDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.seq.CfgRFcittScadenzePersonaliSequenceDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgRFcittScadenzePersonaliInsertDAO;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgKeyValue;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.CfgRFcittScadenzePersonali;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.MovimentoBibliotecaDto;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.ScadenzePersonalizzateDto;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScadenzaBibliotecaAutoRun extends TemplateScadenzeRunnable
    implements Callable<Boolean>, CostantiBaseDatiScadenzePersonalizzate {

  private static Log log = LogFactory.getLog(ScadenzaBibliotecaAutoRun.class);

  private ScadenzePersonalizzateDto utenteDto;
  private MovimentoBibliotecaDto movimentoDto;

  public ScadenzaBibliotecaAutoRun(
      ScadenzePersonalizzateDto utenteDto, MovimentoBibliotecaDto movimentoDto) {
    super();

    this.utenteDto = utenteDto;
    this.movimentoDto = movimentoDto;

    log.debug("[ScadenzaBibliotecaAutoRun] INIZIO");
    log.debug("[ScadenzaBibliotecaAutoRun] utenteDto = " + utenteDto);
    log.debug("[ScadenzaBibliotecaAutoRun] movimentoDto = " + movimentoDto);
  }

  @Override
  public Boolean call() throws Exception {
    log.debug("--- ScadenzaBibliotecaAutoRun run INIZIO---");
    log.debug("ScadenzePersonalizzateDto=" + utenteDto);

    Thread ct = Thread.currentThread();
    ct.setName("3dBiblioteca_" + movimentoDto.getId());
    Boolean esito = true;
    try {
      LocalDate scadenzaMovimento = ricavaScadenzaMovimento();

      PonMetroDatasourceTransactionManager manager =
          new PonMetroDatasourceTransactionManager() {

            @Override
            protected void execute(Connection paramConnection) throws Exception {
              if (scadenzaMovimento != null
                  && isScadenzaNonPresente(paramConnection, scadenzaMovimento)) {
                inserisciNuovaScadenza(paramConnection, scadenzaMovimento);
              }
            }
          };
      manager.executeTransaction();
    } catch (BusinessException e) {
      log.error("Errore durante la chiamata alle Biblioteche: ", e);
      esito = false;
    }
    log.debug("--- ScadenzaBibliotecaAutoRun FINE ---");
    return esito;
  }

  private LocalDate ricavaScadenzaMovimento() throws BusinessException {
    log.debug("ricavaScadenzaMovimento biblioteca INIZIO: " + movimentoDto);
    BibliotecaInterface service = new BibliotecaImpl();
    LocalDate dataScadenza = service.getDataScadenzaMovimento(movimentoDto);
    return dataScadenza;
  }

  @SuppressWarnings("unchecked")
  protected boolean isScadenzaNonPresente(Connection paramConnection, LocalDate scadenzaMovimento)
      throws SQLException, BusinessException, IOException {
    log.debug("isScadenzaNonPresente biblioteca INIZIO ");

    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setDataScadenza(scadenzaMovimento.atStartOfDay());
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdTEvento(SCADENZA_BIBLIOTECA);
    scadenza.setOggetto(getOggettoMovimento());
    CfgRFcittScadenzePersonaliDAO dao = new CfgRFcittScadenzePersonaliDAO(scadenza);
    List<CfgRFcittScadenzePersonali> lista = dao.retrieveWhere(paramConnection);
    log.debug("isScadenzaNonPresente biblioteca =" + lista.isEmpty());
    return lista.isEmpty();
  }

  private String getOggettoMovimento() throws BusinessException, IOException {
    BibliotecaInterface service = new BibliotecaImpl();
    String descrizioneOggetto = service.getDescrizioneTipoMovimento(movimentoDto);
    return descrizioneOggetto;
  }

  protected void inserisciNuovaScadenza(Connection paramConnection, LocalDate scadenzaMovimento)
      throws SQLException, BusinessException, IOException {
    log.debug("inserisciNuovaScadenza biblioteca INIZIO");
    CfgRFcittScadenzePersonali scadenza = new CfgRFcittScadenzePersonali();
    scadenza.setIdFcitt(utenteDto.getIdFcitt());
    scadenza.setIdStato(STATO_PUBBLICATA);
    scadenza.setDataIns(LocalDateTime.now());
    scadenza.setDataScadenza(scadenzaMovimento.atStartOfDay());
    Long idScadenza = getNextIdSequence(paramConnection);
    scadenza.setDataInvioMsg(controllaDataInvioMessaggio(scadenzaMovimento, idScadenza, utenteDto));
    scadenza.setIdScadenza(idScadenza);
    scadenza.setIdStatoRec(0L);
    scadenza.setIdTEvento(SCADENZA_BIBLIOTECA);
    scadenza.setScadenza(getTestoDaPubblicare(paramConnection));
    scadenza.setUtenteIns(BATCH_SCADENZE);
    scadenza.setOggetto(getOggettoMovimento());
    CfgRFcittScadenzePersonaliInsertDAO dao = new CfgRFcittScadenzePersonaliInsertDAO(scadenza);
    dao.insertPrepared(paramConnection);
  }

  private Long getNextIdSequence(Connection paramConnection) throws SQLException {
    log.debug("getNextIdSequence bilioteca INIZIO");
    CfgRFcittScadenzePersonaliSequenceDAO dao = new CfgRFcittScadenzePersonaliSequenceDAO();
    return (Long) dao.retrieveWhere(paramConnection).get(0);
  }

  private String getTestoDaPubblicare(Connection con) throws SQLException {
    log.debug("getTestoDaPubblicare biblioteca INIZIO");
    CfgKeyValue cfgkeyvalue = new CfgKeyValue();
    cfgkeyvalue.setCfgKey(TESTO_SCADENZE_BIBLIOTECA);
    CfgKeyValueDAO dao = new CfgKeyValueDAO(cfgkeyvalue);
    cfgkeyvalue = (CfgKeyValue) dao.retrieveByKey(con);
    return cfgkeyvalue.getCfgValue();
  }
}
