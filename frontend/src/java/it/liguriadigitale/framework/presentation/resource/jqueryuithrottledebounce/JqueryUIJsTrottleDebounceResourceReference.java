package it.liguriadigitale.framework.presentation.resource.jqueryuithrottledebounce;

import org.apache.wicket.request.resource.PackageResourceReference;

public class JqueryUIJsTrottleDebounceResourceReference extends PackageResourceReference {

  private static final long serialVersionUID = -7066053290300340966L;

  private static final String nomeFileJs = "jquery.ba-throttle-debounce.min.js";

  public JqueryUIJsTrottleDebounceResourceReference(final Class<?> scope) {
    super(scope, nomeFileJs);
  }

  private static final class Holder {
    private static final JqueryUIJsTrottleDebounceResourceReference INSTANCE =
        new JqueryUIJsTrottleDebounceResourceReference(
            JqueryUIJsTrottleDebounceResourceReference.class);
  }

  /**
   * Normally you should not use this method, but use {@link
   * de.agilecoders.wicket.core.settings.IBootstrapSettings#getCssResourceReference()} ()} to
   * prevent version conflicts.
   *
   * @return the single instance of the resource reference
   */
  public static JqueryUIJsTrottleDebounceResourceReference instance() {
    return Holder.INSTANCE;
  }
}
