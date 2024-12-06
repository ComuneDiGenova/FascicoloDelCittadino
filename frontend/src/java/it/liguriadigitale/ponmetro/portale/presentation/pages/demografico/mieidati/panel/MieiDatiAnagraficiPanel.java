package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.GeneratoreCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.MieiDatiTemplate;
import java.util.LinkedHashMap;
import java.util.Map;

public class MieiDatiAnagraficiPanel extends MieiDatiTemplate {

  private static final long serialVersionUID = 8241192045685630741L;

  public MieiDatiAnagraficiPanel(String id) {
    super(id);
    fillDati(LoadData.caricaMieiDatiResidente(getSession()));
    templateFeedBackPanel.setEnabled(false);
    templateFeedBackPanel.setVisible(false);
    templateFeedBackPanel.setMaxMessages(0);
  }

  @Override
  protected void logicaBusinessDelPannello(Object dati) throws BusinessException, ApiException {
    Map<String, Object> mappaNomeValore = new LinkedHashMap<>();
    Residente residente = (Residente) dati;
    mappaNomeValore = fillMappaNomeValore(residente, mappaNomeValore);
    GeneratoreCardLabel<Residente> panel =
        new GeneratoreCardLabel<>("panel", mappaNomeValore, this.getClass().getSimpleName());
    myAdd(panel);
  }

  protected Map<String, Object> fillMappaNomeValore(
      Residente residente, Map<String, Object> mappaNomeValore) {

    if (residente.getCpvHasSex().equalsIgnoreCase("M")) {
      mappaNomeValore.put("natoA", residente.getCpvHasBirthPlace().getClvCity().toLowerCase());
    } else {
      mappaNomeValore.put("nataA", residente.getCpvHasBirthPlace().getClvCity().toLowerCase());
    }
    mappaNomeValore.put("natoIl", residente.getCpvDateOfBirth());
    mappaNomeValore.put("sesso", residente.getCpvHasSex());
    mappaNomeValore.put("statoCivile", residente.getGenovaStatoCivile());
    if (residente.getCpvHasCitizenship() != null) {
      mappaNomeValore.put(
          "cittadinanza", residente.getCpvHasCitizenship().getClvCountry().toLowerCase());
    }
    mappaNomeValore.put("codiceFiscale", residente.getCpvTaxCode());
    mappaNomeValore.put("cartaIdentita", residente.getGenovaOntoIDCardNumber());
    mappaNomeValore.put("isElettronica", residente.getGenovaOntoIDCardIsCIE());
    mappaNomeValore.put("rilasciataIl", residente.getGenovaOntoIDCardIssueDate());
    mappaNomeValore.put(
        "rilasciataDalComuneDi", residente.getGenovaOntoIDCardIssuingMunicipality().toLowerCase());
    mappaNomeValore.put("ValiditaCarta", residente.getGenovaOntoIDCardValidUntilDate());

    return mappaNomeValore;
  }
}
