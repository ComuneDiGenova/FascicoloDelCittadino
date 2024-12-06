package it.liguriadigitale.ponmetro.portale.presentation.components.icon;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;

public class LdIconType extends IconType {

  private static final long serialVersionUID = -1770635176570003711L;

  public LdIconType(String cssClassName) {
    super(cssClassName);
  }

  @Override
  public String cssClassName() {
    return getCssClassName();
  }
}
