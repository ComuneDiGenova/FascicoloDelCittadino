package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DettaglioVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.avvisoBonario.DettaglioAvvisoBonarioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliGeneraAvviso;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliImportiPagabili;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliImportiPagati;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliIuv0;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaNotifica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ArticoloViolato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AvvisoBonario;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiGeneraAvviso;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.GeneraAvvisoResult;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportiPagabili;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportoPagato;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class DettaglioPagamentiPanel extends BasePanel {

  private static final long serialVersionUID = -7246951872446734021L;

  private DettaglioVerbale dettaglioVerbale;

  private final String codiceAvvisoStr =
      getString("DettaglioPagamentiPanel.codiceAvviso").concat(" ");

  private WebMarkupContainer tabellaImportiPagabili =
      new WebMarkupContainer("tabellaImportiPagabili");

  WebMarkupContainer containerDaPagare;

  WebMarkupContainer containerRate;

  private GeneraAvvisoResult generaAvvisoResult5gg;

  private GeneraAvvisoResult generaAvvisoResult60gg;

  private GeneraAvvisoResult generaAvvisoResultDopo60gg;

  public DettaglioPagamentiPanel(String id, DettaglioVerbale dettaglioVerbale) {
    super(id);

    this.dettaglioVerbale = dettaglioVerbale;

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    containerDaPagare = new WebMarkupContainer("containerDaPagare");
    containerRate = new WebMarkupContainer("containerRate");

    WebMarkupContainer containerMessaggioInfoScontoDpp =
        new WebMarkupContainer("containerMessaggioInfoScontoDpp");
    Integer totPuntiPatente = 0;
    if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getArticoliViolati())
        && !dettaglioVerbale.getArticoliViolati().isEmpty()) {
      for (ArticoloViolato articoloViolatoElem : dettaglioVerbale.getArticoliViolati()) {
        if (articoloViolatoElem != null) {
          totPuntiPatente += articoloViolatoElem.getPuntiPatente();
        }
      }
    }

    // JIRA FDCOMGE-53
    /*
     * LocalDate localDateAccertamento = dettaglioVerbale.getDataAccertamento();
     * String dataAccertamento = ""; if
     * (UtilVerbali.checkIfNotNull(localDateAccertamento)) { LocalDate
     * dataAccertamentoPiu25gg = localDateAccertamento.plusDays(25);
     * dataAccertamento =
     * LocalDateUtil.getDataFormatoEuropeo(dataAccertamentoPiu25gg); }
     */

    /*LocalDate localDateDataSconto = dettaglioVerbale.getDataSconto();
    LocalDate localDateDataRegistrazione = dettaglioVerbale.getDataRegistrazione();
    String dataDaMostrarePerSconto = "";
    if (UtilVerbali.checkIfNotNull(localDateDataSconto)) {
    	dataDaMostrarePerSconto = LocalDateUtil.getDataFormatoEuropeo(localDateDataSconto);
    } else {
    	if (UtilVerbali.checkIfNotNull(localDateDataRegistrazione)) {
    		LocalDate dataRegistrazionePiu8gg = localDateDataRegistrazione.plusDays(8);
    		dataDaMostrarePerSconto = LocalDateUtil.getDataFormatoEuropeo(dataRegistrazionePiu8gg);
    	}
    }*/

    Label infoScontoDpp =
        new Label(
            "infoScontoDpp",
            new StringResourceModel("DettaglioPagamentiPanel.infoScontoDpp", this)
                .setParameters(
                    LocalDateUtil.getDataFormatoEuropeo(dettaglioVerbale.getDataScontoSpese())));
    containerMessaggioInfoScontoDpp.addOrReplace(infoScontoDpp);
    boolean isProprietarioVerbale = false;
    AnagraficaNotifica anagraficaProprietario = null;

    if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getAnagraficheNotifiche())) {
      anagraficaProprietario =
          dettaglioVerbale.getAnagraficheNotifiche().stream()
              .filter(elem -> elem.getTipologiaAnagrafica().equalsIgnoreCase("Proprietario"))
              .findAny()
              .orElse(null);
    }

    if (UtilVerbali.checkIfNotNull(anagraficaProprietario)) {
      if (LabelFdCUtil.checkIfNull(anagraficaProprietario.getDatiAnagrafici().getCpvTaxCode())
          || (LabelFdCUtil.checkIfNotNull(
                  anagraficaProprietario.getDatiAnagrafici().getCpvTaxCode())
              && anagraficaProprietario
                  .getDatiAnagrafici()
                  .getCpvTaxCode()
                  .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore()))) {
        isProprietarioVerbale = true;
      }
    }
    containerMessaggioInfoScontoDpp.setVisible(totPuntiPatente > 0 && isProprietarioVerbale);
    addOrReplace(containerMessaggioInfoScontoDpp);

    NumberFormat numberFormatEuro = NumberFormat.getCurrencyInstance(Locale.ITALY);

    int importoDaMostrare = dettaglioVerbale.getImporto().compareTo(BigDecimal.ZERO);
    Label importo = new Label("importo", numberFormatEuro.format(dettaglioVerbale.getImporto()));
    if (!UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
        && dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value())) {
      importo.setVisible(false);
    } else {
      importo.setVisible(importoDaMostrare > 0);
    }
    addOrReplace(importo);

    List<ImportoPagato> listaImportiPagati =
        UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagati())
            ? dettaglioVerbale.getImportiPagati()
            : new ArrayList<ImportoPagato>();

    ListView<ImportoPagato> listViewImportiPagati =
        new ListView<ImportoPagato>("listViewImportiPagati", listaImportiPagati) {

          private static final long serialVersionUID = 5949768876220351979L;

          @Override
          protected void populateItem(ListItem<ImportoPagato> item) {
            ImportoPagato importoPagatoItem = item.getModelObject();

            item.setOutputMarkupId(true);

            if (UtilVerbali.checkIfNotNull(importoPagatoItem)) {
              String dettaglioPagamento = getImportoPagato(numberFormatEuro, importoPagatoItem);

              Label importoPagato = new Label("importoPagato", dettaglioPagamento);
              importoPagato.setVisible(UtilVerbali.checkIfNotNull(importoPagatoItem));
              item.addOrReplace(importoPagato);

              ResourceLink<?> linkRicevuta = null;

              if (UtilVerbali.checkIfNotNull(importoPagatoItem.getCodiceAvviso())
                  && !UtilVerbali.checkNotEmpty(importoPagatoItem.getCodiceAvviso())) {
                linkRicevuta =
                    UtilVerbaliImportiPagati.downloadRicevutaMip(importoPagatoItem, getUtente());
              }

              if (linkRicevuta != null) {
                linkRicevuta.setVisible(
                    UtilVerbali.checkIfNotNull(importoPagatoItem.getCodiceAvviso())
                        && !UtilVerbali.checkNotEmpty(importoPagatoItem.getCodiceAvviso()));
                item.addOrReplace(linkRicevuta);
              } else {
                item.addOrReplace(new WebMarkupContainer("btnRicevutaMip").setVisible(false));
              }
            }
          }

          private String getImportoPagato(
              NumberFormat numberFormatEuro, ImportoPagato importoPagatoItem) {
            StringBuilder importoPagato = new StringBuilder();
            importoPagato.append(getString("DettaglioPagamentiPanel.dataPagamento"));
            importoPagato.append(
                " " + LocalDateUtil.getDataFormatoEuropeo(importoPagatoItem.getDataPagamento()));
            importoPagato.append(" " + getString("DettaglioPagamentiPanel.importoPagamento"));
            importoPagato.append(
                " " + numberFormatEuro.format(importoPagatoItem.getImportoPagato()));
            importoPagato.append("(" + importoPagatoItem.getTipoPagamento() + ")");

            if (LabelFdCUtil.checkIfNotNull(importoPagatoItem.getRata())
                && importoPagatoItem.getRata() > 0) {
              importoPagato.append(" Rata N. " + importoPagatoItem.getRata());
            }

            return importoPagato.toString();
          }
        };

    listViewImportiPagati.setVisible(!listaImportiPagati.isEmpty());
    addOrReplace(listViewImportiPagati);

    WebMarkupContainer containerMessaggioRicorso =
        new WebMarkupContainer("containerMessaggioRicorso");
    addOrReplace(containerMessaggioRicorso);

    WebMarkupContainer containerMessaggioInvioARuolo =
        new WebMarkupContainer("containerMessaggioInvioARuolo");
    addOrReplace(containerMessaggioInvioARuolo);

    WebMarkupContainer containerMessaggioImporti =
        new WebMarkupContainer("containerMessaggioImporti");
    containerMessaggioImporti.setVisible(
        UtilVerbaliImportiPagabili.checkVisibilitaContainerMessaggiInfo(
            dettaglioVerbale.getImportiPagabili()));
    addOrReplace(containerMessaggioImporti);

    WebMarkupContainer containerMessaggioVerbaleVecchio =
        new WebMarkupContainer("containerMessaggioVerbaleVecchio");
    containerMessaggioVerbaleVecchio.setVisible(
        UtilVerbali.checkDataAccertamentoFinePeople(dettaglioVerbale.getDataAccertamento())
            && dettaglioVerbale.getStato().equalsIgnoreCase(StatoEnum.APERTO.value()));
    addOrReplace(containerMessaggioVerbaleVecchio);

    boolean pdf = true;
    boolean attualizza = true;

    WebMarkupContainer pagamentoIuv0 = new WebMarkupContainer("pagamentoIuv0");
    if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())) {
      Label codiceAvvisoIuv0 =
          new Label("codiceAvvisoIuv0", dettaglioVerbale.getImportiPagabili().getCodiceAvviso0());
      codiceAvvisoIuv0.setVisible(UtilVerbaliIuv0.checkVisibilitaCodiceAvviso0(dettaglioVerbale));
      pagamentoIuv0.addOrReplace(codiceAvvisoIuv0);

      String descrizioneImporto0 = "";
      if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getImporto0())) {
        descrizioneImporto0 =
            numberFormatEuro.format(dettaglioVerbale.getImportiPagabili().getImporto0());
      }
      Label importoIuv0 = new Label("importoIuv0", descrizioneImporto0);
      importoIuv0.setVisible(UtilVerbaliIuv0.checkVisibilitaImporto0(dettaglioVerbale));
      pagamentoIuv0.addOrReplace(importoIuv0);

      // Per pdf avviso iuv0
      ResourceLink pdfAvvisoIuv0 = null;
      if (UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili())
          && UtilVerbali.checkIfNotNull(dettaglioVerbale.getImportiPagabili().getCodiceAvviso0())) {

        AvvisoPagamento avvisoPagamentoIuv0 =
            UtilVerbaliImportiPagabili.getAvvisoPagamentoAnonimo(
                dettaglioVerbale.getImportiPagabili().getCodiceAvviso0(), pdf, attualizza);

        if (UtilVerbali.checkIfNotNull(avvisoPagamentoIuv0)) {
          pdfAvvisoIuv0 =
              UtilVerbaliImportiPagabili.downloadPdfAvviso(avvisoPagamentoIuv0, "pdfAvvisoIuv0");
        }
      }
      if (UtilVerbali.checkIfNotNull(pdfAvvisoIuv0)) {
        pagamentoIuv0.addOrReplace(pdfAvvisoIuv0);
      } else {
        pagamentoIuv0.addOrReplace(new WebMarkupContainer("pdfAvvisoIuv0").setVisible(false));
      }

      //

      AbstractLink linkVaiAlPagamentoIuv0 =
          UtilVerbaliIuv0.creaBtnPagaIuv0(dettaglioVerbale.getImportiPagabili(), getUtente());
      linkVaiAlPagamentoIuv0.setVisible(
          UtilVerbaliIuv0.checkVisibilitaBtnPagaIuv0(dettaglioVerbale));
      pagamentoIuv0.addOrReplace(linkVaiAlPagamentoIuv0);
    }
    pagamentoIuv0.setVisible(UtilVerbaliIuv0.checkVisibilitaIuv0(dettaglioVerbale));
    pagamentoIuv0.setOutputMarkupId(true);
    containerDaPagare.addOrReplace(pagamentoIuv0);

    tabellaImportiPagabili.setVisible(
        UtilVerbaliImportiPagabili.checkVisibilitaTabellaImportiPagabili(dettaglioVerbale));
    tabellaImportiPagabili.setOutputMarkupId(true);
    containerDaPagare.addOrReplace(tabellaImportiPagabili);
    containerDaPagare.setVisible(!isEsistonoRate());
    addOrReplace(containerDaPagare);

    DettaglioPagamentiRatePanel pagamentoRate =
        new DettaglioPagamentiRatePanel("pagamentoRate", dettaglioVerbale);
    containerRate.addOrReplace(pagamentoRate);

    pagamentoRate.setVisible(isEsistonoRate());
    addOrReplace(containerRate);

    AttributeModifier fontBold = new AttributeModifier("style", "font-weight: bold;");

    ImportiPagabili importiPagabiliDettaglio = dettaglioVerbale.getImportiPagabili();
    String descrizioneData5gg = " - ";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getDataEntro5GG())) {
      descrizioneData5gg =
          LocalDateUtil.getDataFormatoEuropeo(importiPagabiliDettaglio.getDataEntro5GG());
    }
    String descrizioneDataEntro60gg = " - ";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getDataEntro60GG())) {
      descrizioneDataEntro60gg =
          LocalDateUtil.getDataFormatoEuropeo(importiPagabiliDettaglio.getDataEntro60GG());
    }

    Label data5gg = new Label("data5gg", descrizioneData5gg);
    Label dataEntro60gg =
        new Label(
            "dataEntro60gg",
            getString("DettaglioPagamentiPanel.entroIl")
                .concat(" ")
                .concat(descrizioneDataEntro60gg));
    Label dataOltre60gg =
        new Label(
            "dataOltre60gg",
            getString("DettaglioPagamentiPanel.dopoIl")
                .concat(" ")
                .concat(descrizioneDataEntro60gg));

    String descrizioneImporto5gg = " - ";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getImportoEntro5GG())) {
      descrizioneImporto5gg =
          numberFormatEuro.format(importiPagabiliDettaglio.getImportoEntro5GG());
    }
    String descrizioneImportoEntro60gg = " - ";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getImportoEntro60GG())) {
      descrizioneImportoEntro60gg =
          numberFormatEuro.format(importiPagabiliDettaglio.getImportoEntro60GG());
    }
    String descrizioneImportoOltre60gg = " - ";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getImportoOltre60GG())) {
      descrizioneImportoOltre60gg =
          numberFormatEuro.format(importiPagabiliDettaglio.getImportoOltre60GG());
    }
    Label importoEntro5gg = new Label("importoEntro5gg", descrizioneImporto5gg);
    Label importoEntro60gg = new Label("importoEntro60gg", descrizioneImportoEntro60gg);
    Label importoOltre60gg = new Label("importoOltre60gg", descrizioneImportoOltre60gg);

    String codiceAvviso5gg = "";
    if (UtilVerbaliImportiPagabili.checkVisibilitaCodiceAvvisoEntro5gg(
        dettaglioVerbale, getUtente())) {
      codiceAvviso5gg =
          codiceAvvisoStr.concat(" ").concat(importiPagabiliDettaglio.getCodiceAvvisoEntro5GG());
    }
    String codiceAvviso60gg = "";
    if (UtilVerbaliImportiPagabili.checkVisibilitaCodiceAvvisoEntro60gg(
        dettaglioVerbale, getUtente())) {
      codiceAvviso60gg =
          codiceAvvisoStr.concat(" ").concat(importiPagabiliDettaglio.getCodiceAvvisoEntro60GG());
    }
    String codiceAvvisoDopo60gg = "";
    if (UtilVerbaliImportiPagabili.checkVisibilitaCodiceAvvisoOltre60gg(
        dettaglioVerbale, getUtente())) {
      codiceAvvisoDopo60gg =
          codiceAvvisoStr.concat(" ").concat(importiPagabiliDettaglio.getCodiceAvvisoEntro60GG());
    }
    Label codiceAvvisoEntro5GG = new Label("codiceAvvisoEntro5GG", codiceAvviso5gg);
    Label codiceAvvisoEntro60GG = new Label("codiceAvvisoEntro60GG", codiceAvviso60gg);
    Label codiceAvvisoOltre60gg = new Label("codiceAvvisoOltre60gg", codiceAvvisoDopo60gg);

    ResourceLink pdfAvvisoEntro5GG = null;
    ResourceLink pdfAvvisoEntro60GG = null;
    ResourceLink pdfAvvisoOltre60GG = null;

    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getCodiceAvvisoEntro5GG())
        && UtilVerbaliImportiPagabili.checkVisibilitaCodiceAvvisoEntro5gg(
            dettaglioVerbale, getUtente())) {
      AvvisoPagamento avvisoPagamento =
          UtilVerbaliImportiPagabili.getAvvisoPagamento(
              getUtente(), importiPagabiliDettaglio.getCodiceAvvisoEntro5GG(), pdf, attualizza);

      if (UtilVerbali.checkIfNotNull(avvisoPagamento)) {
        pdfAvvisoEntro5GG =
            UtilVerbaliImportiPagabili.downloadPdfAvviso(avvisoPagamento, "pdfAvvisoEntro5GG");
      }
    }

    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getCodiceAvvisoEntro60GG())
        && UtilVerbaliImportiPagabili.checkVisibilitaCodiceAvvisoEntro60gg(
            dettaglioVerbale, getUtente())) {
      AvvisoPagamento avvisoPagamento =
          UtilVerbaliImportiPagabili.getAvvisoPagamento(
              getUtente(), importiPagabiliDettaglio.getCodiceAvvisoEntro60GG(), pdf, attualizza);
      if (UtilVerbali.checkIfNotNull(avvisoPagamento)) {
        pdfAvvisoEntro60GG =
            UtilVerbaliImportiPagabili.downloadPdfAvviso(avvisoPagamento, "pdfAvvisoEntro60GG");
      }
    }

    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getCodiceAvvisoEntro60GG())
        && UtilVerbaliImportiPagabili.checkVisibilitaCodiceAvvisoOltre60gg(
            dettaglioVerbale, getUtente())) {
      AvvisoPagamento avvisoPagamento =
          UtilVerbaliImportiPagabili.getAvvisoPagamento(
              getUtente(), importiPagabiliDettaglio.getCodiceAvvisoEntro60GG(), pdf, attualizza);

      if (UtilVerbali.checkIfNotNull(avvisoPagamento)) {
        pdfAvvisoOltre60GG =
            UtilVerbaliImportiPagabili.downloadPdfAvviso(avvisoPagamento, "pdfAvvisoOltre60GG");
      }
    }

    if (UtilVerbali.checkIfNotNull(pdfAvvisoEntro5GG)) {
      tabellaImportiPagabili.addOrReplace(pdfAvvisoEntro5GG);
    } else {
      tabellaImportiPagabili.addOrReplace(
          new WebMarkupContainer("pdfAvvisoEntro5GG").setVisible(false));
    }

    if (UtilVerbali.checkIfNotNull(pdfAvvisoEntro60GG)) {
      tabellaImportiPagabili.addOrReplace(pdfAvvisoEntro60GG);
    } else {
      tabellaImportiPagabili.addOrReplace(
          new WebMarkupContainer("pdfAvvisoEntro60GG").setVisible(false));
    }

    if (UtilVerbali.checkIfNotNull(pdfAvvisoOltre60GG)) {
      tabellaImportiPagabili.addOrReplace(pdfAvvisoOltre60GG);
    } else {
      tabellaImportiPagabili.addOrReplace(
          new WebMarkupContainer("pdfAvvisoOltre60GG").setVisible(false));
    }

    String importoIntegrEntro60gg = "";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getImportoIntegrazioneEntro60GG())) {
      importoIntegrEntro60gg =
          getString("DettaglioPagamentiPanel.importoIntegrazione")
              .concat(" ")
              .concat(
                  numberFormatEuro.format(
                      importiPagabiliDettaglio.getImportoIntegrazioneEntro60GG()));
    }
    String importoIntegrOltre60gg = "";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getImportoIntegrazioneOltre60GG())) {
      importoIntegrOltre60gg =
          getString("DettaglioPagamentiPanel.importoIntegrazione")
              .concat(" ")
              .concat(
                  numberFormatEuro.format(
                      importiPagabiliDettaglio.getImportoIntegrazioneOltre60GG()));
    }

    Label importoIntegrazioneEntro60gg =
        new Label("importoIntegrazioneEntro60gg", importoIntegrEntro60gg);
    Label importoIntegrazioneOltre60gg =
        new Label("importoIntegrazioneOltre60gg", importoIntegrOltre60gg);

    String codiceAvvisoIntegrazione5gg = "";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getCodiceAvvisoIntegrazione())
        && UtilVerbaliGeneraAvviso.checkVisibilitaIntegrazione5gg(
            dettaglioVerbale,
            getUtente(),
            importiPagabiliDettaglio.getCodiceAvvisoIntegrazione())) {
      codiceAvvisoIntegrazione5gg =
          getString("DettaglioPagamentiPanel.codiceAvvisoIntegrazione")
              .concat(" ")
              .concat(importiPagabiliDettaglio.getCodiceAvvisoIntegrazione());
    }

    String codiceAvvisoIntegrazioneEntro60gg = "";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getCodiceAvvisoIntegrazione())
        && UtilVerbaliGeneraAvviso.checkVisibilitaIntegrazioneEntro60gg(
            dettaglioVerbale,
            getUtente(),
            importiPagabiliDettaglio.getCodiceAvvisoIntegrazione())) {
      codiceAvvisoIntegrazioneEntro60gg =
          getString("DettaglioPagamentiPanel.codiceAvvisoIntegrazione")
              .concat(" ")
              .concat(importiPagabiliDettaglio.getCodiceAvvisoIntegrazione());
    }

    String codiceAvvisoIntegrazioneDopo60gg = "";
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbali.checkIfNotNull(importiPagabiliDettaglio.getCodiceAvvisoIntegrazione())
        && UtilVerbaliGeneraAvviso.checkVisibilitaIntegrazioneOltre60gg(
            dettaglioVerbale,
            getUtente(),
            importiPagabiliDettaglio.getCodiceAvvisoIntegrazione())) {
      codiceAvvisoIntegrazioneDopo60gg =
          getString("DettaglioPagamentiPanel.codiceAvvisoIntegrazione")
              .concat(" ")
              .concat(importiPagabiliDettaglio.getCodiceAvvisoIntegrazione());
    }

    Label codiceAvvisoIntegrazioneEntro5GG =
        new Label("codiceAvvisoIntegrazioneEntro5GG", codiceAvvisoIntegrazione5gg);
    Label codiceAvvisoIntegrazioneEntro60GG =
        new Label("codiceAvvisoIntegrazioneEntro60GG", codiceAvvisoIntegrazioneEntro60gg);
    Label codiceAvvisoIntegrazioneOltre60gg =
        new Label("codiceAvvisoIntegrazioneOltre60gg", codiceAvvisoIntegrazioneDopo60gg);

    ResourceLink pdfAvvisoIntegrazioneEntro5GG = null;
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbaliGeneraAvviso.checkVisibilitaIntegrazione5gg(
            dettaglioVerbale, getUtente(), importiPagabiliDettaglio.getPdfAvvisoIntegrazione())
        && UtilVerbali.checkIfNotNull(
            importiPagabiliDettaglio.getPdfAvvisoIntegrazione().getFile())) {
      pdfAvvisoIntegrazioneEntro5GG =
          UtilVerbaliGeneraAvviso.downloadPdfAvvisoIntegrazione(
              dettaglioVerbale, "pdfAvvisoIntegrazioneEntro5GG");
    }
    if (UtilVerbali.checkIfNotNull(pdfAvvisoIntegrazioneEntro5GG)) {
      tabellaImportiPagabili.addOrReplace(pdfAvvisoIntegrazioneEntro5GG);
    } else {
      tabellaImportiPagabili.addOrReplace(
          new WebMarkupContainer("pdfAvvisoIntegrazioneEntro5GG").setVisible(false));
    }

    ResourceLink pdfAvvisoIntegrazioneEntro60GG = null;
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbaliGeneraAvviso.checkVisibilitaIntegrazioneEntro60gg(
            dettaglioVerbale, getUtente(), importiPagabiliDettaglio.getPdfAvvisoIntegrazione())
        && UtilVerbali.checkIfNotNull(
            importiPagabiliDettaglio.getPdfAvvisoIntegrazione().getFile())) {
      pdfAvvisoIntegrazioneEntro60GG =
          UtilVerbaliGeneraAvviso.downloadPdfAvvisoIntegrazione(
              dettaglioVerbale, "pdfAvvisoIntegrazioneEntro60GG");
    }
    if (UtilVerbali.checkIfNotNull(pdfAvvisoIntegrazioneEntro60GG)) {
      tabellaImportiPagabili.addOrReplace(pdfAvvisoIntegrazioneEntro60GG);
    } else {
      tabellaImportiPagabili.addOrReplace(
          new WebMarkupContainer("pdfAvvisoIntegrazioneEntro60GG").setVisible(false));
    }

    ResourceLink pdfAvvisoIntegrazioneOltre60gg = null;
    if (UtilVerbali.checkIfNotNull(importiPagabiliDettaglio)
        && UtilVerbaliGeneraAvviso.checkVisibilitaIntegrazioneOltre60gg(
            dettaglioVerbale, getUtente(), importiPagabiliDettaglio.getPdfAvvisoIntegrazione())
        && UtilVerbali.checkIfNotNull(
            importiPagabiliDettaglio.getPdfAvvisoIntegrazione().getFile())) {
      pdfAvvisoIntegrazioneOltre60gg =
          UtilVerbaliGeneraAvviso.downloadPdfAvvisoIntegrazione(
              dettaglioVerbale, "pdfAvvisoIntegrazioneOltre60gg");
    }
    if (UtilVerbali.checkIfNotNull(pdfAvvisoIntegrazioneOltre60gg)) {
      tabellaImportiPagabili.addOrReplace(pdfAvvisoIntegrazioneOltre60gg);
    } else {
      tabellaImportiPagabili.addOrReplace(
          new WebMarkupContainer("pdfAvvisoIntegrazioneOltre60gg").setVisible(false));
    }

    if (UtilVerbaliImportiPagabili.label5GGInBold(importiPagabiliDettaglio)) {
      data5gg.add(fontBold);
      importoEntro5gg.add(fontBold);
      codiceAvvisoEntro5GG.add(fontBold);
      codiceAvvisoIntegrazioneEntro5GG.add(fontBold);
    }
    if (UtilVerbaliImportiPagabili.labelEntro60GGInBold(importiPagabiliDettaglio)) {
      dataEntro60gg.add(fontBold);
      importoEntro60gg.add(fontBold);
      codiceAvvisoEntro60GG.add(fontBold);
      importoIntegrazioneEntro60gg.add(fontBold);
      codiceAvvisoIntegrazioneEntro60GG.add(fontBold);
    }
    if (UtilVerbaliImportiPagabili.labelOltre60GGInBold(importiPagabiliDettaglio)) {
      dataOltre60gg.add(fontBold);
      importoOltre60gg.add(fontBold);
      codiceAvvisoOltre60gg.add(fontBold);
      importoIntegrazioneOltre60gg.add(fontBold);
      codiceAvvisoIntegrazioneOltre60gg.add(fontBold);
    }

    AbstractLink linkVaiAlPagamento5gg =
        UtilVerbaliImportiPagabili.creaBtnPaga5gg(importiPagabiliDettaglio, getUtente());
    AbstractLink linkVaiAlPagamento60gg =
        UtilVerbaliImportiPagabili.creaBtnPaga60gg(importiPagabiliDettaglio, getUtente());
    AbstractLink linkVaiAlPagamentoDopo60gg =
        UtilVerbaliImportiPagabili.creaBtnPagaDopo60gg(importiPagabiliDettaglio, getUtente());

    boolean paga5ggVisible =
        UtilVerbaliImportiPagabili.checkVisibilitaPaga5gg(dettaglioVerbale, getUtente());
    boolean paga60ggVisible =
        UtilVerbaliImportiPagabili.checkVisibilitaPagaEntro60gg(dettaglioVerbale, getUtente());
    boolean pagadopo60ggVisible =
        UtilVerbaliImportiPagabili.checkVisibilitaPagaDopo60gg(dettaglioVerbale, getUtente());

    linkVaiAlPagamento5gg.setVisible(paga5ggVisible);
    linkVaiAlPagamento60gg.setVisible(paga60ggVisible);
    linkVaiAlPagamentoDopo60gg.setVisible(pagadopo60ggVisible);

    tabellaImportiPagabili.addOrReplace(linkVaiAlPagamento5gg);
    tabellaImportiPagabili.addOrReplace(linkVaiAlPagamento60gg);
    tabellaImportiPagabili.addOrReplace(linkVaiAlPagamentoDopo60gg);

    tabellaImportiPagabili.addOrReplace(data5gg);
    tabellaImportiPagabili.addOrReplace(dataEntro60gg);
    tabellaImportiPagabili.addOrReplace(dataOltre60gg);

    tabellaImportiPagabili.addOrReplace(importoEntro5gg);
    tabellaImportiPagabili.addOrReplace(importoEntro60gg);
    tabellaImportiPagabili.addOrReplace(importoOltre60gg);

    tabellaImportiPagabili.addOrReplace(codiceAvvisoEntro5GG);
    tabellaImportiPagabili.addOrReplace(codiceAvvisoEntro60GG);
    tabellaImportiPagabili.addOrReplace(codiceAvvisoOltre60gg);

    tabellaImportiPagabili.addOrReplace(importoIntegrazioneEntro60gg);
    tabellaImportiPagabili.addOrReplace(importoIntegrazioneOltre60gg);

    tabellaImportiPagabili.addOrReplace(codiceAvvisoIntegrazioneEntro5GG);
    tabellaImportiPagabili.addOrReplace(codiceAvvisoIntegrazioneEntro60GG);
    tabellaImportiPagabili.addOrReplace(codiceAvvisoIntegrazioneOltre60gg);

    tabellaImportiPagabili.addOrReplace(creaBtnGeneraAvviso5gg(dettaglioVerbale));
    tabellaImportiPagabili.addOrReplace(creaBtnGeneraAvviso60gg(dettaglioVerbale));
    tabellaImportiPagabili.addOrReplace(creaBtnGeneraAvvisoDopo60gg(dettaglioVerbale));

    tabellaImportiPagabili.setOutputMarkupId(true);

    WebMarkupContainer avvisoBonarioContainer = new WebMarkupContainer("alertAvvisoBonario");
    avvisoBonarioContainer.setOutputMarkupId(true);

    avvisoBonarioContainer.setVisible(
        LabelFdCUtil.checkIfNotNull(dettaglioVerbale.getAvvisoBonario())
            && !dettaglioVerbale.getAvvisoBonario().isEmpty());
    addOrReplace(avvisoBonarioContainer);

    Link<Object> avvisoBonarioLink =
        new Link<Object>("avvisoBonarioLink") {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick() {
            try {

              AvvisoBonario avvisoBonario =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .getAvvisoBonario(dettaglioVerbale.getAvvisoBonario());
              setResponsePage(new DettaglioAvvisoBonarioPage(1, avvisoBonario, dettaglioVerbale));

            } catch (BusinessException | ApiException e) {
              // TODO Auto-generated catch block
              log.debug("[ClickAvvisoBonario] Avviso Bonario");
            }
          }
        };

    avvisoBonarioContainer.addOrReplace(avvisoBonarioLink);
  }

  private boolean isEsistonoRate() {
    if (LabelFdCUtil.checkIfNull(dettaglioVerbale.getImportiPagabili())) {
      return false;
    }

    if (LabelFdCUtil.checkIfNull(dettaglioVerbale.getImportiPagabili().getRate())) {
      return false;
    }

    return !LabelFdCUtil.checkEmptyList(dettaglioVerbale.getImportiPagabili().getRate());
  }

  private LaddaAjaxLink<Object> creaBtnGeneraAvviso5gg(DettaglioVerbale dettaglioVerbale) {
    LaddaAjaxLink<Object> btnGeneraAvviso5gg =
        new LaddaAjaxLink<Object>("btnGeneraAvviso5gg", Type.Primary) {

          private static final long serialVersionUID = -7266062095249727094L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            String dataVerbale =
                LocalDateUtil.getDataFormatoEuropeo(dettaglioVerbale.getDataAccertamento());
            BigDecimal importoInCentesimi =
                dettaglioVerbale.getImporto().multiply(new BigDecimal(100));
            BigInteger importoDaPagare = importoInCentesimi.toBigInteger();

            String indirizzoResidenza = "";
            if (getUtente().isResidente()) {
              indirizzoResidenza =
                  getUtente()
                      .getDatiCittadinoResidente()
                      .getCpvHasAddress()
                      .getClvFullAddress()
                      .concat(" ")
                      .concat(
                          getUtente()
                              .getDatiCittadinoResidente()
                              .getCpvHasAddress()
                              .getClvPostCode())
                      .concat(" ")
                      .concat(
                          getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
            }

            Optional<AnagraficaNotifica> anagrafica =
                dettaglioVerbale.getAnagraficheNotifiche().stream()
                    .filter(
                        elem ->
                            UtilVerbali.checkIfNotNull(elem.getDatiAnagrafici().getCpvTaxCode())
                                && elem.getDatiAnagrafici()
                                    .getCpvTaxCode()
                                    .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore()))
                    .findAny();
            String tipologiaAnagrafica = "";
            if (anagrafica.isPresent()) {
              tipologiaAnagrafica = anagrafica.get().getTipologiaAnagrafica();
            }

            DatiGeneraAvviso datiGeneraAvviso =
                UtilVerbaliGeneraAvviso.datiGeneraAvviso(
                    dettaglioVerbale,
                    dataVerbale,
                    importoDaPagare,
                    indirizzoResidenza,
                    tipologiaAnagrafica,
                    getUtente());

            try {

              generaAvvisoResult5gg =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .setGeneraAvviso(datiGeneraAvviso);

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore genera avviso verbali");
            }

            target.add(tabellaImportiPagabili, DettaglioPagamentiPanel.this);
            setResponsePage(new DettaglioVerbaliPage(dettaglioVerbale));
          }
        };

    IconType iconType =
        new IconType("btnGeneraAvviso5gg") {

          private static final long serialVersionUID = -5775168777882762346L;

          @Override
          public String cssClassName() {
            return "icon-pagamento";
          }
        };

    btnGeneraAvviso5gg.setOutputMarkupId(true);

    btnGeneraAvviso5gg.setIconType(iconType);
    btnGeneraAvviso5gg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DettaglioPagamentiPanel.btnGeneraAvviso", DettaglioPagamentiPanel.this)));
    AttributeModifier title =
        new AttributeModifier("title", getString("DettaglioPagamentiPanel.btnGeneraAvviso"));
    btnGeneraAvviso5gg.add(title);

    btnGeneraAvviso5gg.setVisible(
        UtilVerbaliGeneraAvviso.checkInizioGeneraAvviso(dettaglioVerbale)
            && UtilVerbaliGeneraAvviso.checkVisibilitaBtnGeneraAvviso5gg(
                dettaglioVerbale, getUtente()));

    return btnGeneraAvviso5gg;
  }

  private LaddaAjaxLink<Object> creaBtnGeneraAvviso60gg(DettaglioVerbale dettaglioVerbale) {
    LaddaAjaxLink<Object> btnGeneraAvviso60gg =
        new LaddaAjaxLink<Object>("btnGeneraAvviso60gg", Type.Primary) {

          private static final long serialVersionUID = 7847023689768259978L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            String dataVerbale =
                LocalDateUtil.getDataFormatoEuropeo(dettaglioVerbale.getDataAccertamento());
            BigDecimal importoInCentesimi =
                dettaglioVerbale.getImporto().multiply(new BigDecimal(100));
            BigInteger importoDaPagare = importoInCentesimi.toBigInteger();

            String indirizzoResidenza = "";
            if (getUtente().isResidente()) {
              indirizzoResidenza =
                  getUtente()
                      .getDatiCittadinoResidente()
                      .getCpvHasAddress()
                      .getClvFullAddress()
                      .concat(" ")
                      .concat(
                          getUtente()
                              .getDatiCittadinoResidente()
                              .getCpvHasAddress()
                              .getClvPostCode())
                      .concat(" ")
                      .concat(
                          getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
            }

            Optional<AnagraficaNotifica> anagrafica =
                dettaglioVerbale.getAnagraficheNotifiche().stream()
                    .filter(
                        elem ->
                            UtilVerbali.checkIfNotNull(elem.getDatiAnagrafici().getCpvTaxCode())
                                && elem.getDatiAnagrafici()
                                    .getCpvTaxCode()
                                    .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore()))
                    .findAny();
            String tipologiaAnagrafica = "";
            if (anagrafica.isPresent()) {
              tipologiaAnagrafica = anagrafica.get().getTipologiaAnagrafica();
            }

            DatiGeneraAvviso datiGeneraAvviso =
                UtilVerbaliGeneraAvviso.datiGeneraAvviso(
                    dettaglioVerbale,
                    dataVerbale,
                    importoDaPagare,
                    indirizzoResidenza,
                    tipologiaAnagrafica,
                    getUtente());

            try {

              generaAvvisoResult60gg =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .setGeneraAvviso(datiGeneraAvviso);

              target.add(tabellaImportiPagabili, DettaglioPagamentiPanel.this);
              setResponsePage(new DettaglioVerbaliPage(dettaglioVerbale));

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore genera avviso verbali");
            }
          }
        };

    IconType iconType =
        new IconType("btnGeneraAvviso60gg") {

          private static final long serialVersionUID = 1167424012907435044L;

          @Override
          public String cssClassName() {
            return "icon-pagamento";
          }
        };

    btnGeneraAvviso60gg.setOutputMarkupId(true);

    btnGeneraAvviso60gg.setIconType(iconType);
    btnGeneraAvviso60gg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DettaglioPagamentiPanel.btnGeneraAvviso", DettaglioPagamentiPanel.this)));
    AttributeModifier title =
        new AttributeModifier("title", getString("DettaglioPagamentiPanel.btnGeneraAvviso"));
    btnGeneraAvviso60gg.add(title);

    btnGeneraAvviso60gg.setVisible(
        UtilVerbaliGeneraAvviso.checkInizioGeneraAvviso(dettaglioVerbale)
            && UtilVerbaliGeneraAvviso.checkVisibilitaBtnGeneraAvviso60gg(
                dettaglioVerbale, getUtente()));

    return btnGeneraAvviso60gg;
  }

  private LaddaAjaxLink<Object> creaBtnGeneraAvvisoDopo60gg(DettaglioVerbale dettaglioVerbale) {
    LaddaAjaxLink<Object> btnGeneraAvvisoDopo60gg =
        new LaddaAjaxLink<Object>("btnGeneraAvvisoDopo60gg", Type.Primary) {

          private static final long serialVersionUID = -3659025500329472920L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            String dataVerbale =
                LocalDateUtil.getDataFormatoEuropeo(dettaglioVerbale.getDataAccertamento());
            BigDecimal importoInCentesimi =
                dettaglioVerbale.getImporto().multiply(new BigDecimal(100));
            BigInteger importoDaPagare = importoInCentesimi.toBigInteger();

            String indirizzoResidenza = "";
            if (getUtente().isResidente()) {
              indirizzoResidenza =
                  getUtente()
                      .getDatiCittadinoResidente()
                      .getCpvHasAddress()
                      .getClvFullAddress()
                      .concat(" ")
                      .concat(
                          getUtente()
                              .getDatiCittadinoResidente()
                              .getCpvHasAddress()
                              .getClvPostCode())
                      .concat(" ")
                      .concat(
                          getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
            }

            Optional<AnagraficaNotifica> anagrafica =
                dettaglioVerbale.getAnagraficheNotifiche().stream()
                    .filter(
                        elem ->
                            UtilVerbali.checkIfNotNull(elem.getDatiAnagrafici().getCpvTaxCode())
                                && getUtente()
                                    .getCodiceFiscaleOperatore()
                                    .equalsIgnoreCase(elem.getDatiAnagrafici().getCpvTaxCode()))
                    .findAny();
            String tipologiaAnagrafica = "";
            if (anagrafica.isPresent()) {
              tipologiaAnagrafica = anagrafica.get().getTipologiaAnagrafica();
            }

            DatiGeneraAvviso datiGeneraAvviso =
                UtilVerbaliGeneraAvviso.datiGeneraAvviso(
                    dettaglioVerbale,
                    dataVerbale,
                    importoDaPagare,
                    indirizzoResidenza,
                    tipologiaAnagrafica,
                    getUtente());

            try {

              generaAvvisoResultDopo60gg =
                  ServiceLocator.getInstance()
                      .getServiziMieiVerbali()
                      .setGeneraAvviso(datiGeneraAvviso);

              target.add(tabellaImportiPagabili, DettaglioPagamentiPanel.this);
              setResponsePage(new DettaglioVerbaliPage(dettaglioVerbale));

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore genera avviso verbali");
            }
          }
        };

    IconType iconType =
        new IconType("btnGeneraAvvisoDopo60gg") {

          private static final long serialVersionUID = -2092030119303173206L;

          @Override
          public String cssClassName() {
            return "icon-pagamento";
          }
        };

    btnGeneraAvvisoDopo60gg.setOutputMarkupId(true);

    btnGeneraAvvisoDopo60gg.setIconType(iconType);
    btnGeneraAvvisoDopo60gg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DettaglioPagamentiPanel.btnGeneraAvviso", DettaglioPagamentiPanel.this)));
    AttributeModifier title =
        new AttributeModifier("title", getString("DettaglioPagamentiPanel.btnGeneraAvviso"));
    btnGeneraAvvisoDopo60gg.add(title);

    btnGeneraAvvisoDopo60gg.setVisible(
        UtilVerbaliGeneraAvviso.checkInizioGeneraAvviso(dettaglioVerbale)
            && UtilVerbaliGeneraAvviso.checkVisibilitaBtnGeneraAvvisoDopo60gg(
                dettaglioVerbale, getUtente()));

    return btnGeneraAvvisoDopo60gg;
  }
}
