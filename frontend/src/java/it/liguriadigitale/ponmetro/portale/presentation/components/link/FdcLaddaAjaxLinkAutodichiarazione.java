package it.liguriadigitale.ponmetro.portale.presentation.components.link;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxCallListener;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaBehavior;
import java.io.Serializable;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.model.IModel;

public abstract class FdcLaddaAjaxLinkAutodichiarazione<T>
    extends FdcBootstrapAjaxLinkAutodichiarazione<T> {

  /** */
  private static final long serialVersionUID = 7919562541339606986L;

  /** The behavior that sets the Ladda UI specific CSS classes and attributes */
  private final LaddaBehavior laddaBehavior = new LaddaBehavior();

  /**
   * Constructor.
   *
   * @param id The component id
   * @param type The Bootstrap type of the button
   */
  public FdcLaddaAjaxLinkAutodichiarazione(String id, Buttons.Type type) {
    super(id, type);
  }

  /**
   * Constructor.
   *
   * @param id The component id
   * @param model The model to use for the label
   * @param type The Bootstrap type of the button
   */
  public FdcLaddaAjaxLinkAutodichiarazione(String id, IModel<T> model, Buttons.Type type) {
    super(id, model, type);
  }

  /**
   * Constructor.
   *
   * @param id The component id
   * @param model The model to use for the label
   * @param type The Bootstrap type of the button
   * @param labelModel The model for the link's label
   */
  public <L extends Serializable> FdcLaddaAjaxLinkAutodichiarazione(
      String id, IModel<T> model, Buttons.Type type, IModel<L> labelModel) {
    super(id, model, type, labelModel);
  }

  @Override
  protected void onInitialize() {
    super.onInitialize();

    add(laddaBehavior);
  }

  /**
   * Sets the effect to use
   *
   * @param effect The effect to use
   * @return {@code this}, for chaining
   */
  public FdcLaddaAjaxLinkAutodichiarazione<T> setEffect(LaddaBehavior.Effect effect) {
    this.laddaBehavior.withEffect(effect);
    return this;
  }

  /**
   * Sets the color for the spinner
   *
   * @param color The color for the spinner
   * @return {@code this}, for chaining
   */
  public FdcLaddaAjaxLinkAutodichiarazione<T> setSpinnerColor(String color) {
    this.laddaBehavior.withSpinnerColor(color);
    return this;
  }

  /**
   * Sets the size of the spinner in pixels
   *
   * @param size The size of the spinner in pixels
   * @return {@code this}, for chaining
   */
  public FdcLaddaAjaxLinkAutodichiarazione<T> setSpinnerSize(int size) {
    this.laddaBehavior.withSpinnerSize(size);
    return this;
  }

  @Override
  protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
    super.updateAjaxAttributes(attributes);
    attributes.getAjaxCallListeners().add(new LaddaAjaxCallListener());
  }

  @Override
  protected <L extends Serializable> Component newLabel(String markupId, IModel<L> model) {
    Component label = super.newLabel(markupId, model);
    label.setRenderBodyOnly(false);
    label.add(AttributeModifier.append("class", "ladda-label"));
    return label;
  }
}
