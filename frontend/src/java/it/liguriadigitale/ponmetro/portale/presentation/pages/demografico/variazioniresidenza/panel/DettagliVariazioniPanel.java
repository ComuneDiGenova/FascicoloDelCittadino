package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegati;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.VariazioniDiResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.CambioIndirizzoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.common.panel.modali.ModaleEliminaPraticaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.RichiestaResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.DocumentoPratica;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDatiGeneraliPraticaResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.StringGenericResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class DettagliVariazioniPanel extends BasePanel {

  private static final long serialVersionUID = -8502968450990858514L;

  private VariazioniResidenza variazioniResidenza;

  protected PaginazioneFascicoloPanel paginationIndividui;

  protected PaginazioneFascicoloPanel paginationDocumenti;

  protected ModaleEliminaPraticaPanel<String> modaleEliminaPratica;

  public DettagliVariazioniPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    VariazioniResidenza variazioniResidenza = (VariazioniResidenza) dati;

    GetDatiGeneraliPraticaResponse datiGenerali = variazioniResidenza.getDatiGenerali();

    WebMarkupContainer containerDatiGenerali = new WebMarkupContainer("containerDatiGenerali");
    containerDatiGenerali.setOutputMarkupId(true);

    WebMarkupContainer icona = new WebMarkupContainer("icona");
    icona.add(VariazioniResidenzaUtil.getCssIconaVariazione(datiGenerali.getIdTipo()));
    containerDatiGenerali.addOrReplace(icona);

    Label id = new Label("id", datiGenerali.getId());
    id.setVisible(LabelFdCUtil.checkIfNotNull(datiGenerali.getId()));
    containerDatiGenerali.addOrReplace(id);

    Label anno = new Label("anno", datiGenerali.getAnno());
    anno.setVisible(
        LabelFdCUtil.checkIfNotNull(datiGenerali.getAnno())
            && LabelFdCUtil.checkIfNotNull(datiGenerali.getNumero())
            && datiGenerali.getNumero() != 0);
    containerDatiGenerali.addOrReplace(anno);

    Label numero = new Label("numero", datiGenerali.getNumero());
    numero.setVisible(
        LabelFdCUtil.checkIfNotNull(datiGenerali.getNumero()) && datiGenerali.getNumero() != 0);
    containerDatiGenerali.addOrReplace(numero);

    Label tipo = new Label("tipo", datiGenerali.getTipo());
    tipo.setVisible(PageUtil.isStringValid(datiGenerali.getTipo()));
    containerDatiGenerali.addOrReplace(tipo);

    LocalDateLabel dataInizio = new LocalDateLabel("dataInizio", datiGenerali.getDataInizio());
    dataInizio.setVisible(LabelFdCUtil.checkIfNotNull(datiGenerali.getDataInizio()));
    containerDatiGenerali.addOrReplace(dataInizio);

    LocalDateLabel dataScadenza =
        new LocalDateLabel("dataScadenza", datiGenerali.getDataScadenza());
    dataScadenza.setVisible(
        LabelFdCUtil.checkIfNotNull(datiGenerali.getDataScadenza())
            && !datiGenerali.getDataScadenza().equals(LocalDate.of(0001, 1, 1)));
    containerDatiGenerali.addOrReplace(dataScadenza);

    Label termineRicorso = new Label("termineRicorso", datiGenerali.getTermineRicorso());
    termineRicorso.setVisible(
        LabelFdCUtil.checkIfNotNull(datiGenerali.getTermineRicorso())
            && datiGenerali.getTermineRicorso() != 0);
    containerDatiGenerali.addOrReplace(termineRicorso);

    Label ufficioAtti = new Label("ufficioAtti", datiGenerali.getUfficioAtti());
    ufficioAtti.setVisible(PageUtil.isStringValid(datiGenerali.getUfficioAtti()));
    containerDatiGenerali.addOrReplace(ufficioAtti);

    Label stato = new Label("stato", datiGenerali.getStatoDescr());
    stato.setVisible(PageUtil.isStringValid(datiGenerali.getStatoDescr()));
    containerDatiGenerali.addOrReplace(stato);

    Label unita = new Label("unita", datiGenerali.getUnita());
    unita.setVisible(PageUtil.isStringValid(datiGenerali.getUnita()));
    containerDatiGenerali.addOrReplace(unita);

    Label responsabile = new Label("responsabile", datiGenerali.getResponsabile());
    responsabile.setVisible(PageUtil.isStringValid(datiGenerali.getResponsabile()));
    containerDatiGenerali.addOrReplace(responsabile);

    Label nomeIs = new Label("nomeIs", datiGenerali.getNomeIs());
    nomeIs.setVisible(PageUtil.isStringValid(datiGenerali.getNomeIs()));
    containerDatiGenerali.addOrReplace(nomeIs);

    Label cognomeIs = new Label("cognomeIs", datiGenerali.getCognomeIs());
    cognomeIs.setVisible(PageUtil.isStringValid(datiGenerali.getCognomeIs()));
    containerDatiGenerali.addOrReplace(cognomeIs);

    Label nomeProprietario = new Label("nomeProprietario", datiGenerali.getNomeProprietario());
    nomeProprietario.setVisible(PageUtil.isStringValid(datiGenerali.getNomeProprietario()));
    containerDatiGenerali.addOrReplace(nomeProprietario);

    Label cognomeProprietario =
        new Label("cognomeProprietario", datiGenerali.getCognomeProprietario());
    cognomeProprietario.setVisible(PageUtil.isStringValid(datiGenerali.getCognomeProprietario()));
    containerDatiGenerali.addOrReplace(cognomeProprietario);

    Label ragioneSocialeProprietario =
        new Label("ragioneSocialeProprietario", datiGenerali.getRagioneSocialeProprietario());
    ragioneSocialeProprietario.setVisible(
        PageUtil.isStringValid(datiGenerali.getRagioneSocialeProprietario()));
    containerDatiGenerali.addOrReplace(ragioneSocialeProprietario);

    Label codiceFiscaleProprietario =
        new Label("codiceFiscaleProprietario", datiGenerali.getCodiceFiscaleProprietario());
    codiceFiscaleProprietario.setVisible(
        PageUtil.isStringValid(datiGenerali.getCodiceFiscaleProprietario()));
    containerDatiGenerali.addOrReplace(codiceFiscaleProprietario);

    Label viario = new Label("viario", variazioniResidenza.getViario());
    viario.setVisible(PageUtil.isStringValid(variazioniResidenza.getViario()));
    containerDatiGenerali.addOrReplace(viario);

    Label numeroCivico = new Label("numeroCivico", datiGenerali.getNumeroCivico());
    numeroCivico.setVisible(PageUtil.isStringValid(datiGenerali.getNumeroCivico()));
    containerDatiGenerali.addOrReplace(numeroCivico);

    Label esponente = new Label("esponente", datiGenerali.getEsponente());
    esponente.setVisible(PageUtil.isStringValid(datiGenerali.getEsponente()));
    containerDatiGenerali.addOrReplace(esponente);

    Label scala = new Label("scala", datiGenerali.getScala());
    scala.setVisible(PageUtil.isStringValid(datiGenerali.getScala()));
    containerDatiGenerali.addOrReplace(scala);

    Label piano = new Label("piano", datiGenerali.getPiano());
    piano.setVisible(PageUtil.isStringValid(datiGenerali.getPiano()));
    containerDatiGenerali.addOrReplace(piano);

    Label interno = new Label("interno", datiGenerali.getInterno());
    interno.setVisible(PageUtil.isStringValid(datiGenerali.getInterno()));
    containerDatiGenerali.addOrReplace(interno);

    Label internoEsponente = new Label("internoEsponente", datiGenerali.getInternoEsponente());
    internoEsponente.setVisible(PageUtil.isStringValid(datiGenerali.getInternoEsponente()));
    containerDatiGenerali.addOrReplace(internoEsponente);

    Label colore = new Label("colore", datiGenerali.getColore());
    colore.setVisible(PageUtil.isStringValid(datiGenerali.getColore()));
    containerDatiGenerali.addOrReplace(colore);

    containerDatiGenerali.setVisible(LabelFdCUtil.checkIfNotNull(datiGenerali));
    addOrReplace(containerDatiGenerali);

    WebMarkupContainer containerIndividuiCollegati =
        new WebMarkupContainer("containerIndividuiCollegati");
    containerIndividuiCollegati.setOutputMarkupId(true);

    List<IndividuiCollegati> listaIndividuiCollegati =
        variazioniResidenza.getListaIndividuiCollegati();

    PageableListView<IndividuiCollegati> listViewIndividui =
        new PageableListView<IndividuiCollegati>("boxIndividui", listaIndividuiCollegati, 4) {

          private static final long serialVersionUID = 6251753332279702263L;

          @Override
          protected void populateItem(ListItem<IndividuiCollegati> item) {

            final IndividuiCollegati individuo = item.getModelObject();

            WebMarkupContainer iconaIndividuo = new WebMarkupContainer("iconaIndividuo");
            iconaIndividuo.add(VariazioniResidenzaUtil.getCssIconaIndividuo(individuo));
            item.addOrReplace(iconaIndividuo);

            String nomeCognome = "";
            if (LabelFdCUtil.checkIfNotNull(individuo.getNominativo())) {
              nomeCognome = individuo.getNominativo().toUpperCase();
            }
            Label nominativo = new Label("nominativo", nomeCognome);
            nominativo.setVisible(PageUtil.isStringValid(individuo.getNominativo()));
            item.addOrReplace(nominativo);

            Label parentela = new Label("parentela", individuo.getParentela());
            parentela.setVisible(PageUtil.isStringValid(individuo.getParentela()));
            item.addOrReplace(parentela);
          }
        };
    containerIndividuiCollegati.addOrReplace(listViewIndividui);

    paginationIndividui = new PaginazioneFascicoloPanel("paginationIndividui", listViewIndividui);
    paginationIndividui.setVisible(
        LabelFdCUtil.checkIfNotNull(listaIndividuiCollegati) && listaIndividuiCollegati.size() > 4);
    containerIndividuiCollegati.addOrReplace(paginationIndividui);

    containerIndividuiCollegati.setVisible(
        LabelFdCUtil.checkIfNotNull(listaIndividuiCollegati)
            && !LabelFdCUtil.checkEmptyList(listaIndividuiCollegati));
    addOrReplace(containerIndividuiCollegati);

    WebMarkupContainer containerDocumenti = new WebMarkupContainer("containerDocumenti");
    containerDocumenti.setOutputMarkupId(true);

    List<DocumentoPratica> listaDocumentiCaricati = variazioniResidenza.getListaDocumentiCaricati();

    PageableListView<DocumentoPratica> listViewDocumenti =
        new PageableListView<DocumentoPratica>("boxDocumenti", listaDocumentiCaricati, 6) {

          private static final long serialVersionUID = 7105272841921078559L;

          @Override
          protected void populateItem(ListItem<DocumentoPratica> item) {

            final DocumentoPratica documento = item.getModelObject();

            Label idDocumento = new Label("idDocumento", documento.getIdDocumento());
            idDocumento.setVisible(LabelFdCUtil.checkIfNotNull(documento.getIdDocumento()));
            item.addOrReplace(idDocumento);

            Label nomeDocumento = new Label("nomeDocumento", documento.getNomeDocumento());
            nomeDocumento.setVisible(PageUtil.isStringValid(documento.getNomeDocumento()));
            item.addOrReplace(nomeDocumento);

            try {
              if (LabelFdCUtil.checkIfNotNull(documento)
                  && LabelFdCUtil.checkIfNotNull(documento.getNomeDocumento())
                  && LabelFdCUtil.checkIfNotNull(documento.getDocumento())) {
                item.addOrReplace(
                    VariazioniResidenzaUtil.createLinkDocumentoCaricato(
                        "btnScarica", documento.getNomeDocumento(), documento.getDocumento()));
              } else {
                WebMarkupContainer btnScarica = new WebMarkupContainer("btnScarica");
                btnScarica.setVisible(false);
                item.addOrReplace(btnScarica);
              }

            } catch (BusinessException | MagicMatchNotFoundException e) {
              log.error("Errore scarica documento apk: " + e.getMessage());
            }
          }
        };
    containerDocumenti.addOrReplace(listViewDocumenti);

    paginationDocumenti = new PaginazioneFascicoloPanel("paginationDocumenti", listViewDocumenti);
    paginationDocumenti.setVisible(
        LabelFdCUtil.checkIfNotNull(listaDocumentiCaricati) && listaDocumentiCaricati.size() > 6);
    containerDocumenti.addOrReplace(paginationDocumenti);

    containerDocumenti.setVisible(
        LabelFdCUtil.checkIfNotNull(listaDocumentiCaricati)
            && !LabelFdCUtil.checkEmptyList(listaDocumentiCaricati));
    addOrReplace(containerDocumenti);

    WebMarkupContainer containerAzioni = new WebMarkupContainer("containerAzioni");
    containerAzioni.setOutputMarkupId(true);
    containerAzioni.setOutputMarkupPlaceholderTag(true);
    containerAzioni.setVisible(
        VariazioniResidenzaUtil.checkVisibilitaBottoniDettagli(variazioniResidenza));

    modaleEliminaPratica =
        new ModaleEliminaPraticaPanel<String>("modaleEliminaPratica", variazioniResidenza);
    String infoAnnullamento = getString("DettagliVariazioniPanel.id").concat(" ");
    Label praticaDaAnnullare =
        new Label(
            "praticaDaAnnullare",
            infoAnnullamento.concat(String.valueOf(variazioniResidenza.getIdPratica())));
    modaleEliminaPratica.myAdd(praticaDaAnnullare);
    modaleEliminaPratica.addOrReplace(creaBtnSi(modaleEliminaPratica, variazioniResidenza));
    modaleEliminaPratica.addOrReplace(creaBtnNo(modaleEliminaPratica, variazioniResidenza));
    containerAzioni.addOrReplace(modaleEliminaPratica);

    containerAzioni.addOrReplace(creaBtnRiprendi(variazioniResidenza));
    containerAzioni.addOrReplace(creaBtnElimina(variazioniResidenza));
    addOrReplace(containerAzioni);
  }

  public VariazioniResidenza getVariazioniResidenza() {
    return variazioniResidenza;
  }

  public void setVariazioniResidenza(VariazioniResidenza variazioniResidenza) {
    this.variazioniResidenza = variazioniResidenza;
  }

  private LaddaAjaxLink<Object> creaBtnRiprendi(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> btnRiprendi =
        new LaddaAjaxLink<Object>("btnRiprendi", Type.Primary) {

          private static final long serialVersionUID = -3286653229722309158L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DettagliVariazioniPanel.this);

            Integer indexCaricaDocumenti = 4;

            String descrizione = "";

            if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getDatiGenerali())) {
              if (variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("1")) {
                descrizione = "Richiesta residenza";
                setResponsePage(
                    new RichiestaResidenzaPage(
                        indexCaricaDocumenti, descrizione, variazioniResidenza));
              }
              if (variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("3")
                  || variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("4")) {
                descrizione = "Cambio indirizzo";
                setResponsePage(
                    new CambioIndirizzoPage(
                        indexCaricaDocumenti, descrizione, variazioniResidenza));
              }
            }
          }
        };
    btnRiprendi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DettagliVariazioniPanel.btnRiprendi", DettagliVariazioniPanel.this)));

    btnRiprendi.setVisible(
        VariazioniResidenzaUtil.checkVisibilitaBottoniDettagli(variazioniResidenza));

    IconType iconType =
        new IconType("btnRiprendi") {

          private static final long serialVersionUID = -6990974891254530910L;

          @Override
          public String cssClassName() {
            String icona = "icon-passeggiate";

            if (LabelFdCUtil.checkIfNotNull(variazioniResidenza)
                && LabelFdCUtil.checkIfNotNull(variazioniResidenza.getIdTipoPratica())) {
              if (variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("1")) {
                icona = "icon-cambio-residenza";
              } else if (variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("3")
                  || variazioniResidenza.getIdTipoPratica().equalsIgnoreCase("4")) {
                icona = "icon-cambio-indirizzo";
              }
            }

            return icona;
          }
        };
    btnRiprendi.setIconType(iconType);

    btnRiprendi.setOutputMarkupId(true);
    btnRiprendi.setOutputMarkupPlaceholderTag(true);

    return btnRiprendi;
  }

  private LaddaAjaxLink<Object> creaBtnElimina(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> btnElimina =
        new LaddaAjaxLink<Object>("btnElimina", Type.Primary) {

          private static final long serialVersionUID = -228954625313765821L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DettagliVariazioniPanel.this);

            modaleEliminaPratica.show(target);
          }
        };

    btnElimina.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DettagliVariazioniPanel.btnElimina", DettagliVariazioniPanel.this)));

    btnElimina.setVisible(
        VariazioniResidenzaUtil.checkVisibilitaBottoniDettagli(variazioniResidenza));

    IconType iconType =
        new IconType("btnElimina") {

          private static final long serialVersionUID = -6331547954209180920L;

          @Override
          public String cssClassName() {
            return "icon-spazzatura";
          }
        };
    btnElimina.setIconType(iconType);

    btnElimina.setOutputMarkupId(true);
    btnElimina.setOutputMarkupPlaceholderTag(true);

    return btnElimina;
  }

  private LaddaAjaxLink<Object> creaBtnSi(
      Modal<String> modale, VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> btnSi =
        new LaddaAjaxLink<Object>("btnSi", Type.Primary) {

          private static final long serialVersionUID = -2564929823923823139L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            try {
              StringGenericResponse praticaCancellata =
                  ServiceLocator.getInstance()
                      .getServiziAnagrafici()
                      .cancellaPratica(variazioniResidenza.getIdPratica());
              if (LabelFdCUtil.checkIfNotNull(praticaCancellata)
                  && praticaCancellata.getStatus().equalsIgnoreCase("OK")) {
                setResponsePage(VariazioniDiResidenzaPage.class);
              }
            } catch (BusinessException | ApiException | IOException e) {
              log.error("CP errore durante cancellazione pratica: " + e.getMessage());
              error("Errore durante cancellazione pratica");
            }
          }
        };

    btnSi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DettagliVariazioniPanel.si", DettagliVariazioniPanel.this)));

    return btnSi;
  }

  private LaddaAjaxLink<Object> creaBtnNo(
      Modal<String> modale, VariazioniResidenza variazioniResidenza) {
    LaddaAjaxLink<Object> btnNo =
        new LaddaAjaxLink<Object>("btnNo", Type.Primary) {

          private static final long serialVersionUID = -9026479784989310510L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            modaleEliminaPratica.close(target);
          }
        };

    btnNo.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DettagliVariazioniPanel.no", DettagliVariazioniPanel.this)));

    return btnNo;
  }
}
