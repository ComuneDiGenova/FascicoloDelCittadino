package it.liguriadigitale.ponmetro.api.business.categoriecatastali.impl;

import it.liguriadigitale.ponmetro.api.business.categoriecatastali.service.ICategorieCatastali;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.integration.dao.S3Ct46tCodCategorieDAO;
import it.liguriadigitale.ponmetro.api.pojo.categoriecatastali.S3Ct46tCodCategorie;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CategorieCatastaliImpl implements ICategorieCatastali {

  private static Log log = LogFactory.getLog(CategorieCatastaliImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<S3Ct46tCodCategorie> getCategorieCatastali() {
    // TODO Auto-generated method stub
    try {

      S3Ct46tCodCategorie cat = new S3Ct46tCodCategorie();
      S3Ct46tCodCategorieDAO dao = new S3Ct46tCodCategorieDAO(cat);
      PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
      return (List<S3Ct46tCodCategorie>) helper.cercaOggetti();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      log.debug("[getCategorieCatastali - Service] Nessuna categoria catastale trovata");
      return new ArrayList<S3Ct46tCodCategorie>();
    }
  }
}
