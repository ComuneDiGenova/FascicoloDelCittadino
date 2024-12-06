package it.liguriadigitale.ponmetro.api.business.entitaconfigurazioneutente.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioneChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.SottopagineDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import java.util.List;

public interface ServiziHomePageInterface {

  public String getValoreByChiave(String chiave);

  public List<FunzioneChiaveValore> getListaFunzioniInEvidenza(String chiave);

  public List<ChiaveValore> getListaFunzioniInRealizzazione(String chiave);

  public List<SottopagineDisponibili> getElencoSottoPagine();

  public List<CfgTCatFunzLink> getElencoLinkEsterni(VCfgTCatFunz vcfgtcatfunz)
      throws BusinessException;

  public List<CfgTCatFunzLink> getLinkEsterniPerIdFunz(String idFunz);
}
