package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.attestazionipagamenti.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AttestazioniDiPagamento;
import it.liguriadigitale.ponmetro.serviziristorazione.model.FileAllegato;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.StringResourceModel;

public class AttestatoPagamentiPanel extends BasePanel {

  private static final long serialVersionUID = -8087750230904020603L;

  private static final String ICON_ATTESTAZIONE_PAGAMENTO =
      "color-orange col-3 icon-attestazione-pagamento-mensa";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private UtenteServiziRistorazione iscrizione;

  private Integer annoPrecedenteAllAnnoCorrente = LocalDate.now().minusYears(1).getYear();

  public AttestatoPagamentiPanel(String id, UtenteServiziRistorazione iscrizione) {
    super(id);

    setIscrizione(iscrizione);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<AttestazioniDiPagamento> listaAttestazioniPagamento = (List<AttestazioniDiPagamento>) dati;

    WebMarkupContainer containerListaVuota = new WebMarkupContainer("containerListaVuota");
    Label listaVuota =
        new Label(
            "listaVuota",
            new StringResourceModel("AttestatoPagamentiPanel.listaVuota", this)
                .setParameters(getIscrizione().getNome(), getIscrizione().getCognome()));
    containerListaVuota.addOrReplace(listaVuota);
    containerListaVuota.setVisible(!checkPresenzaAttestatiPagamento(listaAttestazioniPagamento));
    containerListaVuota.setOutputMarkupId(true);
    containerListaVuota.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerListaVuota);

    Label listaPiena =
        new Label(
            "listaPiena",
            new StringResourceModel("AttestatoPagamentiPanel.listaPiena", this)
                .setParameters(getIscrizione().getNome(), getIscrizione().getCognome()));
    listaPiena.setVisible(checkPresenzaAttestatiPagamento(listaAttestazioniPagamento));
    listaPiena.setOutputMarkupId(true);
    listaPiena.setOutputMarkupPlaceholderTag(true);
    addOrReplace(listaPiena);

    PageableListView<AttestazioniDiPagamento> listView =
        new PageableListView<AttestazioniDiPagamento>("box", listaAttestazioniPagamento, 4) {

          private static final long serialVersionUID = 63609650036389494L;

          @Override
          protected void populateItem(ListItem<AttestazioniDiPagamento> item) {
            final AttestazioniDiPagamento attestazione = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIcona(attestazione));
            item.addOrReplace(icona);

            item.addOrReplace(new NotEmptyLabel("annoSolare", attestazione.getAnnoSolare()));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "importoScuola",
                    attestazione.getImportoScuola(),
                    AttestatoPagamentiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "importoAsilo", attestazione.getImportoAsilo(), AttestatoPagamentiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "numeroProtocolloIn",
                    attestazione.getNumeroProtocolloIn(),
                    AttestatoPagamentiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "annoProtocolloIn",
                    attestazione.getAnnoProtocolloIn(),
                    AttestatoPagamentiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "numeroProtocolloOut",
                    attestazione.getNumeroProtocolloOut(),
                    AttestatoPagamentiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "annoProtocolloOut",
                    attestazione.getAnnoProtocolloOut(),
                    AttestatoPagamentiPanel.this));

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaRicevuta =
                creaDownloadAttestazione(
                    getIscrizione().getCodiceFiscale(),
                    attestazione.getAnnoSolare(),
                    attestazione.getNumeroProtocolloOut());
            item.addOrReplace(btnScaricaRicevuta);
          }
        };

    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);
    listView.setVisible(checkPresenzaAttestatiPagamento(listaAttestazioniPagamento));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationAttestatoPagamenti", listView);
    paginazioneFascicolo.setVisible(
        checkPresenzaAttestatiPagamento(listaAttestazioniPagamento)
            && listaAttestazioniPagamento.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkPresenzaAttestatiPagamento(List<AttestazioniDiPagamento> lista) {
    return LabelFdCUtil.checkIfNotNull(lista) && !LabelFdCUtil.checkEmptyList(lista);
  }

  private AttributeAppender getCssIcona(AttestazioniDiPagamento attestazione) {
    String css = "";

    css = ICON_ATTESTAZIONE_PAGAMENTO;

    return new AttributeAppender("class", " " + css);
  }

  @SuppressWarnings({"rawtypes", "serial"})
  private BottoneAJAXDownloadWithError creaDownloadAttestazione(
      String codiceFiscaleIscritto, Integer annoSolare, String numeroProtocolloOut) {
    BottoneAJAXDownloadWithError btnScaricaDocumento =
        new BottoneAJAXDownloadWithError("btnScaricaDocumento", AttestatoPagamentiPanel.this) {

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
            FileAllegato response;

            try {

              response =
                  ServiceLocator.getInstance()
                      .getServiziRistorazione()
                      .getPdfAttestazionePagamento(codiceFiscaleIscritto, annoSolare);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(response.getFile());
              String nomeFileConEstensione =
                  response.getNomeFile().concat(".").concat(response.getEstensioneFile());
              fileDaScaricare.setFileName(nomeFileConEstensione);
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;
            } catch (ApiException | BusinessException | IOException e) {

              String prefisso = "GNC-000-Server was unable to process request. --->";
              String message = e.getMessage();
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              if (message.contains(prefisso)) {
                log.error("ERRORE API: " + e);
                fileDaScaricare.setMessaggioErrore(message.replace(prefisso, ""));
              } else {
                fileDaScaricare.setMessaggioErrore(message);
              }
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }

          @Override
          protected String creaLabelEtichetta(Component pannello, String id) {
            String nomePannello = pannello.getClass().getSimpleName();
            String resourceId = nomePannello + "." + "scarica";
            String etichetta = getLocalizer().getString(resourceId, pannello);

            // if (annoSolare.equals(annoPrecedenteAllAnnoCorrente)) {
            if (!PageUtil.isStringValid(numeroProtocolloOut)) {
              resourceId = nomePannello + "." + "genera";
              etichetta = getLocalizer().getString(resourceId, pannello);
            }
            // }

            return etichetta;
          }
        };

    btnScaricaDocumento.setVisibileDopoDownload(true);

    // btnScaricaDocumento.setVisible(!(Integer.compare(annoSolare,
    // annoPrecedenteAllAnnoCorrente) == -1
    // && !PageUtil.isStringValid(numeroProtocolloOut)));

    return btnScaricaDocumento;
  }

  public UtenteServiziRistorazione getIscrizione() {
    return iscrizione;
  }

  public void setIscrizione(UtenteServiziRistorazione iscrizione) {
    this.iscrizione = iscrizione;
  }
}
