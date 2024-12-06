package it.liguriadigitale.ponmetro.portale.business.demografico.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.Relative;
import it.liguriadigitale.ponmetro.demografico.model.DatiCatastali;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteTari;
import it.liguriadigitale.ponmetro.demografico.model.StrutturaGenitori;
import it.liguriadigitale.ponmetro.demografico.model.TesseraElettorale;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import java.util.List;

public interface DemograficoInterface {

  public List<ComponenteNucleo> getFigli(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public Residente getDatiResidente(Utente utente) throws BusinessException, ApiException;

  public Residente getDatiResidente(String codiceFiscale) throws BusinessException, ApiException;

  public List<ComponenteNucleo> getFigliMinorenni(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<ComponenteNucleo> getFigliMinori16anni(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<ComponenteNucleo> getFigliSchedaAnagrafica(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<MinoreConvivente> getMinoriDaSchedaAnagrafica(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public DatiCatastali getDatiCatastali(Utente capoFamiglia) throws BusinessException, ApiException;

  public boolean testGenitoreDiFigli(String codiceFiscale) throws BusinessException, ApiException;

  public TesseraElettorale getDatiTesseraElettorale(String codiceFiscale)
      throws BusinessException, ApiException;

  public List<ComponenteNucleo> getNucleoFamiliareAllargato(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<ComponenteNucleo> getNucleoFamiliare(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<MinoreConvivente> getFigliMieiDati(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<MinoreConvivente> getFigliPerAutodichiarazione(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<MinoreConvivente> getFigliPerScolastici(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<MinoreConvivente> getFigliPerIscrizioneRefezione(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public MinoreConvivente popolaMinoreConvivente(ComponenteNucleo cn);

  public Residente getDatiResidenteDaSessione(String cf, Utente capofamiglia);

  public List<ComponenteNucleo> getFigliNonPerAutodichiarazione(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<Relative> getMinoriDaAutodichiarazione(Utente capoFamiglia) throws BusinessException;

  public StrutturaGenitori getGenitoriDaCfBambino(String cfBambino)
      throws BusinessException, ApiException;

  public List<ResidenteTari> getTuttiConviventiTuttiNuclei(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<Residente> listaPersoneInAnagrafeDelDemografico(Utente utente)
      throws BusinessException, ApiException;
}
