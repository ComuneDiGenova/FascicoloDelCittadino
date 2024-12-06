package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.MenuMotiviReligiosiRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

public class MenuMotiviReligiosiDropDownChoise
    extends FdCDropDownChoice<DietaMotiviEticoReligiosi> {

  private static final long serialVersionUID = 3120446573992174153L;

  protected static Log log = LogFactory.getLog(MenuMotiviReligiosiDropDownChoise.class);

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  public MenuMotiviReligiosiDropDownChoise(String id, IModel<DietaMotiviEticoReligiosi> model) {
    super(id);

    setChoices(popolaListaMenuReligiosi());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new MenuMotiviReligiosiRender();

    setChoiceRenderer(render);
  }

  protected void setOptionAttributes(
      AppendingStringBuffer buffer, DietaMotiviEticoReligiosi choice, int index, String selected) {
    if (choice != null) {
      log.debug("buffer.nome =" + choice.getDescrizione());
      log.debug("buffer.append =" + choice.getCodice());
    }

    super.setOptionAttributes(buffer, choice, index, selected);
  }

  private static List<DietaMotiviEticoReligiosi> popolaListaMenuReligiosi() {
    try {
      return ServiceLocator.getInstance().getServiziRistorazione().getListaMenuMotiviReligiosi();
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
