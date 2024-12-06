package it.liguriadigitale.ponmetro.portale.presentation.components.combo.provider.biblioteche;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.Response;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.TextChoiceProvider;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class TabellaRecordProvider extends TextChoiceProvider<TabellaPaeseRecord> {

  private static final long serialVersionUID = 2976109827878282894L;

  private Log log = LogFactory.getLog(getClass());

  @Override
  protected String getDisplayText(TabellaPaeseRecord choice) {
    return choice.getDscr();
  }

  @Override
  protected Object getId(TabellaPaeseRecord choice) {
    return choice.getDscr();
  }

  @Override
  public void query(String term, int page, Response<TabellaPaeseRecord> response) {
    response.addAll(getPaesi(term));
    response.setHasMore(response.size() == 10);
  }

  @Override
  public Collection<TabellaPaeseRecord> toChoices(Collection<String> ids) {
    return getPaesi(ids.iterator().next());
  }

  private List<TabellaPaeseRecord> getPaesi(String input) {
    try {
      List<TabellaPaeseRecord> listaPaesi =
          ServiceLocator.getInstance().getServiziBiblioteche().getPaesi(input);
      return listaPaesi;
    } catch (BusinessException | ApiException | IOException e) {
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));
    }
  }
}
