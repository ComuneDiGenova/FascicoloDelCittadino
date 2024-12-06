package it.liguriadigitale.ponmetro.api.business.common.datasource;

import it.liguriadigitale.framework.business.BusinessHelper;
import it.liguriadigitale.framework.integration.dao.AbstractTableWithoutKeysDAO;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;

/**
 * @author scaldaferri
 */
public class PrivacyBusinessHelper extends BusinessHelper {

  /**
   * Classe helper da usare per operazioni CRUD su una singola tabella
   *
   * @param dao
   */
  public PrivacyBusinessHelper(AbstractTableWithoutKeysDAO dao) {
    super(dao, BaseServiceImpl.PRIV_DATASOURCE);
  }
}
