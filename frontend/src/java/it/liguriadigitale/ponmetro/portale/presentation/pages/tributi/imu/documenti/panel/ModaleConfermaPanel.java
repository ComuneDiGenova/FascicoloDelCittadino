package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public abstract class ModaleConfermaPanel extends Panel {

  private static final long serialVersionUID = 1L;

  protected Log log = LogFactory.getLog(this.getClass());

  public ModaleConfermaPanel(String markupId, String message, final ModalWindow modalWindow) {
    super(markupId);
    // TODO Auto-generated constructor stub

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    Form<?> yesNoForm = new Form("yesOrNo");

    modalWindow.setTitle(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ModaleConfermaPanel.titolo", ModaleConfermaPanel.this)));

    yesNoForm.addOrReplace(creaBtnSi());
    yesNoForm.addOrReplace(creaBtnNo());
    yesNoForm.addOrReplace(new Label("message", message));

    addOrReplace(yesNoForm);
  }

  private LaddaAjaxLink<Object> creaBtnSi() {
    LaddaAjaxLink<Object> btnSi =
        new LaddaAjaxLink<Object>("btnSi", Type.Primary) {

          private static final long serialVersionUID = -1939768425829411648L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            conferma();
          }
        };

    btnSi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ModaleConfermaPanel.si", ModaleConfermaPanel.this)));

    return btnSi;
  }

  private LaddaAjaxLink<Object> creaBtnNo() {
    LaddaAjaxLink<Object> btnNo =
        new LaddaAjaxLink<Object>("btnNo", Type.Primary) {

          private static final long serialVersionUID = -1939768425829411648L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            annulla();
          }
        };

    btnNo.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ModaleConfermaPanel.no", ModaleConfermaPanel.this)));

    return btnNo;
  }

  public abstract void annulla();

  public abstract void conferma();
}
