package it.liguriadigitale.ponmetro.api.business.common.test;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestConnectionFactory {

  private Log log = LogFactory.getLog(TestConnectionFactory.class);

  private static TestConnectionFactory instance;

  public static TestConnectionFactory getInstance() {
    if (null == instance) {
      instance = new TestConnectionFactory();
    }
    return instance;
  }

  public Connection getTestConnection() throws BusinessException {
    try {
      log.warn("---- UTILIZZO TEST CONNECTION ----");
      String driver = "oracle.jdbc.driver.OracleDriver";

      String url = "jdbc:oracle:thin:@10.170.0.28:1521:VARIETEST";
      String user = "LDFDCADM";
      String password = "$2LDFDCADM2$";

      Class.forName(driver);
      return DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
  }
}
