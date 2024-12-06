package it.liguriadigitale.ponmetro.api.business.configurazione.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.configurazione.model.ImagineCaricata;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public interface ConfigurazioneInterface {

  public ImagineCaricata getAvatar(BigDecimal idAnagrafica) throws BusinessException;

  public ImagineCaricata uploadAvatar(BigDecimal idAnagrafica, File imageAvatar)
      throws BusinessException;

  public List<FunzioniDisponibili> getListaFunzioni() throws BusinessException;

  public Boolean isAccessoAutorizzato(String codiceFiscale) throws BusinessException;
}
