package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;

public class AnnoScolasticoDropDownChoice extends FdCDropDownChoice<AnnoScolastico> {

  private static final long serialVersionUID = 5091704304873428104L;

  protected static Log log = LogFactory.getLog(AnnoScolasticoDropDownChoice.class);

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  public AnnoScolasticoDropDownChoice(String id, PropertyModel<AnnoScolastico> model) {
    super(id);

    setChoices(getListaAnniScolastici());

    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new AnnoScolasticoRender();

    setChoiceRenderer(render);
  }

  private List<AnnoScolastico> getListaAnniScolastici() {
    try {
      return ServiceLocator.getInstance().getServiziRistorazione().getAnniScolastici();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
