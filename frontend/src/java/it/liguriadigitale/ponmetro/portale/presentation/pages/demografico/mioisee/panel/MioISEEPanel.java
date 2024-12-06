package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.util.DateUtil;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.PrestazioneDaErogareEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.StatodomandaPrestazioneEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.Indicatore;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.ComponenteNucleoFamigliareRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee.IndicatoreISEEDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mioisee.pojo.DatiMioISEE;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import java.util.stream.Collectors;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class MioISEEPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 66884533221603716L;

  Indicatore indicatore = new Indicatore();
  WebMarkupContainer risultatoISEE;

  WebMarkupContainer nessunRisultatoISEE;

  public MioISEEPanel(String id, DatiMioISEE datiMioISEE) {
    super(id, datiMioISEE);
    setOutputMarkupId(true);
    fillDati(datiMioISEE);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    DatiMioISEE datiMioISEE = (DatiMioISEE) dati;

    DropDownChoice<ComponenteNucleo> componenteNucleo =
        new DropDownChoice<>(
            "componenteNucleo", new PropertyModel<>(datiMioISEE, "componenteNucleo"));
    componenteNucleo.setChoiceRenderer(new ComponenteNucleoFamigliareRenderer());

    // getUtente().getListaNucleoFamiliareConviventiEAutodichiarati()

    componenteNucleo.setChoices(
        getUtente().getNucleoFamiliareAllargato().stream()
            .filter(x -> x.getDatiCittadino() != null)
            .collect(Collectors.toList()));
    componenteNucleo.setLabel(Model.of("Componente Nucleo"));
    componenteNucleo.setRequired(true);
    form.addOrReplace(componenteNucleo);

    IndicatoreISEEDropDownChoice indicatoreISEEDropDownChoice =
        new IndicatoreISEEDropDownChoice(
            "indicatore", new PropertyModel<>(datiMioISEE, "indicatoreISEE"));
    indicatoreISEEDropDownChoice.setRequired(true);
    indicatoreISEEDropDownChoice.setLabel(Model.of("Indicatore"));
    form.addOrReplace(indicatoreISEEDropDownChoice);

    form.addOrReplace(
        new FdcAjaxButton("btnCerca") {

          private static final long serialVersionUID = 4792068628183196880L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {

            try {

              ConsultazioneIndicatoreCFBody consultazioneIndicatoreCFBody =
                  InpsModiHelper.createConsultazioneIndicatoreCFBody(
                      componenteNucleo.getModelObject().getCodiceFiscale(),
                      PrestazioneDaErogareEnum.Z9_99,
                      StatodomandaPrestazioneEnum.EROGATA,
                      indicatoreISEEDropDownChoice.getModelObject().getDescrizione());

              ConsultazioneIndicatoreCF200 consultazioneIndicatoreCF200 =
                  ServiceLocator.getInstance()
                      .getServiziINPSModi()
                      .consultazioneIndicatoreCF(consultazioneIndicatoreCFBody);

              if (consultazioneIndicatoreCF200 == null) {
                indicatore = new Indicatore();

              } else {
                indicatore = InpsModiHelper.getIndicatoreIsee(consultazioneIndicatoreCF200);
              }

              risultatoISEE.setVisible(indicatore != null && indicatore.getISEE() != null);
              nessunRisultatoISEE.setVisible(!(indicatore != null && indicatore.getISEE() != null));
              log.debug("MP-" + datiMioISEE.toString());

            } catch (BusinessException e) {

              log.error("Errore INPS: " + e.getMessage(), e);
            }

            target.add(getPage(), feedbackPanel);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(getPage());
          }
        });

    risultatoISEE = new WebMarkupContainer("risultatoISEE");
    risultatoISEE.setVisible(false);
    risultatoISEE.setOutputMarkupId(true);
    risultatoISEE.setOutputMarkupPlaceholderTag(false);

    nessunRisultatoISEE = new WebMarkupContainer("nessunRisultatoISEE");
    nessunRisultatoISEE.setVisible(false);
    nessunRisultatoISEE.setOutputMarkupId(true);
    nessunRisultatoISEE.setOutputMarkupPlaceholderTag(false);

    form.addOrReplace(risultatoISEE);
    form.addOrReplace(nessunRisultatoISEE);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    Double ise = null;
    if (indicatore.getISE() != null) {
      ise = Double.parseDouble(indicatore.getISE());
    }
    risultatoISEE.addOrReplace(new AmtCardLabel<>("ISE", ise, MioISEEPanel.this));
    risultatoISEE.addOrReplace(
        new AmtCardLabel<>(
            "scalaEquivalenza", indicatore.getScalaEquivalenza(), MioISEEPanel.this));

    Double isee = null;
    if (indicatore.getISEE() != null) {
      isee = Double.parseDouble(indicatore.getISEE());
    }
    risultatoISEE.addOrReplace(new AmtCardLabel<>("ISEE", isee, MioISEEPanel.this));

    Double isr = null;
    if (indicatore.getISEE() != null) {
      isr = Double.parseDouble(indicatore.getISR());
    }
    risultatoISEE.addOrReplace(new AmtCardLabel<>("ISR", isr, MioISEEPanel.this));

    Double isp = null;
    if (indicatore.getISP() != null) {
      isp = Double.parseDouble(indicatore.getISP());
    }
    risultatoISEE.addOrReplace(new AmtCardLabel<>("ISP", isp, MioISEEPanel.this));
    risultatoISEE.addOrReplace(
        new AmtCardLabel<>(
            "dataPresentazione", indicatore.getDataPresentazione(), MioISEEPanel.this));
    risultatoISEE.addOrReplace(
        new AmtCardLabel<>("protocolloDSU", indicatore.getProtocolloDSU(), MioISEEPanel.this));

    String codiceFiscale = null;
    String dataValidita = null;
    if (indicatore.getRicercaCF() != null) {
      codiceFiscale = indicatore.getRicercaCF().getCodiceFiscale();
      dataValidita = DateUtil.toStringFromLocalDate(indicatore.getRicercaCF().getDataValidita());
    }

    risultatoISEE.addOrReplace(
        new AmtCardLabel<>("codiceFiscale", codiceFiscale, MioISEEPanel.this));
    risultatoISEE.addOrReplace(new AmtCardLabel<>("dataValidita", dataValidita, MioISEEPanel.this));
  }
}
