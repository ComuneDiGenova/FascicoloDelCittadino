package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.ajax.PaginazioneAjaxFascicoloPanel;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;

public abstract class BasePanelGenericContent extends BasePanel {

  private static final long serialVersionUID = 1L;

  public static final String CSS_COLOR_SENDED = " color-blue ";
  public static final String CSS_COLOR_RECEIVED = " color-yellow ";
  public static final String CSS_COLOR_PROCESSING = " color-orange ";
  public static final String CSS_COLOR_DELETED = " color-brown ";
  public static final String CSS_COLOR_REJECT_KO = " color-red ";
  public static final String CSS_COLOR_SUCCESS_OK = " color-green ";
  public static final String CSS_COLOR_UNKNOWN = " color-grey ";

  public static final String CSS_NUM_COLUMN_ICON = " col-3 ";

  public static final String PREFIX_COLOR_FDC = " color-fc-";
  public static final String PREFIX_BADGE = " bg-";

  public static final String SUFFIX_COLOR_FDC_PRIMARY = "primary ";
  public static final String SUFFIX_COLOR_FDC_WARNING = "warning ";
  public static final String SUFFIX_COLOR_FDC_SECONDARY = "secondary ";
  public static final String SUFFIX_COLOR_FDC_DANGER = "danger ";
  public static final String SUFFIX_COLOR_FDC_SUCCESS = "success ";
  public static final String SUFFIX_COLOR_FDC_DARK = "dark ";
  public static final String SUFFIX_COLOR_FDC_LIGHT = "light ";
  public static final String SUFFIX_COLOR_FDC_INFO = "info ";
  public static final String SUFFIX_COLOR_FDC_SUN = "sun ";
  public static final String SUFFIX_COLOR_FDC_FLOWER = "flower ";

  public static final String CSS_BADGE_FDC_PRIMARY = PREFIX_BADGE + SUFFIX_COLOR_FDC_PRIMARY;
  public static final String CSS_BADGE_FDC_WARNING = PREFIX_BADGE + SUFFIX_COLOR_FDC_WARNING;
  public static final String CSS_BADGE_FDC_SECONDARY = PREFIX_BADGE + SUFFIX_COLOR_FDC_SECONDARY;
  public static final String CSS_BADGE_FDC_DANGER = PREFIX_BADGE + SUFFIX_COLOR_FDC_DANGER;
  public static final String CSS_BADGE_FDC_SUCCESS = PREFIX_BADGE + SUFFIX_COLOR_FDC_SUCCESS;
  public static final String CSS_BADGE_FDC_DARK = PREFIX_BADGE + SUFFIX_COLOR_FDC_DARK;
  public static final String CSS_BADGE_FDC_LIGHT = PREFIX_BADGE + SUFFIX_COLOR_FDC_LIGHT;
  public static final String CSS_BADGE_FDC_INFO = PREFIX_BADGE + SUFFIX_COLOR_FDC_INFO;
  public static final String CSS_BADGE_FDC_SUN = PREFIX_BADGE + SUFFIX_COLOR_FDC_SUN;
  public static final String CSS_BADGE_FDC_FLOWER = PREFIX_BADGE + SUFFIX_COLOR_FDC_FLOWER;

  public static final String CSS_COLOR_FDC_PRIMARY = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_PRIMARY;
  public static final String CSS_COLOR_FDC_WARNING = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_WARNING;
  public static final String CSS_COLOR_FDC_SECONDARY =
      PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_SECONDARY;
  public static final String CSS_COLOR_FDC_DANGER = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_DANGER;
  public static final String CSS_COLOR_FDC_SUCCESS = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_SUCCESS;
  public static final String CSS_COLOR_FDC_DARK = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_DARK;
  public static final String CSS_COLOR_FDC_LIGHT = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_LIGHT;
  public static final String CSS_COLOR_FDC_INFO = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_INFO;
  public static final String CSS_COLOR_FDC_SUN = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_SUN;
  public static final String CSS_COLOR_FDC_FLOWER = PREFIX_COLOR_FDC + SUFFIX_COLOR_FDC_FLOWER;

  public static final String CSS_COLOR_FDC_SENDED = CSS_COLOR_FDC_PRIMARY;
  public static final String CSS_COLOR_FDC_RECEIVED = CSS_COLOR_FDC_WARNING;
  public static final String CSS_COLOR_FDC_PROCESSING = CSS_COLOR_FDC_WARNING;
  public static final String CSS_COLOR_FDC_DELETED = CSS_COLOR_FDC_SECONDARY;
  public static final String CSS_COLOR_FDC_REJECT_KO = CSS_COLOR_FDC_DANGER;
  public static final String CSS_COLOR_FDC_SUCCESS_OK = CSS_COLOR_FDC_SUCCESS;
  public static final String CSS_COLOR_FDC_UNKNOWN = CSS_COLOR_FDC_DARK;

  public static final String CSS_ICON_IOCON_AGEV_TARIF_TARI = " icon-tari-porcellino ";
  public static final String ALL_ICON_IOCON_AGEV_TARIF_TARI =
      CSS_ICON_IOCON_AGEV_TARIF_TARI + CSS_NUM_COLUMN_ICON;

  public static final String CSS_ICON_IOCON_SCADENZE_ACCER = " icon-cerchio ";
  public static final String ALL_ICON_IOCON_SCADENZE_ACCER =
      CSS_ICON_IOCON_SCADENZE_ACCER + CSS_NUM_COLUMN_ICON;

  public static final String CSS_ICON_IOCON_QUA_TRIB_TARI = " icon-tari ";
  public static final String ALL_ICON_IOCON_QUA_TRIB_TARI =
      CSS_ICON_IOCON_QUA_TRIB_TARI + CSS_NUM_COLUMN_ICON;
  public static final String CSS_ICON_IOCON_QUA_TRIB_IMU = " icon-casa ";
  public static final String ALL_ICON_IOCON_QUA_TRIB_IMU =
      CSS_ICON_IOCON_QUA_TRIB_IMU + CSS_NUM_COLUMN_ICON;
  public static final String CSS_ICON_IOCON_VERSATO = " icon-pagamento ";
  public static final String ALL_ICON_IOCON_VERSATO = CSS_ICON_IOCON_VERSATO + CSS_NUM_COLUMN_ICON;
  public static final String CSS_ICON_IOCON_SCADENZE = " icon-calendario ";
  public static final String ALL_ICON_IOCON_SCADENZE =
      CSS_ICON_IOCON_SCADENZE + CSS_NUM_COLUMN_ICON;

  public static final String CSS_ICON_IOMUO_ISTANZA_VERBALE = " icon-foglio ";
  public static final String ALL_ICON_IOMUO_ISTANZA_VERBALE =
      CSS_ICON_IOMUO_ISTANZA_VERBALE + CSS_NUM_COLUMN_ICON + CSS_COLOR_UNKNOWN;

  private static final String BASE_LABEL_KEY = "BasePanel.";

  public static final String BASE_CSS_ICON = " icon-suggerimenti ";
  private static final int BASE_LARGHEZZA_PRIMA_COLONNA = 5;

  private int _larghezzaPrimaColonna;
  protected boolean atLeastOneShowed;

  public BasePanelGenericContent(final String id) {
    this(id, BASE_LARGHEZZA_PRIMA_COLONNA);
  }

  public BasePanelGenericContent(final String id, int larghezzaPrimaColonna) {
    super(id);
    atLeastOneShowed = false;
    _larghezzaPrimaColonna = larghezzaPrimaColonna;
  }

  // abstract protected String getBaseLabelKey();

  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  protected String getBaseIcon() {
    return BASE_CSS_ICON;
  }

  protected String getBaseIcon(Object t) {
    return BASE_CSS_ICON;
  }

  protected String getLabelEmpty() {
    return "";
  }

  protected String getBaseDomId() {
    return "did_";
  }

  protected void setLarghezzaPrimaColonna(int larghezzaPrimaColonna) {
    _larghezzaPrimaColonna = larghezzaPrimaColonna;
  }

  protected int getLarghezzaPrimaColonna() {
    return _larghezzaPrimaColonna;
  }

  protected int getLarghezzaSecondaColonna() {
    return 12 - getLarghezzaPrimaColonna();
  }

  private String createDynamicHtml(String id, String message, String value, Boolean showIfBlank) {
    // showIfBlank dovrebbe diverntare showIfBlankOrZero
    if ((StringUtils.isNotBlank(value) && !value.equals("0") && !value.equals("0.0"))
        || (StringUtils.isBlank(value) && showIfBlank)) {
      String valore = StringUtils.isNotBlank(value) ? value : getLabelEmpty();
      atLeastOneShowed = true;
      return "	<div class=\"row py-1 mb-2 border-bottom\">\r\n"
          + "		<div class=\"col-"
          + getLarghezzaPrimaColonna()
          + "\">\r\n"
          + "			<p class=\"card-text text-start fw-bold\">\r\n"
          + "				"
          + message
          + " \r\n"
          + "				:\r\n"
          + "			</p>\r\n"
          + "		</div>\r\n"
          + "		<div class=\"col-"
          + getLarghezzaSecondaColonna()
          + "\">\r\n"
          + "			<p class=\"card-text text-left d-table h-100\">\r\n"
          + "				<span class=\"d-table-cell text-capitalize align-middle\"\r\n"
          + "					id="
          + id
          + "> "
          + valore
          + "</span>\r\n"
          + "			</p>\r\n"
          + "		</div>\r\n"
          + "	</div>\r\n";
    }
    return "";
  }

  protected <T> String getFieldString(T object, String fieldName) {
    return addFieldString(object, fieldName, null);
  }

  protected <T> String addFieldString(T object, String fieldName, ListItem<T> item) {
    String myValue = "";
    try {
      Method myMethod = object.getClass().getMethod("get" + StringUtils.capitalize(fieldName));
      myValue = "" + (myMethod.invoke(object)).toString();

      if (item != null) {
        Label myLabel = new Label(fieldName, myValue);
        myLabel.setVisible(StringUtils.isNotBlank(myValue));
        item.add(myLabel);
      }
    } catch (Exception e) {
      log.error(
          "Attenzione:: Errors on reflection "
              + object.getClass()
              + " -- "
              + fieldName
              + " addFieldString error: "
              + e.getMessage());
    }
    return myValue;
  }

  protected <T> String getStringContentPanel(T object, List<String> fields, String baseId) {
    StringBuilder sB = new StringBuilder(8192);

    for (String field : fields) {
      sB.append(
          createDynamicHtml(
              getBaseDomId() + field + baseId,
              getString(getBaseLabelKey() + field),
              getFieldString(object, field),
              false));
    }
    return sB.toString();
  }

  protected Label createContentPanel(String content) {
    Label divLabel = new Label("contenuto", content);
    divLabel.setEscapeModelStrings(false);
    return divLabel;
  }

  protected <T> Label createContentPanel(T object, List<String> fields, String baseId) {
    Label divLabel = new Label("contenuto", getStringContentPanel(object, fields, baseId));
    divLabel.setEscapeModelStrings(false);
    return divLabel;
  }

  protected <T> Label createContentPanel(T object, List<String> fields) {
    return createContentPanel(object, fields, "");
  }

  protected <T> LaddaAjaxLink<Object> creaBottoneDettagli(T oggetto) {
    return null;
  }

  protected <T, T1 extends IRequestablePage> LaddaAjaxLink<Object> creaBottoneDettagli(
      T oggetto, T1 responsePage) {
    LaddaAjaxLink<Object> btnDettagli =
        new LaddaAjaxLink<Object>("bottoneDettagli", Type.Primary) {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(this);
            setResponsePage(responsePage);
          }
        };

    btnDettagli.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("Common.bottoneDettagli", this)));

    btnDettagli.setOutputMarkupId(true);

    return btnDettagli;
  }

  protected AttributeAppender getCssIcona(Object t) {
    return null;
  }

  protected <T> void addIcona(T t) {
    add(getIcona(t));
  }

  protected <T> void addIconaETitolo(T t, ListItem<T> item) {
    item.add(getIcona(t));
    addFieldString(t, "titolo", item);
  }

  protected <T> WebMarkupContainer getIcona(T t) {
    AttributeAppender attributeAppender = getCssIcona(t);
    WebMarkupContainer icona = null;
    if (attributeAppender != null) {
      icona = new WebMarkupContainer("icona");
      icona.add(attributeAppender);
    }
    return icona;
  }

  protected <T> Label getLabel(T t, String fieldName) {
    String myValue = getFieldString(t, fieldName);
    Label myLabel = new Label(fieldName, myValue);
    myLabel.setVisible(StringUtils.isNotBlank(myValue));
    return myLabel;
  }

  protected <T> Label getLabelTitolo(T t) {
    return getLabel(t, "titolo");
  }

  protected String getCssColorSended() {
    return CSS_COLOR_FDC_SENDED;
  }

  protected String getCssColorReceived() {
    return CSS_COLOR_FDC_RECEIVED;
  }

  protected String getCssColorProcessing() {
    return CSS_COLOR_FDC_PROCESSING;
  }

  protected String getCssColorDeleted() {
    return CSS_COLOR_FDC_DELETED;
  }

  protected String getCssColorRejectKo() {
    return CSS_COLOR_FDC_REJECT_KO;
  }

  protected String getCssColorSuccessOk() {
    return CSS_COLOR_FDC_SUCCESS_OK;
  }

  protected String getCssColorUnknown() {
    return CSS_COLOR_FDC_UNKNOWN;
  }

  public boolean isAtLeastOneShowed() {
    return atLeastOneShowed;
  }

  public void setAtLeastOneShowed(boolean atLeastOneShowed) {
    this.atLeastOneShowed = atLeastOneShowed;
  }

  public <T> void superFillDati(
      List<T> tListItem,
      String baseLabelKey,
      List<String> fields,
      boolean boShowTitolo,
      boolean addPaginazioneAjax,
      Long longItemsForPage,
      int larghezzaPrimaColonna) {
    setLarghezzaPrimaColonna(larghezzaPrimaColonna);
    Label listaVuota = new Label("listaVuota", getString(baseLabelKey + "listaVuota"));
    listaVuota.setVisible(tListItem == null || tListItem.isEmpty());
    add(listaVuota);

    Label titolo = new Label("titolo", getString(baseLabelKey + "titolo"));
    titolo.setVisible(boShowTitolo);
    addOrReplace(titolo);

    PageableListView<T> listView =
        new PageableListView<T>("box", tListItem, longItemsForPage) {

          private static final long serialVersionUID = 3042186948160882101L;

          @Override
          protected void populateItem(ListItem<T> item) {
            final T ithitem = item.getModelObject();

            item.add(createContentPanel(ithitem, fields));
          }
        };
    listView.setOutputMarkupId(true);
    add(listView);
    if (addPaginazioneAjax) {
      PaginazioneAjaxFascicoloPanel paginazioneAjaxFascicolo =
          new PaginazioneAjaxFascicoloPanel("pagination", listView);
      paginazioneAjaxFascicolo.setComponentToRender(this);
      paginazioneAjaxFascicolo.setVisible(tListItem != null && tListItem.size() > longItemsForPage);
      add(paginazioneAjaxFascicolo);
    }
  }
}
