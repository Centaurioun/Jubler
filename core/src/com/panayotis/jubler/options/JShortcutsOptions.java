/*
 * JShortcutsOptions.java
 *
 * Created on June 1, 2007, 10:58 AM
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

import com.panayotis.jubler.os.SystemDependent;
import static com.panayotis.jubler.i18n.I18N._;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author  teras
 */
public class JShortcutsOptions extends JPanel implements OptionsHolder {

    private ShortcutsModel smodel;

    /** Creates new form JShortcutsOptions */
    public JShortcutsOptions(JMenuBar bar) {
        smodel = new ShortcutsModel(bar);
        initComponents();

        ShortT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting())
                    return;
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty())
                    return;
                smodel.setSelection(ShortT.getSelectedRow());
            }
        });
        ShortT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void applyMenuShortcuts(JMenuBar bar) {
        smodel.applyMenuShortcuts(bar);
    }

    public void loadPreferences() {
        smodel.loadPreferences();
    }

    public void savePreferences() {
        smodel.savePreferences();
    }

    public JPanel getTabPanel() {
        return this;
    }

    public String getTabName() {
        return _("Shortcuts");
    }

    public String getTabTooltip() {
        return _("Set the menu keyboard shortcuts");
    }

    public Icon getTabIcon() {
        return new ImageIcon(getClass().getResource("/icons/shortcut_pref.png"));
    }

    public void changeProgram() {
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ShortT = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        ClearSB = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        ResetSB = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 200));

        ShortT.setModel(smodel);
        ShortT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ShortTKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ShortTKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(ShortT);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        ClearSB.setText(_("Clear current shortcut"));
        SystemDependent.setCommandButtonStyle(ClearSB, "only");
        ClearSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearSBActionPerformed(evt);
            }
        });
        jPanel3.add(ClearSB);

        jPanel2.add(jPanel3, java.awt.BorderLayout.EAST);

        ResetSB.setText(_("Reset all to defaults"));
        SystemDependent.setCommandButtonStyle(ResetSB, "only");
        ResetSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetSBActionPerformed(evt);
            }
        });
        jPanel4.add(ResetSB);

        jPanel2.add(jPanel4, java.awt.BorderLayout.WEST);

        add(jPanel2, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void ResetSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetSBActionPerformed
        smodel.resetAllShortcuts();
    }//GEN-LAST:event_ResetSBActionPerformed

    private void ClearSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearSBActionPerformed
        smodel.removeShortcut();
    }//GEN-LAST:event_ClearSBActionPerformed

    private void ShortTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ShortTKeyReleased
        smodel.keyReleased(evt.getKeyCode());
    }//GEN-LAST:event_ShortTKeyReleased

    private void ShortTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ShortTKeyPressed
        smodel.keyPressed(evt.getKeyCode());
    }//GEN-LAST:event_ShortTKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClearSB;
    private javax.swing.JButton ResetSB;
    private javax.swing.JTable ShortT;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
