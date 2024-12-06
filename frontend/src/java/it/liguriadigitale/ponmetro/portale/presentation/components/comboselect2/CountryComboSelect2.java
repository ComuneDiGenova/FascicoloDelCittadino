package it.liguriadigitale.ponmetro.portale.presentation.components.comboselect2;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.componentifascicolo.LabelValue;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCSelect2;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.combo.ComboCountry;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.form.BibliotecheIscrizioneForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class CountryComboSelect2<T> extends FdCSelect2<TabellaPaeseRecord> {

  private static final long serialVersionUID = -8904934712755967830L;

  BibliotecheIscrizioneForm bibliotecheIscrizioneForm;

  public CountryComboSelect2(
      String wicketId,
      IModel<TabellaPaeseRecord> modelField,
      BibliotecheIscrizioneForm bibliotecheIscrizioneForm,
      Component pannello) {
    super(wicketId, modelField, pannello);
    this.bibliotecheIscrizioneForm = bibliotecheIscrizioneForm;
    addOrReplace(combo = new ComboCountry("combo", modelField));
    combo.setOutputMarkupId(true);
    combo.setOutputMarkupPlaceholderTag(true);
    addOrReplace(lblCombo = new Label("lblCombo", "Cittadinanza:"));
    lblCombo.add(new AttributeModifier("for", combo.getMarkupId()));
  }

  @Override
  protected List<LabelValue> getElementiCombo() {

    List<LabelValue> listaLabelValue = new ArrayList<LabelValue>();

    if (textField.getModelObject() != null) {
      try {
        List<TabellaPaeseRecord> listaPaesi =
            ServiceLocator.getInstance()
                .getServiziBiblioteche()
                .getPaesi(textField.getModelObject());

        combo.setChoices(listaPaesi);

        for (TabellaPaeseRecord tabellaRecord : listaPaesi) {
          LabelValue labelValue = new LabelValue();
          labelValue.setId(tabellaRecord.getCd());
          labelValue.setDesc(tabellaRecord.getDscr());

          listaLabelValue.add(labelValue);
        }
      } catch (BusinessException | ApiException | IOException e) {
        throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));
      }
    }
    return listaLabelValue;
  }

  /*
   * @Override protected void setModelloCombo() {
   *
   * TabellaRecord tabellaRecord = new TabellaRecord();
   * tabellaRecord.setCd(textFieldOut.getModelObject());
   * bibliotecheIscrizioneForm.getModelObject().setCountry(tabellaRecord);
   * comboCountry.setModelObject(tabellaRecord);
   *
   * }
   */

}
