package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.breadcrumb;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BreadcrumbBaseElement implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Log log = LogFactory.getLog(this.getClass());

  private String hrefToAndId;
  private String idKeyString;

  public BreadcrumbBaseElement(String hrefToAndId) {
    this.setIdKeyString(hrefToAndId);
    this.setHrefToAndId(hrefToAndId);
  }

  public String getHrefToAndId() {
    return hrefToAndId;
  }

  public void setHrefToAndId(String hrefToAndId) {
    this.hrefToAndId = hrefToAndId;
  }

  public String getIdKeyString() {
    return idKeyString;
  }

  public void setIdKeyString(String idKeyString) {
    this.idKeyString = idKeyString;
  }
}
