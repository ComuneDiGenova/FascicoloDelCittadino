package it.liguriadigitale.ponmetro.portale.presentation.pages.arpal.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arpal.model.ItemA;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.GeneratoreCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class PraticheArpalAlimsPanel extends BasePanel {

  private static final long serialVersionUID = -3778073753192285905L;

  public PraticheArpalAlimsPanel(String id, List<ItemA> lista) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
    fillDati(lista);
  }

  @SuppressWarnings({"unchecked", "serial"})
  @Override
  public void fillDati(Object dati) {

    log.debug("Inizio Arpal fillDati");
    List<ItemA> listaItemA = (List<ItemA>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(
        LabelFdCUtil.checkIfNull(listaItemA)
            || (LabelFdCUtil.checkIfNotNull(listaItemA))
                && LabelFdCUtil.checkEmptyList(listaItemA));
    addOrReplace(listaVuota);

    ListView<ItemA> listView =
        new ListView<ItemA>("lista", listaItemA) {
          @Override
          protected void populateItem(ListItem<ItemA> item) {

            ItemA elem = item.getModelObject();
            Map<String, Object> mappa = new LinkedHashMap<>();
            log.debug("Item :" + item);
            mappa.put("cf", elem.getCodiceFiscale());
            mappa.put("campione", elem.getCampione());
            mappa.put("matrice", elem.getMatrice());
            mappa.put("dataAcc", elem.getDataAccettazione());
            mappa.put("dataRdp", elem.getDataRdp());
            mappa.put("nomeRdp", elem.getNomeRdp());
            mappa.put("stato", elem.getStato());

            GeneratoreCardLabel<ItemA> panel =
                new GeneratoreCardLabel<>(
                    "myPanel", mappa, PraticheArpalAlimsPanel.class.getSimpleName());

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaAllegato = creaDownloadAllegato(elem);
            item.addOrReplace(panel);
            item.addOrReplace(btnScaricaAllegato);
          }
        };
    add(listView);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadAllegato(ItemA item) {

    BottoneAJAXDownloadWithError btnScaricaAllegato =
        new BottoneAJAXDownloadWithError("btnScaricaAllegato", PraticheArpalAlimsPanel.this) {

          private static final long serialVersionUID = 122730422092894313L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              log.debug("avant-alims If");

              String nomeFile = "";
              File file = null;

              if (LabelFdCUtil.checkIfNotNull(item) && PageUtil.isStringValid(item.getNameFile())) {

                log.debug("dans-alims  If");
                nomeFile = item.getNameFile();
                log.debug("nomeFile: " + nomeFile);
                String nomeDir = item.getNameDirectory();
                log.debug("nomeDir : " + nomeDir);
                File allegatoPdf =
                    ServiceLocator.getInstance()
                        .getServiziArpal()
                        .getAllegatoPdfAlims(nomeFile, nomeDir);
                if (LabelFdCUtil.checkIfNotNull(allegatoPdf)) {
                  file = allegatoPdf;
                }
              }

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              byte[] bytes = Files.readAllBytes(file.toPath());
              fileDaScaricare.setFileBytes(bytes);
              fileDaScaricare.setFileName(nomeFile);
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

    if (item.getNameDirectory() == null || item.getNameFile() == null) {
      btnScaricaAllegato.setVisible(false);
    } else {
      btnScaricaAllegato.setVisibileDopoDownload(true);
    }
    return btnScaricaAllegato;
  }
}
