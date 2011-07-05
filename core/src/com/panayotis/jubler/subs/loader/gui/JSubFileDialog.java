/*
 * JSubFileDialog.java
 *
 * Created on Dec 21, 2008, 10:57:59 AM
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
package com.panayotis.jubler.subs.loader.gui;

import static com.panayotis.jubler.i18n.I18N._;

import com.panayotis.jubler.media.MediaFile;
import com.panayotis.jubler.os.FileCommunicator;
import com.panayotis.jubler.subs.SubFile;
import com.panayotis.jubler.subs.Subtitles;
import com.panayotis.jubler.subs.loader.SubFileFilter;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author teras
 */
public class JSubFileDialog extends javax.swing.JDialog {

    private boolean isAccepted;
    private JFileOptions jload;
    private JFileOptions jsave;

    public JSubFileDialog() {
        super((Frame) null, true);
        initComponents();
        chooser.addChoosableFileFilter(new SubFileFilter());
        chooser.setSelectedFile(new File(FileCommunicator.getDefaultDirPath(), "."));
        jload = new JLoadOptions();
        jsave = new JSaveOptions();
    }

    private SubFile showDialog(Frame parent, Subtitles subs, MediaFile mfile, JFileOptions jopt) {
        SubFile sfile;
        jopt.updateVisuals(subs, mfile);
        getContentPane().removeAll();
        getContentPane().add(chooser, BorderLayout.CENTER);
        getContentPane().add(jopt, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);

        if (!isAccepted)
            return null;

        if (subs == null) // Load
            sfile = new SubFile(chooser.getSelectedFile(), SubFile.EXTENSION_GIVEN);
        else {   // Save
            sfile = new SubFile(subs.getSubFile());
            sfile.setFile(chooser.getSelectedFile());
        }
        jopt.applyOptions(sfile);
        if (subs != null) // Only in Save
            sfile.updateFileByType();
        FileCommunicator.setDefaultDir(chooser.getCurrentDirectory());
        return sfile;
    }

    public SubFile getSaveFile(Frame parent, Subtitles subs, MediaFile mfile) {
        setTitle(_("Save Subtitles"));
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setSelectedFile(subs.getSubFile().getStrippedFile());
        return showDialog(parent, subs, mfile, jsave);
    }

    public SubFile getLoadFile(Frame parent, MediaFile mfile) {
        setTitle(_("Load Subtitles"));
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        return showDialog(parent, null, mfile, jload);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooser = new javax.swing.JFileChooser();

        chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void chooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooserActionPerformed
        isAccepted = evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION);
        setVisible(false);
    }//GEN-LAST:event_chooserActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser chooser;
    // End of variables declaration//GEN-END:variables
}
