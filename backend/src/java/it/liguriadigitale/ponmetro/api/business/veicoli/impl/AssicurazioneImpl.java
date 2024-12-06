package it.liguriadigitale.ponmetro.api.business.veicoli.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.api.business.veicoli.service.AssicurazioneInterface;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.dto.request.VeicoloDto;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.TipoVeicolo;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.WebApplicationException;
import javax.xml.ws.http.HTTPException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AssicurazioneImpl implements AssicurazioneInterface {

  private static Log log = LogFactory.getLog(AssicurazioneImpl.class);
  private static final String ERRORE_API_VEICOLI = "Errore di connessione alle API Veicolo";

  @Override
  public VerificaAssicurazioneVeicoli getAssicurazione(VeicoloDto veicolo)
      throws BusinessException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      String targa = veicolo.getTarga();

      TipoVeicolo tipoVeicolo = null;
      String tipoVeicoloValue = veicolo.getTipoVeicolo();
      if (tipoVeicoloValue.equalsIgnoreCase("AUTOVEICOLO")) {
        tipoVeicolo = TipoVeicolo.AUTOVEICOLO;
      } else if (tipoVeicoloValue.equalsIgnoreCase("MOTOVEICOLO")) {
        tipoVeicolo = TipoVeicolo.MOTOVEICOLO;
      } else if (tipoVeicoloValue.equalsIgnoreCase("RIMORCHIO")) {
        tipoVeicolo = TipoVeicolo.RIMORCHIO;
      } else if (tipoVeicoloValue.equalsIgnoreCase("CICLOMOTORE")) {
        tipoVeicolo = TipoVeicolo.CICLOMOTORE;
      }

      String pattern = "yyyy-MM-dd";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      String dataRiferimento = simpleDateFormat.format(new Date());

      VerificaAssicurazioneVeicoli verificaAssicurazioneVeicoli =
          new VerificaAssicurazioneVeicoli();
      verificaAssicurazioneVeicoli =
          instance
              .getAssicurazioneVeicoloApi()
              .verificaCoperturaAssicurativaDatiAnagraficiTargaCodiceTipoVeicoloDataRiferimentoGet(
                  targa, tipoVeicolo, dataRiferimento);
      return verificaAssicurazioneVeicoli;
    } catch (HTTPException e) {
      log.debug("1 Errore durante la chiamata delle API assicurazione ", e);
      throw new BusinessException(e);
    } catch (WebApplicationException e) {
      log.debug("2 Errore durante la chiamata delle API assicurazione webapplExc ", e);
      throw new BusinessException(e);
    } catch (RuntimeException e) {
      log.debug("3 Errore durante la chiamata delle API assicurazione webapplExc ", e);
      throw new BusinessException(e);
    } catch (BusinessException e) {
      log.error(
          "4 ServiziMieiMezziImpl -- getAssicurazione: errore API veicoli BusinessException :", e);
      throw new BusinessException(ERRORE_API_VEICOLI);
    } finally {
      instance.closeConnection();
    }
  }
}
