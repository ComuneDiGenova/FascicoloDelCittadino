package it.liguriadigitale.ponmetro.api.business.genovaparcheggi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.BravTFunzioni;
import it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi.BravTPermessi;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import java.util.List;

public interface GenovaParcheggiHelperService {

  List<BravTFunzioni> selectBravFunzioni() throws BusinessException;

  List<BravTPermessi> selectBravPermessi(String tipoFunz) throws BusinessException;

  List<BravFunzioni> getBravFunzioni() throws BusinessException;

  List<BravPermessi> getBravPermessi(String tipoFunz) throws BusinessException;

  BravTFunzioni selectBravFunzione(String tipoFunz) throws BusinessException;

  BravFunzioni getBravFunzione(String tipoFunz) throws BusinessException;
}
