package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati.ListItemRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetProvenienzaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ProvenienzaDropDownChoise<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = 8547290145445483248L;

  protected static Log log = LogFactory.getLog(ProvenienzaDropDownChoise.class);

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ProvenienzaDropDownChoise(String id, IModel modello) {
    super(id);

    this.render = new ListItemRenderer();

    setModel(modello);
    setChoiceRenderer(render);
    setNullValid(false);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona Provenienza"));

    setChoices(new ComboLoadableDetachableModel(getListaProvenienza()));
  }

  private static List<ListItem> getListaProvenienza() {
    try {
      GetProvenienzaResponseGenericResponse response =
          ServiceLocator.getInstance().getServiziAnagrafici().getProvenienza();
      return response.getResult().getTipiProvenienza();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
