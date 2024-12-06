package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.preferenze;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.STRADE;
import it.liguriadigitale.ponmetro.allertecortesia.model.Tratti;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiStradeAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.AggiungiPreferenzaServizioAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class AggiungiPreferenzaServizioAllerteCortesiaPanel extends FdcCardFormPanel {

  private FdcAjaxButton avanti;
  private FdcAjaxButton indietro;
  private FdcAjaxButton annulla;

  private int index;
  private StepPanel stepPanel;
  private StradaAllerteCortesiaPanel stradaPanel;
  private ListaStradeAllerteCortesiaPanel listaStradePanel;

  private int startIndex = 0;

  private Tratti tratti;
  private List<STRADE> listaStrade = new ArrayList<STRADE>();

  private String idServizio;
  private String emailUtente;

  private String stradaValue;

  private int numeroOccorenze;

  public AggiungiPreferenzaServizioAllerteCortesiaPanel(
      String id, DatiStradeAllerteCortesia datiStrade, int index) {
    super(id, datiStrade);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.idServizio = datiStrade.getIdServizio();
    this.emailUtente = datiStrade.getEmail();

    this.index = index;

    fillDati(datiStrade);
  }

  public AggiungiPreferenzaServizioAllerteCortesiaPanel(
      String id,
      DatiStradeAllerteCortesia datiStrada,
      int index,
      int numeroOccorenze,
      int startIndex) {
    super(id, datiStrada);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.idServizio = datiStrada.getIdServizio();
    this.emailUtente = datiStrada.getEmail();

    this.index = index;

    this.numeroOccorenze = numeroOccorenze;

    this.startIndex = startIndex;

    fillDati(datiStrada);
  }

  @SuppressWarnings({"unchecked", "rawtypes", "serial"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    DatiStradeAllerteCortesia datiStrada = (DatiStradeAllerteCortesia) dati;

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance().getServiziAllerteCortesia().getListaStepPreferenze());
    form.addOrReplace(stepPanel);

    form.add(
        new FdCTitoloPanel(
            "titolo", getString("AggiungiPreferenzaServizioAllerteCortesiaPanel.titolo")));

    stradaPanel = new StradaAllerteCortesiaPanel("stradaPanel", datiStrada, index);
    stradaPanel.setVisible(index == 1);
    form.addOrReplace(stradaPanel);

    listaStradePanel =
        new ListaStradeAllerteCortesiaPanel("listaStradePanel", datiStrada, index, startIndex);
    listaStradePanel.setVisible(index == 2);
    form.addOrReplace(listaStradePanel);

    form.addOrReplace(creaBtnAvanti(datiStrada));
    form.addOrReplace(creaBtnIndietro(datiStrada));
    form.addOrReplace(creaBtnAnnulla());
  }

  private FdcAjaxButton creaBtnAvanti(DatiStradeAllerteCortesia datiStrada) {

    avanti =
        new FdcAjaxButton("avanti") {

          @Override
          protected void onSubmit(AjaxRequestTarget target) {

            try {
              tratti =
                  ServiceLocator.getInstance()
                      .getServiziGeorefToponomastica()
                      .getWsGetGeorefToponomastica(
                          datiStrada.getAutocompleteViario().getCOD_STRADA())
                      .getRisposta();

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore get strade cortesia: " + e.getMessage(), e);

              error("Errore durante ricerca strada");
            }

            if (tratti != null) {
              index = index + 1;
              datiStrada.setTratti(tratti);
            }
            /*
             * log.debug("CP click avanti su avanti dopo strada " + datiStrada.getStrada() +
             * " - index = " + index);
             *
             * startIndex = 0;
             *
             * log.debug("CP start index = " + startIndex);
             *
             * try {
             * if (LabelFdCUtil.checkIfNotNull(datiStrada) &&
             * PageUtil.isStringValid(datiStrada.getStrada())) {
             *
             * String stradaPulita =
             * ServiceLocator.getInstance().getServiziAllerteCortesia()
             * .pulisciStringaStrada(datiStrada.getStrada());
             *
             * index = index + 1;
             *
             * datiStrada.setStrada(stradaPulita);
             *
             * stradaValue = stradaPulita;
             *
             * tratti = ServiceLocator.getInstance().getServiziGeorefToponomastica().
             * getWsGetGeorefToponomastica(datiStrada.getStrada()).getRisposta();
             *
             * log.debug("CP tratti = " + tratti);
             *
             * datiStrada.setTratti(tratti);
             *
             * }
             *
             * } catch (BusinessException | ApiException | IOException e) {
             * log.error("Errore get strade cortesia: " + e.getMessage(), e);
             *
             * error("Errore durante ricerca strada");
             *
             * }
             */
            AggiungiPreferenzaServizioAllerteCortesiaPage page =
                (AggiungiPreferenzaServizioAllerteCortesiaPage) getParent().getPage();
            page.refreshListaRisultati(datiStrada, index, numeroOccorenze, startIndex);

            // target.add(feedbackPanel);
            target.add(page);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(AggiungiPreferenzaServizioAllerteCortesiaPanel.this);
          }
        };

    avanti.setVisible(index == 1);

    avanti.setLabel(Model.of(getString("AggiungiPreferenzaServizioAllerteCortesiaPanel.avanti")));

    return avanti;
  }

  private FdcAjaxButton creaBtnIndietro(DatiStradeAllerteCortesia datiStrada) {
    indietro =
        new FdcAjaxButton("indietro") {

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index - 1;

            AggiungiPreferenzaServizioAllerteCortesiaPage page =
                (AggiungiPreferenzaServizioAllerteCortesiaPage) getParent().getPage();
            page.refreshListaRisultati(datiStrada, index, numeroOccorenze, startIndex);

            target.add(page);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(AggiungiPreferenzaServizioAllerteCortesiaPanel.this);
          }
        };

    indietro.setVisible(index == 2);

    indietro.setLabel(
        Model.of(getString("AggiungiPreferenzaServizioAllerteCortesiaPanel.indietro")));

    return indietro;
  }

  private FdcAjaxButton creaBtnAnnulla() {
    annulla =
        new FdcAjaxButton("annulla") {

          private static final long serialVersionUID = 8031455942344666783L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(AggiungiPreferenzaServizioAllerteCortesiaPanel.this);
          }
        };

    annulla.setLabel(Model.of(getString("AggiungiPreferenzaServizioAllerteCortesiaPanel.annulla")));

    return annulla;
  }
}
