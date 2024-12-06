package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.aggiungitelefono;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto.LinguaEnum;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ContattoTelefonicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.LinguaDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.LinguaNoItaliaDrowDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.TipoTelefonoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiTelefonoPanel extends BasePanel {

  private static final long serialVersionUID = 7286243895258671407L;

  private int index;

  @SuppressWarnings("rawtypes")
  LinguaNoItaliaDrowDownChoice linguaNoItalia;

  public DatiTelefonoPanel(String id, ContattoTelefonicoZonaRossa contattoTelefonico, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati(contattoTelefonico);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    ContattoTelefonicoZonaRossa contattoTelefonico = (ContattoTelefonicoZonaRossa) dati;

    addOrReplace(new FdCTitoloPanel("titolo", getString("DatiTelefonoPanel.titolo")));

    FdCPhoneNumberField numero =
        new FdCPhoneNumberField(
            "numero", new PropertyModel(contattoTelefonico, "numero"), DatiTelefonoPanel.this);
    numero.getTextField().setRequired(true);
    addOrReplace(numero);

    TipoTelefonoDropDownChoice tipo =
        new TipoTelefonoDropDownChoice("tipo", new PropertyModel(contattoTelefonico, "tipo"));
    tipo.setRequired(true);
    tipo.setLabel(Model.of("Tipo telefono"));
    addOrReplace(tipo);

    LinguaDropDownChoice lingua =
        new LinguaDropDownChoice("lingua", new PropertyModel(contattoTelefonico, "lingua"));
    lingua.setLabel(Model.of("Lingua"));
    addOrReplace(lingua);

    // wmkLinguaNoItalia

    WebMarkupContainer wmkLinguaNoItalia = new WebMarkupContainer("wmkLinguaNoItalia");

    wmkLinguaNoItalia.setOutputMarkupId(true);
    wmkLinguaNoItalia.setOutputMarkupPlaceholderTag(true);

    addOrReplace(wmkLinguaNoItalia);

    linguaNoItalia =
        new LinguaNoItaliaDrowDownChoice(
            "linguaNoItalia", new PropertyModel(contattoTelefonico, "linguaNoItalia"));
    linguaNoItalia.setLabel(Model.of("Lingua non italiana"));
    linguaNoItalia.setNullValid(true);
    wmkLinguaNoItalia.addOrReplace(linguaNoItalia);

    lingua.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 4567199981239849845L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            boolean linguaNoItaliaAbilitata =
                lingua.getModelObject().equals(LinguaEnum.BUONA_SOLO_SE_IN_LINGUA_STRANIERA);
            linguaNoItalia.setEnabled(linguaNoItaliaAbilitata);

            linguaNoItalia.setRequired(linguaNoItaliaAbilitata);

            if (!linguaNoItaliaAbilitata) {

              target.appendJavaScript("");
              linguaNoItalia.setModelObject(null);
            }

            // target.appendJavaScript("$('#" + linguaNoItalia.getMarkupId() +
            // "').selectpicker('');");

            target.add(wmkLinguaNoItalia);
          }
        });
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
