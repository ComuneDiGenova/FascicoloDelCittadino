package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.domanda.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Bambino;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica.AccettazioneNucleoIseeAnagraficoEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.BorseStudioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.Model;

public class DomandaBorsaStudioPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -7448421422531754973L;

  private int index;

  private StepPanel stepPanel;

  private DatiIntestatarioBorsaStudioPanel datiIntestatarioBorsaStudioPanel;

  private DatiRichiedenteBorseStudioPanel datiRichiedenteBorseStudioPanel;

  private DatiDomandaBorsaStudioPanel domandaPanel;

  private DichiarazioniBorseStudioPanel dichiarazioniPanel;

  private EsitoDomandaBorsaStudioPanel esitoPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public DomandaBorsaStudioPanel(String id, PraticaBorseStudioExtend datiDomanda) {
    super(id, datiDomanda);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(datiDomanda);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    form.addOrReplace(new FdCTitoloPanel("titolo", getString("DomandaBorsaStudioPanel.titolo")));

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance().getServiziBorseDiStudio().getListaStep());
    form.addOrReplace(stepPanel);

    PraticaBorseStudioExtend datiDomanda = (PraticaBorseStudioExtend) dati;

    datiIntestatarioBorsaStudioPanel =
        new DatiIntestatarioBorsaStudioPanel(
            "datiIntestatarioBorsaStudioPanel", datiDomanda, index);
    datiIntestatarioBorsaStudioPanel.setVisible(index == 1);
    form.addOrReplace(datiIntestatarioBorsaStudioPanel);

    datiRichiedenteBorseStudioPanel =
        new DatiRichiedenteBorseStudioPanel("datiRichiedenteBorseStudioPanel", datiDomanda, index);
    datiRichiedenteBorseStudioPanel.setVisible(index == 2);
    form.addOrReplace(datiRichiedenteBorseStudioPanel);

    domandaPanel =
        new DatiDomandaBorsaStudioPanel("domandaPanel", datiDomanda, index, feedbackPanel);
    domandaPanel.setVisible(index == 3);
    form.addOrReplace(domandaPanel);

    dichiarazioniPanel =
        new DichiarazioniBorseStudioPanel("dichiarazioniPanel", datiDomanda, index);
    dichiarazioniPanel.setVisible(index == 4);
    form.addOrReplace(dichiarazioniPanel);

    esitoPanel = new EsitoDomandaBorsaStudioPanel("esitoPanel", datiDomanda, index);
    esitoPanel.setVisible(index == 6);
    form.addOrReplace(esitoPanel);

    form.addOrReplace(creaBottoneAvanti(datiDomanda));
    form.addOrReplace(creaBottoneIndietro(datiDomanda));
    form.addOrReplace(creaBottoneAnnulla());
  }

  private FdcAjaxButton creaBottoneAvanti(PraticaBorseStudioExtend datiDomanda) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = 2833348095985815699L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index + 1;

            if (index == 2) {

              boolean isErroreStep2 = false;

              if (LabelFdCUtil.checkIfNull(datiDomanda.getImportoIsee())) {
                error(
                    "Attenzione, per proseguire è necessario inserire un intestatario che abbia presentato la DSU.");

                isErroreStep2 = true;

              } else if (LabelFdCUtil.checkIfNotNull(datiDomanda.getAccettazioneIntestatarioSiNo())
                  && datiDomanda.getAccettazioneIntestatarioSiNo().equalsIgnoreCase("No")) {
                error(
                    "Attenzione, per proseguire è necessario dichiarare di essere l'intestatario della borsa di studio.");

                isErroreStep2 = true;

              } else if (LabelFdCUtil.checkIfNotNull(datiDomanda.getIseeNonCalcolabile())
                  && datiDomanda.getIseeNonCalcolabile()) {
                error(getString("DomandaBorsaStudioPanel.messaggioIseeNonCalcolabile"));

                isErroreStep2 = true;

              } else if (LabelFdCUtil.checkIfNotNull(datiDomanda.getAccettazioneNuclei())
                  && datiDomanda
                      .getAccettazioneNuclei()
                      .equals(AccettazioneNucleoIseeAnagraficoEnum.NON_ACCETTA)) {
                error(getString("DomandaBorsaStudioPanel.nonAccetto"));

                isErroreStep2 = true;

              } else if (LocalDateUtil.calcolaEta(datiDomanda.getDataNascitaIntestatarioBorsa())
                  <= 9) {
                error(
                    "Attenzione, è possibile effettuare richiesta solo per i componenti del nucleo familare con età maggiore di 9 anni.");

                isErroreStep2 = true;
              }

              if (isErroreStep2) {
                index = index - 1;
              }
            }

            if (index == 3
                && LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getDichiarazioneSpesaFiscale())
                && !datiDomanda.getDichiarazioneSpesaFiscale()) {

              index = index - 1;

              error(
                  "Attenzione, per proseguire è necessario dichiarare di essere in possesso della documentazione fiscale giustificativa della spesa sostenuta.");
            }

            if (index == 3
                && LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getNumeroPersoneDisabili())
                && datiDomanda.getNumeroPersoneDisabili() < 0) {

              index = index - 1;

              error(
                  "Attenzione, il campo 'Numero persone disabili ai sensi della Legge 104/1992' non può essere negativo.");
            }

            if (index == 4) {

              Bambino datiBambino = null;
              try {
                datiBambino =
                    ServiceLocator.getInstance()
                        .getServiziBorseDiStudio()
                        .getBambino(
                            datiDomanda.getAnnoScolastico().getCodice(),
                            datiDomanda.getCodiceIntestatarioBorsa());
              } catch (BusinessException | ApiException | IOException e) {
                log.error("Errore bambino borse: " + e.getMessage(), e);
              }

              if (LabelFdCUtil.checkIfNotNull(datiBambino)
                  && LabelFdCUtil.checkIfNotNull(datiDomanda.getCategoria())
                  && LabelFdCUtil.checkIfNotNull(datiDomanda.getScuola())) {
                if (PageUtil.isStringValid(datiBambino.getCategoria())
                    && PageUtil.isStringValid(datiBambino.getClasse())
                    && PageUtil.isStringValid(datiBambino.getScuola())
                    && PageUtil.isStringValid(datiBambino.getSezione())) {

                  if (datiBambino
                          .getCategoria()
                          .equalsIgnoreCase(datiDomanda.getCategoria().getCodice())
                      && datiBambino
                          .getClasse()
                          .equalsIgnoreCase(String.valueOf(datiDomanda.getClasseNumero()))
                      && datiBambino
                          .getScuola()
                          .equalsIgnoreCase(datiDomanda.getScuola().getCodice())
                      && datiBambino.getSezione().equalsIgnoreCase(datiDomanda.getSezione())) {

                    datiDomanda.setScuolaModificata(false);
                  } else {

                    datiDomanda.setScuolaModificata(true);
                  }

                } else {

                  datiDomanda.setScuolaModificata(true);
                }
              } else {

                datiDomanda.setScuolaModificata(true);
              }

              if (LabelFdCUtil.checkIfNotNull(datiDomanda)
                  && LabelFdCUtil.checkIfNotNull(datiDomanda.getFiglioVittimaLavoro())
                  && datiDomanda.getFiglioVittimaLavoro()
                  && LabelFdCUtil.checkIfNull(datiDomanda.getFileAllegatoVittimaIn())) {
                index = index - 1;

                error(
                    "Attenzione, è necessario allegare il documento comprovante lo stato di figlio vittima sul lavoro e inidoneità assoluta al lavoro");
              }

              if (LabelFdCUtil.checkIfNotNull(datiDomanda.getCategoria())) {
                BigDecimal minClasse = datiDomanda.getCategoria().getMinClasse();
                BigDecimal maxClasse = datiDomanda.getCategoria().getMaxClasse();

                boolean checkMinClasse = false;
                boolean checkMaxClasse = false;

                if (LabelFdCUtil.checkIfNotNull(datiDomanda.getClasseNumero())) {
                  BigDecimal classe = new BigDecimal(datiDomanda.getClasseNumero());

                  if (LabelFdCUtil.checkIfNotNull(classe)) {
                    if (LabelFdCUtil.checkIfNotNull(minClasse)) {
                      if (classe.compareTo(minClasse) == 0 || classe.compareTo(minClasse) == 1) {
                        checkMinClasse = true;
                      }
                    }
                    if (LabelFdCUtil.checkIfNotNull(maxClasse)) {
                      if (classe.compareTo(maxClasse) == 0 || classe.compareTo(maxClasse) != 1) {
                        checkMaxClasse = true;
                      }
                    }
                  }
                }

                if (!checkMinClasse || !checkMaxClasse) {
                  index = index - 1;

                  error(
                      "Attenzione, nel campo Classe inserire un valore compreso fra "
                          + minClasse
                          + " e "
                          + maxClasse);
                }
              }
            }

            if (index == 4 && LabelFdCUtil.checkIfNotNull(datiDomanda.getImportoEuro())) {
              Double cifraMassima = new Double(2000);

              if (Double.compare(datiDomanda.getImportoEuro(), cifraMassima) > 0) {
                index = index - 1;

                error("Attenzione, la spesa massima sostenuta non può superare i 2000 €");
              }
            }

            if (index == 5
                && LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getDichiarazioneDiEssereAConoscenza())
                && !datiDomanda.getDichiarazioneDiEssereAConoscenza()) {
              index = index - 1;

              error("Attenzione, è necessario dichiarare di essere a conoscenza");
            }

            if (index == 6) {
              try {
                ServiceLocator.getInstance()
                    .getServiziBorseDiStudio()
                    .richiediBorsaStudio(datiDomanda);
              } catch (BusinessException | ApiException | IOException e) {
                log.error("Errore POST richiedi borsa studio: " + e.getMessage(), e);
              }
            }

            getTargetPanel(target, datiDomanda);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            log.error("CP errore domanda borsa studio ");

            getTargetPanel(target, datiDomanda);
          }
        };

    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DomandaBorsaStudioPanel.avanti", DomandaBorsaStudioPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = -7266507129521502492L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DomandaBorsaStudioPanel.this);

            form.clearInput();

            setResponsePage(new BorseStudioPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DomandaBorsaStudioPanel.annulla", DomandaBorsaStudioPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      PraticaBorseStudioExtend datiDomanda) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 5148931191005252959L;

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
                .getString("DomandaBorsaStudioPanel.indietro", DomandaBorsaStudioPanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(
      AjaxRequestTarget target, PraticaBorseStudioExtend datiDomanda) {
    stepPanel.setIndexStep(index);

    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    if (index == 1) {

      datiIntestatarioBorsaStudioPanel.setVisible(true);
      datiIntestatarioBorsaStudioPanel.setEnabled(true);

      datiRichiedenteBorseStudioPanel.setVisible(false);
      datiRichiedenteBorseStudioPanel.setEnabled(false);

      domandaPanel.setVisible(false);
      domandaPanel.setEnabled(false);

      dichiarazioniPanel.setVisible(false);
      dichiarazioniPanel.setEnabled(false);

      esitoPanel.setVisible(false);
      esitoPanel.setEnabled(false);

      indietro.setVisible(false);

      target.add(this);

    } else if (index == 2) {

      datiIntestatarioBorsaStudioPanel.setVisible(false);
      datiIntestatarioBorsaStudioPanel.setEnabled(false);

      datiRichiedenteBorseStudioPanel.setVisible(true);
      datiRichiedenteBorseStudioPanel.setEnabled(true);

      domandaPanel.setVisible(false);
      domandaPanel.setEnabled(false);

      dichiarazioniPanel.setVisible(false);
      dichiarazioniPanel.setEnabled(false);

      esitoPanel.setVisible(false);
      esitoPanel.setEnabled(false);

      indietro.setVisible(true);

      target.add(this);

    } else if (index == 3) {

      datiIntestatarioBorsaStudioPanel.setVisible(false);
      datiIntestatarioBorsaStudioPanel.setEnabled(false);

      datiRichiedenteBorseStudioPanel.setVisible(false);
      datiRichiedenteBorseStudioPanel.setEnabled(false);

      domandaPanel.setVisible(true);
      domandaPanel.setEnabled(true);

      dichiarazioniPanel.setVisible(false);
      dichiarazioniPanel.setEnabled(false);

      esitoPanel.setVisible(false);
      esitoPanel.setEnabled(false);

      indietro.setVisible(true);

      target.add(this);

    } else if (index == 4) {

      datiIntestatarioBorsaStudioPanel.setVisible(false);
      datiIntestatarioBorsaStudioPanel.setEnabled(false);

      datiRichiedenteBorseStudioPanel.setVisible(false);
      datiRichiedenteBorseStudioPanel.setEnabled(false);

      domandaPanel.setVisible(false);
      domandaPanel.setEnabled(false);

      dichiarazioniPanel.setVisible(true);
      dichiarazioniPanel.setEnabled(true);

      esitoPanel.setVisible(false);
      esitoPanel.setEnabled(false);

      indietro.setVisible(true);

      target.add(this);

    } else if (index == 5) {

      datiIntestatarioBorsaStudioPanel.setVisible(true);
      datiIntestatarioBorsaStudioPanel.setEnabled(false);

      datiRichiedenteBorseStudioPanel.setVisible(true);
      datiRichiedenteBorseStudioPanel.setEnabled(false);

      domandaPanel.setVisible(true);
      domandaPanel.setEnabled(false);

      dichiarazioniPanel.setVisible(true);
      dichiarazioniPanel.setEnabled(false);

      esitoPanel.setVisible(false);
      esitoPanel.setEnabled(false);

      avanti.setVisible(true);
      indietro.setVisible(true);

      target.add(this);

    } else if (index == 6) {

      datiIntestatarioBorsaStudioPanel.setVisible(false);
      datiIntestatarioBorsaStudioPanel.setEnabled(false);

      datiRichiedenteBorseStudioPanel.setVisible(false);
      datiRichiedenteBorseStudioPanel.setEnabled(false);

      domandaPanel.setVisible(false);
      domandaPanel.setEnabled(false);

      dichiarazioniPanel.setVisible(false);
      dichiarazioniPanel.setEnabled(false);

      esitoPanel.setVisible(true);
      esitoPanel.setEnabled(false);

      avanti.setVisible(false);
      indietro.setVisible(false);
      annulla.setVisible(false);

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
