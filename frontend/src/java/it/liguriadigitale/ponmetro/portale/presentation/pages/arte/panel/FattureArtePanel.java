package it.liguriadigitale.ponmetro.portale.presentation.pages.arte.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arte.model.Fattura;
import it.liguriadigitale.ponmetro.arte.model.FatturaPdf;
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
import java.io.IOException;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class FattureArtePanel extends BasePanel {

  private static final long serialVersionUID = 3345617982283143841L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public FattureArtePanel(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    createFeedBackPanel();
  }

  public FattureArtePanel(String id, List<Fattura> lista) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(lista);

    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<Fattura> fatture = (List<Fattura>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkFatture(fatture));
    addOrReplace(listaVuota);

    PageableListView<Fattura> listaFatture =
        new PageableListView<Fattura>("fatture", fatture, 6) {

          private static final long serialVersionUID = 710217759602818216L;

          @Override
          protected void populateItem(ListItem<Fattura> itemFattura) {
            final Fattura fattura = itemFattura.getModelObject();

            NotEmptyLabel nomePdf = new NotEmptyLabel("nomePdf", fattura.getNomePdf());
            itemFattura.addOrReplace(nomePdf);

            itemFattura.addOrReplace(
                new AmtCardLabel<>("anno", fattura.getAnno(), FattureArtePanel.this));

            itemFattura.addOrReplace(
                new AmtCardLabel<>("mese", fattura.getMese(), FattureArtePanel.this));

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaRicevuta = creaDownloadFattura(fattura);
            itemFattura.addOrReplace(btnScaricaRicevuta);
          }
        };
    listaFatture.setVisible(checkFatture(fatture));
    addOrReplace(listaFatture);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listaFatture);
    paginazioneFascicolo.setVisible(LabelFdCUtil.checkIfNotNull(fatture) && fatture.size() > 6);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkFatture(List<Fattura> fatture) {
    return LabelFdCUtil.checkIfNotNull(fatture) && !LabelFdCUtil.checkEmptyList(fatture);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadFattura(Fattura fattura) {
    BottoneAJAXDownloadWithError btnScaricaFattura =
        new BottoneAJAXDownloadWithError("btnScaricaFattura", FattureArtePanel.this) {

          private static final long serialVersionUID = -2462124386849804338L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
            try {

              String nomeFile = "";
              byte[] file = null;

              if (LabelFdCUtil.checkIfNotNull(fattura)
                  && PageUtil.isStringValid(fattura.getNomePdf())) {

                nomeFile = fattura.getNomePdf();

                FatturaPdf fatturaPdf =
                    ServiceLocator.getInstance().getServiziArte().getFatturaPdf(nomeFile);

                if (LabelFdCUtil.checkIfNotNull(fatturaPdf)) {
                  file = fatturaPdf.getFile();
                }
              }

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(file);
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

    btnScaricaFattura.setVisibileDopoDownload(true);

    return btnScaricaFattura;
  }
}
