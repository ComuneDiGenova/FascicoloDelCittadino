package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbali;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
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

public class ConfermaDppProprietarioPanel extends BasePanel {

  private static final long serialVersionUID = -5431196033323486961L;

  public ConfermaDppProprietarioPanel(PuntiPatenteProprietario puntiPatenteProprietario) {
    super("dppProprietarioPanel");

    fillDati(puntiPatenteProprietario);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    try {

      PuntiPatenteProprietario puntiPatenteProprietario = (PuntiPatenteProprietario) dati;

      log.debug("CP fill dati conferma dpp prop = " + puntiPatenteProprietario);

      RichiestaDPP richiestaDPP = new RichiestaDPP();

      richiestaDPP.setNumeroAvviso(puntiPatenteProprietario.getNumeroAvviso());
      richiestaDPP.setNumeroProtocollo(puntiPatenteProprietario.getNumeroProtocollo());

      LocalDate dataVerbale = puntiPatenteProprietario.getDataVerbale();
      richiestaDPP.setDataVerbale(dataVerbale);

      richiestaDPP.setTarga(puntiPatenteProprietario.getTarga().toUpperCase());

      Anagrafica anagraficaConducente = new Anagrafica();

      Anagrafica anagraficaProprietario = new Anagrafica();

      // se dichiarazione true = prop dichiara prop, altrimenti prop
      // dichiara cond

      String cognomeNomeProprietario =
          puntiPatenteProprietario
              .getCognomeProprietario()
              .concat(" ")
              .concat(puntiPatenteProprietario.getNomeProprietario());
      anagraficaProprietario.setRdfsLabel(cognomeNomeProprietario.toUpperCase());
      anagraficaProprietario.setCpvTaxCode(puntiPatenteProprietario.getCfProprietario());
      anagraficaProprietario.setCpvDateOfBirth(puntiPatenteProprietario.getProprietarioNatoIl());

      CpvHasSexEnum sessoProprietario = null;
      String genereProprietario =
          CodiceFiscaleValidatorUtil.getSessoFromCf(puntiPatenteProprietario.getCfProprietario());
      if (genereProprietario.equalsIgnoreCase("F")) {
        sessoProprietario = CpvHasSexEnum.F;
      } else {
        sessoProprietario = CpvHasSexEnum.M;
      }
      anagraficaProprietario.setCpvHasSex(sessoProprietario);
      anagraficaProprietario.setCpvFamilyName(
          puntiPatenteProprietario.getCognomeProprietario().toUpperCase());
      anagraficaProprietario.setCpvGivenName(
          puntiPatenteProprietario.getNomeProprietario().toUpperCase());

      AnagraficaCpvHasBirthPlace luogoNascitaProprietario = new AnagraficaCpvHasBirthPlace();
      luogoNascitaProprietario.setClvCity(
          puntiPatenteProprietario.getProprietarioNatoA().toUpperCase());
      luogoNascitaProprietario.setClvProvince(
          puntiPatenteProprietario.getProprietarioNatoAProvincia().toUpperCase());
      anagraficaProprietario.setCpvHasBirthPlace(luogoNascitaProprietario);

      AnagraficaCpvHasAddress indirizzoProprietario = new AnagraficaCpvHasAddress();
      indirizzoProprietario.setClvFullAddress(
          puntiPatenteProprietario.getProprietarioResidenteIn().toUpperCase());
      indirizzoProprietario.setClvPostCode(puntiPatenteProprietario.getProprietarioResidenteCap());
      indirizzoProprietario.setClvCity(
          puntiPatenteProprietario.getProprietarioResidenteCitta().toUpperCase());
      anagraficaProprietario.setCpvHasAddress(indirizzoProprietario);

      if (puntiPatenteProprietario.isDichiarazioneProprietario()) {
        log.debug("CP prop dichiara prop");

        DatiPatente datiPatente = new DatiPatente();
        datiPatente.setNumeroPatente(puntiPatenteProprietario.getNumeroPatente().toUpperCase());
        datiPatente.setCategoriaPatente(
            puntiPatenteProprietario.getCategoriaPatente().toUpperCase());
        datiPatente.setPatenteRilasciataDa(
            puntiPatenteProprietario.getPatenteRilasciataDa().toUpperCase());
        LocalDate dataRilascioPatente = puntiPatenteProprietario.getPatenteRilasciataIl();
        datiPatente.setPatenteRilasciataIl(dataRilascioPatente);
        LocalDate dataPatenteValida = puntiPatenteProprietario.getPatenteValidaFinoAl();
        datiPatente.setPatenteValidaFinoAl(dataPatenteValida);
        FileAllegato fileAllegatoPatente = new FileAllegato();
        fileAllegatoPatente.setFile(puntiPatenteProprietario.getBytePatenteProprietarioUpload());
        fileAllegatoPatente.setNomeFile(
            puntiPatenteProprietario.getNomePatenteProprietarioUpload());

        String mimeType =
            FileFdCUtil.getMimeTypeFileUploadAllegato(
                puntiPatenteProprietario.getBytePatenteProprietarioUpload());
        fileAllegatoPatente.setMimeType(mimeType);

        datiPatente.setImmaginePatente(fileAllegatoPatente);

        richiestaDPP.setDatiPatenteDichiarante(datiPatente);

        richiestaDPP.setEmailDichiarante(puntiPatenteProprietario.getEmailProprietario());

        DichiarazioneEnum dichiarazioneEnum = DichiarazioneEnum.PROPRIETARIODICHIARAPROPRIETARIO;
        richiestaDPP.setDichiarazione(dichiarazioneEnum);

      } else {
        log.debug("CP prop dichiara cond");

        String nomeCognomeConducente =
            puntiPatenteProprietario
                .getCognomeConducente()
                .concat(" ")
                .concat(puntiPatenteProprietario.getNomeConducente());
        anagraficaConducente.setRdfsLabel(nomeCognomeConducente.toUpperCase());
        anagraficaConducente.setCpvFamilyName(
            puntiPatenteProprietario.getCognomeConducente().toUpperCase());
        anagraficaConducente.setCpvGivenName(
            puntiPatenteProprietario.getNomeConducente().toUpperCase());

        CpvHasSexEnum sesso = null;
        String genere =
            CodiceFiscaleValidatorUtil.getSessoFromCf(puntiPatenteProprietario.getCfConducente());
        if (genere.equalsIgnoreCase("F")) {
          sesso = CpvHasSexEnum.F;
        } else {
          sesso = CpvHasSexEnum.M;
        }
        anagraficaConducente.setCpvHasSex(sesso);

        anagraficaConducente.setCpvTaxCode(
            puntiPatenteProprietario.getCfConducente().toUpperCase());

        LocalDate conducenteNatoIl = puntiPatenteProprietario.getConducenteNatoIl();
        anagraficaConducente.setCpvDateOfBirth(conducenteNatoIl);

        AnagraficaCpvHasBirthPlace luogoNascitaConducente = new AnagraficaCpvHasBirthPlace();
        luogoNascitaConducente.setClvCity(
            puntiPatenteProprietario.getConducenteNatoA().toUpperCase());
        luogoNascitaConducente.setClvProvince(
            puntiPatenteProprietario.getConducenteNatoAProvincia().toUpperCase());
        anagraficaConducente.setCpvHasBirthPlace(luogoNascitaConducente);

        AnagraficaCpvHasAddress indirizzoConducente = new AnagraficaCpvHasAddress();

        String indirizzoCompletoConducente =
            puntiPatenteProprietario
                .getConducenteResidenteIn()
                .concat(" ")
                .concat(puntiPatenteProprietario.getConducenteResidenteNumCivico());
        indirizzoConducente.setClvFullAddress(indirizzoCompletoConducente.toUpperCase());
        indirizzoConducente.setClvPostCode(puntiPatenteProprietario.getConducenteResidenteCap());
        indirizzoConducente.setClvCity(
            puntiPatenteProprietario.getConducenteResidenteA().toUpperCase());
        anagraficaConducente.setCpvHasAddress(indirizzoConducente);

        DatiPatente datiPatenteConducente = new DatiPatente();
        datiPatenteConducente.setNumeroPatente(
            puntiPatenteProprietario.getNumeroPatenteConducente().toUpperCase());
        datiPatenteConducente.setCategoriaPatente(
            puntiPatenteProprietario.getCategoriaPatenteConducente().toUpperCase());
        datiPatenteConducente.setPatenteRilasciataDa(
            puntiPatenteProprietario.getPatenteConducenteRilasciataDa().toUpperCase());
        LocalDate dataPatenteConducenteRilasciata =
            puntiPatenteProprietario.getPatenteConducenteRilasciataIl();
        datiPatenteConducente.setPatenteRilasciataIl(dataPatenteConducenteRilasciata);
        LocalDate dataPatenteConducenteValida =
            puntiPatenteProprietario.getPatenteConducenteValidaFinoAl();
        datiPatenteConducente.setPatenteValidaFinoAl(dataPatenteConducenteValida);

        FileAllegato fileAllegatoPatenteConducente = new FileAllegato();
        fileAllegatoPatenteConducente.setFile(
            puntiPatenteProprietario.getBytePatenteConducenteUpload());
        fileAllegatoPatenteConducente.setNomeFile(
            puntiPatenteProprietario.getNomePatenteConducenteUpload());
        String mimeTypeConducente =
            FileFdCUtil.getMimeTypeFileUploadAllegato(
                puntiPatenteProprietario.getBytePatenteConducenteUpload());
        fileAllegatoPatenteConducente.setMimeType(mimeTypeConducente);
        datiPatenteConducente.setImmaginePatente(fileAllegatoPatenteConducente);

        richiestaDPP.setDatiPatenteDichiarante(datiPatenteConducente);

        richiestaDPP.setEmailDichiarante(puntiPatenteProprietario.getEmailConducente());

        DichiarazioneEnum dichiarazioneEnum = DichiarazioneEnum.PROPRIETARIODICHIARACONDUCENTE;
        richiestaDPP.setDichiarazione(dichiarazioneEnum);
      }

      richiestaDPP.setAnagraficaConducente(anagraficaConducente);

      richiestaDPP.setAnagraficaProprietario(anagraficaProprietario);

      log.debug("CP richiesta dpp = " + richiestaDPP);

      PuntiPatente dichiarazionePuntiPatente =
          ServiceLocator.getInstance()
              .getServiziMieiVerbali()
              .dichiarazionePuntiPatenteProprietario(
                  getUtente(), puntiPatenteProprietario.getNumeroAvviso(), richiestaDPP);

      log.debug("CP dichiarazionePuntiPatente prop = " + dichiarazionePuntiPatente);

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
        success("Richiesta dichiarazione punti patente inviata correttamente.");
      }

    } catch (BusinessException | ApiException | IOException | MagicMatchNotFoundException e) {
      log.error("Errore durante DPP proprietario: " + e.getMessage());
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

          private static final long serialVersionUID = 1353467382593744654L;

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
              log.error("Errore durante scarico pdf ricevuta dpp proprietario");
            }

            return r;
          }
        };

    final ResourceLink linkFile =
        new ResourceLink("btnDownload", fileResourceByte) {

          private static final long serialVersionUID = -7735889322731036021L;

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
