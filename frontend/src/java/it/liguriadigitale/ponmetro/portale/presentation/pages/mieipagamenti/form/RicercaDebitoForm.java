package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicoloDatiGenerali;
import it.liguriadigitale.ponmetro.portale.pojo.mipGlobali.TipologieSelezionate;
import it.liguriadigitale.ponmetro.portale.presentation.common.mip.MipErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.TipologiaServiziPagamentiRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.PagamentiFormPage;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class RicercaDebitoForm extends AbstracFrameworkForm<TipologieSelezionate> {

  private static final long serialVersionUID = 4293989726736103684L;

  private List<TipologiaEntrata> listaTipologiaEntrata;

  @SuppressWarnings("unused")
  private Utente utente;

  @SuppressWarnings("unused")
  private TipologiaEntrata tipologiaEntrata;

  public RicercaDebitoForm(
      String id,
      TipologieSelezionate model,
      List<TipologiaEntrata> listaTipologiaEntrata,
      Utente utente) {
    super(id, model);

    this.listaTipologiaEntrata = listaTipologiaEntrata;
    this.utente = utente;
    this.tipologiaEntrata = model.getTipologiaEntrata();

    addElementiForm(this.listaTipologiaEntrata, utente);
  }

  public void addElementiForm(List<TipologiaEntrata> listaTipologiaEntrata, Utente utente) {

    TipologieSelezionate model = getModelObject();

    TipologieServizioDropDownChoise tipologieServizioDropDownChoise =
        creaDropDownChoice(
            listaTipologiaEntrata,
            "comboServizi",
            new TipologiaServiziPagamentiRenderer(),
            new PropertyModel<>(model, "comboServizi"));

    tipologieServizioDropDownChoise.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -4939216821511965462L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (tipologieServizioDropDownChoise.getModelObject() != null) {
              try {
                List<DebitoMipFascicoloDatiGenerali> listaDebitiPerServizio =
                    ServiceLocator.getInstance()
                        .getServiziMipGlobali()
                        .getListaDebitiGeneraliPerServizioOrdinatiPerDataCreazione(
                            utente, tipologieServizioDropDownChoise.getModelObject());

                PagamentiFormPage pagamentiFormPage = (PagamentiFormPage) getPage();
                pagamentiFormPage.aggiornaPagamenti(listaDebitiPerServizio);
                target.add(pagamentiFormPage);

              } catch (BusinessException | ApiException | IOException e) {
                log.debug("Errore durante la chiamata delle API MIP GLOBALI", e);
                throw new RestartResponseAtInterceptPageException(MipErrorPage.class);
              }
            }
          }
        });

    tipologieServizioDropDownChoise.setOutputMarkupId(true);
    add(tipologieServizioDropDownChoise);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  protected TipologieServizioDropDownChoise creaDropDownChoice(
      List lista, String idWicket, IChoiceRenderer choiceRenderer, IModel modello) {

    ComboLoadableDetachableModel choices = new ComboLoadableDetachableModel(lista);
    TipologieServizioDropDownChoise combo = new TipologieServizioDropDownChoise(idWicket, choices);
    combo.setChoiceRenderer(choiceRenderer);
    combo.setModel(modello);
    combo.setNullValid(false);
    return combo;
  }

  @Override
  public void addElementiForm() {}
}
