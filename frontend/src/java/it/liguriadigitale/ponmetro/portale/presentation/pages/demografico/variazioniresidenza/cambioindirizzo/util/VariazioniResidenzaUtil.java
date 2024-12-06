package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegati;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegatiImmigrazione;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Cittadinanza;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Comune;
import it.liguriadigitale.ponmetro.servizianagrafici.model.DocumentoPratica;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDocumentiPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Professione;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Stato;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class VariazioniResidenzaUtil {

  private static Log log = LogFactory.getLog(VariazioniResidenzaUtil.class);

  private static final String ICON_CAMBIO = "color-yellow col-3 icon-cambio-indirizzo";
  private static final String ICON_RICHIESTA = "color-yellow col-3 icon-cambio-residenza";
  private static final String ICON_VARIAZIONI = "color-yellow col-3 icon-passeggiate";

  private static final String ICON_UOMO = "color-yellow col-3 icon-uomo";
  private static final String ICON_DONNA = "color-yellow col-3 icon-donna";
  private static final String ICON_BIMBA = "color-yellow col-3 icon-bimba";
  private static final String ICON_BIMBO = "color-yellow col-3 icon-bimbo";
  private static final String ICON_OMINO = "color-yellow col-3 icon-omino";

  public static List<DocumentoPratica> getDocumentiCaricati(Integer idPratica) {
    try {
      boolean attivo = true;
      List<DocumentoPratica> listaDocumentiCaricati = new ArrayList<DocumentoPratica>();
      List<DocumentoPratica> listaDocumentiCaricatiOrdinatiPerData =
          new ArrayList<DocumentoPratica>();
      if (LabelFdCUtil.checkIfNotNull(idPratica)) {
        GetDocumentiPraticaResponseGenericResponse risposta =
            ServiceLocator.getInstance()
                .getServiziAnagrafici()
                .getDocumentiPratica(idPratica, attivo);
        if (LabelFdCUtil.checkIfNotNull(risposta)
            && LabelFdCUtil.checkIfNotNull(risposta.getResult())) {
          listaDocumentiCaricati = risposta.getResult().getDocumenti();

          listaDocumentiCaricatiOrdinatiPerData =
              listaDocumentiCaricati.stream()
                  .sorted(Comparator.comparing(DocumentoPratica::getIdDocumento).reversed())
                  .collect(Collectors.toList());
        }
      }
      return listaDocumentiCaricatiOrdinatiPerData;
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante get dichiarazione precompilata:" + e.getMessage());
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  @SuppressWarnings("rawtypes")
  public static ResourceLink downloadPdfImage(
      String wicketId, String nomeFile, byte[] file, String estensione, String mimeType) {

    final AbstractResource fileResourceByte;
    ResourceLink linkFile = null;

    if (LabelFdCUtil.checkIfNotNull(file)) {

      fileResourceByte =
          new AbstractResource() {

            private static final long serialVersionUID = -479633408555716778L;

            @Override
            protected ResourceResponse newResourceResponse(final Attributes attributes) {
              final ResourceResponse r = new ResourceResponse();
              try {

                if (estensione.equalsIgnoreCase("pdf")) {
                  r.setFileName(nomeFile);
                  r.setContentType(mimeType);
                  r.setContentDisposition(ContentDisposition.INLINE);
                  r.setContentLength(file.length);
                  r.setWriteCallback(
                      new WriteCallback() {
                        @Override
                        public void writeData(final Attributes attributes) {
                          attributes.getResponse().write(file);
                        }
                      });
                } else {
                  r.setFileName(nomeFile);
                  r.setContentType(mimeType);
                  r.setContentDisposition(ContentDisposition.INLINE);
                  r.setContentLength(file.length);
                  r.setWriteCallback(
                      new WriteCallback() {
                        @Override
                        public void writeData(final Attributes attributes) {
                          attributes.getResponse().write(file);
                        }
                      });
                }

                r.disableCaching();
              } catch (final Exception e) {
                log.error("Errore durante scarico pdf allegato");
              }

              return r;
            }
          };

      linkFile =
          new ResourceLink(wicketId, fileResourceByte) {

            private static final long serialVersionUID = -412312457249016414L;

            @Override
            protected void onComponentTag(final ComponentTag tag) {
              super.onComponentTag(tag);
              tag.put("target", "_blank");
              tag.put("title", "scarica FILE: " + nomeFile);
            }
          };
    }

    return linkFile;
  }

  public static Component createLinkDocumentoCaricato(String idWicket, String nomeFile, byte[] file)
      throws BusinessException, MagicMatchNotFoundException {

    String estensione = FileFdCUtil.getEstensionFileUploadAllegato(file);
    String mimeType = FileFdCUtil.getMimeTypeFileUploadAllegato(file);

    String nomeFileConEstensione = "";
    if (LabelFdCUtil.checkIfNotNull(nomeFile)) {
      nomeFileConEstensione = nomeFile.concat(".").concat(estensione);
    }

    ResourceLink<?> linkPdfImage =
        downloadPdfImage(idWicket, nomeFileConEstensione, file, estensione, mimeType);
    boolean visibile = LabelFdCUtil.checkIfNotNull(file);

    if (LabelFdCUtil.checkIfNotNull(linkPdfImage)) {
      linkPdfImage.setVisible(visibile);
      return linkPdfImage;
    } else {
      WebMarkupContainer btnWicketId = new WebMarkupContainer(idWicket);
      btnWicketId.setVisible(false);

      return btnWicketId;
    }
  }

  public static AttributeAppender getCssIconaVariazione(String idTipo) {
    String css = "";

    if (LabelFdCUtil.checkIfNotNull(idTipo) && idTipo.equals("1")) {
      css = ICON_RICHIESTA;
    } else if (LabelFdCUtil.checkIfNotNull(idTipo)
        && (idTipo.equalsIgnoreCase("3") || idTipo.equalsIgnoreCase("4"))) {
      css = ICON_CAMBIO;
    } else {
      css = ICON_VARIAZIONI;
    }

    return new AttributeAppender("class", " " + css);
  }

  public static AttributeAppender getCssIconaIndividuo(IndividuiCollegati individuo) {
    String css = "";

    if (LabelFdCUtil.checkIfNotNull(individuo) && PageUtil.isStringValid(individuo.getGenere())) {
      if (individuo.getGenere().equalsIgnoreCase("F")) {
        if (LocalDateUtil.isMaggiorenne(individuo.getDataNascita())) {
          css = ICON_DONNA;
        } else {
          css = ICON_BIMBA;
        }
      } else if (individuo.getGenere().equalsIgnoreCase("M")) {
        if (LocalDateUtil.isMaggiorenne(individuo.getDataNascita())) {
          css = ICON_UOMO;
        } else {
          css = ICON_BIMBO;
        }
      }
    } else {
      css = ICON_OMINO;
    }

    return new AttributeAppender("class", " " + css);
  }

  public static AttributeAppender getCssIconaIndividuoImmi(
      IndividuiCollegatiImmigrazione individuo) {
    String css = ICON_OMINO;

    if (LabelFdCUtil.checkIfNotNull(individuo) && PageUtil.isStringValid(individuo.getSesso())) {
      if (individuo.getSesso().equalsIgnoreCase("Femmina")) {
        if (LocalDateUtil.isMaggiorenne(individuo.getDataNascita())) {
          css = ICON_DONNA;
        } else {
          css = ICON_BIMBA;
        }
      } else if (individuo.getSesso().equalsIgnoreCase("Maschio")) {
        if (LocalDateUtil.isMaggiorenne(individuo.getDataNascita())) {
          css = ICON_UOMO;
        } else {
          css = ICON_BIMBO;
        }
      }
    }

    return new AttributeAppender("class", " " + css);
  }

  public static boolean checkTipologiaFamigliaInFaseDiCreazione(
      VariazioniResidenza variazioniResidenza) {
    boolean isFamigliaInFaseDiCreazione = false;
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipologiaFamiglia())
        && variazioniResidenza.getComboTipologiaFamiglia().getCodice().equalsIgnoreCase("2")) {
      isFamigliaInFaseDiCreazione = true;
    }
    return isFamigliaInFaseDiCreazione;
  }

  public static boolean checkNumeroPratica(VariazioniResidenza variazioniResidenza) {
    boolean isNumeroPratica = true;
    if (checkTipologiaFamigliaInFaseDiCreazione(variazioniResidenza)) {
      if (PageUtil.isStringValid(variazioniResidenza.getNumeroPratica())) {
        isNumeroPratica = true;
      } else {
        isNumeroPratica = false;
      }
    }
    return isNumeroPratica;
  }

  public static boolean checkVisibilitaBottoniDettagli(VariazioniResidenza variazioniResidenza) {
    boolean isVisible = false;

    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getStato())
        && variazioniResidenza.getStato().equalsIgnoreCase("9999")
        && (variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("1")
            || variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("3")
            || variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("4"))) {
      isVisible = true;
    }

    return isVisible;
  }

  public static boolean checkComune(String comune)
      throws BusinessException, ApiException, IOException {
    boolean comuneOk = true;
    String info = "Non esiste una via corrispondente ai caratteri inseriti";
    if (PageUtil.isStringValid(comune)) {
      if (comune.equalsIgnoreCase(info)) {
        comuneOk = false;
      }

      if (comune.contains("-")) {
        String[] comuneSplitted = comune.split("-");
        String codiceComune = comuneSplitted[comuneSplitted.length - 1];
        String descrizioneComune = comune.substring(0, comune.lastIndexOf("-"));

        List<Comune> comuni =
            ServiceLocator.getInstance()
                .getServiziAnagrafici()
                .getTuttiComuniApkappa(Integer.parseInt(codiceComune), descrizioneComune)
                .getResult()
                .getComuni();

        if (LabelFdCUtil.checkIfNotNull(comuni)
            && comuni.size() == 1
            && comuni.get(0).getDescrizione().equalsIgnoreCase(descrizioneComune)
            && comuni.get(0).getCodice() == Integer.parseInt(codiceComune)) {
          comuneOk = true;
        } else {
          comuneOk = false;
        }

      } else {
        comuneOk = false;
      }
    }

    return comuneOk;
  }

  public static boolean checkStato(String stato)
      throws BusinessException, ApiException, IOException {
    boolean statoOk = true;
    String info = "Non esiste una via corrispondente ai caratteri inseriti";
    if (PageUtil.isStringValid(stato)) {
      if (stato.equalsIgnoreCase(info)) {
        statoOk = false;
      }

      if (stato.contains("-")) {
        String[] statoSplitted = stato.split("-");

        String codiceStato = statoSplitted[1];
        String descrizioneStato = statoSplitted[0];

        List<Stato> stati =
            ServiceLocator.getInstance()
                .getServiziAnagrafici()
                .getStati(codiceStato, descrizioneStato)
                .getResult()
                .getStati();

        if (LabelFdCUtil.checkIfNotNull(stati)
            && stati.size() == 1
            && stati.get(0).getDescrizione().equalsIgnoreCase(descrizioneStato)
            && stati.get(0).getCodice().equalsIgnoreCase(codiceStato)) {
          statoOk = true;
        } else {
          statoOk = false;
        }

      } else {
        statoOk = false;
      }
    }

    return statoOk;
  }

  public static boolean checkStatoComune(String stato, String comune)
      throws NumberFormatException, BusinessException, ApiException, IOException {
    boolean statoComuneOk = true;

    if (PageUtil.isStringValid(stato) && PageUtil.isStringValid(comune)) {

      String[] statoSplitted = stato.split("-");
      String codiceStato = statoSplitted[1];
      String descrizioneStato = statoSplitted[0];

      String[] comuneSplitted = comune.split("-");
      String codiceComune = comuneSplitted[comuneSplitted.length - 1];
      String descrizioneComune = comune.substring(0, comune.lastIndexOf("-"));

      List<Comune> comuni =
          ServiceLocator.getInstance()
              .getServiziAnagrafici()
              .getTuttiComuniApkappa(Integer.parseInt(codiceComune), descrizioneComune)
              .getResult()
              .getComuni();

      if (LabelFdCUtil.checkIfNotNull(comuni) && comuni.size() == 1) {
        if (comuni.get(0).getCodiceStato().equalsIgnoreCase(codiceStato)) {
          statoComuneOk = true;
        } else {
          statoComuneOk = false;
        }
      }
    }

    if ((LabelFdCUtil.checkIfNotNull(stato) && LabelFdCUtil.checkIfNull(comune))
        || (LabelFdCUtil.checkIfNotNull(comune) && LabelFdCUtil.checkIfNull(stato))) {
      statoComuneOk = false;
    }

    return statoComuneOk;
  }

  public static boolean checkCittadinanza(String cittadinanza)
      throws BusinessException, ApiException, IOException {
    boolean cittadinanzaOk = true;

    String info = "Non esiste una cittadinanza corrispondente ai caratteri inseriti";
    if (PageUtil.isStringValid(cittadinanza)) {
      if (cittadinanza.equalsIgnoreCase(info)) {
        cittadinanzaOk = false;
      }

      if (cittadinanza.contains("-")) {
        String[] cittadinanzaSplitted = cittadinanza.split("-");
        String codiceCittadinanza = cittadinanzaSplitted[cittadinanzaSplitted.length - 1];
        String descrizioneCittadinanza = cittadinanza.substring(0, cittadinanza.lastIndexOf("-"));

        List<Cittadinanza> cittadinanze =
            ServiceLocator.getInstance()
                .getServiziAnagrafici()
                .getCittadinanza(codiceCittadinanza, descrizioneCittadinanza)
                .getResult()
                .getCittadinanze();

        if (LabelFdCUtil.checkIfNotNull(cittadinanze)
            && cittadinanze.size() == 1
            && cittadinanze.get(0).getDescrizione().equalsIgnoreCase(descrizioneCittadinanza)
            && cittadinanze.get(0).getCodice().equalsIgnoreCase(codiceCittadinanza)) {
          cittadinanzaOk = true;
        } else {
          cittadinanzaOk = false;
        }

      } else {
        cittadinanzaOk = false;
      }
    }

    return cittadinanzaOk;
  }

  public static boolean checkProfessione(String professione)
      throws BusinessException, ApiException, IOException {
    boolean professioneOk = true;

    String info = "Non esiste una professione corrispondente ai caratteri inseriti";
    if (PageUtil.isStringValid(professione)) {
      if (professione.equalsIgnoreCase(info)) {
        professioneOk = false;
      }

      if (professione.contains("-")) {
        String[] professioneSplitted = professione.split("-");
        String codiceProfessione = professioneSplitted[professioneSplitted.length - 1];
        String descrizioneProfessione = professione.substring(0, professione.lastIndexOf("-"));

        Professione professioneTrovata =
            ServiceLocator.getInstance()
                .getServiziAnagrafici()
                .getProfessioniFiltrateByCodice(codiceProfessione);

        if (LabelFdCUtil.checkIfNotNull(professioneTrovata)
            && String.valueOf(professioneTrovata.getCodice()).equalsIgnoreCase(codiceProfessione)
            && LabelFdCUtil.checkIfNotNull(professioneTrovata.getDescrizioneM())
            && professioneTrovata
                .getDescrizioneM()
                .trim()
                .equalsIgnoreCase(descrizioneProfessione)) {
          professioneOk = true;
        } else {
          professioneOk = false;
        }

      } else {
        professioneOk = false;
      }
    }

    log.debug("CP professioneOk = " + professioneOk);

    return professioneOk;
  }

  public static boolean checkIfIndividuoAggiuntoIsLoggatoIsResidente(
      IndividuiCollegatiImmigrazione individuiCollegatiImmigrazione)
      throws BusinessException, ApiException {
    boolean checkOk = true;

    if (LabelFdCUtil.checkIfNotNull(individuiCollegatiImmigrazione)
        && (LabelFdCUtil.checkIfNotNull(individuiCollegatiImmigrazione.getCf()))) {
      Residente residente =
          ServiceLocator.getInstance()
              .getServiziAnagrafici()
              .getDatiResidentePerApk(individuiCollegatiImmigrazione.getCf());
      if (LabelFdCUtil.checkIfNotNull(residente)) {
        checkOk = false;
      }
    }

    return checkOk;
  }
}
