package it.liguriadigitale.ponmetro.portale.presentation.pages.integrazioneclassic2.panel;

import com.google.common.base.Strings;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.Model;

public class IntegrazioneFdCClassicFdC2Panel extends BasePanel {

  private static final long serialVersionUID = 4989394577093682883L;

  public IntegrazioneFdCClassicFdC2Panel(String id, String servizioFdC2) {
    super(id);

    fillDati(servizioFdC2);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {

    String servizioFdC2 = (String) dati;

    log.debug("servizioFdC2 = " + servizioFdC2);

    Form<Void> form =
        new Form("form", null) {

          private static final long serialVersionUID = 6945256023044148017L;

          @Override
          protected String getActionUrl() {
            return servizioFdC2;
          }
        };

    HiddenField comge_codicefiscale =
        new HiddenField<>("comge_codicefiscale", Model.of(getUtente().getCodiceFiscaleOperatore()));
    form.addOrReplace(comge_codicefiscale);

    HiddenField fdc_classic = new HiddenField("fdc_classic", Model.of("true"));
    form.addOrReplace(fdc_classic);

    HiddenField comge_emailAddress =
        new HiddenField<>(
            "comge_emailAddress", Model.of(Strings.emptyToNull(getUtente().getMail())));
    form.addOrReplace(comge_emailAddress);

    HiddenField comge_cellulare =
        new HiddenField<>(
            "comge_cellulare", Model.of(Strings.emptyToNull(getUtente().getMobile())));
    form.addOrReplace(comge_cellulare);

    HiddenField comge_nome =
        new HiddenField<>("comge_nome", Model.of(Strings.emptyToNull(getUtente().getNome())));
    form.addOrReplace(comge_nome);

    HiddenField comge_cognome =
        new HiddenField<>("comge_cognome", Model.of(Strings.emptyToNull(getUtente().getCognome())));
    form.addOrReplace(comge_cognome);

    HiddenField comge_sesso =
        new HiddenField<>("comge_sesso", Model.of(Strings.emptyToNull(getUtente().getSesso())));
    form.addOrReplace(comge_sesso);

    HiddenField residente = new HiddenField<>("residente", Model.of(getUtente().isResidente()));
    form.addOrReplace(residente);

    form.setMarkupId("form");

    addOrReplace(form);

    setOutputMarkupId(true);
  }
}
