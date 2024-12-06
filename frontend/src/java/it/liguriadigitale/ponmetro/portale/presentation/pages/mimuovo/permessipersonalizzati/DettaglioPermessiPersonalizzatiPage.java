package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaCompletaResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo.ComboPermessoDisabile;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.converter.RichiestaPermessoPersonalizzatoConverter;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.riepilogo.RiepilogoPermessiPersonalizzatiPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class DettaglioPermessiPersonalizzatiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7660309620639141558L;
  private Form form;

  public DettaglioPermessiPersonalizzatiPage(int idDomanda, String descrizioneProcedimento) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    try {

      DomandaCompletaResponse domandaCompletaResponse =
          ServiceLocator.getInstance().getServiziPermessiPersonalizzati().getDomanda(idDomanda);

      RichiestaPermessoPersonalizzatoConverter richiestaPermessoPersonalizzatoConverter =
          new RichiestaPermessoPersonalizzatoConverter(domandaCompletaResponse);

      IModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel =
          Model.of(
              richiestaPermessoPersonalizzatoConverter.toRichiestaPermessoPersonalizzato(
                  descrizioneProcedimento, idDomanda));

      getSession().removeAttribute("listaPermessiDisabili");
      // inizializzo la combo in sessione
      new ComboPermessoDisabile(
          "combo",
          richiestaPermessoPersonalizzatoModel
              .getObject()
              .getSoggettiCoinvolti()
              .getDisabile()
              .getCodiceFiscaleDisabile());

      CompoundPropertyModel<RichiestaPermessoPersonalizzato>
          richiestaPermessoPersonalizzatoCompoundModel =
              new CompoundPropertyModel<RichiestaPermessoPersonalizzato>(
                  richiestaPermessoPersonalizzatoModel);
      addOrReplace(form = new Form<>("form", richiestaPermessoPersonalizzatoModel));

      setOutputMarkupId(true);

      form.addOrReplace(
          new RiepilogoPermessiPersonalizzatiPanel(
              "riepilogoPermessiPersonalizzatiPanel",
              richiestaPermessoPersonalizzatoCompoundModel,
              idDomanda));

      createFeedBackPanel();

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("elenco permessi personalizzati"));
    }
  }

  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioMiMuovo", "io Mi Muovo"));
    listaBreadcrumb.add(new BreadcrumbFdC("permessiPersonalizzati", "Permessi personalizzati"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("dettaglioPermessiPersonalizzati", "Dettaglio Permessi personalizzati"));
    return listaBreadcrumb;
  }
}
