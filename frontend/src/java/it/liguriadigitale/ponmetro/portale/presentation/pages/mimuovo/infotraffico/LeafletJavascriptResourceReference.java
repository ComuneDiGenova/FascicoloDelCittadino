package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.infotraffico;

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class LeafletJavascriptResourceReference extends WebjarsJavaScriptResourceReference {

  private static final long serialVersionUID = 8706198116320919812L;

  private static final ResourceReference INSTANCE = new LeafletJavascriptResourceReference();

  /** Private constructor to create singleton instance */
  private LeafletJavascriptResourceReference() {
    super("webjars/leaflet/1.6.0/dist/leaflet.js");
  }

  /**
   * @return singleton instance of resource.
   */
  public static ResourceReference instance() {
    return INSTANCE;
  }
}
