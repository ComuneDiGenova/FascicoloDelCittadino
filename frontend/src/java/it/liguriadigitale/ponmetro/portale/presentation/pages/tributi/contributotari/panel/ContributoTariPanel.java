package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.DettagliContributoTariPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiAgevolazioneTariffariaTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class ContributoTariPanel extends BasePanel {

  private static final long serialVersionUID = -2390232163337569668L;

  private static final String ICON_CONTRIBUTO_TARI_RIFIUTATA =
      "color-fc-danger col-3 icon-tari-porcellino";
  private static final String ICON_CONTRIBUTO_TARI_ACCETTATA =
      "color-fc-success col-3 icon-tari-porcellino";
  private static final String ICON_CONTRIBUTO_TARI_IN_ELABORAZIONE =
      "color-fc-warning col-3 icon-tari-porcellino";
  private static final String ICON_CONTRIBUTO_TARI_GENERICA =
      "color-green col-3 icon-tari-porcellino";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private DatiDomandaContributoTari datiDomandaContributoTari;

  public ContributoTariPanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  public ContributoTariPanel(String id, DatiDomandaContributoTari datiDomandaContributoTari) {
    super(id);

    setDatiDomandaContributoTari(datiDomandaContributoTari);

    createFeedBackPanel();

    setOutputMarkupId(true);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    fillDati(popolaDomande(getUtente().getCodiceFiscaleOperatore()));
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<DatiAgevolazioneTariffariaTari> domandeContributoTari =
        (List<DatiAgevolazioneTariffariaTari>) dati;

    DatiDomandaContributoTari datiDomandaContributoTari = getDatiDomandaContributoTari();

    LinkDinamicoLaddaFunzione<Object> btnDomandaContributoTari =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaContributoTari",
            new LinkDinamicoFunzioneData(
                "ContributoTariPanel.btnDomandaContributoTari",
                "RichiestaContributoTariPage",
                "ContributoTariPanel.btnDomandaContributoTari"),
            datiDomandaContributoTari,
            ContributoTariPanel.this,
            "color-green col-auto icon-tari-porcellino",
            isVisibileBtnRichiesta(domandeContributoTari, datiDomandaContributoTari));
    addOrReplace(btnDomandaContributoTari);

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkDomandePresenti(domandeContributoTari));
    addOrReplace(listaVuota);

    PageableListView<DatiAgevolazioneTariffariaTari> listView =
        new PageableListView<DatiAgevolazioneTariffariaTari>("domande", domandeContributoTari, 4) {

          private static final long serialVersionUID = 2120468793936335572L;

          @Override
          protected void populateItem(ListItem<DatiAgevolazioneTariffariaTari> itemDomanda) {
            final DatiAgevolazioneTariffariaTari domanda = itemDomanda.getModelObject();

            log.debug(
                "CP in list view = "
                    + domanda.getDatiCompletiRichiesta().getDatiPersoneACaricoONoRichiedente());

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaContributoTari(domanda));
            itemDomanda.addOrReplace(icona);

            itemDomanda.addOrReplace(new NotEmptyLabel("idRichiesta", domanda.getIdRichiesta()));

            itemDomanda.addOrReplace(
                new NotEmptyLabel("numeroProtocollo", domanda.getNumeroProtocollo()));

            itemDomanda.addOrReplace(
                new NotEmptyLabel("annoProtocollo", domanda.getAnnoProtocollo()));

            String codiceFiscale = "";
            String nome = "";
            String cognome = "";

            if (LabelFdCUtil.checkIfNotNull(domanda)) {
              if (LabelFdCUtil.checkIfNotNull(domanda.getDatiCompletiRichiesta())
                  && LabelFdCUtil.checkIfNotNull(
                      domanda.getDatiCompletiRichiesta().getDatiRichiedente())) {
                codiceFiscale =
                    domanda.getDatiCompletiRichiesta().getDatiRichiedente().getCodiceFiscale();
                nome = domanda.getDatiCompletiRichiesta().getDatiRichiedente().getNome();
                cognome = domanda.getDatiCompletiRichiesta().getDatiRichiedente().getCognome();
              }
            }

            itemDomanda.addOrReplace(
                new AmtCardLabel<>("codiceFiscale", codiceFiscale, ContributoTariPanel.this));

            itemDomanda.addOrReplace(new AmtCardLabel<>("nome", nome, ContributoTariPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>("cognome", cognome, ContributoTariPanel.this));

            String stato = "";
            if (PageUtil.isStringValid(domanda.getStato())) {
              stato = domanda.getStato().replaceAll("_", " ");
            }
            itemDomanda.addOrReplace(new AmtCardLabel<>("stato", stato, ContributoTariPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "dataRichiesta", domanda.getDataRichiesta(), ContributoTariPanel.this));

            itemDomanda.addOrReplace(
                creaBtnDettagli(domanda, datiDomandaContributoTari.getMqMassimi()));

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaFile = creaDownloadFile(domanda);
            itemDomanda.addOrReplace(btnScaricaFile);
          }
        };

    listView.setVisible(checkDomandePresenti(domandeContributoTari));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        checkDomandePresenti(domandeContributoTari) && domandeContributoTari.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkDomandePresenti(
      List<DatiAgevolazioneTariffariaTari> domandeTeleriscaldamento) {
    return LabelFdCUtil.checkIfNotNull(domandeTeleriscaldamento)
        && !LabelFdCUtil.checkEmptyList(domandeTeleriscaldamento);
  }

  private boolean isVisibileBtnRichiesta(
      List<DatiAgevolazioneTariffariaTari> domandeContributoTari,
      DatiDomandaContributoTari datiDomandaContributoTari) {

    boolean isBtbnVisibile = false;

    LocalDate presentabileDa = datiDomandaContributoTari.getPresentabileDa();
    LocalDate presentabileFinoAl = datiDomandaContributoTari.getPresentabileFinoAl();

    LocalDate oggi = LocalDate.now();

    if ((LabelFdCUtil.checkIfNotNull(presentabileDa)
            && LabelFdCUtil.checkIfNotNull(presentabileFinoAl))
        && (oggi.isEqual(presentabileDa)
            || oggi.isEqual(presentabileFinoAl)
            || (oggi.isAfter(presentabileDa) && oggi.isBefore(presentabileFinoAl)))) {

      isBtbnVisibile = true;

      if (LabelFdCUtil.checkIfNotNull(domandeContributoTari)) {
        Integer annoInCorso = oggi.getYear();

        if (domandeContributoTari.stream()
            .filter(elem -> elem.getDataRichiesta().getYear() == annoInCorso)
            .findFirst()
            .isPresent()) {
          isBtbnVisibile = false;
        }
      }
    }

    return isBtbnVisibile;
  }

  private AttributeAppender getCssIconaContributoTari(DatiAgevolazioneTariffariaTari dati) {
    String css = ICON_CONTRIBUTO_TARI_GENERICA;

    if (LabelFdCUtil.checkIfNotNull(dati) && LabelFdCUtil.checkIfNotNull(dati.getStato())) {
      if (dati.getStato()
              .equalsIgnoreCase(DatiAgevolazioneTariffariaTari.StatoEnum.IN_ELABORAZIONE.value())
          || dati.getStato()
              .equalsIgnoreCase(DatiAgevolazioneTariffariaTari.StatoEnum.AUTORIZZATA.value())) {
        css = ICON_CONTRIBUTO_TARI_IN_ELABORAZIONE;
      } else if (dati.getStato()
          .equalsIgnoreCase(DatiAgevolazioneTariffariaTari.StatoEnum.RIFIUTATA.value())) {
        css = ICON_CONTRIBUTO_TARI_RIFIUTATA;
      } else if (dati.getStato()
          .equalsIgnoreCase(DatiAgevolazioneTariffariaTari.StatoEnum.ACCETTATA.value())) {
        css = ICON_CONTRIBUTO_TARI_ACCETTATA;
      }
    }

    return new AttributeAppender("class", " " + css);
  }

  private LaddaAjaxLink<Object> creaBtnDettagli(
      DatiAgevolazioneTariffariaTari domanda, Double mqMassimi) {
    LaddaAjaxLink<Object> btnDettagli =
        new LaddaAjaxLink<Object>("btnDettagli", Type.Primary) {

          private static final long serialVersionUID = -5739499642470509033L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ContributoTariPanel.this);

            setResponsePage(new DettagliContributoTariPage(domanda, mqMassimi));
          }
        };
    btnDettagli.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ContributoTariPanel.btnDettagli", ContributoTariPanel.this)));

    return btnDettagli;
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadFile(DatiAgevolazioneTariffariaTari domanda) {
    BottoneAJAXDownloadWithError btnScaricaFile =
        new BottoneAJAXDownloadWithError("btnScaricaFile", ContributoTariPanel.this) {

          private static final long serialVersionUID = 973476743125831988L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            FileAllegato documento;
            String idFile = "";
            try {

              if (LabelFdCUtil.checkIfNotNull(domanda)
                  && PageUtil.isStringValid(domanda.getCodiceIdentificativoFile())) {

                idFile = domanda.getCodiceIdentificativoFile();
              }

              documento =
                  ServiceLocator.getInstance().getServiziContributoTari().getDocumento(idFile);

              String estensione = "";
              if (LabelFdCUtil.checkIfNotNull(documento)
                  && LabelFdCUtil.checkIfNotNull(documento.getFile())) {
                estensione = FileFdCUtil.getEstensionFileUploadAllegato(documento.getFile());
              }

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(documento.getFile());
              if (LabelFdCUtil.checkIfNotNull(documento)
                  && PageUtil.isStringValid(documento.getNomeFile())) {
                fileDaScaricare.setFileName(documento.getNomeFile().concat(".").concat(estensione));
              }
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;

            } catch (ApiException
                | BusinessException
                | RuntimeException
                | IOException
                | MagicMatchNotFoundException e) {

              log.debug("CP catch contributo tari getMessage = " + e.getMessage());

              String notFound = "javax.ws.rs.NotFoundException: Not Found";

              String message = e.getMessage();

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              if (message.equalsIgnoreCase(notFound)) {
                log.error("ERRORE CONTRIBUTO TARI PDF: " + e);
                fileDaScaricare.setMessaggioErrore(
                    getString("ContributoTariPanel.documentoNonTrovato"));
              } else {
                fileDaScaricare.setMessaggioErrore(
                    getString("ContributoTariPanel.erroreScaricaDocumento"));
              }
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnScaricaFile.setVisible(
        LabelFdCUtil.checkIfNotNull(domanda)
            && PageUtil.isStringValid(domanda.getCodiceIdentificativoFile()));

    btnScaricaFile.setVisibileDopoDownload(true);
    return btnScaricaFile;
  }

  public DatiDomandaContributoTari getDatiDomandaContributoTari() {
    return datiDomandaContributoTari;
  }

  public void setDatiDomandaContributoTari(DatiDomandaContributoTari datiDomandaContributoTari) {
    this.datiDomandaContributoTari = datiDomandaContributoTari;
  }

  private List<DatiAgevolazioneTariffariaTari> popolaDomande(String codiceFiscale) {
    Integer anno = LocalDate.now().getYear();

    List<DatiAgevolazioneTariffariaTari> domande = new ArrayList<>();
    try {
      domande =
          ServiceLocator.getInstance()
              .getServiziContributoTari()
              .getDomandeContributoTari(codiceFiscale, anno);
    } catch (BusinessException | ApiException e) {
      log.error("Errore popolaDomande contributo TARI: " + e.getMessage(), e);
    }

    return domande;
  }
}
