package it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento;

import it.liguriadigitale.ponmetro.portale.pojo.imu.SessoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCCodiceFiscaleTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCLocalDateField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.time.LocalDate;
import java.util.Arrays;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;

@SuppressWarnings("serial")
public class FdCPagamentoPressoTesoreriaPanel<T extends Component> extends BasePanel {

  private static final long serialVersionUID = -1620726148325193522L;

  private boolean isPresentiAltriCampi;

  private FdCTextField nomeDelegato;

  private FdCTextField cognomeDelegato;

  private FdCCodiceFiscaleTextField codiceFiscaleDelegato;

  public FdCPagamentoPressoTesoreriaPanel(String id, Object pojo, boolean isPresentiAltriCampi) {
    super(id);

    this.isPresentiAltriCampi = isPresentiAltriCampi;

    fillDati(pojo);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    Object pojoDati = dati;

    AttributeModifier attributeModifierRequired = new AttributeModifier("required", "");

    nomeDelegato =
        new FdCTextField(
            "nomeDelegato",
            new PropertyModel(pojoDati, "nomeDelegato"),
            FdCPagamentoPressoTesoreriaPanel.this);
    addOrReplace(nomeDelegato);

    cognomeDelegato =
        new FdCTextField(
            "cognomeDelegato",
            new PropertyModel(pojoDati, "cognomeDelegato"),
            FdCPagamentoPressoTesoreriaPanel.this);
    addOrReplace(cognomeDelegato);

    codiceFiscaleDelegato =
        new FdCCodiceFiscaleTextField(
            "codiceFiscaleDelegato",
            new PropertyModel(pojoDati, "codiceFiscaleDelegato"),
            FdCPagamentoPressoTesoreriaPanel.this);

    addOrReplace(codiceFiscaleDelegato);

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

    WebMarkupContainer containerAltriCampi = new WebMarkupContainer("containerAltriCampi");

    FdCLocalDateField dataDiNascitaDelegato =
        new FdCLocalDateField(
            "dataDiNascitaDelegato",
            new PropertyModel<LocalDate>(pojoDati, "dataDiNascitaDelegato"),
            FdCPagamentoPressoTesoreriaPanel.this);
    containerAltriCampi.addOrReplace(dataDiNascitaDelegato);

    DropDownChoice<SessoEnum> sessoDelegato =
        new DropDownChoice<SessoEnum>(
            "sessoDelegato",
            new PropertyModel<SessoEnum>(pojoDati, "sessoDelegato"),
            Arrays.asList(SessoEnum.values()));
    containerAltriCampi.addOrReplace(sessoDelegato);

    FdCTextField luogoDiNascitaDelegato =
        new FdCTextField(
            "luogoDiNascitaDelegato",
            new PropertyModel(pojoDati, "luogoDiNascitaDelegato"),
            FdCPagamentoPressoTesoreriaPanel.this);
    containerAltriCampi.addOrReplace(luogoDiNascitaDelegato);

    FdCTextField cittadinanzaDelegato =
        new FdCTextField(
            "cittadinanzaDelegato",
            new PropertyModel(pojoDati, "cittadinanzaDelegato"),
            FdCPagamentoPressoTesoreriaPanel.this);
    containerAltriCampi.addOrReplace(cittadinanzaDelegato);

    containerAltriCampi.setOutputMarkupId(true);
    containerAltriCampi.setOutputMarkupPlaceholderTag(true);
    containerAltriCampi.setVisible(isPresentiAltriCampi);
    addOrReplace(containerAltriCampi);
  }

  public FdCTextField getNomeDelegato() {
    return nomeDelegato;
  }

  public void setNomeDelegato(FdCTextField nomeDelegato) {
    this.nomeDelegato = nomeDelegato;
  }

  public FdCTextField getCognomeDelegato() {
    return cognomeDelegato;
  }

  public void setCognomeDelegato(FdCTextField cognomeDelegato) {
    this.cognomeDelegato = cognomeDelegato;
  }

  public FdCCodiceFiscaleTextField getCodiceFiscaleDelegato() {
    return codiceFiscaleDelegato;
  }

  public void setCodiceFiscaleDelegato(FdCCodiceFiscaleTextField codiceFiscaleDelegato) {
    this.codiceFiscaleDelegato = codiceFiscaleDelegato;
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
}
