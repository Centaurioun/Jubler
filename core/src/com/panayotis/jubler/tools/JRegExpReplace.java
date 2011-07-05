/*
 * JRegExpReplace.java
 *
 * Created on 30 Ιούνιος 2005, 2:21 πμ
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

import java.util.ArrayList;
import com.panayotis.jubler.subs.SubEntry;
import com.panayotis.jubler.tools.replace.JReplaceList;
import com.panayotis.jubler.tools.replace.ReplaceModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import static com.panayotis.jubler.i18n.I18N._;

/**
 *
 * @author  teras
 */
public class JRegExpReplace extends JTool {

    private ArrayList<Pattern> patterns;
    private ArrayList<String> texts;
    private JReplaceList rlist;

    /**
     * Creates new form JRegExpReplace
     */
    public JRegExpReplace() {
        super(true);
    }

    public void initialize() {
        initComponents();
        rlist = new JReplaceList();
        TextList.setListData(rlist.getModel().getReplaceList());
        patterns = new ArrayList<Pattern>();
        texts = new ArrayList<String>();
    }

    protected String getToolTitle() {
        return _("Regular Expression replace");
    }

    protected void storeSelections() {
        ReplaceModel model = rlist.getModel();
        patterns.clear();
        texts.clear();
        for (int i = 0; i < model.size(); i++)
            if (model.elementAt(i).usable) {
                patterns.add(Pattern.compile(model.elementAt(i).fromS));
                texts.add(model.elementAt(i).toS);
            }
    }

    protected void affect(int index) {
        SubEntry sub = affected_list.get(index);
        String res;
        Matcher m;

        res = sub.getText();
        for (int i = 0; i < patterns.size(); i++) {
            m = patterns.get(i).matcher(res);
            res = m.replaceAll(texts.get(i));
        }
        sub.setText(res);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextList = new javax.swing.JList();
        EditB = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(_("Regular expressions to be executed")));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(259, 80));

        TextList.setToolTipText(_("List of replacements to be done"));
        jScrollPane1.setViewportView(TextList);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        EditB.setText(_("Edit"));
        EditB.setActionCommand("Edit");
        EditB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBActionPerformed(evt);
            }
        });
        jPanel1.add(EditB, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void EditBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBActionPerformed
        int ret;
        Object[] options = {_("Use"), _("Cancel"), _("Reset")};
        ret = JOptionPane.showOptionDialog(this, rlist, _("Edit regular expression replace list"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        switch (ret) {
            case 0:
                //do
                rlist.getModel().saveOptions();
                break;
            case 1:
            case JOptionPane.CLOSED_OPTION:
                //cancel
                rlist.getModel().loadOptions();
                break;
            case 2:
                // reset
                rlist.getModel().reset();
                rlist.getModel().saveOptions();
                break;
        }
        TextList.setListData(rlist.getModel().getReplaceList());
    }//GEN-LAST:event_EditBActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EditB;
    private javax.swing.JList TextList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
