package it.liguriadigitale.ponmetro.api.business.common;

import it.liguriadigitale.framework.business.BusinessHelper;
import it.liguriadigitale.framework.integration.dao.AbstractTableWithoutKeysDAO;

/**
 * @author scaldaferri
 */
public class PonMetroBusinessHelper extends BusinessHelper {

  /**
   * Classe helper da usare per operazioni CRUD su una singola tabella
   *
   * @param dao
   */
  public PonMetroBusinessHelper(AbstractTableWithoutKeysDAO dao) {
    super(dao, BaseServiceImpl.FDCT_DATASOURCE);
  }

  // @Override
  // protected Connection getConnection() throws BusinessException {
  //
  // try {
  // return super.getConnection();
  // } catch (Exception e) {
  // return TestConnectionFactory.getInstance().getTestConnection();
  // }
  // }
}
