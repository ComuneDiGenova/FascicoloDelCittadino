package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.EnumMonthRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.PresenzeInMensaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.form.AnnoRender;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPresenzaServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class CalendarioMensaPanel extends BasePanel {

  private static Log log = LogFactory.getLog(CalendarioMensaPanel.class);

  private static final long serialVersionUID = 8543095470255220015L;

  private static final String CSS_PRESENZA_NON_RILEVATA = "text-muted";
  private static final String CSS_CALENDARIO_PRESENZA = "calendario_presenza";
  private static final String CSS_CALENDARIO_ASSENZA = "calendario_assenza";
  private static final String CSS_CALENDARIO_ASSENZA_MENSA = "calendario_assenza_mensa";
  private LocalDate day;
  private List<DatiPresenzaServiziRistorazione> listaPresenze;

  private DropDownChoice<Integer> comboAnni;
  private DropDownChoice<Month> comboMesi;

  public CalendarioMensaPanel(
      UtenteServiziRistorazione iscrizione,
      LocalDate day,
      List<DatiPresenzaServiziRistorazione> listaPresenze) {
    super("calendarioMensa");
    this.day = day;
    this.listaPresenze = listaPresenze;
    fillDati(iscrizione);

    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    final UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;

    List<LocalDate> listaLunedi = getListaLunediDelMeseCorrente();
    ListView<LocalDate> listView =
        new ListView<LocalDate>("settimana", listaLunedi) {

          private static final long serialVersionUID = -4197836610280954379L;
          private static final String CLASS = "class";

          @Override
          protected void populateItem(ListItem<LocalDate> item) {

            AttributeModifier modPresente = new AttributeModifier(CLASS, "td_calendario_scadenze");
            AttributeModifier modMuto = new AttributeModifier(CLASS, "text-muted");
            AttributeModifier domenica = new AttributeModifier(CLASS, "domenica");
            LocalDate primo = item.getModelObject();

            for (int i = 0; i < 7; i++) {
              LocalDate giorno = primo.plusDays(i);
              Label label = new Label("day" + i, giorno.getDayOfMonth());
              WebMarkupContainer cell = new WebMarkupContainer("cell" + i);
              AjaxLink<Void> link = generaLink(iscritto, giorno, "link" + i);
              if (LocalDateUtil.isSameMonth(giorno, day)) {
                if (giorno.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                  cell.add(modPresente);
                  link.add(domenica);
                } else {
                  String css = giornoPresenzainMensa(giorno, listaPresenze);
                  AttributeModifier modDataPresente = new AttributeModifier(CLASS, css);
                  cell.add(modPresente);
                  link.add(modDataPresente);
                }
                if (giorno.equals(day)) {
                  cell.add(new AttributeAppender("class", new Model("bg-blu-dark"), " "));
                }
              } else {
                link.add(modMuto);
              }
              cell.add(link);
              link.add(label);
              item.add(cell);
            }
          }

          private String giornoPresenzainMensa(
              LocalDate giorno, List<DatiPresenzaServiziRistorazione> listaPresenze) {
            String css = CSS_PRESENZA_NON_RILEVATA;
            for (DatiPresenzaServiziRistorazione presenza : listaPresenze) {
              if (presenza.getDataRiferimento().equals(giorno)) {

                if (presenza
                    .getPresenza()
                    .equalsIgnoreCase(
                        DatiPresenzaServiziRistorazione.PresenzaEnum
                            .PRESENTE_SIA_A_SCUOLA_CHE_IN_MENSA
                            .value())) {
                  css = CSS_CALENDARIO_PRESENZA;
                } else if (presenza
                    .getPresenza()
                    .equalsIgnoreCase(
                        DatiPresenzaServiziRistorazione.PresenzaEnum.PRESENTE_SOLO_A_SCUOLA
                            .value())) {
                  css = CSS_CALENDARIO_ASSENZA_MENSA;
                } else if (presenza
                    .getPresenza()
                    .equalsIgnoreCase(
                        DatiPresenzaServiziRistorazione.PresenzaEnum.ASSENTE.value())) {
                  css = CSS_CALENDARIO_ASSENZA;
                }
              }
            }
            return css;
          }
        };

    String contattoRefezioneMail = iscritto.getContattoRefezioneEmail();
    String contattoRefezioneNominativo = iscritto.getContattoRefezioneNominativo();

    ExternalLink linkContattoRefezione =
        new ExternalLink(
            "contattoRefezione", "mailto:" + contattoRefezioneMail, contattoRefezioneNominativo);
    add(linkContattoRefezione);

    linkContattoRefezione.setVisible(
        contattoRefezioneMail != null && contattoRefezioneNominativo != null);

    add(listView);

    LocalDate now = LocalDate.now();
    add(generaLinkLaddaTornaOggi(iscritto, now, "tornaOggi").setVisible(!day.equals(now)));

    creaComboMesi(iscritto);

    creaComboAnni(iscritto);
  }

  @SuppressWarnings("unchecked")
  private void creaComboMesi(final UtenteServiziRistorazione iscritto) {
    IModel<Month> modello = new Model<>(day.getMonth());
    List<Month> listaMesi = Arrays.asList(Month.values());

    comboMesi =
        new DropDownChoice<Month>(
            "comboMesi",
            modello,
            new ComboLoadableDetachableModel(listaMesi),
            new EnumMonthRenderer()) {

          private static final long serialVersionUID = 8298744970265130470L;

          @Override
          protected boolean isDisabled(Month object, int index, String selected) {
            Month meseAttuale = LocalDate.now().getMonth();
            int annoAttuale = LocalDate.now().getYear();
            int annoSelezionato = day.getYear();
            if ((object.getValue() > meseAttuale.getValue()) && (annoAttuale == annoSelezionato)) {
              return true;
            } else {
              return false;
            }
          }
        };

    comboMesi.add(
        new FormComponentUpdatingBehavior() {

          private static final long serialVersionUID = -2438963815674437412L;

          @Override
          protected void onUpdate() {
            LocalDate newDay =
                LocalDate.of(comboAnni.getModelObject(), comboMesi.getModelObject(), 1);
            setResponsePage(new PresenzeInMensaPage(iscritto, newDay));
          }

          @Override
          protected void onError(RuntimeException ex) {
            super.onError(ex);
          }
        });

    add(comboMesi);
  }

  private static List<Integer> getListaAnni() {
    int annoCorrente = LocalDate.now().getYear();
    int annoMinimo = annoCorrente - 5;
    List<Integer> listaAnni = new ArrayList<Integer>();

    while (annoCorrente > annoMinimo) {
      listaAnni.add(annoCorrente);
      annoCorrente = annoCorrente - 1;
    }

    return listaAnni;
  }

  @SuppressWarnings("unchecked")
  private void creaComboAnni(final UtenteServiziRistorazione iscritto) {

    Model<Integer> modello = new Model<Integer>(day.getYear());

    comboAnni =
        new DropDownChoice<Integer>(
            "comboAnni",
            modello,
            new ComboLoadableDetachableModel(getListaAnni()),
            new AnnoRender());

    comboAnni.add(
        new FormComponentUpdatingBehavior() {

          @Override
          protected void onUpdate() {
            LocalDate newDay =
                LocalDate.of(comboAnni.getModelObject(), comboMesi.getModelObject(), 1);

            setResponsePage(new PresenzeInMensaPage(iscritto, newDay));
          }

          @Override
          protected void onError(RuntimeException ex) {
            super.onError(ex);
          }
        });

    addOrReplace(comboAnni);
  }

  private AjaxLink<Void> generaLink(
      final UtenteServiziRistorazione iscritto, final LocalDate giorno, String id) {
    return new AjaxLink<Void>(id) {

      private static final long serialVersionUID = -3093327045133095573L;

      @Override
      public boolean isEnabled() {

        return (!(giorno.isBefore(getInizioAnnoScolastico())
            || giorno.isAfter(getFineAnnoScolastico())));
      }

      @Override
      public void onClick(AjaxRequestTarget target) {
        if (giorno.equals(day.getMonth())) {
          PresenzeInMensaPage page = (PresenzeInMensaPage) getPage();
          page.aggiornaMenu(iscritto, giorno);
          target.add(page);
        } else {
          setResponsePage(new PresenzeInMensaPage(iscritto, giorno));
        }
      }
    };
  }

  private LaddaAjaxLink<Object> generaLinkLadda(
      final UtenteServiziRistorazione iscritto, final LocalDate giorno, String id, String icon) {
    LaddaAjaxLink<Object> link =
        new LaddaAjaxLink<Object>(id, Type.Link) {

          private static final long serialVersionUID = 1968918775944582825L;

          @Override
          public boolean isEnabled() {
            return (!(giorno.isBefore(getInizioAnnoScolastico())
                || giorno.isAfter(getFineAnnoScolastico())));
          }

          @Override
          public void onClick(AjaxRequestTarget target) {
            if (giorno.equals(day.getMonth())) {
              PresenzeInMensaPage page = (PresenzeInMensaPage) getPage();
              page.aggiornaMenu(iscritto, giorno);
              target.add(page);
            } else {
              setResponsePage(new PresenzeInMensaPage(iscritto, giorno));
            }
          }
        };

    IconType iconType =
        new IconType(id) {

          private static final long serialVersionUID = -7695462951525166211L;

          @Override
          public String cssClassName() {
            return icon;
          }
        };

    link.setIconType(iconType);
    link.setSpinnerColor("#f95e01");
    link.setOutputMarkupId(true);
    return link;
  }

  private LaddaAjaxLink<Object> generaLinkLaddaTornaOggi(
      final UtenteServiziRistorazione iscritto, final LocalDate giorno, String id) {
    LaddaAjaxLink<Object> link =
        new LaddaAjaxLink<Object>(id, Type.Link) {

          private static final long serialVersionUID = -82637073323129062L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new PresenzeInMensaPage(iscritto, giorno));
          }
        };

    link.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("CalendarioMensaPanel.tornaOggi", CalendarioMensaPanel.this)));

    link.setSpinnerColor("#f95e01");

    link.setOutputMarkupId(true);

    return link;
  }

  private List<LocalDate> getListaLunediDelMeseCorrente() {

    LocalDate firstMonday = LocalDateUtil.firstMondayOfMonth(day);
    LocalDate lastDayMonth = LocalDateUtil.lastDayOfMonth(day);
    return LocalDateUtil.mondayInMonth(firstMonday, lastDayMonth);
  }

  private LocalDate getInizioAnnoScolastico() {
    int year = LocalDate.now().getYear();
    if (LocalDate.now().getMonthValue() < 9) {
      year = year - 1;
    }
    return LocalDate.of(year, 9, 1);
  }

  private LocalDate getFineAnnoScolastico() {
    int year = LocalDate.now().getYear();
    if (LocalDate.now().getMonthValue() > 8) {
      year = year + 1;
    }
    return LocalDate.of(year, 6, 30);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    // TODO Auto-generated method stub
    StringBuilder sb = new StringBuilder();

    sb.append(
        "\r\n"
            + "$(document).ready(function() {\r\n"
            + "  var comboMesi = $(\"#comboMesi\");\r\n"
            + "  comboMesi.change(function() {\r\n"
            + "    $('#indicator').show();\r\n"
            + "});"
            + "  var comboAnni = $(\"#comboAnni\");\r\n"
            + "  comboAnni.change(function() {\r\n"
            + "    $('#indicator').show();\r\n"
            + "});"
            + "});");

    response.render(OnDomReadyHeaderItem.forScript(sb.toString()));

    super.renderHead(response);
  }
}
