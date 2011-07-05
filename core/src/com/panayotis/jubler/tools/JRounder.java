/*
 * JRounder.java
 *
 * Created on 6 Ιούλιος 2005, 5:31 μμ
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
package com.panayotis.jubler.tools;

import com.panayotis.jubler.subs.SubEntry;
import com.panayotis.jubler.time.Time;

import static com.panayotis.jubler.i18n.I18N._;

/**
 *
 * @author  teras
 */
public class JRounder extends JTool {

    int precise;

    /** Creates new form JRounder */
    public JRounder() {
        super(true);
    }

    public void initialize() {
        initComponents();
    }

    protected String getToolTitle() {
        return _("Round timing");
    }

    protected void storeSelections() {
        switch (PrecS.getValue()) {
            case 0:
                precise = 1;
                break;
            case 1:
                precise = 10;
                break;
            case 2:
                precise = 100;
                break;
            default:
                precise = 1000;
        }
    }

    protected void affect(int index) {
        SubEntry sub = affected_list.get(index);
        roundTime(sub.getStartTime());
        roundTime(sub.getFinishTime());
    }

    private void roundTime(Time t) {
        double round = t.toSeconds();
        round *= precise;
        round = Math.round(round);
        t.setTime(round / precise);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PrecS = new javax.swing.JSlider();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setText(_("Number of decimals")+"      ");
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);

        PrecS.setMajorTickSpacing(1);
        PrecS.setMaximum(2);
        PrecS.setPaintLabels(true);
        PrecS.setPaintTicks(true);
        PrecS.setSnapToTicks(true);
        PrecS.setToolTipText(_("Decimal digits"));
        jPanel1.add(PrecS, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider PrecS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
