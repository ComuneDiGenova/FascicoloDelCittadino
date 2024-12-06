package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.imu.MotivazioneVersamentoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Versamento;
import java.math.BigDecimal;
import java.util.Arrays;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public abstract class InsertVersamentoForm extends AbstracFrameworkForm<Versamento> {
  private static final long serialVersionUID = 1L;

  Versamento versamento;
  TextField<BigDecimal> totale;
  TextField<BigDecimal> quotaComune;
  TextField<BigDecimal> quotaStato;

  WebMarkupContainer containerAltro;

  public InsertVersamentoForm(String id, Versamento model) {
    super(id, model);
    // TODO Auto-generated constructor stub
    versamento = model;

    addElementiForm();
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub

    AnnoDropDownChoice anno =
        new AnnoDropDownChoice("anno", new PropertyModel<Integer>(versamento, "anno"));
    anno.setRequired(true);

    addOrReplace(anno);

    /*DropDownChoice<TipoQuotaEnum> tipoQuota = new DropDownChoice<TipoQuotaEnum>("tipoQuota",
    		new PropertyModel<TipoQuotaEnum>(versamento, "tipoQuota"), Arrays.asList(TipoQuotaEnum.values()));

    addOrReplace(tipoQuota);*/

    quotaComune =
        new NumberTextField<BigDecimal>(
            "quotaComune",
            new PropertyModel<BigDecimal>(versamento, "quotaComune"),
            BigDecimal.class);
    quotaComune.add(new AttributeAppender("step", ".01"));
    quotaComune.add(new AttributeAppender("min", "0"));
    quotaComune.add(new NumberPositiveValidator<BigDecimal>(false));
    quotaComune.setLabel(Model.of("Quota Comune"));

    quotaComune.setOutputMarkupId(true);
    quotaComune.setOutputMarkupPlaceholderTag(true);

    quotaStato =
        new NumberTextField<BigDecimal>(
            "quotaStato", new PropertyModel<BigDecimal>(versamento, "quotaStato"));
    quotaStato.setType(BigDecimal.class);
    quotaStato.add(new AttributeAppender("step", ".01"));
    quotaStato.add(new AttributeAppender("min", "0"));
    quotaStato.add(new NumberPositiveValidator<BigDecimal>(false));
    quotaStato.setLabel(Model.of("Quota Stato"));

    quotaStato.setOutputMarkupId(true);
    quotaStato.setOutputMarkupPlaceholderTag(true);

    /*totale = new NumberTextField<BigDecimal>("totale", new PropertyModel<BigDecimal>(versamento, "totaleImporto"));
    totale.setType(BigDecimal.class);
    totale.setRequired(false);
    totale.setVisible(false);
    totale.add(new AttributeAppender("step", ".01"));*/

    // addOrReplace(totale);
    addOrReplace(quotaComune);
    addOrReplace(quotaStato);

    RadioChoice<MotivazioneVersamentoEnum> motivazione =
        new RadioChoice<MotivazioneVersamentoEnum>(
            "motivazione",
            new PropertyModel<MotivazioneVersamentoEnum>(versamento, "motivazioneVersamento"),
            Arrays.asList(MotivazioneVersamentoEnum.values()));

    motivazione.setPrefix("<div class=\"form-check\">");
    motivazione.setSuffix("</div>");
    motivazione.setRequired(true);
    motivazione.setOutputMarkupPlaceholderTag(true);
    motivazione.setLabel(Model.of("Motivazione"));

    motivazione.add(
        new AjaxFormChoiceComponentUpdatingBehavior() {

          private static final long serialVersionUID = 1654654564898L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            // TODO Auto-generated method stub
            containerAltro.setVisible(
                motivazione.getModelObject().equals(MotivazioneVersamentoEnum.M15));
            versamento.setAltro(null);

            target.add(motivazione, containerAltro);
          }
        });
    addOrReplace(motivazione);

    containerAltro = new WebMarkupContainer("containerAltro");
    TextField<String> altro =
        new TextField<String>("altro", new PropertyModel<String>(versamento, "altro"));
    containerAltro.addOrReplace(altro);
    containerAltro.setOutputMarkupPlaceholderTag(true);
    containerAltro.setOutputMarkupId(true);
    containerAltro.setVisible(false);

    addOrReplace(containerAltro);

    Button annulla =
        new AjaxButton("btnAnnulla") {
          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            log.debug("[Button Annulla] Cliccato Annulla");
            annulla(target);
            clear();
          }
        };

    annulla.setDefaultFormProcessing(false);

    annulla.setLabel(Model.of("Annulla"));

    addOrReplace(annulla);
  }

  public abstract void annulla(AjaxRequestTarget target);

  public void clear() {
    versamento = null;
  }
}
