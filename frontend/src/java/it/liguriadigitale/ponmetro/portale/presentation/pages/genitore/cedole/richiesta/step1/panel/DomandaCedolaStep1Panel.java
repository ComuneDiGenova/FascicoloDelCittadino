package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step1.panel;

import it.liguriadigitale.framework.presentation.components.behavior.RedAsteriskBehavior;
import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.cedole.ClasseRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.cedole.ScuolaRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPhoneNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step2b.DomandaCedolaStep2Page;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.CedoleLibrarieConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel.DatiBambinoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Classe;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Scuola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.SoggettoAdulto;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class DomandaCedolaStep1Panel extends BasePanel {

  private static final long serialVersionUID = 3630106380046949776L;

  private CedoleMinore cedolaMinore;

  public DomandaCedolaStep1Panel(String id, CedoleMinore cedolaMinore, DomandaCedola cedola) {
    super(id);
    this.cedolaMinore = cedolaMinore;
    log.debug("cedolaMinore:" + cedolaMinore);
    fillDati(cedola);
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {

    DomandaCedola domanda = (DomandaCedola) dati;

    if (domanda.getSoggettoPrenotazione() == null) {
      domanda.setSoggettoPrenotazione(new SoggettoAdulto());
    }

    AbstracFrameworkForm<DomandaCedola> form =
        new AbstracFrameworkForm<DomandaCedola>("form", domanda) {

          private static final long serialVersionUID = 7602157250344140043L;

          DropDownChoice<Scuola> comboScuole;
          DropDownChoice<Classe> comboClassi;

          WebMarkupContainer containerLibroReligione;
          SiNoDropDownChoice<String> libroReligione;

          WebMarkupContainer containerInfoNoReligione;

          @SuppressWarnings({"rawtypes", "unchecked"})
          protected DropDownChoice creaDropDownChoice(
              List lista, String idWicket, IChoiceRenderer choiceRenderer, IModel modello) {
            ComboLoadableDetachableModel choices = new ComboLoadableDetachableModel(lista);
            FdCDropDownChoice combo = new FdCDropDownChoice(idWicket);
            combo.setChoices(choices);
            combo.setChoiceRenderer(choiceRenderer);
            combo.setModel(modello);
            combo.add(new Behavior[] {new RedAsteriskBehavior()});
            return combo;
          }

          @SuppressWarnings("unchecked")
          protected void creaComboLibroReligione() {

            containerLibroReligione = new WebMarkupContainer("containerLibroReligione");
            containerLibroReligione.setOutputMarkupId(true);
            containerLibroReligione.setOutputMarkupPlaceholderTag(true);

            String libroReligioneStr = null;
            IModel<String> libroReligioneModel = Model.of(libroReligioneStr);

            libroReligione = new SiNoDropDownChoice("libroReligione", libroReligioneModel);
            libroReligione.add(
                new AjaxFormComponentUpdatingBehavior("change") {

                  private static final long serialVersionUID = -3144881386028861365L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {

                    log.debug("CP libro religione click:" + libroReligione.getValue());

                    if (LabelFdCUtil.checkIfNotNull(libroReligione.getValue())
                        && libroReligione.getValue().equalsIgnoreCase("1")) {
                      containerInfoNoReligione.setVisible(true);
                    } else {
                      containerInfoNoReligione.setVisible(false);
                    }

                    target.add(containerInfoNoReligione);
                  }
                });
            libroReligione.setLabel(Model.of("Richiedo il libro di religione"));
            // libroReligione.setRequired(true);

            //				boolean containerLibroReligioneVisibile = LabelFdCUtil.checkIfNotNull(comboClassi)
            // &&
            //						LabelFdCUtil.checkIfNotNull(comboClassi.getModelObject()) &&
            //						PageUtil.isStringValid(comboClassi.getModelObject().getClasse()) &&
            //						(comboClassi.getModelObject().getClasse().equalsIgnoreCase("1") ||
            // comboClassi.getModelObject().getClasse().equalsIgnoreCase("4"));
            //
            //				log.debug("CP containerLibroReligioneVisibile " +
            // containerLibroReligioneVisibile);

            containerLibroReligione.setEnabled(false);

            containerLibroReligione.addOrReplace(libroReligione);

            this.add(containerLibroReligione);
          }

          @SuppressWarnings({"rawtypes"})
          @Override
          public void addElementiForm() {
            NotEmptyLabel anno =
                new NotEmptyLabel(
                    "annoScolastico",
                    " per l'anno scolastico " + DatiBambinoPanel.ricavaAnnoScolastico());
            addOrReplace(anno);

            creaContainerLibroReligione();

            creaComboLibroReligione();

            creaComboScuole();
            creaComboClassi();

            if (getUtente().isResidente()) {
              domanda.setIndirizzoMinore(
                  getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
              domanda.setComuneMinore(
                  getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
              domanda.setCapMinore(
                  getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
            }

            domanda.getSoggettoPrenotazione().setTelefono(getUtente().getMobile());
            domanda.getSoggettoPrenotazione().setEmail(getUtente().getMail());

            CompoundPropertyModel<DomandaCedola> cmpDomanda = new CompoundPropertyModel<>(domanda);

            FdCTextField indirizzo =
                new FdCTextField<Component>(
                    "indirizzo", cmpDomanda.bind("indirizzoMinore"), DomandaCedolaStep1Panel.this);
            indirizzo.setEnabled(!getUtente().isResidente());
            indirizzo.setRequired(true);
            addOrReplace(indirizzo);

            FdCTextField comune =
                new FdCTextField<Component>(
                    "comune", cmpDomanda.bind("comuneMinore"), DomandaCedolaStep1Panel.this);
            comune.setEnabled(!getUtente().isResidente());
            comune.setRequired(true);
            addOrReplace(comune);

            FdCTextField cap =
                new FdCTextField<Component>(
                    "cap", cmpDomanda.bind("capMinore"), DomandaCedolaStep1Panel.this);
            cap.setEnabled(!getUtente().isResidente());
            cap.setRequired(true);
            addOrReplace(cap);

            FdCPhoneNumberField telefono =
                new FdCPhoneNumberField<Component>(
                    "telefono",
                    cmpDomanda.bind("soggettoPrenotazione.telefono"),
                    DomandaCedolaStep1Panel.this);
            telefono.setRequired(true);
            addOrReplace(telefono);

            FdCEmailTextField mail =
                new FdCEmailTextField<Component>(
                    "email",
                    cmpDomanda.bind("soggettoPrenotazione.email"),
                    DomandaCedolaStep1Panel.this);
            mail.getTextField().setRequired(true);
            addOrReplace(mail);
          }

          @Override
          protected void onSubmit() {
            DomandaCedola domanda = getModelObject();
            log.debug("modello cedole prima: " + domanda);

            Scuola scuolaSelezionata = comboScuole.getModelObject();
            Classe classeSelezionata = comboClassi.getModelObject();

            String libroReligioneSelezione = libroReligione.getModelObject();
            log.debug("CP libroReligioneSelezione = " + libroReligioneSelezione);

            if (scuolaSelezionata != null) {
              cedolaMinore
                  .getMinore()
                  .setStatoIscrizioneServiziRistorazione(
                      StatoIscrizioneServiziRistorazioneEnum.INVIATA);
              cedolaMinore
                  .getMinore()
                  .setCategoriaStrutturaScolastica(
                      scuolaSelezionata.getCategoriaStrutturaScolastica());
              cedolaMinore
                  .getMinore()
                  .setStrutturaScolastica(scuolaSelezionata.getDenominazioneScuola());
              cedolaMinore.getMinore().setCodiceScuola(scuolaSelezionata.getIdScuola());
              cedolaMinore.getMinore().setClasse(classeSelezionata.getClasse());
              cedolaMinore.getMinore().setSezione(classeSelezionata.getSezione());
              domanda.setIdScuola(scuolaSelezionata.getIdScuola());
              domanda.setScuola(scuolaSelezionata.getDenominazioneScuola());
              domanda.setClasse(classeSelezionata.getClasse());
              domanda.setSezione(classeSelezionata.getSezione());

              if (LabelFdCUtil.checkIfNotNull(libroReligioneSelezione)
                  && !libroReligioneSelezione.equalsIgnoreCase("No")) {
                domanda.setLibroReligione(true);
              } else {
                domanda.setLibroReligione(false);
              }
            }
            log.debug("modello cedole dopo: " + domanda);
            setResponsePage(new DomandaCedolaStep2Page(cedolaMinore, domanda));
          }

          @SuppressWarnings("unchecked")
          private void creaComboClassi() {
            ClasseRenderer choiceRenderer = new ClasseRenderer();
            Classe classe = new Classe();
            classe.setClasse(cedolaMinore.getMinore().getClasse());
            classe.setSezione(cedolaMinore.getMinore().getSezione());
            comboClassi =
                this.creaDropDownChoice(
                    inizializzaClassi(), "comboClasse", choiceRenderer, Model.of(classe));

            comboClassi.add(
                new AjaxFormComponentUpdatingBehavior("change") {

                  private static final long serialVersionUID = 1921339707936562310L;

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {

                    log.debug("CP classe click:" + comboClassi.getModelObject());

                    if (LabelFdCUtil.checkIfNotNull(comboClassi)
                        && LabelFdCUtil.checkIfNotNull(comboClassi.getModelObject())
                        && PageUtil.isStringValid(comboClassi.getModelObject().getClasse())) {

                      if (comboClassi.getModelObject().getClasse().equalsIgnoreCase("1")
                          || comboClassi.getModelObject().getClasse().equalsIgnoreCase("4")) {

                        log.debug("CP 1 o 4");

                        libroReligione.setRequired(true);
                        containerLibroReligione.setEnabled(true);
                      } else {

                        log.debug("CP diverso da 1 o 4");

                        libroReligione.setRequired(false);
                        containerLibroReligione.setEnabled(false);
                      }
                    }

                    target.add(libroReligione, containerLibroReligione);
                  }
                });

            comboClassi.setOutputMarkupId(true);
            comboClassi.setOutputMarkupId(true);

            this.add(comboClassi);
          }

          private List<Classe> inizializzaClassi() {

            if (cedolaMinore.getMinore().getClasse() != null) {
              Scuola scuola = new Scuola();
              scuola.setIdScuola(cedolaMinore.getMinore().getCodiceScuola());
              scuola.setDenominazioneScuola(cedolaMinore.getMinore().getStrutturaScolastica());
              return getElencoClassi(scuola);
            } else {
              return new ArrayList<>();
            }
          }

          @SuppressWarnings("unchecked")
          private void creaComboScuole() {
            ScuolaRenderer choiceRenderer = new ScuolaRenderer();
            Scuola scuola = new Scuola();
            scuola.setIdScuola(cedolaMinore.getMinore().getCodiceScuola());
            scuola.setDenominazioneScuola(cedolaMinore.getMinore().getStrutturaScolastica());
            comboScuole =
                this.creaDropDownChoice(
                    getElencoScuole(), "comboScuola", choiceRenderer, Model.of(scuola));
            comboScuole.setOutputMarkupId(true);
            comboScuole.add(creaBehaviorComboScuole());
            this.add(comboScuole);
          }

          private AjaxFormComponentUpdatingBehavior creaBehaviorComboScuole() {
            return new AjaxFormComponentUpdatingBehavior("change") {
              private static final long serialVersionUID = 729725369588897805L;

              private Log log = LogFactory.getLog(getClass());

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                Scuola model = comboScuole.getModelObject();
                log.debug("modello: " + model);
                if (model != null) {
                  comboClassi.setChoices(getElencoClassi(model));
                }
                target.add(DomandaCedolaStep1Panel.this);
              }
            };
          }

          private void creaContainerLibroReligione() {
            containerInfoNoReligione = new WebMarkupContainer("containerInfoNoReligione");
            containerInfoNoReligione.setOutputMarkupId(true);
            containerInfoNoReligione.setOutputMarkupPlaceholderTag(true);
            containerInfoNoReligione.setVisible(false);
            this.addOrReplace(containerInfoNoReligione);
          }

          private List<Scuola> getElencoScuole() {
            return ServiceLocator.getInstance().getCedoleLibrarie().getScuole();
          }

          private List<Classe> getElencoClassi(Scuola scuola) {
            if (!StringUtils.isEmpty(scuola.getIdScuola())) {
              return ServiceLocator.getInstance()
                  .getCedoleLibrarie()
                  .getClassi(scuola.getIdScuola());
            } else {
              log.debug("scuola:" + scuola);
              return new ArrayList<>();
            }
          }
        };

    Link<Void> linkannulla =
        new Link<Void>("annulla") {

          private static final long serialVersionUID = -3743805429895310539L;

          @Override
          public void onClick() {
            setResponsePage(new CedoleLibrarieConPrivacyPage(cedolaMinore.getMinore()));
          }
        };
    form.add(linkannulla);
    add(form);
    createFeedBackPanel();
  }
}
