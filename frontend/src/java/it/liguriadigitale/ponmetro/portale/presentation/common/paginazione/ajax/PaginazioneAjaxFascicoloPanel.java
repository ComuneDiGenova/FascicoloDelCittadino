package it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;

public class PaginazioneAjaxFascicoloPanel extends AjaxPagingNavigator {

  private static final long serialVersionUID = 5454069837694278948L;

  private final IPageable pageable;

  private Component componentToRender;

  public PaginazioneAjaxFascicoloPanel(final String id, final IPageable pageable) {
    super(id, pageable);
    this.pageable = pageable;
    componentToRender = null;
  }

  @Override
  protected void onAjaxEvent(AjaxRequestTarget arg0) {
    if (componentToRender != null) {
      arg0.add(componentToRender);
    } else {
      super.onAjaxEvent(arg0);
    }
  }

  @Override
  protected void onInitialize() {
    super.onInitialize();

    addOrReplace(
        newPagingNavigationLink("first", pageable, 0)
            .add(new FascicoloTitleAppender("PaginazioneAjaxFascicoloPanel.first")));

    addOrReplace(
        newPagingNavigationIncrementLink("prev", pageable, -1)
            .add(new FascicoloTitleAppender("PaginazioneAjaxFascicoloPanel.prev")));

    Loop loopingOnPages =
        new Loop("navigation", (int) pageable.getPageCount()) {

          private static final long serialVersionUID = -3905093567721153852L;

          @Override
          protected void populateItem(LoopItem item) {
            final int pageIndeX0B = item.getIndex();
            final int pageLabel = pageIndeX0B + 1;

            final AbstractLink link = newPagingNavigationLink("paginaLink", pageable, pageIndeX0B);
            link.add(
                new Behavior() {

                  private static final long serialVersionUID = 3788953806641884267L;

                  @Override
                  public void onComponentTag(Component component, ComponentTag tag) {
                    tag.put("title", pageLabel);
                  }
                });

            link.add(new Label("paginaNumero", pageLabel));
            item.add(link);
          }
        };
    loopingOnPages.setVisible(pageable != null && pageable.getPageCount() != 0);
    addOrReplace(loopingOnPages);

    addOrReplace(
        newPagingNavigationIncrementLink("next", pageable, 1)
            .add(new FascicoloTitleAppender("PaginazioneAjaxFascicoloPanel.next")));

    addOrReplace(
        newPagingNavigationLink("last", pageable, -1)
            .add(new FascicoloTitleAppender("PaginazioneAjaxFascicoloPanel.last")));
  }

  private final class FascicoloTitleAppender extends Behavior {
    private static final long serialVersionUID = 1L;

    private final String resourceKey;

    public FascicoloTitleAppender(String resourceKey) {
      this.resourceKey = resourceKey;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
      tag.put("title", getString(resourceKey));
    }
  }

  public void setComponentToRender(Component componentToRender) {
    this.componentToRender = componentToRender;
  }
}
