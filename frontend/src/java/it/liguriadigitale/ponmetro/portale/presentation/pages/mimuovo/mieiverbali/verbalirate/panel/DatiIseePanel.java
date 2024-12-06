package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.panel;

import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ISEEOrdinario;
import it.liguriadigitale.ponmetro.inps.modi.model.Ordinario;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCLocalDateField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextFieldCurrency;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiISEE;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

public class DatiIseePanel extends BasePanel {

  /** */
  private static final long serialVersionUID = 1L;

  private static final int NUMERO_GIORNI = 40;

  DatiRichiestaIstanzaPl datiRichiestaIstanzaPl;

  private static final double VALORE_CONVIVENTE = 1032.91;
  private static final double LIMITE_ISEE = 10628.16;

  public DatiIseePanel(
      String id,
      ConsultazioneAttestazioneCF200 attestazioneIsee,
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id);
    this.datiRichiestaIstanzaPl = datiRichiestaIstanzaPl;
    // TODO Auto-generated constructor stub
    fillDati(attestazioneIsee);
  }

  @Override
  public void fillDati(Object dati) {
    ConsultazioneAttestazioneCF200 attestazioniISEE = (ConsultazioneAttestazioneCF200) dati;

    Ordinario ordinario = InpsModiHelper.getOrdinarioByAttestazioneIsee(attestazioniISEE);

    boolean isIseeExist = LabelFdCUtil.checkIfNotNull(ordinario);

    // TODO Auto-generated method stub
    WebMarkupContainer containerIseeNonPresente = new WebMarkupContainer("iseeNonPresente");
    containerIseeNonPresente.addOrReplace(
        new Label(
            "differenzaGiorni",
            new StringResourceModel("DatiIseePanel.iseeNonPresenteMessage", this)
                .setParameters(calcolaGiorniRimanenti())));
    containerIseeNonPresente.setVisible(!isIseeExist);
    addOrReplace(containerIseeNonPresente);

    WebMarkupContainer superamentoISEEAlert = new WebMarkupContainer("superamentoISEEAlert");
    superamentoISEEAlert.setOutputMarkupId(true);
    superamentoISEEAlert.setVisible(false);
    addOrReplace(superamentoISEEAlert);

    WebMarkupContainer containerDati = new WebMarkupContainer("containerDati");
    ISEEOrdinario isee = isIseeExist ? ordinario.getIsEEOrdinario() : new ISEEOrdinario();

    containerDati.addOrReplace(
        createFdCTextFieldCurrency(
            "importo", new PropertyModel<Double>(isee.getValori(), "iSEE"), false, true));

    int nucleoFam = calcolaNumeroComponentiNucleoFamigliare(ordinario, isIseeExist);
    FdCNumberField<Component> numeroComponenti =
        new FdCNumberField<Component>("numeroComponenti", Model.of(nucleoFam), DatiIseePanel.this);

    numeroComponenti.setVisible(nucleoFam > 0);
    numeroComponenti.setEnabled(false);

    containerDati.addOrReplace(numeroComponenti);

    containerDati.addOrReplace(
        createFdCLocalDateTextfield(
            "dataISEE", new PropertyModel<LocalDate>(isee, "dataRilascio"), false, true));
    containerDati.addOrReplace(
        createFdCTextField(
            "anno", new PropertyModel<String>(isee.getDataRilascio(), "year"), false, true));
    containerDati.setVisible(false);
    addOrReplace(containerDati);

    datiRichiestaIstanzaPl.setPresentabile(true);

    if (isIseeExist) {
      log.debug("[DatiIsee] Recuperato isee: " + ordinario);

      DatiISEE datiISEE = new DatiISEE();
      datiISEE.setAnno(new BigDecimal(isee.getDataRilascio().getYear()));
      datiISEE.setComponenti(
          new BigDecimal(ordinario.getNucleoFamiliare().getComponenteNucleo().size()));
      datiISEE.setDataISEE(isee.getDataRilascio());
      datiISEE.setImporto(new BigDecimal(isee.getValori().getISEE()));

      datiRichiestaIstanzaPl.setDatiISEE(datiISEE);
      datiRichiestaIstanzaPl.setiSEETotale(calcolaImportoISEE(ordinario));
      datiRichiestaIstanzaPl.setPresentabile(isPresentabile());
      containerDati.setVisible(isPresentabile());
      superamentoISEEAlert.setVisible(!isPresentabile());
    }
  }

  private int calcolaNumeroComponentiNucleoFamigliare(Ordinario ordinario, boolean isIseeExist) {
    if (!isIseeExist) return 0;

    if (LabelFdCUtil.checkIfNull(ordinario.getNucleoFamiliare().getComponenteNucleo())) return 0;

    return ordinario.getNucleoFamiliare().getComponenteNucleo().size();
  }

  private boolean isPresentabile() {
    if (LabelFdCUtil.checkIfNull(datiRichiestaIstanzaPl.getDatiISEE())) {
      return true;
    }

    return datiRichiestaIstanzaPl.getDatiISEE().getImporto().doubleValue()
        <= LIMITE_ISEE + datiRichiestaIstanzaPl.getiSEETotale();
  }

  private double calcolaImportoISEE(Ordinario ordinario) {
    // TODO Auto-generated method stub
    int numeroConviventi = ordinario.getNucleoFamiliare().getComponenteNucleo().size() - 1;

    return numeroConviventi <= 0 ? 0 : VALORE_CONVIVENTE * numeroConviventi;
  }

  // Calcola i giorni rimanenti entro i quali il cittadino può inviare i dati
  // dell'ISEE
  private int calcolaGiorniRimanenti() {
    // TODO Auto-generated method stub
    if (LabelFdCUtil.checkIfNull(datiRichiestaIstanzaPl.getIstanza())) {
      return NUMERO_GIORNI;
    }

    long days =
        Duration.between(
                LocalDate.now().atStartOfDay(),
                datiRichiestaIstanzaPl.getIstanza().getData().atStartOfDay())
            .toDays();

    log.debug("[GiorniRimanenti] Giorni: " + days);

    return (int) (NUMERO_GIORNI - days);
  }

  private FdCTextField<Component> createFdCTextField(
      String id, IModel<String> model, boolean enabled, boolean isRequired) {
    FdCTextField<Component> field = new FdCTextField<Component>(id, model, DatiIseePanel.this);
    field.setEnabled(enabled);
    field.setRequired(isRequired);
    return field;
  }

  private FdCTextFieldCurrency<Component> createFdCTextFieldCurrency(
      String id, IModel<Double> model, boolean enabled, boolean isRequired) {
    FdCTextFieldCurrency<Component> field =
        new FdCTextFieldCurrency<Component>(id, model, DatiIseePanel.this, "€");
    field.setEnabled(enabled);
    field.setRequired(isRequired);
    return field;
  }

  private FdCLocalDateField<Component> createFdCLocalDateTextfield(
      String id, IModel<LocalDate> model, boolean enabled, boolean isRequired) {
    FdCLocalDateField<Component> field =
        new FdCLocalDateField<Component>(id, model, DatiIseePanel.this);
    field.setEnabled(enabled);

    return field;
  }
}
