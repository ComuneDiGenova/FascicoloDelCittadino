package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.api.pojo.family.dto.CitizenResponse;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.FamilyResponse;
import it.liguriadigitale.ponmetro.api.presentation.rest.family.service.FamilyRestInterface;

public class ApiServiziDichiarazioneGenitoreImpl implements FamilyRestInterface {

  private FamilyRestInterface instance;

  public ApiServiziDichiarazioneGenitoreImpl(FamilyRestInterface instance) {
    super();
    this.instance = instance;
  }

  @Override
  public CitizenResponse getIdCittadino(Long arg0) {
    return instance.getIdCittadino(arg0);
  }

  @Override
  public FamilyResponse getListaFamigliari(Long arg0) {
    return instance.getListaFamigliari(arg0);
  }

  @Override
  public CitizenResponse salvaFamigliare(Long arg0, Long arg1, String arg2, Boolean arg3) {
    return instance.salvaFamigliare(arg0, arg1, arg2, arg3);
  }

  @Override
  public CitizenResponse updateBloccoAutodichiarazione(Long arg0, Long arg1, boolean arg2) {
    return instance.updateBloccoAutodichiarazione(arg0, arg1, arg2);
  }

  @Override
  public FamilyResponse getListaFamigliariNonResidenti(Long arg0) {
    return instance.getListaFamigliariNonResidenti(arg0);
  }

  @Override
  public CitizenResponse cancellazioneLogica(Long arg0, Long arg1) {
    return instance.cancellazioneLogica(arg0, arg1);
  }

  @Override
  public CitizenResponse cancellazioneLogicaMinoreResidente(Long arg0, Long arg1) {
    return instance.cancellazioneLogicaMinoreResidente(arg0, arg1);
  }

  @Override
  public CitizenResponse inserisciFamigliare(Long arg0, Long arg1, String arg2, Boolean arg3) {
    return instance.inserisciFamigliare(arg0, arg1, arg2, arg3);
  }
}
