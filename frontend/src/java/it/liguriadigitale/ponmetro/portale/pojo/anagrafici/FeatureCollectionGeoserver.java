package it.liguriadigitale.ponmetro.portale.pojo.anagrafici;

import java.io.Serializable;
import java.util.List;

public class FeatureCollectionGeoserver implements Serializable {

  private static final long serialVersionUID = 5683205391174368235L;

  private List<FeaturesGeoserver> features = null;

  public List<FeaturesGeoserver> getFeatures() {
    return features;
  }

  public void setFeatures(List<FeaturesGeoserver> features) {
    this.features = features;
  }

  @Override
  public String toString() {
    return "FeatureCollectionGeoserver [features=" + features + "]";
  }
}
