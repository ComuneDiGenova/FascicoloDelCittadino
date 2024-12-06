package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel.RichiestaDietaSpecialeNewPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel.modale.ModaleRichiestaDietaSpecialePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.pojo.UtenteServiziRistorazioneDieteSpeciali;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpecialeValida;
import it.liguriadigitale.ponmetro.serviziristorazione.model.GiornoRientro.RientroEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;
import java.util.ArrayList;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class RichiestaDietaSpecialePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5163948353298715225L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RichiestaDietaSpecialePage(UtenteServiziRistorazioneDieteSpeciali datiIscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    WebMarkupContainer containerModaleDieta = new WebMarkupContainer("containerModaleDieta");
    containerModaleDieta.setOutputMarkupId(true);
    containerModaleDieta.setOutputMarkupPlaceholderTag(true);

    ModaleRichiestaDietaSpecialePanel modaleDieteSpeciali =
        new ModaleRichiestaDietaSpecialePanel<String>(
            "modaleDieteSpeciali", datiIscrizione.getDietaValida());

    String dietaId = "";
    String dietaBimbo = "";
    String dietaTipo = "";
    String dietaMenu = "";
    if (LabelFdCUtil.checkIfNotNull(datiIscrizione)
        && LabelFdCUtil.checkIfNotNull(datiIscrizione.getDietaValida())) {
      dietaId = datiIscrizione.getDietaValida().getIdDieta();
      dietaBimbo = datiIscrizione.getDietaValida().getNome();
      dietaTipo = datiIscrizione.getDietaValida().getTipoDieta();
      dietaMenu = datiIscrizione.getDietaValida().getTipoMenu();
    }

    NotEmptyLabel dietaLabel =
        new NotEmptyLabel(
            "dieta",
            new StringResourceModel("RichiestaDietaSpecialePage.info", this)
                .setParameters(dietaBimbo, dietaMenu, dietaTipo, dietaId));

    modaleDieteSpeciali.myAdd(dietaLabel);
    modaleDieteSpeciali.addOrReplace(creaBtnSi(modaleDieteSpeciali, datiIscrizione));
    modaleDieteSpeciali.addOrReplace(creaBtnNo(modaleDieteSpeciali, datiIscrizione));
    containerModaleDieta.addOrReplace(modaleDieteSpeciali);

    containerModaleDieta.setVisible(
        checkPresenteUnaDietaAccettata(datiIscrizione.getDietaValida()));

    addOrReplace(containerModaleDieta);

    RichiestaDietaSpecialeNewPanel dieteSpecialiPanel =
        new RichiestaDietaSpecialeNewPanel(
            "richiestaDietaSpecialeNewPanel",
            createDietaSpeciale(datiIscrizione.getIscritto()),
            datiIscrizione.getIscritto());
    dieteSpecialiPanel.setVisible(!checkPresenteUnaDietaAccettata(datiIscrizione.getDietaValida()));
    addOrReplace(dieteSpecialiPanel);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RichiestaDietaSpecialePage(
      UtenteServiziRistorazioneDieteSpeciali datiIscrizione, boolean isNuovaIscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    WebMarkupContainer containerModaleDieta = new WebMarkupContainer("containerModaleDieta");
    containerModaleDieta.setOutputMarkupId(true);
    containerModaleDieta.setOutputMarkupPlaceholderTag(true);

    ModaleRichiestaDietaSpecialePanel modaleDieteSpeciali =
        new ModaleRichiestaDietaSpecialePanel<String>(
            "modaleDieteSpeciali", datiIscrizione.getDietaValida());

    String dietaMess = "";
    if (LabelFdCUtil.checkIfNotNull(datiIscrizione)
        && LabelFdCUtil.checkIfNotNull(datiIscrizione.getDietaValida())) {
      dietaMess = datiIscrizione.getDietaValida().getIdDieta();
    }

    String info = getString("RichiestaDietaSpecialePage.info").concat(" ");
    Label dietaLabel = new Label("dieta", info.concat(dietaMess));

    modaleDieteSpeciali.myAdd(dietaLabel);
    modaleDieteSpeciali.addOrReplace(creaBtnSi(modaleDieteSpeciali, datiIscrizione));
    modaleDieteSpeciali.addOrReplace(creaBtnNo(modaleDieteSpeciali, datiIscrizione));
    containerModaleDieta.addOrReplace(modaleDieteSpeciali);

    containerModaleDieta.setVisible(!isNuovaIscrizione);
    addOrReplace(containerModaleDieta);

    RichiestaDietaSpecialeNewPanel dieteSpecialiPanel =
        new RichiestaDietaSpecialeNewPanel(
            "richiestaDietaSpecialeNewPanel",
            createDietaSpeciale(datiIscrizione.getIscritto()),
            datiIscrizione.getIscritto());
    dieteSpecialiPanel.setVisible(isNuovaIscrizione);
    addOrReplace(dieteSpecialiPanel);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public RichiestaDietaSpecialePage(
      UtenteServiziRistorazione iscrizione, DietaSpeciale dietaSpeciale) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RichiestaDietaSpecialeNewPanel dieteSpecialiPanel =
        new RichiestaDietaSpecialeNewPanel(
            "richiestaDietaSpecialeNewPanel", dietaSpeciale, iscrizione);
    addOrReplace(dieteSpecialiPanel);

    WebMarkupContainer containerModaleDieta = new WebMarkupContainer("containerModaleDieta");
    containerModaleDieta.setOutputMarkupId(true);
    containerModaleDieta.setOutputMarkupPlaceholderTag(true);
    containerModaleDieta.setVisible(false);
    addOrReplace(containerModaleDieta);
  }

  private DietaSpeciale createDietaSpeciale(UtenteServiziRistorazione iscrizione) {
    DietaSpeciale dietaSpeciale = new DietaSpeciale();
    dietaSpeciale.setNomeRichiedente(getUtente().getNome());
    dietaSpeciale.setCognomeRichiedente(getUtente().getCognome());
    dietaSpeciale.setCfRichiedente(getUtente().getCodiceFiscaleOperatore());
    dietaSpeciale.setEmailRichiedente(getUtente().getMail());

    dietaSpeciale.setNomeIscritto(iscrizione.getNome());
    dietaSpeciale.setCognomeIscritto(iscrizione.getCognome());
    dietaSpeciale.setCfIscritto(iscrizione.getCodiceFiscale());

    dietaSpeciale.setDataRichiesta(LocalDate.now());

    dietaSpeciale.setListaGiorniRientriSelezionati(new ArrayList<RientroEnum>());

    return dietaSpeciale;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnSi(
      Modal<String> modale, UtenteServiziRistorazioneDieteSpeciali dati) {
    FdCButtonBootstrapAjaxLink<Object> btnSi =
        new FdCButtonBootstrapAjaxLink<Object>("btnSi", Type.Primary) {

          private static final long serialVersionUID = -2015577886005462510L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new RichiestaDietaSpecialePage(dati, true));
          }
        };

    btnSi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RichiestaDietaSpecialePage.si", RichiestaDietaSpecialePage.this)));

    btnSi.add(new AttributeAppender("style", "min-width: 100px;"));

    return btnSi;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnNo(
      Modal<String> modale, UtenteServiziRistorazioneDieteSpeciali dati) {
    FdCButtonBootstrapAjaxLink<Object> btnNo =
        new FdCButtonBootstrapAjaxLink<Object>("btnNo", Type.Primary) {

          private static final long serialVersionUID = -1934054875656427233L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            modale.close(target);

            setResponsePage(new DieteSpecialiPage(dati.getIscritto()));
          }
        };

    btnNo.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RichiestaDietaSpecialePage.no", RichiestaDietaSpecialePage.this)));

    btnNo.add(new AttributeAppender("style", "min-width: 100px;"));

    return btnNo;
  }

  private boolean checkPresenteUnaDietaAccettata(DatiDietaSpecialeValida dieta) {
    return LabelFdCUtil.checkIfNotNull(dieta) ? true : false;
  }
}
