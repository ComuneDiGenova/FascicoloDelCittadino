package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.EsitoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiDocumentiTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.ImportoEuroLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarieng.model.DocumentiPDF;
import it.liguriadigitale.ponmetro.tarieng.model.Rate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.StringResourceModel;

public class DettagliQuadroTributarioTariPanel extends BasePanel {

  private static final long serialVersionUID = -3665039256105556225L;

  private static final String ICON_TARI = "color-green col-3 icon-cassonetto";

  private Double importoPagatoMIP;

  private RicevutaPagamento ricevutaMIP;

  private Debito debitoMIP;

  private AbstractLink linkPaga = null;

  private Component componenteRicevuta = null;

  private boolean isPresenteRataUnica;

  public DettagliQuadroTributarioTariPanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    DatiDocumentiTariEng debito = (DatiDocumentiTariEng) dati;

    log.debug("CP debito in dettagli quadro " + debito);

    WebMarkupContainer icona = new WebMarkupContainer("icona");
    icona.add(getCssIconaTari(debito.getEsito()));
    addOrReplace(icona);

    String tipologiaDocumento = "";
    if (PageUtil.isStringValid(debito.getTipoDoc())) {
      tipologiaDocumento =
          getString("DettagliQuadroTributarioTariPanel.tipoDoc")
              .concat(" ")
              .concat(debito.getTipoDoc());
    }
    NotEmptyLabel tipoDoc = new NotEmptyLabel("tipoDoc", tipologiaDocumento);
    tipoDoc.setVisible(PageUtil.isStringValid(tipologiaDocumento));
    addOrReplace(tipoDoc);

    addOrReplace(
        new AmtCardLabel<>(
                "denominazione", debito.getDenominazione(), DettagliQuadroTributarioTariPanel.this)
            .setVisible(PageUtil.isStringValid(debito.getDenominazione())));

    addOrReplace(
        new AmtCardLabel<>(
                "indirizzo", debito.getIndirizzo(), DettagliQuadroTributarioTariPanel.this)
            .setVisible(PageUtil.isStringValid(debito.getIndirizzo())));

    addOrReplace(
        new AmtCardLabel<>("idDeb", debito.getIdDeb(), DettagliQuadroTributarioTariPanel.this));

    addOrReplace(
        new AmtCardLabel<>("idDoc", debito.getIdDoc(), DettagliQuadroTributarioTariPanel.this)
            .setVisible(false));

    addOrReplace(
        new AmtCardLabel<>("numDoc", debito.getNumDoc(), DettagliQuadroTributarioTariPanel.this));

    addOrReplace(
        new AmtCardLabel<>("anno", debito.getAnno(), DettagliQuadroTributarioTariPanel.this));

    addOrReplace(
        new AmtCardLabel<>(
            "annoEmissione", debito.getAnnoEmissione(), DettagliQuadroTributarioTariPanel.this));

    String fraseInfoAnnullamentoIuv =
        ServiceLocator.getInstance().getServiziTariEng().getValoreDaDb("TARI_ANNULLAMENTO_IUV");
    Label infoAnnullamentoIuv = new Label("infoAnnullamentoIuv", fraseInfoAnnullamentoIuv);
    infoAnnullamentoIuv.setOutputMarkupId(true);
    infoAnnullamentoIuv.setOutputMarkupPlaceholderTag(true);
    infoAnnullamentoIuv.setVisible(
        LabelFdCUtil.checkIfNotNull(debito.getTipoDoc())
            && debito.getTipoDoc().equalsIgnoreCase("ACCONTO TARI 2023")
            && PageUtil.isStringValid(fraseInfoAnnullamentoIuv));
    addOrReplace(infoAnnullamentoIuv);

    NotEmptyLabel tipoDocNegliImporti =
        new NotEmptyLabel("tipoDocNegliImporti", debito.getTipoDoc());
    tipoDocNegliImporti.setVisible(PageUtil.isStringValid(debito.getTipoDoc()));
    addOrReplace(tipoDocNegliImporti);

    ImportoEuroLabel importo = new ImportoEuroLabel("importo", debito.getImporto());
    importo.setVisible(LabelFdCUtil.checkIfNotNull(debito.getImporto()));
    addOrReplace(importo);

    ImportoEuroLabel incassato = new ImportoEuroLabel("incassato", debito.getIncassato());
    incassato.setVisible(
        LabelFdCUtil.checkIfNotNull(debito.getIncassato())
            && PageUtil.isStringValid(debito.getFlagMigrazioneEsperta())
            && debito.getFlagMigrazioneEsperta().equalsIgnoreCase("N"));
    addOrReplace(incassato);

    log.debug("CP debito.getIdDeb() = " + debito.getIdDeb());

    if (LabelFdCUtil.checkIfNotNull(debito.getDettagliDocumento())
        && LabelFdCUtil.checkIfNotNull(debito.getDettagliDocumento().getIdAlleg())) {

      log.debug("CP debito.getDettagli = " + debito.getDettagliDocumento().getIdAlleg());

      int idDebGeri = 0;
      int idAllegatoGeri = 0;

      if (LabelFdCUtil.checkIfNotNull(debito.getIdDeb())) {
        idDebGeri = debito.getIdDeb().intValue();
      }

      idAllegatoGeri = debito.getDettagliDocumento().getIdAlleg().intValue();

      String identificativoDocumento = String.valueOf(debito.getIdDoc());

      @SuppressWarnings("rawtypes")
      BottoneAJAXDownloadWithError btnDocumento =
          creaDownloadBolletta(idDebGeri, idAllegatoGeri, identificativoDocumento);
      addOrReplace(btnDocumento);

    } else {
      WebMarkupContainer btnDocumento = new WebMarkupContainer("btnDocumento");
      btnDocumento.setVisible(false);
      addOrReplace(btnDocumento);
    }

    List<Rate> listaRateDaDettaglio = new ArrayList<Rate>();
    if (LabelFdCUtil.checkIfNotNull(debito.getDettagliDocumento())
        && LabelFdCUtil.checkIfNotNull(debito.getDettagliDocumento().getRate())) {
      listaRateDaDettaglio = debito.getDettagliDocumento().getRate();
    }

    setInfoRate(listaRateDaDettaglio);

    ListView<Rate> boxRate =
        new ListView<Rate>("boxRate", listaRateDaDettaglio) {

          private static final long serialVersionUID = -7716983425379530428L;

          @Override
          protected void populateItem(ListItem<Rate> itemRata) {
            final Rate rata = itemRata.getModelObject();

            setImportoPagatoMIP(null);

            setRicevutaMIP(null);

            int numeroRate = 0;
            if (LabelFdCUtil.checkIfNotNull(debito.getDettagliDocumento())
                && LabelFdCUtil.checkIfNotNull(debito.getDettagliDocumento().getRate())) {
              numeroRate = debito.getDettagliDocumento().getRate().size() - 1;
            }

            NotEmptyLabel totaleRate =
                new NotEmptyLabel(
                    "totaleRate",
                    new StringResourceModel("DettagliQuadroTributarioTariPanel.fraseRate", this)
                        .setParameters(numeroRate));
            totaleRate.setVisible(itemRata.getIndex() == 1 && isPresenteRataUnica);
            itemRata.addOrReplace(totaleRate);

            String numeroRataValue = "";
            if (PageUtil.isStringValid(rata.getRata())) {
              if (rata.getRata().equalsIgnoreCase("0")) {
                numeroRataValue = "Unica";
              } else {
                numeroRataValue = rata.getRata();
              }
            }
            Label numeroRata = new Label("numeroRata", numeroRataValue);
            numeroRata.setVisible(PageUtil.isStringValid(numeroRataValue));
            itemRata.addOrReplace(numeroRata);

            LocalDate scadenzaRataLocaldate = null;
            if (PageUtil.isStringValid(rata.getScad())) {
              scadenzaRataLocaldate =
                  LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(rata.getScad());
            }
            Label scadenzaRata =
                new Label(
                    "scadenzaRata", LocalDateUtil.getDataFormatoEuropeo(scadenzaRataLocaldate));
            scadenzaRata.setVisible(
                LabelFdCUtil.checkIfNotNull(scadenzaRataLocaldate)
                    && ServiceLocator.getInstance()
                        .getServiziTariEng()
                        .dataScadenzaRataConguaglioVisible(debito));
            itemRata.addOrReplace(scadenzaRata);

            ImportoEuroLabel importoTari = new ImportoEuroLabel("importoTari", rata.getImpTari());
            importoTari.setVisible(
                LabelFdCUtil.checkIfNotNull(rata.getImpTari())
                    && LabelFdCUtil.checkIfNull(rata.getIuv()));
            itemRata.addOrReplace(importoTari);

            ImportoEuroLabel incassatoTari =
                new ImportoEuroLabel("incassatoTari", rata.getIncTari());
            incassatoTari.setVisible(
                LabelFdCUtil.checkIfNotNull(rata.getIncTari())
                    && LabelFdCUtil.checkIfNull(rata.getIuv()));
            itemRata.addOrReplace(incassatoTari);

            ImportoEuroLabel importoTefa = new ImportoEuroLabel("importoTefa", rata.getImpTefa());
            importoTefa.setVisible(
                LabelFdCUtil.checkIfNotNull(rata.getImpTefa())
                    && LabelFdCUtil.checkIfNull(rata.getIuv()));
            itemRata.addOrReplace(importoTefa);

            ImportoEuroLabel incassatoTefa =
                new ImportoEuroLabel("incassatoTefa", rata.getIncTefa());
            incassatoTefa.setVisible(
                LabelFdCUtil.checkIfNotNull(rata.getIncTefa())
                    && LabelFdCUtil.checkIfNull(rata.getIuv()));
            itemRata.addOrReplace(incassatoTefa);

            Double sommaImportoTariTefa = 0.0;
            if (LabelFdCUtil.checkIfNotNull(rata.getImpTari())) {
              sommaImportoTariTefa = Double.sum(sommaImportoTariTefa, rata.getImpTari());
            }
            if (LabelFdCUtil.checkIfNotNull(rata.getImpTefa())) {
              sommaImportoTariTefa = Double.sum(sommaImportoTariTefa, rata.getImpTefa());
            }

            ImportoEuroLabel importoConIuv =
                new ImportoEuroLabel("importoConIuv", sommaImportoTariTefa);
            importoConIuv.setVisible(
                LabelFdCUtil.checkIfNotNull(sommaImportoTariTefa)
                    && LabelFdCUtil.checkIfNotNull(rata.getIuv()));
            itemRata.addOrReplace(importoConIuv);

            Double sommaIncassatoTariTefa = 0.0;
            if (LabelFdCUtil.checkIfNotNull(rata.getIncTari())) {
              sommaIncassatoTariTefa = Double.sum(sommaIncassatoTariTefa, rata.getIncTari());
            }
            if (LabelFdCUtil.checkIfNotNull(rata.getIncTefa())) {
              sommaIncassatoTariTefa = Double.sum(sommaIncassatoTariTefa, rata.getIncTefa());
            }

            Label iuv = new Label("iuv", rata.getIuv());
            iuv.setVisible(PageUtil.isStringValid(rata.getIuv()));
            itemRata.addOrReplace(iuv);

            try {

              linkPaga = null;

              componenteRicevuta = null;

              if (PageUtil.isStringValid(rata.getIuv())) {

                if (PageUtil.isStringValid(rata.getPagFlag())) {

                  if (rata.getPagFlag().equalsIgnoreCase("N")) {

                    setRicevutaMIP(
                        ServiceLocator.getInstance()
                            .getServiziTariEng()
                            .getRicevutaMipDaIUV(getUtente(), rata.getIuv()));

                    if (LabelFdCUtil.checkIfNotNull(getRicevutaMIP())) {

                      String nomeFileTogliendoEstensione = "";
                      if (PageUtil.isStringValid(getRicevutaMIP().getPdfRicevuta().getNomeFile())) {
                        nomeFileTogliendoEstensione =
                            getRicevutaMIP().getPdfRicevuta().getNomeFile().replace(".pdf", "");
                      }

                      componenteRicevuta =
                          VariazioniResidenzaUtil.createLinkDocumentoCaricato(
                              "btnRicevutaMIP",
                              nomeFileTogliendoEstensione,
                              getRicevutaMIP().getPdfRicevuta().getFile());
                    }

                  } else {

                    getDebitoMipPagatoODaPagare(rata);
                  }

                } else {

                  getDebitoMipPagatoODaPagare(rata);
                }
              }

              visibilitaBottonePagaEBottoneRicevuta(itemRata);

            } catch (BusinessException | ApiException | MagicMatchNotFoundException e) {
              log.error("Errore TARI ENG e MIP: " + e.getMessage(), e);
            }

            if ((PageUtil.isStringValid(rata.getIuv())
                    && PageUtil.isStringValid(rata.getPagFlag())
                    && rata.getPagFlag().equalsIgnoreCase("N"))
                || (LabelFdCUtil.checkIfNotNull(debitoMIP)
                    && LabelFdCUtil.checkIfNotNull(debitoMIP.getEsitoPagamento())
                    && debitoMIP.getEsitoPagamento().equals(EsitoPagamento.OK))) {

              try {
                setRicevutaMIP(
                    ServiceLocator.getInstance()
                        .getServiziTariEng()
                        .getRicevutaMipDaIUV(getUtente(), rata.getIuv()));

                if (LabelFdCUtil.checkIfNotNull(getRicevutaMIP())) {

                  log.debug("CP setto importo MIP pagato");

                  setImportoPagatoMIP(getRicevutaMIP().getImporto());
                } else {
                  log.debug("CP setto importo MIP pagato ELSE");
                }

              } catch (BusinessException | ApiException e) {
                log.error("Errore ricevuta MIP in Eng: " + e.getMessage(), e);
              }
            }

            ImportoEuroLabel incassatoConIuv =
                new ImportoEuroLabel("incassatoConIuv", sommaIncassatoTariTefa);
            incassatoConIuv.setVisible(
                LabelFdCUtil.checkIfNotNull(sommaIncassatoTariTefa)
                    && LabelFdCUtil.checkIfNotNull(rata.getIuv())
                    && (LabelFdCUtil.checkIfNotNull(getImportoPagatoMIP())
                        && Double.compare(sommaIncassatoTariTefa, getImportoPagatoMIP()) > 0));
            itemRata.addOrReplace(incassatoConIuv);

            ImportoEuroLabel incassatoMIP =
                new ImportoEuroLabel("incassatoMIP", getImportoPagatoMIP());
            incassatoMIP.setVisible(
                (LabelFdCUtil.checkIfNotNull(getImportoPagatoMIP())
                        && LabelFdCUtil.checkIfNotNull(rata.getIuv()))
                    || (LabelFdCUtil.checkIfNotNull(debitoMIP)
                            && LabelFdCUtil.checkIfNotNull(debitoMIP.getEsitoPagamento())
                            && debitoMIP.getEsitoPagamento().equals(EsitoPagamento.OK))
                        && (LabelFdCUtil.checkIfNotNull(sommaIncassatoTariTefa)
                            && Double.compare(getImportoPagatoMIP(), sommaImportoTariTefa) >= 0));

            itemRata.addOrReplace(incassatoMIP);
          }

          private void visibilitaBottonePagaEBottoneRicevuta(ListItem<Rate> itemRata) {
            if (LabelFdCUtil.checkIfNotNull(linkPaga)) {

              log.debug("CP link paga non null");

              itemRata.addOrReplace(linkPaga);
            } else {

              log.debug("CP link paga null");

              itemRata.addOrReplace(new WebMarkupContainer("btnPaga").setVisible(false));
            }

            if (LabelFdCUtil.checkIfNotNull(componenteRicevuta)) {

              log.debug("CP componenteRicevuta non null");

              itemRata.addOrReplace(componenteRicevuta);

            } else {

              log.debug("CP componenteRicevuta null");

              itemRata.addOrReplace(new WebMarkupContainer("btnRicevutaMIP").setVisible(false));
            }
          }

          private void getDebitoMipPagatoODaPagare(final Rate rata)
              throws BusinessException, ApiException {
            debitoMIP =
                ServiceLocator.getInstance()
                    .getServiziTariEng()
                    .getDebitoMIPDaIUV(getUtente().getCodiceFiscaleOperatore(), rata.getIuv());

            if (LabelFdCUtil.checkIfNotNull(debitoMIP)) {

              if (LabelFdCUtil.checkIfNotNull(debitoMIP.getEsitoPagamento())) {
                log.debug("CP esitoPagamento MIP pieno");

                if (debitoMIP.getEsitoPagamento().equals(EsitoPagamento.OK)) {

                  // RICEVUTA

                  log.debug("CP esito mip OK");

                  setRicevutaMIP(
                      ServiceLocator.getInstance()
                          .getServiziTariEng()
                          .getRicevutaMipDaIUV(getUtente(), rata.getIuv()));

                  if (LabelFdCUtil.checkIfNotNull(getRicevutaMIP())) {
                    try {

                      String nomeFileTogliendoEstensione = "";
                      if (PageUtil.isStringValid(getRicevutaMIP().getPdfRicevuta().getNomeFile())) {
                        nomeFileTogliendoEstensione =
                            getRicevutaMIP().getPdfRicevuta().getNomeFile().replace(".pdf", "");
                      }

                      componenteRicevuta =
                          VariazioniResidenzaUtil.createLinkDocumentoCaricato(
                              "btnRicevutaMIP",
                              nomeFileTogliendoEstensione,
                              getRicevutaMIP().getPdfRicevuta().getFile());
                    } catch (BusinessException | MagicMatchNotFoundException e) {
                      log.error("Errore ricevuta MIP Tari Eng: " + e.getMessage(), e);
                    }
                  }

                } else {
                  log.debug("CP esito mip diverso da OK");

                  // PAGA

                  linkPaga =
                      ServiceLocator.getInstance()
                          .getServiziTariEng()
                          .creaBtnPaga(rata, getUtente());
                  linkPaga.setVisible(debito.isBtnPagaConPagoPaVisibile());
                }
              } else if (!(LabelFdCUtil.checkIfNotNull(rata)
                      && (LabelFdCUtil.checkIfNotNull(rata.getIncTari()) && rata.getIncTari() > 0)
                  || (LabelFdCUtil.checkIfNotNull(rata.getIncTefa()) && rata.getIncTefa() > 0))) {

                log.debug("CP esitoPagamento MIP nullo");

                linkPaga =
                    ServiceLocator.getInstance().getServiziTariEng().creaBtnPaga(rata, getUtente());
                linkPaga.setVisible(debito.isBtnPagaConPagoPaVisibile());
              }
            }
          }
        };
    boxRate.setOutputMarkupId(true);
    boxRate.setOutputMarkupPlaceholderTag(true);
    boxRate.setVisible(!checkBoxRateNonVisibile(debito));

    addOrReplace(boxRate);

    String accontoPagatoParzialmente =
        ServiceLocator.getInstance()
            .getServiziTariEng()
            .getValoreDaDb("TARI_ACCONTO_PAGATO_PARZIALMENTE");
    boolean messaggioAccontoPagatoParzialmenteVisibile =
        ServiceLocator.getInstance()
            .getServiziTariEng()
            .messaggioAccontoPagatoParzialmenteVisibile(debito);
    Label messaggioAccontoPagatoParzialmente =
        new Label("messaggioAccontoPagatoParzialmente", accontoPagatoParzialmente);
    messaggioAccontoPagatoParzialmente.setVisible(messaggioAccontoPagatoParzialmenteVisibile);
    addOrReplace(messaggioAccontoPagatoParzialmente);
  }

  private AttributeAppender getCssIconaTari(String esito) {
    String css = "";

    css = ICON_TARI;

    return new AttributeAppender("class", " " + css);
  }

  public Double getImportoPagatoMIP() {
    return importoPagatoMIP;
  }

  public void setImportoPagatoMIP(Double importoPagatoMIP) {
    this.importoPagatoMIP = importoPagatoMIP;
  }

  public RicevutaPagamento getRicevutaMIP() {
    return ricevutaMIP;
  }

  public void setRicevutaMIP(RicevutaPagamento ricevutaMIP) {
    this.ricevutaMIP = ricevutaMIP;
  }

  private void setInfoRate(List<Rate> listaRate) {
    if (LabelFdCUtil.checkIfNotNull(listaRate) && !LabelFdCUtil.checkEmptyList(listaRate)) {
      if (listaRate.stream()
          .filter(elem -> LabelFdCUtil.checkIfNotNull(elem) && "0".equalsIgnoreCase(elem.getRata()))
          .findAny()
          .isPresent()) {
        isPresenteRataUnica = true;
      } else {
        isPresenteRataUnica = false;
      }
    }
  }

  private boolean checkBoxRateNonVisibile(DatiDocumentiTariEng debito) {
    Double zero = new Double(0.0);

    return LabelFdCUtil.checkIfNotNull(debito)
            && (PageUtil.isStringValid(debito.getFlagMigrazioneEsperta())
                && debito.getFlagMigrazioneEsperta().equalsIgnoreCase("S"))
        || (LabelFdCUtil.checkIfNotNull(debito.getImporto())
            && Double.compare(debito.getImporto(), zero) < 0);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadBolletta(
      int idDebGeri, int idAllegatoGeri, String identificativoDocumento) {
    BottoneAJAXDownloadWithError btnDocumento =
        new BottoneAJAXDownloadWithError("btnDocumento", DettagliQuadroTributarioTariPanel.this) {

          private static final long serialVersionUID = 5781227736096300917L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              DocumentiPDF documento =
                  ServiceLocator.getInstance()
                      .getServiziTariEng()
                      .getStampaDocumentoPDF(idDebGeri, idAllegatoGeri);

              String nomeFile = "";

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              if (LabelFdCUtil.checkIfNotNull(documento)
                  && LabelFdCUtil.checkIfNotNull(documento.getContenutoDocumento())) {
                String estensione =
                    FileFdCUtil.getEstensionFileUploadAllegato(documento.getContenutoDocumento());
                nomeFile = identificativoDocumento.concat(".").concat(estensione);

                fileDaScaricare.setFileBytes(documento.getContenutoDocumento());
                fileDaScaricare.setFileName(nomeFile);
                fileDaScaricare.setEsitoOK(true);

              } else {
                fileDaScaricare.setMessaggioErrore("Errore durante download file");
                fileDaScaricare.setEsitoOK(false);
              }

              return fileDaScaricare;

            } catch (ApiException | BusinessException | MagicMatchNotFoundException e) {
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setMessaggioErrore("Errore durante download file");
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnDocumento.setVisible(
        LabelFdCUtil.checkIfNotNull(idAllegatoGeri) && LabelFdCUtil.checkIfNotNull(idDebGeri));

    btnDocumento.setVisibileDopoDownload(true);

    return btnDocumento;
  }
}
