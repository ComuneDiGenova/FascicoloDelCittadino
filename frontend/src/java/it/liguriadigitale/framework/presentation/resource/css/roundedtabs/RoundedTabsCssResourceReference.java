package it.liguriadigitale.framework.presentation.resource.css.roundedtabs;

import org.apache.wicket.request.resource.PackageResourceReference;

public class RoundedTabsCssResourceReference extends PackageResourceReference {

  private static final long serialVersionUID = -7066053290300340966L;

  private static final String nomeFileCss = "roundedTabs.css";

  public RoundedTabsCssResourceReference(final Class<?> scope) {
    super(scope, nomeFileCss);
  }

  private static final class Holder {
    private static final RoundedTabsCssResourceReference INSTANCE =
        new RoundedTabsCssResourceReference(RoundedTabsCssResourceReference.class);
  }

  /**
   * Normally you should not use this method, but use {@link
   * de.agilecoders.wicket.core.settings.IBootstrapSettings#getCssResourceReference()} ()} to
   * prevent version conflicts.
   *
   * @return the single instance of the resource reference
   */
  public static RoundedTabsCssResourceReference instance() {
    return Holder.INSTANCE;
  }
}
