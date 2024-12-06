package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.condoni.dettaglio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.edilizia.condono.model.Abuso;
import it.liguriadigitale.ponmetro.edilizia.condono.model.AllegatoBody;
import it.liguriadigitale.ponmetro.edilizia.condono.model.AllegatoMetadata;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponseCompleta;
import it.liguriadigitale.ponmetro.edilizia.condono.model.Indirizzo;
import it.liguriadigitale.ponmetro.edilizia.condono.model.Soggetto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.FdCMultiLineLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class DettaglioCondoniPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7660309620639141558L;
  // private Form form;

  int numeroPratica;
  int annoPratica;

  public DettaglioCondoniPage(int numeroPratica, int annoPratica) {
    super();
    this.numeroPratica = numeroPratica;
    this.annoPratica = annoPratica;
    List<BreadcrumbFdC> listaBreadcrumb = getListaBreadcrumb();
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    try {

      CondonoResponseCompleta condonoResponseCompleta =
          ServiceLocator.getInstance()
              .getServiziEdiliziaCondono()
              .getDettaglioCondono(numeroPratica, annoPratica);

      Label recId = new Label("recId", condonoResponseCompleta.getRecId());
      recId.setVisible(false);
      add(recId);

      Label lblNumeroPratica =
          new Label("numeroPratica", condonoResponseCompleta.getNumeroPratica());
      add(lblNumeroPratica);

      Label lblAnnoPratica = new Label("lblAnnoPratica", condonoResponseCompleta.getAnnoPratica());
      add(lblAnnoPratica);

      String strSoggetto;
      List<String> listaSoggetti = new ArrayList<>();
      for (Soggetto soggetto : condonoResponseCompleta.getNominativi()) {
        strSoggetto =
            soggetto.getCognome()
                + " "
                + soggetto.getNome()
                + " ("
                + soggetto.getCodiceFiscale()
                + ")";
        listaSoggetti.add(strSoggetto);
      }
      FdCMultiLineLabel nominativi =
          new FdCMultiLineLabel("nominativi", listaSoggetti, DettaglioCondoniPage.this);
      add(nominativi);

      String strAbuso;
      List<String> listaAbusi = new ArrayList<>();
      for (Abuso abuso : condonoResponseCompleta.getAbusi()) {
        strAbuso = abuso.getDescrizione();
        listaAbusi.add(strAbuso);
      }
      FdCMultiLineLabel abusi =
          new FdCMultiLineLabel("abusi", listaAbusi, DettaglioCondoniPage.this);
      add(abusi);

      String strIndirizzi;
      List<String> listaIndirizzi = new ArrayList<>();
      for (Indirizzo indirizzo : condonoResponseCompleta.getIndirizzi()) {
        strIndirizzi = "";

        if (indirizzo.getVia() != null) {
          strIndirizzi = strIndirizzi.concat(indirizzo.getVia());
        }

        if (indirizzo.getCivico() != null) {
          strIndirizzi = strIndirizzi.concat("" + indirizzo.getCivico());
        }

        if (indirizzo.getNumero() != null) {
          strIndirizzi = strIndirizzi.concat("/" + indirizzo.getNumero());
        }

        listaIndirizzi.add(strIndirizzi);
      }

      FdCMultiLineLabel indirizzi =
          new FdCMultiLineLabel("indirizzi", listaIndirizzi, DettaglioCondoniPage.this);
      add(indirizzi);

      /*
       * String strDocumento; List<String> listaDocumenti = new ArrayList<>(); for
       * (AllegatoMetadata allegatoMetadata : condonoResponseCompleta.getDocumenti())
       * { strDocumento = allegatoMetadata.getNome();
       * listaDocumenti.add(strDocumento);
       *
       *
       * BottoneAJAXDownloadWithError btnDownloadPDF =
       * creaDownloadPDF(allegatoMetadata); addOrReplace(new
       * Label("nomeFile",Model.of(allegatoMetadata.getNome())));
       * addOrReplace(btnDownloadPDF);
       *
       *
       * }
       */

      ListView<AllegatoMetadata> listView =
          new ListView<AllegatoMetadata>("listView", condonoResponseCompleta.getDocumenti()) {

            private static final long serialVersionUID = -1949303234023054323L;

            @Override
            protected void populateItem(ListItem<AllegatoMetadata> item) {
              AllegatoMetadata allegatoMetadata = item.getModelObject();

              BottoneAJAXDownloadWithError btnDownloadPDF = creaDownloadPDF(allegatoMetadata);
              item.addOrReplace(new Label("nomeFile", Model.of(allegatoMetadata.getNome())));
              item.addOrReplace(btnDownloadPDF);
            }
          };
      add(listView);

      //			FdCMultiLineLabel documenti = new FdCMultiLineLabel("documenti", listaDocumenti,
      // DettaglioCondoniPage.this);
      //			add(documenti);

    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("elenco condoni"));
    }
  }

  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("condoniEdilizia", "I miei condoni edilizi"));
    listaBreadcrumb.add(new BreadcrumbFdC("dettaglioCondoniEdilizia", "Dettaglio condoni edilizi"));
    return listaBreadcrumb;
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadPDF(AllegatoMetadata allegatoMetadata) {

    BottoneAJAXDownloadWithError btnScaricaRicevuta =
        new BottoneAJAXDownloadWithError("btnScaricaPDF", DettaglioCondoniPage.this) {

          private static final long serialVersionUID = -8998456801171600346L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              AllegatoBody allegatoBody =
                  ServiceLocator.getInstance()
                      .getServiziEdiliziaCondono()
                      .getFilePDF(
                          numeroPratica,
                          annoPratica,
                          allegatoMetadata.getNumeroProvvedimento(),
                          allegatoMetadata.getDataProvvedimento().getYear(),
                          allegatoMetadata.getNome());

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              fileDaScaricare.setFileBytes(allegatoBody.getAllegatoFile().getFile());
              fileDaScaricare.setFileName(allegatoBody.getMetaDati().getNome());
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;
            } catch (ApiException | BusinessException e) {

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

    btnScaricaRicevuta.setVisibileDopoDownload(true);

    return btnScaricaRicevuta;
  }
}
