package it.liguriadigitale.ponmetro.portale.business.domandealloggio.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.bandirealgim.model.BandoAttivoFullDOMValidationResult;
import it.liguriadigitale.ponmetro.bandirealgim.model.BandoDomandaCategoriaFullDOM;
import it.liguriadigitale.ponmetro.bandirealgim.model.BooleanValidationResult;
import it.liguriadigitale.ponmetro.bandirealgim.model.V1BandoDomandeStatusDOM;
import it.liguriadigitale.ponmetro.bandirealgim.model.V1BandoDomandeViewFullDOM;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

public interface ServiziDomandeAlloggioService {

  BooleanValidationResult checkAnnouncementStatus(String codBando)
      throws BusinessException, ApiException, IOException;

  BandoAttivoFullDOMValidationResult getActiveAnnouncement(
      OffsetDateTime data, String codTipologiaBando)
      throws BusinessException, ApiException, IOException;

  V1BandoDomandeViewFullDOM getAnnouncementQuestionDetail(
      Boolean flagExternal, String codiceFiscale, String codBando)
      throws BusinessException, ApiException, IOException;

  V1BandoDomandeStatusDOM getAnnouncementQuestionStatus(
      Boolean flagExternal, String codiceFiscale, String codBando, String codTipologiaBando)
      throws BusinessException, ApiException, IOException;

  List<BandoDomandaCategoriaFullDOM> getListAnnouncementQuestionCategory(Integer anno)
      throws BusinessException, ApiException, IOException;

  String getValoreDaDb(String chiave);

  byte[] getDownloadDocumentAnnouncementQuestion(String codiceFiscale, String idBandoDomande)
      throws BusinessException, ApiException, IOException;
}
