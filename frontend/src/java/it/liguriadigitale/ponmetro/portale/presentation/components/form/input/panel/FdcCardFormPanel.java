package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

public abstract class FdcCardFormPanel extends BasePanel {

  private static final long serialVersionUID = 66884533221603716L;

  WebMarkupContainer wmkCardBody;
  boolean conCornice = true;
  WebMarkupContainer wmkCornice;
  protected Form<Object> form;
  Object pojo;

  public FdcCardFormPanel(String id, Object pojo) {
    super(id);
    this.pojo = pojo;
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  // protected abstract void setForm();
  protected void setForm(Object pojo) {

    log.debug("Set Form: " + pojo);

    CompoundPropertyModel pojoModel = new CompoundPropertyModel<>(pojo);

    wmkCornice = new WebMarkupContainer("wmkCornice");

    log.debug("Set Cornice: " + wmkCornice);

    addOrReplace(wmkCornice);

    form = new Form<Object>("form", pojoModel);
    wmkCornice.addOrReplace(form);
  }

  @Override
  public void fillDati(Object dati) {
    setForm(pojo);
  }

  public WebMarkupContainer getWmkCardBody() {
    return wmkCardBody;
  }

  public void setWmkCardBody(WebMarkupContainer wmkCardBody) {
    this.wmkCardBody = wmkCardBody;
  }

  public boolean isConCornice() {
    return conCornice;
  }

  public void setConCornice(boolean conCornice) {
    this.conCornice = conCornice;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    log.debug("Corniceeee: " + wmkCornice);
    if (isConCornice())
      wmkCornice.add(AttributeModifier.append("class", "form-servizi-anagrafici"));
  }
}
