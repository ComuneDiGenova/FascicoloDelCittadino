package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.ParentelaRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoParenteleResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Parentela;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ParenteleDropDownChoise<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = -5405503125502783191L;

  protected static Log log = LogFactory.getLog(ParenteleDropDownChoise.class);

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ParenteleDropDownChoise(String id, IModel modello) {
    super(id);

    this.render = new ParentelaRender();

    setModel(modello);
    setChoiceRenderer(render);
    setNullValid(false);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona Nuova Parentela"));

    setChoices(new ComboLoadableDetachableModel(getListaParentele()));
  }

  private static List<Parentela> getListaParentele() {
    try {
      GetElencoParenteleResponseGenericResponse response =
          ServiceLocator.getInstance().getServiziAnagrafici().getElencoParentele();
      return response.getResult().getParentele();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
