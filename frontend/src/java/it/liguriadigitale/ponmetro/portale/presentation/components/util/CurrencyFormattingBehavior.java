package it.liguriadigitale.ponmetro.portale.presentation.components.util;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class CurrencyFormattingBehavior extends Behavior {
  private static final long serialVersionUID = -4754030237711643182L;

  private final String format;

  public CurrencyFormattingBehavior(final String format) {
    this.format = format;
  }

  @Override
  public void renderHead(Component component, IHeaderResponse response) {
    super.renderHead(component, response);
    String script =
        ""
            + "var currencyInputField = $('#"
            + component.getMarkupId()
            + "');"
            + "currencyInputField.val(currencyInputField.val() + '"
            + format
            + "');"
            + "currencyInputField.on('focus', function(){"
            + "currencyInputField.val(currencyInputField.val().replace('"
            + format
            + "', ''));"
            + "});"
            + "currencyInputField.on('blur', function(){"
            + "currencyInputField.val(currencyInputField.val() + '"
            + format
            + "')"
            + "});";

    response.render(OnDomReadyHeaderItem.forScript(script));
  }
}
