package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
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

public class ProvinciaBorseStudioDropDownChoice extends FdCDropDownChoice<Provincia> {

  private static final long serialVersionUID = -5864336233464498148L;

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  IModel<Provincia> model;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ProvinciaBorseStudioDropDownChoice(String id, IModel provincia) {
    super(id);

    this.model = provincia;

    this.inizializza(null);

    setChoices(getProvince());
    setModel(provincia);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new ProvinciaBorseStudioRender();
    setChoiceRenderer(render);
  }

  private List<Provincia> getProvince() {
    List<Provincia> lista = new ArrayList<>();
    try {
      lista = ServiceLocator.getInstance().getServiziBorseDiStudio().getPronvice();
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore province borse: " + e.getMessage(), e);
    }
    return lista;
  }

  @SuppressWarnings("unchecked")
  public void inizializza(String input) {

    log.debug("CP inizializza = " + input);

    if (input != null) {

      List<Provincia> lista = getProvince();

      this.setChoices(lista);

    } else if (model.getObject() != null) {

      List<Provincia> lista = new ArrayList<>();
      lista.add(model.getObject());
      this.setChoices(lista);
    }
  }
}
