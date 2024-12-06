package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.util.DateUtil;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.DocumentoNonTrovatoPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIscrizioneServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum;
import java.time.LocalDate;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class StatoIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = -4652274627139372877L;

  public StatoIscrizionePanel(UtenteServiziRistorazione iscrizione) {
    super("iscrizionePanel");
    createFeedBackPanel();
    fillDati(iscrizione);
  }

  @SuppressWarnings("rawtypes")
  private void addDownloadLink(String codicePDF, WebMarkupContainer containerPdf) {
    final AbstractResource pdfResourceByte;

    pdfResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = -1074345906676060158L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {

            final ResourceResponse r = new ResourceResponse();
            try {
              final byte[] pdfBytes =
                  ServiceLocator.getInstance().getServiziRistorazione().getPdfIscrizione(codicePDF);

              String dataFileName = DateUtil.toStringInteropFromDate(new Date());
              r.setFileName(
                  getUtente().getCodiceFiscaleOperatore()
                      + "_"
                      + dataFileName
                      + "_StatoIscrizione.pdf");

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
        };

    containerPdf.add(linkPdf);
  }

  @Override
  public void fillDati(Object dati) {
    UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;

    String statoText =
        getString("StatoIscrizionePanel.titolo")
            + " "
            + iscritto.getNome()
            + " "
            + iscritto.getCognome();

    add(new Label("nomeCognome", iscritto.getNome() + " " + iscritto.getCognome()));
    add(new Label("statoIscrizione", iscritto.getStatoIscrizioneServiziRistorazione()));

    String nomeImpegnato = iscritto.getNomeImpegnato() != null ? iscritto.getNomeImpegnato() : "-";
    String cognomeImpegnato =
        iscritto.getCognomeImpegnato() != null ? iscritto.getCognomeImpegnato() : "-";
    String labelNomeImpegnato = nomeImpegnato.concat(" ").concat(cognomeImpegnato);

    if (iscritto.getNomeImpegnato() == null || iscritto.getCognomeImpegnato() == null) {
      labelNomeImpegnato = "Informazione disponibile nel PDF della domanda";
    }
    Label labelImpegnato = new Label("nomeImpegnato", labelNomeImpegnato);
    add(labelImpegnato);

    add(new Label("iscritto", statoText));

    WebMarkupContainer containerIscrittoDal = new WebMarkupContainer("containerIscrittoDal");
    LocalDate localDateIscrittoDal =
        iscritto.getDataIscrizioneServiziRistorazione() == null
            ? null
            : iscritto.getDataIscrizioneServiziRistorazione();
    containerIscrittoDal.add(new LocalDateLabel("iscrittoDal", localDateIscrittoDal));
    containerIscrittoDal.add(
        new Label(
            "iscrizione",
            StringUtils.isNotEmpty(getStringTextIscrizione(iscritto))
                ? getStringTextIscrizione(iscritto)
                : ""));
    add(containerIscrittoDal);

    WebMarkupContainer containerScuola = new WebMarkupContainer("containerScuola");
    String scuola =
        (iscritto.getStrutturaScolastica() == null
            ? null
            : iscritto.getCategoriaStrutturaScolastica().toLowerCase()
                + "  "
                + iscritto.getStrutturaScolastica().toLowerCase());
    Label lblScuola = new Label("scuola", scuola);
    lblScuola.setVisible(this.isIscritto(iscritto));
    containerScuola.add(lblScuola);
    add(containerScuola);

    WebMarkupContainer containerClasse = new WebMarkupContainer("containerClasse");
    Label lblClasse = new Label("sezione", this.getClasseESezione(iscritto));
    lblClasse.setVisible(StringUtils.isNotEmpty(this.getClasseESezione(iscritto)));
    containerClasse.add(lblClasse);
    add(containerClasse);

    WebMarkupContainer containerPdf = new WebMarkupContainer("containerPdf");
    String codicePdfDomanda = iscritto.getCodicePdfDomanda();

    Label lblPDf = new Label("codicepdf", codicePdfDomanda);
    lblPDf.setVisible(false); // StringUtils.isNotEmpty(codicePdfDomanda));
    containerPdf.add(lblPDf);
    add(containerPdf);

    // Cambia la visibilita dei container (della li)
    // in presenza di enclosure la condizione e' sul figlio
    containerIscrittoDal.setVisible(localDateIscrittoDal != null);
    containerScuola.setVisible(StringUtils.isNotEmpty(scuola));
    containerClasse.setVisible(StringUtils.isNotEmpty(this.getClasseESezione(iscritto)));
    containerPdf.setVisible(StringUtils.isNotEmpty(codicePdfDomanda));

    addDownloadLink(codicePdfDomanda, containerPdf);

    impostaStatoIscrizione(iscritto);
  }

  private String getClasseESezione(UtenteServiziRistorazione iscritto) {
    String ret = "";

    // TODO FRR uniformare con metodo del pannello riepilogoiscrizionipanel
    if (isIscritto(iscritto)) {
      String classe = iscritto.getClasse();
      String sezione = iscritto.getSezione();
      if (classe != null
          && StringUtils.isNotEmpty(classe)
          && !classe.equalsIgnoreCase("0")
          && !classe.equalsIgnoreCase("null")) {
        ret = ret + classe + " ";
      }
      if (sezione != null
          && !sezione.isEmpty()
          && !sezione.equalsIgnoreCase("0")
          && !sezione.equalsIgnoreCase("null")) {
        ret = ret + sezione;
      }
    }
    return ret;
  }

  private void impostaStatoIscrizione(UtenteServiziRistorazione iscrizione) {
    WebMarkupContainer step1 = new WebMarkupContainer("step1");
    WebMarkupContainer step2 = new WebMarkupContainer("step2");
    WebMarkupContainer step3 = new WebMarkupContainer("step3");
    AttributeAppender attivo = new AttributeAppender("class", "active no-line");
    AttributeAppender confermato = new AttributeAppender("class", "confirmed no-line");

    if (iscrizione
        .getStatoIscrizioneServiziRistorazione()
        .equalsIgnoreCase(
            UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.ACCETTATA.value())) {
      step1.add(confermato);
      step2.add(confermato);
      step3.add(confermato);
    } else if (iscrizione
        .getStatoIscrizioneServiziRistorazione()
        .equalsIgnoreCase(
            UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.IN_ELABORAZIONE
                .value())) {
      step1.add(confermato);
      step2.add(attivo);
    }
    add(step1);
    add(step2);
    add(step3);
  }

  private boolean isIscritto(UtenteServiziRistorazione iscritto) {
    return (!StatoIscrizioneServiziRistorazioneEnum.NON_ISCRITTO
        .value()
        .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione()));
  }

  @SuppressWarnings("unused")
  private void verificaIscrizione(UtenteServiziRistorazione iscritto) {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      DatiIscrizioneServiziRistorazione iscrizione =
          instance
              .getApiRistorazionePortale()
              .getIscrizioneRistorazione(iscritto.getCodiceFiscale());

    } catch (BusinessException e) {
      error("Impossibile verificare iscrizione");
      log.error("Impossibile verificare iscrizione: ", e);
    } finally {
      instance.closeConnection();
    }
  }
}
