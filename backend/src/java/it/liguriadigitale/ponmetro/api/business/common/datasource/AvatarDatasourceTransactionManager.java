package it.liguriadigitale.ponmetro.api.business.common.datasource;

import it.liguriadigitale.framework.business.TransactionManager;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;

public abstract class AvatarDatasourceTransactionManager extends TransactionManager {

  public AvatarDatasourceTransactionManager() {
    super(BaseServiceImpl.FDCT_DATASOURCE);
  }
}
