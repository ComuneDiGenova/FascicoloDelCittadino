package it.liguriadigitale.ponmetro.portale.presentation.pages.arte.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.arte.model.FatturaNonPagata;
import it.liguriadigitale.ponmetro.arte.model.FatturaPdf;
import it.liguriadigitale.ponmetro.arte.model.Mora;
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
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class MoreArtePanel extends BasePanel {

  private static final long serialVersionUID = -1795571248227275413L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public MoreArtePanel(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    Mora mora = (Mora) dati;

    log.debug("CP mora = " + mora);

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkMore(mora));
    addOrReplace(listaVuota);

    List<FatturaNonPagata> listaMore = new ArrayList<>();
    if (checkMore(mora)) {
      listaMore = mora.getFattureNonPagate();
    }

    PageableListView<FatturaNonPagata> listaFatture =
        new PageableListView<FatturaNonPagata>("more", listaMore, 4) {

          private static final long serialVersionUID = -1627390943428615585L;

          @Override
          protected void populateItem(ListItem<FatturaNonPagata> itemFattura) {
            final FatturaNonPagata fattura = itemFattura.getModelObject();

            NotEmptyLabel id = new NotEmptyLabel("id", fattura.getId());
            itemFattura.addOrReplace(id);

            itemFattura.addOrReplace(
                new AmtCardLabel<>("idImmobile", fattura.getIdImmobile(), MoreArtePanel.this));

            itemFattura.addOrReplace(
                new AmtCardLabel<>("anno", fattura.getAnno(), MoreArtePanel.this));

            itemFattura.addOrReplace(
                new AmtCardLabel<>("mese", fattura.getMese(), MoreArtePanel.this));

            itemFattura.addOrReplace(
                new AmtCardLabel<>("scadenza", fattura.getScadenza(), MoreArtePanel.this));

            itemFattura.addOrReplace(
                new AmtCardLabel<>("emesso", fattura.getEmesso(), MoreArtePanel.this));

            itemFattura.addOrReplace(
                new AmtCardLabel<>("incassato", fattura.getIncassato(), MoreArtePanel.this));

            itemFattura.addOrReplace(
                new AmtCardLabel<>("mora", fattura.getMora(), MoreArtePanel.this));

            itemFattura.addOrReplace(
                new AmtCardLabel<>("nomePdf", fattura.getNomePdf(), MoreArtePanel.this));

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaMora = creaDownloadMora(fattura);
            itemFattura.addOrReplace(btnScaricaMora);
          }
        };
    listaFatture.setVisible(checkMore(mora));
    addOrReplace(listaFatture);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listaFatture);
    paginazioneFascicolo.setVisible(checkMore(mora) && listaMore.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkMore(Mora mora) {
    return LabelFdCUtil.checkIfNotNull(mora)
        && LabelFdCUtil.checkIfNotNull(mora.getFattureNonPagate())
        && !LabelFdCUtil.checkEmptyList(mora.getFattureNonPagate());
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadMora(FatturaNonPagata fattura) {
    BottoneAJAXDownloadWithError btnScaricaMora =
        new BottoneAJAXDownloadWithError("btnScaricaMora", MoreArtePanel.this) {

          private static final long serialVersionUID = -7831531651042675934L;

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

    btnScaricaMora.setVisibileDopoDownload(true);

    return btnScaricaMora;
  }
}
