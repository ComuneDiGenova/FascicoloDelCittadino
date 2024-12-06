package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.componentifascicolo.LabelValue;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCSelect2;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class ProvinciaBorseStudioSelect2 extends FdCSelect2<Provincia> {

  private static final long serialVersionUID = -6806596653363326543L;

  private Log log = LogFactory.getLog(getClass());

  public ProvinciaBorseStudioSelect2(
      String wicketId, IModel<Provincia> modelField, Component pannello) {
    super(wicketId, modelField, pannello);

    addOrReplace(combo = new ProvinciaBorseStudioDropDownChoice("combo", modelField));
    combo.setOutputMarkupId(true);
    combo.setOutputMarkupPlaceholderTag(true);
    addOrReplace(lblCombo = new Label("lblCombo", "Provincia domicilio:"));
    lblCombo.add(new AttributeModifier("for", combo.getMarkupId()));
  }

  @Override
  protected List<LabelValue> getElementiCombo() {

    List<LabelValue> listaLabelValue = new ArrayList<LabelValue>();
    try {
      if (textField.getModelObject() != null) {

        List<Provincia> listaProvince;

        listaProvince =
            ServiceLocator.getInstance()
                .getServiziBorseDiStudio()
                .getProvinciaSelect2(textField.getModelObject());

        combo.setChoices(listaProvince);

        for (Provincia elemProvincia : listaProvince) {
          LabelValue labelValue = new LabelValue();
          labelValue.setId(elemProvincia.getCodice());
          labelValue.setDesc(elemProvincia.getDescrizione());

          listaLabelValue.add(labelValue);
        }
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore province borse: " + e.getMessage(), e);
    }

    return listaLabelValue;
  }
}
