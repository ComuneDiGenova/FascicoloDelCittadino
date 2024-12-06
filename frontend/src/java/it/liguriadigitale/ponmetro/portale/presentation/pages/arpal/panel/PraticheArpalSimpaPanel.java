package it.liguriadigitale.ponmetro.portale.presentation.pages.arpal.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arpal.model.ItemA;
import it.liguriadigitale.ponmetro.arpal.model.ItemS;
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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class PraticheArpalSimpaPanel extends BasePanel {

  private static final long serialVersionUID = 1L;

  public PraticheArpalSimpaPanel(String id, List<ItemS> lista) {

    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
    fillDati(lista);
  }

  @SuppressWarnings({"unchecked", "serial"})
  @Override
  public void fillDati(Object dati) {

    List<ItemS> listaItemS = (List<ItemS>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(
        LabelFdCUtil.checkIfNull(listaItemS)
            || (LabelFdCUtil.checkIfNotNull(listaItemS))
                && LabelFdCUtil.checkEmptyList(listaItemS));
    addOrReplace(listaVuota);

    ListView<ItemS> listView =
        new ListView<ItemS>("lista", listaItemS) {
          @Override
          protected void populateItem(ListItem<ItemS> item) {
            ItemS elem = item.getModelObject();

            log.debug("Item :" + item);
            Map<String, Object> mappa = new LinkedHashMap<>();
            mappa.put("cfPiva", elem.getCfPiva());
            mappa.put("matricola", elem.getMatricola());
            mappa.put("ragioneSoc", elem.getRagioneSociale());
            mappa.put("indirizzo", elem.getIndirizzo());
            mappa.put("dataVer", elem.getDataVerifica());

            insertImportoFattura(elem, mappa);

            mappa.put("ruolo", elem.getRuolo());
            mappa.put("stato", elem.getStato());
            mappa.put("dataAppr", elem.getVeiDataProxVer());
            mappa.put("veiEsito", elem.getVeiEsito());
            mappa.put("statoVerifica", elem.getStatoVerifica());

            log.debug("Allegato :" + elem.getAllegato());

            GeneratoreCardLabel<ItemA> panel =
                new GeneratoreCardLabel<>(
                    "myPanel", mappa, PraticheArpalSimpaPanel.class.getSimpleName());

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaAllegato = creaDownloadAllegato(elem);
            item.addOrReplace(panel);
            item.addOrReplace(btnScaricaAllegato);
          }
        };

    add(listView);
  }

  private void insertImportoFattura(ItemS elem, Map<String, Object> mappa) {

    BigDecimal zeroDecimale = new BigDecimal("0.00");
    int num = 0;
    if (LabelFdCUtil.checkIfNotNull(elem)
        && LabelFdCUtil.checkIfNotNull(elem.getImportofattura())) {
      num = elem.getImportofattura().compareTo(zeroDecimale);
    }
    if (num >= 1) {
      BigDecimal importoFatturaInBigDecimal = elem.getImportofattura();
      Double importoFatturaInDouble = importoFatturaInBigDecimal.doubleValue();
      mappa.put("importoFatt", importoFatturaInDouble);
    }
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadAllegato(ItemS item) {

    BottoneAJAXDownloadWithError btnScaricaAllegato =
        new BottoneAJAXDownloadWithError("btnScaricaAllegato", PraticheArpalSimpaPanel.this) {

          private static final long serialVersionUID = 2080786660774215037L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              String nomeFile = "";
              File file = null;

              log.debug("avant-simpa" + item);

              if (LabelFdCUtil.checkIfNotNull(item) && PageUtil.isStringValid(item.getAllegato())) {
                log.debug("apres-simpa " + item);
                nomeFile = item.getAllegato();
                file = ServiceLocator.getInstance().getServiziArpal().getAllegatoPdfSimpa(nomeFile);
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

    if (item.getAllegato() != null) {
      btnScaricaAllegato.setVisibileDopoDownload(true);
    } else {
      btnScaricaAllegato.setVisible(false);
    }
    return btnScaricaAllegato;
  }
}
