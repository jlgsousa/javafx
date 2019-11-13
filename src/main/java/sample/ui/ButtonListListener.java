package sample.ui;

import com.gk_software.cst.development.tableau_editor.objects.TableauPositionWrapper;
import com.gk_software.cst.development.tableau_editor.service.MapperProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;


public class ButtonListListener implements ListSelectionListener {

  private RSyntaxTextArea buttonTextArea;

  public ButtonListListener(RSyntaxTextArea buttonTextArea) {
    this.buttonTextArea = buttonTextArea;
  }


  @Override
  public void valueChanged(ListSelectionEvent e) {
    if(!e.getValueIsAdjusting()) {
      JList<TableauPositionWrapper> list = (JList<TableauPositionWrapper>)e.getSource();
      TableauPositionWrapper position = list.getSelectedValue();
      if(position != null) {
        try {
          buttonTextArea.setText(MapperProvider.getObjectMapper().writeValueAsString(position.getPosition()));
        }
        catch(IOException e1) {
          e1.printStackTrace();
        }
      }
      else
      {
        buttonTextArea.setText("");
      }
    }

  }

}
