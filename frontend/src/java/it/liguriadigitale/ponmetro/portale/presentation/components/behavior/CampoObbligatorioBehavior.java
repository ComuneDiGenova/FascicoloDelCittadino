package it.liguriadigitale.ponmetro.portale.presentation.components.behavior;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.ObbligatorioCampoImporto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.Response;

public class CampoObbligatorioBehavior extends Behavior {

  private static final long serialVersionUID = -4452074846596107579L;
  protected Log log = LogFactory.getLog(this.getClass());

  @Override
  public void beforeRender(Component component) {
    Response response = component.getResponse();
    StringBuffer asterisktHtml = new StringBuffer(200);

    if ((component instanceof FormComponent) && ((FormComponent<?>) component).isRequired())
      if (component instanceof ObbligatorioCampoImporto)
        if (!((ObbligatorioCampoImporto) component).isAsteriskRendered()) {
          asterisktHtml.append("<span class='required'></span>");
          ((ObbligatorioCampoImporto) component).setAsteriskRendered(true);
        }

    response.write(asterisktHtml);
  }
}
