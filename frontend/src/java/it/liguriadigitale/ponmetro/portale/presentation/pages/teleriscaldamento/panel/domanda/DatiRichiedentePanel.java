package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.domanda;

import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.validator.PatternValidator;

public class DatiRichiedentePanel extends BasePanel {

  private static final long serialVersionUID = 5266424856761086937L;

  private int index;

  public DatiRichiedentePanel(String id, DatiDomandaTeleriscaldamento datiDomanda, int index) {
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

    addOrReplace(new FdCTitoloPanel("titolo", getString("DatiRichiedentePanel.titolo")));

    FdCTextField nome =
        new FdCTextField("nome", new PropertyModel(datiDomanda, "nome"), DatiRichiedentePanel.this);
    nome.getTextField().setRequired(true);
    nome.setEnabled(false);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField(
            "cognome", new PropertyModel(datiDomanda, "cognome"), DatiRichiedentePanel.this);
    cognome.getTextField().setRequired(true);
    cognome.setEnabled(false);
    addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField(
            "codiceFiscale",
            new PropertyModel(datiDomanda, "codiceFiscale"),
            DatiRichiedentePanel.this);
    codiceFiscale.getTextField().setRequired(true);
    codiceFiscale.setEnabled(false);
    addOrReplace(codiceFiscale);

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email", new PropertyModel(datiDomanda, "email"), DatiRichiedentePanel.this);
    email.getTextField().setRequired(true);
    addOrReplace(email);

    FdCTextField telefono =
        new FdCTextField(
            "telefono", new PropertyModel(datiDomanda, "telefono"), DatiRichiedentePanel.this);
    telefono.getTextField().setMarkupId(telefono.getMarkupId());
    telefono.getTextField().add(new PatternValidator(MetaPattern.DIGITS));
    addOrReplace(telefono);

    FdCTextField cellulare =
        new FdCTextField(
            "cellulare", new PropertyModel(datiDomanda, "cellulare"), DatiRichiedentePanel.this);
    cellulare.setMarkupId(cellulare.getTextField().getMarkupId());
    cellulare.getTextField().add(new PatternValidator(MetaPattern.DIGITS));
    addOrReplace(cellulare);

    telefono.setMarkupId("telefono");
    telefono.getTextField().setOutputMarkupId(true);
    telefono.getTextField().setOutputMarkupPlaceholderTag(true);
    telefono
        .getTextField()
        .add(
            new AjaxFormComponentUpdatingBehavior("blur") {

              private static final long serialVersionUID = 8911052855749535700L;

              @Override
              protected void onUpdate(AjaxRequestTarget target) {

                telefono
                    .getTextField()
                    .setRequired(
                        datiDomanda != null
                            && !PageUtil.isStringValid(cellulare.getTextField().getValue()));
                cellulare
                    .getTextField()
                    .setRequired(
                        datiDomanda != null
                            && !PageUtil.isStringValid(telefono.getTextField().getValue()));

                target.add(cellulare, telefono);
              }

              @Override
              protected void onError(AjaxRequestTarget target, RuntimeException e) {
                super.onError(target, e);
                telefono
                    .getTextField()
                    .setRequired(
                        datiDomanda != null
                            && !PageUtil.isStringValid(cellulare.getTextField().getValue()));
                cellulare
                    .getTextField()
                    .setRequired(
                        datiDomanda != null
                            && !PageUtil.isStringValid(telefono.getTextField().getValue()));

                target.add(telefono, cellulare);
              }
            });

    cellulare.setMarkupId("cellulare");
    cellulare.getTextField().setOutputMarkupId(true);
    cellulare.getTextField().setOutputMarkupPlaceholderTag(true);
    cellulare
        .getTextField()
        .add(
            new AjaxFormComponentUpdatingBehavior("blur") {

              private static final long serialVersionUID = -6184605888952288059L;

              @Override
              protected void onUpdate(AjaxRequestTarget target) {

                telefono
                    .getTextField()
                    .setRequired(
                        datiDomanda != null
                            && !PageUtil.isStringValid(cellulare.getTextField().getValue()));
                cellulare
                    .getTextField()
                    .setRequired(
                        datiDomanda != null
                            && !PageUtil.isStringValid(telefono.getTextField().getValue()));

                target.add(telefono, cellulare);
              }

              @Override
              protected void onError(AjaxRequestTarget target, RuntimeException e) {
                super.onError(target, e);
                telefono
                    .getTextField()
                    .setRequired(
                        datiDomanda != null
                            && !PageUtil.isStringValid(cellulare.getTextField().getValue()));
                cellulare
                    .getTextField()
                    .setRequired(
                        datiDomanda != null
                            && !PageUtil.isStringValid(telefono.getTextField().getValue()));

                target.add(telefono, cellulare);
              }
            });

    cellulare
        .getTextField()
        .setRequired(datiDomanda != null && !PageUtil.isStringValid(datiDomanda.getTelefono()));

    telefono
        .getTextField()
        .setRequired(datiDomanda != null && !PageUtil.isStringValid(datiDomanda.getCellulare()));
  }
}
