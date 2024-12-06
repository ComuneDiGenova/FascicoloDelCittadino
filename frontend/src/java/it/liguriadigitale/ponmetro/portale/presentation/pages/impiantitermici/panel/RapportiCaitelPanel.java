package it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento.TipoDocumentoEnum;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Impianto;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Rapporto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class RapportiCaitelPanel extends BasePanel {

  private static final long serialVersionUID = 3370481612756438226L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public RapportiCaitelPanel(String id, Impianto impianto) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(impianto);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    Impianto impianto = (Impianto) dati;

    List<Rapporto> listaRapporti = new ArrayList<Rapporto>();
    if (checkRapporti(impianto)) {
      listaRapporti = impianto.getRapporti();
    } else {
      warn("Nessun rapporto presente");
    }

    log.debug("CP rapporti = " + listaRapporti);

    PageableListView<Rapporto> listView =
        new PageableListView<Rapporto>("rapporti", listaRapporti, 4) {

          private static final long serialVersionUID = -6239446092959100060L;

          @Override
          protected void populateItem(ListItem<Rapporto> itemRapporto) {
            final Rapporto rapporto = itemRapporto.getModelObject();

            String dataRapportoInFormatoGGMMAAAA = "";
            if (LabelFdCUtil.checkIfNotNull(rapporto.getDataRapporto())) {
              dataRapportoInFormatoGGMMAAAA =
                  LocalDateUtil.getDataFormatoEuropeo(rapporto.getDataRapporto());
            }
            NotEmptyLabel dataRapporto =
                new NotEmptyLabel("dataRapporto", dataRapportoInFormatoGGMMAAAA);
            dataRapporto.setVisible(PageUtil.isStringValid(dataRapportoInFormatoGGMMAAAA));
            itemRapporto.addOrReplace(dataRapporto);

            itemRapporto.addOrReplace(
                new AmtCardLabel<>("ditta", rapporto.getDitta(), RapportiCaitelPanel.this));

            itemRapporto.addOrReplace(
                new AmtCardLabel<>(
                    "dataProtocollo", rapporto.getDataprotocollo(), RapportiCaitelPanel.this));

            itemRapporto.addOrReplace(
                new AmtCardLabel<>(
                    "generatore", rapporto.getGeneratore(), RapportiCaitelPanel.this));

            itemRapporto.addOrReplace(
                new AmtCardLabel<>("macchina", rapporto.getMacchina(), RapportiCaitelPanel.this));

            itemRapporto.addOrReplace(
                new AmtCardLabel<>("sigla", rapporto.getSigla(), RapportiCaitelPanel.this));

            itemRapporto.addOrReplace(
                new AmtCardLabel<>("rapporto", rapporto.getRapporto(), RapportiCaitelPanel.this));

            itemRapporto.addOrReplace(
                new AmtCardLabel<>("ricevuta", rapporto.getRicevuta(), RapportiCaitelPanel.this));

            itemRapporto.addOrReplace(
                new AmtCardLabel<>(
                    "codRapporto", rapporto.getCodRapporto(), RapportiCaitelPanel.this));

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaRapporto =
                creaDownloadRapporto(impianto, rapporto);
            itemRapporto.addOrReplace(btnScaricaRapporto);

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaRicevuta =
                creaDownloadRicevuta(impianto, rapporto);
            itemRapporto.addOrReplace(btnScaricaRicevuta);
          }
        };
    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkRapporti(impianto) && listaRapporti.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkRapporti(Impianto impianto) {
    return LabelFdCUtil.checkIfNotNull(impianto)
        && LabelFdCUtil.checkIfNotNull(impianto.getRapporti())
        && !LabelFdCUtil.checkEmptyList(impianto.getRapporti());
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadRapporto(Impianto impianto, Rapporto rapporto) {
    BottoneAJAXDownloadWithError btnScaricaRapporto =
        new BottoneAJAXDownloadWithError("btnScaricaRapporto", RapportiCaitelPanel.this) {

          private static final long serialVersionUID = -7192656769394552379L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            Documento documento;
            try {

              String idImpianto = "";
              String idGruppo = "";
              String idRapporto = "";
              TipoDocumentoEnum tipoDocumento = TipoDocumentoEnum.RAPPORTO;

              if (LabelFdCUtil.checkIfNotNull(impianto)) {

                if (LabelFdCUtil.checkIfNotNull(impianto.getIdImpianto())) {
                  idImpianto = String.valueOf(impianto.getIdImpianto());
                }

                if (LabelFdCUtil.checkIfNotNull(impianto.getIdGruppo())) {
                  idGruppo = String.valueOf(impianto.getIdGruppo());
                }
              }

              if (LabelFdCUtil.checkIfNotNull(rapporto)
                  && LabelFdCUtil.checkIfNotNull(rapporto.getIdRapporto())) {
                idRapporto = String.valueOf(rapporto.getIdRapporto());
              }

              documento =
                  ServiceLocator.getInstance()
                      .getServiziImpiantiTermici()
                      .getDocumento(idImpianto, idGruppo, idRapporto, tipoDocumento.value());

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

              log.debug("CP catch rapporti getMessage = " + e.getMessage());

              String notFound = "javax.ws.rs.NotFoundException: Not Found";

              String message = e.getMessage();

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              if (message.equalsIgnoreCase(notFound)) {
                log.error("ERRORE API CAITEL PDF: " + e);
                fileDaScaricare.setMessaggioErrore(
                    getString("RapportiCaitelPanel.documentoNonTrovato"));
              } else {
                fileDaScaricare.setMessaggioErrore(
                    getString("RapportiCaitelPanel.erroreScaricaDocumento"));
              }
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnScaricaRapporto.setVisible(
        LabelFdCUtil.checkIfNotNull(impianto)
            && LabelFdCUtil.checkIfNotNull(rapporto)
            && PageUtil.isStringValid(rapporto.getRapporto()));

    btnScaricaRapporto.setVisibileDopoDownload(true);
    return btnScaricaRapporto;
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadRicevuta(Impianto impianto, Rapporto rapporto) {
    @SuppressWarnings("serial")
    BottoneAJAXDownloadWithError btnScaricaRicevuta =
        new BottoneAJAXDownloadWithError("btnScaricaRicevuta", RapportiCaitelPanel.this) {

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            Documento documento;
            try {

              String idImpianto = "";
              String idGruppo = "";
              String idRapporto = "";

              if (LabelFdCUtil.checkIfNotNull(impianto)) {

                if (LabelFdCUtil.checkIfNotNull(impianto.getIdImpianto())) {
                  idImpianto = String.valueOf(impianto.getIdImpianto());
                }

                if (LabelFdCUtil.checkIfNotNull(impianto.getIdGruppo())) {
                  idGruppo = String.valueOf(impianto.getIdGruppo());
                }
              }

              if (LabelFdCUtil.checkIfNotNull(rapporto)
                  && LabelFdCUtil.checkIfNotNull(rapporto.getIdRapporto())) {
                idRapporto = String.valueOf(rapporto.getIdRapporto());
              }

              TipoDocumentoEnum tipoDocumento = TipoDocumentoEnum.RICEVUTA;

              documento =
                  ServiceLocator.getInstance()
                      .getServiziImpiantiTermici()
                      .getDocumento(idImpianto, idGruppo, idRapporto, tipoDocumento.value());

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(documento.getFile());
              fileDaScaricare.setFileName(documento.getNomeFile());
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;

            } catch (ApiException | BusinessException | RuntimeException | IOException e) {

              log.debug("CP catch ricevuta getMessage = " + e.getMessage());

              String notFound = "javax.ws.rs.NotFoundException: Not Found";

              String message = e.getMessage();

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              if (message.equalsIgnoreCase(notFound)) {
                log.error("ERRORE API CAITEL PDF: " + e);
                fileDaScaricare.setMessaggioErrore(
                    getString("RapportiCaitelPanel.documentoNonTrovato"));
              } else {
                fileDaScaricare.setMessaggioErrore(
                    getString("RapportiCaitelPanel.erroreScaricaDocumento"));
              }
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnScaricaRicevuta.setVisible(
        LabelFdCUtil.checkIfNotNull(impianto)
            && LabelFdCUtil.checkIfNotNull(rapporto)
            && PageUtil.isStringValid(rapporto.getRicevuta()));

    btnScaricaRicevuta.setVisibileDopoDownload(true);

    return btnScaricaRicevuta;
  }
}
