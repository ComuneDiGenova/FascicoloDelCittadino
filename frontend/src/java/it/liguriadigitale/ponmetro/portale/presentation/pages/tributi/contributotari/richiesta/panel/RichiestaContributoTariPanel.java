package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.richiesta.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper.DatiPersoneACaricoContributTariNetribeMapper;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiPersoneACaricoContributoTariNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaContributoTariNetribeResult;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.ContributoTariPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.modelmapper.ModelMapper;

public class RichiestaContributoTariPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -7486131934653213738L;

  private int index;

  private StepPanel stepPanel;

  private IseeContributoTariPanel iseeContributoTariPanel;

  private DatiRichiedenteContributoTariPanel datiRichiedenteContributoTariPanel;

  private DatiNucleoContributoTariPanel datiNucleoContributoTariPanel;

  private EsitoContributoTariPanel esitoContributoTariPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  private DatiRichiestaContributoTariNetribeResult esitoRichiestaContributo;

  private WebMarkupContainer containerInfoInvia;

  private WebMarkupContainer containerFileAllegato;

  public RichiestaContributoTariPanel(String id, DatiDomandaContributoTari datiDomanda) {
    super(id, datiDomanda);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(datiDomanda);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    form.setMultiPart(true);

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance().getServiziContributoTari().getListaStep());
    form.addOrReplace(stepPanel);

    DatiDomandaContributoTari datiDomanda = (DatiDomandaContributoTari) dati;

    iseeContributoTariPanel =
        new IseeContributoTariPanel("iseeContributoTariPanel", datiDomanda, index);
    iseeContributoTariPanel.setVisible(index == 1);
    form.addOrReplace(iseeContributoTariPanel);

    datiRichiedenteContributoTariPanel =
        new DatiRichiedenteContributoTariPanel(
            "datiRichiedenteContributoTariPanel", datiDomanda, index);
    datiRichiedenteContributoTariPanel.setVisible(index == 2);
    form.addOrReplace(datiRichiedenteContributoTariPanel);

    datiNucleoContributoTariPanel =
        new DatiNucleoContributoTariPanel("datiNucleoContributoTariPanel", datiDomanda, index);
    datiNucleoContributoTariPanel.setVisible(index == 3);
    form.addOrReplace(datiNucleoContributoTariPanel);

    esitoContributoTariPanel = new EsitoContributoTariPanel("esitoContributoTariPanel", index);
    esitoContributoTariPanel.setVisible(index == 5);
    form.addOrReplace(esitoContributoTariPanel);

    form.addOrReplace(creaBottoneAvanti(datiDomanda));
    form.addOrReplace(creaBottoneIndietro(datiDomanda));
    form.addOrReplace(creaBottoneAnnulla());

    containerInfoInvia = new WebMarkupContainer("containerInfoInvia");
    containerInfoInvia.setOutputMarkupId(true);
    containerInfoInvia.setOutputMarkupId(true);
    containerInfoInvia.setVisible(index == 4);
    form.addOrReplace(containerInfoInvia);

    containerFileAllegato = new WebMarkupContainer("containerFileAllegato");
    containerFileAllegato.setOutputMarkupId(true);
    containerFileAllegato.setOutputMarkupId(true);
    containerFileAllegato.setVisible(
        index == 4
            && LabelFdCUtil.checkIfNotNull(datiDomanda)
            && LabelFdCUtil.checkIfNotNull(datiDomanda.getFileAllegato()));
    form.addOrReplace(containerFileAllegato);
  }

  private FdcAjaxButton creaBottoneAvanti(DatiDomandaContributoTari datiDomanda) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -5157858749744399840L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index + 1;

            boolean isErrore = false;

            if (index == 3) {
              if (PageUtil.isStringValid(datiDomanda.getCodiceFiscaleDelegato())
                  && !datiDomanda
                      .getCodiceFiscaleDelegato()
                      .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {

                if (LabelFdCUtil.checkIfNull(datiDomanda.getFileAllegato())) {
                  isErrore = true;

                  error("Attenzione, è necessario inserire il file");
                }
              }

              if (PageUtil.isStringValid(datiDomanda.getNomeDelegato())) {
                datiDomanda.setNomeDelegato(datiDomanda.getNomeDelegato());
              } else {
                datiDomanda.setNomeDelegato(datiDomanda.getNome());
              }

              if (PageUtil.isStringValid(datiDomanda.getCognomeDelegato())) {
                datiDomanda.setCognomeDelegato(datiDomanda.getCognomeDelegato());
              } else {
                datiDomanda.setCognomeDelegato(datiDomanda.getCognome());
              }

              if (PageUtil.isStringValid(datiDomanda.getCodiceFiscaleDelegato())) {
                datiDomanda.setCodiceFiscaleDelegato(datiDomanda.getCodiceFiscaleDelegato());
              } else {
                datiDomanda.setCodiceFiscaleDelegato(datiDomanda.getCodiceFiscale());
              }
            }

            if (index == 4) {

              if (datiDomanda.getFlagCategoriaImmobileDiversaDaA1A8A9() == 0) {
                isErrore = true;

                error(
                    "Attenzione: secondo i requisiti del bando la categoria dell’immobile deve essere diversa da A1, A8 o A9. Se questo requisito non è rispettato non è possibile proseguire con la compilazione della domanda.");
              }

              if (datiDomanda.getFlagSuperficieImmobileEntroMq() == 0) {
                isErrore = true;

                error(
                    "Attenzione: secondo i requisiti del bando la superficie dell’immobile deve essere inferiore agli "
                        .concat(String.valueOf(datiDomanda.getMqMassimi()))
                        .concat(
                            " mq. Se questo requisito non è rispettato non è possibile proseguire con la compilazione della domanda."));
              }

              if (datiDomanda.getFlagBeneficarioNonFruitoreAltreAgevolazioni() == 0) {
                isErrore = true;

                error(
                    "Attenzione: secondo i requisiti del bando il richiedente non deve percepire l'agevolazione ex art 21 a favore di soggetti in grave disagio economico. Se questo requisito non è rispettato non è possibile proseguire con la compilazione della domanda.");
              }

              if (datiDomanda.getFlagInRegolaTari() == 0) {
                isErrore = true;

                error(
                    "Attenzione: secondo i requisiti del bando è necessario essere in regola alla data di presentazione della domanda con i pagamenti TARI 2022. Se questo requisito non è rispettato non è possibile proseguire con la compilazione della domanda.");
              }

              if (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiNucleoIseeACarico())
                  && !LabelFdCUtil.checkEmptyList(
                      datiDomanda.getListaComponentiNucleoIseeACarico())) {
                Optional<DatiPersoneACaricoContributoTariNetribe> nonHaiDichiaratoUnoDelNucleoIsee =
                    datiDomanda.getListaComponentiNucleoIseeACarico().stream()
                        .filter(elem -> LabelFdCUtil.checkIfNull(elem.getFlagIsACarico()))
                        .findFirst();
                if (nonHaiDichiaratoUnoDelNucleoIsee.isPresent()) {
                  isErrore = true;

                  error(
                      "Attenzione: è necessario selezionare una opzione per ogni familiare presente nella lista");
                } else {
                  boolean haDichiaratoTuttiNo =
                      datiDomanda.getListaComponentiNucleoIseeACarico().stream()
                          .allMatch(elem -> elem.getFlagIsACarico().equalsIgnoreCase("No"));

                  if (haDichiaratoTuttiNo) {
                    isErrore = true;

                    error("Attenzione, è necessario avere persone a carico ");
                  } else {
                    List<DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari>
                        listaDatiPersoneACarico = new ArrayList<>();

                    for (DatiPersoneACaricoContributoTariNetribe elemPersonaACarico :
                        datiDomanda.getListaComponentiNucleoIseeACarico()) {
                      ModelMapper mapper = new ModelMapper();
                      mapper.addMappings(new DatiPersoneACaricoContributTariNetribeMapper());

                      DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari datiPersoneACarico =
                          mapper.map(
                              elemPersonaACarico,
                              DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari.class);

                      listaDatiPersoneACarico.add(datiPersoneACarico);
                    }

                    datiDomanda.setDatiPersoneACarico(listaDatiPersoneACarico);

                    log.debug("CP getDatiPersoneACarico = " + datiDomanda.getDatiPersoneACarico());
                  }
                }
              }
            }

            if (index == 5) {
              try {
                esitoRichiestaContributo =
                    ServiceLocator.getInstance()
                        .getServiziContributoTari()
                        .richiediContributoTari(datiDomanda);
              } catch (ApiException | BusinessException | IOException e) {
                log.error("Errore richiediContributoTari : " + e.getMessage());
              }
            }

            if (isErrore) {
              index = index - 1;
            }

            getTargetPanel(target, datiDomanda);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP errore richiesta contributo tari netribe: " + getFeedbackMessages());

            super.onError(target);

            target.add(RichiestaContributoTariPanel.this);
          }
        };

    avanti.setVisible(
        LabelFdCUtil.checkIfNotNull(datiDomanda) && datiDomanda.isDomandaPresentabile());

    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaContributoTariPanel.avanti", RichiestaContributoTariPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 305005011307967182L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaContributoTariPanel.this);

            form.clearInput();

            setResponsePage(new ContributoTariPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaContributoTariPanel.annulla", RichiestaContributoTariPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      DatiDomandaContributoTari datiDomanda) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 7189614083663909126L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            index = index - 1;

            getTargetPanel(target, datiDomanda);
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaContributoTariPanel.indietro", RichiestaContributoTariPanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(
      AjaxRequestTarget target, DatiDomandaContributoTari datiDomanda) {
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    log.debug("CP aaaa = " + index);

    if (index == 1) {
      iseeContributoTariPanel.setVisible(true);
      datiNucleoContributoTariPanel.setVisible(false);
      datiRichiedenteContributoTariPanel.setVisible(false);
      esitoContributoTariPanel.setVisible(false);

      iseeContributoTariPanel.setEnabled(true);
      datiNucleoContributoTariPanel.setEnabled(false);
      datiRichiedenteContributoTariPanel.setEnabled(false);
      esitoContributoTariPanel.setEnabled(false);

      indietro.setVisible(false);

      containerInfoInvia.setVisible(false);

      containerFileAllegato.setVisible(false);

      target.add(this);

    } else if (index == 2) {
      iseeContributoTariPanel.setVisible(false);
      datiNucleoContributoTariPanel.setVisible(false);
      datiRichiedenteContributoTariPanel.setVisible(true);
      esitoContributoTariPanel.setVisible(false);

      iseeContributoTariPanel.setEnabled(false);
      datiNucleoContributoTariPanel.setEnabled(false);
      datiRichiedenteContributoTariPanel.setEnabled(true);
      esitoContributoTariPanel.setEnabled(false);

      indietro.setVisible(true);

      containerInfoInvia.setVisible(false);

      containerFileAllegato.setVisible(false);

      target.add(this);

    } else if (index == 3) {
      iseeContributoTariPanel.setVisible(false);
      datiNucleoContributoTariPanel.setVisible(true);
      datiRichiedenteContributoTariPanel.setVisible(false);
      esitoContributoTariPanel.setVisible(false);

      iseeContributoTariPanel.setEnabled(false);
      datiNucleoContributoTariPanel.setEnabled(true);
      datiRichiedenteContributoTariPanel.setEnabled(false);
      esitoContributoTariPanel.setEnabled(false);

      indietro.setVisible(true);

      containerInfoInvia.setVisible(false);

      containerFileAllegato.setVisible(false);

      target.add(this);

    } else if (index == 4) {
      iseeContributoTariPanel.setVisible(true);
      datiNucleoContributoTariPanel.setVisible(true);
      datiRichiedenteContributoTariPanel.setVisible(true);
      esitoContributoTariPanel.setVisible(false);

      iseeContributoTariPanel.setEnabled(false);
      datiNucleoContributoTariPanel.setEnabled(false);
      datiRichiedenteContributoTariPanel.setEnabled(false);
      esitoContributoTariPanel.setEnabled(false);

      indietro.setVisible(true);

      containerInfoInvia.setVisible(true);

      String nomeFile = "";
      if (LabelFdCUtil.checkIfNotNull(datiDomanda.getFileAllegato())) {
        nomeFile = datiDomanda.getFileAllegato().getNomeFile();
      }
      Label nomeFileAllegato = new Label("nomeFileAllegato", nomeFile);
      containerFileAllegato.addOrReplace(nomeFileAllegato);

      containerFileAllegato.setVisible(LabelFdCUtil.checkIfNotNull(datiDomanda.getFileAllegato()));
      containerFileAllegato.setEnabled(false);

      target.add(this);

    } else if (index == 5) {
      iseeContributoTariPanel.setVisible(false);
      datiNucleoContributoTariPanel.setVisible(false);
      datiRichiedenteContributoTariPanel.setVisible(false);
      esitoContributoTariPanel.setVisible(true);

      iseeContributoTariPanel.setEnabled(false);
      datiNucleoContributoTariPanel.setEnabled(false);
      datiRichiedenteContributoTariPanel.setEnabled(false);
      esitoContributoTariPanel.setEnabled(true);

      esitoContributoTariPanel.fillDati(esitoRichiestaContributo);

      indietro.setVisible(false);
      avanti.setVisible(false);
      annulla.setVisible(false);

      containerInfoInvia.setVisible(false);

      containerFileAllegato.setVisible(false);

      target.add(this);
    }

    return target;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }
}
