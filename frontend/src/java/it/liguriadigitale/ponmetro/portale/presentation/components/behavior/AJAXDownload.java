package it.liguriadigitale.ponmetro.portale.presentation.components.behavior;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.IResourceStream;

public abstract class AJAXDownload extends AbstractAjaxBehavior {

  private static final long serialVersionUID = 8116020276120845786L;

  private final boolean addAntiCache;

  private IResourceStream internalResourceStream;

  public AJAXDownload() {
    this(true);
  }

  public AJAXDownload(final boolean addAntiCache) {
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
    final ResourceStreamRequestHandler handler =
        new ResourceStreamRequestHandler(getResourceStream(), getFileName());
    handler.setContentDisposition(ContentDisposition.ATTACHMENT);
    getComponent().getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
  }

  /**
   * Implement this method for a file name which will let the browser prompt with a save/open
   * dialog.
   *
   * @see ResourceStreamRequestTarget#getFileName()
   */
  protected abstract String getFileName();

  /** Hook method providing the actual resource stream. */
  protected abstract IResourceStream getResourceStream();

  /**
   * @param resourceStream the resourceStream to set
   */
  public IResourceStream getInternalResourceStream() {
    return this.internalResourceStream;
  }

  /**
   * @param resourceStream the resourceStream to set
   */
  public void setResourceStream(IResourceStream resourceStream) {
    this.internalResourceStream = resourceStream;
  }
}
