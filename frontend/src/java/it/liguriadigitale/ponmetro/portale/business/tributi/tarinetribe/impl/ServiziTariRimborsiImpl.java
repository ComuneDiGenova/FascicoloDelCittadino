package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoTariNetribe;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper.DatiRichiestaRimborsoTariNetribeMapper;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service.ServiziTariRimborsiService;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribeResult;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsi;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsoResult;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.modelmapper.ModelMapper;

public class ServiziTariRimborsiImpl implements ServiziTariRimborsiService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_TARI = "Errore di connessione alle API Rimborso TARI";

  @Override
  public List<TARIRimborsi> getRimborsiTARIAnnoCorrente(String codiceFiscale)
      throws ApiException, BusinessException {
    ServiceLocatorLivelloUnoTariNetribe instance =
        ServiceLocatorLivelloUnoTariNetribe.getInstance();

    try {
      List<TARIRimborsi> listaRimborsi =
          instance.getApiTariRimborso().getRimborsiTARIAnnoCorrente(codiceFiscale);

      // Ordinamento per data creazione istanza
      //			listaRimborsi.sort(new Comparator<TARIRimborsi>() {
      //				@Override
      //				public int compare(TARIRimborsi m1, TARIRimborsi m2) {
      //					return (m2.getDatiRichiedenteRimborso().getDataIstanza()
      //							.compareTo(m1.getDatiRichiedenteRimborso().getDataIstanza()));
      //				}
      //			});

      listaRimborsi.sort(
          new Comparator<TARIRimborsi>() {
            @Override
            public int compare(TARIRimborsi m1, TARIRimborsi m2) {
              return (m2.getDatiRichiedenteRimborso()
                  .getId()
                  .compareTo(m1.getDatiRichiedenteRimborso().getId()));
            }
          });

      return listaRimborsi;

    } catch (BusinessException e) {
      log.error(
          "ServiziTariRimborsiImpl -- getRimborsiTARIAnnoCorrente: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariRimborsiImpl -- getRimborsiTARIAnnoCorrente: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariRimborsiImpl -- getRimborsiTARIAnnoCorrente: errore durante la chiamata delle API TARI Netribe ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Rimborsi TARI"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public DatiRichiestaRimborsoTariNetribeResult richiestaRimborsoTari(
      DatiRichiestaRimborsoTariNetribe datiRimborso) throws ApiException, BusinessException {
    ServiceLocatorLivelloUnoTariNetribe instance = new ServiceLocatorLivelloUnoTariNetribe();
    instance.getInstance(true);

    DatiRichiestaRimborsoTariNetribeResult esitoRichiesta =
        new DatiRichiestaRimborsoTariNetribeResult();

    try {

      ModelMapper mapper = new ModelMapper();
      mapper.addMappings(new DatiRichiestaRimborsoTariNetribeMapper());

      TARIRimborsi bodyRimborso = mapper.map(datiRimborso, TARIRimborsi.class);

      log.debug("richiestaRimborsoTari datiRimborso = " + datiRimborso);

      log.debug("richiestaRimborsoTari bodyRimborso = " + bodyRimborso);

      List<TARIRimborsoResult> risultatiRimbrsi =
          instance.getApiTariRimborso().createRichiestaRimborso(bodyRimborso);

      if (LabelFdCUtil.checkIfNotNull(risultatiRimbrsi)) {
        esitoRichiesta.setEsitoOk(true);
        esitoRichiesta.setRisultatiRimborsi(risultatiRimbrsi);
      }

    } catch (BusinessException e) {
      log.error("ServiziTariRimborsiImpl -- richiestaRimborsoTari: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariRimborsiImpl -- richiestaRimborsoTari: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariRimborsiImpl -- richiestaRimborsoTari: errore durante la chiamata delle API TARI Netribe ",
          e);

      String erroreRicevuto = e.getMessage();
      String[] splitted = erroreRicevuto.split(":");
      String messaggioErrore = splitted[splitted.length - 1];

      byte[] messaggioErroreBytes = messaggioErrore.getBytes();

      String utf8EncodedMessaggioErroreBytes =
          new String(messaggioErroreBytes, StandardCharsets.UTF_8);

      esitoRichiesta.setEsitoOk(false);
      esitoRichiesta.setMessaggioKO(utf8EncodedMessaggioErroreBytes);

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    return esitoRichiesta;
  }

  @Override
  public List<ModalitaPagamentoEnum> getListaModalitaPagamento() {
    return Arrays.asList(ModalitaPagamentoEnum.values());
  }

  @Override
  public byte[] getHelpRimborsiPDF(Utente utente, String codiceHelp, Long idHelp)
      throws BusinessException {
    log.debug("ServiziTariRimborsiImpl getHelRimborsiPDF: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(codiceHelp, utente.getIdAnonimoComuneGenova(), idHelp);
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF HELP rimborsi tari: ", e);
      throw new BusinessException("Impossibile recuperare PDF HELP rimborsi tari");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
