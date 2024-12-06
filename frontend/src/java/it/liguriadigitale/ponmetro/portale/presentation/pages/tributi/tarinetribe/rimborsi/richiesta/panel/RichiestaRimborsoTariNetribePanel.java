package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.richiesta.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribeResult;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.TariNetribePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.Saldi;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class RichiestaRimborsoTariNetribePanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 2569741360140280633L;

  private int index;

  private StepPanel stepPanel;

  private DatiRichiestaRimborsoTariNetribePanel datiRimborsoPanel;

  private DatiRichiestaRimborsoBeneficiarioTariNetribePanel datiRimborsoBeneficiarioPanel;

  private ListaFileAllegatiRimborsiTariNetribeInLetturaPanel listaAllegatiInLetturaPanel;

  private EsitoRichiestaRimborsoTariNetribePanel esitoRimborsoTariPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  private WebMarkupContainer containerInfoInvia;

  private DatiRichiestaRimborsoTariNetribeResult esitoRichiestaRimborsi;

  public RichiestaRimborsoTariNetribePanel(String id, DatiRichiestaRimborsoTariNetribe rimborso) {
    super(id, rimborso);
    setOutputMarkupId(true);

    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(rimborso);
  }

  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    DatiRichiestaRimborsoTariNetribe datiRimborso = (DatiRichiestaRimborsoTariNetribe) dati;

    form.setMultiPart(true);

    stepPanel =
        new StepPanel(
            "stepPanel", index, ServiceLocator.getInstance().getServiziTariEng().getListaStep());
    form.addOrReplace(stepPanel);

    datiRimborsoPanel =
        new DatiRichiestaRimborsoTariNetribePanel("datiRimborsoPanel", datiRimborso, index);
    datiRimborsoPanel.setVisible(index == 1);
    form.addOrReplace(datiRimborsoPanel);

    datiRimborsoBeneficiarioPanel =
        new DatiRichiestaRimborsoBeneficiarioTariNetribePanel(
            "datiRimborsoBeneficiarioPanel", datiRimborso, index);
    datiRimborsoBeneficiarioPanel.setVisible(index == 2);
    form.addOrReplace(datiRimborsoBeneficiarioPanel);

    listaAllegatiInLetturaPanel =
        new ListaFileAllegatiRimborsiTariNetribeInLetturaPanel(
            "listaAllegatiInLetturaPanel", datiRimborso, index);
    listaAllegatiInLetturaPanel.setVisible(index == 3);
    form.addOrReplace(listaAllegatiInLetturaPanel);

    esitoRimborsoTariPanel =
        new EsitoRichiestaRimborsoTariNetribePanel("esitoRimborsoTariPanel", index);
    esitoRimborsoTariPanel.setVisible(index == 4);
    form.addOrReplace(esitoRimborsoTariPanel);

    form.addOrReplace(creaBottoneAvanti(datiRimborso));
    form.addOrReplace(creaBottoneIndietro(datiRimborso));
    form.addOrReplace(creaBottoneAnnulla());

    containerInfoInvia = new WebMarkupContainer("containerInfoInvia");
    containerInfoInvia.setOutputMarkupId(true);
    containerInfoInvia.setOutputMarkupId(true);
    containerInfoInvia.setVisible(index == 3);
    form.addOrReplace(containerInfoInvia);
  }

  private FdcAjaxButton creaBottoneAvanti(DatiRichiestaRimborsoTariNetribe datiRimborso) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -8354547634496487926L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index + 1;

            boolean isErrore = false;

            if (index == 2) {
              if (LabelFdCUtil.checkIfNotNull(datiRimborso.getListaSaldi())
                  && datiRimborso.getListaSaldi().stream()
                      .allMatch(elemSaldo -> LabelFdCUtil.checkIfNull(elemSaldo.getImporto()))) {

                isErrore = true;

                error("Attenzione, è necessario inserire almeno un importo");
              } else if (!datiRimborso.isIntestatario()) {
                List<Saldi> listaSaldiSenzaImportiNull =
                    datiRimborso.getListaSaldi().stream()
                        .filter(elemSaldo -> LabelFdCUtil.checkIfNotNull(elemSaldo.getImporto()))
                        .collect(Collectors.toList());
                datiRimborso.setListaSaldi(listaSaldiSenzaImportiNull);
              }

              if (!datiRimborso.isIntestatario()) {
                Double importoTotale =
                    datiRimborso.getListaSaldi().stream()
                        .filter(
                            elemSaldo ->
                                LabelFdCUtil.checkIfNotNull(elemSaldo)
                                    && LabelFdCUtil.checkIfNotNull(elemSaldo.getImporto()))
                        .map(elemSaldo -> elemSaldo.getImporto())
                        .collect(Collectors.summingDouble(Double::doubleValue));

                datiRimborso.setImportoRimborso(importoTotale);
              }

              if (!datiRimborso.isIntestatario()
                  && PageUtil.isStringValid(datiRimborso.getCodiceFiscaleIntestatario())
                  && datiRimborso
                      .getCodiceFiscaleIntestatario()
                      .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
                isErrore = true;

                error(
                    "Attenzione, il codice fiscale intestatario non può coincidere con il codice fiscale del cittadino collegato al Fascicolo");
              }
            }

            if (index == 3) {
              if ((!datiRimborso.isIntestatario())
                  || (datiRimborso.isIntestatario()
                      && PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())
                      && !datiRimborso
                          .getCodiceFiscaleDelegato()
                          .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore()))) {

                if (LabelFdCUtil.checkIfNull(datiRimborso.getListaAllegati())
                    || LabelFdCUtil.checkEmptyList(datiRimborso.getListaAllegati())) {

                  log.debug("CP errore file");

                  isErrore = true;

                  error("Attenzione, è necessario inserire almeno un file");
                } else {
                  boolean esisteUnNomeFileTroppoLungo =
                      datiRimborso.getListaAllegati().stream()
                          .filter(elem -> elem.getNomeFile().length() > 200)
                          .findAny()
                          .isPresent();
                  if (esisteUnNomeFileTroppoLungo) {
                    isErrore = true;

                    error("Attenzione, il nome del file non può superare i 200 caratteri");
                  }
                }
              }

              if (PageUtil.isStringValid(datiRimborso.getNomeDelegato())
                  && PageUtil.isStringValid(datiRimborso.getCognomeDelegato())) {
                datiRimborso.setNominativoDelegato(
                    datiRimborso
                        .getCognomeDelegato()
                        .concat(" ")
                        .concat(datiRimborso.getNomeDelegato()));
              } else {
                datiRimborso.setNominativoDelegato(
                    datiRimborso
                        .getCognomeRichiedente()
                        .concat(" ")
                        .concat(datiRimborso.getNomeRichiedente()));
              }

              if (PageUtil.isStringValid(datiRimborso.getNomeDelegato())) {
                datiRimborso.setNomeDelegato(datiRimborso.getNomeDelegato());
              } else {
                datiRimborso.setNomeDelegato(datiRimborso.getNomeRichiedente());
              }

              if (PageUtil.isStringValid(datiRimborso.getCognomeDelegato())) {
                datiRimborso.setCognomeDelegato(datiRimborso.getCognomeDelegato());
              } else {
                datiRimborso.setCognomeDelegato(datiRimborso.getCognomeRichiedente());
              }

              if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())) {
                datiRimborso.setCodiceFiscaleDelegato(datiRimborso.getCodiceFiscaleDelegato());
              } else {
                datiRimborso.setCodiceFiscaleDelegato(datiRimborso.getCodiceFiscaleRichiedente());
              }

              if (!datiRimborso.isIntestatario()) {
                datiRimborso.setNominativoRichiedente(
                    datiRimborso
                        .getCognomeRichiedente()
                        .concat(" ")
                        .concat(datiRimborso.getNomeRichiedente()));
              }
            }

            if (index == 4) {
              try {
                esitoRichiestaRimborsi =
                    ServiceLocator.getInstance()
                        .getServiziTariRimborsiNetribe()
                        .richiestaRimborsoTari(datiRimborso);
              } catch (ApiException | BusinessException e) {
                log.error("Errore POST rimborsi tari netribe: " + e.getMessage());
              }
            }

            if (isErrore) {
              index = index - 1;
            }

            getTargetPanel(target, datiRimborso);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP errore richiesta rimborso tari netribe:");

            super.onError(target);

            target.add(RichiestaRimborsoTariNetribePanel.this);
          }
        };

    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRimborsoTariNetribePanel.avanti",
                    RichiestaRimborsoTariNetribePanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = -2881994471892541741L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaRimborsoTariNetribePanel.this);

            form.clearInput();

            setResponsePage(new TariNetribePage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRimborsoTariNetribePanel.annulla",
                    RichiestaRimborsoTariNetribePanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      DatiRichiestaRimborsoTariNetribe datiRimborso) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 7836537006099600321L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            index = index - 1;

            getTargetPanel(target, datiRimborso);
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaRimborsoTariNetribePanel.indietro",
                    RichiestaRimborsoTariNetribePanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(
      AjaxRequestTarget target, DatiRichiestaRimborsoTariNetribe datiRimborso) {

    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    if (index == 1) {

      indietro.setVisible(false);

      datiRimborsoPanel.setVisible(true);
      datiRimborsoPanel.setEnabled(true);

      datiRimborsoBeneficiarioPanel.setVisible(false);
      datiRimborsoBeneficiarioPanel.setEnabled(false);

      esitoRimborsoTariPanel.setVisible(false);
      esitoRimborsoTariPanel.setEnabled(false);

      listaAllegatiInLetturaPanel.setVisible(false);
      listaAllegatiInLetturaPanel.setEnabled(false);

      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariNetribePanel.avanti",
                      RichiestaRimborsoTariNetribePanel.this)));

      containerInfoInvia.setVisible(false);

    } else if (index == 2) {
      datiRimborsoPanel.setVisible(false);
      datiRimborsoPanel.setEnabled(false);

      datiRimborsoBeneficiarioPanel.setVisible(true);
      datiRimborsoBeneficiarioPanel.setEnabled(true);

      esitoRimborsoTariPanel.setVisible(false);
      esitoRimborsoTariPanel.setEnabled(false);

      listaAllegatiInLetturaPanel.setVisible(false);
      listaAllegatiInLetturaPanel.setEnabled(false);

      indietro.setVisible(true);

      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariNetribePanel.avanti",
                      RichiestaRimborsoTariNetribePanel.this)));

      containerInfoInvia.setVisible(false);

    } else if (index == 3) {
      datiRimborsoPanel.setVisible(true);
      datiRimborsoPanel.setEnabled(false);

      datiRimborsoBeneficiarioPanel.setVisible(true);
      datiRimborsoBeneficiarioPanel.setEnabled(false);

      esitoRimborsoTariPanel.setVisible(false);
      esitoRimborsoTariPanel.setEnabled(false);

      listaAllegatiInLetturaPanel.fillDati(datiRimborso);
      listaAllegatiInLetturaPanel.setVisible(true);
      listaAllegatiInLetturaPanel.setEnabled(true);

      indietro.setVisible(true);

      avanti.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString(
                      "RichiestaRimborsoTariNetribePanel.invia",
                      RichiestaRimborsoTariNetribePanel.this)));

      containerInfoInvia.setVisible(true);

    } else if (index == 4) {
      datiRimborsoPanel.setEnabled(false);
      datiRimborsoBeneficiarioPanel.setEnabled(false);

      datiRimborsoPanel.setVisible(false);
      datiRimborsoBeneficiarioPanel.setVisible(false);

      listaAllegatiInLetturaPanel.setVisible(false);
      listaAllegatiInLetturaPanel.setEnabled(false);

      indietro.setVisible(false);
      avanti.setVisible(false);
      annulla.setVisible(false);

      containerInfoInvia.setVisible(false);

      esitoRimborsoTariPanel.setVisible(true);
      esitoRimborsoTariPanel.setEnabled(true);
      esitoRimborsoTariPanel.fillDati(esitoRichiestaRimborsi);
    }

    target.add(this);

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

  private boolean checkLunghezzaNomeFile(String nomeFile) {
    boolean lunghezzaOk = true;
    if (LabelFdCUtil.checkIfNotNull(nomeFile) && nomeFile.length() > 200) {
      lunghezzaOk = false;
    }
    return lunghezzaOk;
  }
}
