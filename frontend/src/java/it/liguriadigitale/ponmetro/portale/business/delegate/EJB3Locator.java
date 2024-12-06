package it.liguriadigitale.ponmetro.portale.business.delegate;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import java.util.Hashtable;
import javax.naming.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EJB3Locator extends BaseServiceImpl {

  private static Log log = LogFactory.getLog(EJB3Locator.class);

  public static EJB3Locator getInstance() {
    return new EJB3Locator();
  }

  @SuppressWarnings({"unchecked", "rawtypes", "unused"})
  private Hashtable getEnviroment() {
    final Hashtable prop = new Hashtable();
    prop.put("jboss.naming.client.ejb.context", true);
    prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
    return prop;
  }

  @SuppressWarnings({"rawtypes", "unused"})
  private Object lookup(final java.util.Hashtable environment, final String jndiName)
      throws javax.naming.NamingException {
    final javax.naming.InitialContext initialContext = new javax.naming.InitialContext(environment);
    LogFactory.getLog(BaseServiceImpl.class).debug("CHIAMATA EJB : " + jndiName);
    try {
      final Object objRef = initialContext.lookup(jndiName);
      LogFactory.getLog(BaseServiceImpl.class).debug("CHIAMATA EJB : " + jndiName);
      return objRef;
    } catch (final Exception e) {
      log.error("ERRORE DISASTROSO ", e);
      throw new Error("Errore inizializzazione business: " + e.getMessage());
    } finally {
      initialContext.close();
    }
  }
}
