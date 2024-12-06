package it.liguriadigitale.ponmetro.portale.presentation.components.form.input;

import it.liguriadigitale.ponmetro.portale.pojo.componentifascicolo.LabelValue;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class FdCSelect2<T> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  private Log log = LogFactory.getLog(getClass());

  protected TextField<String> textField;

  String wicketId;
  IModel<T> modelField;
  Label label;
  Component pannello;

  String etichetta;
  String nomePannello;

  protected Label lblCombo;
  protected FdCDropDownChoice<T> combo;

  public FdCSelect2(String wicketId, IModel<T> modelField) {
    super(wicketId);
    this.wicketId = wicketId;
    this.modelField = modelField;

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    textField = new TextField<>("textField", Model.of(""));
    textField.setOutputMarkupId(true);
    textField.setOutputMarkupPlaceholderTag(true);

    textField.add(
        new OnChangeAjaxBehaviorSenzaIndicator() {

          private static final long serialVersionUID = -3709711666730574150L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            List<LabelValue> elementiCombo = getElementiCombo();

            StringBuilder sb = new StringBuilder();
            sb.append(
                "$(document).ready(function() { \r\n"
                    + "$('#"
                    + combo.getMarkupId()
                    + "')\r\n"
                    + "    .find('option')\r\n"
                    + "    .remove()\r\n"
                    + "    .end()\r\n"
                    + "    .append('');");

            for (LabelValue labelValue : elementiCombo) {

              String desc = labelValue.getDesc().replace("'", "&#39;");

              sb.append(
                  "$('#"
                      + combo.getMarkupId()
                      + "').append('<option value=\""
                      + labelValue.getId()
                      + "\">"
                      + desc
                      + "</option>');\r\n");
            }
            sb.append(
                "$('#" + combo.getMarkupId() + "').selectpicker('refresh');\r\n" + "} );\r\n");

            target.appendJavaScript(sb.toString());
          }
        });

    addOrReplace(textField);
  }

  public FdCSelect2(String wicketId, IModel<T> modelField, Component pannello) {
    this(wicketId, modelField);
    this.pannello = pannello;
  }

  public FdCSelect2(
      String wicketId, IModel<T> modelField, Component pannello, String nomePannello) {
    this(wicketId, modelField);
    this.pannello = pannello;
    this.nomePannello = nomePannello;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    String script = "$('#" + combo.getMarkupId() + "').selectpicker();\r\n";

    script =
        script
            + "$('#"
            + getMarkupId()
            + " .dropdown-menu .form-control').on(\"input\", function() {\r\n"
            + "		$('#"
            + textField.getMarkupId()
            + "').val($('#"
            + getMarkupId()
            + " .dropdown-menu .form-control').val()).change(); \r\n"
            + "var el = document.getElementById('"
            + textField.getMarkupId()
            + "');el.dispatchEvent(new Event('change'));"
            + "});";

    response.render(OnDomReadyHeaderItem.forScript(script));
  }

  private void creaLabelEtichettaGenerandoResourceId(Component pannello, String wicketId) {

    String resourceId =
        LabelFdCUtil.checkIfNotNull(nomePannello)
            ? nomePannello
            : pannello.getClass().getSimpleName();
    resourceId = resourceId + "." + wicketId;

    log.debug("CP nomePannello = " + nomePannello + "\n resourceId = " + resourceId);

    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  private void creaLabelEtichetta(Component pannello, String resourceId, String wicketId) {
    etichetta = getLocalizer().getString(resourceId, pannello);

    log.debug("CP etichetta = " + etichetta);

    label = new NotEmptyLabel("lblCombo", etichetta);
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", "textField" + wicketId));
    label.add(new AttributeModifier("class", "active"));

    addOrReplace(label);
  }

  protected abstract List<LabelValue> getElementiCombo();

  public FdCDropDownChoice<T> getCombo() {
    return combo;
  }

  public void setCombo(FdCDropDownChoice<T> combo) {
    this.combo = combo;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

    combo.setLabel(Model.of(etichetta));
  }
}
