package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * The Class CardLabel.
 *
 * @param <T> the generic type
 */
public class CardLabel<T extends Component> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  /** The Constant WICKET_ID_LABEL. */
  protected static final String WICKET_ID_LABEL = "valore";

  /** The Constant WICKET_ID_DESCRIZIONE. */
  private static final String WICKET_ID_DESCRIZIONE = "descrizione";

  /**
   * Instantiates a new card label.
   *
   * @param id the id
   * @param value the value
   * @param istanza the pannello
   *     <p>Costruttore standard il quale creerà la label "Nome Valore"
   *     <p>Esempio: Nome: Marco "Etichetta" "Valore"
   *     <p>con l'id passato verrà generato il nome della label e la sua etichetta, il valore
   *     rappresenterà il risultato della variabile passata, mentre l'istanza sarà necessaria per
   *     estrarre il nome della classe sulla quale verrà posta la label e conseguente file html e
   *     properties, la quale avrà il nome composto con NomeDellaClasse.id
   */
  public CardLabel(String id, Object value, T istanza) {
    super(id);

    creaLabelValore(value);
    creaLabelEtichettaGenerandoResourceId(istanza, id);
  }

  /**
   * @param id
   * @param value
   * @param pannello
   * @param resourceId
   *     <p>Questo costruttore verrà utilizzato nel caso in cui il resourceId (nome della proprietà
   *     nel file di proprietà) dovrà avere un nome specifico, chiamando un metodo differente per
   *     NON GENERARE il resourceId
   */
  public CardLabel(String id, Object value, T pannello, String resourceId) {
    super(id);

    creaLabelValore(value);
    creaLabelEtichetta(pannello, resourceId);
  }

  /**
   * Crea label etichetta.
   *
   * @param pannello the pannello
   * @param id the id
   *     <p>Questo metodo, richiamato dal costruttore standard, grazie all'utilizzo dell istanza e
   *     dell'id richiamerà il metodo per la creazione della label dell'etichetta dopo aver estratto
   *     il nome della classe su cui verrà posizionato il pannello e aver generato il nome della
   *     proprietà da ricercare.
   */
  private void creaLabelEtichettaGenerandoResourceId(T pannello, String id) {
    String nomePannello = pannello.getClass().getSimpleName();
    String resourceId = nomePannello + "." + id;
    creaLabelEtichetta(pannello, resourceId);
  }

  /**
   * @param pannello
   * @param resourceId
   *     <p>Questo metodo grazie all'utilizzo dell istanza e dell'id richiamerà il metodo per la
   *     creazione della label dell'etichetta dopo aver estratto il nome della classe su cui verrà
   *     posizionato il pannello e aver recuperato il nome della proprietà da utilizzare nel file di
   *     properties.
   */
  private void creaLabelEtichetta(T pannello, String resourceId) {
    String etichetta = getLocalizer().getString(resourceId, pannello);
    addOrReplace(new NotEmptyLabel(WICKET_ID_DESCRIZIONE, etichetta));
  }

  /**
   * Crea label valore.
   *
   * @param value the value
   *     <p>Il valore sarà invece generato da questo metodo il quale creerà la label in base anche
   *     al tipo di valore che si desidera mostrare.
   */
  protected void creaLabelValore(Object value) {
    if (value instanceof String) {
      addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, (String) value));
    } else if (value instanceof LocalDate) {
      addOrReplace(new NotEmptyLocalDateLabel(WICKET_ID_LABEL, (LocalDate) value));
    } else if (value instanceof Double) {
      addOrReplace(new ImportoEuroLabel(WICKET_ID_LABEL, (Double) value));
    } else if (value instanceof Long) {
      addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, (Long) value));
    } else if (value instanceof Integer) {
      addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, value.toString()));
    } else if (value instanceof Boolean) {
      addOrReplace(new SiNoLabel(WICKET_ID_LABEL, (Boolean) value));
    } else if (value instanceof BigDecimal) {
      addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, value.toString()));
    } else if (value instanceof Float) {
      addOrReplace(new ImportoEuroLabel(WICKET_ID_LABEL, (Float) value));
    } else {
      addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, ""));
    }
  }
}
