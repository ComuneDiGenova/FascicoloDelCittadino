package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.breadcrumb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Page;

public class Breadcrumbs implements Serializable {

  private static final long serialVersionUID = 1L;

  private static Map<String, BreadcrumbsElement> mapBreadcrumbs;

  static {
    mapBreadcrumbs = new HashMap<String, BreadcrumbsElement>();
  }

  public static void addElement(String id, String idParent, Class<? extends Page> pageClass) {
    BreadcrumbsElement element = new BreadcrumbsElement(id, pageClass);

    if (idParent != null) {
      // search Parent
      mapBreadcrumbs.get(idParent).addChild(element);
      element.setParent(mapBreadcrumbs.get(idParent));
    }
    mapBreadcrumbs.put(element.getElement().getIdKeyString(), element);
  }

  public static List<BreadcrumbsElement> getElementsFromLeaf(String idLeaf) {
    List<BreadcrumbsElement> elements = new ArrayList<BreadcrumbsElement>();

    BreadcrumbsElement element = mapBreadcrumbs.get(idLeaf);
    while (element != null) {
      elements.add(element);
      element = element.getParent();
    }
    // invert list perche' parto dalla leaf e aggiungo i parent, per cui
    // home
    Collections.reverse(elements); // lista vuota? verificare
    // risulta essere ultimo elemento
    return elements;
  }
}
