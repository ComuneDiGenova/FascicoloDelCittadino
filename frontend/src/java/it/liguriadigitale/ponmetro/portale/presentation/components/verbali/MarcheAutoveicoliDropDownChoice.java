package it.liguriadigitale.ponmetro.portale.presentation.components.verbali;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
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

public class MarcheAutoveicoliDropDownChoice extends FdCDropDownChoice<String> {

  private static final long serialVersionUID = -320772032040955701L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public MarcheAutoveicoliDropDownChoice(String id, IModel marca) {
    super(id);

    setChoices(getMarche());
    setModel(marca);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    // setModel(Model.of("Marca autoveicoli"));
    // this.render = new MarcheAutoveicoliRender();
    setChoiceRenderer(render);
  }

  private List<String> getMarche() {
    List<String> lista = new ArrayList<>();
    try {
      lista = ServiceLocator.getInstance().getServiziMieiVerbali().getMarcheVeicoli();
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getMarche: " + e.getMessage(), e);
    }
    return lista;
  }
}
