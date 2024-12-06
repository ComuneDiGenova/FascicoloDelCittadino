package it.liguriadigitale.ponmetro.api.business.common.datasource;

import it.liguriadigitale.framework.business.TransactionManager;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;

public abstract class PrivacyDatasourceTransactionManager extends TransactionManager {

  /** Classe template da usare per transazioni su piu tabelle */
  public PrivacyDatasourceTransactionManager() {
    super(BaseServiceImpl.PRIV_DATASOURCE);
  }
}
