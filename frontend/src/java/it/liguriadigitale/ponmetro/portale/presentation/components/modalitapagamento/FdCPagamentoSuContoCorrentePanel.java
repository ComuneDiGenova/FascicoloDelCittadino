package it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCCodiceFiscaleTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCIbanField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

@SuppressWarnings("serial")
public class FdCPagamentoSuContoCorrentePanel<T extends Component, R> extends BasePanel {

  private boolean isPresentiAltriCampi;

  private boolean isPresentiCampiDelegato;

  @SuppressWarnings("rawtypes")
  private FdCTextField nomeDelegato;

  @SuppressWarnings("rawtypes")
  private FdCTextField cognomeDelegato;

  @SuppressWarnings("rawtypes")
  private FdCCodiceFiscaleTextField codiceFiscaleDelegato;

  public FdCPagamentoSuContoCorrentePanel(
      String id, R pojo, boolean isPresentiAltriCampi, boolean isPresentiCampiDelegato) {
    super(id);

    this.isPresentiAltriCampi = isPresentiAltriCampi;

    this.isPresentiCampiDelegato = isPresentiCampiDelegato;

    fillDati(pojo);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    Object pojoDati = dati;

    FdCIbanField iban =
        new FdCIbanField(
            "iban", new PropertyModel(pojoDati, "iban"), FdCPagamentoSuContoCorrentePanel.this);
    iban.setOutputMarkupId(true);
    iban.setOutputMarkupPlaceholderTag(true);
    iban.setRequired(true);
    addOrReplace(iban);

    FdCTextField swift =
        new FdCTextField(
            "swift", new PropertyModel(pojoDati, "swift"), FdCPagamentoSuContoCorrentePanel.this);
    swift.getTextField().add(StringValidator.maximumLength(11));
    swift.setOutputMarkupId(true);
    swift.setOutputMarkupPlaceholderTag(true);
    addOrReplace(swift);

    WebMarkupContainer containerAltriCampi = new WebMarkupContainer("containerAltriCampi");

    FdCTextField nominativoIntestatario =
        new FdCTextField(
            "nominativoIntestatario",
            new PropertyModel(pojoDati, "nominativoIntestatario"),
            FdCPagamentoSuContoCorrentePanel.this);
    containerAltriCampi.addOrReplace(nominativoIntestatario);

    FdCCodiceFiscaleTextField codiceFiscaleIntestatario =
        new FdCCodiceFiscaleTextField(
            "codiceFiscaleIntestatario",
            new PropertyModel(pojoDati, "codiceFiscaleIntestatario"),
            FdCPagamentoSuContoCorrentePanel.this);
    containerAltriCampi.addOrReplace(codiceFiscaleIntestatario);

    FdCTextField istituto =
        new FdCTextField(
            "istituto",
            new PropertyModel(pojoDati, "istituto"),
            FdCPagamentoSuContoCorrentePanel.this);
    containerAltriCampi.addOrReplace(istituto);

    containerAltriCampi.setOutputMarkupId(true);
    containerAltriCampi.setOutputMarkupPlaceholderTag(true);
    containerAltriCampi.setVisible(isPresentiAltriCampi);
    addOrReplace(containerAltriCampi);

    WebMarkupContainer containerCampiDelegato = new WebMarkupContainer("containerCampiDelegato");

    WebMarkupContainer infoDelegato = new WebMarkupContainer("infoDelegato");
    infoDelegato.setOutputMarkupId(true);
    infoDelegato.setOutputMarkupPlaceholderTag(true);
    containerCampiDelegato.addOrReplace(infoDelegato);

    nomeDelegato =
        new FdCTextField(
            "nomeDelegato",
            new PropertyModel(pojoDati, "nomeDelegato"),
            FdCPagamentoSuContoCorrentePanel.this);
    containerCampiDelegato.addOrReplace(nomeDelegato);

    cognomeDelegato =
        new FdCTextField(
            "cognomeDelegato",
            new PropertyModel(pojoDati, "cognomeDelegato"),
            FdCPagamentoSuContoCorrentePanel.this);
    containerCampiDelegato.addOrReplace(cognomeDelegato);

    codiceFiscaleDelegato =
        new FdCCodiceFiscaleTextField(
            "codiceFiscaleDelegato",
            new PropertyModel(pojoDati, "codiceFiscaleDelegato"),
            FdCPagamentoSuContoCorrentePanel.this);
    containerCampiDelegato.addOrReplace(codiceFiscaleDelegato);

    containerCampiDelegato.setOutputMarkupId(true);
    containerCampiDelegato.setOutputMarkupPlaceholderTag(true);
    containerCampiDelegato.setVisible(isPresentiCampiDelegato);
    addOrReplace(containerCampiDelegato);

    nomeDelegato
        .getTextField()
        .add(
            new AjaxFormComponentUpdatingBehavior("change") {

              @Override
              protected void onError(AjaxRequestTarget target, RuntimeException e) {
                super.onError(target, e);
                controlloCampi(target);
              }

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                controlloCampi(target);
              }
            });

    cognomeDelegato
        .getTextField()
        .add(
            new AjaxFormComponentUpdatingBehavior("change") {

              @Override
              protected void onError(AjaxRequestTarget target, RuntimeException e) {
                super.onError(target, e);
                controlloCampi(target);
              }

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                controlloCampi(target);
              }
            });

    codiceFiscaleDelegato
        .getTextField()
        .add(
            new AjaxFormComponentUpdatingBehavior("change") {

              @Override
              protected void onError(AjaxRequestTarget target, RuntimeException e) {
                super.onError(target, e);
                controlloCampi(target);
              }

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                controlloCampi(target);
              }
            });
  }

  private void controlloCampi(AjaxRequestTarget target) {
    boolean tuttiCampiVuoti =
        (nomeDelegato.getTextField().getValue() == null
                || (nomeDelegato.getTextField().getValue()).equalsIgnoreCase(""))
            && (cognomeDelegato.getTextField().getValue() == null
                || (cognomeDelegato.getTextField().getValue()).equalsIgnoreCase(""))
            && (codiceFiscaleDelegato.getTextField().getValue() == null
                || (codiceFiscaleDelegato.getTextField().getValue()).equalsIgnoreCase(""));

    cognomeDelegato.getTextField().setRequired(!tuttiCampiVuoti);
    nomeDelegato.getTextField().setRequired(!tuttiCampiVuoti);
    codiceFiscaleDelegato.getTextField().setRequired(!tuttiCampiVuoti);

    target.add(nomeDelegato, cognomeDelegato, codiceFiscaleDelegato);
  }

  public FdCCodiceFiscaleTextField getCodiceFiscaleDelegato() {
    return codiceFiscaleDelegato;
  }

  public void setCodiceFiscaleDelegato(FdCCodiceFiscaleTextField codiceFiscaleDelegato) {
    this.codiceFiscaleDelegato = codiceFiscaleDelegato;
  }
}
