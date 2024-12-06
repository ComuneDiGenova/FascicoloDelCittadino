package it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze.panel;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.api.pojo.scadenze.db.VScScadenzeUt;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.EnumMonthRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze.ScadenzePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class CalendarioScadenzePanel extends BasePanel {

  private static final long serialVersionUID = -8756273867923733761L;

  private static final String CSS_SCADENZA_GIORNO = "scad_giorno";
  private static final String CSS_SCADENZA_MESE_IN_CORSO = "scad_mese_in_corso";
  private static final String CSS_SCADENZA_ALTRI_MESI = "scad_altri_mesi";
  private static final String CSS_SCADENZA_DOMENICA = "scad_giorno_domenica";

  private LocalDate day;

  Boolean trovatoPrimoDelMese;

  public CalendarioScadenzePanel(LocalDate day, List<VScScadenzeUt> lista) {
    super("calendarioScadenze");
    this.day = day;
    fillDati(lista);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<VScScadenzeUt> listaScadenzeMese = ((List<VScScadenzeUt>) dati);

    List<LocalDate> listaLunedi = getListaLunediDelMeseCorrente();
    trovatoPrimoDelMese = false;

    ListView<LocalDate> listView =
        new ListView<LocalDate>("settimana", listaLunedi) {

          private static final long serialVersionUID = -4197836610280954379L;
          private static final String CLASS = "class";

          AttributeModifier moMeseInCorso =
              new AttributeModifier(CLASS, CSS_SCADENZA_MESE_IN_CORSO);
          AttributeModifier moScadenza = new AttributeModifier(CLASS, CSS_SCADENZA_GIORNO);
          AttributeModifier moDomenica = new AttributeModifier(CLASS, CSS_SCADENZA_DOMENICA);
          AttributeModifier moAltriMesi = new AttributeModifier(CLASS, CSS_SCADENZA_ALTRI_MESI);

          @Override
          protected void populateItem(ListItem<LocalDate> item) {
            LocalDate primo = item.getModelObject();

            for (int i = 0; i < 7; i++) {
              LocalDate giorno = primo.plusDays(i);
              int iGiorno = giorno.getDayOfMonth();
              if (iGiorno == 1) {
                trovatoPrimoDelMese = !trovatoPrimoDelMese;
              }
              WebMarkupContainer cell = new WebMarkupContainer("cell" + i);
              cell.add(getRightLabel(i, iGiorno, giorno.getDayOfWeek().equals(DayOfWeek.SUNDAY)));
              item.add(cell);
            }
          }

          private Label getRightLabel(int indexInSettimana, int iGiorno, boolean isDomenica) {
            Label label = new Label("day" + indexInSettimana, iGiorno);
            if (trovatoPrimoDelMese && listContainsDay(listaScadenzeMese, iGiorno)) {
              label.add(moScadenza);
            } else if (!trovatoPrimoDelMese) {
              label.add(moAltriMesi);
            } else if (isDomenica) {
              label.add(moDomenica);
            } else {
              label.add(moMeseInCorso);
            }
            return label;
          }
        };

    // String meseCorrente = day.getMonth().getDisplayName(TextStyle.FULL,
    // Locale.ITALIAN) + " " + day.getYear();
    int annoCorrente = day.getYear();

    add(listView);
    add(generaLink(LocalDateUtil.previousMonth(day), "mesePrima"));
    add(generaLink(LocalDateUtil.nextMonth(day), "meseDopo"));
    add(generaLink(LocalDateUtil.previousYear(day), "annoPrima"));
    add(generaLink(LocalDateUtil.nextYear(day), "annoDopo"));

    LocalDate now = LocalDate.now();
    Link<Void> linkTornaOggi = generaLink(LocalDate.now(), "tornaOggi");
    add(linkTornaOggi);
    boolean stessoMeseEAnno =
        day.getMonth().equals(now.getMonth()) && day.getYear() == now.getYear();
    linkTornaOggi.setVisible(!stessoMeseEAnno);

    add(new Label("annoCorrente", annoCorrente));
    creaComboMesi();
  }

  private boolean listContainsDay(List<VScScadenzeUt> list, int iDay) {
    return (list.stream()
            .filter(ith -> ith.getDataScadenza().getDayOfMonth() == iDay)
            .findFirst()
            .orElse(null))
        != null;
  }

  @SuppressWarnings("unchecked")
  private void creaComboMesi() {
    Month mese = day.getMonth();
    IModel<Month> modello = new Model<Month>(mese);
    List<Month> listaMesi = Arrays.asList(Month.values());
    final DropDownChoice<Month> comboMesi =
        new DropDownChoice<Month>(
            "comboMesi",
            modello,
            new ComboLoadableDetachableModel(listaMesi),
            new EnumMonthRenderer());

    comboMesi.add(
        new FormComponentUpdatingBehavior() {

          private static final long serialVersionUID = -8418621730334667906L;

          @Override
          protected void onUpdate() {

            LocalDate newDay = LocalDate.of(day.getYear(), comboMesi.getModelObject(), 1);
            setResponsePage(new ScadenzePage(newDay));
          }

          @Override
          protected void onError(RuntimeException ex) {
            super.onError(ex);
          }
        });
    add(comboMesi);
  }

  private Link<Void> generaLink(final LocalDate giorno, String id) {
    return new Link<Void>(id) {

      private static final long serialVersionUID = -3093327045133095573L;

      @Override
      public void onClick() {
        setResponsePage(new ScadenzePage(giorno));
      }
    };
  }

  private List<LocalDate> getListaLunediDelMeseCorrente() {

    LocalDate firstMonday = LocalDateUtil.firstMondayOfMonth(day);
    LocalDate lastDayMonth = LocalDateUtil.lastDayOfMonth(day);
    return LocalDateUtil.mondayInMonth(firstMonday, lastDayMonth);
  }
}
