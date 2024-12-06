package it.liguriadigitale.ponmetro.api.pojo.categoriecatastali;

/**
 * S3Ct46tCodCategorie
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2022-12-12T16:24:21.216
 */
public class S3Ct46tCodCategorieBuilder {

  public String ct46CodCategoria;

  public String ct46Descriz;

  public static S3Ct46tCodCategorieBuilder getInstance() {
    return new S3Ct46tCodCategorieBuilder();
  }

  public S3Ct46tCodCategorieBuilder addCt46CodCategoria(String ct46CodCategoria) {
    this.ct46CodCategoria = ct46CodCategoria;
    return this;
  }

  public S3Ct46tCodCategorieBuilder addCt46Descriz(String ct46Descriz) {
    this.ct46Descriz = ct46Descriz;
    return this;
  }

  public S3Ct46tCodCategorie build() {
    S3Ct46tCodCategorie obj = new S3Ct46tCodCategorie();
    obj.setCt46CodCategoria(this.ct46CodCategoria);
    obj.setCt46Descriz(this.ct46Descriz);
    return obj;
  }
}
