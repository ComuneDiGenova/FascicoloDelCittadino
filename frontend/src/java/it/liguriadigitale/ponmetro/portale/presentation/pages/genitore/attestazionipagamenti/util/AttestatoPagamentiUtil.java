package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.attestazionipagamenti.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AttestazioniDiPagamento;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class AttestatoPagamentiUtil {

  private static Log log = LogFactory.getLog(AttestatoPagamentiUtil.class);

  @SuppressWarnings("rawtypes")
  public static ResourceLink downloadPdfImage(
      String wicketId, String nomeFile, byte[] file, String estensione, String mimeType) {

    final AbstractResource fileResourceByte;
    ResourceLink linkFile = null;

    if (LabelFdCUtil.checkIfNotNull(file)) {

      fileResourceByte =
          new AbstractResource() {

            private static final long serialVersionUID = -479633408555716778L;

            @Override
            protected ResourceResponse newResourceResponse(final Attributes attributes) {
              final ResourceResponse r = new ResourceResponse();
              try {

                if (estensione.equalsIgnoreCase("pdf")) {
                  r.setFileName(nomeFile);
                  r.setContentType(mimeType);
                  r.setContentDisposition(ContentDisposition.INLINE);
                  r.setContentLength(file.length);
                  r.setWriteCallback(
                      new WriteCallback() {
                        @Override
                        public void writeData(final Attributes attributes) {
                          attributes.getResponse().write(file);
                        }
                      });
                } else {
                  r.setFileName(nomeFile);
                  r.setContentType(mimeType);
                  r.setContentDisposition(ContentDisposition.INLINE);
                  r.setContentLength(file.length);
                  r.setWriteCallback(
                      new WriteCallback() {
                        @Override
                        public void writeData(final Attributes attributes) {
                          attributes.getResponse().write(file);
                        }
                      });
                }

                r.disableCaching();
              } catch (final Exception e) {
                log.error("Errore durante scarico pdf allegato");
              }

              return r;
            }
          };

      linkFile =
          new ResourceLink(wicketId, fileResourceByte) {

            private static final long serialVersionUID = -412312457249016414L;

            @Override
            protected void onComponentTag(final ComponentTag tag) {
              super.onComponentTag(tag);
              tag.put("target", "_blank");
              tag.put("title", "scarica FILE: " + nomeFile);
            }
          };
    }

    return linkFile;
  }

  public static Component getScaricaPdf(
      String idWicket, AttestazioniDiPagamento attestazioniDiPagamento)
      throws BusinessException, MagicMatchNotFoundException {

    return null;

    // TODO se non è nullo o vuoto id chiamo servizio get pdf e uso questo metodo
    // cosi

    //		String estensione = FileFdCUtil.getEstensionFileUploadAllegato(file);
    //		String mimeType = FileFdCUtil.getMimeTypeFileUploadAllegato(file);
    //
    //		String nomeFileConEstensione = "";
    //		if (LabelFdCUtil.checkIfNotNull(nomeFile)) {
    //			nomeFileConEstensione = nomeFile.concat(".").concat(estensione);
    //		}
    //
    //		ResourceLink<?> linkPdfImage = downloadPdfImage(idWicket, nomeFileConEstensione, file,
    // estensione, mimeType);
    //		boolean visibile = LabelFdCUtil.checkIfNotNull(file);
    //
    //		if (LabelFdCUtil.checkIfNotNull(linkPdfImage)) {
    //			linkPdfImage.setVisible(visibile);
    //			return linkPdfImage;
    //		} else {
    //			WebMarkupContainer btnWicketId = new WebMarkupContainer(idWicket);
    //			btnWicketId.setVisible(false);
    //
    //			return btnWicketId;
    //		}

  }
}
