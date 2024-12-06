package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.breadcrumb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;

public class BreadcrumbsElement implements Serializable {

  private static final long serialVersionUID = 1L;
  private BreadcrumbsElement parent;
  private List<BreadcrumbsElement> childs;
  private BreadcrumbBaseElement element;

  private Class<? extends Page> pageClass;

  public BreadcrumbsElement(String id, Class<? extends Page> pageClass) {
    this(id, null, pageClass);
  }

  public BreadcrumbsElement(String id, BreadcrumbsElement parent, Class<? extends Page> pageClass) {
    element = new BreadcrumbBaseElement(id);
    this.parent = parent;
    this.setPageClass(pageClass);
    childs = null;
  }

  public BreadcrumbsElement getParent() {
    return parent;
  }

  public void setParent(BreadcrumbsElement parent) {
    this.parent = parent;
  }

  public BreadcrumbBaseElement getElement() {
    return element;
  }

  public void setElement(BreadcrumbBaseElement element) {
    this.element = element;
  }

  public void addChild(BreadcrumbsElement child) {
    if (childs == null) {
      childs = new ArrayList<BreadcrumbsElement>();
    }
    childs.add(child);
  }

  public Class<? extends Page> getPageClass() {
    return pageClass;
  }

  public void setPageClass(Class<? extends Page> pageClass) {
    this.pageClass = pageClass;
  }
}
