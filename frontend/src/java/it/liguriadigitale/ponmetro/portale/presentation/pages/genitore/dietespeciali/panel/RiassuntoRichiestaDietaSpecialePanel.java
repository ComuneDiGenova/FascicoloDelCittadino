package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.GiorniRientroScuola;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.ConfermaRichiestaDietaSpecialePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.RichiestaDietaSpecialePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.pojo.UtenteServiziRistorazioneDieteSpeciali;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class RiassuntoRichiestaDietaSpecialePanel extends BasePanel {

  private static final long serialVersionUID = -7427042050889994986L;

  private UtenteServiziRistorazione iscrizione;

  private DietaSpeciale dietaSpeciale;

  private WebMarkupContainer containerAlertIndietro =
      new WebMarkupContainer("containerAlertIndietro");

  public RiassuntoRichiestaDietaSpecialePanel(String id) {
    super(id);
  }

  public RiassuntoRichiestaDietaSpecialePanel(
      UtenteServiziRistorazione iscrizione, DietaSpeciale dietaSpeciale) {
    super("riassuntoPanel");

    this.iscrizione = iscrizione;
    this.dietaSpeciale = dietaSpeciale;

    createFeedBackPanel();
    fillDati(dietaSpeciale);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(Object dati) {
    DietaSpeciale dieta = (DietaSpeciale) dati;

    Label nomeRichiedente = new Label("nomeRichiedente", dieta.getNomeRichiedente());
    nomeRichiedente.setVisible(PageUtil.isStringValid(dieta.getNomeRichiedente()));
    addOrReplace(nomeRichiedente);

    Label cognomeRichiedente = new Label("cognomeRichiedente", dieta.getCognomeRichiedente());
    cognomeRichiedente.setVisible(PageUtil.isStringValid(dieta.getCognomeRichiedente()));
    addOrReplace(cognomeRichiedente);

    Label cfRichiedente = new Label("cfRichiedente", dieta.getCfRichiedente());
    cfRichiedente.setVisible(PageUtil.isStringValid(dieta.getCfRichiedente()));
    addOrReplace(cfRichiedente);

    Label emailRichiedente = new Label("emailRichiedente", dieta.getEmailRichiedente());
    emailRichiedente.setVisible(PageUtil.isStringValid(dieta.getEmailRichiedente()));
    addOrReplace(emailRichiedente);

    Label nomeIscritto = new Label("nomeIscritto", dieta.getNomeIscritto());
    nomeIscritto.setVisible(PageUtil.isStringValid(dieta.getNomeIscritto()));
    addOrReplace(nomeIscritto);

    Label cognomeIscritto = new Label("cognomeIscritto", dieta.getCognomeIscritto());
    cognomeIscritto.setVisible(PageUtil.isStringValid(dieta.getCognomeIscritto()));
    addOrReplace(cognomeIscritto);

    Label cfIscritto = new Label("cfIscritto", dieta.getCfIscritto());
    cfIscritto.setVisible(PageUtil.isStringValid(dieta.getCfIscritto()));
    addOrReplace(cfIscritto);

    Label tipoDieta = new Label("tipoDieta", dieta.getTipoDieta());
    tipoDieta.setVisible(LabelFdCUtil.checkIfNotNull((dieta.getTipoDieta())));
    addOrReplace(tipoDieta);

    String descrizioneMenu = "";
    if (LabelFdCUtil.checkIfNotNull(dieta.getComboMenu())) {
      descrizioneMenu = dieta.getComboMenu().getDescrizione();
    }
    Label tipoMenu = new Label("tipoMenu", descrizioneMenu);
    tipoMenu.setVisible(
        LabelFdCUtil.checkIfNotNull((dieta.getComboMenu()))
            && dieta.getTipoDieta().equals(TipoDietaSpecialeEnum.ETICO_RELIGIOSI));
    addOrReplace(tipoMenu);

    String valoreAnafilassi = dieta.isAnafilassi() ? "Sì" : "No";
    Label anafilassi = new Label("anafilassi", valoreAnafilassi);
    anafilassi.setVisible(
        LabelFdCUtil.checkIfNotNull((dieta.isAnafilassi()))
            && dieta.getTipoDieta().equals(TipoDietaSpecialeEnum.DI_SALUTE));
    addOrReplace(anafilassi);

    Label nomeFile = new Label("nomeFile", dieta.getNomeFileAttestazioneMedica());
    nomeFile.setVisible(
        PageUtil.isStringValid(dieta.getNomeFileAttestazioneMedica())
            && dieta.getTipoDieta().equals(TipoDietaSpecialeEnum.DI_SALUTE));
    addOrReplace(nomeFile);

    Label noteIntegrative = new Label("noteIntegrative", dieta.getNoteIntegrative());
    noteIntegrative.setVisible(PageUtil.isStringValid(dieta.getNoteIntegrative()));
    addOrReplace(noteIntegrative);

    Label scuola = new Label("scuola", dieta.getScuola());
    scuola.setVisible(PageUtil.isStringValid(dieta.getScuola()));
    addOrReplace(scuola);

    Label classe = new Label("classe", dieta.getClasse());
    classe.setVisible(LabelFdCUtil.checkIfNotNull(dieta.getClasse()));
    addOrReplace(classe);

    Label sezione = new Label("sezione", dieta.getSezione());
    sezione.setVisible(PageUtil.isStringValid(dieta.getSezione()));
    addOrReplace(sezione);

    String giorniRientro = "";
    List<GiorniRientroScuola> listaGiorniSelezionati = new ArrayList<GiorniRientroScuola>();
    if (LabelFdCUtil.checkIfNotNull(dieta.getListaGiorniRientri())) {
      listaGiorniSelezionati =
          dieta.getListaGiorniRientri().stream()
              .filter(elem -> elem.isSelezionato())
              .collect(Collectors.toList());
      for (GiorniRientroScuola elem : listaGiorniSelezionati) {
        giorniRientro = giorniRientro.concat(elem.getGiornoRientro().value().concat("\n"));
      }
    }
    MultiLineLabel rientri = new MultiLineLabel("rientri", giorniRientro);
    rientri.setVisible(
        LabelFdCUtil.checkIfNotNull(dieta.getListaGiorniRientri())
            && LabelFdCUtil.checkIfNotNull(listaGiorniSelezionati)
            && !LabelFdCUtil.checkEmptyList(listaGiorniSelezionati));
    rientri.setEscapeModelStrings(false);
    addOrReplace(rientri);

    containerAlertIndietro.setVisible(
        dieta.getTipoDieta().equals(TipoDietaSpecialeEnum.DI_SALUTE)
            && LabelFdCUtil.checkIfNotNull(dieta.getByteFileAttestazioneMedica()));
    addOrReplace(containerAlertIndietro);

    LaddaAjaxLink<?> linkConferma =
        new LaddaAjaxLink<Object>("conferma", Type.Primary) {

          private static final long serialVersionUID = 8448456698988764992L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());

            setResponsePage(new ConfermaRichiestaDietaSpecialePage(dieta));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel<?> model) {
            return setDefaultModel(model);
          }
        };
    linkConferma.setLabel(Model.of(getString("RiassuntoRichiestaDietaSpecialePanel.conferma")));
    addOrReplace(linkConferma);

    Link linkAnnulla =
        new Link("annulla") {

          private static final long serialVersionUID = 5240504796447548753L;

          @Override
          public void onClick() {
            UtenteServiziRistorazioneDieteSpeciali iscrizioneDiete =
                new UtenteServiziRistorazioneDieteSpeciali();
            iscrizioneDiete.setIscritto(iscrizione);
            setResponsePage(new RichiestaDietaSpecialePage(iscrizioneDiete));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    addOrReplace(linkAnnulla);

    Link linkIndietro =
        new Link("indietro") {

          private static final long serialVersionUID = 7240043548168296125L;

          @Override
          public void onClick() {
            setResponsePage(new RichiestaDietaSpecialePage(iscrizione, dieta));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    addOrReplace(linkIndietro);
  }
}
