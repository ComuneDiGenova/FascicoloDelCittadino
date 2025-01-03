package it.liguriadigitale.ponmetro.portale.presentation.components.link;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.IBootstrapButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.PanelMarkupSourcingStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Default {@link AjaxLink} which is styled by bootstrap
 *
 * @author miha
 */
public abstract class FdcBootstrapAjaxLink<T> extends AjaxLink<T>
    implements IBootstrapButton<FdcBootstrapAjaxLink<T>> {

  private final Icon icon;
  private final Component label;
  private final Component splitter;
  private final ButtonBehavior buttonBehavior;

  /** To use the splitter or not (true by default). */
  private boolean useSplitter = true;

  /**
   * Construct.
   *
   * @param id the components id
   * @param type the type of the button
   */
  public FdcBootstrapAjaxLink(final String id, final Buttons.Type type) {
    this(id, null, type);
  }

  /**
   * Construct.
   *
   * @param id The component id
   * @param model The component model
   * @param type the type of the button
   */
  public FdcBootstrapAjaxLink(String id, IModel<T> model, Buttons.Type type) {
    this(id, model, type, Model.of(""));
  }

  /**
   * Construct.
   *
   * @param id The component id
   * @param model The component model
   * @param type the type of the button
   * @param labelModel The model for the link's label
   */
  public <L extends Serializable> FdcBootstrapAjaxLink(
      String id, IModel<T> model, Buttons.Type type, IModel<L> labelModel) {
    super(id, model);

    add(buttonBehavior = new ButtonBehavior(type, Buttons.Size.Medium));
    add(icon = newIcon("icon"));
    add(splitter = newSplitter("splitter"));

    this.label = newLabel("label", wrap(labelModel));
    // label.setEscapeModelStrings(false);
    add(label);
  }

  /**
   * Creates a new icon component
   *
   * @param markupId the component id of the icon
   * @return new icon component
   */
  protected Icon newIcon(final String markupId) {
    return new Icon(markupId, (IconType) null);
  }

  /**
   * Creates a new label component
   *
   * @param markupId the component id of the label
   * @return new label component
   */
  protected <L extends Serializable> Component newLabel(final String markupId, IModel<L> model) {
    return new Label(markupId, model).setRenderBodyOnly(true);
  }

  /**
   * Creates a new splitter component. The splitter is visible only if icon is visible and
   * useSplitter is true.
   *
   * @param markupId the component id of the splitter
   * @return new splitter component
   */
  protected Component newSplitter(final String markupId) {
    return new WebMarkupContainer(markupId)
        .setRenderBodyOnly(true)
        .setEscapeModelStrings(false)
        .setVisible(false);
  }

  /** {@inheritDoc} */
  @Override
  protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
    return new PanelMarkupSourcingStrategy(true);
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();

    if (useSplitter) {
      splitter.setVisible(
          icon.hasIconType() && StringUtils.isNotEmpty(label.getDefaultModelObjectAsString()));
    }
  }

  /**
   * Sets the label of the button.
   *
   * @param label the new button label
   * @return reference to the current instance
   */
  public <L extends Serializable> FdcBootstrapAjaxLink<T> setLabel(IModel<L> label) {
    this.label.setDefaultModel(label);

    return this;
  }

  /**
   * Sets the button's icon which will be rendered in front of the label.
   *
   * @param iconType the new button icon type
   * @return reference to the current instance
   */
  public FdcBootstrapAjaxLink<T> setIconType(IconType iconType) {
    icon.setType(iconType);

    return this;
  }

  /** Sets the size. */
  public FdcBootstrapAjaxLink<T> setSize(Buttons.Size size) {
    buttonBehavior.setSize(size);

    return this;
  }

  /** Sets the type. */
  public FdcBootstrapAjaxLink<T> setType(Buttons.Type type) {
    this.buttonBehavior.setType(type);
    return this;
  }

  /**
   * @param value whether to use splitter between the icon and the label or not
   * @return this instance for chaining
   */
  public FdcBootstrapAjaxLink<T> useSplitter(boolean value) {
    this.useSplitter = value;
    return this;
  }

  /**
   * Sets whether this button should display inline or block
   *
   * @param block <code>true</code>, for block mode
   * @return this instance for chaining
   */
  public FdcBootstrapAjaxLink<T> setBlock(boolean block) {
    this.buttonBehavior.setBlock(block);
    return this;
  }
}
