package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.step1;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.MieIstanzePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.GestisciIstanzaPLPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form.Step1IstanzaPlDatiGeneraliForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.panel.StepBaseIstanzaPlPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.BigDecimalUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateTimeUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.ArticoloViolato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale.StatoPagamentoEnum;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class Step1IstanzaPlDatiGeneraliPanel extends StepBaseIstanzaPlPanel {

  private static final long serialVersionUID = 2781955826041290508L;

  private Step1IstanzaPlDatiGeneraliForm form = null;

  public Step1IstanzaPlDatiGeneraliPanel(
      String id, Integer index, DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {
    super(id, index, datiRichiestaIstanzaPl);
    datiRichiestaIstanzaPl.setUtente(getUtente());
    fillDati(null);
  }

  @Override
  public void fillDati(Object dati) {
    form = new Step1IstanzaPlDatiGeneraliForm("form", datiRichiestaIstanzaPl);

    log.debug(
        "Step1IstanzaPlDatiGeneraliFormStep1IstanzaPlDatiGeneraliForm: datiRichiestaIstanzaPl "
            + datiRichiestaIstanzaPl);

    form.addOrReplace(creaBottoneAnnulla());
    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    form.addOrReplace(creaBottoneAvanti());
    addOrReplace(form);
  }

  private LaddaAjaxButton creaBottoneAvanti() {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 1031626225565871794L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(Step1IstanzaPlDatiGeneraliPanel.this);

            // setto "tutto" nella form, anche i dati statici
            datiRichiestaIstanzaPl.setDatiMotivoSummary(
                form.getDatiMotivoSummarySelezionato(), true);

            try {
              datiRichiestaIstanzaPl = inizializzaTuttiVerbali(datiRichiestaIstanzaPl);
            } catch (Exception e) {
              log.error("Step 1 Istanze Codice Hermes 1 -- errore " + e.getMessage(), e);
            }

            log.debug("[AvantionSubmit]" + datiRichiestaIstanzaPl);

            if (datiRichiestaIstanzaPl.getDatiMotivoSummary() != null
                && LabelFdCUtil.checkIfNotNull(
                    datiRichiestaIstanzaPl.getDatiMotivoSummary().getCodice())) {

              if (("01".equalsIgnoreCase(datiRichiestaIstanzaPl.getCodiceHermesMotivoSelezionato()))
                  && !checkCodiceHermes1Articoli7e158(datiRichiestaIstanzaPl)) {
                error(
                    "La motivazione selezionata non può essere applicata in quanto non sono presenti verbali relativi agli articoli 7 e 158 redatti nella stessa via allo stesso veicolo nell’arco temporale che va dalle 24 ore precedenti alle 24 ore successive rispetto alla data e all’ora del verbale per il quale si richiede istanza di annullamento.");
              } else {
                index = index + 1;
                setResponsePage(new GestisciIstanzaPLPage(index, datiRichiestaIstanzaPl));
              }
            } else {
              error("Selezionare un motivo per poter procedere.");
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(Step1IstanzaPlDatiGeneraliPanel.this);

            log.error("Attenzione, errore step 1 richiesta istanza PL");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step1IstanzaPlDatiGeneraliPanel.avanti",
                    Step1IstanzaPlDatiGeneraliPanel.this)));
    avanti.setVisible(
        form.getListDatiMotivoSummary() != null && !form.getListDatiMotivoSummary().isEmpty());
    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(Step1IstanzaPlDatiGeneraliPanel.this);
            form.clearInput();
            index = 1;
            setResponsePage(new MieIstanzePage());
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "Step1IstanzaPlDatiGeneraliPanel.annulla",
                    Step1IstanzaPlDatiGeneraliPanel.this)));

    return annulla;
  }

  private boolean checkCodiceHermes1Articoli7e158(DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {

    boolean result = false;

    List<Verbale> listVerbali = datiRichiestaIstanzaPl.getListTuttiVerbali();

    if ("01".equalsIgnoreCase(datiRichiestaIstanzaPl.getCodiceHermesMotivoSelezionato())) {
      // verbali articolo 7 o 158
      // stessa via
      // stesso veicolo
      // +-24h
      log.debug("Step 1 Istanze -- DENTRO CODICE UNO");
      List<Verbale> listVerbaliFiltratiSoloStatoAperto =
          listVerbali.stream()
              .filter(elemVerbale -> elemVerbale.getStato().equalsIgnoreCase("APERTO"))
              .collect(Collectors.toList());

      for (Verbale verbale : listVerbaliFiltratiSoloStatoAperto) {

        log.debug("CP in codice 01 verbale = " + verbale.getNumeroProtocollo());

        Boolean articoloOk = false;
        DettaglioVerbale dettaglioVerbale =
            datiRichiestaIstanzaPl.getMapDettaglioVerbale(
                verbale.getNumeroProtocollo(), getUtente());
        if (dettaglioVerbale == null) {
          continue;
        }

        if (dettaglioVerbale.getStatoPagamento() != null
            && !dettaglioVerbale
                .getStatoPagamento()
                .equalsIgnoreCase(StatoPagamentoEnum.DA_EFFETTUARE.value())) {
          continue;
        }

        if (verbale.getArticoliViolati() != null && !verbale.getArticoliViolati().isEmpty()) {
          for (ArticoloViolato articoloViolato : verbale.getArticoliViolati()) {
            if (articoloViolato.getNumero() == null) {
              continue;
            }
            if (articoloViolato.getNumero().equals(7L)
                || articoloViolato.getNumero().equals(158L)) {
              articoloOk = true;
              break;
            }
          }
        }
        Boolean viaOk = false;
        if (articoloOk) {
          // stessa via
          String codeVia1VerbalePartenza = datiRichiestaIstanzaPl.getCodeVia1VerbalePartenza();
          String codeVia2VerbalePartenza = datiRichiestaIstanzaPl.getCodeVia2VerbalePartenza();
          String codeVia3VerbalePartenza = datiRichiestaIstanzaPl.getCodeVia3VerbalePartenza();
          String verbaleStreetNameCode1 = verbale.getOfficialStreetNameCode1();
          String verbaleStreetNameCode2 = verbale.getOfficialStreetNameCode2();
          String verbaleStreetNameCode3 = verbale.getOfficialStreetNameCode3();
          if (verbaleStreetNameCode1 != null
              && (verbaleStreetNameCode1.equalsIgnoreCase(codeVia1VerbalePartenza)
                  || verbaleStreetNameCode1.equalsIgnoreCase(codeVia2VerbalePartenza)
                  || verbaleStreetNameCode1.equalsIgnoreCase(codeVia3VerbalePartenza))) {
            viaOk = true;
          }
          if (!viaOk
              && verbaleStreetNameCode2 != null
              && (verbaleStreetNameCode2.equalsIgnoreCase(codeVia1VerbalePartenza)
                  || verbaleStreetNameCode2.equalsIgnoreCase(codeVia2VerbalePartenza)
                  || verbaleStreetNameCode2.equalsIgnoreCase(codeVia3VerbalePartenza))) {
            viaOk = true;
          }
          if (!viaOk
              && verbaleStreetNameCode3 != null
              && (verbaleStreetNameCode3.equalsIgnoreCase(codeVia1VerbalePartenza)
                  || verbaleStreetNameCode3.equalsIgnoreCase(codeVia2VerbalePartenza)
                  || verbaleStreetNameCode3.equalsIgnoreCase(codeVia3VerbalePartenza))) {
            viaOk = true;
          }
        }

        log.debug("CP viaOk = " + viaOk);

        Boolean veicoloOk = false;
        if (viaOk) {
          // stesso veicolo
          if (verbale.getTarga() != null
              && datiRichiestaIstanzaPl.getTargaVerbalePartenza() != null
              && verbale
                  .getTarga()
                  .equalsIgnoreCase(datiRichiestaIstanzaPl.getTargaVerbalePartenza())) {
            veicoloOk = true;
          }
        }
        Boolean dataOk = false;
        if (veicoloOk) {
          // +-24h
          LocalDateTime dataDataEOraAccertamentoVerbalePartenza =
              datiRichiestaIstanzaPl.getDataEOraAccertamentoVerbalePartenza();

          boolean stessaDataConVerbaleDiPartenza = false;
          LocalDate localDateVerbaleDiPartenza = null;
          if (LabelFdCUtil.checkIfNotNull(datiRichiestaIstanzaPl.getDatiCompletiVerbaleDiPartenza())
              && LabelFdCUtil.checkIfNotNull(
                  datiRichiestaIstanzaPl
                      .getDatiCompletiVerbaleDiPartenza()
                      .getDettaglioVerbale())) {
            localDateVerbaleDiPartenza =
                datiRichiestaIstanzaPl
                    .getDatiCompletiVerbaleDiPartenza()
                    .getDettaglioVerbale()
                    .getDataAccertamento();
          }

          log.debug(
              "CP dataDataEOraAccertamentoVerbalePartenza = "
                  + dataDataEOraAccertamentoVerbalePartenza);

          LocalDate dataAccertamentoVerbale = verbale.getDataAccertamento();

          log.debug("CP dataAccertamentoVerbale = " + dataAccertamentoVerbale);

          if (LabelFdCUtil.checkIfNotNull(localDateVerbaleDiPartenza)) {
            if (localDateVerbaleDiPartenza.isEqual(dataAccertamentoVerbale)
                || localDateVerbaleDiPartenza.isEqual(dataAccertamentoVerbale.minusDays(1))
                || localDateVerbaleDiPartenza.isEqual(dataAccertamentoVerbale.plusDays(1))) {
              stessaDataConVerbaleDiPartenza = true;
            }
          }

          log.debug("CP stessaDataConVerbaleDiPartenza = " + stessaDataConVerbaleDiPartenza);

          String timeStringAccertamentoVerbale = dettaglioVerbale.getOraAccertamento();

          log.debug("CP timeStringAccertamentoVerbale = " + timeStringAccertamentoVerbale);

          if (dataAccertamentoVerbale != null
              && timeStringAccertamentoVerbale != null
              && stessaDataConVerbaleDiPartenza) {
            LocalTime timeAccertamentoVerbale =
                LocalDateTimeUtil.getLocalTimeFromStringHHmm(timeStringAccertamentoVerbale);

            log.debug("CP timeAccertamentoVerbale = " + timeAccertamentoVerbale);

            LocalDateTime dataDataEOraAccertamentoVerbaleIth =
                LocalDateTimeUtil.getLocalDateTimeFromLocalDateAndLocalTime(
                    dataAccertamentoVerbale, timeAccertamentoVerbale);

            log.debug(
                "CP dataDataEOraAccertamentoVerbaleIth = " + dataDataEOraAccertamentoVerbaleIth);

            try {
              dataOk =
                  LocalDateTimeUtil.isSecondInside24hFromFirst(
                      dataDataEOraAccertamentoVerbalePartenza, dataDataEOraAccertamentoVerbaleIth);
            } catch (BusinessException e) {
              // TODO Auto-generated catch block
              log.error("Step 1 Istanze Errore Data codice uno");
            }

            log.debug("CP dataOk in if = " + dataOk);
          }
        }

        log.debug("CP dataOk = " + dataOk);

        log.debug(
            "CP prima di aggiungere in lista = "
                + articoloOk
                + " - "
                + dataOk
                + " - "
                + viaOk
                + " - "
                + veicoloOk);

        if (articoloOk && dataOk && viaOk && veicoloOk) {
          result = true;
          break;
        }
      }
    }
    log.debug("Result Istanze Step 1 " + result);
    return result;
  }

  private DatiRichiestaIstanzaPl inizializzaTuttiVerbali(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl)
      throws BusinessException, ApiException, IOException {
    List<Verbale> listTuttiVerbali = datiRichiestaIstanzaPl.getListTuttiVerbali();
    if (listTuttiVerbali == null
        || (LabelFdCUtil.checkIfNotNull(listTuttiVerbali)
            && LabelFdCUtil.checkEmptyList(listTuttiVerbali))) {
      listTuttiVerbali =
          ServiceLocator.getInstance().getServiziMieiVerbali().getTuttiVerbali(getUtente());
      String numeroVerbalePartenza = datiRichiestaIstanzaPl.getNumeroVerbalePartenza();
      // rimuovere il verbale iniziale e i vebali per cui non posso
      // aprire una istanza
      List<Verbale> listVerbaliFiltrata =
          listTuttiVerbali.stream()
              .filter(
                  elem -> {
                    return !(elem.getNumeroAvviso().equalsIgnoreCase(numeroVerbalePartenza))
                        && !(elem.getStato().equalsIgnoreCase(StatoEnum.ARCHIVIATO.value()))
                        && BigDecimalUtil.isMaggioreBigDecimal(elem.getImporto(), BigDecimal.ZERO);
                  })
              .collect(Collectors.toList());
      datiRichiestaIstanzaPl.setListTuttiVerbali(listVerbaliFiltrata);

    } else {
      log.debug("CP entro in else getVerbaliCompatibili");
      log.debug("CP listVerbali prima = " + listTuttiVerbali.size());
      datiRichiestaIstanzaPl.setListTuttiVerbali(listTuttiVerbali);
    }

    // questo diventa un metodo metodo multi-thread in business
    datiRichiestaIstanzaPl =
        ServiceLocator.getInstance()
            .getServiziMieIstanze()
            .popolaMappaUnicoServizio(datiRichiestaIstanzaPl, getUtente());
    return datiRichiestaIstanzaPl;
  }
}
