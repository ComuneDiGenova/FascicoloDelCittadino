/** */
package it.liguriadigitale.ponmetro.portale.presentation.roles;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;

/**
 * @author scaldaferri
 */
public class RolesUtil {

  enum RUOLI_PORTALE {
    RW_USER,
    RO_USER
  };

  private static RolesUtil istanza;

  public static RolesUtil getInstance() {

    if (istanza != null) return istanza;
    else {
      istanza = new RolesUtil();
      return istanza;
    }
  }

  /**
   * Metodo che permette di associare i ruoli al Componente Wicket
   *
   * @param componente
   * @param clazz
   * @throws BusinessException
   */
  public void autorizzaFunzione(final Component componente, final Class<? extends Component> clazz)
      throws BusinessException {

    String listaRuoliAutorizzati = getListaRuoliAutorizzati(componente, clazz);
    MetaDataRoleAuthorizationStrategy.authorize(
        componente, Component.RENDER, listaRuoliAutorizzati);
  }

  /**
   * Data una classe Page di Wicket e un WicketID restituisce i ruoli che possono visualizzare quel
   * Componente
   *
   * @param componente
   * @param clazz
   * @return String listaDeiRuoli
   */
  public String getListaRuoliAutorizzati(
      final Component componente, final Class<? extends Component> clazz) {

    // Metodo Fake restituisce sempre il ruolo
    // che può leggere/scrivere i dati
    // In una situazione più complessa potrebbe leggere da un DB
    // o da un file di configurazione

    return RUOLI_PORTALE.RW_USER.name();
  }
}
