package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.panel;

import it.liguriadigitale.ponmetro.portale.pojo.portale.StatoAgevolazione;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class StatoAgevolazionePanel extends BasePanel {

  private static final long serialVersionUID = -4652274627139372877L;

  public StatoAgevolazionePanel() {
    super("agevolazionePanel");
    createFeedBackPanel();
    fillDati(new UtenteServiziRistorazione());
  }

  public StatoAgevolazionePanel(UtenteServiziRistorazione iscritto) {
    super("agevolazionePanel");
    createFeedBackPanel();
    fillDati(iscritto);
  }

  @Override
  public void fillDati(Object dati) {
    UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;

    Long annoDiPartenza = 2010L;
    List<StatoAgevolazione> lista =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .elencoRichiesteAgevolazioniConFiglioAnnoPartenza(iscritto, annoDiPartenza);

    log.debug("UtenteServiziRistorazione - AgevolazioneTariffariaRistorazione: lista : " + lista);
    ListView<StatoAgevolazione> listView =
        new ListView<StatoAgevolazione>("lista", lista) {

          private static final long serialVersionUID = -2552599382811312891L;

          @SuppressWarnings("rawtypes")
          @Override
          protected void populateItem(ListItem<StatoAgevolazione> item) {
            StatoAgevolazione statoAgevolazioneTarif = item.getModelObject();

            StatoRichiestaAgevolazioneEnum statoRichiesta =
                statoAgevolazioneTarif.getStatoRichiestaAgevolazione();

            log.debug("UtenteServiziRistorazione - populateItem: iscritto : " + iscritto);

            item.add(
                new Label(
                    "nomeCognome",
                    statoAgevolazioneTarif.getNome() + " " + statoAgevolazioneTarif.getCognome()));
            item.add(new LocalDateLabel("dataNascita", statoAgevolazioneTarif.getDataNascita()));

            Integer annoScolasticoValue =
                Integer.parseInt(statoAgevolazioneTarif.getAnnoScolastico()) + 1;
            String annoScolastico =
                statoAgevolazioneTarif.getAnnoScolastico() + "/" + annoScolasticoValue;
            item.add(new Label("annoScolastico", annoScolastico));

            item.add(new Label("statoRichiesta", statoRichiesta));

            final ResourceLink linkPdf = downloadPdfRichiestaAgevolazione(statoAgevolazioneTarif);
            Label cartaceo = new Label("cartaceo", getString("StatoAgevolazionePanel.cartaceo"));

            if (statoRichiesta != null
                && !statoRichiesta.equals(StatoRichiestaAgevolazioneEnum.NON_PRESENTATA)) {
              boolean isPdfPresente = StringUtils.isNotEmpty(statoAgevolazioneTarif.getCodicePDF());
              cartaceo.setVisible(!isPdfPresente);
              linkPdf.setVisible(isPdfPresente);
            } else {
              cartaceo.setVisible(false);
              linkPdf.setVisible(false);
            }
            item.add(cartaceo);
            item.add(linkPdf);
          }
        };
    add(listView);
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfRichiestaAgevolazione(StatoAgevolazione iscrittoAgTarif) {
    final AbstractResource pdfResourceByte;
    String cf = iscrittoAgTarif.getCodiceFiscale();
    String anno = iscrittoAgTarif.getAnnoScolastico();
    String codicePdf = iscrittoAgTarif.getCodicePDF();
    String suffissoNomePdf = anno + "_" + codicePdf + ".pdf";
    String nomePdf = cf + "_RichiestaAgevolazioneTariffaria_" + suffissoNomePdf;

    pdfResourceByte =
        new AbstractResource() {
          private static final long serialVersionUID = -1074345906676060158L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {

              final byte[] pdfBytes =
                  ServiceLocator.getInstance()
                      .getServiziRistorazione()
                      .getPdfRichiestaAgevolazione(codicePdf);

              r.setFileName(nomePdf);
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
            } catch (final Exception e) {
              log.error("Errore durante scarico pdf");
            }

            return r;
          }
        };

    final ResourceLink linkPdf =
        new ResourceLink("btnDownload", pdfResourceByte) {

          private static final long serialVersionUID = -4613703674361043929L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica PDF: " + suffissoNomePdf);
          }
        };
    return linkPdf;
  }
}
