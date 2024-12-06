package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegati;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.pojo.enums.CpvParentTypeEnum;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.ParenteleDropDownChoise;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.OnChangeAjaxBehaviorSenzaIndicator;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class IndividuiCollegatiForm extends AbstracFrameworkForm<VariazioniResidenza> {

  private static final long serialVersionUID = -1009931441211289883L;

  private Utente utente;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private VariazioniResidenza variazioniResidenza;

  public IndividuiCollegatiForm(String id, VariazioniResidenza model, Utente utente) {
    super(id, model);

    setUtente(utente);
    setVariazioniResidenza(model);

    setOutputMarkupId(true);

    addElementiForm(utente);
  }

  public void addElementiForm(Utente utente) {

    List<IndividuiCollegati> listaIndividuiCollegati =
        getListaIndividuiCollegatiDaNucleoFamigliare(utente);

    PageableListView<IndividuiCollegati> listView =
        new PageableListView<IndividuiCollegati>("box", listaIndividuiCollegati, 4) {

          private static final long serialVersionUID = 5350524900794699550L;

          @SuppressWarnings({"rawtypes", "unchecked"})
          @Override
          protected void populateItem(ListItem<IndividuiCollegati> item) {
            IndividuiCollegati individuo = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(VariazioniResidenzaUtil.getCssIconaIndividuo(individuo));
            item.addOrReplace(icona);

            WebMarkupContainer containerComboParentela =
                new WebMarkupContainer("containerComboParentela");
            containerComboParentela.setVisible(
                individuo.isSelezionato()
                    && LabelFdCUtil.checkIfNotNull(
                        getVariazioniResidenza().getComboTipologiaIscrizione())
                    && getVariazioniResidenza()
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("2"));
            containerComboParentela.setOutputMarkupId(true);
            containerComboParentela.setOutputMarkupPlaceholderTag(true);

            ParenteleDropDownChoise parentelaConCoabitante =
                new ParenteleDropDownChoise(
                    "parentelaConCoabitante",
                    new PropertyModel<>(individuo, "parentelaConCoabitante"));
            parentelaConCoabitante.setVisible(
                individuo.isSelezionato()
                    && LabelFdCUtil.checkIfNotNull(
                        getVariazioniResidenza().getComboTipologiaIscrizione())
                    && getVariazioniResidenza()
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("2"));
            parentelaConCoabitante.setOutputMarkupId(true);
            parentelaConCoabitante.setOutputMarkupPlaceholderTag(true);
            parentelaConCoabitante.setRequired(
                getVariazioniResidenza()
                    .getComboTipologiaIscrizione()
                    .getCodiceFDC()
                    .equalsIgnoreCase("2"));

            parentelaConCoabitante.add(
                new AjaxFormComponentUpdatingBehavior("change") {

                  private static final long serialVersionUID = 799781430432946477L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget targetComboParentela) {
                    log.debug("CP combo parentela = " + parentelaConCoabitante.getValue());
                  }
                });

            containerComboParentela.addOrReplace(parentelaConCoabitante);

            item.addOrReplace(containerComboParentela);

            // Con modifica per accessibilita

            // FdCCheckField fdCCFSelezionato = new FdCCheckField("selezionato",
            // new PropertyModel<Boolean>(individuo, "selezionato"),
            // IndividuiCollegatiForm.this);
            //
            // fdCCFSelezionato.getCheckBox().setEnabled(getVariazioniResidenza().getComboTipologiaIscrizione()
            // .getCodiceFDC().equalsIgnoreCase("2")
            // ||
            // getVariazioniResidenza().getComboTipologiaIscrizione().getCodiceFDC().equalsIgnoreCase("3")
            // ||
            // getVariazioniResidenza().getComboTipologiaIscrizione().getCodiceFDC().equalsIgnoreCase("4"));
            // item.addOrReplace(fdCCFSelezionato);

            //

            // prima
            AjaxCheckBox selezionato =
                new AjaxCheckBox(
                    "selezionato", new PropertyModel<Boolean>(individuo, "selezionato")) {

                  private static final long serialVersionUID = -6947931904034174989L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {
                    target.add(IndividuiCollegatiForm.this);
                  }
                };

            selezionato.setEnabled(
                getVariazioniResidenza()
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("2")
                    || getVariazioniResidenza()
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("3")
                    || getVariazioniResidenza()
                        .getComboTipologiaIscrizione()
                        .getCodiceFDC()
                        .equalsIgnoreCase("4"));

            selezionato.setLabel(Model.of("Selezionato"));
            selezionato.setOutputMarkupId(true);
            selezionato.setOutputMarkupPlaceholderTag(true);
            item.addOrReplace(selezionato);
            //

            Label nominativo = new Label("nominativo", individuo.getNominativo());
            nominativo.setVisible(PageUtil.isStringValid(individuo.getNominativo()));
            item.addOrReplace(nominativo);

            LocalDateLabel dataNascita =
                new LocalDateLabel("dataNascita", individuo.getDataNascita());
            dataNascita.setVisible(LabelFdCUtil.checkIfNotNull(individuo.getDataNascita()));
            item.addOrReplace(dataNascita);

            Label parentela = new Label("parentela", individuo.getParentela());
            parentela.setVisible(PageUtil.isStringValid(individuo.getParentela()));
            item.addOrReplace(parentela);

            Label nuovaParentela = new Label("nuovaParentela", individuo.getNuovaParentela());
            nuovaParentela.setVisible(PageUtil.isStringValid(individuo.getNuovaParentela()));
            item.addOrReplace(nuovaParentela);

            Label idDemografico = new Label("idDemografico", individuo.getIdDemografico());
            idDemografico.setVisible(PageUtil.isStringValid(individuo.getIdDemografico()));
            item.addOrReplace(idDemografico);

            SiNoDropDownChoice possiedePatenti =
                new SiNoDropDownChoice(
                    "possiedePatenti", new PropertyModel<>(individuo, "possiedePatenti"));
            possiedePatenti.setLabel(Model.of("Possiede patenti"));
            possiedePatenti.add(
                new OnChangeAjaxBehaviorSenzaIndicator() {

                  private static final long serialVersionUID = -6423258714266772227L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {
                    // 0 è sì
                    // 1 è no
                    if (LabelFdCUtil.checkIfNotNull(possiedePatenti)
                        && LabelFdCUtil.checkIfNotNull(possiedePatenti.getValue())) {
                      if (possiedePatenti.getValue().equalsIgnoreCase("1")) {
                        individuo.setPossiedePatenti("No");
                      }
                      if (possiedePatenti.getValue().equalsIgnoreCase("0")) {
                        individuo.setPossiedePatenti("Sì");
                      }
                    }
                  }
                });
            item.addOrReplace(possiedePatenti);

            SiNoDropDownChoice possiedeVeicoli =
                new SiNoDropDownChoice(
                    "possiedeVeicoli", new PropertyModel<>(individuo, "possiedeVeicoli"));
            possiedeVeicoli.setLabel(Model.of("Possiede veicoli"));
            possiedeVeicoli.add(
                new OnChangeAjaxBehaviorSenzaIndicator() {

                  private static final long serialVersionUID = -103119839907513516L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {
                    // 0 è sì
                    // 1 è no
                    if (LabelFdCUtil.checkIfNotNull(possiedeVeicoli)
                        && LabelFdCUtil.checkIfNotNull(possiedeVeicoli.getValue())) {
                      if (possiedeVeicoli.getValue().equalsIgnoreCase("1")) {
                        individuo.setPossiedeVeicoli("No");
                      }
                      if (possiedeVeicoli.getValue().equalsIgnoreCase("0")) {
                        individuo.setPossiedeVeicoli("Sì");
                      }
                    }
                  }
                });
            item.addOrReplace(possiedeVeicoli);

            updateVariazione(listaIndividuiCollegati);
          }
        };

    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(listaIndividuiCollegati.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private void updateVariazione(List<IndividuiCollegati> listaIndividuiCollegati) {
    getVariazioniResidenza().setListaIndividuiCollegati(listaIndividuiCollegati);
  }

  private List<IndividuiCollegati> getListaIndividuiCollegatiDaNucleoFamigliare(Utente utente) {
    // List<ComponenteNucleo> listaNucleo = utente.getNucleoFamiliareAllargato();

    List<ComponenteNucleo> listaNucleo = new ArrayList<ComponenteNucleo>();

    try {
      listaNucleo =
          ServiceLocator.getInstance().getServizioDemografico().getNucleoFamiliare(utente);
    } catch (BusinessException | ApiException e) {
      log.error("Errore nucleo familiare in individui collegati APK: " + e.getMessage(), e);
    }

    List<IndividuiCollegati> listaIndividui = new ArrayList<IndividuiCollegati>();

    for (ComponenteNucleo cn : listaNucleo) {
      IndividuiCollegati individuo = new IndividuiCollegati();

      if (LabelFdCUtil.checkIfNotNull(getVariazioniResidenza())
          && LabelFdCUtil.checkIfNotNull(getVariazioniResidenza().getComboTipologiaIscrizione())
          && getVariazioniResidenza()
              .getComboTipologiaIscrizione()
              .getCodiceFDC()
              .equalsIgnoreCase("1")) {
        individuo.setSelezionato(true);
      }

      if (LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())) {
        individuo.setNome(cn.getDatiCittadino().getCpvGivenName());
        individuo.setCognome(cn.getDatiCittadino().getCpvFamilyName());
        individuo.setNominativo(cn.getDatiCittadino().getRdfsLabel());
        individuo.setCf(cn.getDatiCittadino().getCpvTaxCode());
        individuo.setDataNascita(cn.getDatiCittadino().getCpvDateOfBirth());
        individuo.setParentela(cn.getRelazione().getCpvParentType());
        individuo.setIdDemografico(cn.getDatiCittadino().getCpvPersonID());
        individuo.setNuovaParentela(getDescrizioneCodiceParentela(cn.getRelazione()));
        individuo.setGenere(cn.getDatiCittadino().getCpvHasSex());
        individuo.setEta(LocalDateUtil.calcolaEta(cn.getDatiCittadino().getCpvDateOfBirth()));

        listaIndividui.add(individuo);
      }
    }

    return listaIndividui;
  }

  private String getDescrizioneCodiceParentela(ItemRelazioneParentale famigliare) {
    CpvParentTypeEnum parentela = CpvParentTypeEnum.fromValue(famigliare.getCpvParentType());

    String descrizioneParentela = parentela.getDescription();
    String cfSex = famigliare.getCpvComponentTaxCode().substring(9, 11);
    if (parentela.name().equals(CpvParentTypeEnum.FG.name()) && Integer.parseInt(cfSex) >= 41) {
      descrizioneParentela = descrizioneParentela.replace("o", "a");
    }

    if (parentela.name().equals(CpvParentTypeEnum.IS.name())) {
      return descrizioneParentela;
    } else {
      return descrizioneParentela.concat(" dell'intestatario");
    }
  }

  @Override
  public void addElementiForm() {
    // TODO Auto-generated method stub

  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public VariazioniResidenza getVariazioniResidenza() {
    return variazioniResidenza;
  }

  public void setVariazioniResidenza(VariazioniResidenza variazioniResidenza) {
    this.variazioniResidenza = variazioniResidenza;
  }
}
