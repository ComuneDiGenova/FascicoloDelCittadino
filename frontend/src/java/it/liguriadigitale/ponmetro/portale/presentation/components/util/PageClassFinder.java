package it.liguriadigitale.ponmetro.portale.presentation.components.util;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PageClassFinder {

  protected static final Log log = LogFactory.getLog(PageClassFinder.class);

  private PageClassFinder() {
    super();
  }

  private static Class<?> findClassByNameUtil(String name, String[] whereSearchPackages) {
    for (String searchPackage : whereSearchPackages) {
      try {
        Class<?> clazz = Class.forName(searchPackage + "." + name);
        log.debug("trovata classe " + name + " in package: " + searchPackage);
        return clazz;
      } catch (ClassNotFoundException e) {
        log.trace("[[Classe " + name + " NON TROVATA, nel package, : " + searchPackage + "]]");
      }
    }
    if (name == null || (name.equalsIgnoreCase("null"))) {
      log.error("Impossibile cercare Classe null in PageClassFinder");
    } else {
      log.error("Classe " + name + " NON TROVATA nei package salvati in PageClassFinder");
    }
    return Page404.class;
  }

  public static Class<?> findClassByName(String name) {
    return findClassByNameUtil(name, BaseServiceImpl.searchPackages);
  }

  public static Class<?> findClassByNameWidget(String name) {
    return findClassByNameUtil(name, BaseServiceImpl.searchPackagesWidget);
  }

  public static Class<?> findClassByNameFunction(String name) {
    return findClassByNameUtil(name, BaseServiceImpl.searchPackagesFunction);
  }
}
