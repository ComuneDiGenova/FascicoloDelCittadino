package it.liguriadigitale.ponmetro.portale.presentation.components.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class PanelClassFinder {

  protected static final Log log = LogFactory.getLog(PanelClassFinder.class);

  private PanelClassFinder() {
    super();
  }

  public static final String[] searchPackagesSezioni = {
    "it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.demografico",
    "it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita",
    "it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.scolastici",
    "it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.tributi",
    "it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.biblioteche",
    "it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.segnalazioni",
    "it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.virtuoso",
    "it.liguriadigitale.ponmetro.portale.presentation.pages.home.contatti"
  };

  private static Class<?> findPanelByNameUtil(String name, String[] whereSearchPackages) {
    for (String searchPackage : whereSearchPackages) {
      try {
        log.debug("looking for " + name + " in package: " + searchPackage);
        return Class.forName(searchPackage + "." + name);
      } catch (ClassNotFoundException e) {
        log.debug("[[Classe, in questo package, NON TROVATA: " + name + "]]");
      }
    }
    log.debug("Classe NON TROVATA (dopo): " + name);
    return EmptyPanel.class;
  }

  public static Class<?> findClassByNameSezioni(String name) {
    return findPanelByNameUtil(name, searchPackagesSezioni);
  }
}
