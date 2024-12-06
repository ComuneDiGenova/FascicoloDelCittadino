package it.liguriadigitale.ponmetro.api.business.common;

import it.liguriadigitale.framework.business.TransactionManager;

public abstract class PonMetroDatasourceTransactionManager extends TransactionManager {

  /** Classe template da usare per transazioni su piu' tabelle */
  public PonMetroDatasourceTransactionManager() {
    super(BaseServiceImpl.FDCT_DATASOURCE);
  }
}
