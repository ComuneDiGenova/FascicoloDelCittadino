package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.domanda.panel;

import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiRichiedenteBorseStudioPanel extends BasePanel {

  private static final long serialVersionUID = 5499778474833651819L;

  private int index;

  public DatiRichiedenteBorseStudioPanel(
      String id, PraticaBorseStudioExtend datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    PraticaBorseStudioExtend datiDomanda = (PraticaBorseStudioExtend) dati;

    addOrReplace(new FdCTitoloPanel("titolo", getString("DatiRichiedenteBorseStudioPanel.titolo")));

    WebMarkupContainer containerDati = new WebMarkupContainer("containerDati");

    FdCTextField nomeRichiedente =
        new FdCTextField(
            "nomeRichiedente",
            new PropertyModel(datiDomanda, "nomeRichiedente"),
            DatiRichiedenteBorseStudioPanel.this);
    nomeRichiedente.setEnabled(false);
    containerDati.addOrReplace(nomeRichiedente);

    FdCTextField cognomeRichiedente =
        new FdCTextField(
            "cognomeRichiedente",
            new PropertyModel(datiDomanda, "cognomeRichiedente"),
            DatiRichiedenteBorseStudioPanel.this);
    cognomeRichiedente.setEnabled(false);
    containerDati.addOrReplace(cognomeRichiedente);

    FdCTextField codiceFiscaleRichiedente =
        new FdCTextField(
            "codiceFiscaleRichiedente",
            new PropertyModel(datiDomanda, "codiceFiscaleRichiedente"),
            DatiRichiedenteBorseStudioPanel.this);
    codiceFiscaleRichiedente.setEnabled(false);
    containerDati.addOrReplace(codiceFiscaleRichiedente);

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email", new PropertyModel(datiDomanda, "email"), DatiRichiedenteBorseStudioPanel.this);
    email.setEnabled(true);
    email.getTextField().setRequired(true);
    containerDati.addOrReplace(email);

    FdCPhoneNumberField cellulare =
        new FdCPhoneNumberField(
            "cellulare",
            new PropertyModel(datiDomanda, "cellulare"),
            DatiRichiedenteBorseStudioPanel.this);
    cellulare.setEnabled(true);
    cellulare.setRequired(true);
    containerDati.addOrReplace(cellulare);

    NumberTextField numeroPersoneDisabili =
        new NumberTextField<Integer>(
            "numeroPersoneDisabili",
            new PropertyModel<Integer>(datiDomanda, "numeroPersoneDisabili"));
    numeroPersoneDisabili.setMinimum(0);
    numeroPersoneDisabili.setRequired(true);
    numeroPersoneDisabili.setLabel(
        Model.of("Numero persone disabili ai sensi della Legge 104/1992"));
    containerDati.addOrReplace(numeroPersoneDisabili);

    Label numeroPersoneDisabiliLabel =
        new NotEmptyLabel(
            "numeroPersoneDisabiliLabel", "Numero persone disabili ai sensi della Legge 104/1992");
    numeroPersoneDisabiliLabel.setOutputMarkupId(true);
    numeroPersoneDisabiliLabel.setOutputMarkupPlaceholderTag(true);

    numeroPersoneDisabiliLabel.add(
        new AttributeModifier("for", numeroPersoneDisabili.getMarkupId()));
    // numeroPersoneDisabiliLabel.add(new AttributeModifier("class", "active"));
    containerDati.addOrReplace(numeroPersoneDisabiliLabel);

    NumberTextField numeroFigliACarico =
        new NumberTextField<Integer>(
            "numeroFigliACarico", new PropertyModel<Integer>(datiDomanda, "numeroFigliACarico"));
    numeroFigliACarico.setMinimum(0);
    numeroFigliACarico.setRequired(true);
    numeroFigliACarico.setLabel(Model.of("Numero figli a carico"));
    containerDati.addOrReplace(numeroFigliACarico);

    Label numeroFigliACaricoLabel =
        new NotEmptyLabel("numeroFigliACaricoLabel", "Numero figli a carico");
    numeroFigliACaricoLabel.setOutputMarkupId(true);
    numeroFigliACaricoLabel.setOutputMarkupPlaceholderTag(true);

    numeroFigliACaricoLabel.add(new AttributeModifier("for", numeroFigliACarico.getMarkupId()));
    numeroFigliACaricoLabel.add(new AttributeModifier("class", "active"));
    containerDati.addOrReplace(numeroFigliACaricoLabel);

    SiNoDropDownChoice figlioVittimaLavoro =
        new SiNoDropDownChoice(
            "figlioVittimaLavoroString",
            new PropertyModel<>(datiDomanda, "figlioVittimaLavoroString"));
    figlioVittimaLavoro.setRequired(true);
    figlioVittimaLavoro.setLabel(
        Model.of("Figlio di vittime sul lavoro e inidoneità assoluta al lavoro"));

    figlioVittimaLavoro.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -8345118382038655669L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(figlioVittimaLavoro)
                && PageUtil.isStringValid(figlioVittimaLavoro.getValue())) {
              if (figlioVittimaLavoro.getValue().equalsIgnoreCase("0")) {
                datiDomanda.setFiglioVittimaLavoro(true);
              } else {
                datiDomanda.setFiglioVittimaLavoro(false);
              }
            }
          }
        });

    containerDati.addOrReplace(figlioVittimaLavoro);

    WebMarkupContainer lblFiglioVittimaLavoroString =
        new WebMarkupContainer("lblFiglioVittimaLavoroString");
    lblFiglioVittimaLavoroString.add(
        new AttributeModifier("for", figlioVittimaLavoro.getMarkupId()));
    containerDati.addOrReplace(lblFiglioVittimaLavoroString);

    SiNoDropDownChoice dichiarazioneSpesaFiscale =
        new SiNoDropDownChoice(
            "dichiarazioneSpesaFiscaleString",
            new PropertyModel<>(datiDomanda, "dichiarazioneSpesaFiscaleString"));
    dichiarazioneSpesaFiscale.setLabel(
        Model.of(
            "Dichiaro di essere in possesso della documentazione fiscale giustificativa della spesa sostenuta"));
    dichiarazioneSpesaFiscale.setRequired(true);

    dichiarazioneSpesaFiscale.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 7502417965948325398L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(dichiarazioneSpesaFiscale)
                && PageUtil.isStringValid(dichiarazioneSpesaFiscale.getValue())) {
              if (dichiarazioneSpesaFiscale.getValue().equalsIgnoreCase("0")) {
                datiDomanda.setDichiarazioneSpesaFiscale(true);
              } else {
                datiDomanda.setDichiarazioneSpesaFiscale(false);
              }
            }
          }
        });

    containerDati.addOrReplace(dichiarazioneSpesaFiscale);

    WebMarkupContainer lblDichiarazioneSpesaFiscaleString =
        new WebMarkupContainer("lblDichiarazioneSpesaFiscaleString");
    lblDichiarazioneSpesaFiscaleString.add(
        new AttributeModifier("for", dichiarazioneSpesaFiscale.getMarkupId()));
    containerDati.addOrReplace(lblDichiarazioneSpesaFiscaleString);

    containerDati.setOutputMarkupId(true);
    containerDati.setOutputMarkupPlaceholderTag(true);

    addOrReplace(containerDati);
  }
}
