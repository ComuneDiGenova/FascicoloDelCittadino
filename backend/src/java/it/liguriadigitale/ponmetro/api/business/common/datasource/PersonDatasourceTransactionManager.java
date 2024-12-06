package it.liguriadigitale.ponmetro.api.business.common.datasource;

import it.liguriadigitale.framework.business.TransactionManager;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;

public abstract class PersonDatasourceTransactionManager extends TransactionManager {

  /** Classe template da usare per transazioni su piu tabelle */
  public PersonDatasourceTransactionManager() {
    super(BaseServiceImpl.PERS_DATASOURCE);
  }
}
