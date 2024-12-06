package it.liguriadigitale.ponmetro.api.presentation.rest.family;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.CitizenResponse;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.FamilyResponse;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.api.presentation.rest.family.service.FamilyRestInterface;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FamilyResource implements FamilyRestInterface {

  private static Log log = LogFactory.getLog(FamilyResource.class);

  @Override
  public CitizenResponse getIdCittadino(Long idPerson) {
    log.debug("getIdCittadino=" + idPerson);
    try {
      return ServiceLocator.getInstance().getFamily().getIdFCittadinoByIdPerson(idPerson);
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      throw new BadRequestException("Impossibile recuperare id");
    }
  }

  @Override
  public FamilyResponse getListaFamigliari(Long idFCitt) {
    log.debug("getListaFamigliari -- idFCitt=" + idFCitt);
    try {
      return ServiceLocator.getInstance().getFamily().getListaFamigliari(idFCitt);
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      throw new BadRequestException("Impossibile recuperare lista");
    }
  }

  @Override
  public CitizenResponse salvaFamigliare(
      Long idPersonMinore, Long idFCitt, String idCatRelatives, Boolean isAutocertificazione) {
    log.debug("salvaFamigliare=" + idPersonMinore);
    try {
      return ServiceLocator.getInstance()
          .getFamily()
          .salvaFamigliare(idPersonMinore, idFCitt, idCatRelatives, isAutocertificazione);
    } catch (BusinessException e) {
      log.error("Errore: " + e.getMessage());
      throw new BadRequestException("Impossibile salvare");
    }
  }

  @Override
  public CitizenResponse updateBloccoAutodichiarazione(
      Long idPersonMinore, Long idFCitt, boolean isBloccoAutocertificazione) {
    log.debug("salvaFamigliare=" + idPersonMinore);
    log.debug("isBloccoAutocertificazione=" + isBloccoAutocertificazione);
    try {
      return ServiceLocator.getInstance()
          .getFamily()
          .updateBloccoAutodichiarazione(idPersonMinore, idFCitt, isBloccoAutocertificazione);
    } catch (BusinessException e) {
      log.error("Errore: " + e.getMessage());
      throw new BadRequestException("Impossibile salvare");
    }
  }

  @Override
  public FamilyResponse getListaFamigliariNonResidenti(Long idFCitt) {
    log.debug("getListaFamigliariNonResidenti -- idFCitt=" + idFCitt);
    try {
      return ServiceLocator.getInstance().getFamily().getListaFamigliariNonResidenti(idFCitt);
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      throw new BadRequestException("Impossibile recuperare lista");
    }
  }

  @Override
  public CitizenResponse cancellazioneLogica(Long idPersonMinore, Long idFCitt) {
    log.debug("cancellazioneLogica -- idFCitt=" + idFCitt);
    try {
      return ServiceLocator.getInstance().getFamily().cancellaFamigliare(idPersonMinore, idFCitt);
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      throw new BadRequestException("Impossibile cancellazione logica");
    }
  }

  @Override
  public CitizenResponse cancellazioneLogicaMinoreResidente(Long idPersonMinore, Long idFCitt) {
    log.debug("cancellazioneLogicaMinoreResidente -- idFCitt=" + idFCitt);
    try {
      return ServiceLocator.getInstance()
          .getFamily()
          .cancellaFamigliareResidente(idPersonMinore, idFCitt);
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      throw new BadRequestException("Impossibile cancellazione o update");
    }
  }

  @Override
  public CitizenResponse inserisciFamigliare(
      Long idPersonMinore, Long idFCitt, String idCatRelatives, Boolean isAutocertificazione) {

    log.debug("inserisciFamigliare=" + idPersonMinore);
    try {
      return ServiceLocator.getInstance()
          .getFamily()
          .inserisciFamigliare(idPersonMinore, idFCitt, idCatRelatives, isAutocertificazione);
    } catch (BusinessException e) {
      log.error("Errore: " + e.getMessage());
      throw new BadRequestException("Impossibile salvare");
    }
  }
}
