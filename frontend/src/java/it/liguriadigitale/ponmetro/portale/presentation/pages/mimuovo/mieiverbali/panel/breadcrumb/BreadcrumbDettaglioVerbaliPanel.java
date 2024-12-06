package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.breadcrumb;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.MieiVerbaliPage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class BreadcrumbDettaglioVerbaliPanel extends BasePanel {

  private static final long serialVersionUID = 6262091361689120738L;

  public BreadcrumbDettaglioVerbaliPanel(String id) {
    super(id);

    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    add(creaLinkMieiVerbali());
  }

  private LaddaAjaxLink<Object> creaLinkMieiVerbali() {
    LaddaAjaxLink<Object> linkMieiVerbali =
        new LaddaAjaxLink<Object>("mieiVerbali", Type.Link) {

          private static final long serialVersionUID = -4008552307522128249L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new MieiVerbaliPage());
          }
        };

    String testoLink = getString("BreadcrumbDettaglioVerbaliPanel.mieiVerbali");
    linkMieiVerbali.setLabel(Model.of(testoLink));

    AttributeModifier attributeModifier = new AttributeModifier("class", "btn-link ladda-button");
    linkMieiVerbali.add(attributeModifier);

    linkMieiVerbali.setSpinnerColor("#0066cb");

    return linkMieiVerbali;
  }
}
