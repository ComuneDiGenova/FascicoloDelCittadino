package it.liguriadigitale.ponmetro.portale.business.common;

import it.liguriadigitale.framework.business.TransactionManager;

public abstract class SkeletonDatasourceTransactionManager extends TransactionManager {

  /** Classe template da usare per transazioni su più tabelle */
  public SkeletonDatasourceTransactionManager() {
    super("");
  }
}
