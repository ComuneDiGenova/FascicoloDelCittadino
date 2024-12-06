package it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento.TipoDocumentoEnum;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Impianto;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Manutenzione;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class ManutenzioniCaitelPanel extends BasePanel {

  private static final long serialVersionUID = 896231406335064557L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public ManutenzioniCaitelPanel(String id, Impianto impianto) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(impianto);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    Impianto impianto = (Impianto) dati;

    List<Manutenzione> listaManutenzioni = new ArrayList<Manutenzione>();
    if (checkManutenzioni(impianto)) {
      listaManutenzioni = impianto.getManutenzioni();
    } else {
      warn("Nessuna manutenzione presente");
    }

    PageableListView<Manutenzione> listView =
        new PageableListView<Manutenzione>("manutenzioni", listaManutenzioni, 4) {

          private static final long serialVersionUID = -6199673629545647364L;

          @Override
          protected void populateItem(ListItem<Manutenzione> itemManutenzioni) {
            final Manutenzione manutenzione = itemManutenzioni.getModelObject();

            String dataManutenzioneInFormatoGGMMAAAA = "";
            if (LabelFdCUtil.checkIfNotNull(manutenzione.getDataManutenzione())) {
              dataManutenzioneInFormatoGGMMAAAA =
                  LocalDateUtil.getDataFormatoEuropeo(manutenzione.getDataManutenzione());
            }
            NotEmptyLabel dataManutenzione =
                new NotEmptyLabel("dataManutenzione", dataManutenzioneInFormatoGGMMAAAA);
            dataManutenzione.setVisible(PageUtil.isStringValid(dataManutenzioneInFormatoGGMMAAAA));
            itemManutenzioni.addOrReplace(dataManutenzione);

            itemManutenzioni.addOrReplace(
                new AmtCardLabel<>("ditta", manutenzione.getDitta(), ManutenzioniCaitelPanel.this));

            String origine = "";
            if (PageUtil.isStringValid(manutenzione.getOrigine())) {
              if (manutenzione.getOrigine().equalsIgnoreCase("M")) {
                origine = "Inserita manualmente";
              }
              if (manutenzione.getOrigine().equalsIgnoreCase("R")) {
                origine = "Derivata da rapporto di efficienza energetica";
              }
            }
            itemManutenzioni.addOrReplace(
                new AmtCardLabel<>("origine", origine, ManutenzioniCaitelPanel.this));

            itemManutenzioni.addOrReplace(
                new AmtCardLabel<>(
                    "manutenzione", manutenzione.getManutenzione(), ManutenzioniCaitelPanel.this));

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaManutanzione =
                creaDownloadManutenzione(impianto, manutenzione);
            itemManutenzioni.addOrReplace(btnScaricaManutanzione);
          }
        };
    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkManutenzioni(impianto) && listaManutenzioni.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkManutenzioni(Impianto impianto) {
    return LabelFdCUtil.checkIfNotNull(impianto)
        && LabelFdCUtil.checkIfNotNull(impianto.getManutenzioni())
        && !LabelFdCUtil.checkEmptyList(impianto.getManutenzioni());
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadManutenzione(
      Impianto impianto, Manutenzione manutenzione) {
    BottoneAJAXDownloadWithError btnScaricaManutanzione =
        new BottoneAJAXDownloadWithError("btnScaricaManutanzione", ManutenzioniCaitelPanel.this) {

          private static final long serialVersionUID = 7455769031239111740L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            Documento documento;
            try {

              String idImpianto = "";
              String idGruppo = "";
              String idRapporto = "";

              if (LabelFdCUtil.checkIfNotNull(manutenzione)
                  && LabelFdCUtil.checkIfNotNull(manutenzione.getIdManutenzione())) {
                idRapporto = String.valueOf(manutenzione.getIdManutenzione());
              }

              TipoDocumentoEnum tipoDocumento = TipoDocumentoEnum.DETTAGLIOMAN;

              if (LabelFdCUtil.checkIfNotNull(manutenzione)) {

                if (LabelFdCUtil.checkIfNotNull(impianto.getIdImpianto())) {
                  idImpianto = String.valueOf(impianto.getIdImpianto());
                }

                if (LabelFdCUtil.checkIfNotNull(impianto.getIdGruppo())) {
                  idGruppo = String.valueOf(impianto.getIdGruppo());
                }
              }

              documento =
                  ServiceLocator.getInstance()
                      .getServiziImpiantiTermici()
                      .getDocumento(idImpianto, idGruppo, idRapporto, tipoDocumento.value());

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(documento.getFile());
              fileDaScaricare.setFileName(documento.getNomeFile());
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
        };

    btnScaricaManutanzione.setVisible(
        LabelFdCUtil.checkIfNotNull(impianto)
            && LabelFdCUtil.checkIfNotNull(manutenzione)
            && PageUtil.isStringValid(manutenzione.getManutenzione()));

    btnScaricaManutanzione.setVisibileDopoDownload(true);

    return btnScaricaManutanzione;
  }
}
