package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ComuneBorseStudioDropDownChoice extends FdCDropDownChoice<Comune> {

  private static final long serialVersionUID = -6870724726412393251L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ComuneBorseStudioDropDownChoice(String id, IModel comune, String codiceProvincia) {
    super(id);

    if (LabelFdCUtil.checkIfNotNull(codiceProvincia)) {
      setChoices(getListaComuni(codiceProvincia));
    }

    setModel(comune);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new ComuneBorseStudioRender();
    setChoiceRenderer(render);
  }

  public List<Comune> getListaComuni(String codiceProvincia) {
    List<Comune> lista = new ArrayList<>();
    try {
      lista = ServiceLocator.getInstance().getServiziBorseDiStudio().getComuni(codiceProvincia);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore comuni: " + e.getMessage(), e);
    }

    setChoices(lista);

    return lista;
  }
}
