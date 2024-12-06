package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.domanda;

import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.validator.PatternValidator;

public class DatiContrattoPanel extends BasePanel {

  private static final long serialVersionUID = -2806136984552199159L;

  private int index;

  public DatiContrattoPanel(String id, DatiDomandaTeleriscaldamento datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    DatiDomandaTeleriscaldamento datiDomanda = (DatiDomandaTeleriscaldamento) dati;

    addOrReplace(
        new FdCTitoloPanel("titoloDatiUtenza", getString("DatiContrattoPanel.titoloDatiUtenza")));

    FdCTextField nomeIntestatarioContratto =
        new FdCTextField(
            "nomeIntestatarioContratto",
            new PropertyModel(datiDomanda, "nomeIntestatarioContratto"),
            DatiContrattoPanel.this);
    nomeIntestatarioContratto.getTextField().setRequired(true);
    addOrReplace(nomeIntestatarioContratto);

    FdCTextField cognomeIntestatarioContratto =
        new FdCTextField(
            "cognomeIntestatarioContratto",
            new PropertyModel(datiDomanda, "cognomeIntestatarioContratto"),
            DatiContrattoPanel.this);
    cognomeIntestatarioContratto.getTextField().setRequired(true);
    addOrReplace(cognomeIntestatarioContratto);

    FdCTextField cfIntestatarioContratto =
        new FdCTextField(
            "cfIntestatarioContratto",
            new PropertyModel(datiDomanda, "cfIntestatarioContratto"),
            DatiContrattoPanel.this);
    cfIntestatarioContratto.getTextField().setRequired(true);
    addOrReplace(cfIntestatarioContratto);

    FdCTextField pIvaIntestatarioContratto =
        new FdCTextField(
            "pIvaIntestatarioContratto",
            new PropertyModel(datiDomanda, "pIvaIntestatarioContratto"),
            DatiContrattoPanel.this);
    // pIvaIntestatarioContratto.getTextField().setRequired(campoObbligatorio);
    addOrReplace(pIvaIntestatarioContratto);

    addOrReplace(
        new FdCTitoloPanel(
            "titoloDatiProprietario", getString("DatiContrattoPanel.titoloDatiProprietario")));

    FdCTextField nomeProprietarioAppartamento =
        new FdCTextField(
            "nomeProprietarioAppartamento",
            new PropertyModel(datiDomanda, "nomeProprietarioAppartamento"),
            DatiContrattoPanel.this);
    nomeProprietarioAppartamento.getTextField().setRequired(true);
    addOrReplace(nomeProprietarioAppartamento);

    FdCTextField cognomeProprietarioAppartamento =
        new FdCTextField(
            "cognomeProprietarioAppartamento",
            new PropertyModel(datiDomanda, "cognomeProprietarioAppartamento"),
            DatiContrattoPanel.this);
    cognomeProprietarioAppartamento.getTextField().setRequired(true);
    addOrReplace(cognomeProprietarioAppartamento);

    addOrReplace(
        new FdCTitoloPanel(
            "titoloDatiAmministratore", getString("DatiContrattoPanel.titoloDatiAmministratore")));

    FdCTextField nomeAmministratore =
        new FdCTextField(
            "nomeAmministratore",
            new PropertyModel(datiDomanda, "nomeAmministratore"),
            DatiContrattoPanel.this);
    nomeAmministratore.getTextField().setRequired(true);
    addOrReplace(nomeAmministratore);

    FdCTextField cognomeAmministratore =
        new FdCTextField(
            "cognomeAmministratore",
            new PropertyModel(datiDomanda, "cognomeAmministratore"),
            DatiContrattoPanel.this);
    cognomeAmministratore.getTextField().setRequired(true);
    addOrReplace(cognomeAmministratore);

    FdCTextField viaAmministratore =
        new FdCTextField(
            "viaAmministratore",
            new PropertyModel(datiDomanda, "viaAmministratore"),
            DatiContrattoPanel.this);
    viaAmministratore.getTextField().setRequired(true);
    addOrReplace(viaAmministratore);

    FdCTextField numeroCivicoAmministratore =
        new FdCTextField(
            "numeroCivicoAmministratore",
            new PropertyModel(datiDomanda, "numeroCivicoAmministratore"),
            DatiContrattoPanel.this);
    numeroCivicoAmministratore.getTextField().setRequired(true);
    addOrReplace(numeroCivicoAmministratore);

    FdCTextField comuneAmministratore =
        new FdCTextField(
            "comuneAmministratore",
            new PropertyModel(datiDomanda, "comuneAmministratore"),
            DatiContrattoPanel.this);
    comuneAmministratore.getTextField().setRequired(true);
    addOrReplace(comuneAmministratore);

    FdCTextField provinciaAmministratore =
        new FdCTextField(
            "provinciaAmministratore",
            new PropertyModel(datiDomanda, "provinciaAmministratore"),
            DatiContrattoPanel.this);
    provinciaAmministratore.getTextField().setRequired(true);
    addOrReplace(provinciaAmministratore);

    FdCTextField capAmministratore =
        new FdCTextField(
            "capAmministratore",
            new PropertyModel(datiDomanda, "capAmministratore"),
            DatiContrattoPanel.this);
    capAmministratore.getTextField().setRequired(true);
    addOrReplace(capAmministratore);

    FdCEmailTextField emailAmministratore =
        new FdCEmailTextField(
            "emailAmministratore",
            new PropertyModel(datiDomanda, "emailAmministratore"),
            DatiContrattoPanel.this);
    emailAmministratore.getTextField().setRequired(true);
    addOrReplace(emailAmministratore);

    FdCTextField telefonoAmministratore =
        new FdCTextField(
            "telefonoAmministratore",
            new PropertyModel(datiDomanda, "telefonoAmministratore"),
            DatiContrattoPanel.this);
    // telefonoAmministratore.getTextField().setRequired(true);
    telefonoAmministratore.getTextField().add(new PatternValidator(MetaPattern.DIGITS));
    addOrReplace(telefonoAmministratore);

    FdCTextField cellulareAmministratore =
        new FdCTextField(
            "cellulareAmministratore",
            new PropertyModel(datiDomanda, "cellulareAmministratore"),
            DatiContrattoPanel.this);
    // cellulareAmministratore.getTextField().setRequired(true);
    cellulareAmministratore.getTextField().add(new PatternValidator(MetaPattern.DIGITS));
    addOrReplace(cellulareAmministratore);
  }
}
