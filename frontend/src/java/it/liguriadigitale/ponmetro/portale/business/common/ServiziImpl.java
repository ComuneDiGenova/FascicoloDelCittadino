package it.liguriadigitale.ponmetro.portale.business.common;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziImpl {
  @SuppressWarnings("unused")
  private static final int DIRECT_STACK_TRACE_INDEX = 0;

  @SuppressWarnings("unused")
  private static final int DIRECT_FIRST_CODE_STACK_INDEX = 1;

  private static final int METHOD_FIRST_CODE_STACK_INDEX = 2;
  private Log log = LogFactory.getLog(getClass());

  protected String getMethodName() {
    return getMethodName(METHOD_FIRST_CODE_STACK_INDEX);
  }

  protected String getMethodName(int index) {
    return Thread.currentThread().getStackTrace()[index].getMethodName();
  }

  protected <T> void manageException(
      T e,
      String exceptionMessage,
      String errorMessage,
      String methodName,
      String className,
      Class<? extends Page> classErrorPage)
      throws ApiException, BusinessException, RestartResponseAtInterceptPageException {
    if (e instanceof BusinessException) {
      log.debug("Errore 1");
      throw new BusinessException(errorMessage);
    } else if (e instanceof ServiceUnavailableException) {
      log.debug("Errore 2");
      log.error(
          className
              + " -- "
              + methodName
              + ": errore ServiceUnavailableException:"
              + exceptionMessage);
      throw new ApiException(
          Response.serverError().status(500).tag(exceptionMessage).build(), errorMessage);
    } else if (e instanceof ResponseProcessingException || e instanceof WebApplicationException) {
      log.debug("Errore 3");
      log.error(
          className
              + " -- "
              + methodName
              + " errore ResponseProcessingException ! WebApplicationException:"
              + exceptionMessage);
      throw new ApiException(
          Response.serverError().status(400).tag(exceptionMessage).build(), exceptionMessage);
    } else if (e instanceof RuntimeException) {
      log.error(
          className
              + " -- "
              + methodName
              + ": errore"
              + e.getClass().getName()
              + ":"
              + exceptionMessage);
      throw new RestartResponseAtInterceptPageException(classErrorPage);
    } else {
      log.error("Errore Innested: " + className + " - " + methodName + " - " + errorMessage);
      throw new BusinessException(errorMessage);
    }
  }
}
