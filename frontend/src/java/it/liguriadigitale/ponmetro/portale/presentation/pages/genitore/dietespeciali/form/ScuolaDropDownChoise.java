package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIstituto;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;

public class ScuolaDropDownChoise extends FdCDropDownChoice<DatiIstituto> {

  /** */
  private static final long serialVersionUID = 1L;

  protected static Log log = LogFactory.getLog(ScuolaDropDownChoise.class);

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  public ScuolaDropDownChoise(String id, PropertyModel<DatiIstituto> model, boolean isRequired) {
    super(id);

    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setRequired(isRequired);

    this.render = new ScuolaRender();

    setChoiceRenderer(render);
  }

  public void setListaScuola(String codiceDirezioneTerritoriale) {
    setChoices(getListaDatiIstituto(codiceDirezioneTerritoriale));
  }

  private List<DatiIstituto> getListaDatiIstituto(String codideDirezioneTerritoriale) {
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getListaDatiIstituto(codideDirezioneTerritoriale);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
