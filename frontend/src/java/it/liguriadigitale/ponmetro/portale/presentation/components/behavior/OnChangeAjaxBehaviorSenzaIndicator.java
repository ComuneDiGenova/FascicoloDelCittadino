package it.liguriadigitale.ponmetro.portale.presentation.components.behavior;

import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;

public abstract class OnChangeAjaxBehaviorSenzaIndicator extends OnChangeAjaxBehavior {

  private static final long serialVersionUID = -3555818520102207431L;

  @Override
  protected String findIndicatorId() {
    return "";
  }
}
