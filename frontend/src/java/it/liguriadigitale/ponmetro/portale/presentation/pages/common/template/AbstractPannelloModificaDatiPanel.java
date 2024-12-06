package it.liguriadigitale.ponmetro.portale.presentation.pages.common.template;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.roles.RolesUtil;
import java.io.Serializable;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class AbstractPannelloModificaDatiPanel extends BasePanel {

  private static final long serialVersionUID = 1257639040270528420L;

  protected String testoDescrizionepanel;

  @SuppressWarnings("rawtypes")
  protected AjaxLink bottoneModifica;

  protected boolean visibile;

  protected boolean editabile;

  public AbstractPannelloModificaDatiPanel(String id) {
    super(id);
    visibile = true;
    editabile = false;
  }

  public AbstractPannelloModificaDatiPanel(String id, boolean visibile) {
    super(id);
    this.visibile = visibile;
    editabile = false;
  }

  public AbstractPannelloModificaDatiPanel(String id, boolean visibile, boolean editabile) {
    super(id);
    this.visibile = visibile;
    this.editabile = editabile;
  }

  @SuppressWarnings("rawtypes")
  protected abstract AbstracFrameworkForm creaFormModifica(Object dati, Fragment editFragment);

  protected abstract String creaPannello(Object dati, Fragment viewFragment);

  @SuppressWarnings("rawtypes")
  public AjaxLink getBottoneModifica() {
    return bottoneModifica;
  }

  @SuppressWarnings("rawtypes")
  public void setBottoneModifica(AjaxLink bottoneModifica) {
    this.bottoneModifica = bottoneModifica;
  }

  public String getTestoDescrizionepanel() {
    return testoDescrizionepanel;
  }

  public void setTestoDescrizionepanel(String testoDescrizionepanel) {
    this.testoDescrizionepanel = testoDescrizionepanel;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(final Object dati) {
    removeAll();

    setTestoDescrizionepanel(creaTestoDescrizionePanel(dati));
    add(
        new Label(
            "descrizionepanel",
            new Model() {
              private static final long serialVersionUID = 1L;

              @Override
              public Serializable getObject() {
                return testoDescrizionepanel;
              }
            }));
    Fragment viewFragment = fillDatiPannelloView(dati);
    this.add(viewFragment);
    if (editabile)
      viewFragment.replaceWith(AbstractPannelloModificaDatiPanel.this.fillDatiPannelloEdit(dati));
    createFeedBackPanel();
  }

  protected Fragment fillDatiPannelloView(final Object dati) {
    final Fragment viewFragment = new Fragment("group", "view", this);
    String testoEtichettaBottone = creaPannello(dati, viewFragment);

    creaBottoneModifica(dati, viewFragment, testoEtichettaBottone);

    return viewFragment;
  }

  @SuppressWarnings("rawtypes")
  protected void creaBottoneModifica(
      final Object dati, final Fragment viewFragment, String testoEtichettaBottone) {
    bottoneModifica =
        new AjaxLink("modificaButton") {

          private static final long serialVersionUID = 3861763849355173127L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            viewFragment.replaceWith(
                AbstractPannelloModificaDatiPanel.this.fillDatiPannelloEdit(dati));
            target.add(AbstractPannelloModificaDatiPanel.this);
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return super.setDefaultModel(model);
          }
        };

    bottoneModifica.add(new Label("buttontext", Model.of(testoEtichettaBottone)));
    bottoneModifica.setOutputMarkupId(true);
    bottoneModifica.setVisibilityAllowed(visibile);
    viewFragment.add(bottoneModifica);

    try {
      RolesUtil.getInstance().autorizzaFunzione(bottoneModifica, this.getClass());
    } catch (BusinessException e) {
      log.error("[NavigationPanel] Errore nel reperimento della lista Ruoli Autorizzati", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  @SuppressWarnings("rawtypes")
  protected Fragment fillDatiPannelloEdit(final Object dati) {
    final Fragment editFragment = new Fragment("group", "edit", this);
    AbstracFrameworkForm formModifica = creaFormModifica(dati, editFragment);
    AjaxButton pulsanteAnnulla = creaPulsanteAnnulla(editFragment, dati);
    formModifica.add(pulsanteAnnulla);
    editFragment.add(formModifica);
    // add(new Label("descrizionepanel", Model.of(testoDescrizionepanel)));
    return editFragment;
  }

  protected AjaxButton creaPulsanteAnnulla(final Fragment editFragment, final Object dati) {
    AjaxButton annulla =
        new AjaxButton("annullaButton") {

          private static final long serialVersionUID = -8161087481486549208L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {

            Fragment viewFragment =
                AbstractPannelloModificaDatiPanel.this.fillDatiPannelloView(dati);
            editFragment.replaceWith(viewFragment);
            target.add(AbstractPannelloModificaDatiPanel.this);
          }
        };
    annulla.setDefaultFormProcessing(false);
    return annulla;
  }

  protected abstract String creaTestoDescrizionePanel(Object dati);
}
