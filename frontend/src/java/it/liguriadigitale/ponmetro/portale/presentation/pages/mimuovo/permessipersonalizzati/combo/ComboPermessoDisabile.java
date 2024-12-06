package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitVerificationResult;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.PermessoDisabileRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.model.IModel;

public class ComboPermessoDisabile extends FdCDropDownChoice<PermitVerificationResult> {

  private static final long serialVersionUID = -6765798125295641058L;

  protected Log log = LogFactory.getLog(this.getClass());

  String codiceFiscaleDisabile;

  public ComboPermessoDisabile(
      String id, IModel<PermitVerificationResult> model, String codiceFiscaleDisabile) {
    super(id);
    this.codiceFiscaleDisabile = codiceFiscaleDisabile;

    this.inizializza(codiceFiscaleDisabile);
    this.setChoiceRenderer(new PermessoDisabileRenderer());
    this.setModel(model);
  }

  public ComboPermessoDisabile(String id, String codiceFiscaleDisabile) {
    this(id, null, codiceFiscaleDisabile);
  }

  @SuppressWarnings("unchecked")
  public void inizializza(String codiceFiscaleDisabile) {

    if (getSession().getAttribute("listaPermessiDisabili") == null
        || ((List<PermitVerificationResult>) getSession().getAttribute("listaPermessiDisabili"))
            .isEmpty()) {
      List<PermitVerificationResult> listaPermessiDisabile = new ArrayList<>();

      if (codiceFiscaleDisabile != null) {
        ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
        try {
          listaPermessiDisabile =
              instance.getApiGenovaParcheggi().permitVerification(codiceFiscaleDisabile, "cude");

        } catch (RuntimeException e) {
          log.error(
              "ServiziGenovaParcheggiImpl -- getPermessiGePark: errore durante la chiamata delle API Genova Parcheggi ",
              e);
          throw new RestartResponseAtInterceptPageException(
              new ErroreServiziPage("Genova Parcheggi"));
        } catch (BusinessException e) {
          log.error(
              "ServiziGenovaParcheggiImpl -- getPermessiGePark: errore API Genova Parcheggi:", e);
        } finally {
          instance.closeConnection();
        }
      }

      List<PermitVerificationResult> sortedListaPermessiDisabile =
          listaPermessiDisabile.stream()
              .sorted(Comparator.comparing(PermitVerificationResult::getValidFrom).reversed())
              .collect(Collectors.toList());

      getSession()
          .setAttribute("listaPermessiDisabili", (Serializable) sortedListaPermessiDisabile);

      this.setChoices(sortedListaPermessiDisabile);
    } else
      this.setChoices(
          (List<PermitVerificationResult>) getSession().getAttribute("listaPermessiDisabili"));
  }
}
