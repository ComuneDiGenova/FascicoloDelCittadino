package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.EsitoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ImportoPagato;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class UtilVerbaliImportiPagati {

  private static Log log = LogFactory.getLog(UtilVerbaliImportiPagati.class);

  public static boolean verificaVisibilitaListView(List<ImportoPagato> listaImportiPagati) {
    boolean isVisiblelistaImportiPagati = false;

    if (UtilVerbali.checkIfNotNull(listaImportiPagati)) {
      isVisiblelistaImportiPagati = true;

      if (listaImportiPagati.isEmpty()) {
        isVisiblelistaImportiPagati = false;
      }
    }

    return isVisiblelistaImportiPagati;
  }

  public static ResourceLink downloadRicevutaMip(ImportoPagato importoPagato, Utente utente) {

    final AbstractResource pdfResourceByte;
    ResourceLink linkPdf = null;

    RicevutaPagamento ricevutaPagamento = getRicevutaMipDaCodiceAvviso(importoPagato, utente);

    if (UtilVerbali.checkIfNotNull(ricevutaPagamento.getPdfRicevuta())) {
      String nomeFile = ricevutaPagamento.getPdfRicevuta().getNomeFile();

      pdfResourceByte =
          new AbstractResource() {

            private static final long serialVersionUID = 6342710824568119374L;

            @Override
            protected ResourceResponse newResourceResponse(final Attributes attributes) {

              final ResourceResponse r = new ResourceResponse();

              final byte[] pdfBytes = ricevutaPagamento.getPdfRicevuta().getFile();
              String nomeFile = ricevutaPagamento.getPdfRicevuta().getNomeFile();

              r.setFileName(nomeFile);
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
            }
          };

      linkPdf =
          new ResourceLink("btnRicevutaMip", pdfResourceByte) {

            private static final long serialVersionUID = 3766438939598370925L;

            @Override
            protected void onComponentTag(final ComponentTag tag) {
              super.onComponentTag(tag);
              tag.put("target", "_blank");
              tag.put("title", "scarica FILE: " + nomeFile);
            }
          };
    }

    return linkPdf;
  }

  private static RicevutaPagamento getRicevutaMipDaCodiceAvviso(
      ImportoPagato importoPagato, Utente utente) {

    boolean esitoPositivo = true;
    RicevutaPagamento ricevutaPagamento = new RicevutaPagamento();
    if (importoPagato.getCodiceAvviso() != null && !importoPagato.getCodiceAvviso().isEmpty()) {
      try {
        List<RicevutaPagamento> listaRicevutePagamento =
            ServiceLocator.getInstance()
                .getServiziMipVerticali()
                .getRicevutaDaCodiceAvviso(utente, importoPagato.getCodiceAvviso(), esitoPositivo);
        ricevutaPagamento =
            listaRicevutePagamento.stream()
                .filter(
                    elem ->
                        elem.getEsitoPagamento()
                            .toString()
                            .equalsIgnoreCase(EsitoPagamento.OK.toString()))
                .findAny()
                .orElse(new RicevutaPagamento());

      } catch (BusinessException | ApiException e) {
        log.error("Errore get ricevuta verbali da Mip per codice avviso: " + e.getMessage());
      }
    }

    return ricevutaPagamento;
  }
}
