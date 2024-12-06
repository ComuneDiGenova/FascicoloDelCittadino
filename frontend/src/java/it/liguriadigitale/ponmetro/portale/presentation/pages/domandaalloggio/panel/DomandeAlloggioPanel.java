package it.liguriadigitale.ponmetro.portale.presentation.pages.domandaalloggio.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.bandirealgim.model.V1BandoDomandeViewFullDOM;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

public class DomandeAlloggioPanel extends BasePanel {

  private static final long serialVersionUID = 1124449651644765793L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  //	public DomandeAlloggioPanel(String id, List<String> listaPratiche) {
  //		super(id);
  //
  //		createFeedBackPanel();
  //
  //		setOutputMarkupId(true);
  //
  //		fillDati(listaPratiche);
  //
  //	}

  public DomandeAlloggioPanel(String id, V1BandoDomandeViewFullDOM dettaglio) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(dettaglio);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    //		List<String> listaPratiche = (List<String>) dati;
    //
    //		WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    //		listaVuota.setVisible(!checkPratiche(listaPratiche));
    //		addOrReplace(listaVuota);
    //
    //		PageableListView<String> listView = new PageableListView<String>("pratiche", listaPratiche,
    // 4) {
    //
    //			private static final long serialVersionUID = -4958590730986269569L;
    //
    //			@Override
    //			protected void populateItem(ListItem<String> itemPratica) {
    //				final String pratica = itemPratica.getModelObject();
    //
    //				itemPratica.addOrReplace(
    //						new AmtCardLabel<>("id", pratica, DomandeAlloggioPanel.this));
    //
    //			}
    //		};
    //
    //		listView.setVisible(checkPratiche(listaPratiche));
    //		addOrReplace(listView);
    //
    //		paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    //		paginazioneFascicolo.setVisible(checkPratiche(listaPratiche) && listaPratiche.size() > 4);
    //		addOrReplace(paginazioneFascicolo);

    V1BandoDomandeViewFullDOM dettaglio = (V1BandoDomandeViewFullDOM) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkDettaglio(dettaglio));
    addOrReplace(listaVuota);

    // TODO
    WebMarkupContainer elemDettaglio = new WebMarkupContainer("elemDettaglio");
    String codIdentificativoDomanda = "";
    String statoDomanda = "";
    String descrizioneStato = "";
    LocalDate dataPresentazione = null;
    String numeroProtocollo = "";
    LocalDate dataProtocollo = null;
    String idBandoDomande = "";

    if (LabelFdCUtil.checkIfNotNull(dettaglio)) {
      codIdentificativoDomanda = dettaglio.getCodIdentificativoDomanda();
      statoDomanda = dettaglio.getStatoDomandaText();
      descrizioneStato = dettaglio.getDescrizioneStato();

      if (LabelFdCUtil.checkIfNotNull(dettaglio.getDataPresentazione())) {
        dataPresentazione =
            LocalDateUtil.getLocalDateFromOffsetDateTime(dettaglio.getDataPresentazione());
      }

      numeroProtocollo = dettaglio.getNumeroProtocollo();

      if (LabelFdCUtil.checkIfNotNull(dettaglio.getDataProtocollo())) {
        dataProtocollo =
            LocalDateUtil.getLocalDateFromOffsetDateTime(dettaglio.getDataProtocollo());
      }

      idBandoDomande = dettaglio.getIdBandoDomande();
    }

    elemDettaglio.addOrReplace(new Label("codIdentificativoDomanda", codIdentificativoDomanda));
    elemDettaglio.addOrReplace(
        new AmtCardLabel<>("statoDomanda", statoDomanda, DomandeAlloggioPanel.this));
    elemDettaglio.addOrReplace(
        new AmtCardLabel<>("descrizioneStato", descrizioneStato, DomandeAlloggioPanel.this));
    elemDettaglio.addOrReplace(
        new AmtCardLabel<>("dataPresentazione", dataPresentazione, DomandeAlloggioPanel.this));
    elemDettaglio.addOrReplace(
        new AmtCardLabel<>("numeroProtocollo", numeroProtocollo, DomandeAlloggioPanel.this));
    elemDettaglio.addOrReplace(
        new AmtCardLabel<>("dataProtocollo", dataProtocollo, DomandeAlloggioPanel.this));

    @SuppressWarnings("rawtypes")
    BottoneAJAXDownloadWithError btnScaricaRicevuta = creaDownloadRicevuta(idBandoDomande);
    elemDettaglio.addOrReplace(btnScaricaRicevuta);

    elemDettaglio.setVisible(checkDettaglio(dettaglio));
    addOrReplace(elemDettaglio);
  }

  private boolean checkPratiche(List<String> listaPratiche) {
    return LabelFdCUtil.checkIfNotNull(listaPratiche)
        && !LabelFdCUtil.checkEmptyList(listaPratiche);
  }

  private boolean checkDettaglio(V1BandoDomandeViewFullDOM dettaglio) {
    return LabelFdCUtil.checkIfNotNull(dettaglio);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadRicevuta(String idBandoDomande) {
    BottoneAJAXDownloadWithError btnScaricaRicevuta =
        new BottoneAJAXDownloadWithError("btnScaricaRicevuta", DomandeAlloggioPanel.this) {

          private static final long serialVersionUID = -6040297282572237899L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              byte[] ricevuta =
                  ServiceLocator.getInstance()
                      .getServiziDomandeAlloggio()
                      .getDownloadDocumentAnnouncementQuestion(
                          getUtente().getCodiceFiscaleOperatore(), idBandoDomande);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              if (LabelFdCUtil.checkIfNotNull(ricevuta)) {
                fileDaScaricare.setFileBytes(ricevuta);
                String estenzione = FileFdCUtil.getEstensionFileUploadAllegato(ricevuta);
                fileDaScaricare.setFileName(
                    "Ricevuta_".concat(idBandoDomande).concat(".").concat(estenzione));
                fileDaScaricare.setEsitoOK(true);
              } else {
                fileDaScaricare.setMessaggioErrore("Errore durante download ricevuta");
                fileDaScaricare.setEsitoOK(false);
              }

              return fileDaScaricare;

            } catch (ApiException
                | BusinessException
                | IOException
                | MagicMatchNotFoundException e) {
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setMessaggioErrore("Errore durante download file");
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnScaricaRicevuta.setVisibileDopoDownload(true);

    btnScaricaRicevuta.setVisible(PageUtil.isStringValid(idBandoDomande));

    return btnScaricaRicevuta;
  }
}
