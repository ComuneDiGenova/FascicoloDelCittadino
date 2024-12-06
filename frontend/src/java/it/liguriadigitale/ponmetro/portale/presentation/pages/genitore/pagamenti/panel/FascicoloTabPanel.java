package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class FascicoloTabPanel<T extends ITab> extends AjaxTabbedPanel<T> {

  private static final long serialVersionUID = 1L;

  public FascicoloTabPanel(final String id, final List<T> tabs) {
    super(id, tabs);
  }

  public FascicoloTabPanel(final String id, final List<T> tabs, final IModel<Integer> model) {
    super(id, tabs, model);
  }

  @Override
  protected String getSelectedTabCssClass() {
    return " nav-link nav-active";
  }

  @Override
  protected String getTabContainerCssClass() {
    return "nav nav-tabs nav-tabs-cards";
  }

  @Override
  protected Component newTitle(String titleId, IModel<?> titleModel, int index) {
    Label label = new Label(titleId, titleModel);
    String inRegola = " nav-in-regola";
    String nonInRegola = " nav-non-regola";
    String css = "";
    if (titleModel.getObject().toString().contains("In regola")) {
      css = inRegola;
    } else if (titleModel.getObject().toString().contains("Non in regola")) {
      css = nonInRegola;
    }
    AttributeAppender appender = new AttributeAppender("class", css);
    label.add(appender);
    return label;
  }

  @Override
  protected WebMarkupContainer newLink(final String linkId, final int index) {
    WebMarkupContainer link = super.newLink(linkId, index);
    ITab tabCorrente = getTabs().get(index);
    IModel<?> titleModel = tabCorrente.getTitle();
    String inRegola = " nav-in-regola";
    String nonInRegola = " nav-non-regola";
    String css = "";
    if (titleModel.getObject().toString().contains("In regola")) {
      css = inRegola;
    } else if (titleModel.getObject().toString().contains("Non in regola")) {
      css = nonInRegola;
    }
    AttributeAppender appender = new AttributeAppender("class", css);
    link.add(appender);
    return link;
  }
}
