package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.model.IModel;

public class ComboCountry extends FdCDropDownChoice<TabellaPaeseRecord> {

  private static final long serialVersionUID = -6765798125295641058L;

  protected Log log = LogFactory.getLog(this.getClass());

  IModel<TabellaPaeseRecord> model;

  public ComboCountry(String id, IModel<TabellaPaeseRecord> model) {
    super(id);
    this.model = model;
    this.inizializza(null);
    this.setOutputMarkupId(true);
    this.setOutputMarkupPlaceholderTag(true);
    this.setChoiceRenderer(new TabellaRecordRenderer());
    this.setModel(model);
  }

  @SuppressWarnings("unchecked")
  public void inizializza(String input) {

    if (input != null) {
      try {
        List<TabellaPaeseRecord> listaPaesi =
            ServiceLocator.getInstance().getServiziBiblioteche().getPaesi(input);

        this.setChoices(listaPaesi);

      } catch (BusinessException | ApiException | IOException e) {
        throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));
      }
    } else if (model.getObject() != null) {

      List<TabellaPaeseRecord> listaPaesi = new ArrayList<>();
      listaPaesi.add(model.getObject());
      this.setChoices(listaPaesi);
    }
  }
}
