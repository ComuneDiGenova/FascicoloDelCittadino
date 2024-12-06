package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.accessoaivarchi.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.SuspectTransit;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.bravutils.BravAzioniUtils;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;

public class AccessoAiVarchiPanel extends BasePanel {

  private static final long serialVersionUID = -806675590203968148L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public AccessoAiVarchiPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<SuspectTransit> listaVarchi = (List<SuspectTransit>) dati;

    WebMarkupContainer listaVarchiVuota = new WebMarkupContainer("listaVarchiVuota");
    listaVarchiVuota.setVisible(!checkPresenzaVarchi(listaVarchi));
    addOrReplace(listaVarchiVuota);

    PageableListView<SuspectTransit> listView =
        new PageableListView<SuspectTransit>("box", listaVarchi, 4) {

          private static final long serialVersionUID = 3500660986522941205L;

          @Override
          protected void populateItem(ListItem<SuspectTransit> item) {
            final SuspectTransit varco = item.getModelObject();

            String gate = "";
            if (LabelFdCUtil.checkIfNotNull(varco.getGate())) {
              gate =
                  varco
                      .getGate()
                      .getGateDescription()
                      .concat(" - ")
                      .concat(varco.getGate().getGateCode());
            }
            item.addOrReplace(
                new NotEmptyLabel("gate", gate).setVisible(PageUtil.isStringValid(gate)));

            String transitDate = null;
            if (LabelFdCUtil.checkIfNotNull(varco.getTransitDate())) {
              transitDate = LocalDateUtil.getStringOffsetDateTime(varco.getTransitDate());
            }
            item.addOrReplace(
                new AmtCardLabel<>("transitDate", transitDate, AccessoAiVarchiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "transitPlate", varco.getTransitPlate(), AccessoAiVarchiPanel.this));

            String permitCode = "";
            String categoryDescription = "";
            String zoneDescription = "";
            String permitType = "";
            String permitStatusDescription = "";

            if (LabelFdCUtil.checkIfNotNull(varco.getPermitInfo())) {
              permitCode = varco.getPermitInfo().getPermitCode();
              categoryDescription = varco.getPermitInfo().getCategoryDescription();
              zoneDescription = varco.getPermitInfo().getZoneDescription();
              permitType = varco.getPermitInfo().getPermitType();
              permitStatusDescription = varco.getPermitInfo().getPermitStatusDescription();
            }

            item.addOrReplace(
                new AmtCardLabel<>("permitCode", permitCode, AccessoAiVarchiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "categoryDescription", categoryDescription, AccessoAiVarchiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>("permitType", permitType, AccessoAiVarchiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>("zoneDescription", zoneDescription, AccessoAiVarchiPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "permitStatusDescription", permitStatusDescription, AccessoAiVarchiPanel.this));

            List<PermitAllowedAction> listaTutteAzioni =
                popolaListaAzioniSulPermesso(varco.getPermitInfo());

            ListView<PermitAllowedAction> boxAzioni =
                new ListView<PermitAllowedAction>("boxAzioni", listaTutteAzioni) {

                  private static final long serialVersionUID = 7341895172312223368L;

                  @Override
                  protected void populateItem(ListItem<PermitAllowedAction> itemAzione) {

                    PermitAllowedAction azione = itemAzione.getModelObject();

                    WebMarkupContainer btnAzione =
                        BravAzioniUtils.creaBottoneDinamico(varco.getPermitInfo(), azione);
                    itemAzione.addOrReplace(btnAzione);
                  }
                };

            boxAzioni.setOutputMarkupId(true);
            item.addOrReplace(boxAzioni);

            String daAccessoAiVarchiAVerbali =
                ServiceLocator.getInstance()
                    .getServiziAccessoAiVarchi()
                    .getValoreDaDb("ACCESSO_AI_VARCHI_MIEI_VERBALI");
            boolean daAccessoAiVarchiAVerbaliIsVisibile =
                PageUtil.isStringValid(daAccessoAiVarchiAVerbali)
                    && daAccessoAiVarchiAVerbali.equalsIgnoreCase("SI");

            LinkDinamicoLaddaFunzione<Object> btnMieiVerbali =
                new LinkDinamicoLaddaFunzione<Object>(
                    "btnMieiVerbali",
                    new LinkDinamicoFunzioneData(
                        "AccessoAiVarchiPanel.btnMieiVerbali",
                        "MieiVerbaliPage",
                        "AccessoAiVarchiPanel.btnMieiVerbali"),
                    null,
                    AccessoAiVarchiPanel.this,
                    "color-cyan col-auto icon-vigile-verbale",
                    daAccessoAiVarchiAVerbaliIsVisibile);
            item.addOrReplace(btnMieiVerbali);

            LinkDinamicoLaddaFunzione<Object> btnRichiediZtl =
                new LinkDinamicoLaddaFunzione<Object>(
                    "btnRichiediZtl",
                    new LinkDinamicoFunzioneData(
                        "AccessoAiVarchiPanel.btnRichiediZtl",
                        "PermessiZTLPage",
                        "AccessoAiVarchiPanel.btnRichiediZtl"),
                    null,
                    AccessoAiVarchiPanel.this,
                    "color-cyan col-auto icon-genova-pass");
            item.addOrReplace(btnRichiediZtl);
          }
        };
    listView.setVisible(checkPresenzaVarchi(listaVarchi));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationVarchi", listView);
    paginazioneFascicolo.setVisible(checkPresenzaVarchi(listaVarchi) && listaVarchi.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkPresenzaVarchi(List<SuspectTransit> listaVarchi) {
    return LabelFdCUtil.checkIfNotNull(listaVarchi) && !LabelFdCUtil.checkEmptyList(listaVarchi);
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
      log.error("Errore durante get azioni su permesso in varchi: " + e.getMessage());
    }

    return listaAzioni;
  }
}
