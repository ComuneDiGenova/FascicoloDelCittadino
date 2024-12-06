package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl;

import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.DatiRichiestaIstanzaPl;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

public class DettaglioIstanzaPage extends AbstracFrameworkForm<DatiRichiestaIstanzaPl> {

  private static final long serialVersionUID = -7349674609304452360L;

  public DettaglioIstanzaPage(String id, DatiRichiestaIstanzaPl model) {
    super(id, model);

    addElementiForm();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void addElementiForm() {
    DatiRichiestaIstanzaPl datiRichiestaIstanza = getModelObject();
    // set form object
    TextField richiedenteCognome =
        new TextField(
            "richiedenteCognome",
            new PropertyModel<String>(getModelObject(), "richiedenteCognome"));
    richiedenteCognome.setEnabled(false);
    addOrReplace(richiedenteCognome);

    TextField richiedenteNome =
        new TextField(
            "richiedenteNome", new PropertyModel<String>(getModelObject(), "richiedenteNome"));
    richiedenteNome.setEnabled(false);
    addOrReplace(richiedenteNome);

    TextField nascitaComune =
        new TextField(
            "nascitaComune", new PropertyModel<String>(getModelObject(), "nascitaComune"));
    nascitaComune.setEnabled(false);
    addOrReplace(nascitaComune);

    TextField nascitaData =
        new TextField("nascitaData", new PropertyModel<String>(getModelObject(), "nascitaData"));
    nascitaData.setEnabled(false);
    addOrReplace(nascitaData);

    TextField richiedenteCf =
        new TextField(
            "richiedenteCf", new PropertyModel<String>(getModelObject(), "richiedenteCf"));
    richiedenteCf.setEnabled(false);
    addOrReplace(richiedenteCf);

    TextField residenzaComune =
        new TextField(
            "residenzaComune", new PropertyModel<String>(getModelObject(), "residenzaComune"));
    residenzaComune.setEnabled(false);
    addOrReplace(residenzaComune);

    TextField residenzaVia =
        new TextField("residenzaVia", new PropertyModel<String>(getModelObject(), "residenzaVia"));
    residenzaVia.setEnabled(false);
    addOrReplace(residenzaVia);

    TextField residenzaNumeroCivico =
        new TextField(
            "residenzaNumeroCivico",
            new PropertyModel<String>(getModelObject(), "residenzaNumeroCivico"));
    residenzaNumeroCivico.setEnabled(false);
    addOrReplace(residenzaNumeroCivico);

    TextField telefono =
        new TextField(
            "richiedenteTelefono",
            new PropertyModel<String>(getModelObject(), "richiedenteTelefono"));
    telefono.setEnabled(false);
    addOrReplace(telefono);

    EmailTextField emailRichiedente =
        new EmailTextField(
            "richiedenteEmail", new PropertyModel<String>(getModelObject(), "richiedenteEmail"));
    emailRichiedente.setEnabled(false);
    addOrReplace(emailRichiedente);

    TextField<String> autodichiarazioneTarga =
        new TextField(
            "autodichiarazioneTarga",
            new PropertyModel<String>(getModelObject(), "autodichiarazioneTarga"));
    autodichiarazioneTarga.setEnabled(false);
    autodichiarazioneTarga.setVisible(
        "03".equalsIgnoreCase(getModelObject().getCodiceHermesMotivoSelezionato()));
    addOrReplace(autodichiarazioneTarga);

    TextField<String> autodichiarazioneMarca =
        new TextField(
            "autodichiarazioneMarca",
            new PropertyModel<String>(getModelObject(), "autodichiarazioneMarca"));
    autodichiarazioneMarca.setEnabled(false);
    addOrReplace(autodichiarazioneMarca);

    TextField<String> autodichiarazioneModello =
        new TextField(
            "autodichiarazioneModello",
            new PropertyModel<String>(getModelObject(), "autodichiarazioneModello"));
    autodichiarazioneModello.setEnabled(false);
    addOrReplace(autodichiarazioneModello);

    TextField furtoData =
        new TextField("furtoData", new PropertyModel<String>(getModelObject(), "furtoData"));
    furtoData.setEnabled(false);
    addOrReplace(furtoData);

    TextField autodichiarazioneFurtoStringa =
        new TextField(
            "autodichiarazioneFurtoStringa",
            new PropertyModel<String>(getModelObject(), "autodichiarazioneFurtoStringa"));
    autodichiarazioneFurtoStringa.setEnabled(false);
    autodichiarazioneFurtoStringa.setVisible(
        "SI".equalsIgnoreCase(getModelObject().getAutodichiarazioneFurtoStringa()));
    addOrReplace(autodichiarazioneFurtoStringa);

    TextField ritrovatoStringa =
        new TextField(
            "autodichiarazioneFurtoRitrovamentoStringa",
            new PropertyModel<String>(
                getModelObject(), "autodichiarazioneFurtoRitrovamentoStringa"));
    ritrovatoStringa.setEnabled(false);
    ritrovatoStringa.setVisible(
        "SI".equalsIgnoreCase(getModelObject().getAutodichiarazioneFurtoRitrovamentoStringa()));
    addOrReplace(ritrovatoStringa);

    TextField ritrovatoData =
        new TextField(
            "ritrovamentoData", new PropertyModel<String>(getModelObject(), "ritrovamentoData"));
    ritrovatoData.setEnabled(false);
    addOrReplace(ritrovatoData);

    TextField<String> riconsegnatoPolizia =
        new TextField<String>(
            "riconsegnatoPolizia",
            new PropertyModel<String>(getModelObject(), "riconsegnatoPolizia"));
    riconsegnatoPolizia.setEnabled(false);
    addOrReplace(riconsegnatoPolizia);

    TextField<String> riconsegnatoPoliziaComune =
        new TextField<String>(
            "riconsegnatoPoliziaComune",
            new PropertyModel<String>(getModelObject(), "riconsegnatoPoliziaComune"));
    riconsegnatoPoliziaComune.setEnabled(false);
    addOrReplace(riconsegnatoPoliziaComune);

    TextField numeroVerbalePartenza =
        new TextField(
            "numeroVerbalePartenza",
            new PropertyModel<String>(getModelObject(), "numeroVerbalePartenza"));
    numeroVerbalePartenza.setEnabled(false);
    addOrReplace(numeroVerbalePartenza);

    TextField numeriVerbaliAltri =
        new TextField(
            "numeriAltriVerbali",
            new PropertyModel<String>(getModelObject(), "numeriAltriVerbali"));
    numeriVerbaliAltri.setEnabled(false);
    numeriVerbaliAltri.setVisible(
        datiRichiestaIstanza.getNumeriAltriVerbali() != null
            && !StringUtils.isEmpty(datiRichiestaIstanza.getNumeriAltriVerbali()));
    addOrReplace(numeriVerbaliAltri);

    TextField descrizioneCodiceHermesMotivoSelezionato =
        new TextField(
            "descrizioneCodiceHermesMotivoSelezionato",
            new PropertyModel<String>(
                getModelObject(), "descrizioneCodiceHermesMotivoSelezionato"));
    descrizioneCodiceHermesMotivoSelezionato.setEnabled(false);
    addOrReplace(descrizioneCodiceHermesMotivoSelezionato);

    TextField autodichiarazioneProprietarioETitolareStringa =
        new TextField(
            "autodichiarazioneProprietarioETitolareStringa",
            new PropertyModel<String>(
                getModelObject(), "autodichiarazioneProprietarioETitolareStringa"));
    autodichiarazioneProprietarioETitolareStringa.setEnabled(false);
    autodichiarazioneProprietarioETitolareStringa.setVisible(
        "SI".equalsIgnoreCase(getModelObject().getAutodichiarazioneProprietarioETitolareStringa()));
    addOrReplace(autodichiarazioneProprietarioETitolareStringa);
  }
}
