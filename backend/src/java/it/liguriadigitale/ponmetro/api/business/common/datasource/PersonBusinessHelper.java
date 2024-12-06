package it.liguriadigitale.ponmetro.api.business.common.datasource;

import it.liguriadigitale.framework.business.BusinessHelper;
import it.liguriadigitale.framework.integration.dao.AbstractTableWithoutKeysDAO;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;

/**
 * @author scaldaferri
 */
public class PersonBusinessHelper extends BusinessHelper {

  /**
   * Classe helper da usare per operazioni CRUD su una singola tabella
   *
   * @param dao
   */
  public PersonBusinessHelper(AbstractTableWithoutKeysDAO dao) {
    super(dao, BaseServiceImpl.PERS_DATASOURCE);
  }
}
