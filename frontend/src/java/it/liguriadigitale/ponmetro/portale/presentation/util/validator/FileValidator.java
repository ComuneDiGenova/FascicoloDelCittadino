package it.liguriadigitale.ponmetro.portale.presentation.util.validator;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class FileValidator implements IValidator<FileUploadField> {

  private static final long serialVersionUID = 2723541218915050395L;

  private static Log log = LogFactory.getLog(FileValidator.class);

  private static String pdf = "application/pdf";

  private static String image = "image/jpeg";

  @Override
  public void validate(IValidatable<FileUploadField> validatable) {
    FileUploadField file = validatable.getValue();

    try {
      if (!FileFdCUtil.getMimeTypeFileUploadAllegato(file.getFileUpload().getBytes())
              .equalsIgnoreCase(pdf)
          || FileFdCUtil.getMimeTypeFileUploadAllegato(file.getFileUpload().getBytes())
              .equalsIgnoreCase(image)) {
        error(validatable, "only-pdfImage");
      }
    } catch (BusinessException | MagicMatchNotFoundException e) {
      log.error("Errore durante mime type: " + e.getMessage());
    }
  }

  private void error(IValidatable<FileUploadField> validatable, String errorKey) {
    ValidationError error = new ValidationError();
    error.addKey(getClass().getSimpleName() + "." + errorKey);
    validatable.error(error);
  }
}
