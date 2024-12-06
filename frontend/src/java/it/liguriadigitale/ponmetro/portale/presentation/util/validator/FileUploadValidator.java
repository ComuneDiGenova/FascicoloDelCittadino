package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class FileUploadValidator implements IValidator<List<FileUpload>> {

  private static final long serialVersionUID = 2723541218915050395L;

  private static Log log = LogFactory.getLog(FileUploadValidator.class);

  public FileUploadValidator() {
    super();
  }

  @Override
  public void validate(IValidatable<List<FileUpload>> validatable) {
    List<FileUpload> listaFile = validatable.getValue();

    String nomeFile = "";
    try {
      for (FileUpload file : listaFile) {
        nomeFile = file.getClientFileName();

        int punto = nomeFile.lastIndexOf(".");
        String subStringNome = nomeFile.substring(punto, nomeFile.length());

        if (subStringNome.equalsIgnoreCase(".tif") || subStringNome.equalsIgnoreCase(".tiff")) {
          error(validatable, "only-mimetype", nomeFile);
        } else {
          String mimetype = FileFdCUtil.getMimeTypeFileUploadAllegato(file.getBytes());
          boolean mimetypeCorretto =
              mimetype.equalsIgnoreCase("application/pdf")
                  || mimetype.substring(0, 5).equalsIgnoreCase("image");

          if (!mimetypeCorretto) {
            error(validatable, "only-mimetype", nomeFile);
          }

          if (file.getSize() > Bytes.kilobytes(2000).bytes()) {
            error(validatable, "maxSize-file", nomeFile);
          }
        }
      }
    } catch (BusinessException | MagicMatchNotFoundException e) {
      log.error("Errore durante validazione file allegato: " + e.getMessage());
      error(validatable, "only-estensioni", nomeFile);
    }
  }

  private void error(IValidatable<List<FileUpload>> validatable, String errorKey, String nomeFile) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    error.setVariable("nomeFile", nomeFile);
    validatable.error(error);
  }
}
