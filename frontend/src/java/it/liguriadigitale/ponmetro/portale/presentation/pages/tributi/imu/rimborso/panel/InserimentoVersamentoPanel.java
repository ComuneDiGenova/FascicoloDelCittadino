package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel;

import it.liguriadigitale.ponmetro.portale.pojo.imu.MotivazioneVersamentoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Versamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form.AnnoDropDownChoice;
import java.util.Arrays;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.validator.PatternValidator;

public class InserimentoVersamentoPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 154645887965321L;

  Versamento versamento;

  FdCTextField<?> quotaStato;
  FdCTextField<?> quotaComune;
  FdCTextField<?> totaleImporto;
  AnnoDropDownChoice anno;

  public InserimentoVersamentoPanel(String id, Versamento pojo) {
    super(id, pojo);
    // TODO Auto-generated constructor stub

    fillDati(pojo);
  }

  @Override
  public void fillDati(Object dati) {
    // TODO Auto-generated method stub
    super.fillDati(dati);

    versamento = (Versamento) dati;

    quotaComune =
        RichiestaRimborsoImuHelper.createFdCTextField(
            "quotaComune",
            new PropertyModel<String>(versamento, "quotaComune"),
            true,
            true,
            InserimentoVersamentoPanel.this);
    quotaComune.getTextField().add(new PatternValidator(MetaPattern.DOUBLE_QUOTED_STRING));

    quotaStato =
        RichiestaRimborsoImuHelper.createFdCTextField(
            "quotaStato",
            new PropertyModel<String>(versamento, "quotaStato"),
            true,
            true,
            InserimentoVersamentoPanel.this);
    quotaStato.getTextField().add(new PatternValidator(MetaPattern.DOUBLE_QUOTED_STRING));

    totaleImporto =
        RichiestaRimborsoImuHelper.createFdCTextField(
            "totale",
            new PropertyModel<String>(versamento, "totaleImporto"),
            false,
            true,
            InserimentoVersamentoPanel.this);
    totaleImporto.getTextField().add(new PatternValidator(MetaPattern.DOUBLE_QUOTED_STRING));

    anno = new AnnoDropDownChoice("anno", new PropertyModel<Integer>(versamento, "anno"));
    anno.setRequired(true);

    form.addOrReplace(anno);
    form.addOrReplace(quotaStato);
    form.addOrReplace(quotaComune);
    form.addOrReplace(totaleImporto);

    RadioChoice<MotivazioneVersamentoEnum> motivazione =
        new RadioChoice<MotivazioneVersamentoEnum>(
            "motivazione",
            new PropertyModel<MotivazioneVersamentoEnum>(versamento, "motivazioneVersamento"),
            Arrays.asList(MotivazioneVersamentoEnum.values()));
    motivazione.setPrefix("<div class=\"link-list\">");
    motivazione.setSuffix("</div>");
    motivazione.setRequired(true);
    motivazione.setOutputMarkupPlaceholderTag(true);
    motivazione.setLabel(Model.of("Motivazione"));
    form.addOrReplace(motivazione);

    FdcAjaxButton annulla =
        new FdcAjaxButton("btnAnnulla") {
          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            // annulla();
          }
        };

    annulla.setDefaultFormProcessing(false);
    annulla.setLabel(Model.of("Annulla"));

    form.addOrReplace(annulla);

    FdcAjaxButton salva =
        new FdcAjaxButton("btnSalva") {
          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            // salva(versamento);
          }
        };

    salva.setDefaultFormProcessing(false);
    salva.setLabel(Model.of("Salva"));

    form.addOrReplace(salva);
  }

  // public abstract void annulla();

  // public abstract void salva(Versamento versamento);

}
