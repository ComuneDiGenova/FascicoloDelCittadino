package it.liguriadigitale.framework.presentation.resource.emptyjs;

import org.apache.wicket.request.resource.PackageResourceReference;

public class EmptyJsResourceReference extends PackageResourceReference {

  private static final long serialVersionUID = -7066053290300340966L;

  private static final String nomeFileJs = "empty.js";

  public EmptyJsResourceReference(final Class<?> scope) {
    super(scope, nomeFileJs);
  }

  private static final class Holder {
    private static final EmptyJsResourceReference INSTANCE =
        new EmptyJsResourceReference(EmptyJsResourceReference.class);
  }

  /**
   * Normally you should not use this method, but use {@link
   * de.agilecoders.wicket.core.settings.IBootstrapSettings#getCssResourceReference()} ()} to
   * prevent version conflicts.
   *
   * @return the single instance of the resource reference
   */
  public static EmptyJsResourceReference instance() {
    return Holder.INSTANCE;
  }
}
