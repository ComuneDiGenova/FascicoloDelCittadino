package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel.breadcrumb;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.PagamentiPage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class BreadcrumbPagamentiFormPanel extends BasePanel {

  private static final long serialVersionUID = 672923768792010105L;

  public BreadcrumbPagamentiFormPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    add(creaLinkPagamenti());
  }

  private LaddaAjaxLink<Object> creaLinkPagamenti() {
    LaddaAjaxLink<Object> linkPagamenti =
        new LaddaAjaxLink<Object>("pagamenti", Type.Link) {

          private static final long serialVersionUID = -2179861447998551861L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new PagamentiPage());
          }
        };

    String testoLink = getString("BreadcrumbPagamentiFormPanel.pagamenti");
    linkPagamenti.setLabel(Model.of(testoLink));

    AttributeModifier attributeModifier = new AttributeModifier("class", "btn-link ladda-button");
    linkPagamenti.add(attributeModifier);

    linkPagamenti.setSpinnerColor("#0066cb");

    return linkPagamenti;
  }
}
