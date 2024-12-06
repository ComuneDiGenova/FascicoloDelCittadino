package it.liguriadigitale.ponmetro.portale.presentation.common.paginazione;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.collections.MicroMap;

public class PagingNavigationFascicolo extends PagingNavigation {

  private static final long serialVersionUID = -5392139312659221220L;

  @SuppressWarnings("unused")
  private Log log = LogFactory.getLog(getClass());

  protected IPageable pageable;

  protected IPagingLabelProvider labelProvider;

  private long startIndex;

  private int viewSize = 200;

  public PagingNavigationFascicolo(final String id, final IPageable pageable) {
    this(id, pageable, null);
  }

  public PagingNavigationFascicolo(
      final String id, final IPageable pageable, final IPagingLabelProvider labelProvider) {

    super(id, pageable);

    this.pageable = pageable;
    this.labelProvider = labelProvider;
    startIndex = 0;
  }

  // @Override
  // protected void onConfigure() {
  // super.onConfigure();
  // setDefaultModel(new Model<Integer>(
  // (int) Math.max(Integer.MAX_VALUE, pageable.getPageCount())));
  //
  // setStartIndex();
  // }

  @SuppressWarnings("unused")
  private void setStartIndex() {
    long firstListItem = startIndex;

    int viewSize = (int) Math.min(getViewSize(), pageable.getPageCount());
    long margin = getMargin();

    long currentPage = pageable.getCurrentPage();

    if (currentPage < (firstListItem + margin)) {
      firstListItem = currentPage - margin;
    } else if ((currentPage >= (firstListItem + viewSize - margin))) {

      firstListItem = (currentPage + margin + 1) - viewSize;
    }

    if ((firstListItem + viewSize) >= pageable.getPageCount()) {
      firstListItem = pageable.getPageCount() - viewSize;
    }

    if (firstListItem < 0) {
      firstListItem = 0;
    }

    if ((viewSize != getIterations()) || (startIndex != firstListItem)) {
      modelChanging();

      addStateChange();
      startIndex = firstListItem;

      setIterations((int) Math.min(viewSize, pageable.getPageCount()));

      modelChanged();

      removeAll();
    }
  }

  private void setIterations(int i) {
    setDefaultModelObject(i);
  }

  @Override
  public int getViewSize() {
    return viewSize;
  }

  @SuppressWarnings("unused")
  @Override
  protected void populateItem(final LoopItem loopItem) {
    final long pageIndex = getStartIndex() + loopItem.getIndex();

    AttributeModifier attributeModifier = new AttributeModifier("aria-current", "page");

    final AbstractLink link = newPagingNavigationLink("pageLink", pageable, pageIndex);
    link.add(new FascicoloTitleAppenderCurrentPage(pageIndex));

    loopItem.add(link);

    String label = "";
    if (labelProvider != null) {
      label = labelProvider.getPageLabel(pageIndex);
    } else {
      label = String.valueOf(pageIndex + 1).intern();
    }
    link.add(new Label("pageNumber", label));
  }

  private final class FascicoloTitleAppenderCurrentPage extends Behavior {

    private static final long serialVersionUID = 3609367826028227297L;

    private static final String RES = "PaginazioneFascicoloPanel.page";
    private final long page;

    public FascicoloTitleAppenderCurrentPage(long page) {
      this.page = page;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {

      String pageIndex = String.valueOf(page + 1).intern();
      Map<String, String> vars = new MicroMap<String, String>("page", pageIndex);

      tag.put("title", getString(RES, Model.ofMap(vars)));
    }
  }

  protected String contaPagine(IPageable pageable) {
    long paginaCorrente = pageable.getCurrentPage() + 1L;
    long totalePagine = pageable.getPageCount();
    String descrizionePagine = paginaCorrente + " di " + totalePagine;
    return descrizionePagine;
  }
}
