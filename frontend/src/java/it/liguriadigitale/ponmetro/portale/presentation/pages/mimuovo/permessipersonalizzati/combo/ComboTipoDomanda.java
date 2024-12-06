package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.TipologiaProcedimento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.ProcedimentoRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoDomandaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.form.DropDownChoice;

public class ComboTipoDomanda extends DropDownChoice<TipologiaProcedimento> {

  private static final long serialVersionUID = -6765798125295641058L;

  boolean isResidente;

  public ComboTipoDomanda(String id, boolean isRedidente) {
    super(id);
    this.isResidente = isRedidente;

    this.setChoiceRenderer(new ProcedimentoRenderer());

    this.setChoices(inizializza());
  }

  private List<TipologiaProcedimento> inizializza() {

    List<TipologiaProcedimento> lista;
    List<TipologiaProcedimento> listaRisultato = new ArrayList<>();
    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziPermessiPersonalizzati()
              .getTipologieProcedimento();

      if (isResidente) {
        listaRisultato = lista;
      } else {
        for (Iterator<TipologiaProcedimento> iterator = lista.iterator(); iterator.hasNext(); ) {
          TipologiaProcedimento tipologiaProcedimento = iterator.next();

          if (!(EnumTipoDomandaPermessoPersonalizzato.getById(tipologiaProcedimento.getCodice())
                  .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_GUIDATORE_RESIDENZA)
              || EnumTipoDomandaPermessoPersonalizzato.getById(tipologiaProcedimento.getCodice())
                  .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_ACCOMPAGNATO))) {
            listaRisultato.add(tipologiaProcedimento);
          }
        }
      }

      setOutputMarkupId(true);

    } catch (BusinessException | ApiException | IOException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("elenco tipologie procedimento"));
    }

    return listaRisultato;
  }
}
