package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LayoutNoFeedBackPage extends LayoutBasePage {

  private static final long serialVersionUID = 501959409363942804L;

  public LayoutNoFeedBackPage() {
    super();
  }

  public LayoutNoFeedBackPage(PageParameters parameters) {
    super(parameters);
  }

  /*
   * Datepicker (non-Javadoc)
   *
   * inizializzazione datepicker di Bootstrap Italia vedi:
   * https://italia.github.io/bootstrap-italia/docs/form/input-calendario/
   *
   * @see
   * it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.
   * LayoutBasePage#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
   */
  @Override
  public void renderHead(IHeaderResponse response) {

    super.renderHead(response);
  }

  @Override
  protected void createFeedBackPanel() {
    NotificationPanel feedback =
        new NotificationPanel("feedback") {

          private static final long serialVersionUID = -883302032153540620L;

          /* PANEL senza X per la chiusura */

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.add(feedback);
  }
}
