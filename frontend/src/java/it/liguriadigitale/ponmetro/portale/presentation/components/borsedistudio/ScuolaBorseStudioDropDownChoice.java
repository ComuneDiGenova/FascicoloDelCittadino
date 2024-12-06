package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Categoria;
import it.liguriadigitale.ponmetro.borsestudio.model.Scuola;
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

public class ScuolaBorseStudioDropDownChoice extends FdCDropDownChoice<Scuola> {

  private static final long serialVersionUID = 827439778064245160L;

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ScuolaBorseStudioDropDownChoice(String id, IModel scuola, Categoria categoria) {
    super(id);

    if (LabelFdCUtil.checkIfNotNull(categoria)
        && LabelFdCUtil.checkIfNotNull(categoria.getCodice())) {
      setChoices(getScuole(categoria.getCodice()));
    }

    setModel(scuola);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new ScuolaBorseStudioRender();
    setChoiceRenderer(render);
  }

  public List<Scuola> getScuole(String codiceCategoria) {
    List<Scuola> lista = new ArrayList<>();
    try {
      lista = ServiceLocator.getInstance().getServiziBorseDiStudio().getScuole(codiceCategoria);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore scuole : " + e.getMessage(), e);
    }

    setChoices(lista);

    return lista;
  }

  //	public Scuola getScuola(String codiceScuola, String codiceCategoria) {
  //		Scuola scuola = null;
  //		try {
  //			scuola = ServiceLocator.getInstance().getServiziBorseDiStudio().getScuola(codiceScuola,
  // codiceCategoria);
  //		} catch (BusinessException | ApiException | IOException e) {
  //			log.error("Errore scuola borsa: " + e.getMessage(), e);
  //		}
  //
  //		setDefaultModelObject(scuola);
  //
  //		return scuola;
  //	}
}
