package it.liguriadigitale.ponmetro.api.presentation.rest.categoriacatastale;

import it.liguriadigitale.ponmetro.api.pojo.catasto.S3Ct46tCodCategorie;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.catasto.service.CategorieCatastaliApi;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CategoriaCatastaleResource implements CategorieCatastaliApi {

  private static Log log = LogFactory.getLog(CategoriaCatastaleResource.class);

  @Override
  public List<S3Ct46tCodCategorie> getCategorieCatastali() {
    // TODO Auto-generated method stub
    List<it.liguriadigitale.ponmetro.api.pojo.categoriecatastali.S3Ct46tCodCategorie> lista =
        ServiceLocator.getInstance().getCategorieCatastaliService().getCategorieCatastali();

    return lista.stream()
        .map(
            x -> {
              S3Ct46tCodCategorie cat = new S3Ct46tCodCategorie();
              cat.setCt46CodCategoria(x.ct46CodCategoria);
              cat.setCt46Descriz(x.ct46Descriz);

              return cat;
            })
        .collect(Collectors.toList());
  }
}
