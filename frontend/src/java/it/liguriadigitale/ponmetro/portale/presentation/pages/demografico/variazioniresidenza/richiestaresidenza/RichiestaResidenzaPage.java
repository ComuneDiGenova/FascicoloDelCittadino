package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegatiImmigrazione;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniDiResidenzaEnum;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel.CaricaDocumentiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel.ConfermaDatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel.EsitoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel.IndirizzoResidenzaCoabitazionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel.IndirizzoResidenzaNuovoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.panel.DatiGeneraliRichiestaResidenzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.panel.IndividuiCollegatiResidenzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class RichiestaResidenzaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 4315396293738169792L;

  protected Integer index = 1;

  private VariazioniResidenza variazione;

  public RichiestaResidenzaPage() {
    super();

    this.setVariazione(new VariazioniResidenza());
    getVariazione().setTipoVariazioneDiResidenza(VariazioniDiResidenzaEnum.RICHIESTA_RESIDENZA);

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    DatiGeneraliRichiestaResidenzaPanel datiGeneraliPanel =
        new DatiGeneraliRichiestaResidenzaPanel("datiGeneraliPanel", index, variazione);
    datiGeneraliPanel.setVisible(index == 1);
    addOrReplace(datiGeneraliPanel);

    IndirizzoResidenzaNuovoPanel indirizzoResidenzaNuovoPanel =
        new IndirizzoResidenzaNuovoPanel("indirizzoResidenzaNuovoPanel", index, variazione);
    indirizzoResidenzaNuovoPanel.setVisible(
        index == 2
            && (LabelFdCUtil.checkIfNotNull(variazione.getComboTipologiaIscrizione())
                && (variazione
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("1"))));
    addOrReplace(indirizzoResidenzaNuovoPanel);

    IndirizzoResidenzaCoabitazionePanel indirizzoResidenzaCoabitazionePanel =
        new IndirizzoResidenzaCoabitazionePanel(
            "indirizzoResidenzaCoabitazionePanel", index, variazione);
    indirizzoResidenzaCoabitazionePanel.setVisible(
        index == 2
            && (LabelFdCUtil.checkIfNotNull(variazione.getComboTipologiaIscrizione())
                && (variazione.getComboTipologiaIscrizione().getCodiceFDC().equalsIgnoreCase("2")
                    || variazione
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("4"))));
    addOrReplace(indirizzoResidenzaCoabitazionePanel);

    IndividuiCollegatiResidenzaPanel individuiCollegatiPanel =
        new IndividuiCollegatiResidenzaPanel("individuiCollegatiPanel", index, variazione);
    individuiCollegatiPanel.setVisible(index == 3);
    addOrReplace(individuiCollegatiPanel);

    CaricaDocumentiPanel caricaDocumentiPanel =
        new CaricaDocumentiPanel("caricaDocumentiPanel", index, variazione);
    caricaDocumentiPanel.setVisible(index == 4);
    addOrReplace(caricaDocumentiPanel);

    ConfermaDatiPanel confermaDatiPanel =
        new ConfermaDatiPanel("confermaDatiPanel", index, variazione);
    confermaDatiPanel.setVisible(index == 5);
    addOrReplace(confermaDatiPanel);

    EsitoPanel esitoPanel = new EsitoPanel("esitoPanel", index, variazione);
    esitoPanel.setVisible(index == 6);
    addOrReplace(esitoPanel);
  }

  public RichiestaResidenzaPage(Integer index) {
    super();

    this.index = index;
    setVariazione(variazione);

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    DatiGeneraliRichiestaResidenzaPanel datiGeneraliPanel =
        new DatiGeneraliRichiestaResidenzaPanel("datiGeneraliPanel", index, variazione);
    datiGeneraliPanel.setVisible(index == 1);
    addOrReplace(datiGeneraliPanel);

    IndirizzoResidenzaNuovoPanel indirizzoResidenzaNuovoPanel =
        new IndirizzoResidenzaNuovoPanel("indirizzoResidenzaNuovoPanel", index, variazione);
    indirizzoResidenzaNuovoPanel.setVisible(
        index == 2
            && (LabelFdCUtil.checkIfNotNull(variazione.getComboTipologiaIscrizione())
                && (variazione
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("1"))));
    addOrReplace(indirizzoResidenzaNuovoPanel);

    IndirizzoResidenzaCoabitazionePanel indirizzoResidenzaCoabitazionePanel =
        new IndirizzoResidenzaCoabitazionePanel(
            "indirizzoResidenzaCoabitazionePanel", index, variazione);
    indirizzoResidenzaCoabitazionePanel.setVisible(
        index == 2
            && (LabelFdCUtil.checkIfNotNull(variazione.getComboTipologiaIscrizione())
                && (variazione.getComboTipologiaIscrizione().getCodiceFDC().equalsIgnoreCase("2")
                    || variazione
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("4"))));
    addOrReplace(indirizzoResidenzaCoabitazionePanel);

    IndividuiCollegatiResidenzaPanel individuiCollegatiPanel =
        new IndividuiCollegatiResidenzaPanel("individuiCollegatiPanel", index, variazione);
    individuiCollegatiPanel.setVisible(index == 3);
    addOrReplace(individuiCollegatiPanel);

    CaricaDocumentiPanel caricaDocumentiPanel =
        new CaricaDocumentiPanel("caricaDocumentiPanel", index, variazione);
    caricaDocumentiPanel.setVisible(index == 4);
    addOrReplace(caricaDocumentiPanel);

    ConfermaDatiPanel confermaDatiPanel =
        new ConfermaDatiPanel("confermaDatiPanel", index, variazione);
    confermaDatiPanel.setVisible(index == 5);
    addOrReplace(confermaDatiPanel);

    EsitoPanel esitoPanel = new EsitoPanel("esitoPanel", index, variazione);
    esitoPanel.setVisible(index == 6);
    addOrReplace(esitoPanel);
  }

  public RichiestaResidenzaPage(Integer index, VariazioniResidenza variazione) {
    super();

    this.index = index;
    setVariazione(variazione);

    String descrizione = "";
    if (LabelFdCUtil.checkIfNotNull(variazione)
        && LabelFdCUtil.checkIfNotNull(variazione.getTipoVariazioneDiResidenza())) {
      descrizione = variazione.getTipoVariazioneDiResidenza().getDescrizione();
    }
    if (LabelFdCUtil.checkIfNotNull(variazione)
        && LabelFdCUtil.checkIfNotNull(variazione.getDatiGenerali())
        && PageUtil.isStringValid(variazione.getDatiGenerali().getTipo())) {
      descrizione = variazione.getDatiGenerali().getTipo();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    DatiGeneraliRichiestaResidenzaPanel datiGeneraliPanel =
        new DatiGeneraliRichiestaResidenzaPanel("datiGeneraliPanel", index, variazione);
    datiGeneraliPanel.setVisible(index == 1);
    addOrReplace(datiGeneraliPanel);

    IndirizzoResidenzaNuovoPanel indirizzoResidenzaNuovoPanel =
        new IndirizzoResidenzaNuovoPanel("indirizzoResidenzaNuovoPanel", index, variazione);
    indirizzoResidenzaNuovoPanel.setVisible(
        index == 2
            && (LabelFdCUtil.checkIfNotNull(variazione.getComboTipologiaIscrizione())
                && (variazione
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("1"))));
    addOrReplace(indirizzoResidenzaNuovoPanel);

    IndirizzoResidenzaCoabitazionePanel indirizzoResidenzaCoabitazionePanel =
        new IndirizzoResidenzaCoabitazionePanel(
            "indirizzoResidenzaCoabitazionePanel", index, variazione);
    indirizzoResidenzaCoabitazionePanel.setVisible(
        index == 2
            && (LabelFdCUtil.checkIfNotNull(variazione.getComboTipologiaIscrizione())
                && (variazione.getComboTipologiaIscrizione().getCodiceFDC().equalsIgnoreCase("2")
                    || variazione
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("4"))));
    addOrReplace(indirizzoResidenzaCoabitazionePanel);

    IndividuiCollegatiResidenzaPanel individuiCollegatiPanel =
        new IndividuiCollegatiResidenzaPanel("individuiCollegatiPanel", index, variazione);
    individuiCollegatiPanel.setVisible(index == 3);
    addOrReplace(individuiCollegatiPanel);

    CaricaDocumentiPanel caricaDocumentiPanel =
        new CaricaDocumentiPanel("caricaDocumentiPanel", index, variazione);
    caricaDocumentiPanel.setVisible(index == 4);
    addOrReplace(caricaDocumentiPanel);

    ConfermaDatiPanel confermaDatiPanel =
        new ConfermaDatiPanel("confermaDatiPanel", index, variazione);
    confermaDatiPanel.setVisible(index == 5);
    addOrReplace(confermaDatiPanel);

    EsitoPanel esitoPanel = new EsitoPanel("esitoPanel", index, variazione);
    esitoPanel.setVisible(index == 6);
    addOrReplace(esitoPanel);
  }

  public RichiestaResidenzaPage(
      Integer index,
      VariazioniResidenza variazione,
      IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione) {
    super();

    this.index = index;
    setVariazione(variazione);

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    DatiGeneraliRichiestaResidenzaPanel datiGeneraliPanel =
        new DatiGeneraliRichiestaResidenzaPanel("datiGeneraliPanel", index, variazione);
    datiGeneraliPanel.setVisible(index == 1);
    addOrReplace(datiGeneraliPanel);

    IndirizzoResidenzaNuovoPanel indirizzoResidenzaNuovoPanel =
        new IndirizzoResidenzaNuovoPanel("indirizzoResidenzaNuovoPanel", index, variazione);
    indirizzoResidenzaNuovoPanel.setVisible(
        index == 2
            && (LabelFdCUtil.checkIfNotNull(variazione.getComboTipologiaIscrizione())
                && (variazione
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("1"))));
    addOrReplace(indirizzoResidenzaNuovoPanel);

    IndirizzoResidenzaCoabitazionePanel indirizzoResidenzaCoabitazionePanel =
        new IndirizzoResidenzaCoabitazionePanel(
            "indirizzoResidenzaCoabitazionePanel", index, variazione);
    indirizzoResidenzaCoabitazionePanel.setVisible(
        index == 2
            && (LabelFdCUtil.checkIfNotNull(variazione.getComboTipologiaIscrizione())
                && (variazione.getComboTipologiaIscrizione().getCodiceFDC().equalsIgnoreCase("2")
                    || variazione
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("4"))));
    addOrReplace(indirizzoResidenzaCoabitazionePanel);

    IndividuiCollegatiResidenzaPanel individuiCollegatiPanel =
        new IndividuiCollegatiResidenzaPanel(
            "individuiCollegatiPanel", index, variazione, individuiCollegatiImmigrazione);
    individuiCollegatiPanel.setVisible(index == 3);
    addOrReplace(individuiCollegatiPanel);

    CaricaDocumentiPanel caricaDocumentiPanel =
        new CaricaDocumentiPanel("caricaDocumentiPanel", index, variazione);
    caricaDocumentiPanel.setVisible(index == 4);
    addOrReplace(caricaDocumentiPanel);

    ConfermaDatiPanel confermaDatiPanel =
        new ConfermaDatiPanel("confermaDatiPanel", index, variazione);
    confermaDatiPanel.setVisible(index == 5);
    addOrReplace(confermaDatiPanel);

    EsitoPanel esitoPanel = new EsitoPanel("esitoPanel", index, variazione);
    esitoPanel.setVisible(index == 6);
    addOrReplace(esitoPanel);
  }

  public RichiestaResidenzaPage(
      Integer index, String descrizione, VariazioniResidenza variazioniResidenza) {

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    EmptyPanel datiGeneraliPanel = new EmptyPanel("datiGeneraliPanel");
    datiGeneraliPanel.setVisible(false);
    addOrReplace(datiGeneraliPanel);

    EmptyPanel indirizzoResidenzaNuovoPanel = new EmptyPanel("indirizzoResidenzaNuovoPanel");
    indirizzoResidenzaNuovoPanel.setVisible(false);
    addOrReplace(indirizzoResidenzaNuovoPanel);

    EmptyPanel indirizzoResidenzaCoabitazionePanel =
        new EmptyPanel("indirizzoResidenzaCoabitazionePanel");
    indirizzoResidenzaCoabitazionePanel.setVisible(false);
    addOrReplace(indirizzoResidenzaCoabitazionePanel);

    EmptyPanel individuiCollegatiPanel = new EmptyPanel("individuiCollegatiPanel");
    individuiCollegatiPanel.setVisible(false);
    addOrReplace(individuiCollegatiPanel);

    CaricaDocumentiPanel caricaDocumentiPanel =
        new CaricaDocumentiPanel("caricaDocumentiPanel", index, variazioniResidenza);
    caricaDocumentiPanel.setVisible(index == 4);
    addOrReplace(caricaDocumentiPanel);

    ConfermaDatiPanel confermaDatiPanel =
        new ConfermaDatiPanel("confermaDatiPanel", index, variazioniResidenza);
    confermaDatiPanel.setVisible(index == 5);
    addOrReplace(confermaDatiPanel);

    EsitoPanel esitoPanel = new EsitoPanel("esitoPanel", index, variazioniResidenza);
    esitoPanel.setVisible(index == 6);
    addOrReplace(esitoPanel);
  }

  public RichiestaResidenzaPage(
      Integer index, String descrizione, VariazioniResidenza variazioniResidenza, boolean esito) {

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    StepPanel stepPanel = new StepPanel("stepPanel", index, getListaStep());
    addOrReplace(stepPanel);

    EmptyPanel datiGeneraliPanel = new EmptyPanel("datiGeneraliPanel");
    datiGeneraliPanel.setVisible(false);
    addOrReplace(datiGeneraliPanel);

    EmptyPanel indirizzoResidenzaNuovoPanel = new EmptyPanel("indirizzoResidenzaNuovoPanel");
    indirizzoResidenzaNuovoPanel.setVisible(false);
    addOrReplace(indirizzoResidenzaNuovoPanel);

    EmptyPanel indirizzoResidenzaCoabitazionePanel =
        new EmptyPanel("indirizzoResidenzaCoabitazionePanel");
    indirizzoResidenzaCoabitazionePanel.setVisible(false);
    addOrReplace(indirizzoResidenzaCoabitazionePanel);

    EmptyPanel individuiCollegatiPanel = new EmptyPanel("individuiCollegatiPanel");
    individuiCollegatiPanel.setVisible(false);
    addOrReplace(individuiCollegatiPanel);

    CaricaDocumentiPanel caricaDocumentiPanel =
        new CaricaDocumentiPanel("caricaDocumentiPanel", index, variazioniResidenza);
    caricaDocumentiPanel.setVisible(index == 4);
    addOrReplace(caricaDocumentiPanel);

    ConfermaDatiPanel confermaDatiPanel =
        new ConfermaDatiPanel("confermaDatiPanel", index, variazioniResidenza);
    confermaDatiPanel.setVisible(index == 5);
    addOrReplace(confermaDatiPanel);

    EsitoPanel esitoPanel = new EsitoPanel("esitoPanel", index, variazioniResidenza, esito);
    esitoPanel.setVisible(index == 6);
    addOrReplace(esitoPanel);
  }

  private List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC(getString("RichiestaResidenzaPage.datiGenerali"), 1));
    listaStep.add(new StepFdC(getString("RichiestaResidenzaPage.indirizzoResidenza"), 2));
    listaStep.add(new StepFdC(getString("RichiestaResidenzaPage.individuiCollegati"), 3));
    listaStep.add(new StepFdC(getString("RichiestaResidenzaPage.caricaDocumenti"), 4));
    listaStep.add(new StepFdC(getString("RichiestaResidenzaPage.confermaDati"), 5));
    listaStep.add(new StepFdC(getString("RichiestaResidenzaPage.esito"), 6));

    return listaStep;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public VariazioniResidenza getVariazione() {
    return variazione;
  }

  public void setVariazione(VariazioniResidenza variazione) {
    this.variazione = variazione;
  }
}
