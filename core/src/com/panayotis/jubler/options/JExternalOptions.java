/*
 * JExternalOptions.java
 *
 * Created on 16 Ιούλιος 2005, 1:51 μμ
 *
 * This file is part of Jubler.
 *
 * Jubler is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2.
 *
 *
 * Jubler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Jubler; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

package com.panayotis.jubler.options;

import com.panayotis.jubler.tools.externals.AvailExternals;
import static com.panayotis.jubler.i18n.I18N._;
import java.awt.CardLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;



/**
 *
 * @author  teras
 */
public class JExternalOptions extends JPanel implements OptionsHolder {
    private AvailExternals list;

    /**
     * Creates new form JExternalOptions
     */
    public JExternalOptions(AvailExternals list) {
        initComponents();
        this.list = list;

        for ( int i = 0 ; i < list.size() ; i++ ) {
            PList.addItem(list.nameAt(i));
            JExtBasicOptions opts = list.programAt(i).getOptionsPanel();
            ParamsP.add( (opts==null ? new JPanel() : opts), Integer.toString(i));
        }
        SelectorL.setText(_("Select a {0} from the following list", _(list.getType()).toLowerCase() ));
    }
    
    public Object getObject() {
        return list.elementAt(PList.getSelectedIndex());
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        ExtSelectorP = new javax.swing.JPanel();
        SelectorL = new javax.swing.JLabel();
        PList = new javax.swing.JComboBox();
        ParamsP = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        ExtSelectorP.setLayout(new java.awt.BorderLayout());

        SelectorL.setText("[selector]");
        ExtSelectorP.add(SelectorL, java.awt.BorderLayout.NORTH);

        PList.setToolTipText(_("Select a video player in order to use it"));
        PList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PListActionPerformed(evt);
            }
        });

        ExtSelectorP.add(PList, java.awt.BorderLayout.CENTER);

        add(ExtSelectorP, java.awt.BorderLayout.NORTH);

        ParamsP.setLayout(new java.awt.CardLayout());

        ParamsP.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        add(ParamsP, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents
    
    private void PListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PListActionPerformed
        ((CardLayout)ParamsP.getLayout()).show(ParamsP, Integer.toString(PList.getSelectedIndex()));
        tabChanged();
    }//GEN-LAST:event_PListActionPerformed
    
    public void loadPreferences() {
        if (list.size()<1)
            return;
        JExtBasicOptions opts = list.programAt(PList.getSelectedIndex()).getOptionsPanel();
        if (opts!=null) opts.loadPreferences();
    }
    
    public void savePreferences() {
        JExtBasicOptions opts = list.programAt(PList.getSelectedIndex()).getOptionsPanel();
        if (opts!=null) opts.savePreferences();
    }
    
    public JPanel getTabPanel() { return this; }
    

    public void tabChanged() {
        JExtBasicOptions opts = list.programAt(PList.getSelectedIndex()).getOptionsPanel();
        if (opts!=null) opts.updateOptionsPanel();
    }
    
    public String getTabName() { return list.getType(); }
    public String getTabTooltip() { return _("{0} options", list.getType()); }
    public Icon getTabIcon() { return new ImageIcon(getClass().getResource(list.getIconName())); }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ExtSelectorP;
    private javax.swing.JComboBox PList;
    private javax.swing.JPanel ParamsP;
    private javax.swing.JLabel SelectorL;
    // End of variables declaration//GEN-END:variables
    
}
