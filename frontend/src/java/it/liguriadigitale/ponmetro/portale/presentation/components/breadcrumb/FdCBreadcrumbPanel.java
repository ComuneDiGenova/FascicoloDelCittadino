package it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.junit.platform.commons.util.StringUtils;

public class FdCBreadcrumbPanel<T extends Component> extends BasePanel {

  private static final long serialVersionUID = -8110320826632943272L;

  public FdCBreadcrumbPanel(String id, List<BreadcrumbFdC> lista) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(lista);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<BreadcrumbFdC> listaBreadcrumb = (List<BreadcrumbFdC>) dati;

    ListView<BreadcrumbFdC> listView =
        new ListView<BreadcrumbFdC>("listaBreadcrumb", listaBreadcrumb) {

          private static final long serialVersionUID = 4021804258907832623L;

          @Override
          protected void populateItem(ListItem<BreadcrumbFdC> item) {

            final BreadcrumbFdC breadcrumb = item.getModelObject();

            Label itemBreacrumb = new Label("itemBreacrumb", breadcrumb.getDescrizione());
            Label testo = new Label("testo", breadcrumb.getDescrizione());

            WebMarkupContainer separator = new WebMarkupContainer("separator");

            String pathRelativoNecessario = "";
            if (item.getIndex() > 1 && StringUtils.isNotBlank(breadcrumb.getContextPath())) {
              pathRelativoNecessario = breadcrumb.getContextPath();
            }

            AttributeAppender href =
                new AttributeAppender("href", pathRelativoNecessario + breadcrumb.getMountedPath());
            AttributeModifier active = new AttributeAppender("class", " active");
            AttributeAppender current = new AttributeAppender("aria-current", "page");

            if (item.getIndex() != listaBreadcrumb.size() - 1) {
              if (isUtenteDelegato() && item.getIndex() < 2) {
                itemBreacrumb.setVisible(false);
              } else {
                itemBreacrumb.add(href);
                testo.setVisible(false);
              }
            } else {
              itemBreacrumb.setVisible(false);
              separator.setVisible(false);
              item.add(active);
              item.add(current);
            }
            log.debug(
                "breadcrumb.getMountedPath()="
                    + pathRelativoNecessario
                    + breadcrumb.getMountedPath());
            log.debug("item.getIndex()=" + item.getIndex());

            item.addOrReplace(testo);
            item.addOrReplace(separator);
            item.addOrReplace(itemBreacrumb);
          }
        };
    addOrReplace(listView);
  }

  private boolean isUtenteDelegato() {
    return getUtente().isUtenteDelegato();
  }
}
