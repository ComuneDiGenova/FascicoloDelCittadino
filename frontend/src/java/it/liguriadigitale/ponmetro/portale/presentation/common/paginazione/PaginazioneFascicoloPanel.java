package it.liguriadigitale.ponmetro.portale.presentation.common.paginazione;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

public class PaginazioneFascicoloPanel extends PagingNavigator {

  private static final long serialVersionUID = 6134044729875954094L;

  private PagingNavigationFascicolo pagingNavigationFascicolo;

  private final IPageable pageable;

  private final IPagingLabelProvider labelProvider;

  public PaginazioneFascicoloPanel(final String id, final IPageable pageable) {
    this(id, pageable, null);
  }

  public PaginazioneFascicoloPanel(
      final String id, final IPageable pageable, IPagingLabelProvider labelProvider) {
    super(id, pageable);
    this.pageable = pageable;
    this.labelProvider = labelProvider;
  }

  @Override
  protected void onInitialize() {
    super.onInitialize();

    pagingNavigationFascicolo = newNavigationFascicolo("navigation", pageable, labelProvider);
    addOrReplace(pagingNavigationFascicolo);

    addOrReplace(
        newPagingNavigationLink("first", pageable, 0)
            .add(new FascicoloTitleAppender("PaginazioneFascicoloPanel.first")));

    addOrReplace(
        newPagingNavigationIncrementLink("prev", pageable, -1)
            .add(new FascicoloTitleAppender("PaginazioneFascicoloPanel.prev")));

    addOrReplace(
        newPagingNavigationIncrementLink("next", pageable, 1)
            .add(new FascicoloTitleAppender("PaginazioneFascicoloPanel.next")));

    addOrReplace(
        newPagingNavigationLink("last", pageable, -1)
            .add(new FascicoloTitleAppender("PaginazioneFascicoloPanel.last")));
  }

  protected PagingNavigationFascicolo newNavigationFascicolo(
      final String id, final IPageable pageable, final IPagingLabelProvider labelProvider) {

    return new PagingNavigationFascicolo(id, pageable, labelProvider);
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
}
