package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.modal;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImuAllegato;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.StringResourceModel;

public class ModalEliminaFile extends Modal<RimborsoImuAllegato> {

  private static final long serialVersionUID = 16546465469874L;

  WebMarkupContainer dialog = (WebMarkupContainer) get("dialog");

  public ModalEliminaFile(String id) {

    super(id);
    // TODO Auto-generated constructor stub

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    NotEmptyLabel messaggio =
        new NotEmptyLabel("messaggio", new StringResourceModel("ModalEliminaFile.messaggio", this));

    setHeaderVisible(false);

    dialog.addOrReplace(messaggio);
  }

  public MarkupContainer myAdd(Component... children) {
    return dialog.addOrReplace(children);
  }
}
