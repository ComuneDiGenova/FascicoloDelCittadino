package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class BreadcrumbBasePanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 4234527383068466160L;
  private static final String BASE_PANEL_ID = "breadcrumbPanel";
  private static final String BASE_LABEL_KEY = "BreadcrumbBasePanel.";

  // private List<BreadcrumbBaseElement> breadcrumbElements;

  public BreadcrumbBasePanel(final String actualPage) {
    this(BASE_PANEL_ID, actualPage);
  }

  // public BreadcrumbBasePanel(List<BreadcrumbBaseElement>
  // breadcrumbElements) {
  // this(BASE_PANEL_ID, null, breadcrumbElements);
  // }

  public BreadcrumbBasePanel(String id, String actualPage) {
    super(id);
    initContent(actualPage);
  }

  protected void initContent(String actualPage) {
    fillDati(Breadcrumbs.getElementsFromLeaf(actualPage));
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  private String getStringContentPanel(BreadcrumbBaseElement elemento, Boolean isLast) {
    String toRet = "";
    toRet = "	<li class=\"breadcrumb-item";
    if (!isLast) {
      toRet += "\"><a href=\"" + elemento.getHrefToAndId() + "\">";
    } else {
      toRet += " active\" aria-current=\"page\">";
    }
    toRet += getString(BASE_LABEL_KEY + elemento.getIdKeyString());
    if (!isLast) {
      toRet += "</a><span class=\"separator\">/</span>";
    }
    toRet += "</li>\r\n";
    return toRet;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<BreadcrumbsElement> breadcrumbElements = (List<BreadcrumbsElement>) dati;
    String contentPanelString = "";
    if (breadcrumbElements != null && !breadcrumbElements.isEmpty()) {
      contentPanelString = "<ol class=\"breadcrumb\">";
      int iEsimo = 0;
      for (BreadcrumbsElement elemento : breadcrumbElements) {
        iEsimo++;
        contentPanelString +=
            getStringContentPanel(elemento.getElement(), iEsimo == breadcrumbElements.size());
      }
      contentPanelString += " </ol> ";
    }
    add(createContentPanel(contentPanelString));
    if (StringUtils.isBlank(contentPanelString)) {
      setVisible(false);
    }
  }
}
