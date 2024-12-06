package it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoTeleriscaldamentoIren;
import it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.service.ServiziTeleriscaldamentoIren;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.TipoUtenzaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.EsitoVerificaEnum;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus.DatiVerificatiEnum;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonusResponse;
import java.io.IOException;
import java.util.Random;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiziTeleriscaldamentoIrenImpl implements ServiziTeleriscaldamentoIren {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_TELERISCALDAMENTO_IREN =
      "Errore di connessione alle API Teleriscaldamento IREN";

  @Override
  public FileRichiestaBonusResponse serviceRichiestaBonusPost(
      DatiDomandaTeleriscaldamento datiDomanda)
      throws BusinessException, ApiException, IOException {
    log.debug("CP serviceRichiestaBonusPost: ");

    try {
      FileRichiestaBonus fileRichiestaBonus = new FileRichiestaBonus();

      if (LabelFdCUtil.checkIfNotNull(datiDomanda)) {
        fileRichiestaBonus.setCapAmmCondominio(datiDomanda.getCapAmministratore());
        fileRichiestaBonus.setCellAmmCondominio(datiDomanda.getCellulareAmministratore());
        fileRichiestaBonus.setCellulare(datiDomanda.getCellulare());
        fileRichiestaBonus.setCfIntestatarioContratto(datiDomanda.getCfIntestatarioContratto());
        fileRichiestaBonus.setCfRichiedente(datiDomanda.getCodiceFiscale());
        fileRichiestaBonus.setCivicoAmmCondominio(datiDomanda.getNumeroCivico());
        fileRichiestaBonus.setCognomeRichiedente(datiDomanda.getCognome());
        fileRichiestaBonus.setComuneAmmCondominio(datiDomanda.getCognomeAmministratore());
        fileRichiestaBonus.setComuneFornitura(datiDomanda.getComune());
        fileRichiestaBonus.setConsensoInf(datiDomanda.getArt43());
        fileRichiestaBonus.setConsensoPrivacy(datiDomanda.getPrivacy());

        fileRichiestaBonus.setDataProtocollo(
            LocalDateUtil.getDataFormatoEuropeo(datiDomanda.getDataProtocollo()));

        it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento
                .DatiVerificatiEnum
            datiVerificati = datiDomanda.getDatiVerificati();
        String valoreDaviVerificati = datiVerificati.value();

        if ("SI".equalsIgnoreCase(valoreDaviVerificati)) {
          fileRichiestaBonus.setDatiVerificati(DatiVerificatiEnum.OK);

        } else {
          fileRichiestaBonus.setDatiVerificati(DatiVerificatiEnum.KO);
        }

        fileRichiestaBonus.setEmail(datiDomanda.getEmail());
        fileRichiestaBonus.setEmailAmmCondominio(datiDomanda.getEmailAmministratore());

        EsitoVerificaEnum esitoVerifica = datiDomanda.getEsitoVerifica();
        String esitoVerificaValue = esitoVerifica.value();
        if ("OK".equalsIgnoreCase(esitoVerificaValue)) {
          fileRichiestaBonus.setEsitoVerifica(
              it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus
                  .EsitoVerificaEnum.OK);
        } else {
          fileRichiestaBonus.setEsitoVerifica(
              it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus
                  .EsitoVerificaEnum.KO);
        }

        //				IndicatoreIsee12Enum indicatoreIsee12 = datiDomanda.getIndicatoreIsee12();
        //				String indicatoreIsee12Value = indicatoreIsee12.value();
        //				if ("SI".equalsIgnoreCase(indicatoreIsee12Value)) {
        //					fileRichiestaBonus.setIndicatoreIsee12(
        //
        //	it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus.IndicatoreIsee12Enum.SI);
        //				} else {
        //					fileRichiestaBonus.setIndicatoreIsee12(
        //
        //	it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus.IndicatoreIsee12Enum.FALSE);
        //				}
        //
        //				IndicatoreIsee20Enum indicatoreIsee20 = datiDomanda.getIndicatoreIsee20();
        //				String indicatoreIsee20Value = indicatoreIsee20.value();
        //				if ("SI".equalsIgnoreCase(indicatoreIsee20Value)) {
        //					fileRichiestaBonus.setIndicatoreIsee20(
        //
        //	it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus.IndicatoreIsee20Enum.SI);
        //				} else {
        //					fileRichiestaBonus.setIndicatoreIsee20(
        //
        //	it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus.IndicatoreIsee20Enum.FALSE);
        //				}

        it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento
                .IndicatoreIsee25Enum
            indicatoreIsee25 = datiDomanda.getIndicatoreIsee25();
        String indicatoreIsee25Value = indicatoreIsee25.value();
        if ("SI".equalsIgnoreCase(indicatoreIsee25Value)) {
          fileRichiestaBonus.setIndicatoreIsee25(
              it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus
                  .IndicatoreIsee25Enum.OK);
        } else {
          fileRichiestaBonus.setIndicatoreIsee25(
              it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonus
                  .IndicatoreIsee25Enum.KO);
        }

        fileRichiestaBonus.setNomeRichiedente(datiDomanda.getNome());
        fileRichiestaBonus.setNominativoAmmCond(datiDomanda.getNominativoAmministratore());
        fileRichiestaBonus.setNominativoApt(datiDomanda.getNominativoProprietarioAppartamento());
        fileRichiestaBonus.setNominativoContratto(datiDomanda.getNominativoIntestatarioContratto());
        fileRichiestaBonus.setNumCivicoFornitura(datiDomanda.getNumeroCivico());
        fileRichiestaBonus.setNumContratto(datiDomanda.getNumeroContratto());
        fileRichiestaBonus.setNumeroCliente(datiDomanda.getNumeroCliente());
        fileRichiestaBonus.setNumNucleoFamiliare(datiDomanda.getNumeroComponenti());
        fileRichiestaBonus.setNumProtocollo(datiDomanda.getNumeroProtocollo());
        fileRichiestaBonus.setpIvaContratto(datiDomanda.getpIvaIntestatarioContratto());
        fileRichiestaBonus.setProvAmmCondominio(datiDomanda.getProvinciaAmministratore());
        fileRichiestaBonus.setProvinciaFornitura(datiDomanda.getProvincia());
        fileRichiestaBonus.setTelAmmCondominio(datiDomanda.getTelefonoAmministratore());
        fileRichiestaBonus.setTelefono(datiDomanda.getTelefono());

        TipoUtenzaEnum tipoUtenza = datiDomanda.getTipoUtenza();
        String tipoUtenzaValue = tipoUtenza.getDescrizione();
        fileRichiestaBonus.setTipoUtenza(tipoUtenzaValue);

        fileRichiestaBonus.setViaAmmCondominio(datiDomanda.getViaAmministratore());
        fileRichiestaBonus.setViaFornitura(datiDomanda.getVia());
      }

      log.debug("CP prima di post a iren = " + fileRichiestaBonus);

      //			return ServiceLocatorLivelloUno.getInstance().getApiTeleriscaldamentoIren()
      //					.apiServiceRichiestaBonusPost(fileRichiestaBonus);

      return ServiceLocatorLivelloUnoTeleriscaldamentoIren.getInstance()
          .getApiTeleriscaldamentoIren()
          .apiServiceRichiestaBonusPost(fileRichiestaBonus);

    } catch (BusinessException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- serviceRichiestaBonusPost: errore API Teleriscaldamento:");
      throw new BusinessException(ERRORE_API_TELERISCALDAMENTO_IREN);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- serviceRichiestaBonusPost: errore durante la chiamata delle API Teleriscaldamento"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(),
          ERRORE_API_TELERISCALDAMENTO_IREN);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- serviceRichiestaBonusPost: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    }
  }

  @Override
  public FileRichiestaBonusResponse serviceRichiestaBonusPostEsitoOK(
      DatiDomandaTeleriscaldamento datiDomanda) {
    FileRichiestaBonusResponse returnOk = new FileRichiestaBonusResponse();

    returnOk.setEsito("OK");
    Random r = new Random();
    returnOk.setIdRichiesta(r.toString());
    return returnOk;
  }

  @Override
  public FileRichiestaBonusResponse serviceRichiestaBonusPostEsitoKO(
      DatiDomandaTeleriscaldamento datiDomanda) {
    FileRichiestaBonusResponse returnOk = new FileRichiestaBonusResponse();

    returnOk.setEsito("KO");
    Random r = new Random();
    returnOk.setIdRichiesta(r.toString());
    return returnOk;
  }
}
