package it.liguriadigitale.ponmetro.api.business.genovaparcheggi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.configurazione.impl.ContattiImpl;
import it.liguriadigitale.ponmetro.api.business.genovaparcheggi.service.GenovaParcheggiHelperService;
import it.liguriadigitale.ponmetro.api.integration.dao.genovaparcheggi.BravTFunzioniDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.genovaparcheggi.BravTPermessiDAO;
import it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.BravTFunzioni;
import it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.BravTPermessi;
import it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.builder.BravFunzioniBuilder;
import it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.builder.BravPermessiBuilder;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni.TipoFunzEnum;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GenovaParcheggiHelperImpl implements GenovaParcheggiHelperService {

  private static Log log = LogFactory.getLog(ContattiImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<BravTFunzioni> selectBravFunzioni() throws BusinessException {
    log.debug("selectBravFunzioni");

    BravTFunzioni funzioni = new BravTFunzioni();
    BravTFunzioniDAO dao = new BravTFunzioniDAO(funzioni);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<BravTFunzioni> listaFunzioniBrav = helper.cercaOggetti();

    log.debug("Select brav funzioni = " + listaFunzioniBrav);
    return listaFunzioniBrav;
  }

  @Override
  public List<BravTPermessi> selectBravPermessi(String tipoFunz) throws BusinessException {
    log.debug("selectBravPermessi tipoFunz = " + tipoFunz);

    BravTPermessi permessi = new BravTPermessi();
    permessi.setTipoFunz(tipoFunz);
    BravTPermessiDAO dao = new BravTPermessiDAO(permessi);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<BravTPermessi> listaPermessiBrav = helper.cercaOggetti();

    log.debug("Select permessi brav = " + listaPermessiBrav);
    return listaPermessiBrav;
  }

  @Override
  public List<BravFunzioni> getBravFunzioni() throws BusinessException {
    List<BravTFunzioni> selectBravFunzioni = selectBravFunzioni();

    List<BravFunzioni> funzioni = new ArrayList<>();

    if (selectBravFunzioni != null) {
      for (BravTFunzioni elem : selectBravFunzioni) {
        BravFunzioni funzione = buildBravFunzioni(elem);
        funzioni.add(funzione);
      }
    }

    return funzioni;
  }

  @Override
  public List<BravPermessi> getBravPermessi(String tipoFunz) throws BusinessException {
    List<BravTPermessi> selectBravPermessi = selectBravPermessi(tipoFunz);

    List<BravPermessi> permessi = new ArrayList<>();

    if (selectBravPermessi != null) {
      for (BravTPermessi elem : selectBravPermessi) {
        BravPermessi permesso = buildBravPermessi(elem);
        permessi.add(permesso);
      }
    }

    return permessi;
  }

  private BravFunzioni buildBravFunzioni(BravTFunzioni funzioni) {
    log.debug("buildBravFunzioni");

    BravFunzioniBuilder funzioniBuilder =
        new BravFunzioniBuilder()
            .setIdFunz(funzioni.getIdFunz())
            .setTipoFunz(TipoFunzEnum.valueOf(funzioni.getTipoFunz()))
            .setDescrizioneFunz(funzioni.getDescrizioneFunz());

    BravFunzioni funzioniResult = funzioniBuilder.build();

    log.debug("buildBravFunzioni funzioniResult = " + funzioniResult);

    return funzioniResult;
  }

  private BravPermessi buildBravPermessi(BravTPermessi permessi) {
    log.debug("buildBravPermessi");

    BravPermessiBuilder permessiBuilder =
        new BravPermessiBuilder()
            .setIdPermesso(permessi.getIdPermesso())
            .setIdFunz(permessi.getIdFunz())
            .setTipoFunz(permessi.getTipoFunz())
            .setDescrizionePermesso(permessi.getDescrizioneFunz());

    BravPermessi permessiResult = permessiBuilder.build();

    log.debug("buildBravPermessi permessiResult = " + permessiResult);

    return permessiResult;
  }

  @Override
  public BravTFunzioni selectBravFunzione(String tipoFunz) throws BusinessException {
    log.debug("selectBravFunzione tipoFunz = " + tipoFunz);

    BravTFunzioni funzione = new BravTFunzioni();
    funzione.setTipoFunz(tipoFunz);
    BravTFunzioniDAO dao = new BravTFunzioniDAO(funzione);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    BravTFunzioni funzioneBrav = (BravTFunzioni) helper.cercaOggetto();

    log.debug("selectBravFunzione = " + funzioneBrav);
    return funzioneBrav;
  }

  @Override
  public BravFunzioni getBravFunzione(String tipoFunz) throws BusinessException {
    BravTFunzioni selectBravFunzione = selectBravFunzione(tipoFunz);

    BravFunzioni funzione = new BravFunzioni();

    if (selectBravFunzione != null) {
      funzione = buildBravFunzioni(selectBravFunzione);
    }

    return funzione;
  }
}
