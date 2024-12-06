package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati.ListItemCodiceFDCRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetTipoIscrizioneResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TipologiaIscrizioneDropDownChoise<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = 4609785597691000363L;

  protected static Log log = LogFactory.getLog(TipologiaIscrizioneDropDownChoise.class);

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public TipologiaIscrizioneDropDownChoise(String id, IModel modello, Integer tipoPratica) {
    super(id);

    this.render = new ListItemCodiceFDCRender();

    setModel(modello);
    setChoiceRenderer(render);
    setNullValid(false);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona tipologia di iscrizione"));

    setChoices(new ComboLoadableDetachableModel(getListaTipiIscrizione(tipoPratica)));
  }

  private static List<ListItem> getListaTipiIscrizione(Integer tipoPratica) {
    try {
      // promemoria
      /*
       * Integer richiestaResidenza = 1; Integer cambioIndirizzo = 2;
       * richiesta.setTipoPratica(2);
       */

      GetTipoIscrizioneResponseGenericResponse response =
          ServiceLocator.getInstance().getServiziAnagrafici().getTipologiaIscrizione(tipoPratica);
      return response.getResult().getTipiIscrizione();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
