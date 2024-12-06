package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
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

public class ComuneBorseStudioSelect2 extends FdCSelect2<Comune> {

  private static final long serialVersionUID = -2529273769830861176L;

  private Log log = LogFactory.getLog(getClass());

  private String codiceProvicia;

  public String getCodiceProvicia() {
    return codiceProvicia;
  }

  public void setCodiceProvicia(String codiceProvicia) {
    this.codiceProvicia = codiceProvicia;
  }

  public ComuneBorseStudioSelect2(
      String wicketId, IModel<Comune> modelField, Component pannello, String codiceProvicia) {
    super(wicketId, modelField, pannello);

    addOrReplace(combo = new ComuneBorseStudioDropDownChoice("combo", modelField, codiceProvicia));
    combo.setOutputMarkupId(true);
    combo.setOutputMarkupPlaceholderTag(true);
    addOrReplace(lblCombo = new Label("lblCombo", "Comune domicilio:"));
    lblCombo.add(new AttributeModifier("for", combo.getMarkupId()));

    this.codiceProvicia = codiceProvicia;
  }

  @Override
  protected List<LabelValue> getElementiCombo() {
    List<LabelValue> listaLabelValue = new ArrayList<LabelValue>();
    try {
      if (textField.getModelObject() != null) {

        List<Comune> listaComuni;

        listaComuni =
            ServiceLocator.getInstance()
                .getServiziBorseDiStudio()
                .getComuniSelect2(codiceProvicia, textField.getModelObject());

        combo.setChoices(listaComuni);

        for (Comune elemComune : listaComuni) {
          LabelValue labelValue = new LabelValue();
          labelValue.setId(elemComune.getCodice());
          labelValue.setDesc(elemComune.getDescrizione());

          listaLabelValue.add(labelValue);
        }
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore comuni borse: " + e.getMessage(), e);
    }

    return listaLabelValue;
  }
}
