package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.panel;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCLocalDateField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.time.LocalDate;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class DatiPersonaliPanel extends BasePanel {

  /** */
  private static final long serialVersionUID = 1L;

  public DatiPersonaliPanel(
      String id, CompoundPropertyModel<DatiRichiestaIstanzaPl> datiRichiestaIstanzaPl) {
    super(id);
    // TODO Auto-generated constructor stub

    fillDati(datiRichiestaIstanzaPl);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    CompoundPropertyModel<DatiRichiestaIstanzaPl> cDatiRichiestaIstanzaPl =
        (CompoundPropertyModel<DatiRichiestaIstanzaPl>) dati;

    DatiRichiestaIstanzaPl datiRichiestaIstanzaPl = cDatiRichiestaIstanzaPl.getObject();

    addDatiPersonali(datiRichiestaIstanzaPl);

    log.debug(
        "[DatiPersonaliPanel] Valorizzo i campi Anagrafica dell'Istanza: "
            + datiRichiestaIstanzaPl);

    WebMarkupContainer containerDati = new WebMarkupContainer("containerDati");

    containerDati.addOrReplace(
        createFdCTextField(
            "nome",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "richiedenteNome"),
            false,
            false));
    containerDati.addOrReplace(
        createFdCTextField(
            "cognome",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "richiedenteCognome"),
            false,
            false));
    containerDati.addOrReplace(
        createFdCTextField(
            "comuneNascita",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "nascitaComune"),
            true,
            true));
    containerDati.addOrReplace(
        createFdCLocalDateTextfield(
            "dataNascita",
            new PropertyModel<LocalDate>(datiRichiestaIstanzaPl, "nascitaData"),
            false,
            false));
    containerDati.addOrReplace(
        createFdCTextField(
            "codiceFiscale",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "richiedenteCf"),
            false,
            false));
    containerDati.addOrReplace(
        createFdCTextField(
            "comuneResidenza",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "residenzaComune"),
            true,
            true));
    containerDati.addOrReplace(
        createFdCTextField(
            "indirizzo",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "residenzaVia"),
            true,
            true));
    containerDati.addOrReplace(
        createFdCTextField(
            "numeroCivico",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "residenzaNumeroCivico"),
            true,
            true));
    containerDati.addOrReplace(
        createFdCPhoneNumberField(
            "telefonoCellulare",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "richiedenteTelefono"),
            true,
            true));
    containerDati.addOrReplace(
        createFdCEmailTextField(
            "email",
            new PropertyModel<String>(datiRichiestaIstanzaPl, "richiedenteEmail"),
            true,
            true));

    addOrReplace(containerDati);
  }

  private void addDatiPersonali(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {

    try {
      // TODO Auto-generated method stub
      Utente utente = getUtente(); // datiRichiestaIstanzaPl.getUtente();

      log.debug("[DatiPersonaliPanel - addDatiPersonali] Utente per Dati Personali: " + utente);

      datiRichiestaIstanzaPl.setRichiedenteCognome(utente.getCognome());
      datiRichiestaIstanzaPl.setRichiedenteNome(utente.getNome());
      datiRichiestaIstanzaPl.setRichiedenteCf(utente.getCodiceFiscaleOperatore());
      datiRichiestaIstanzaPl.setNascitaData(utente.getDataDiNascita());

      if (LabelFdCUtil.checkIfNull(datiRichiestaIstanzaPl.getNascitaComune())) {
        datiRichiestaIstanzaPl.setNascitaComune(utente.getLuogoNascita());
      }

      if (LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente())
          && LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente().getCpvHasAddress())) {
        if (LabelFdCUtil.checkIfNull(datiRichiestaIstanzaPl.getResidenzaComune())) {
          datiRichiestaIstanzaPl.setResidenzaComune(
              utente.getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
        }
        if (LabelFdCUtil.checkIfNull(datiRichiestaIstanzaPl.getResidenzaNumeroCivico())) {
          datiRichiestaIstanzaPl.setResidenzaNumeroCivico(
              utente.getDatiCittadinoResidente().getCpvHasAddress().getClvStreetNumber());
        }
        if (LabelFdCUtil.checkIfNull(datiRichiestaIstanzaPl.getResidenzaVia())) {
          datiRichiestaIstanzaPl.setResidenzaVia(
              utente.getDatiCittadinoResidente().getCpvHasAddress().getClvOfficialStreetName());
        }
      }

      if (LabelFdCUtil.checkIfNull(datiRichiestaIstanzaPl.getRichiedenteTelefono())
          && LabelFdCUtil.checkIfNotNull(utente.getMobile())) {
        datiRichiestaIstanzaPl.setRichiedenteTelefono(utente.getMobile());
      }

      if (LabelFdCUtil.checkIfNull(datiRichiestaIstanzaPl.getRichiedenteEmail())
          && LabelFdCUtil.checkIfNotNull(utente.getMail())) {
        datiRichiestaIstanzaPl.setRichiedenteEmail(utente.getMail());
      }
    } catch (Exception ex) {
      log.error("[DatiPersonaliPanel - addDatiPersonali]" + ex.getMessage());
    }
  }

  private FdCTextField<Component> createFdCTextField(
      String id, IModel<String> model, boolean enabled, boolean isRequired) {
    FdCTextField<Component> field = new FdCTextField<Component>(id, model, DatiPersonaliPanel.this);
    field.setEnabled(enabled);
    field.setRequired(isRequired);

    return field;
  }

  private FdCEmailTextField<Component> createFdCEmailTextField(
      String id, IModel<String> model, boolean enabled, boolean isRequired) {
    FdCEmailTextField<Component> field =
        new FdCEmailTextField<Component>(id, model, DatiPersonaliPanel.this);

    field.setEnabled(enabled);
    field.getTextField().setRequired(isRequired);
    return field;
  }

  private FdCPhoneNumberField<?> createFdCPhoneNumberField(
      String id, IModel<String> model, boolean enabled, boolean isRequired) {
    FdCPhoneNumberField<?> cellulare =
        new FdCPhoneNumberField<Object>(id, model, DatiPersonaliPanel.this);

    cellulare.setEnabled(enabled);
    cellulare.setRequired(isRequired);
    cellulare.getTextField().add(StringValidator.maximumLength(15));

    return cellulare;
  }

  private FdCLocalDateField<Component> createFdCLocalDateTextfield(
      String id, IModel<LocalDate> model, boolean enabled, boolean isRequired) {
    FdCLocalDateField<Component> field =
        new FdCLocalDateField<Component>(id, model, DatiPersonaliPanel.this);
    field.setEnabled(enabled);

    return field;
  }
}
