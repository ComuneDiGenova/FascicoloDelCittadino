package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.documenti.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImuAllegato;
import it.liguriadigitale.ponmetro.portale.pojo.imu.TipoAllegatoEnum;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadDieteValidator;
import java.util.Arrays;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public abstract class CaricaAllegatoForm extends AbstracFrameworkForm<RimborsoImuAllegato> {

  private static final long serialVersionUID = 198965465425872L;

  WebMarkupContainer containerFile;
  WebMarkupContainer containerAltro;
  RimborsoImuAllegato allegato;

  FileUploadField fileAllegatoUpload;
  AjaxButton eliminaFile;
  FeedbackPanel feedbackPanel;

  public CaricaAllegatoForm(String id, RimborsoImuAllegato model) {
    super(id, model);
    addElementiForm();
  }

  public CaricaAllegatoForm(String id, RimborsoImuAllegato model, FeedbackPanel feedbackPanel) {
    super(id, model);
    // TODO Auto-generated constructor stub

    allegato = model;
    this.feedbackPanel = feedbackPanel;
    addElementiForm();
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub
    containerAltro = new WebMarkupContainer("containerAltro");
    containerAltro.setVisible(false);
    containerAltro.setOutputMarkupId(true);
    containerAltro.addOrReplace(
        new TextField<String>("altro", new PropertyModel<String>(allegato, "altro")));
    containerFile = new WebMarkupContainer("containerFile");
    containerFile.setOutputMarkupId(true);

    Label nomeFile = new Label("nomeFile", "");
    Label dimensioneFile = new Label("dimensioneFile", "");

    eliminaFile =
        new AjaxButton("eliminaFile") {

          private static final long serialVersionUID = 6421871465837323595L;

          @Override
          protected void onSubmit(AjaxRequestTarget targetElimina) {

            containerFile.setVisible(false);

            targetElimina.add(containerFile);
          }
          ;
        };
    eliminaFile.setDefaultFormProcessing(false);

    containerFile.addOrReplace(eliminaFile);

    containerFile.addOrReplace(nomeFile);
    containerFile.addOrReplace(dimensioneFile);
    containerFile.setVisible(false);

    fileAllegatoUpload = new FileUploadField("fileAllegatoUpload");
    fileAllegatoUpload.setLabel(Model.of("Upload Allegato"));
    fileAllegatoUpload.add(new FileUploadDieteValidator());
    fileAllegatoUpload.setOutputMarkupId(true);
    fileAllegatoUpload.setOutputMarkupPlaceholderTag(true);
    addOrReplace(fileAllegatoUpload);

    RadioChoice<TipoAllegatoEnum> tipoAllegato =
        new RadioChoice<TipoAllegatoEnum>(
            "tipoAllegato",
            new PropertyModel<TipoAllegatoEnum>(allegato, "tipoAllegato"),
            Arrays.asList(TipoAllegatoEnum.values()));

    tipoAllegato.setPrefix("<div class=\"form-check\">");
    tipoAllegato.setSuffix("</div>");
    tipoAllegato.setRequired(true);
    tipoAllegato.setOutputMarkupPlaceholderTag(true);
    tipoAllegato.setLabel(Model.of("Tipo allegato"));

    addOrReplace(tipoAllegato);
    addOrReplace(containerAltro);
    addOrReplace(containerFile);

    Button annulla =
        new Button("btnAnnulla") {
          private static final long serialVersionUID = 1L;

          @Override
          public void onSubmit() {
            log.debug("[Button Annulla] Cliccato Annulla");
            annulla();
          }
        };

    annulla.setDefaultFormProcessing(false);
    annulla.setLabel(Model.of("Annulla"));

    addOrReplace(annulla);
  }

  public abstract void annulla();

  @Override
  protected void onError() {
    // TODO Auto-generated method stub
    log.debug("[formUploadFile] Error");
  }
}
