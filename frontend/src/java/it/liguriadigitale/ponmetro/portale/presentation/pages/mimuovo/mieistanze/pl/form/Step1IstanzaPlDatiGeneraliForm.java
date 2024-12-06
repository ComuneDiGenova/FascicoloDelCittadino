package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.IstanzePlDatiMotivoSummaryRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield.FdCLocalDateTextfield;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.TelefonoFissoCellulareValidator;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Motivazione;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class Step1IstanzaPlDatiGeneraliForm extends AbstracFrameworkForm<DatiRichiestaIstanzaPl> {

  private static final long serialVersionUID = -7349674609304452360L;

  private DropDownChoice<DatiMotivoSummary> comboMotivi;

  private FdCLocalDateTextfield nascitaData;
  public List<DatiMotivoSummary> listDatiMotivoSummary;

  private WebMarkupContainer containerRiferimentoLeggeMotivoSelezionato;
  private NotEmptyLabel riferimentoLeggeMotivoSelezionato;

  public DropDownChoice<DatiMotivoSummary> getComboMotivi() {
    return comboMotivi;
  }

  public void setComboMotivi(DropDownChoice<DatiMotivoSummary> comboMotivi) {
    this.comboMotivi = comboMotivi;
  }

  public FdCLocalDateTextfield getNascitaData() {
    return nascitaData;
  }

  public void setNascitaData(FdCLocalDateTextfield nascitaData) {
    this.nascitaData = nascitaData;
  }

  public List<DatiMotivoSummary> getListDatiMotivoSummary() {
    return listDatiMotivoSummary;
  }

  public void setListDatiMotivoSummary(List<DatiMotivoSummary> listDatiMotivoSummary) {
    this.listDatiMotivoSummary = listDatiMotivoSummary;
  }

  public Step1IstanzaPlDatiGeneraliForm(String id, DatiRichiestaIstanzaPl model) {
    super(id, model);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    addElementiForm();
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public void addElementiForm() {

    DatiRichiestaIstanzaPl datiRichiestaIstanza = getModelObject();
    Utente utente = datiRichiestaIstanza.getUtente();

    // datiRichiestaIstanzaPl.setRichiedenteCognome(getUtente().getCognome());
    // datiRichiestaIstanzaPl.setRichiedenteNome(getUtente().getNome());
    // datiRichiestaIstanzaPl.setRichiedenteCf(getUtente().getCodiceFiscaleOperatore());
    //
    // datiRichiestaIstanzaPl.setRichiedenteEmail("mail");
    // datiRichiestaIstanzaPl.setNascitaData(getUtente().getDataDiNascita());
    // datiRichiestaIstanzaPl.setNascitaComune(getUtente().getLuogoNascita());
    // datiRichiestaIstanzaPl.setResidenzaComune("comune");
    // datiRichiestaIstanzaPl.setResidenzaVia("via");
    // datiRichiestaIstanzaPl.setResidenzaNumeroCivico("1");

    // private DettaglioVerbale verbaleDiPartenza;
    // private DatiMotivoSummary datiMotivoSummary;

    // get static values
    String cognome = utente.getCognome();
    String nome = utente.getNome();
    String cf = utente.getCodiceFiscaleOperatore();
    LocalDate dataDiNascita = utente.getDataDiNascita();

    // set model object - se disabilito
    getModelObject().setRichiedenteCognome(cognome);
    getModelObject().setRichiedenteNome(nome);
    getModelObject().setRichiedenteCf(cf);
    getModelObject().setNascitaData(dataDiNascita);

    /* FRR PER PROVA PRECOMPILATI - ALCUNI DA LASCIARE */
    if (LabelFdCUtil.checkIfNull(getModelObject().getNascitaComune())) {
      getModelObject().setNascitaComune(utente.getLuogoNascita());
    }
    if (LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente())
        && LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente().getCpvHasAddress())) {
      if (LabelFdCUtil.checkIfNull(getModelObject().getResidenzaComune())) {
        getModelObject()
            .setResidenzaComune(utente.getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
      }
      if (LabelFdCUtil.checkIfNull(getModelObject().getResidenzaNumeroCivicoSoloNumero())) {
        if (LabelFdCUtil.checkIfNotNull(
            utente.getDatiCittadinoResidente().getCpvHasAddress().getClvStreenNumberOnly())) {
          getModelObject()
              .setResidenzaNumeroCivicoSoloNumero(
                  Integer.parseInt(
                      utente
                          .getDatiCittadinoResidente()
                          .getCpvHasAddress()
                          .getClvStreenNumberOnly()));
        }
      }

      if (LabelFdCUtil.checkIfNull(getModelObject().getResidenzaNumeroCivicoEsponente())) {
        getModelObject()
            .setResidenzaNumeroCivicoEsponente(
                utente.getDatiCittadinoResidente().getCpvHasAddress().getClvStreetNumberExponent());
      }

      if (LabelFdCUtil.checkIfNull(getModelObject().getResidenzaVia())) {
        String toponomy =
            utente.getDatiCittadinoResidente().getCpvHasAddress().getClvToponymQualifier();
        String officialStreet =
            utente.getDatiCittadinoResidente().getCpvHasAddress().getClvOfficialStreetName();

        getModelObject().setResidenzaVia(toponomy.concat(" ").concat(officialStreet));
      }

      if (LabelFdCUtil.checkIfNull(getModelObject().getResidenzaInterno())) {
        if (LabelFdCUtil.checkIfNotNull(
            utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFlatNumberOnly())) {
          getModelObject()
              .setResidenzaInterno(
                  Integer.parseInt(
                      utente
                          .getDatiCittadinoResidente()
                          .getCpvHasAddress()
                          .getClvFlatNumberOnly()));
        }
      }

      if (LabelFdCUtil.checkIfNull(getModelObject().getResidenzaInternoEsponente())) {
        getModelObject()
            .setResidenzaInternoEsponente(
                utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFlatExponent());
      }

      if (LabelFdCUtil.checkIfNull(getModelObject().getResidenzaColore())) {
        getModelObject()
            .setResidenzaColore(
                utente.getDatiCittadinoResidente().getCpvHasAddress().getClvStreetNumberColor());
      }
    }

    if (LabelFdCUtil.checkIfNull(getModelObject().getRichiedenteTelefono())
        && LabelFdCUtil.checkIfNotNull(utente.getMobile())) {
      getModelObject().setRichiedenteTelefono(utente.getMobile());
    }

    if (LabelFdCUtil.checkIfNull(getModelObject().getRichiedenteEmail())
        && LabelFdCUtil.checkIfNotNull(utente.getMail())) {
      getModelObject().setRichiedenteEmail(utente.getMail());
    }

    // getModelObject().setResidenzaComune(utente.getDatiCittadinoResidente()
    // == null ? "" : "Genova");
    // getModelObject().setResidenzaVia(utente.getDatiCittadinoResidente()
    // == null
    // || utente.getDatiCittadinoResidente().getCpvHasAddress() == null ? ""
    // :
    // utente.getDatiCittadinoResidente().getCpvHasAddress().getClvOfficialStreetName());
    // getModelObject().setResidenzaNumeroCivico(utente.getDatiCittadinoResidente()
    // == null
    // || utente.getDatiCittadinoResidente().getCpvHasAddress() == null ? ""
    // :
    // utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFlatNumber());
    // getModelObject().setRichiedenteTelefono(utente.getMobile() == null ?
    // "11111111111111" : utente.getMobile());
    // getModelObject()
    // .setRichiedenteEmail(utente.getMail() == null ?
    // "f.ferrando2@liguriadigitale.it" : utente.getMail());

    // set form object
    TextField richiedenteCognome =
        new TextField(
            "richiedenteCognome",
            new PropertyModel<String>(getModelObject(), "richiedenteCognome"));
    richiedenteCognome.setRequired(true);
    richiedenteCognome.setEnabled(false);
    richiedenteCognome.setLabel(Model.of("Cognome richiedente"));

    addOrReplace(richiedenteCognome);

    TextField richiedenteNome =
        new TextField(
            "richiedenteNome", new PropertyModel<String>(getModelObject(), "richiedenteNome"));
    richiedenteNome.setRequired(true);
    richiedenteNome.setEnabled(false);
    richiedenteNome.setLabel(Model.of("Nome richiedente"));
    addOrReplace(richiedenteNome);

    TextField nascitaComune =
        new TextField(
            "nascitaComune", new PropertyModel<String>(getModelObject(), "nascitaComune"));
    nascitaComune.setRequired(true);
    nascitaComune.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setNascitaComune(nascitaComune.getValue());
          }
        });
    addOrReplace(nascitaComune);
    nascitaComune.setLabel(Model.of("Comune nascita"));

    addOrReplace(createTextboxDataNascita());

    TextField richiedenteCf =
        new TextField(
            "richiedenteCf", new PropertyModel<String>(getModelObject(), "richiedenteCf"));
    richiedenteCf.setRequired(true);
    richiedenteCf.setEnabled(false);
    richiedenteCf.setLabel(Model.of("Codice fiscale richiedente"));
    addOrReplace(richiedenteCf);

    TextField residenzaComune =
        new TextField(
            "residenzaComune", new PropertyModel<String>(getModelObject(), "residenzaComune"));
    residenzaComune.setRequired(true);
    residenzaComune.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setResidenzaComune(residenzaComune.getValue());
          }
        });
    residenzaComune.setLabel(Model.of("Comune residenza"));
    addOrReplace(residenzaComune);

    TextField residenzaVia =
        new TextField("residenzaVia", new PropertyModel<String>(getModelObject(), "residenzaVia"));
    residenzaVia.setRequired(true);
    residenzaVia.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setResidenzaVia(residenzaVia.getValue());
          }
        });
    residenzaVia.setLabel(Model.of("Via residenza"));
    addOrReplace(residenzaVia);

    NumberTextField residenzaNumeroCivico =
        new NumberTextField(
            "residenzaNumeroCivicoSoloNumero",
            new PropertyModel<Integer>(getModelObject(), "residenzaNumeroCivicoSoloNumero"));
    residenzaNumeroCivico.setRequired(true);
    residenzaNumeroCivico.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject()
                .setResidenzaNumeroCivicoSoloNumero(
                    Integer.parseInt(residenzaNumeroCivico.getValue()));
          }
        });
    residenzaNumeroCivico.setLabel(Model.of("Numero civico"));

    residenzaNumeroCivico.setMinimum(0);

    addOrReplace(residenzaNumeroCivico);

    TextField residenzaNumeroCivicoEsponente =
        new TextField(
            "residenzaNumeroCivicoEsponente",
            new PropertyModel<String>(getModelObject(), "residenzaNumeroCivicoEsponente"));
    residenzaNumeroCivicoEsponente.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject()
                .setResidenzaNumeroCivicoEsponente(residenzaNumeroCivicoEsponente.getValue());
          }
        });
    residenzaNumeroCivicoEsponente.setLabel(Model.of("Civico esponente"));
    addOrReplace(residenzaNumeroCivicoEsponente);

    NumberTextField residenzaInterno =
        new NumberTextField(
            "residenzaInterno", new PropertyModel<Integer>(getModelObject(), "residenzaInterno"));
    residenzaInterno.setRequired(true);
    residenzaInterno.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            if (LabelFdCUtil.checkIfNotNull(residenzaInterno.getValue())) {
              getModelObject().setResidenzaInterno(Integer.valueOf(residenzaInterno.getValue()));
            }
          }
        });
    residenzaInterno.setLabel(Model.of("Interno"));

    residenzaInterno.setMinimum(0);

    addOrReplace(residenzaInterno);

    TextField residenzaInternoEsponente =
        new TextField(
            "residenzaInternoEsponente",
            new PropertyModel<String>(getModelObject(), "residenzaInternoEsponente"));
    residenzaInternoEsponente.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setResidenzaInternoEsponente(residenzaInternoEsponente.getValue());
          }
        });
    residenzaInternoEsponente.setLabel(Model.of("Interno esponente"));
    addOrReplace(residenzaInternoEsponente);

    TextField residenzaColore =
        new TextField(
            "residenzaColore", new PropertyModel<String>(getModelObject(), "residenzaColore"));
    residenzaColore.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setResidenzaColore(residenzaColore.getValue());
          }
        });
    residenzaColore.setLabel(Model.of("Colore"));
    addOrReplace(residenzaColore);

    TextField telefono =
        new TextField(
            "richiedenteTelefono",
            new PropertyModel<String>(getModelObject(), "richiedenteTelefono"));
    telefono.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -8027829385285156101L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setRichiedenteTelefono(telefono.getValue());
          }
        });

    getModelObject().setRichiedenteTelefono(telefono.getValue());

    telefono.setRequired(true);
    telefono.setLabel(Model.of("Telefono"));
    telefono.add(new TelefonoFissoCellulareValidator());
    telefono.add(StringValidator.minimumLength(9));
    telefono.add(StringValidator.maximumLength(50));
    addOrReplace(telefono);

    EmailTextField emailRichiedente =
        new EmailTextField(
            "richiedenteEmail", new PropertyModel<String>(getModelObject(), "richiedenteEmail"));
    emailRichiedente.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {
          private static final long serialVersionUID = -1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            getModelObject().setRichiedenteEmail(emailRichiedente.getValue());
          }
        });

    emailRichiedente.setRequired(true);
    emailRichiedente.setLabel(Model.of("Email"));
    addOrReplace(emailRichiedente);

    TextField numeroVerbalePartenza =
        new TextField(
            "numeroVerbalePartenza",
            new PropertyModel<String>(getModelObject(), "numeroVerbalePartenza"));
    numeroVerbalePartenza.setRequired(true);
    numeroVerbalePartenza.setEnabled(false);
    numeroVerbalePartenza.setLabel(Model.of("Numero verbale"));
    addOrReplace(numeroVerbalePartenza);

    addOrReplace(createAndGetComboMotivi());

    containerRiferimentoLeggeMotivoSelezionato =
        new WebMarkupContainer("containerRiferimentoLeggeMotivoSelezionato");
    containerRiferimentoLeggeMotivoSelezionato.setOutputMarkupId(true);
    containerRiferimentoLeggeMotivoSelezionato.setOutputMarkupPlaceholderTag(true);

    // String riferimentoLeggeMotivoSelezionato =
    // getModelObject().getRiferimentoLeggeCodiceHermesMotivoSelezionato();
    // labelRiferimentoLeggeMotivoSelezionato = new
    // Label("riferimentoLeggeMotivoSelezionato",
    // riferimentoLeggeMotivoSelezionato == null ? "-" :
    // riferimentoLeggeMotivoSelezionato);
    // log.debug("CP riferimentoLeggeMotivoSelezionato = " +
    // riferimentoLeggeMotivoSelezionato);
    // labelRiferimentoLeggeMotivoSelezionato.setVisible(riferimentoLeggeMotivoSelezionato
    // != null);

    String descrizione = "";
    if (getModelObject().getDatiMotivoSummary() != null) {
      descrizione = getModelObject().getDatiMotivoSummary().getRiferimentoLegge();
    }

    log.debug("CP descrizione = " + riferimentoLeggeMotivoSelezionato);

    riferimentoLeggeMotivoSelezionato =
        new NotEmptyLabel("riferimentoLeggeMotivoSelezionato", descrizione);
    riferimentoLeggeMotivoSelezionato.setOutputMarkupId(true);
    riferimentoLeggeMotivoSelezionato.setOutputMarkupPlaceholderTag(true);
    containerRiferimentoLeggeMotivoSelezionato.addOrReplace(riferimentoLeggeMotivoSelezionato);

    // labelRiferimentoLeggeMotivoSelezionato.setVisible(PageUtil.isStringValid(prova));

    containerRiferimentoLeggeMotivoSelezionato.setVisible(PageUtil.isStringValid(descrizione));
    addOrReplace(containerRiferimentoLeggeMotivoSelezionato);

    Label noncisonomotiviselezionabili =
        new Label(
            "noncisonomotiviselezionabili",
            "Motivi non selezionabili: potresti avere delle altre istanze per questo verbale.");
    noncisonomotiviselezionabili.setVisible(
        listDatiMotivoSummary == null || listDatiMotivoSummary.isEmpty());
    addOrReplace(noncisonomotiviselezionabili);

    //		targaErrataPanel = new TargaErrataPanel("targaErrataPanel",datiRichiestaIstanza);
    //		targaErrataPanel.setOutputMarkupId(true);
    //		targaErrataPanel.setOutputMarkupPlaceholderTag(true);
    //
    //	targaErrataPanel.setVisible("36".equalsIgnoreCase(comboMotivi.getModelObject().getCodice()));
    //		addOrReplace(targaErrataPanel);
    //
    //
    //		nonProprietarioPanel = new
    // NonProprietarioPanel("nonProprietarioPanel",datiRichiestaIstanza);
    //		nonProprietarioPanel.setOutputMarkupId(true);
    //		nonProprietarioPanel.setOutputMarkupPlaceholderTag(true);
    //
    //	nonProprietarioPanel.setVisible("32".equalsIgnoreCase(comboMotivi.getModelObject().getCodice()));
    //		addOrReplace(nonProprietarioPanel);

  }

  private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  private DatiMotivoSummary buildConDescrizioneCorta(DatiMotivoSummary datiMotivoSummary) {
    if (datiMotivoSummary == null) {
      datiMotivoSummary = new DatiMotivoSummary();
    }
    Integer lunghezzaTesto = datiMotivoSummary.getDescrizione().length();
    datiMotivoSummary.setDescrizione(
        datiMotivoSummary.getDescrizione().substring(0, lunghezzaTesto) + "... ");
    return datiMotivoSummary;
  }

  private List<DatiMotivoSummary> getMotiviBySerieOrArticoli(
      DatiRichiestaIstanzaPl datiRichiestaIstanzaPl) {

    String serie = datiRichiestaIstanzaPl.getSerieVerbalePartenza();
    List<String> articoliNon180 = datiRichiestaIstanzaPl.getArticoliVerbalePartenzaNonArt180();
    log.debug(
        "listaDatiMotivoSummary: getMotiviBySerieOrArticoli, articoliNon180: " + articoliNon180);
    List<String> articoli180 = datiRichiestaIstanzaPl.getArticoliVerbalePartenzaArt180();
    log.debug("listaDatiMotivoSummary: getMotiviBySerieOrArticoli, articoli180: " + articoli180);

    List<DatiMotivoSummary> listaDatiMotivoSummary = new ArrayList<>();
    try {
      log.debug("listaDatiMotivoSummary: getMotiviBySerieOrArticoli");

      // se nella lista articoli c'e' 180 lo devo rimuovere e chiamarlo
      // solo per lui
      if (articoli180 != null && !articoli180.isEmpty()) {
        try {
          listaDatiMotivoSummary =
              ServiceLocator.getInstance()
                  .getServiziSupportoIstanzeVerbaliPL()
                  .getElencoMotiviUgualiASummary(serie, articoli180);
        } catch (RestartResponseAtInterceptPageException | ApiException | BusinessException e) {
          log.error("listaDatiMotivoSummary: errore " + e.getMessage());
        }
      }
      if (articoliNon180 != null && !articoliNon180.isEmpty()) {
        try {
          listaDatiMotivoSummary.addAll(
              ServiceLocator.getInstance()
                  .getServiziSupportoIstanzeVerbaliPL()
                  .getElencoMotiviUgualiASummary(serie, articoliNon180));
        } catch (RestartResponseAtInterceptPageException | ApiException | BusinessException e) {
          log.error("listaDatiMotivoSummary: errore " + e.getMessage());
        }
        listaDatiMotivoSummary.addAll(
            ServiceLocator.getInstance()
                .getServiziSupportoIstanzeVerbaliPL()
                .getElencoMotiviDiversiDaSummary(serie, Arrays.asList("180")));
        // listaDatiMotivoSummary.addAll(ServiceLocator.getInstance()
        // .getServiziSupportoIstanzeVerbaliPL()
        // .getElencoMotiviDiversiDaSummary(serie,
        // Arrays.asList("180")));
      }

      // rimuovi duplicati summary
      listaDatiMotivoSummary =
          listaDatiMotivoSummary.stream()
              .filter(ithitem -> ithitem != null)
              .filter(distinctByKey(ithitem -> ithitem.getCodice()))
              // .map(ithitem -> buildConDescrizioneCorta(ithitem))
              .collect(Collectors.toList());

      // Se nel verbale di partenza c'e' una istanza, rimuovi questo
      // motivo
      // dalla combo dei motivi
      // selezionabili
      List<Istanza> istanzeGiaInEssere = datiRichiestaIstanzaPl.getIstanzeVerbalePartenza();
      if (istanzeGiaInEssere != null) {
        for (Istanza istanza : istanzeGiaInEssere) {
          for (Motivazione motiv : istanza.getMotivazioni()) {
            if (motiv != null && motiv.getCodice() != null) {
              listaDatiMotivoSummary =
                  listaDatiMotivoSummary.stream()
                      .filter(
                          ithitem ->
                              ithitem.getCodice() != null
                                  && !ithitem.getCodice().equalsIgnoreCase(motiv.getCodice()))
                      .collect(Collectors.toList());
            }
          }
        }
      }
      log.debug("listaDatiMotivoSummary: " + listaDatiMotivoSummary.size());
    } catch (RestartResponseAtInterceptPageException | ApiException | BusinessException e) {
      log.error("listaDatiMotivoSummary: errore " + e.getMessage());
    }

    log.debug("listaDatiMotivoSummary: " + listaDatiMotivoSummary);

    return listaDatiMotivoSummary;
  }

  public FdCLocalDateTextfield createTextboxDataNascita() {
    nascitaData =
        new FdCLocalDateTextfield("nascitaData", Model.of(getModelObject().getNascitaData()));
    nascitaData.setEnabled(false);
    return nascitaData;
  }

  @SuppressWarnings("unchecked")
  private DropDownChoice<DatiMotivoSummary> createAndGetComboMotivi() {
    IstanzePlDatiMotivoSummaryRender choiceRenderer = new IstanzePlDatiMotivoSummaryRender();
    DatiMotivoSummary datiMotivoSummary = getModelObject().getDatiMotivoSummary();
    log.debug(
        "getCodiceHermesMotivoSelezionato()= "
            + getModelObject().getCodiceHermesMotivoSelezionato());
    datiMotivoSummary.setCodice(getModelObject().getCodiceHermesMotivoSelezionato());

    listDatiMotivoSummary = this.getMotiviBySerieOrArticoli(getModelObject());
    getModelObject().setListDatiMotivoSummarySelezionabili(listDatiMotivoSummary);

    comboMotivi =
        new DropDownChoice<DatiMotivoSummary>(
            "comboMotivi", Model.of(datiMotivoSummary), listDatiMotivoSummary, choiceRenderer) {

          private static final long serialVersionUID = 515503103037695468L;

          @Override
          protected String getNullKeyDisplayValue() {
            return "Sceglierne uno";
          }
        };

    comboMotivi.setMarkupId("comboMotivi");
    comboMotivi.add(createBehaviorComboMotivi());

    comboMotivi.setOutputMarkupPlaceholderTag(true);
    comboMotivi.setOutputMarkupId(true);

    comboMotivi.setNullValid(true);

    comboMotivi.setVisible(listDatiMotivoSummary != null && !listDatiMotivoSummary.isEmpty());
    return comboMotivi;
  }

  public DatiMotivoSummary getDatiMotivoSummarySelezionato() {
    return comboMotivi.getModelObject();
  }

  private AjaxFormComponentUpdatingBehavior createBehaviorComboMotivi() {
    return new AjaxFormComponentUpdatingBehavior("change") {
      private static final long serialVersionUID = -1L;

      private Log log = LogFactory.getLog(getClass());

      @Override
      protected void onUpdate(AjaxRequestTarget target) {
        DatiMotivoSummary datiMotivoSummary = comboMotivi.getModelObject();
        log.debug("DatiMotivoSummary model: " + datiMotivoSummary);

        getModelObject().setDatiMotivoSummary(datiMotivoSummary, true);
        log.debug(
            "createBehaviorComboMotivi() " + getModelObject().getCodiceHermesMotivoSelezionato());

        containerRiferimentoLeggeMotivoSelezionato.setVisible(true);

        //
        //	targaErrataPanel.setVisible("36".equalsIgnoreCase(comboMotivi.getModelObject().getCodice()));
        //
        //				if(!"36".equalsIgnoreCase(comboMotivi.getModelObject().getCodice())) {
        //					//targa
        //					getModelObject().setMiaMacchina("Sì");
        //					getModelObject().setMarca(null);
        //					getModelObject().setModello(null);
        //
        //				}
        //
        //
        //	nonProprietarioPanel.setVisible("32".equalsIgnoreCase(comboMotivi.getModelObject().getCodice()));
        //
        //				if(!"32".equalsIgnoreCase(comboMotivi.getModelObject().getCodice())) {
        //					//proprietario
        //					getModelObject().setMaiMia("Sì");
        //					getModelObject().setDataInizioProprieta(null);
        //					getModelObject().setDataFineProprieta(null);
        //
        //				}

        target.add(
            containerRiferimentoLeggeMotivoSelezionato,
            riferimentoLeggeMotivoSelezionato,
            Step1IstanzaPlDatiGeneraliForm.this);
      }
    };
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    String descrizione = getModelObject().getRiferimentoLeggeCodiceHermesMotivoSelezionato();

    riferimentoLeggeMotivoSelezionato =
        new NotEmptyLabel("riferimentoLeggeMotivoSelezionato", descrizione);
    riferimentoLeggeMotivoSelezionato.setOutputMarkupId(true);
    riferimentoLeggeMotivoSelezionato.setOutputMarkupPlaceholderTag(true);
    containerRiferimentoLeggeMotivoSelezionato.addOrReplace(riferimentoLeggeMotivoSelezionato);
  }
}
