package it.liguriadigitale.ponmetro.api.business.famiglia.service;

import it.liguriadigitale.ponmetro.api.pojo.family.dto.CitizenResponse;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.FamilyResponse;

public interface GestioneNucleoFamigliareInterface {

  public CitizenResponse getIdFCittadinoByIdPerson(Long idPerson);

  public FamilyResponse getListaFamigliari(Long idFCitt);

  public FamilyResponse getListaFamigliariNonResidenti(Long idFCitt);

  public CitizenResponse salvaFamigliare(
      Long idFCitt, Long idPerson, String idCatRelatives, Boolean isAutocertificazione);

  public CitizenResponse inserisciFamigliare(
      Long idFCitt, Long idPerson, String idCatRelatives, Boolean isAutocertificazione);

  public CitizenResponse updateBloccoAutodichiarazione(
      Long idPersonMinore, Long idPerson, Boolean isBloccoAutocertificazione);

  public CitizenResponse cancellaFamigliare(Long idPersonMinore, Long idPerson);

  public CitizenResponse cancellaFamigliareResidente(Long idPersonMinore, Long idPerson);
}
