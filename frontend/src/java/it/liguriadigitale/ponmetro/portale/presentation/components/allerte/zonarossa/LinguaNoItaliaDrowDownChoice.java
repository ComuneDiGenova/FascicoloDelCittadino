package it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Lingua;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class LinguaNoItaliaDrowDownChoice<T> extends FdCDropDownChoice<T> {

  protected static Log log = LogFactory.getLog(LinguaNoItaliaDrowDownChoice.class);

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public LinguaNoItaliaDrowDownChoice(String id, IModel modello) {
    super(id);

    this.render = new LinguaRender();

    setModel(modello);
    setChoiceRenderer(render);
    setNullValid(false);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Altra lingua"));

    setChoices(new ComboLoadableDetachableModel(getListaLingua()));
  }

  private static List<Lingua> getListaLingua() {
    try {
      return ServiceLocator.getInstance().getServiziAllerteZonaRossa().getLingue();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte zona rossa"));
    }
  }
}
