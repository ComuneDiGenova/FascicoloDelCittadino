package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.ricorsialprefetto.panel;

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
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RicorsoAlPrefetto;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class RicorsiAlPrefettoPanel extends BasePanel {

  private static final long serialVersionUID = -7990710597223658888L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public RicorsiAlPrefettoPanel(String id, List<RicorsoAlPrefetto> listaRicorsi) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(listaRicorsi);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<RicorsoAlPrefetto> listaRicorsi = (List<RicorsoAlPrefetto>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkRicorsi(listaRicorsi));
    addOrReplace(listaVuota);

    PageableListView<RicorsoAlPrefetto> listView =
        new PageableListView<RicorsoAlPrefetto>("ricorsi", listaRicorsi, 4) {

          private static final long serialVersionUID = -5958519530250351311L;

          @Override
          protected void populateItem(ListItem<RicorsoAlPrefetto> itemRicorso) {
            final RicorsoAlPrefetto ricorso = itemRicorso.getModelObject();

            String idRicorso = "";
            if (LabelFdCUtil.checkIfNotNull(ricorso.getProtocolloRicorso())) {
              idRicorso =
                  getString("RicorsiAlPrefettoPanel.identificativoRicorso")
                      .concat(" ")
                      .concat(ricorso.getProtocolloRicorso().toString())
                      .concat(" ");
            }
            if (LabelFdCUtil.checkIfNotNull(ricorso.getAnnoRicorso())) {
              idRicorso = idRicorso.concat(ricorso.getAnnoRicorso().toString());
            }

            itemRicorso.addOrReplace(new NotEmptyLabel("identificativoRicorso", idRicorso));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "numeroAvviso", ricorso.getNumeroAvviso(), RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "dataVerbale", ricorso.getDataVerbale(), RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "numeroProtocollo",
                    ricorso.getNumeroProtocollo(),
                    RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>("targa", ricorso.getTarga(), RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "nomeRichiedente", ricorso.getNomeRichiedente(), RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "nomeRichiedente", ricorso.getNomeRichiedente(), RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "cognomeRichiedente",
                    ricorso.getCognomeRichiedente(),
                    RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscaleRichiedente",
                    ricorso.getCodiceFiscaleRichiedente(),
                    RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "emailRichiedente",
                    ricorso.getEmailRichiedente(),
                    RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "pecRichiedente", ricorso.getPecRichiedente(), RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "cellulareRichiedente",
                    ricorso.getCellulareRichiedente(),
                    RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "natoIlRichiedente",
                    ricorso.getNatoIlRichiedente(),
                    RicorsiAlPrefettoPanel.this));

            String indirizzoRichiedente = "";
            if (LabelFdCUtil.checkIfNotNull(ricorso.getIndirizzoRichiedente())
                && PageUtil.isStringValid(ricorso.getIndirizzoRichiedente().getClvFullAddress())
                && PageUtil.isStringValid(ricorso.getIndirizzoRichiedente().getClvCity())) {
              indirizzoRichiedente =
                  ricorso
                      .getIndirizzoRichiedente()
                      .getClvFullAddress()
                      .concat(" ")
                      .concat(ricorso.getIndirizzoRichiedente().getClvCity());
            }
            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "indirizzoRichiedente", indirizzoRichiedente, RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "dataRichiestaRicorso",
                    ricorso.getDataRichiestaRicorso(),
                    RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "noteRichiedente", ricorso.getNoteRichiedente(), RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>(
                    "dataRicevuta", ricorso.getDataRicevuta(), RicorsiAlPrefettoPanel.this));

            itemRicorso.addOrReplace(
                new AmtCardLabel<>("stato", ricorso.getStato(), RicorsiAlPrefettoPanel.this));

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnFileAllegato =
                creaBtnDownloadPdf(
                    "btnFileAllegato", ricorso.getFileAllegato(), ricorso.getNumeroProtocollo());
            itemRicorso.addOrReplace(btnFileAllegato);

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnPdfRicevuta =
                creaBtnDownloadPdf(
                    "btnPdfRicevuta", ricorso.getPdfRicevuta(), ricorso.getNumeroProtocollo());
            itemRicorso.addOrReplace(btnPdfRicevuta);
          }
        };

    listView.setVisible(checkRicorsi(listaRicorsi));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkRicorsi(listaRicorsi) && listaRicorsi.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkRicorsi(List<RicorsoAlPrefetto> listaRicorsi) {
    return LabelFdCUtil.checkIfNotNull(listaRicorsi) && !LabelFdCUtil.checkEmptyList(listaRicorsi);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaBtnDownloadPdf(
      String wicketId, FileAllegato fileAllegato, String numeroProtocollo) {
    BottoneAJAXDownloadWithError creaDownload =
        new BottoneAJAXDownloadWithError(wicketId, RicorsiAlPrefettoPanel.this) {

          private static final long serialVersionUID = 5156540104844321722L;

          FileDaScaricare fileDaScaricare = new FileDaScaricare();

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
            String nomeFile = "";
            byte[] file = null;

            if (LabelFdCUtil.checkIfNotNull(fileAllegato)) {
              nomeFile = fileAllegato.getNomeFile();

              if (LabelFdCUtil.checkIfNotNull(fileAllegato.getId())) {
                String allegatoId = String.valueOf(fileAllegato.getId());

                try {
                  FileAllegato fileAllegatoRisposta =
                      ServiceLocator.getInstance()
                          .getServiziMieiVerbali()
                          .getFileAllegatoAlVerbaleByAllegatoId(
                              getUtente(), numeroProtocollo, allegatoId);

                  file = fileAllegatoRisposta.getFile();

                } catch (BusinessException | ApiException | IOException e) {
                  log.error("Errore durante scarico allegato ricorsi: " + e.getMessage());
                  fileDaScaricare.setEsitoOK(false);
                }
              }
            }

            fileDaScaricare.setFileBytes(file);
            fileDaScaricare.setFileName(nomeFile);
            fileDaScaricare.setEsitoOK(true);
            return fileDaScaricare;
          }

          @Override
          protected String creaLabelEtichetta(Component pannello, String id) {
            String nomePannello = pannello.getClass().getSimpleName();
            String resourceId = nomePannello + "." + wicketId;
            String etichetta = getLocalizer().getString(resourceId, pannello);

            return etichetta;
          }
        };

    creaDownload.setVisibileDopoDownload(true);

    creaDownload.setVisibilityAllowed(LabelFdCUtil.checkIfNotNull(fileAllegato));

    return creaDownload;
  }
}
