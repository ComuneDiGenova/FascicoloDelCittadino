package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Parentela;
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

public class ParentelaBorseStudioDropDownChoice extends FdCDropDownChoice<Parentela> {

  private static final long serialVersionUID = 1142636142730091007L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ParentelaBorseStudioDropDownChoice(String id, IModel parentela) {
    super(id);

    setChoices(getParentele());
    setModel(parentela);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new ParentelaBorseStudioRender();
    setChoiceRenderer(render);
  }

  private List<Parentela> getParentele() {
    List<Parentela> lista = new ArrayList<>();
    try {
      lista = ServiceLocator.getInstance().getServiziBorseDiStudio().getParentele();
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getParentele: " + e.getMessage(), e);
    }
    return lista;
  }
}
