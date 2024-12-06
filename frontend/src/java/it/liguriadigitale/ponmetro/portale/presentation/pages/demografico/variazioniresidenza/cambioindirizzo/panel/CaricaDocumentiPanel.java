package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegati;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegatiImmigrazione;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.VariazioniDiResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.CambioIndirizzoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form.CaricaDocumentiForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.RichiestaResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.DocumentoPratica;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDichiarazionePrecompilataResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDichiarazionePrecompilataResponseGenericResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class CaricaDocumentiPanel extends BasePanel {

  private static final long serialVersionUID = 7720432706161157815L;

  private CaricaDocumentiForm formCaricaDocumenti = null;

  private Integer index;

  private VariazioniResidenza variazione;

  private FeedbackPanel feedbackPanel;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private Integer minimoDocumentiDaCaricare;

  private Integer massimoDocumentiDaCaricare;

  private WebMarkupContainer alertMinimoDocumentiDaCaricare;

  public CaricaDocumentiPanel(String id, Integer index, VariazioniResidenza variazioniResidenza) {
    super(id);

    setOutputMarkupId(true);

    this.index = index;
    this.setVariazione(variazioniResidenza);
    this.feedbackPanel = createFeedBackStep4Panel();

    fillDati(variazioniResidenza);
  }

  @Override
  public void fillDati(Object dati) {
    VariazioniResidenza variazioniResidenza = (VariazioniResidenza) dati;

    alertMinimoDocumentiDaCaricare = new WebMarkupContainer("alertMinimoDocumentiDaCaricare");
    alertMinimoDocumentiDaCaricare.setOutputMarkupId(true);
    alertMinimoDocumentiDaCaricare.setOutputMarkupPlaceholderTag(true);
    alertMinimoDocumentiDaCaricare.setVisible(false);
    addOrReplace(alertMinimoDocumentiDaCaricare);

    formCaricaDocumenti = new CaricaDocumentiForm("form", variazioniResidenza, feedbackPanel);

    String identificativoPratica =
        getString("CaricaDocumentiPanel.idPratica")
            .concat(" ")
            .concat(String.valueOf(variazioniResidenza.getIdPratica()));
    Label idPratica = new Label("idPratica", identificativoPratica);
    idPratica.setVisible(LabelFdCUtil.checkIfNotNull(variazioniResidenza.getIdPratica()));
    addOrReplace(idPratica);

    String tipoDomanda = null;
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)) {
      if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getTipoVariazioneDiResidenza())) {
        tipoDomanda = variazioniResidenza.getTipoVariazioneDiResidenza().getDescrizione();
      }
      if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali())
          && PageUtil.isStringValid(variazioniResidenza.getDatiGenerali().getTipo())) {
        tipoDomanda = variazioniResidenza.getDatiGenerali().getTipo();
      }
    }
    Label domandaDi =
        new Label(
            "domandaDi",
            new StringResourceModel("CaricaDocumentiPanel.domandaDi", this)
                .setParameters(tipoDomanda));
    domandaDi.setVisible(PageUtil.isStringValid(tipoDomanda));
    addOrReplace(domandaDi);

    // Card sopra per dichiarazione
    GetDichiarazionePrecompilataResponse dichiarazione =
        getDichiarazionePrecompilata(
            variazioniResidenza.getIdPratica(), variazioniResidenza.getCfRichiedente());
    WebMarkupContainer containerDichiarazione = new WebMarkupContainer("containerDichiarazione");

    String nome = "";
    if (LabelFdCUtil.checkIfNotNull(dichiarazione)) {
      nome = dichiarazione.getNomeDocumento();
    }
    Label nomeDocumento = new Label("nomeDocumento", nome);
    nomeDocumento.setVisible(
        LabelFdCUtil.checkIfNotNull(dichiarazione)
            && PageUtil.isStringValid(dichiarazione.getNomeDocumento()));
    containerDichiarazione.addOrReplace(nomeDocumento);

    try {
      if (LabelFdCUtil.checkIfNotNull(dichiarazione)
          && LabelFdCUtil.checkIfNotNull(dichiarazione.getNomeDocumento())
          && LabelFdCUtil.checkIfNotNull(dichiarazione.getDocumento())) {
        containerDichiarazione.addOrReplace(
            VariazioniResidenzaUtil.createLinkDocumentoCaricato(
                "btnDownloadDichiarazione",
                dichiarazione.getNomeDocumento(),
                dichiarazione.getDocumento()));
      } else {
        WebMarkupContainer btnDownloadDichiarazione =
            new WebMarkupContainer("btnDownloadDichiarazione");
        btnDownloadDichiarazione.setVisible(false);
        containerDichiarazione.addOrReplace(btnDownloadDichiarazione);
      }
    } catch (BusinessException | MagicMatchNotFoundException e) {
      log.error("Errore scarica documento apk: " + e.getMessage());
    }

    containerDichiarazione.setVisible(
        LabelFdCUtil.checkIfNotNull(variazioniResidenza)
            && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getIdPratica())
            && LabelFdCUtil.checkIfNotNull(dichiarazione));
    addOrReplace(containerDichiarazione);

    // Lista card individui
    List<IndividuiCollegati> listaIndividuiCollegatiSelezionati =
        new ArrayList<IndividuiCollegati>();
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegati())) {
      listaIndividuiCollegatiSelezionati = variazioniResidenza.getListaIndividuiCollegati();
    }

    PageableListView<IndividuiCollegati> listViewBoxDocumentiDaCaricare =
        new PageableListView<IndividuiCollegati>(
            "boxDocumentiDaCaricare", listaIndividuiCollegatiSelezionati, 4) {

          private static final long serialVersionUID = -8546764796525798682L;

          @Override
          protected void populateItem(ListItem<IndividuiCollegati> item) {

            IndividuiCollegati individuo = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(VariazioniResidenzaUtil.getCssIconaIndividuo(individuo));
            item.addOrReplace(icona);

            String nomeCognome = "";
            if (LabelFdCUtil.checkIfNotNull(individuo.getNominativo())) {
              nomeCognome = individuo.getNominativo().toUpperCase();
            }
            Label nominativoIndividuo =
                new Label(
                    "nominativoIndividuo",
                    new StringResourceModel("CaricaDocumentiPanel.documentiDaAllegare", this)
                        .setParameters(nomeCognome));
            nominativoIndividuo.setVisible(PageUtil.isStringValid(individuo.getNominativo()));
            item.addOrReplace(nominativoIndividuo);

            Label documentoDiRiconoscimento =
                new Label(
                    "documentoDiRiconoscimento",
                    getString("CaricaDocumentiPanel.documentoRiconoscimento"));
            boolean documentoDiRiconoscimentoVisibile = true;

            if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNominativoRichiedente())
                && individuo
                    .getNominativo()
                    .equalsIgnoreCase(variazioniResidenza.getNominativoRichiedente())) {
              documentoDiRiconoscimentoVisibile = false;
            }

            if (LabelFdCUtil.checkIfNotNull(individuo.getCf())) {
              try {
                Residente datiResidenteIndividuo =
                    ServiceLocator.getInstance()
                        .getServiziAnagrafici()
                        .getDatiResidentePerApk(individuo.getCf());
                if (LabelFdCUtil.checkIfNotNull(datiResidenteIndividuo)
                    && LabelFdCUtil.checkIfNotNull(datiResidenteIndividuo.getCpvDateOfBirth())
                    && !LocalDateUtil.isMaggiorenne(datiResidenteIndividuo.getCpvDateOfBirth())) {
                  if (LabelFdCUtil.checkIfNotNull(datiResidenteIndividuo.getCpvHasCitizenship())
                      && LabelFdCUtil.checkIfNotNull(
                          datiResidenteIndividuo.getCpvHasCitizenship().getClvHasIdentifier())) {
                    if (datiResidenteIndividuo
                        .getCpvHasCitizenship()
                        .getClvHasIdentifier()
                        .equalsIgnoreCase("100")) {
                      documentoDiRiconoscimentoVisibile = false;
                    } else {
                      documentoDiRiconoscimentoVisibile = true;
                    }
                  } else {
                    documentoDiRiconoscimentoVisibile = true;
                  }
                }
              } catch (BusinessException | ApiException e1) {
                log.error("Errore durante get dati residente: " + e1);
              }
            }

            documentoDiRiconoscimento.setVisible(documentoDiRiconoscimentoVisibile);
            item.addOrReplace(documentoDiRiconoscimento);

            Label permessoDiSoggiorno =
                new Label(
                    "permessoDiSoggiorno", getString("CaricaDocumentiPanel.permessoDiSoggiorno"));
            boolean permessoDiSoggiornoVisibile = true;
            try {
              if (LabelFdCUtil.checkIfNotNull(individuo.getCf())) {
                Residente datiResidenteDaCf =
                    ServiceLocator.getInstance()
                        .getServizioDemografico()
                        .getDatiResidente(individuo.getCf());
                if (LabelFdCUtil.checkIfNotNull(datiResidenteDaCf)) {
                  if (LabelFdCUtil.checkIfNotNull(datiResidenteDaCf.getCpvHasCitizenship())
                      && datiResidenteDaCf
                          .getCpvHasCitizenship()
                          .getClvHasIdentifier()
                          .equalsIgnoreCase("100")) {
                    permessoDiSoggiornoVisibile = false;
                  }
                }
              }
            } catch (BusinessException | ApiException e) {
              log.error("Errore dati residente in step 4 carica documenti: " + e.getMessage());
            }
            permessoDiSoggiorno.setVisible(permessoDiSoggiornoVisibile);
            item.addOrReplace(permessoDiSoggiorno);

            Label nessunDocumento =
                new Label("nessunDocumento", getString("CaricaDocumentiPanel.nessunDocumento"));
            nessunDocumento.setVisible(
                !documentoDiRiconoscimentoVisibile && !permessoDiSoggiornoVisibile);
            item.addOrReplace(nessunDocumento);

            Label passaportoDaAllegare =
                new Label(
                    "passaportoDaAllegare", getString("CaricaDocumentiPanel.passaportoDaAllegare"));
            boolean passaportoDaAllegareVisibile = false;
            if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali())
                && PageUtil.isStringValid(variazioniResidenza.getDatiGenerali().getIdTipo())
                && variazioniResidenza.getDatiGenerali().getIdTipo().equalsIgnoreCase("1")) {
              passaportoDaAllegareVisibile = true;
            }
            passaportoDaAllegare.setVisible(passaportoDaAllegareVisibile);
            item.addOrReplace(passaportoDaAllegare);
          }
        };

    addOrReplace(listViewBoxDocumentiDaCaricare);

    // Lista card individui immigrazione

    List<IndividuiCollegatiImmigrazione> listaIndividuiImmigrazioneCollegati =
        new ArrayList<IndividuiCollegatiImmigrazione>();
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(
            variazioniResidenza.getListaIndividuiCollegatiImmigrazione())) {
      listaIndividuiImmigrazioneCollegati =
          variazioniResidenza.getListaIndividuiCollegatiImmigrazione();
    }

    PageableListView<IndividuiCollegatiImmigrazione> listViewBoxDocumentiDaCaricareImmigrazione =
        new PageableListView<IndividuiCollegatiImmigrazione>(
            "boxDocumentiDaCaricareImmigrazione", listaIndividuiImmigrazioneCollegati, 4) {

          private static final long serialVersionUID = -3200233196032979740L;

          @Override
          protected void populateItem(ListItem<IndividuiCollegatiImmigrazione> item) {

            IndividuiCollegatiImmigrazione individuo = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(VariazioniResidenzaUtil.getCssIconaIndividuoImmi(individuo));
            item.addOrReplace(icona);

            Label nominativoIndividuo =
                new Label(
                    "nominativoIndividuo",
                    new StringResourceModel("CaricaDocumentiPanel.documentiDaAllegare", this)
                        .setParameters(individuo.getNominativo()));
            nominativoIndividuo.setVisible(PageUtil.isStringValid(individuo.getNominativo()));
            item.addOrReplace(nominativoIndividuo);

            Label documentoDiRiconoscimento =
                new Label(
                    "documentoDiRiconoscimento",
                    getString("CaricaDocumentiPanel.documentoRiconoscimento"));
            boolean documentoDiRiconoscimentoVisibile = true;

            if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNominativoRichiedente())
                && LabelFdCUtil.checkIfNotNull(individuo)
                && LabelFdCUtil.checkIfNotNull(individuo.getNominativo())
                && individuo
                    .getNominativo()
                    .equalsIgnoreCase(variazioniResidenza.getNominativoRichiedente())) {
              documentoDiRiconoscimentoVisibile = false;
            }

            if (LabelFdCUtil.checkIfNotNull(individuo.getCf())) {
              try {
                Residente datiResidenteIndividuo =
                    ServiceLocator.getInstance()
                        .getServiziAnagrafici()
                        .getDatiResidentePerApk(individuo.getCf());
                if (LabelFdCUtil.checkIfNotNull(datiResidenteIndividuo)
                    && LabelFdCUtil.checkIfNotNull(datiResidenteIndividuo.getCpvDateOfBirth())
                    && !LocalDateUtil.isMaggiorenne(datiResidenteIndividuo.getCpvDateOfBirth())) {
                  if (LabelFdCUtil.checkIfNotNull(datiResidenteIndividuo.getCpvHasCitizenship())
                      && LabelFdCUtil.checkIfNotNull(
                          datiResidenteIndividuo.getCpvHasCitizenship().getClvHasIdentifier())) {
                    if (datiResidenteIndividuo
                        .getCpvHasCitizenship()
                        .getClvHasIdentifier()
                        .equalsIgnoreCase("100")) {
                      documentoDiRiconoscimentoVisibile = false;
                    } else {
                      documentoDiRiconoscimentoVisibile = true;
                    }
                  } else {
                    documentoDiRiconoscimentoVisibile = true;
                  }
                } else {
                  if (PageUtil.isStringValid(individuo.getCittadinanza())) {
                    if (!LocalDateUtil.isMaggiorenne(individuo.getDataNascita())
                        && individuo.getCittadinanza().contains("ITALIANA")) {
                      documentoDiRiconoscimentoVisibile = false;
                    } else {
                      documentoDiRiconoscimentoVisibile = true;
                    }
                  }

                  if (LabelFdCUtil.checkIfNotNull(individuo.getNominativo())
                      && individuo
                          .getNominativo()
                          .equalsIgnoreCase(variazioniResidenza.getNominativoRichiedente())) {
                    documentoDiRiconoscimentoVisibile = false;
                  }
                }
              } catch (BusinessException | ApiException e1) {
                log.error("Errore durante get dati residente: " + e1);
              }
            }

            documentoDiRiconoscimento.setVisible(documentoDiRiconoscimentoVisibile);
            item.addOrReplace(documentoDiRiconoscimento);

            Label passaportoDaAllegare =
                new Label(
                    "passaportoDaAllegare", getString("CaricaDocumentiPanel.passaportoDaAllegare"));
            item.addOrReplace(passaportoDaAllegare);
          }
        };

    addOrReplace(listViewBoxDocumentiDaCaricareImmigrazione);

    // paginator
    IPageable pageableList = null;
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegati())
        && !LabelFdCUtil.checkEmptyList(variazioniResidenza.getListaIndividuiCollegati())) {
      pageableList = listViewBoxDocumentiDaCaricare;
    }
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegatiImmigrazione())
        && !LabelFdCUtil.checkEmptyList(
            variazioniResidenza.getListaIndividuiCollegatiImmigrazione())) {
      pageableList = listViewBoxDocumentiDaCaricareImmigrazione;
    }

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", pageableList);
    paginazioneFascicolo.setVisible(
        (!LabelFdCUtil.checkEmptyList(listaIndividuiCollegatiSelezionati)
                && listaIndividuiCollegatiSelezionati.size() > 4)
            || (!LabelFdCUtil.checkEmptyList(listaIndividuiImmigrazioneCollegati)
                && listaIndividuiImmigrazioneCollegati.size() > 4));
    addOrReplace(paginazioneFascicolo);

    formCaricaDocumenti.addOrReplace(creaBottoneAvanti(variazioniResidenza));
    formCaricaDocumenti.addOrReplace(creaBottoneSospendi(variazioniResidenza));

    formCaricaDocumenti.setMultiPart(true);
    formCaricaDocumenti.setOutputMarkupId(true);

    addOrReplace(formCaricaDocumenti);

    // card coabitante
    WebMarkupContainer containerCoabitante = new WebMarkupContainer("containerCoabitante");
    boolean containerCoabitanteVisibile = false;
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && PageUtil.isStringValid(variazioniResidenza.getNomeCoabitante())
        && PageUtil.isStringValid(variazioniResidenza.getCognomeCoabitante())) {
      containerCoabitanteVisibile = true;
    }
    String dichiarazioneCoabitante = getString("CaricaDocumentiPanel.dichiarazioneFamiglia");
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && PageUtil.isStringValid(variazioniResidenza.getNomeCoabitante())
        && PageUtil.isStringValid(variazioniResidenza.getCognomeCoabitante())) {
      dichiarazioneCoabitante =
          dichiarazioneCoabitante
              .concat(" ")
              .concat("di")
              .concat(" ")
              .concat(variazioniResidenza.getCognomeCoabitante().toUpperCase())
              .concat(" ")
              .concat(variazioniResidenza.getNomeCoabitante().toUpperCase());
    }
    Label nominativoCoabitante = new Label("nominativoCoabitante", dichiarazioneCoabitante);
    containerCoabitante.addOrReplace(nominativoCoabitante);
    containerCoabitante.setVisible(containerCoabitanteVisibile);
    containerCoabitante.setOutputMarkupId(true);
    addOrReplace(containerCoabitante);

    // card edilizia
    WebMarkupContainer containerEdilizia = new WebMarkupContainer("containerEdilizia");
    boolean containerEdiliziaVisibile = false;
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipoOccupazione())
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipoOccupazione().getCodice())
        && variazioniResidenza.getComboTipoOccupazione().getCodice().equalsIgnoreCase("03")) {
      containerEdiliziaVisibile = true;
    }
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali())
        && LabelFdCUtil.checkIfNotNull(
            variazioniResidenza.getDatiGenerali().getCodiceTipologiaOccupazione())
        && variazioniResidenza
            .getDatiGenerali()
            .getCodiceTipologiaOccupazione()
            .equalsIgnoreCase("03")) {
      containerEdiliziaVisibile = true;
    }
    containerEdilizia.setVisible(containerEdiliziaVisibile);
    containerEdilizia.setOutputMarkupId(true);
    addOrReplace(containerEdilizia);

    // card minore
    WebMarkupContainer containerMinorenne = new WebMarkupContainer("containerMinorenne");
    boolean containerMinorenneVisibile = false;
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegati())
        && !LabelFdCUtil.checkEmptyList(variazioniResidenza.getListaIndividuiCollegati())) {
      IndividuiCollegati etaMinorenne =
          variazioniResidenza.getListaIndividuiCollegati().stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && LabelFdCUtil.checkIfNotNull(elem.getEta())
                          && elem.getEta() < 18)
              .findAny()
              .orElse(null);
      if (LabelFdCUtil.checkIfNotNull(etaMinorenne)) {
        containerMinorenneVisibile = true;
      }
    }
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegatiImmigrazione())
        && !LabelFdCUtil.checkEmptyList(
            variazioniResidenza.getListaIndividuiCollegatiImmigrazione())) {
      IndividuiCollegatiImmigrazione etaMinorenne =
          variazioniResidenza.getListaIndividuiCollegatiImmigrazione().stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && LabelFdCUtil.checkIfNotNull(elem.getEta())
                          && elem.getEta() < 18)
              .findAny()
              .orElse(null);
      if (LabelFdCUtil.checkIfNotNull(etaMinorenne)) {
        containerMinorenneVisibile = true;
      }
    }

    containerMinorenne.setVisible(containerMinorenneVisibile);
    containerMinorenne.setOutputMarkupId(true);
    addOrReplace(containerMinorenne);

    // card proprietario
    WebMarkupContainer containerProprietario = new WebMarkupContainer("containerProprietario");
    boolean containerProprietarioVisibile = false;
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)) {
      if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getComboTipoOccupazione())) {
        if (!variazioniResidenza.getComboTipoOccupazione().getCodice().equalsIgnoreCase("01")) {
          containerProprietarioVisibile = true;
        }
        if (variazioniResidenza.getComboTipoOccupazione().getCodice().equalsIgnoreCase("03")) {
          containerProprietarioVisibile = false;
        }
      } else {
        if (LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNomeProprietario())
            && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCognomeProprietario())) {
          containerProprietarioVisibile = true;
        }
      }

      if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
          && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali())
          && LabelFdCUtil.checkIfNotNull(
              variazioniResidenza.getDatiGenerali().getCodiceTipologiaOccupazione())
          && variazioniResidenza
              .getDatiGenerali()
              .getCodiceTipologiaOccupazione()
              .equalsIgnoreCase("01")) {
        containerProprietarioVisibile = false;
      }
    }

    String dichiarazioneProp = getString("CaricaDocumentiPanel.dichiarazioneProprietario");
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getNomeProprietario())
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getCognomeProprietario())) {
      dichiarazioneProp =
          dichiarazioneProp
              .concat(" ")
              .concat("di")
              .concat(" ")
              .concat(variazioniResidenza.getCognomeProprietario().toUpperCase())
              .concat(" ")
              .concat(variazioniResidenza.getNomeProprietario().toUpperCase());
    }
    Label nominativoProprietario = new Label("nominativoProprietario", dichiarazioneProp);
    containerProprietario.addOrReplace(nominativoProprietario);
    containerProprietario.setVisible(containerProprietarioVisibile);
    containerProprietario.setOutputMarkupId(true);
    addOrReplace(containerProprietario);

    Integer individuiCollegatiSize = 0;
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegati())
        && !LabelFdCUtil.checkEmptyList(variazioniResidenza.getListaIndividuiCollegati())) {
      individuiCollegatiSize = variazioniResidenza.getListaIndividuiCollegati().size();

      IndividuiCollegati utenteLoggato =
          variazioniResidenza.getListaIndividuiCollegati().stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                          && elem.getNominativo()
                              .equalsIgnoreCase(variazioniResidenza.getNominativoRichiedente()))
              .findAny()
              .orElse(null);
      if (LabelFdCUtil.checkIfNotNull(utenteLoggato)) {
        individuiCollegatiSize = individuiCollegatiSize - 1;
      }

      List<IndividuiCollegati> listaMinorenni =
          variazioniResidenza.getListaIndividuiCollegati().stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && !LocalDateUtil.isMaggiorenne(elem.getDataNascita()))
              .collect(Collectors.toList());
      for (IndividuiCollegati minore : listaMinorenni) {

        if (LabelFdCUtil.checkIfNotNull(minore.getCf())) {
          try {
            Residente datiMinore =
                ServiceLocator.getInstance()
                    .getServiziAnagrafici()
                    .getDatiResidentePerApk(minore.getCf());

            if (LabelFdCUtil.checkIfNotNull(datiMinore)
                && LabelFdCUtil.checkIfNotNull(datiMinore.getCpvDateOfBirth())
                && !LocalDateUtil.isMaggiorenne(datiMinore.getCpvDateOfBirth())) {
              if (LabelFdCUtil.checkIfNotNull(datiMinore.getCpvHasCitizenship())
                  && LabelFdCUtil.checkIfNotNull(
                      datiMinore.getCpvHasCitizenship().getClvHasIdentifier())) {
                if (datiMinore
                    .getCpvHasCitizenship()
                    .getClvHasIdentifier()
                    .equalsIgnoreCase("100")) {
                  individuiCollegatiSize = individuiCollegatiSize - 1;
                }
              }
            }

          } catch (BusinessException | ApiException e) {
            log.error("Errore dati residente: " + e.getMessage());
          }
        }
      }
    }
    if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
        && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getListaIndividuiCollegatiImmigrazione())
        && !LabelFdCUtil.checkEmptyList(
            variazioniResidenza.getListaIndividuiCollegatiImmigrazione())) {
      individuiCollegatiSize = variazioniResidenza.getListaIndividuiCollegatiImmigrazione().size();

      IndividuiCollegatiImmigrazione utenteLoggato =
          variazioniResidenza.getListaIndividuiCollegatiImmigrazione().stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                          && elem.getNominativo()
                              .equalsIgnoreCase(variazioniResidenza.getNominativoRichiedente()))
              .findAny()
              .orElse(null);

      if (LabelFdCUtil.checkIfNotNull(utenteLoggato)) {
        individuiCollegatiSize = individuiCollegatiSize - 1;
      }

      List<IndividuiCollegatiImmigrazione> listaMinorenni =
          variazioniResidenza.getListaIndividuiCollegatiImmigrazione().stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && !LocalDateUtil.isMaggiorenne(elem.getDataNascita()))
              .collect(Collectors.toList());
      for (IndividuiCollegatiImmigrazione minore : listaMinorenni) {

        if (LabelFdCUtil.checkIfNotNull(minore.getCf())) {
          try {
            Residente datiMinore =
                ServiceLocator.getInstance()
                    .getServiziAnagrafici()
                    .getDatiResidentePerApk(minore.getCf());

            if (LabelFdCUtil.checkIfNotNull(datiMinore)
                && LabelFdCUtil.checkIfNotNull(datiMinore.getCpvDateOfBirth())
                && !LocalDateUtil.isMaggiorenne(datiMinore.getCpvDateOfBirth())) {
              if (LabelFdCUtil.checkIfNotNull(datiMinore.getCpvHasCitizenship())
                  && LabelFdCUtil.checkIfNotNull(
                      datiMinore.getCpvHasCitizenship().getClvHasIdentifier())) {
                if (datiMinore
                    .getCpvHasCitizenship()
                    .getClvHasIdentifier()
                    .equalsIgnoreCase("100")) {
                  individuiCollegatiSize = individuiCollegatiSize - 1;
                }
              }
            } else {

              if (PageUtil.isStringValid(minore.getCittadinanza())) {
                if (minore.getCittadinanza().contains("ITALIANA")) {
                  individuiCollegatiSize = individuiCollegatiSize - 1;
                }
              }
            }

          } catch (BusinessException | ApiException e) {
            log.error("Errore dati residente: " + e.getMessage());
          }
        }
      }
    }

    this.minimoDocumentiDaCaricare =
        minimoDocumentiDaCaricare(
            individuiCollegatiSize,
            containerCoabitanteVisibile,
            containerProprietarioVisibile,
            containerEdiliziaVisibile);
    // this.massimoDocumentiDaCaricare =
    // massimoDocumentiDaCaricare(individuiCollegatiSize,
    // containerCoabitanteVisibile, containerProprietarioVisibile,
    // containerEdiliziaVisibile);

  }

  private LaddaAjaxButton creaBottoneAvanti(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = -2302453692103523632L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(CaricaDocumentiPanel.this);

            index = index + 1;

            List<DocumentoPratica> listaDocumentiCaricati = new ArrayList<DocumentoPratica>();
            if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getIdPratica())) {
              listaDocumentiCaricati =
                  VariazioniResidenzaUtil.getDocumentiCaricati(variazioniResidenza.getIdPratica());
            }
            Integer documentiCaricatiSize = listaDocumentiCaricati.size();

            if (documentiCaricatiSize >= minimoDocumentiDaCaricare) {

              if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                  && LabelFdCUtil.checkIfNotNull(
                      variazioniResidenza.getTipoVariazioneDiResidenza())) {
                if (variazioniResidenza.getTipoVariazioneDiResidenza().getId() == 1) {
                  setResponsePage(new CambioIndirizzoPage(index, variazioniResidenza));
                } else {
                  setResponsePage(new RichiestaResidenzaPage(index, variazioniResidenza));
                }
              }

              if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                  && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getIdTipoPratica())) {
                if (variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("1")) {
                  setResponsePage(new RichiestaResidenzaPage(index, variazioniResidenza));
                } else if (variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("3")
                    || variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("4")) {
                  setResponsePage(new CambioIndirizzoPage(index, variazioniResidenza));
                }
              }

            } else {
              alertMinimoDocumentiDaCaricare.setVisible(true);
              onError();
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            // target.add(CaricaDocumentiPanel.this);

            log.error("CP errore step 4 cambio indirizzo");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("CaricaDocumentiPanel.avanti", CaricaDocumentiPanel.this)));

    avanti.setOutputMarkupId(true);

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneSospendi(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> sospendi =
        new LaddaAjaxLink<Object>("sospendi", Type.Primary) {

          private static final long serialVersionUID = 7172410156518540685L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(CaricaDocumentiPanel.this);

            setResponsePage(VariazioniDiResidenzaPage.class);
          }
        };

    sospendi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("CaricaDocumentiPanel.sospendi", CaricaDocumentiPanel.this)));

    return sospendi;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public VariazioniResidenza getVariazione() {
    return variazione;
  }

  public void setVariazione(VariazioniResidenza variazione) {
    this.variazione = variazione;
  }

  public CaricaDocumentiForm getFormCaricaDocumenti() {
    return formCaricaDocumenti;
  }

  public void setFormCaricaDocumenti(CaricaDocumentiForm formCaricaDocumenti) {
    this.formCaricaDocumenti = formCaricaDocumenti;
  }

  public FeedbackPanel getFeedbackPanel() {
    return feedbackPanel;
  }

  public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
    this.feedbackPanel = feedbackPanel;
  }

  protected FeedbackPanel createFeedBackStep4Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback4") {

          private static final long serialVersionUID = 5647709710454195563L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.add(feedback);
    return feedback;
  }

  private GetDichiarazionePrecompilataResponse getDichiarazionePrecompilata(
      Integer idPratica, String codiceFiscaleRichiedente) {
    try {
      GetDichiarazionePrecompilataResponse result = null;
      if (LabelFdCUtil.checkIfNotNull(idPratica)) {
        GetDichiarazionePrecompilataResponseGenericResponse risposta =
            ServiceLocator.getInstance()
                .getServiziAnagrafici()
                .getDichiarazionePrecompilata(idPratica, codiceFiscaleRichiedente);
        if (LabelFdCUtil.checkIfNotNull(risposta)
            && LabelFdCUtil.checkIfNotNull(risposta.getResult())) {
          result = risposta.getResult();
        }
      }
      return result;
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante get dichiarazione precompilata:" + e.getMessage());

      GetDichiarazionePrecompilataResponse result = null;
      return result;
    }
  }

  private Integer minimoDocumentiDaCaricare(
      Integer individuiCollegatiSize,
      boolean containerCoabitanteVisibile,
      boolean containerProprietarioVisibile,
      boolean containerEdiliziaVisibile) {

    Integer minimo = 0;

    minimo = individuiCollegatiSize;
    if (containerCoabitanteVisibile) {
      minimo = minimo + 2;
    }

    if (containerProprietarioVisibile) {
      minimo = minimo + 2;
    }

    if (containerEdiliziaVisibile) {
      minimo = minimo + 1;
    }

    Integer dichiarazionePrecompilata = 1;
    minimo = minimo + dichiarazionePrecompilata;

    log.debug("CP minimoDocumentiDaCaricare = " + minimo);

    return minimo;
  }

  // private Integer massimoDocumentiDaCaricare(Integer
  // individuiCollegatiSize,
  // boolean containerCoabitanteVisibile,
  // boolean containerProprietarioVisibile, boolean containerEdiliziaVisibile)
  // {
  // Integer massimo = 0;
  //
  // massimo = (individuiCollegatiSize * 2) - 1;
  // if (containerCoabitanteVisibile) {
  // massimo = massimo + 2;
  // }
  //
  // if (containerProprietarioVisibile) {
  // massimo = massimo + 2;
  // }
  //
  // if (containerEdiliziaVisibile) {
  // massimo = massimo + 1;
  // }
  //
  // log.debug("CP massimoDocumentiDaCaricare = " + massimo);
  //
  // return massimo;
  // }

}
