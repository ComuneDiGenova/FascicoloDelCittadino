package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction.UrlTypeEnum;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitVehiclesInner;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.bravutils.BravAzioniUtils;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.StringResourceModel;

public class GenovaParcheggiPanel extends BasePanel {

  private static final long serialVersionUID = 7953745032272351619L;

  private static final String ICON_GENOVA_PARCHEGGI = "color-cyan col-3 icon-genova-pass";
  private static final String ICON_GENOVA_PARCHEGGI_EMESSO =
      "color-fc-success col-3 icon-genova-pass";
  private static final String ICON_GENOVA_PARCHEGGI_ANNULLATO =
      "color-fc-danger col-3 icon-genova-pass";
  private static final String ICON_GENOVA_PARCHEGGI_DA_PAGARE =
      "color-fc-warning col-3 icon-genova-pass";
  private static final String ICON_GENOVA_PARCHEGGI_SCADUTO =
      "color-fc-secondary col-3 icon-genova-pass";

  List<PermitAllowedAction> listaTutteAzioni;

  static final String URL_GE_PARK = "https://genovaparcheggi.com/";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private String descrizioneFunzione;

  public GenovaParcheggiPanel(String id, String descrizioneFunzione) {
    super(id);

    this.descrizioneFunzione = descrizioneFunzione;

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<Permit> listaPermessi = (List<Permit>) dati;

    WebMarkupContainer listaPermessiVuota = new WebMarkupContainer("listaPermessiVuota");
    NotEmptyLabel messaggioListaVuota =
        new NotEmptyLabel(
            "messaggioListaVuota",
            new StringResourceModel("GenovaParcheggiPanel.listaVuota", this)
                .setParameters(descrizioneFunzione));
    listaPermessiVuota.addOrReplace(messaggioListaVuota);
    listaPermessiVuota.setVisible(!checkPresenzaPermessi(listaPermessi));
    addOrReplace(listaPermessiVuota);

    PageableListView<Permit> listView =
        new PageableListView<Permit>("box", listaPermessi, 4) {

          private static final long serialVersionUID = -2154254848584556209L;

          @Override
          protected void populateItem(ListItem<Permit> item) {
            final Permit permesso = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaPermesso(permesso));
            item.addOrReplace(icona);

            item.addOrReplace(
                new NotEmptyLabel("categoryDescription", permesso.getCategoryDescription()));

            item.addOrReplace(new NotEmptyLabel("zoneDescription", permesso.getZoneDescription()));

            String permitStatusDescription = "";
            if (LabelFdCUtil.checkIfNotNull(permesso.getValidTo())
                && permesso.getValidTo().isBefore(OffsetDateTime.now())) {
              permitStatusDescription = "SCADUTO";
            } else {
              permitStatusDescription = permesso.getPermitStatusDescription();
            }
            item.addOrReplace(
                new NotEmptyLabel("permitStatusDescription", permitStatusDescription));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "permitCode", permesso.getPermitCode(), GenovaParcheggiPanel.this));

            String validFrom = null;
            if (LabelFdCUtil.checkIfNotNull(permesso.getValidFrom())) {
              validFrom = LocalDateUtil.getDataOraCorretteInItalia(permesso.getValidFrom());
              // validFrom = LocalDateUtil.getLocalDateFromOffsetDateTime(permesso.getValidFrom());
            }
            item.addOrReplace(
                new AmtCardLabel<>("validFrom", validFrom, GenovaParcheggiPanel.this));

            String validTo = null;
            if (LabelFdCUtil.checkIfNotNull(permesso.getValidTo())) {
              validTo = LocalDateUtil.getDataOraCorretteInItalia(permesso.getValidTo());
              // validTo = LocalDateUtil.getLocalDateFromOffsetDateTime(permesso.getValidTo());
            }
            item.addOrReplace(new AmtCardLabel<>("validTo", validTo, GenovaParcheggiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "permitType", permesso.getPermitType(), GenovaParcheggiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "zoneNotes", permesso.getZoneNotes(), GenovaParcheggiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "totalPrice", permesso.getTotalPrice(), GenovaParcheggiPanel.this));

            List<PermitVehiclesInner> listaVehicles = new ArrayList<>();
            if (LabelFdCUtil.checkIfNotNull(permesso.getVehicles())) {
              listaVehicles = permesso.getVehicles();
            }
            ListView<PermitVehiclesInner> vehicles =
                new ListView<PermitVehiclesInner>("vehicles", listaVehicles) {

                  private static final long serialVersionUID = 3541302753470748804L;

                  @Override
                  protected void populateItem(ListItem<PermitVehiclesInner> veicolo) {
                    final PermitVehiclesInner elemVeicolo = veicolo.getModelObject();

                    String datiVeicolo = "";
                    if (PageUtil.isStringValid(elemVeicolo.getPlate())) {
                      datiVeicolo = elemVeicolo.getPlate().concat(" - ");
                    }
                    if (PageUtil.isStringValid(elemVeicolo.getVehicleDescription())) {
                      datiVeicolo = datiVeicolo.concat(elemVeicolo.getVehicleDescription());
                    }
                    veicolo.addOrReplace(
                        new AmtCardLabel<>("veicolo", datiVeicolo, GenovaParcheggiPanel.this));
                  }
                };
            vehicles.setVisible(
                LabelFdCUtil.checkIfNotNull(listaVehicles)
                    && !LabelFdCUtil.checkEmptyList(listaVehicles));
            item.addOrReplace(vehicles);

            listaTutteAzioni = popolaListaAzioniSulPermesso(permesso);

            ListView<PermitAllowedAction> boxAzioni =
                new ListView<PermitAllowedAction>("boxAzioni", listaTutteAzioni) {

                  private static final long serialVersionUID = 3541302753470748804L;

                  @Override
                  protected void populateItem(ListItem<PermitAllowedAction> itemAzione) {

                    PermitAllowedAction azione = itemAzione.getModelObject();

                    WebMarkupContainer btnAzione =
                        BravAzioniUtils.creaBottoneDinamico(permesso, azione);
                    itemAzione.addOrReplace(btnAzione);
                  }
                };

            boxAzioni.setOutputMarkupId(true);
            item.addOrReplace(boxAzioni);
          }
        };
    listView.setVisible(checkPresenzaPermessi(listaPermessi));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationGenovaParcheggi", listView);
    paginazioneFascicolo.setVisible(
        checkPresenzaPermessi(listaPermessi) && listaPermessi.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkPresenzaPermessi(List<Permit> listaPermessi) {
    return LabelFdCUtil.checkIfNotNull(listaPermessi)
        && !LabelFdCUtil.checkEmptyList(listaPermessi);
  }

  private AttributeAppender getCssIconaPermesso(Permit permesso) {
    String css = "";

    if (LabelFdCUtil.checkIfNotNull(permesso)
        && LabelFdCUtil.checkIfNotNull(permesso.getPermitStatusId())) {
      if (LabelFdCUtil.checkIfNotNull(permesso.getValidTo())
          && permesso.getValidTo().isBefore(OffsetDateTime.now())) {
        css = ICON_GENOVA_PARCHEGGI_SCADUTO;
      } else {
        if (permesso.getPermitStatusId().equals(new BigDecimal(70))) {
          css = ICON_GENOVA_PARCHEGGI_EMESSO;
        } else if (permesso.getPermitStatusId().equals(new BigDecimal(90))) {
          css = ICON_GENOVA_PARCHEGGI_ANNULLATO;
        } else if (permesso.getPermitStatusId().equals(new BigDecimal(50))) {
          css = ICON_GENOVA_PARCHEGGI_DA_PAGARE;
        } else {
          css = ICON_GENOVA_PARCHEGGI;
        }
      }
    } else {
      css = ICON_GENOVA_PARCHEGGI;
    }

    return new AttributeAppender("class", " " + css);
  }

  public List<PermitAllowedAction> popolaListaAzioniSulPermesso(Permit permesso) {
    List<PermitAllowedAction> listaAzioni = new ArrayList<PermitAllowedAction>();

    try {
      if (LabelFdCUtil.checkIfNotNull(permesso)
          && LabelFdCUtil.checkIfNotNull(permesso.getPermitId())) {
        listaAzioni =
            ServiceLocator.getInstance().getServiziGenovaParcheggi().getAzioniSulPermesso(permesso);
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante get azioni su permesso: " + e.getMessage());
    }

    return listaAzioni;
  }

  public List<PermitAllowedAction> popolaListaAzioniSulPermessoFiltratePerLinkEsterno(
      Permit permesso) {
    List<PermitAllowedAction> listaTutteAzioni = popolaListaAzioniSulPermesso(permesso);
    List<PermitAllowedAction> listaAzioniFiltrate = new ArrayList<PermitAllowedAction>();

    listaAzioniFiltrate =
        listaTutteAzioni.stream()
            .filter(
                elem ->
                    PageUtil.isStringValid(elem.getUrlType())
                        && (elem.getUrlType().equalsIgnoreCase(UrlTypeEnum.FO.value())
                            || elem.getUrlType().equalsIgnoreCase(UrlTypeEnum.BO.value())))
            .collect(Collectors.toList());

    return listaAzioniFiltrate;
  }
}
