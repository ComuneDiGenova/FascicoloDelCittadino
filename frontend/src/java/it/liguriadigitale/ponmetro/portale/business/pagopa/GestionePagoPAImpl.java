package it.liguriadigitale.ponmetro.portale.business.pagopa;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.pagopa.util.NodoPagamentiConverterUtilities;
import it.liguriadigitale.ponmetro.portale.business.pagopa.util.ParametriConfigurazionePeople;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.pojo.mip.builder.PaymentDataBuilder;
import it.liguriadigitale.ponmetro.portale.pojo.mip.builder.PaymentRequestBuilder;
import it.liguriadigitale.ponmetro.portale.pojo.mip.builder.ServiceDataBuilder;
import it.liguriadigitale.ponmetro.portale.pojo.mip.builder.UserDataBuilder;
import it.liguriadigitale.ponmetro.portale.pojo.mip.builder.UserDataExtBuilder;
import it.liguriadigitale.ponmetro.portale.pojo.mip.builder.UtenteLoginBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.util.JaxbMarshaller;
import it.liguriadigitale.riuso.mip.PaymentRequest;
import it.regulus.osa.secretcode.PayServerClient;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;
import javax.xml.bind.JAXBException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GestionePagoPAImpl implements GestionePagoPAInterface {

  public static final String URL_RICHIESTA_PAGAMENTO = "Request2RID.jsp";
  public static final int RESULT_SUCCESS = 0;
  public static final String PORTALE_ID = "FascCitt";
  public static final String CHIAVE = "CHIAVE";
  public static final String FUNZIONE = "PAGAMENTO_AVVISO";
  public static final String SIMBOLO = "MOS";
  // COSTANTI da prendere in file PROPERTIES
  // private static final String URI_SERVER_PAGAMENTO =
  // "http://vm-people20.comune.genova.it:8090/mip-pagamenti/pagamentoesterno/";
  // private static final String SERVER_URI =
  // "https://wfly12b.liguriadigitale.it:8080/portale/";

  private Log log = LogFactory.getLog(getClass());

  @Override
  public String preparaPagamento(MipData input) throws BusinessException {
    PayServerClient clientPeople;
    log.debug("MipData= " + input);
    clientPeople = new PayServerClient(CHIAVE, PayServerClient.PS2S_KT_CLEAR);
    clientPeople.ServerURL(BaseServiceImpl.URI_SERVER_PAGAMENTO + URL_RICHIESTA_PAGAMENTO);
    String xmlRichiestaPagamento = creaXmlRichiestaPagamento(input);

    int result = clientPeople.PS2S_PC_Request2RID(xmlRichiestaPagamento, SIMBOLO, new Date());
    try {
      if (result == RESULT_SUCCESS) {

        String xmlRichiesta = java.net.URLEncoder.encode(clientPeople.PS2S_NetBuffer(), "utf-8");
        log.debug("xmlRichiesta Encoded " + xmlRichiesta);
        return xmlRichiesta;
      } else {
        String messaggioErrore = getMessagioDiErrore(result);
        log.error("Errore invio messaggio XML risultato " + messaggioErrore);
        throw new BusinessException("Errore invio al client people risultato " + messaggioErrore);
        // TODO sostituire con pagina di errore che mostra messaggio:
        // throw new
        // RestartResponseAtInterceptPageException(ErrorPage.class);
      }
    } catch (RuntimeException e) {
      String messaggio = "Errore invio messaggio XML risultato " + e.getMessage();
      log.error(messaggio, e);
      throw new BusinessException(e);
    } catch (UnsupportedEncodingException e) {
      String messaggio = "Errore invio messaggio XML risultato " + e.getMessage();
      log.error(messaggio, e);
      throw new BusinessException(e);
    } finally {
      clientPeople.ServerURL(null);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private String creaXmlRichiestaPagamento(MipData input) throws BusinessException {

    PaymentRequest paymentRequest =
        PaymentRequestBuilder.getInstance()
            .setServiceData(
                ServiceDataBuilder.getInstance()
                    .setIdServizio(input.getIdServizio())
                    .setNumeroDocumento(input.getNumeroDocumento())
                    .build())
            .setFunzione(FUNZIONE)
            .setPortaleID(PORTALE_ID)
            .setUrlDiRitorno(BaseServiceImpl.URL_PAGOPA_MIP_OK)
            .setUrlDiErrore(BaseServiceImpl.URL_PAGOPA_MIP_KO)
            .setPaymentData(
                PaymentDataBuilder.getInstance()
                    .setImporto(
                        NodoPagamentiConverterUtilities.euroToEurocent(input.getImportoAvviso()))
                    .setValuta(ParametriConfigurazionePeople.VALUTA)
                    .setNumeroOperazione(UUID.randomUUID().toString())
                    .setCalcoloCommissioni(ParametriConfigurazionePeople.CALCOLO_COMMISSIONI)
                    .build())
            .setUserData(
                UserDataBuilder.getInstance().setEmailUtente(input.getUtente().getMail()).build())
            .setUserDataExt(
                UserDataExtBuilder.getInstance()
                    .setUtenteLogin(
                        UtenteLoginBuilder.getInstance()
                            .setCognome(input.getUtente().getCognome())
                            .setNome(input.getUtente().getNome())
                            .setIdentificativoUtente(input.getUtente().getLogin())
                            .setTipoIdentificativo(
                                ParametriConfigurazionePeople.TIPO_IDENTIFICATIVO)
                            .setTipoAutenticazione(
                                ParametriConfigurazionePeople.TIPO_AUTENTICAZIONE)
                            .build())
                    .build())
            .build();

    try {
      JaxbMarshaller<PaymentRequest> marshallerRequest =
          new JaxbMarshaller(paymentRequest.getClass());
      String marshalledPayementRequest = marshallerRequest.marshallRequest(paymentRequest);
      log.debug("request preparata: " + marshalledPayementRequest);
      marshalledPayementRequest = removeHeader(marshalledPayementRequest);
      return marshalledPayementRequest;
    } catch (JAXBException e) {
      log.error("Errore: ", e);
      throw new BusinessException("Impossibile preparare xml per oggetto BufferData");
    }
  }

  private String removeHeader(String marshalledPayementRequest) {
    String headerXml = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>";
    marshalledPayementRequest =
        marshalledPayementRequest.substring(headerXml.length(), marshalledPayementRequest.length());
    log.debug("marshalledPayementRequest=" + marshalledPayementRequest);
    return marshalledPayementRequest;
  }

  private String getMessagioDiErrore(int result) {
    String messaggioErrore = "";
    switch (result) {
      case PayServerClient.pPS2S_COMPERROR:
        messaggioErrore = "Fallita Inizializzazione Applicazione " + result;
        break;
      case PayServerClient.pPS2S_DATAERROR:
        messaggioErrore = "Impossibile estrarre buffer dati" + result;
        break;
      case PayServerClient.pPS2S_DATEERROR:
        messaggioErrore = "Data non accettabile" + result;
        break;
      case PayServerClient.pPS2S_HASHERROR:
        messaggioErrore = "Fallita Verifica Hash" + result;
        break;
      case PayServerClient.pPS2S_HASHNOTFOUND:
        messaggioErrore = "Impossibile estrarre buffer HASH" + result;
        break;
      case PayServerClient.pPS2S_CREATEHASHERROR:
        messaggioErrore = "Impossibile creare buffer HASH" + result;
        break;
      case PayServerClient.pPS2S_TIMEELAPSED:
        messaggioErrore = "Finestra temporale scaduta" + result;
        break;
      case PayServerClient.pPS2S_XMLERROR:
        messaggioErrore = "Documento XML non valido: " + result;
        break;
      default:
        messaggioErrore = "Errore non gestito: " + result;
        break;
    }
    return messaggioErrore;
  }
}
