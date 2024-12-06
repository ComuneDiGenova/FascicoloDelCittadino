package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class SottofascicoloDropDownChoice extends DropDownChoice<CzrmSottoFascicoli> {

  private static final long serialVersionUID = -1321313165489713L;

  private Log log = LogFactory.getLog(getClass());

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public SottofascicoloDropDownChoice(String id, IModel sottofascicolo) {
    super(id);
    // TODO Auto-generated constructor stub
    setChoices(getSottofascicoli());
    setModel(sottofascicolo);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new SottofascicoloRender();
    setChoiceRenderer(render);
  }

  private List<CzrmSottoFascicoli> getSottofascicoli() {
    try {
      return ServiceLocator.getInstance().getHelpCzRMService().getSottofascicolo();
    } catch (ApiException | BusinessException e) {
      // TODO Auto-generated catch block
      log.debug(
          String.format(
              "[GetSottofascicoli] Errore Recupero Sotto fascicoli: %s",
              e.getCause().getMessage()));
      return new ArrayList<CzrmSottoFascicoli>();
    }
  }
}
