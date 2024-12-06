package it.liguriadigitale.ponmetro.portale.presentation.components.download;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.IResourceStream;

public abstract class BottoneAJAXDownload extends AbstractAjaxBehavior {

  private static final long serialVersionUID = 8116020276120845786L;

  protected Log log = LogFactory.getLog(this.getClass());

  private final boolean addAntiCache;

  private IResourceStream internalResourceStream;

  private Component pannello;

  public BottoneAJAXDownload(Component pannello) {
    this(true);
    this.pannello = pannello;
  }

  public BottoneAJAXDownload(final boolean addAntiCache) {
    super();
    this.addAntiCache = addAntiCache;
  }

  /** Call this method to initiate the download. */
  public void initiate(final AjaxRequestTarget target) {
    String url = getCallbackUrl().toString();

    if (addAntiCache) {
      url = url + (url.contains("?") ? "&" : "?");
      url = url + "antiCache=" + System.currentTimeMillis();
    }

    // the timeout is needed to let Wicket release the channel
    target.appendJavaScript("setTimeout(\"window.location.href='" + url + "'\", 100);");
  }

  @Override
  public void onRequest() {
    IResourceStream resourceStream = getResourceStream();
    if (resourceStream != null) {
      final ResourceStreamRequestHandler handler =
          new ResourceStreamRequestHandler(resourceStream, getFileName());
      handler.setContentDisposition(ContentDisposition.ATTACHMENT);
      getComponent().getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
      pannello.info("download iniziato");
    } else {
      log.debug("FEEDBACK!");
      pannello.error("Nessun download");
    }
  }

  /**
   * Implement this method for a file name which will let the browser prompt with a save/open
   * dialog.
   *
   * @see ResourceStreamRequestTarget#getFileName()
   */
  protected abstract String getFileName();

  public abstract void setFilename(String filename);

  protected abstract String getMessaggioErrore();

  public abstract void setMessaggioErrore(String messaggioErrore);

  public abstract void setPdfBytes(byte[] pdfBytes);

  /** Hook method providing the actual resource stream. */
  protected abstract IResourceStream getResourceStream();

  public abstract boolean onBeforeDownload(AjaxRequestTarget target);

  /**
   * @param resourceStream the resourceStream to set
   */
  public IResourceStream getInternalResourceStream() {
    return internalResourceStream;
  }

  /**
   * @param resourceStream the resourceStream to set
   */
  public void setResourceStream(IResourceStream resourceStream) {
    internalResourceStream = resourceStream;
  }
}
