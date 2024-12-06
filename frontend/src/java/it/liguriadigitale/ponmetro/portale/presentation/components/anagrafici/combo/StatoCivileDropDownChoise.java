package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati.ListItemRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoStatoCivileResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class StatoCivileDropDownChoise<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = -4638380039572902275L;

  protected static Log log = LogFactory.getLog(StatoCivileDropDownChoise.class);

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public StatoCivileDropDownChoise(String id, IModel modello) {
    super(id);

    this.render = new ListItemRenderer();

    setModel(modello);
    setChoiceRenderer(render);
    setNullValid(false);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona Stato Civile"));

    setChoices(new ComboLoadableDetachableModel(getListaStatoCivile()));
  }

  private static List<ListItem> getListaStatoCivile() {
    try {
      GetElencoStatoCivileResponseGenericResponse response =
          ServiceLocator.getInstance().getServiziAnagrafici().getStatoCivile();
      return response.getResult().getStatiCivili();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
