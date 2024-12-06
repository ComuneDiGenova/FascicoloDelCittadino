package it.liguriadigitale.ponmetro.portale.presentation.common.uploadFile;

import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.UploadPatente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.form.FascicoloUploadForm;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class FascicoloUploadFilePanel extends BasePanel {

  private static final long serialVersionUID = -8942036900979739172L;

  private FileUploadField fileUploadField;

  private UploadPatente uploadPatente;

  private FascicoloUploadForm fascicoloUploadForm = null;

  public FascicoloUploadFilePanel(String id) {
    super(id);
  }

  public FascicoloUploadFilePanel(String id, FileUploadField fileUploadField) {
    super(id);

    this.fileUploadField = fileUploadField;
  }

  @Override
  public void fillDati(Object dati) {
    uploadPatente = new UploadPatente();

    fascicoloUploadForm =
        new FascicoloUploadForm("formUpload", uploadPatente) {

          private static final long serialVersionUID = -2190326190229568943L;

          @Override
          protected void onSubmit() {
            super.onSubmit();
          }
        };
    add(fascicoloUploadForm);
  }

  public FileUploadField getUploadPatente() {
    return fileUploadField;
  }

  public void setUploadPatente(FileUploadField fileUploadField) {
    this.fileUploadField = fileUploadField;
  }
}
