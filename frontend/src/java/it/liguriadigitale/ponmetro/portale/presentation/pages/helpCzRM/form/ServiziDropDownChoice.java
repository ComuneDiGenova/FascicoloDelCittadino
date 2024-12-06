package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.render.ServizioHelpCzrmRender;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ServiziDropDownChoice extends DropDownChoice<CzrmServizi> {

  private static final long serialVersionUID = -1321313165489713L;

  private Log log = LogFactory.getLog(getClass());

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ServiziDropDownChoice(String id, IModel servizio, List<CzrmServizi> lista) {
    super(id);
    setChoices(lista);
    setModel(servizio);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new ServizioHelpCzrmRender();
    setChoiceRenderer(render);
  }
}
