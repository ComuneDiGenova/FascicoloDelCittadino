package it.liguriadigitale.ponmetro.portale.presentation.components.resource;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.junit.platform.commons.util.StringUtils;

public class FascicoloPdfResources extends AbstractResource {

  private static final long serialVersionUID = 4674818532513599860L;
  private String nomeFile;
  private byte[] pdfBytes;

  public FascicoloPdfResources(String nomeFile, byte[] pdfBytes) {
    this.nomeFile = nomeFile;
    this.pdfBytes = pdfBytes;
  }

  @Override
  protected ResourceResponse newResourceResponse(final Attributes attributes) {
    final ResourceResponse r = new ResourceResponse();
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

  @SuppressWarnings("rawtypes")
  public ResourceLink getResourceLink(String idRisorsa) {
    return new ResourceLink(idRisorsa, this) {
      private static final long serialVersionUID = -4613703674361043929L;

      @Override
      protected void onComponentTag(final ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("target", "_blank");
        if (StringUtils.isBlank(nomeFile)) {
          tag.put("title", "scarica PDF");
        } else {
          tag.put("title", "scarica PDF: " + nomeFile);
        }
      }
    };
  }

  public String getNomeFile() {
    return nomeFile;
  }

  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  public byte[] getPdfBytes() {
    return pdfBytes;
  }

  public void setPdfBytes(byte[] pdfBytes) {
    this.pdfBytes = pdfBytes;
  }
}
