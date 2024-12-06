package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.joda.time.LocalDate;

public class AnnoDropDownChoice extends DropDownChoice<Integer> {

  private static final long serialVersionUID = 124532453874L;

  protected static Log log = LogFactory.getLog(AnnoDropDownChoice.class);

  private final int NUMERI_ANNI_PRECEDENTI = 6;

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  public AnnoDropDownChoice(String id, IModel<Integer> model) {
    super(id);
    // TODO Auto-generated constructor stub
    setModel(model);
    setChoices(getChoises());
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    render = new AnnoRender();
  }

  private List<Integer> getChoises() {
    List<Integer> lista = new ArrayList<Integer>();

    int annoCorrente = LocalDate.now().getYear();

    for (int i = annoCorrente; i > (annoCorrente - NUMERI_ANNI_PRECEDENTI); i--) {
      lista.add(i);
    }

    log.debug("[Anno - getChoise] lista: " + lista);

    return lista;
  }
}
