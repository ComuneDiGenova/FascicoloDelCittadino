package it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import java.util.List;

public interface ServiziGenovaParcheggiHelperService {

  List<BravFunzioni> getBravFunzioni() throws BusinessException;

  BravFunzioni getBravFunzione(String tipoFunz) throws BusinessException;

  List<BravPermessi> getBravPermessi(String tipoFunz) throws BusinessException;
}
