package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget;

import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import org.apache.wicket.request.component.IRequestablePage;

public abstract class MyWidgetPanel extends BasePanel {

  private static final long serialVersionUID = 4059914202343639735L;

  protected abstract void mostraTestoWidget();

  protected abstract void mostraIcona();

  public MyWidgetPanel(POSIZIONE posizione) {
    super(posizione.wicketId);
    setRenderBodyOnly(true);
  }

  public enum POSIZIONE {
    TOP("widgetTop"),
    BOTTOM("widgetBottom");

    private String wicketId;

    private POSIZIONE(String wicketId) {
      this.wicketId = wicketId;
    }

    public String getWicketId() {
      return wicketId;
    }
  }

  @Override
  public void fillDati(Object dati) {

    mostraIcona();
    mostraTestoWidget();
  }

  protected Class getComponentPage(String className) {

    try {
      log.debug("getComponentPage: INIZIO");
      Class<IRequestablePage> clazz =
          (Class<IRequestablePage>) PageClassFinder.findClassByNameFunction(className);
      return clazz;
    } catch (SecurityException e) {
      log.debug("getComponentPage:", e);
    }
    log.debug("addComponenteWidget: ritorna EmptyPanel");
    return Page404.class;
  }
}
