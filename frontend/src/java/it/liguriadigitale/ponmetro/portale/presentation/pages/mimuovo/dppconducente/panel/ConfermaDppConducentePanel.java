package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Anagrafica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Anagrafica.CpvHasSexEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaCpvHasAddress;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaCpvHasBirthPlace;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiPatente;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.PuntiPatente;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RichiestaDPP;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RichiestaDPP.DichiarazioneEnum;
import java.io.IOException;
import java.time.LocalDate;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class ConfermaDppConducentePanel extends BasePanel {

  private static final long serialVersionUID = -1739476939275159213L;

  public ConfermaDppConducentePanel(RicercaVerbaleConducente ricercaVerbaleConducente) {
    super("dppConducentePanel");

    createFeedBackPanel();
    fillDati(ricercaVerbaleConducente);
  }

  @Override
  public void fillDati(Object dati) {
    try {

      RicercaVerbaleConducente ricercaVerbaleConducente = (RicercaVerbaleConducente) dati;

      log.debug("CP fill dati conferma dpp cond = " + ricercaVerbaleConducente);

      RichiestaDPP richiestaDPP = new RichiestaDPP();

      richiestaDPP.setNumeroAvviso(ricercaVerbaleConducente.getNumeroAvviso());

      LocalDate dataVerbale = ricercaVerbaleConducente.getDataVerbale();
      richiestaDPP.setDataVerbale(dataVerbale);

      richiestaDPP.setTarga(ricercaVerbaleConducente.getTarga().toUpperCase());

      Anagrafica anagraficaConducente = new Anagrafica();
      String nomeCognomeConducente =
          ricercaVerbaleConducente
              .getCognomeConducente()
              .concat(" ")
              .concat(ricercaVerbaleConducente.getNomeConducente());
      anagraficaConducente.setRdfsLabel(nomeCognomeConducente.toUpperCase());
      anagraficaConducente.setCpvFamilyName(
          ricercaVerbaleConducente.getCognomeConducente().toUpperCase());
      anagraficaConducente.setCpvGivenName(
          ricercaVerbaleConducente.getNomeConducente().toUpperCase());
      anagraficaConducente.setCpvTaxCode(ricercaVerbaleConducente.getCodiceFiscaleConducente());
      anagraficaConducente.setCpvDateOfBirth(ricercaVerbaleConducente.getDataNascitaConducente());

      CpvHasSexEnum sessoEnum = CpvHasSexEnum.F;
      if (ricercaVerbaleConducente.getSessoConducente().equalsIgnoreCase("M")) {
        sessoEnum = CpvHasSexEnum.M;
      }
      anagraficaConducente.setCpvHasSex(sessoEnum);

      AnagraficaCpvHasBirthPlace luogoNascitaConducente = new AnagraficaCpvHasBirthPlace();
      luogoNascitaConducente.setClvCity(
          ricercaVerbaleConducente.getCittaNascitaConducente().toUpperCase());
      luogoNascitaConducente.setClvProvince(
          ricercaVerbaleConducente.getProvinciaNascitaConducente().toUpperCase());
      anagraficaConducente.setCpvHasBirthPlace(luogoNascitaConducente);

      AnagraficaCpvHasAddress indirizzoConducente = new AnagraficaCpvHasAddress();
      indirizzoConducente.setClvFullAddress(
          ricercaVerbaleConducente.getIndirizzoConducente().toUpperCase());
      indirizzoConducente.setClvPostCode(ricercaVerbaleConducente.getCapConducente());
      indirizzoConducente.setClvCity(ricercaVerbaleConducente.getCittaConducente().toUpperCase());
      anagraficaConducente.setCpvHasAddress(indirizzoConducente);

      richiestaDPP.setAnagraficaConducente(anagraficaConducente);

      Anagrafica anagraficaProprietario = null;
      richiestaDPP.setAnagraficaProprietario(anagraficaProprietario);

      DatiPatente datiPatente = new DatiPatente();
      datiPatente.setNumeroPatente(ricercaVerbaleConducente.getNumeroPatente().toUpperCase());
      datiPatente.setCategoriaPatente(ricercaVerbaleConducente.getCategoriaPatente().toUpperCase());
      datiPatente.setPatenteRilasciataDa(
          ricercaVerbaleConducente.getPatenteRilasciataDa().toUpperCase());
      datiPatente.setPatenteRilasciataIl(ricercaVerbaleConducente.getPatenteRilasciataIl());
      datiPatente.setPatenteValidaFinoAl(ricercaVerbaleConducente.getPatenteValidaFinoAl());
      FileAllegato fileAllegatoPatente = new FileAllegato();
      fileAllegatoPatente.setFile(ricercaVerbaleConducente.getByteFilePatente());
      fileAllegatoPatente.setNomeFile(ricercaVerbaleConducente.getNomeFilePatente());
      String mimeType =
          FileFdCUtil.getMimeTypeFileUploadAllegato(ricercaVerbaleConducente.getByteFilePatente());

      fileAllegatoPatente.setMimeType(mimeType);
      datiPatente.setImmaginePatente(fileAllegatoPatente);
      richiestaDPP.setDatiPatenteDichiarante(datiPatente);

      richiestaDPP.setEmailDichiarante(ricercaVerbaleConducente.getEmailDichiarante());

      DichiarazioneEnum dichiarazioneEnum = DichiarazioneEnum.CONDUCENTEDICHIARACONDUCENTE;
      richiestaDPP.setDichiarazione(dichiarazioneEnum);

      PuntiPatente dichiarazionePuntiPatente =
          ServiceLocator.getInstance()
              .getServiziMieiVerbali()
              .dichiarazionePuntiPatenteConducente(
                  getUtente(), ricercaVerbaleConducente.getNumeroAvviso(), richiestaDPP);

      log.debug("CP dichiarazionePuntiPatente cond = " + dichiarazionePuntiPatente);

      Label codiceFiscaleDichiarante =
          new Label(
              "codiceFiscaleDichiarante", dichiarazionePuntiPatente.getCodiceFiscaleDichiarante());
      codiceFiscaleDichiarante.setVisible(
          PageUtil.isStringValid(dichiarazionePuntiPatente.getCodiceFiscaleDichiarante()));
      addOrReplace(codiceFiscaleDichiarante);

      Label numeroPatente =
          new Label("numeroPatente", dichiarazionePuntiPatente.getNumeroPatente());
      numeroPatente.setVisible(
          PageUtil.isStringValid(dichiarazionePuntiPatente.getNumeroPatente()));
      addOrReplace(numeroPatente);

      Label emailDichiarante =
          new Label("emailDichiarante", dichiarazionePuntiPatente.getEmailDichiarante());
      emailDichiarante.setVisible(
          PageUtil.isStringValid(dichiarazionePuntiPatente.getEmailDichiarante()));
      addOrReplace(emailDichiarante);

      String statoDpp = dichiarazionePuntiPatente.getStatoDichiarazione();
      if (PageUtil.isStringValid(dichiarazionePuntiPatente.getStatoDichiarazione())
          && dichiarazionePuntiPatente.getStatoDichiarazione().equalsIgnoreCase("inElaborazione")) {
        statoDpp = "IN ELABORAZIONE";
      }
      if (PageUtil.isStringValid(dichiarazionePuntiPatente.getStatoDichiarazione())
          && dichiarazionePuntiPatente.getStatoDichiarazione().equalsIgnoreCase("nonPresente")) {
        statoDpp = "NON PRESENTE";
      }
      Label stato = new Label("stato", statoDpp);
      stato.setVisible(PageUtil.isStringValid(dichiarazionePuntiPatente.getStatoDichiarazione()));
      addOrReplace(stato);

      FileAllegato ricevuta = dichiarazionePuntiPatente.getRicevutaDPP();
      if (LabelFdCUtil.checkIfNotNull(ricevuta)) {
        addOrReplace(downloadRicevuta(ricevuta));
      } else {
        WebMarkupContainer ricevutaNulla = new WebMarkupContainer("btnDownload");
        ricevutaNulla.setVisible(false);
        addOrReplace(ricevutaNulla);
      }

      // TAPULLO PER ERRORE LATO HERMES RESTITUISCE HTTP 200 MA TUTTO
      // VUOTO
      if (!UtilVerbali.checkIfNotNull(dichiarazionePuntiPatente.getNumeroPatente())
          && !UtilVerbali.checkIfNotNull(dichiarazionePuntiPatente.getCodiceFiscaleDichiarante())
          && !UtilVerbali.checkIfNotNull(dichiarazionePuntiPatente.getEmailDichiarante())
          && !UtilVerbali.checkIfNotNull(dichiarazionePuntiPatente.getRicevutaDPP())
          && !UtilVerbali.checkIfNotNull(dichiarazionePuntiPatente.getStatoDichiarazione())
          && dichiarazionePuntiPatente.getIdentificativoDichiarazione() == 0) {

        error(
            "Servizio dichiarazione punti patente attualmente non disponibile, si prega di riprovare più tardi. Grazie.");

      } else {
        success(getString("ConfermaDppConducentePanel.success"));
      }

    } catch (BusinessException | ApiException | IOException | MagicMatchNotFoundException e) {
      log.error("Errore durante DPP conducente: " + e.getMessage());
      this.error(
          "Attenzione, errore nella richiesta di dichiarazione punti patente. Si prega di riprovare più tardi. Grazie.");
    }
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadRicevuta(FileAllegato ricevuta) {
    final AbstractResource fileResourceByte;

    String nomeFile = ricevuta.getNomeFile();

    fileResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = 9104653345391693746L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {
              r.setFileName(nomeFile);
              r.setContentType("application/pdf");
              r.setContentDisposition(ContentDisposition.INLINE);
              r.setContentLength(ricevuta.getFile().length);
              r.setWriteCallback(
                  new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                      attributes.getResponse().write(ricevuta.getFile());
                    }
                  });

              r.disableCaching();
            } catch (final Exception e) {
              log.error("Errore durante scarico pdf ricevuta dpp conducente");
            }

            return r;
          }
        };

    final ResourceLink linkFile =
        new ResourceLink("btnDownload", fileResourceByte) {

          private static final long serialVersionUID = 5259836759618558252L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };

    linkFile.setVisible(LabelFdCUtil.checkIfNotNull(ricevuta));

    return linkFile;
  }
}
