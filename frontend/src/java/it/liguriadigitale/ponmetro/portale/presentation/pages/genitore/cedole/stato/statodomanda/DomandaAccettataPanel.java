package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.util.DateUtil;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.DocumentoNonTrovatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.annulla.AnnullaRitiroCedolaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.prenotazione.PrenotazioneCedolaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.ritiro.RitiroEffettuatoCedolaPage;
import it.liguriadigitale.ponmetro.scuola.cedole.model.AllegatoPDF;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola.StatoRitiroEnum;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class DomandaAccettataPanel extends BasePanel {

  private static final long serialVersionUID = 1922276247258571694L;
  private Log log = LogFactory.getLog(DomandaAccettataPanel.class);

  public DomandaAccettataPanel(String id, Cedola cedola) {
    super(id);
    fillDati(cedola);
  }

  @SuppressWarnings("rawtypes")
  private void addDownloadLink(Cedola cedola) {
    final AbstractResource pdfResourceByte;

    pdfResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = -1074345906676060158L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {

            final ResourceResponse r = new ResourceResponse();
            try {
              final AllegatoPDF allegato =
                  ServiceLocator.getInstance().getCedoleLibrarie().getCedolaPDF(cedola);
              byte[] pdfBytes = allegato.getFile();
              String dataFileName = DateUtil.toStringInteropFromDate(new Date());
              r.setFileName(
                  allegato.getNomeFile()
                      + "_"
                      + getUtente().getCodiceFiscaleOperatore()
                      + "_"
                      + dataFileName
                      + ".pdf");

              r.setContentType("application/pdf");
              r.setContentDisposition(ContentDisposition.INLINE);
              r.setContentLength(pdfBytes.length);
              r.setWriteCallback(
                  new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                      attributes.getResponse().write(pdfBytes);
                    }
                  });

              r.disableCaching();
              return r;
            } catch (final BusinessException | ApiException e) {
              log.error("Errore durante scaricamento del pdf");
              throw new RestartResponseAtInterceptPageException(DocumentoNonTrovatoPage.class);
            }
          }
        };

    final ResourceLink linkPdf =
        new ResourceLink("btnDownload", pdfResourceByte) {

          private static final long serialVersionUID = -4613703674361043929L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
          }

          @Override
          public boolean isVisible() {
            String statoRitiro = cedola.getStatoRitiro();
            if (statoRitiro != null) {
              return isVisibileDownload(statoRitiro);
            } else {
              return true;
            }
          }
        };

    add(linkPdf);
  }

  @Override
  public void fillDati(Object dati) {
    Cedola cedola = (Cedola) dati;
    log.debug("cedola: " + cedola);
    add(new NotEmptyLabel("numeroCedola", cedola.getCedolaCodice()));
    add(new NotEmptyLocalDateLabel("data", cedola.getDataDomanda()));

    add(new NotEmptyLabel("cartolibreria", cedola.getCartolibreria()));

    add(new NotEmptyLabel("indirizzoCartolibreria", cedola.getIndirizzoCartolibreria()));

    cedola.getIndirizzoCartolibreria();

    String strFasciatura = "NO";
    if (cedola.getFasciatura() != null && cedola.getFasciatura()) {
      strFasciatura = "SI";
    }

    String strLibroReligione = "NO";
    if (cedola.getLibroReligione() != null && cedola.getLibroReligione()) {
      strLibroReligione = "SI";
    }

    NotEmptyLabel nElFasciatura = new NotEmptyLabel("fasciatura", strFasciatura);
    nElFasciatura.setVisible(
        cedola.getIndirizzoCartolibreria() != null
            && !cedola.getIndirizzoCartolibreria().equalsIgnoreCase(""));
    add(nElFasciatura);

    NotEmptyLabel nElLibroReligione = new NotEmptyLabel("libroReligione", strLibroReligione);
    add(nElLibroReligione);

    add(new NotEmptyLocalDateLabel("dataprenotazione", cedola.getDataPrenotazione()));
    add(new NotEmptyLabel("scuola", cedola.getScuola()));
    add(new NotEmptyLabel("classe", cedola.getClasse() + " " + getSezione(cedola)));
    add(new NotEmptyLabel("soggetto", cedola.getSoggettoPrenotante()));
    add(new NotEmptyLabel("tiposoggetto", cedola.getTipoSoggettoPrenotante()));
    add(new NotEmptyLabel("statoritiro", cedola.getStatoRitiro()));

    addDownloadLink(cedola);

    Link linkannulla =
        new Link<Void>("annulla") {
          @Override
          public void onClick() {
            setResponsePage(new AnnullaRitiroCedolaPage(cedola));
          }

          @Override
          public boolean isVisible() {
            String statoRitiro = cedola.getStatoRitiro();
            if (statoRitiro != null) {
              return !isVisibileAnnullaPrenotazione(statoRitiro);
            } else {
              return false;
            }
          }
        };
    add(linkannulla);

    Link linkRitiroEffettuato =
        new Link<Void>("ritiro") {
          @Override
          public void onClick() {
            setResponsePage(new RitiroEffettuatoCedolaPage(cedola));
          }

          @Override
          public boolean isVisible() {
            String statoRitiro = cedola.getStatoRitiro();
            if (statoRitiro != null) {
              return isStatoRitiro(statoRitiro);
            } else {
              return false;
            }
          }
        };
    add(linkRitiroEffettuato);
    Link linkprenotazione =
        new Link<Void>("prenotazione") {
          @Override
          public void onClick() {
            setResponsePage(new PrenotazioneCedolaPage(cedola));
          }

          @Override
          public boolean isVisible() {
            String statoRitiro = cedola.getStatoRitiro();
            if (statoRitiro != null) {
              // TODO valutare se togliere il pulsante
              return false;
            } else {
              return true;
            }
          }
        };
    add(linkprenotazione);
  }

  public String getSezione(Cedola cedola) {
    if (StringUtils.isEmpty(cedola.getSezione())) {
      return "";
    } else {
      return cedola.getSezione();
    }
  }

  private boolean isStatoRitiro(String statoRitiro) {
    return statoRitiro.equalsIgnoreCase(StatoRitiroEnum.DA_RITIRARE.value());
  }

  private boolean isVisibileAnnullaPrenotazione(String statoRitiro) {
    return statoRitiro.equalsIgnoreCase(StatoRitiroEnum.PRENOTATO.value())
        || statoRitiro.equalsIgnoreCase(StatoRitiroEnum.DA_RITIRARE.value())
        || statoRitiro.equalsIgnoreCase(StatoRitiroEnum.RITIRATO.value());
  }

  private boolean isVisibileDownload(String statoRitiro) {
    return statoRitiro.equalsIgnoreCase(StatoRitiroEnum.RICHIESTO.value())
        || statoRitiro.equalsIgnoreCase(StatoRitiroEnum.PRENOTATO.value())
        || statoRitiro.equalsIgnoreCase(StatoRitiroEnum.RITIRATO.value())
        || statoRitiro.equalsIgnoreCase(StatoRitiroEnum.DA_RITIRARE.value());
  }
}
