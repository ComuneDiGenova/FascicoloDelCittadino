package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.enums.PortateMensaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiMenuMensa;
import it.liguriadigitale.ponmetro.serviziristorazione.model.ItemMenuMensa;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class MenuMensaPanel extends BasePanel {

  //	private static final String	ICON_PRIMO_PIATTO	= "icon-primo-piatto col-3";
  //	private static final String	ICON_SECONDO_PIATTO	= "icon-secondo-piatto col-3";
  //	private static final String	ICON_FRUTTA			= "icon-frutta-dessert col-3";
  //	private static final String	ICON_CONTORNO		= "icon-contorno col-3";
  private static final long serialVersionUID = 1768750593759330978L;

  private LocalDate day;

  private UtenteServiziRistorazione utenteServiziRistorazione;

  public MenuMensaPanel(UtenteServiziRistorazione iscrizione, LocalDate day) {
    super("menuMensa");
    this.day = day;
    utenteServiziRistorazione = iscrizione;
    fillDati(iscrizione);
  }

  @Override
  public void fillDati(Object dati) {
    UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;
    LocalDateLabel giorno = new LocalDateLabel("giorno", day);
    DatiMenuMensa menuMensa = getMenuMensa(iscritto);
    menuSpeciale(menuMensa);
    add(popolaListViewMenu(menuMensa));
    add(giorno);
  }

  private void menuSpeciale(DatiMenuMensa menuMensa) {
    NotEmptyLabel notEmptyLabel = new NotEmptyLabel("menuSpeciale", "Menù speciale");

    boolean isVisibleLabel =
        menuMensa != null
            && menuMensa.getIsDietaSpeciale() != null
            && menuMensa
                .getIsDietaSpeciale()
                .equals(DatiMenuMensa.IsDietaSpecialeEnum.SPECIALE.value());
    notEmptyLabel.setVisible(isVisibleLabel);
    add(notEmptyLabel);
  }

  private DatiMenuMensa getMenuMensa(UtenteServiziRistorazione iscrizione) {
    try {
      return ServiceLocator.getInstance().getServiziRistorazione().getMenuMensa(day, iscrizione);
    } catch (BusinessException e) {
      log.error("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } catch (ApiException e) {
      // FRR TODO verificarne il comportamento
      DatiMenuMensa datiMenuMensa = new DatiMenuMensa();
      if (e.getResponse().getStatus() == 404) {
        return datiMenuMensa;
      } else return datiMenuMensa;
    }
  }

  private Map<String, List<ItemMenuMensa>> getMenuPerPortata(DatiMenuMensa menuMensa) {

    Map<String, List<ItemMenuMensa>> mappa = new HashMap<>();

    List<ItemMenuMensa> menuGiorno = menuMensa.getMenuGiorno();

    if (null != menuGiorno)
      for (ItemMenuMensa portata : menuGiorno) {
        if (null == mappa.get(portata.getOrdinePiatto())) {
          List<ItemMenuMensa> lista = new ArrayList<>();
          lista.add(portata);
          mappa.put(portata.getOrdinePiatto(), lista);
        } else {
          mappa.get(portata.getOrdinePiatto()).add(portata);
        }
      }
    return mappa;
  }

  private Component popolaListViewMenu(DatiMenuMensa menuMensa) {

    final Map<String, List<ItemMenuMensa>> mappa = getMenuPerPortata(menuMensa);
    List<String> keys = new ArrayList<>(mappa.keySet());
    Collections.sort(keys, (String p1, String p2) -> p1.compareTo(p2));
    ListView<String> listView =
        new ListView<String>("listaPortate", keys) {

          private static final long serialVersionUID = 8730505979263497583L;

          @Override
          protected void populateItem(ListItem<String> item) {
            String ordinePiatto = item.getModelObject();
            String css = getIconaPiatto(ordinePiatto);
            Label descrizionePiatto = getDescrizionePiatto(ordinePiatto);

            int indice = item.getIndex();
            descrizionePiatto.setMarkupId("portata" + indice);
            // WebMarkupContainer header = new
            // WebMarkupContainer("heading");
            WebMarkupContainer piatto = new WebMarkupContainer("piatto");
            WebMarkupContainer collapse = new WebMarkupContainer("collapse");
            WebMarkupContainer accordion = new WebMarkupContainer("accordion");
            AttributeAppender cssAppender = new AttributeAppender("class", css);
            AttributeAppender collapseAppender1 = new AttributeAppender("data-target", indice);
            AttributeAppender collapseAppender2 = new AttributeAppender("aria-controls", indice);
            AttributeModifier accordionAppender1 =
                new AttributeModifier("aria-labelledby", "portata" + indice);
            AttributeAppender accordionAppender2 = new AttributeAppender("class", " show");
            accordion.add(accordionAppender2);
            accordion.setOutputMarkupId(true);
            accordion.setMarkupId("accordion" + indice);
            piatto.add(cssAppender);
            piatto.add(descrizionePiatto);
            collapse.add(collapseAppender1);
            collapse.add(collapseAppender2);
            collapse.add(piatto);
            accordion.add(accordionAppender1);
            // header.add(collapse);
            ListView<ItemMenuMensa> listViewPiatti = generaListViewPortate(mappa.get(ordinePiatto));
            accordion.add(listViewPiatti);

            item.add(accordion);
            item.add(collapse);
          }

          private NotEmptyLabel getDescrizionePiatto(String ordinePiatto) {
            String desc = "";
            if (ordinePiatto != null) {
              for (PortateMensaEnum portata : PortateMensaEnum.values()) {
                if (ordinePiatto.equalsIgnoreCase(portata.getCodice()))
                  desc = portata.getDescription();
              }
            }
            return new NotEmptyLabel("piattoDescrizione", desc);
          }

          @Override
          public boolean isVisible() {
            return !getList().isEmpty();
          }

          private String getIconaPiatto(String ordinePiatto) {
            String css = "";
            if (ordinePiatto != null) {
              for (PortateMensaEnum portata : PortateMensaEnum.values()) {
                if (ordinePiatto.equalsIgnoreCase(portata.getCodice())) css = portata.getIcona();
              }
            }
            return css;
          }
        };

    NotEmptyLabel nessunMenu =
        new NotEmptyLabel("nessunMenu", "Nessun menù disponibile per la giornata");
    nessunMenu.setVisible(!listView.isVisible());
    add(nessunMenu);

    String urlMenuScolastici = getString("MenuMensaPanel.urlMenuScolastici");
    ExternalLink linkMenuScolastici = new ExternalLink("linkMenuScolastici", urlMenuScolastici);

    if (utenteServiziRistorazione.getCategoriaStrutturaScolastica().contains("NIDO")) {
      linkMenuScolastici.setVisible(true);
    } else {
      linkMenuScolastici.setVisible(false);
    }
    add(linkMenuScolastici);

    return listView;
  }

  private ListView<ItemMenuMensa> generaListViewPortate(List<ItemMenuMensa> menuGiorno) {

    return new ListView<ItemMenuMensa>("listaMenu", menuGiorno) {

      private static final long serialVersionUID = 8730505979263497583L;

      @Override
      protected void populateItem(ListItem<ItemMenuMensa> item) {

        ItemMenuMensa pietanza = item.getModelObject();

        String warningPiatto = pietanza.getWarningPiatto();
        String caloriePiatto = pietanza.getCaloriePiatto();
        String ingredientiPiatto = pietanza.getIngredientiPiatto();

        if (pietanza.getNomePiatto().equalsIgnoreCase(pietanza.getIngredientiPiatto())) {
          item.add(new NotEmptyLabel("ingredienti", ""));
        } else {
          item.add(new NotEmptyLabel("ingredienti", ingredientiPiatto));
        }
        item.add(new Label("nome", pietanza.getNomePiatto()));
        item.add(
            new NotEmptyLabel("calorie", caloriePiatto)
                .setVisible(!caloriePiatto.equalsIgnoreCase("0")));
        item.add(new NotEmptyLabel("warning", warningPiatto));
      }

      @Override
      public boolean isVisible() {
        return !getList().isEmpty();
      }
    };
  }
}
