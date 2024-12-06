package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.form;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.UploadPatente;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class FascicoloUploadForm extends AbstracFrameworkForm<UploadPatente> {

  private static final long serialVersionUID = 2025833823732541553L;

  private FileUploadField uploadPatente;

  public FascicoloUploadForm(String id, UploadPatente model) {
    super(id, model);
  }

  @Override
  public void addElementiForm() {
    WebMarkupContainer containerFileUpload = new WebMarkupContainer("containerFileUpload");
    add(containerFileUpload);
    containerFileUpload.setVisible(false);
    containerFileUpload.setOutputMarkupId(true);

    uploadPatente = new FileUploadField("uploadPatente");

    uploadPatente.add(
        new AjaxFormSubmitBehavior("change") {

          private static final long serialVersionUID = -6220606607955272728L;

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {
            containerFileUpload.setVisible(true);

            Label nomeFile =
                new Label("nomeFile", uploadPatente.getFileUpload().getClientFileName());
            containerFileUpload.addOrReplace(nomeFile);

            AjaxLink<Void> btnEliminaFile =
                new AjaxLink<Void>("eliminaFile") {

                  private static final long serialVersionUID = 393955288875949450L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {
                    uploadPatente.clearInput();
                  }
                };
            containerFileUpload.addOrReplace(btnEliminaFile);

            target.add(FascicoloUploadForm.this);
          }
        });

    add(uploadPatente);
  }
}
