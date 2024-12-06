package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;

public class DirezioneTerritorialeDropDownChoise
    extends FdCDropDownChoice<DatiDirezioneTerritoriale> {

  /** */
  private static final long serialVersionUID = 1L;

  protected static Log log = LogFactory.getLog(DirezioneTerritorialeDropDownChoise.class);

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  public DirezioneTerritorialeDropDownChoise(
      String id, PropertyModel<DatiDirezioneTerritoriale> model) {
    super(id);
    // TODO Auto-generated constructor stub

    setChoices(getListaTipi());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new DirezioneTerritorialeRender();

    setChoiceRenderer(render);
  }

  private List<DatiDirezioneTerritoriale> getListaTipi() {
    // TODO Auto-generated method stub
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getListaDatiDirezioneTerritoriali();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
