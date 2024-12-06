package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca.panel;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.framework.presentation.components.form.components.types.CampoTesto;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Tag;
import it.liguriadigitale.ponmetro.portale.pojo.globo.ricerca.RicercaGlobo;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.globo.TagArgomentiRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca.GloboRicercaPage;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class GloboPannelloRicerca extends Panel {

  private static final long serialVersionUID = 3139406566407180457L;

  private Log log = LogFactory.getLog(GloboPannelloRicerca.class);
  private RicercaGlobo ricerca;

  public GloboPannelloRicerca(String id) {
    this(id, new RicercaGlobo());
  }

  public GloboPannelloRicerca(String id, RicercaGlobo ricerca) {
    super(id);
    setOutputMarkupId(true);
    this.ricerca = ricerca;
    creaForm();
  }

  private void creaForm() {
    AbstracFrameworkForm<RicercaGlobo> form =
        new AbstracFrameworkForm<RicercaGlobo>("form", ricerca) {

          private static final long serialVersionUID = 9139658281933501327L;

          private Log log = LogFactory.getLog(AbstracFrameworkForm.class);

          DropDownChoice<Tag> combo;
          DropDownChoice<Tag> comboChild;
          CampoTesto campoLibero;

          @Override
          public void addElementiForm() {
            creaCombo(ricerca);
            creaComboChild();
            campoLibero = new CampoTesto("testoLibero");
            addOrReplace(campoLibero);
          }

          @SuppressWarnings({"rawtypes", "unchecked"})
          private void creaComboChild() {
            TagArgomentiRenderer choiceRenderer = new TagArgomentiRenderer();
            Tag argomento = new Tag();

            comboChild =
                this.creaDropDownChoice(
                    new ArrayList(), "comboChild", choiceRenderer, Model.of(argomento));
            comboChild.setOutputMarkupId(true);
            comboChild.setOutputMarkupPlaceholderTag(true);
            comboChild.setVisible(!comboChild.getChoices().isEmpty());
            this.add(comboChild);
          }

          private List<Tag> getTagsChild(Tag modelObject) {

            if (modelObject != null && modelObject.getChild() != null) {
              return modelObject.getChild();
            } else {
              return new ArrayList<>();
            }
          }

          @SuppressWarnings("unchecked")
          private void creaCombo(RicercaGlobo ricerca) {
            TagArgomentiRenderer choiceRenderer = new TagArgomentiRenderer();
            Tag argomento = new Tag();

            combo =
                this.creaDropDownChoice(
                    ricerca.getTags(), "combo", choiceRenderer, Model.of(argomento));
            combo.setOutputMarkupId(true);
            this.add(combo);
            combo.add(creaBehaviorComboTipoCertificato());
          }

          private AjaxFormComponentUpdatingBehavior creaBehaviorComboTipoCertificato() {
            return new AjaxFormComponentUpdatingBehavior("change") {

              private static final long serialVersionUID = 3167432819809832942L;

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                comboChild.setChoices(getTagsChild(combo.getModelObject()));
                comboChild.setVisible(!comboChild.getChoices().isEmpty());
                target.add(comboChild);
              }
            };
          }

          @Override
          protected void onSubmit() {
            ricerca = copioDatiSuModelloForm(getModelObject());
            GloboRicercaPage page = (GloboRicercaPage) getParent().getPage();
            page.refresh(ricerca);
          }

          private RicercaGlobo copioDatiSuModelloForm(RicercaGlobo modelObject) {
            modelObject.setCombo(combo.getModelObject());
            if (comboChild.getChoices().isEmpty()) {
              modelObject.setComboChild(null);
            } else {
              modelObject.setComboChild(comboChild.getModelObject());
            }
            return modelObject;
          }
        };
    addOrReplace(form);
  }
}
