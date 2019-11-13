package sample.objects;

import com.gk_software.gkr.pos.tableau.api.model.TableauPosition;

public class TableauPositionWrapper  {
  private TableauPosition position;
  private String translation;
  private TableauCacheEntry tableau;
  
  
  public TableauPositionWrapper(TableauPosition position, TableauCacheEntry cacheEntry, String translation) {
    this.setPosition(position);
    this.setTranslation(translation);
    this.setTableau(cacheEntry);
  }
  
  
  public String getTranslation() {
    return translation;
  }

  
  public void setTranslation(String translation) {
    this.translation = translation;
  }

  @Override
  public String toString() {
   return translation;
  }

  public TableauPosition getPosition() {
    return position;
  }

  public void setPosition(TableauPosition position) {
    this.position = position;
  }

  public TableauCacheEntry getTableau() {
    return tableau;
  }

  public void setTableau(TableauCacheEntry tableau) {
    this.tableau = tableau;
  }

}
