package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati.ListItemRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTitoliStudioResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TitoloStudioDropDownChoise<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = 7512940346137505766L;

  protected static Log log = LogFactory.getLog(TitoloStudioDropDownChoise.class);

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public TitoloStudioDropDownChoise(String id, IModel modello) {
    super(id);

    this.render = new ListItemRenderer();

    setModel(modello);
    setChoiceRenderer(render);
    setNullValid(false);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona Titolo di studio"));

    setChoices(new ComboLoadableDetachableModel(getListaTitoliDiStudio()));
  }

  private static List<ListItem> getListaTitoliDiStudio() {
    try {
      GetTitoliStudioResponseGenericResponse response =
          ServiceLocator.getInstance().getServiziAnagrafici().getTitoliDiStudio();
      return response.getResult().getTitoliStudio();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
