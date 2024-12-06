package it.liguriadigitale.ponmetro.portale.presentation.components.behavior;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * * Versione Ajax del componente DownloadLink. Mediante il metodo onBeforeDownload è * possibile
 * intervernire prima che inizi il download. * * Ref.
 * https://github.com/magomi/wicket-download-sample * @author Santi Caltabiano
 */
public abstract class AjaxLinkDownload<T> extends AjaxLink<T> {
  /** */
  private static final long serialVersionUID = 9047851251104672366L;

  private final AbstractAjaxBehavior ajaxBehavior;
  private boolean antiCache;
  private boolean showInline;

  private IResourceStream resourceStream;
  private String nomeFile;

  /** * Crea un nuova istanza * @param id */
  public AjaxLinkDownload(String id) {
    super(id);
    this.antiCache = true;
    this.showInline = false;
    ajaxBehavior =
        new AbstractAjaxBehavior() {

          private static final long serialVersionUID = -8489244811539686639L;

          @Override
          public void onRequest() {
            ResourceStreamRequestHandler handler =
                new ResourceStreamRequestHandler(resourceStream, nomeFile);
            handler.setContentDisposition(
                showInline ? (ContentDisposition.INLINE) : (ContentDisposition.ATTACHMENT));
            getComponent().getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
          }
        };
    add(ajaxBehavior);
  }

  // avvia il download (chiamata da client)
  private void initiate(AjaxRequestTarget target) {
    String url = ajaxBehavior.getCallbackUrl().toString();
    if (isAntiCache()) {
      url = url + (url.contains("?") ? "&" : "?");
      url = url + "antiCache=" + System.currentTimeMillis();
    }
    String js = "setTimeout(\"window.location.href='" + url + "'\", 100);";
    // the timeout is needed to let Wicket release the channel
    target.appendJavaScript(js);
  }

  /**
   * @param target
   */
  @Override
  public void onClick(AjaxRequestTarget target) {
    if (onBeforeDownload(target)) {
      initiate(target);
    }
  }

  /**
   * Viene chiamato prima del download. Può essere utilizzato, ad esempio, per generare
   * dinamicamente il file da scaricare.
   *
   * <p>Il download ha inizio se restituisce true altrimenti no.
   *
   * @param target
   * @return
   */
  public abstract boolean onBeforeDownload(AjaxRequestTarget target);

  /**
   * Nome da assegnare al file durante il download
   *
   * @return
   */
  protected void setFileName(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  /** Hook method providing the actual resource stream. */
  protected void setResourceStream(IResourceStream stream) {
    this.resourceStream = stream;
  }

  /**
   * Elimina dalla cache (default true)
   *
   * @return the antiCache
   */
  public boolean isAntiCache() {
    return antiCache;
  }

  /**
   * Elimina dalla cache (default true)
   *
   * @param antiCache the antiCache to set
   */
  public void setAntiCache(boolean antiCache) {
    this.antiCache = antiCache;
  }

  /**
   * Dopo il download apre nel browser (default false)
   *
   * <p>* @return the showInline
   */
  public boolean isShowInline() {
    return showInline;
  }

  /**
   * * Dopo il download apre nel browser (default false)
   *
   * <p>* @param showInline the showInline to set
   */
  public void setShowInline(boolean showInline) {
    this.showInline = showInline;
  }
}
