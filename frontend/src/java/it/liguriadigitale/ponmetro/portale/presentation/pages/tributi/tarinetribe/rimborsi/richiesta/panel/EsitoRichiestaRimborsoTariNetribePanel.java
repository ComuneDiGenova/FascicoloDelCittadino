package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.richiesta.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiFileAllegatoNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribeResult;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsoResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.StringResourceModel;

public class EsitoRichiestaRimborsoTariNetribePanel extends BasePanel {

  private static final long serialVersionUID = 6821218555817886543L;

  private int index;

  private WebMarkupContainer rimborsoKO = new WebMarkupContainer("rimborsoKO");

  public EsitoRichiestaRimborsoTariNetribePanel(String id, int index) {
    super(id);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);
  }

  @Override
  public void fillDati(Object dati) {
    DatiRichiestaRimborsoTariNetribeResult esitoRichiestaRimborsi =
        (DatiRichiestaRimborsoTariNetribeResult) dati;

    rimborsoKO.setOutputMarkupId(true);
    rimborsoKO.setOutputMarkupPlaceholderTag(true);
    rimborsoKO.setVisible(!esitoRichiestaRimborsi.isEsitoOk());

    List<TARIRimborsoResult> rimborsi = new ArrayList<TARIRimborsoResult>();
    String messaggioErrore = "";

    if (esitoRichiestaRimborsi.isEsitoOk()) {
      rimborsi = esitoRichiestaRimborsi.getRisultatiRimborsi();
    } else {
      messaggioErrore = esitoRichiestaRimborsi.getMessaggioKO();
    }

    Label messaggioKO =
        new Label(
            "messaggioKO",
            new StringResourceModel("EsitoRichiestaRimborsoTariNetribePanel.messaggioKO", this)
                .setParameters(messaggioErrore));

    messaggioKO.setOutputMarkupId(true);
    messaggioKO.setOutputMarkupPlaceholderTag(true);
    rimborsoKO.addOrReplace(messaggioKO);
    addOrReplace(rimborsoKO);

    rimborsoKO.setVisible(!esitoRichiestaRimborsi.isEsitoOk());

    ListView<TARIRimborsoResult> listViewRimborsi =
        new ListView<TARIRimborsoResult>("rimborsi", rimborsi) {

          private static final long serialVersionUID = 9077376119331423944L;

          @Override
          protected void populateItem(ListItem<TARIRimborsoResult> itemRimborso) {
            final TARIRimborsoResult rimborso = itemRimborso.getModelObject();

            WebMarkupContainer rimborsoOK = new WebMarkupContainer("rimborsoOK");

            Label messaggioOK =
                new Label(
                    "messaggioOK",
                    new StringResourceModel(
                            "EsitoRichiestaRimborsoTariNetribePanel.messaggioOK", this)
                        .setParameters(
                            String.valueOf(rimborso.getIdRichiesta()),
                            rimborso.getNumeroProtocollo(),
                            rimborso.getAnnoProtocollo()));
            rimborsoOK.addOrReplace(messaggioOK);

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnDocumento =
                creaDownloadDocumento(rimborso.getIdFileProtocollo());
            rimborsoOK.addOrReplace(btnDocumento);

            rimborsoOK.setOutputMarkupId(true);
            rimborsoOK.setOutputMarkupPlaceholderTag(true);
            itemRimborso.addOrReplace(rimborsoOK);
          }
        };

    listViewRimborsi.setVisible(esitoRichiestaRimborsi.isEsitoOk());
    addOrReplace(listViewRimborsi);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadDocumento(String identificativoDocumento) {
    BottoneAJAXDownloadWithError btnDocumento =
        new BottoneAJAXDownloadWithError(
            "btnDocumento", EsitoRichiestaRimborsoTariNetribePanel.this) {

          private static final long serialVersionUID = -1786756813315347827L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              DatiFileAllegatoNetribe datiFileAllegato =
                  ServiceLocator.getInstance()
                      .getServiziTariNetribe()
                      .datiFileAllegatoNetribe(identificativoDocumento);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              if (datiFileAllegato.isScaricato()) {
                FileAllegato documento = datiFileAllegato.getFileAllegatoNetribe();

                fileDaScaricare.setFileBytes(documento.getFile());
                fileDaScaricare.setFileName(documento.getNomeFile());
                fileDaScaricare.setEsitoOK(datiFileAllegato.isScaricato());
              } else {
                fileDaScaricare.setMessaggioErrore(datiFileAllegato.getMessaggioErrore());
                fileDaScaricare.setEsitoOK(datiFileAllegato.isScaricato());
              }

              return fileDaScaricare;

            } catch (ApiException | BusinessException | IOException e) {
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setMessaggioErrore("Errore durante download file");
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnDocumento.setVisibileDopoDownload(true);

    return btnDocumento;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
