package it.liguriadigitale.ponmetro.api.business.configurazione.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.common.datasource.ContattiDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.business.configurazione.service.ContattiService;
import it.liguriadigitale.ponmetro.api.integration.dao.contatti.CfgTContattiDAO;
import it.liguriadigitale.ponmetro.api.pojo.contatti.CfgTContatti;
import it.liguriadigitale.ponmetro.api.pojo.contatti.builder.DatiContattiBuilder;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente.TipoEnum;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContattiImpl implements ContattiService {

  private static Log log = LogFactory.getLog(ContattiImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<CfgTContatti> selectContatti(Long idFcitt) throws BusinessException {
    log.debug("selectContatti idFcitt = " + idFcitt);

    CfgTContatti contatti = new CfgTContatti();
    contatti.setIdFcitt(idFcitt);
    CfgTContattiDAO dao = new CfgTContattiDAO(contatti);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<CfgTContatti> listaContatti = helper.cercaOggetti();

    log.debug("Select contatti = " + listaContatti);
    return listaContatti;
  }

  @Override
  public void inserisciContatti(Long idFcitt, String contatto, String tipo)
      throws BusinessException {
    log.debug("inserisciContatti idFcitt = " + idFcitt + " - " + contatto + " - " + tipo);

    ContattiDatasourceTransactionManager manager =
        new ContattiDatasourceTransactionManager() {

          @Override
          protected void execute(Connection connection) throws Exception {
            CfgTContatti contatti = new CfgTContatti();
            contatti.setIdFcitt(idFcitt);
            contatti.setContatto(contatto);
            contatti.setTipo(tipo);
            contatti.setDataIns(LocalDateTime.now());
            contatti.setDataAgg(LocalDateTime.now());

            CfgTContattiDAO dao = new CfgTContattiDAO(contatti);
            dao.insertPrepared(connection);
            connection.commit();
          }
        };

    manager.executeTransaction();
  }

  @Override
  public void aggiornaContatti(Long idFcitt, String contatto, String tipo)
      throws BusinessException {
    log.debug("aggiornaContatti idFcitt = " + idFcitt + " - " + contatto + " - " + tipo);

    ContattiDatasourceTransactionManager manager =
        new ContattiDatasourceTransactionManager() {

          @Override
          protected void execute(Connection connection) throws Exception {
            CfgTContatti contatti = new CfgTContatti();
            contatti.setIdFcitt(idFcitt);
            contatti.setContatto(contatto);
            contatti.setTipo(tipo);
            contatti.setDataAgg(LocalDateTime.now());

            CfgTContattiDAO dao = new CfgTContattiDAO(contatti);
            dao.update(connection);
            connection.commit();
          }
        };

    manager.executeTransaction();
  }

  @Override
  public void inserisciAggiornaContatto(Long idFcitt, String tipo, ContattiUtente contatti)
      throws BusinessException {
    log.debug("inserisciAggiornaContatto : " + idFcitt + " - " + tipo + " - " + contatti);

    List<CfgTContatti> contattiPresenti = selectContatti(idFcitt);

    CfgTContatti contatto =
        contattiPresenti.stream()
            .filter(elem -> elem.getTipo().equalsIgnoreCase(tipo))
            .findFirst()
            .orElse(null);

    log.debug("CP contatto = " + contatto);

    if (contatto != null) {
      aggiornaContatti(idFcitt, contatti.getContatto(), contatti.getTipo());
    } else {
      inserisciContatti(idFcitt, contatti.getContatto(), contatti.getTipo());
    }
  }

  @Override
  public List<ContattiUtente> getContattiUtente(Long idFcitt) throws BusinessException {

    List<CfgTContatti> selectContatti = selectContatti(idFcitt);

    List<ContattiUtente> contatti = new ArrayList<>();

    if (selectContatti != null) {
      for (CfgTContatti elem : selectContatti) {
        ContattiUtente contatto = buildContattiUtente(elem);
        contatti.add(contatto);
      }
    }

    return contatti;
  }

  private ContattiUtente buildContattiUtente(CfgTContatti contatti) {
    log.debug("buildContattiUtente");

    //	ZoneOffset offset = OffsetDateTime.now().getOffset();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    TipoEnum tipo = null;
    if (contatti != null && contatti.getTipo() != null) {
      if (contatti.getTipo().equalsIgnoreCase("C")) {
        tipo = TipoEnum.C;
      }
      if (contatti.getTipo().equalsIgnoreCase("E")) {
        tipo = TipoEnum.E;
      }
    }

    DatiContattiBuilder datiContattiBuilder =
        new DatiContattiBuilder()
            .setIdFcitt(contatti.getIdFcitt())
            .setContatto(contatti.getContatto())
            .setTipo(tipo)
            .setDataIns(
                contatti.getDataIns() != null ? contatti.getDataIns().format(formatter) : null)
            .setDataAgg(
                contatti.getDataAgg() != null ? contatti.getDataAgg().format(formatter) : null);

    // .setDataIns(contatti.getDataIns() != null ? contatti.getDataIns().atOffset(offset) : null)
    // .setDataAgg(contatti.getDataAgg() != null ? contatti.getDataAgg().atOffset(offset) : null);

    ContattiUtente contattiResult = datiContattiBuilder.build();

    log.debug("contattiResult = " + contattiResult);

    return contattiResult;
  }

  @Override
  public void cancellaContatto(Long idFcitt, String tipo) throws BusinessException {
    log.debug("cancellaContatto idFcitt = " + idFcitt + " - " + tipo);

    ContattiDatasourceTransactionManager manager =
        new ContattiDatasourceTransactionManager() {

          @Override
          protected void execute(Connection connection) throws Exception {
            CfgTContatti contatti = new CfgTContatti();
            contatti.setIdFcitt(idFcitt);
            contatti.setTipo(tipo);

            CfgTContattiDAO dao = new CfgTContattiDAO(contatti);
            dao.deleteByWhere(connection);
            connection.commit();
          }
        };

    manager.executeTransaction();
  }
}
