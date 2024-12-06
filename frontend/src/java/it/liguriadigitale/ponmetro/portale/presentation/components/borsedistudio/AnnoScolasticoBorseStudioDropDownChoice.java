package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class AnnoScolasticoBorseStudioDropDownChoice extends FdCDropDownChoice<AnnoScolastico> {

  private static final long serialVersionUID = -320772032040955701L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public AnnoScolasticoBorseStudioDropDownChoice(String id, IModel annoScolastico) {
    super(id);

    setChoices(getAnniScolastici());
    setModel(annoScolastico);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new AnnoScolasticoBorseStudioRender();
    setChoiceRenderer(render);
  }

  private List<AnnoScolastico> getAnniScolastici() {
    List<AnnoScolastico> lista = new ArrayList<>();
    try {
      lista = ServiceLocator.getInstance().getServiziBorseDiStudio().getAnniScolastici();
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getAnniScolastici: " + e.getMessage(), e);
    }
    return lista;
  }
}
