package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep1;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AgevolazioneTariffariaRistorazione;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.AnnoScolasticoRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorApiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.RichiestaAgevolazioneStep1Page;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.RichiestaAgevolazioneStep2Page;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form.RichiestaAgevolazioneStep1Form;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIscrizioniPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria.AccettazioneNucleoIseeAnagraficoEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

public class RichiestaAgevolazioneStep1Panel extends BasePanel {

  private static final long serialVersionUID = -4652274627139372877L;

  private Integer anno;
  static final String URL_INPS =
      "https://serviziweb2.inps.it/PassiWeb/jsp/login.jsp?uri=https%3a%2f%2fservizi2.inps.it%2fservizi%2fIseeriforma%2fFrmSelezionaDichiarante.aspx&S=S";

  public RichiestaAgevolazioneStep1Panel(UtenteServiziRistorazione iscrizione, Integer anno) {
    super("agevolazionePanel");
    this.anno = anno;
    createFeedBackPanel();
    fillDati(iscrizione);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    AgevolazioneStep1 step1 = new AgevolazioneStep1();
    UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;

    if (iscritto == null) {
      log.debug("[RichiestaAgevolazioneStep1Panel] iscritto null");
      throw new RestartResponseAtInterceptPageException(ErrorApiPage.class);
    }

    try {
      step1.setAnnoScolastico(getAnnoCorretto(anno));
    } catch (Exception e) {
      step1.setAnnoScolastico(anno);
    }

    step1.setIscrittoSelezionato(iscritto);

    step1 = controllaISEEModi(step1);

    List<NucleoFamiliareComponenteNucleoInner> listaFigliComponenteNucleoIseeModi =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .getFigliComponenteNucleoAttestazioneIseeModi(step1);

    if (step1 == null) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("INPS"));
    }

    List<NucleoFamiliareComponenteNucleoInner> listaNucleoIsee =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .getComponenteNucleoAttestazioneIseeModi(step1);
    List<Residente> listaNucleoAnagrafe = new ArrayList<Residente>();
    try {
      listaNucleoAnagrafe =
          ServiceLocator.getInstance()
              .getServizioDemografico()
              .listaPersoneInAnagrafeDelDemografico(getUtente());
    } catch (BusinessException | ApiException e) {
      log.error("Errore nucleo anagrafe: " + e.getMessage());
    }

    boolean checkNuclei =
        InpsModiHelper.checkNucleoIseeUgualeNucleoAnagrafico(listaNucleoIsee, listaNucleoAnagrafe);
    log.debug("CP checkNuclei agevolazione mensa = " + checkNuclei);

    List<AgevolazioneTariffariaRistorazione> listaFigliPerRichiesta =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .elencoRichiesteAgevolazioni(getUtente(), step1.getAnnoScolastico().longValue());

    listaFigliPerRichiesta =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .incrociaConIseeModi(listaFigliPerRichiesta, listaFigliComponenteNucleoIseeModi);

    listaFigliPerRichiesta =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .setImportoIndicatoreIseeBambino(listaFigliPerRichiesta);

    step1.setListaFigliPerRichiesta(
        selezionaFiglioCorrenteNellaLista(step1, listaFigliPerRichiesta));

    log.debug("CP listaFigliPerRichiesta = " + listaFigliPerRichiesta);

    RichiestaAgevolazioneStep1Form form =
        new RichiestaAgevolazioneStep1Form("form", step1) {

          private static final long serialVersionUID = 4608822545549206359L;

          @Override
          protected void onSubmit() {

            if (LabelFdCUtil.checkIfNotNull(getModelObject().getAccettazioneNuclei())) {
              setResponsePage(new RichiestaAgevolazioneStep2Page(getModelObject()));
            } else {
              error("Attenzione, il campo dichiarazione è richiesto.");
              onError();
            }
          }

          @Override
          protected void onError() {
            log.error("Errore durante step 1 agevolazione mensa");
          }
        };
    form.setOutputMarkupId(true);
    form.setOutputMarkupPlaceholderTag(true);

    ExternalLink linkInps = new ExternalLink("linkInps", URL_INPS);

    Label messaggio = new Label("messaggio", getString("RichiestaIscrizioneForm.messaggio"));

    Double iseeOrd = 0.0;
    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
    String moneyString = "";
    String dataPresentazioneIsee = "";

    boolean checkNotNullFinoAttestazioneIseeModi =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .checkCampiNotNullFinoAttestazioneIseeModi(step1);

    boolean checkNotNullFinoIseeInOrdinario =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .checkNotNullFinoISEEInOrdinarioIseeModi(step1);

    boolean omissioneDifformita = false;

    if (checkNotNullFinoAttestazioneIseeModi) {

      if (LabelFdCUtil.checkIfNotNull(
          step1
              .getAttestazioneIseeModi()
              .getConsultazioneAttestazioneResponse()
              .getConsultazioneAttestazioneResult()
              .getXmlEsitoAttestazioneDecoded()
              .getEsitoAttestazione()
              .getAttestazione()
              .getOmissioneDifformita())) {
        omissioneDifformita = true;
      }
      log.debug("CP omissioneDifformita = " + omissioneDifformita);

      if (checkNotNullFinoIseeInOrdinario) {

        iseeOrd =
            Double.parseDouble(
                step1
                    .getAttestazioneIseeModi()
                    .getConsultazioneAttestazioneResponse()
                    .getConsultazioneAttestazioneResult()
                    .getXmlEsitoAttestazioneDecoded()
                    .getEsitoAttestazione()
                    .getAttestazione()
                    .getOrdinario()
                    .getIsEEOrdinario()
                    .getValori()
                    .getISEE());

        LocalDate dataPresentazioneIseeLocalDate =
            step1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione()
                .getDataPresentazione();

        if (LabelFdCUtil.checkIfNotNull(dataPresentazioneIseeLocalDate)) {
          dataPresentazioneIsee =
              LocalDateUtil.getDataFormatoEuropeo(dataPresentazioneIseeLocalDate);
        }
      }
    }

    BigDecimal iseeOrdinario = BigDecimal.valueOf(iseeOrd);
    moneyString = formatter.format(iseeOrdinario);

    WebMarkupContainer infoIseeConOmissioni = new WebMarkupContainer("infoIseeConOmissioni");
    WebMarkupContainer infoIseeConIseeNonCalcolabile =
        new WebMarkupContainer("infoIseeConIseeNonCalcolabile");

    boolean iseeNonCalcolabile =
        LabelFdCUtil.checkIfNotNull(listaFigliPerRichiesta)
            && listaFigliPerRichiesta.stream()
                .filter(
                    elem ->
                        elem.getIseeNonCalcolabile() != null
                            && elem.getIseeNonCalcolabile() == true)
                .findAny()
                .isPresent();
    log.debug("CP iseeNonCalcolabile= " + iseeNonCalcolabile);

    infoIseeConOmissioni.setVisible(omissioneDifformita);
    infoIseeConOmissioni.setOutputMarkupId(true);
    infoIseeConOmissioni.setOutputMarkupPlaceholderTag(true);
    addOrReplace(infoIseeConOmissioni);

    infoIseeConIseeNonCalcolabile.setVisible(iseeNonCalcolabile);
    infoIseeConIseeNonCalcolabile.setOutputMarkupId(true);
    infoIseeConIseeNonCalcolabile.setOutputMarkupPlaceholderTag(true);
    addOrReplace(infoIseeConIseeNonCalcolabile);

    Label messaggioInfoIseeOrdinario =
        new Label(
            "messaggioInfoIseeOrdinario",
            new StringResourceModel("RichiestaAgevolazionePanel.messaggioInfoIseeOrdinario", this)
                .setParameters(moneyString, dataPresentazioneIsee));
    messaggioInfoIseeOrdinario.setEscapeModelStrings(false);
    addOrReplace(messaggioInfoIseeOrdinario);

    List<NucleoFamiliareComponenteNucleoInner> listaComponentiNucleoIsee =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();
    List<Residente> listaAnagrafe = new ArrayList<Residente>();

    if (checkNuclei) {
      form.getModelObject()
          .setAccettazioneNuclei(AccettazioneNucleoIseeAnagraficoEnum.NUCLEI_COINCIDONO);
    } else {
      if (LabelFdCUtil.checkIfNotNull(listaNucleoIsee)) {

        Comparator<NucleoFamiliareComponenteNucleoInner> comparator =
            Comparator.comparing(
                NucleoFamiliareComponenteNucleoInner::getCodiceFiscale,
                Comparator.nullsLast(Comparator.naturalOrder()));

        List<NucleoFamiliareComponenteNucleoInner> listaIseeOrdinata =
            listaNucleoIsee.stream().sorted(comparator).collect(Collectors.toList());

        listaComponentiNucleoIsee = listaIseeOrdinata;
      }

      if (LabelFdCUtil.checkIfNotNull(listaNucleoAnagrafe)) {
        listaAnagrafe = listaNucleoAnagrafe;
      }
    }

    WebMarkupContainer containerControlloNuclei =
        new WebMarkupContainer("containerControlloNuclei");
    containerControlloNuclei.setOutputMarkupId(true);
    containerControlloNuclei.setOutputMarkupPlaceholderTag(true);
    containerControlloNuclei.setVisible(!checkNuclei);
    addOrReplace(containerControlloNuclei);

    ListView<NucleoFamiliareComponenteNucleoInner> listViewNucleoIsee =
        new ListView<NucleoFamiliareComponenteNucleoInner>(
            "listaNucleoIsee", listaComponentiNucleoIsee) {

          private static final long serialVersionUID = 3847413683542396710L;

          @Override
          protected void populateItem(ListItem<NucleoFamiliareComponenteNucleoInner> itemIsee) {
            final NucleoFamiliareComponenteNucleoInner membroIsee = itemIsee.getModelObject();

            String nomeCognome = "";
            if (PageUtil.isStringValid(membroIsee.getNome())) {
              nomeCognome = membroIsee.getNome().concat(" ");
            }
            if (PageUtil.isStringValid(membroIsee.getCognome())) {
              nomeCognome = nomeCognome.concat(membroIsee.getCognome());
            }

            Label nominativoIsee = new Label("nominativoIsee", nomeCognome);
            itemIsee.addOrReplace(nominativoIsee);
          }
        };
    containerControlloNuclei.addOrReplace(listViewNucleoIsee);

    ListView<Residente> listViewNucleoAnagrafe =
        new ListView<Residente>("listaNucleoAnagrafe", listaAnagrafe) {

          private static final long serialVersionUID = 5607749155494324570L;

          @Override
          protected void populateItem(ListItem<Residente> itemIsee) {
            final Residente membroAnagrafe = itemIsee.getModelObject();

            String nomeCognome = "";

            if (LabelFdCUtil.checkIfNotNull(membroAnagrafe.getRdfsLabel())) {
              nomeCognome = membroAnagrafe.getRdfsLabel();
            }

            Label nominativoAnagrafe = new Label("nominativoAnagrafe", nomeCognome);
            itemIsee.addOrReplace(nominativoAnagrafe);
          }
        };
    containerControlloNuclei.addOrReplace(listViewNucleoAnagrafe);

    SiNoDropDownChoice accettazioneNucleiSiNo =
        new SiNoDropDownChoice(
            "accettazioneNucleiSiNo",
            new PropertyModel<String>(form.getModelObject(), "accettazioneNucleiSiNo"));
    accettazioneNucleiSiNo.setLabel(Model.of("Dichiarazione"));
    accettazioneNucleiSiNo.setOutputMarkupId(true);
    accettazioneNucleiSiNo.setOutputMarkupPlaceholderTag(true);
    accettazioneNucleiSiNo.setRequired(true);

    accettazioneNucleiSiNo.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 4810258971796981910L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            log.debug(
                "CP click scelta accettazioneNucleiSiNo = " + accettazioneNucleiSiNo.getValue());

            if (accettazioneNucleiSiNo.getValue().equalsIgnoreCase("0")) {
              form.getModelObject()
                  .setAccettazioneNuclei(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA);
            } else {
              form.getModelObject()
                  .setAccettazioneNuclei(AccettazioneNucleoIseeAnagraficoEnum.NON_ACCETTA);
            }
          }
        });

    containerControlloNuclei.addOrReplace(accettazioneNucleiSiNo);

    WebMarkupContainer containerAnno = new WebMarkupContainer("containerAnno");
    containerAnno.addOrReplace(creaFormSceltaAnnoScolastico(iscritto));
    containerAnno.setOutputMarkupId(true);
    containerAnno.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerAnno);

    if (step1.getAttestazioneIseeModi() == null) {
      form.setVisible(false);
      containerAnno.setVisible(false);

      warn(
          "Per poter proseguire con la richiesta di agevolazione tariffaria \u00E8 necessario presentare la dichiarazione ISEE per l'anno "
              + LocalDate.now().getYear());

      linkInps.setVisible(true);
      messaggio.setVisible(true);

      messaggioInfoIseeOrdinario.setVisible(false);

      containerControlloNuclei.setVisible(false);

    } else if (step1.getAttestazioneIseeModi() != null && !checkNuclei) {
      form.setVisible(true);
      containerAnno.setVisible(true);

      containerControlloNuclei.setVisible(true);

      messaggioInfoIseeOrdinario.setVisible(true);
    } else if (step1.getAttestazioneIseeModi() != null && checkNuclei) {
      form.setVisible(true);
      containerAnno.setVisible(true);

      linkInps.setVisible(false);
      messaggio.setVisible(false);

      messaggioInfoIseeOrdinario.setVisible(true);

      containerControlloNuclei.setVisible(false);

    } else if (step1.getAttestazioneIseeModi() != null
        && listaFigliPerRichiesta.isEmpty()
        && checkNuclei) {
      form.setVisible(false);
      containerAnno.setVisible(false);

      error("Nessun bambino presente in ISEE per l'anno selezionato.");

      linkInps.setVisible(true);
      messaggio.setVisible(true);

      messaggioInfoIseeOrdinario.setVisible(false);

      containerControlloNuclei.setVisible(false);

    } else if (step1.getAttestazioneIseeModi() != null
        && step1.getAccettazioneNuclei() != null
        && step1.getAccettazioneNuclei().equals(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA)) {
      form.setVisible(true);
      containerAnno.setVisible(true);

      linkInps.setVisible(false);
      messaggio.setVisible(false);

      messaggioInfoIseeOrdinario.setVisible(true);

      containerControlloNuclei.setVisible(false);

      warn(
          "Hai dichiarato che il nucleo nella dichiarazione ISEE è corretto. La tua autodichiarazione verrà salvata per i fini previsti dalla legge.");

    } else if (step1.getAttestazioneIseeModi() != null
        && step1.getAccettazioneNuclei() != null
        && step1.getAccettazioneNuclei().equals(AccettazioneNucleoIseeAnagraficoEnum.NON_ACCETTA)) {
      form.setVisible(false);
      containerAnno.setVisible(false);

      linkInps.setVisible(false);
      messaggio.setVisible(false);

      messaggioInfoIseeOrdinario.setVisible(false);

      containerControlloNuclei.setVisible(false);

      warn(
          "Hai dichiarato che il nucleo nella dichiarazione ISEE non è corretto. E' necessario modificare la dichiarazione ISEE prima di poter presentare una nuova richiesta. Per maggiori informazioni vai sul sito di INPS.");
    }

    if (iseeNonCalcolabile) {
      form.setVisible(false);
    }

    form.add(creaBottoneAnnulla());
    add(form);
    add(messaggio);
    add(linkInps);
  }

  private DropDownChoice<Integer> creaFormSceltaAnnoScolastico(UtenteServiziRistorazione iscritto) {
    DropDownChoice<Integer> dropdown =
        new DropDownChoice<Integer>(
            "comboAnno", Model.of(anno), getListaAnniScolastici(), new AnnoScolasticoRenderer());

    dropdown.add(
        new FormComponentUpdatingBehavior() {

          private static final long serialVersionUID = -8418621730334667906L;

          @Override
          protected void onUpdate() {
            Integer annoSelezionato = dropdown.getModelObject();
            RichiestaAgevolazioneStep1Page page =
                new RichiestaAgevolazioneStep1Page(iscritto, annoSelezionato);
            setResponsePage(page);
          }

          @Override
          protected void onError(RuntimeException ex) {
            super.onError(ex);
          }
        });

    dropdown.setRequired(true);

    return dropdown;
  }

  private AgevolazioneStep1 controllaISEEModi(AgevolazioneStep1 step1) {

    log.debug("controllaISEEModi");

    String tipoIndicatore = "Ordinario";
    ConsultazioneAttestazioneCF200 attestazioneModi =
        ServiceLocator.getInstance()
            .getServiziRistorazione()
            .getAttestazioneISEEModi(getUtente(), tipoIndicatore);

    if (LabelFdCUtil.checkIfNotNull(attestazioneModi)) {

      ConsultazioneDichiarazioneCF200 dichiarazioneModi =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getDichiarazioneISEEModi(getUtente().getCodiceFiscaleOperatore(), tipoIndicatore);

      step1.setDichiarazioneIseeModi(dichiarazioneModi);
    }

    step1.setAttestazioneIseeModi(attestazioneModi);

    return step1;
  }

  private Integer getAnnoCorretto(Integer annoCorrente) throws BusinessException {
    if (LocalDateUtil.isTodayAfterDateString("30/06/" + annoCorrente)) {
      return annoCorrente;
    }
    return annoCorrente - 1;
  }

  private List<Integer> getListaAnniScolastici() {

    Integer annoCorrente = LocalDateUtil.today().getYear();
    List<Integer> listaAnni = new ArrayList<>();

    try {
      listaAnni.add(getAnnoCorretto(annoCorrente));
    } catch (BusinessException e) {
      log.error("Errore parsing date agevolazione tariffaria", e);
    }
    return listaAnni;
  }

  private List<AgevolazioneTariffariaRistorazione> selezionaFiglioCorrenteNellaLista(
      AgevolazioneStep1 step1, List<AgevolazioneTariffariaRistorazione> lista) {
    List<AgevolazioneTariffariaRistorazione> ret = new ArrayList<>();

    if (lista != null) {
      for (AgevolazioneTariffariaRistorazione atr : lista) {
        if (atr.getCodiceFiscale()
            .equalsIgnoreCase(step1.getIscrittoSelezionato().getCodiceFiscale())) {
          atr.setSelezionato(true);
        }
        ret.add(atr);
      }
    }
    return ret;
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = 1802532275878600465L;

      @Override
      public void onClick() {
        setResponsePage(new RiepilogoIscrizioniPage());
      }
    });
  }
}
