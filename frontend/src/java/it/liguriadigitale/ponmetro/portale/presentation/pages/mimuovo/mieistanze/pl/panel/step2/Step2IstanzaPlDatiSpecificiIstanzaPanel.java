package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step2;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.GestisciIstanzaPLPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.Step2IstanzaPlDatiSpecificiIstanzaForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.StepBaseIstanzaPlPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.time.LocalDate;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class Step2IstanzaPlDatiSpecificiIstanzaPanel extends StepBaseIstanzaPlPanel {

  private static final long serialVersionUID = 2781955826041290508L;

  private Step2IstanzaPlDatiSpecificiIstanzaForm form = null;

  public Step2IstanzaPlDatiSpecificiIstanzaPanel(
      String id, Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id, index, datiRichiestaIstanzaPl);
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {
    form = new Step2IstanzaPlDatiSpecificiIstanzaForm("form", datiRichiestaIstanzaPl);

    log.error(
        "Step2IstanzaPlDatiSpecificiIstanzaFormStep2IstanzaPlDatiSpecificiIstanzaForm: datiRichiestaIstanzaPl "
            + datiRichiestaIstanzaPl);

    form.setOutputMarkupId(true);
    form.setEscapeModelStrings(false);
    form.setMultiPart(true);
    form.addOrReplace(creaBottoneAvanti());
    form.addOrReplace(creaBottoneIndietro());
    form.addOrReplace(creaBottoneAnnulla());
    addOrReplace(form);

    Label motivoScelto = new Label("motivoScelto", getStringaMotivoScelto(datiRichiestaIstanzaPl));
    form.addOrReplace(motivoScelto);
  }

  private boolean isMotivoSelezionatoEqualToCode(String code) {
    return datiRichiestaIstanzaPl.isMotivoSelezionatoEqualToCode(code);
  }

  private LaddaAjaxButton creaBottoneAvanti() {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 1031626225565871794L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(Step2IstanzaPlDatiSpecificiIstanzaPanel.this);

            Boolean boIsOk = true;
            if (isMotivoSelezionatoEqualToCode("03")) {
              if (datiRichiestaIstanzaPl.getAutodichiarazioneFurto() == null
                  || !datiRichiestaIstanzaPl.getAutodichiarazioneFurto()) {
                boIsOk = false;
                log.debug("CHECK1");
                error(
                    "Per poter procedere, in base alla motivazione scelta, è necessario selezionare SI per autodichiarazione FURTO");
              }
              if (datiRichiestaIstanzaPl.getAutodichiarazioneFurtoRitrovamento() == null) {
                boIsOk = false;
                log.debug("CHECK2");
                error(
                    "Per poter procedere, in base alla motivazione scelta, è necessario selezionare SI o NO autodichiarazione ritrovamento");
              }
              if (datiRichiestaIstanzaPl.getFurtoData() != null
                  && datiRichiestaIstanzaPl.getVerbaleData() != null
                  && (datiRichiestaIstanzaPl
                      .getFurtoData()
                      .isAfter(datiRichiestaIstanzaPl.getVerbaleData()))) {
                boIsOk = false;
                log.debug("CHECK3");
                log.debug(
                    "datiRichiestaIstanzaPl.getFurtoData()="
                        + datiRichiestaIstanzaPl.getFurtoData());
                error("Per poter procedere la data furto non può essere successiva al verbale.");
              }
              if (datiRichiestaIstanzaPl.getRitrovamentoData() != null
                  && datiRichiestaIstanzaPl.getFurtoData() != null
                  && (datiRichiestaIstanzaPl
                      .getRitrovamentoData()
                      .isBefore(datiRichiestaIstanzaPl.getVerbaleData()))) {
                boIsOk = false;
                log.debug("CHECK4");
                log.debug(
                    "datiRichiestaIstanzaPl.getRitrovamentoData()="
                        + datiRichiestaIstanzaPl.getRitrovamentoData());
                error(
                    "Per poter procedere la data di rinvenimento non può essere precedente al verbale.");
              }
              if (datiRichiestaIstanzaPl.getRitrovamentoData() != null
                  && datiRichiestaIstanzaPl.getFurtoData() != null
                  && (datiRichiestaIstanzaPl
                      .getRitrovamentoData()
                      .isBefore(datiRichiestaIstanzaPl.getFurtoData()))) {
                boIsOk = false;
                log.debug("CHECK5");
                log.debug(
                    "datiRichiestaIstanzaPl.getRitrovamentoData()="
                        + datiRichiestaIstanzaPl.getRitrovamentoData());
                error("La data di rinvenimento non può essere precedente alla data furto.");
              }
              if (datiRichiestaIstanzaPl.getFurtoData() != null
                  && LocalDateUtil.calcolaEta(datiRichiestaIstanzaPl.getFurtoData()) > 20) {
                boIsOk = false;
                log.debug("CHECK6");
                error("Per poter procedere la data furto non può essere più vecchia di 20 anni.");
              }
            } else if (isMotivoSelezionatoEqualToCode("13")) {
              //					if (datiRichiestaIstanzaPl.getAutodichiarazioneProprietarioETitolare() == null
              // ||
              //							!datiRichiestaIstanzaPl.getAutodichiarazioneProprietarioETitolare()) {
              //						boIsOk = false;
              //						log.debug("CHECK7");
              //						error("Per poter procedere, in base alla motivazione scelta, è necessario
              // selezionare SI per autodichiarazione Proprietario e titolare");
              //					}

              if (datiRichiestaIstanzaPl.getAutodichiarazioneProprietarioETitolare() == null) {
                boIsOk = false;
                log.debug("CHECK7");
                error(
                    "Per poter procedere, in base alla motivazione scelta, è necessario selezionare SI o NO per autodichiarazione Proprietario e Titolare");
              }
            } else if (datiRichiestaIstanzaPl
                .getDatiMotivoSummary()
                .getCodice()
                .equalsIgnoreCase("32")) {

              LocalDate dataInzioProprieta = datiRichiestaIstanzaPl.getDataInizioProprieta();
              LocalDate dataFineProprieta = datiRichiestaIstanzaPl.getDataFineProprieta();
              if (dataInzioProprieta == null) {
                boIsOk = false;
                error("Inserisci la data di inizio proprietà");
              } else {
                if (dataFineProprieta == null) {
                  dataFineProprieta = LocalDate.now();
                }

                if (dataFineProprieta.isBefore(dataInzioProprieta)) {
                  boIsOk = false;
                  error("La data fine non può essere antecedente alla data inizio");
                } else {

                  if (isWithinRange(
                      datiRichiestaIstanzaPl.getDataAccertamentoVerbalePartenza(),
                      dataInzioProprieta,
                      dataFineProprieta)) {
                    boIsOk = false;
                    error(
                        "La data del verbale è inclusa nell'intervallo di tempo in cui dichiari di essere proprietario.");
                  }
                }
              }
              // error("Se la macchina è la tua non puoi inserire l'istanza selezionata.");
            }

            if (boIsOk) {
              index = index + 1;
              setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(Step2IstanzaPlDatiSpecificiIstanzaPanel.this);

            log.error("Attenzione, errore step 1 richiesta istanza PL");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step2IstanzaPlDatiSpecificiIstanzaPanel.avanti",
                    Step2IstanzaPlDatiSpecificiIstanzaPanel.this)));

    return avanti;
  }

  boolean isWithinRange(LocalDate dataVerbale, LocalDate dataInizio, LocalDate dataFine) {
    return !(dataVerbale.isBefore(dataInizio) || dataVerbale.isAfter(dataFine));
  }

  private LaddaAjaxButton creaBottoneIndietro() {
    LaddaAjaxButton indietro =
        new LaddaAjaxButton("indietro", Type.Primary) {

          private static final long serialVersionUID = -1L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(Step2IstanzaPlDatiSpecificiIstanzaPanel.this);

            index = index - 1;

            setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(Step2IstanzaPlDatiSpecificiIstanzaPanel.this);

            index = index - 1;

            setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));

            log.error("Attenzione, errore step 2 richiesta istanza PL");
          }
        };
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step2IstanzaPlDatiSpecificiIstanzaPanel.indietro",
                    Step2IstanzaPlDatiSpecificiIstanzaPanel.this)));

    return indietro;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(Step2IstanzaPlDatiSpecificiIstanzaPanel.this);

            form.clearInput();
            index = 1;
            setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step2IstanzaPlDatiSpecificiIstanzaPanel.annulla",
                    Step2IstanzaPlDatiSpecificiIstanzaPanel.this)));

    return annulla;
  }

  private String getStringaMotivoScelto(DatiRichiestaIstanzaPl datiRichiestaIstanza) {
    String key = "IstanzePlCodiceHermes." + datiRichiestaIstanza.getCodiceHermesMotivoSelezionato();
    return getString(key);
  }
}
