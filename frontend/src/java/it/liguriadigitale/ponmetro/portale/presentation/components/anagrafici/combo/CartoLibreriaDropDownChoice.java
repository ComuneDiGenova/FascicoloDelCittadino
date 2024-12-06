package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.CartolibreriaRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cartolibreria;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class CartoLibreriaDropDownChoice<T> extends DropDownChoice<T> {

  private static final long serialVersionUID = -8717645220679794999L;

  private static Log log = LogFactory.getLog(CartoLibreriaDropDownChoice.class);
  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public CartoLibreriaDropDownChoice(String id, IModel modello) {
    super(id, new ComboLoadableDetachableModel(getListaCartoLibrerie()));
    this.render = new CartolibreriaRenderer();
    setModel(modello);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Cartolibreria"));
    setChoiceRenderer(render);
  }

  private static List<Cartolibreria> getListaCartoLibrerie() {
    try {
      return ServiceLocator.getInstance().getCedoleLibrarie().elencoCartoliberie();
    } catch (BusinessException e) {
      log.error("Errore", e);
      return new ArrayList<Cartolibreria>();
    } catch (ApiException e) {
      log.error("Errore", e);
      return new ArrayList<Cartolibreria>();
    }
  }
}
