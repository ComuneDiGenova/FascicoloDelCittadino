package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep1;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AgevolazioneTariffariaRistorazione;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class RichiestaAgevolazioneStep1Form extends AbstracFrameworkForm<AgevolazioneStep1> {

  private static final long serialVersionUID = -5422086731044157736L;
  private ListView<AgevolazioneTariffariaRistorazione> listView;

  public RichiestaAgevolazioneStep1Form(String id, AgevolazioneStep1 model) {
    super(id, model);
  }

  @Override
  protected void onValidateModelObjects() {
    super.onValidate();

    if (hasError()) {
      return;
    }
    boolean selezionato = false;
    for (AgevolazioneTariffariaRistorazione iscritto :
        getModelObject().getListaFigliPerRichiesta()) {
      if (iscritto.isSelezionato()) {
        selezionato = true;
      }
    }
    if (!selezionato) {
      listView.error(getString("RichiestaAgevolazionePanel.SelezioneVuota"));
    }
  }

  @Override
  public void addElementiForm() {

    AgevolazioneStep1 model = getModelObject();

    listView =
        new ListView<AgevolazioneTariffariaRistorazione>(
            "lista", getModelObject().getListaFigliPerRichiesta()) {

          private static final long serialVersionUID = 8155193191486019902L;

          @Override
          protected void populateItem(ListItem<AgevolazioneTariffariaRistorazione> item) {
            AgevolazioneTariffariaRistorazione iscritto = item.getModelObject();

            if (iscritto
                .getCodiceFiscale()
                .equalsIgnoreCase(model.getIscrittoSelezionato().getCodiceFiscale())) {
              iscritto.setSelezionato(true);
            }

            item.addOrReplace(
                new Label("nomeCognome", iscritto.getNome() + " " + iscritto.getCognome()));
            item.addOrReplace(new LocalDateLabel("dataNascita", iscritto.getDataNascita()));
            Integer annoScolatiscoInt = Integer.parseInt(iscritto.getAnnoScolastico());
            String as = String.valueOf(annoScolatiscoInt + 1);
            String annoScolastico = iscritto.getAnnoScolastico().concat("/").concat(as);
            item.addOrReplace(new Label("annoScolastico", annoScolastico));
            item.addOrReplace(
                new Label("statoRichiesta", iscritto.getStatoRichiestaAgevolazione()));

            Double iseeMinorenne = Double.parseDouble(iscritto.getImportoIndicatoreIseeBambino());
            BigDecimal iseeMinorenneDouble = BigDecimal.valueOf(iseeMinorenne);
            NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
            String valoreIseeMinDelBambino = formatter.format(iseeMinorenneDouble);

            item.addOrReplace(
                new Label("valoreIseeMinore", valoreIseeMinDelBambino)
                    .setVisible(PageUtil.isStringValid(valoreIseeMinDelBambino)));

            CheckBox check =
                new CheckBox("check", new PropertyModel<Boolean>(iscritto, "selezionato"));
            check.setMarkupId("checkField" + item.getMarkupId());
            item.add(check);

            Label lblCheck = new Label("lblCheck", Model.of("Selezionato"));
            lblCheck.add(new AttributeModifier("for", "checkField" + item.getMarkupId()));
            item.add(lblCheck);
          }
        };
    add(listView);
  }

  public class TrueValidator implements IValidator<Boolean> {

    private static final long serialVersionUID = 7162592109443001630L;

    @Override
    public void validate(IValidatable<Boolean> validatable) {
      if (!Boolean.TRUE.equals(validatable.getValue())) {
        validatable.error(new ValidationError(this));
      }
    }
  }
}
