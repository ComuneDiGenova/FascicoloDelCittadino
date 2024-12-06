package it.liguriadigitale.ponmetro.portale.presentation.components.comboselect2;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.componentifascicolo.LabelValue;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCSelect2;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class GeoserverComboSelect2<T> extends FdCSelect2<FeaturesGeoserver> {

  private static final long serialVersionUID = 6703842858135176351L;

  private Log log = LogFactory.getLog(getClass());

  private String indirizzo;

  public GeoserverComboSelect2(
      String wicketId, IModel<FeaturesGeoserver> modelField, Component pannello) {
    super(wicketId, modelField, pannello);

    addOrReplace(combo = new ComboGeoserver("combo", modelField));
    combo.setOutputMarkupId(true);
    combo.setOutputMarkupPlaceholderTag(true);
    combo.setLabel(Model.of(getString("GeoserverComboSelect2.indirizzo")));

    setIndirizzo("Indirizzo:");
  }

  public GeoserverComboSelect2(
      String wicketId,
      IModel<FeaturesGeoserver> modelField,
      Component pannello,
      String nomePannello) {
    super(wicketId, modelField, pannello, nomePannello);

    addOrReplace(combo = new ComboGeoserver("combo", modelField));
    combo.setOutputMarkupId(true);
    combo.setOutputMarkupPlaceholderTag(true);
    combo.setLabel(Model.of(getString("GeoserverComboSelect2.indirizzo")));

    setIndirizzo("Indirizzo:");
  }

  @Override
  protected List<LabelValue> getElementiCombo() {

    log.debug("CP getElementiCombo");

    List<LabelValue> listaLabelValue = new ArrayList<LabelValue>();

    if (textField.getModelObject() != null) {
      try {
        List<FeaturesGeoserver> listaGeoserver =
            ServiceLocator.getInstance()
                .getServiziGeoserver()
                .getGeoserver(textField.getModelObject());

        combo.setChoices(listaGeoserver);

        for (FeaturesGeoserver elemGeoserver : listaGeoserver) {
          LabelValue labelValue = new LabelValue();
          if (LabelFdCUtil.checkIfNotNull(elemGeoserver.getCODICE_INDIRIZZO())) {
            labelValue.setId(String.valueOf(elemGeoserver.getCODICE_INDIRIZZO()));
            labelValue.setDesc(elemGeoserver.getMACHINE_LAST_UPD());
          }

          listaLabelValue.add(labelValue);
        }
      } catch (BusinessException | ApiException e) {
        throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Toponomastica"));
      }
    }
    return listaLabelValue;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  @Override
  protected void onBeforeRender() {

    super.onBeforeRender();

    addOrReplace(lblCombo = new Label("lblCombo", getIndirizzo()));
    lblCombo.add(new AttributeModifier("for", textField.getMarkupId()));
  }
}
