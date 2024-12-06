package it.liguriadigitale.ponmetro.portale.business.common;

import it.liguriadigitale.framework.business.BusinessHelper;
import it.liguriadigitale.framework.integration.dao.AbstractTableWithoutKeysDAO;

/**
 * @author scaldaferri
 */
public class SkeletonBusinessHelper extends BusinessHelper {

  /**
   * Classe helper da usare per operazioni CRUD su una singola tabella
   *
   * @param dao
   */
  public SkeletonBusinessHelper(AbstractTableWithoutKeysDAO dao) {
    super(dao, "");
  }
}
