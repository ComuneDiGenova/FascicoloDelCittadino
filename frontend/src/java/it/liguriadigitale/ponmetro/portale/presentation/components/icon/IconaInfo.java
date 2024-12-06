package it.liguriadigitale.ponmetro.portale.presentation.components.icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.panel.Panel;

public class IconaInfo extends Panel {

  private static final long serialVersionUID = -1770635176570003711L;

  protected final Log log = LogFactory.getLog(getClass());

  public IconaInfo(String id) {
    super(id);
    log.debug("inserita icon info");
  }
}
