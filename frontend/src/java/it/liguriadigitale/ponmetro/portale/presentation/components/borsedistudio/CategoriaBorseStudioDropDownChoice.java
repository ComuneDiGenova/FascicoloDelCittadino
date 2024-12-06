package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Categoria;
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

public class CategoriaBorseStudioDropDownChoice extends FdCDropDownChoice<Categoria> {

  private static final long serialVersionUID = -40605951803612564L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public CategoriaBorseStudioDropDownChoice(String id, IModel categoria) {
    super(id);

    setChoices(getCategorie());
    setModel(categoria);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new CategoriaBorseStudioRender();
    setChoiceRenderer(render);
  }

  private List<Categoria> getCategorie() {
    List<Categoria> lista = new ArrayList<>();

    try {
      lista = ServiceLocator.getInstance().getServiziBorseDiStudio().getCategorie();
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore categorie : " + e.getMessage(), e);
    }

    return lista;
  }
}
