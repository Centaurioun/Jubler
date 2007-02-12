/*
 * JVersion.java
 *
 * Created on 15 Νοέμβριος 2006, 2:56 πμ
 */

package com.panayotis.jubler.information;

import com.panayotis.jubler.JIDialog;
import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.options.Options;
import com.panayotis.jubler.os.SystemDependent;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author  teras
 */
public class JVersion extends javax.swing.JPanel {
    
    private static final String DownloadPage = "http://www.jubler.org/download.html";
    
    private Properties version;
    private Properties webversion;
    private int web_release;
    
    /** Creates new form JVersion */
    public JVersion() {
        initComponents();
        version = new Properties();
        webversion = new Properties();

        if (getVersion()) { /* A new VALID version was found */
            JIDialog.message(null, this, _("New version"), JIDialog.INFORMATION_MESSAGE);
            if (DisregardB.isSelected()) {
                Options.setOption("System.Version.IgnoreUpdate", Integer.toString(web_release));
                Options.saveOptions();
            }
        }
    }
    
    
    public String getCurrentVersion() {
        return version.getProperty("version", "-");
    }
    public String getWebVersion() {
        return webversion.getProperty("version", "-");
    }
    
    
    public boolean getVersion() {
        try {
            version.load(JVersion.class.getResource("/com/panayotis/jubler/information/version.prop").openStream());
            
            webversion.load(new URL("http://www.panayotis.com/versions/jubler").openStream());
        } catch (IOException e) {
             return false;
        }
        
        
        web_release = Integer.parseInt(webversion.getProperty("release", "0"));
        
        int c_release = Integer.parseInt(version.getProperty("release", "0"));
        if (c_release >= web_release) return false;
        
        boolean force = Boolean.parseBoolean(webversion.getProperty("force.upgrade", "false"));
        int ignore_prefs = Integer.parseInt(Options.getOption("System.Version.IgnoreUpdate", "0"));
        
        
        DisregardB.setSelected(false);
        if ((!force) && (ignore_prefs >= web_release) ) return false;
        
        if (force) DisregardB.setEnabled(false);
        
        URLT.setText(DownloadPage);
        URLT.setEditable(false);
        
        CVersionL.setText(_("Current version:")+" " + getCurrentVersion());
        NVersionL.setText(_("Version found:")+" " + getWebVersion());
        
        if (force)
            PriorityL.setText(_("It is important to upgrade from the following URL:"));
        else
            PriorityL.setText(_("Please consider upgrading from the following URL:"));
        
        return true;
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        NewVersionL = new javax.swing.JLabel();
        CVersionL = new javax.swing.JLabel();
        NVersionL = new javax.swing.JLabel();
        Blank1L = new javax.swing.JLabel();
        PriorityL = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        URLT = new javax.swing.JTextField();
        GoB = new javax.swing.JButton();
        Blank2L = new javax.swing.JLabel();
        DisregardB = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridLayout(0, 1));

        NewVersionL.setText(_("New Jubler version found!"));
        add(NewVersionL);

        add(CVersionL);

        add(NVersionL);

        add(Blank1L);

        add(PriorityL);

        jPanel1.setLayout(new java.awt.BorderLayout());

        URLT.setBackground(java.awt.SystemColor.control);
        URLT.setEditable(false);
        URLT.setText("jTextField1");
        jPanel1.add(URLT, java.awt.BorderLayout.CENTER);

        GoB.setText(_("Go"));
        GoB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GoBActionPerformed(evt);
            }
        });

        jPanel1.add(GoB, java.awt.BorderLayout.EAST);

        add(jPanel1);

        add(Blank2L);

        DisregardB.setText(_("Do not inform me again for this version"));
        DisregardB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DisregardB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        add(DisregardB);

    }// </editor-fold>//GEN-END:initComponents

    private void GoBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GoBActionPerformed
        SystemDependent.openURL(DownloadPage);
    }//GEN-LAST:event_GoBActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Blank1L;
    private javax.swing.JLabel Blank2L;
    private javax.swing.JLabel CVersionL;
    private javax.swing.JCheckBox DisregardB;
    private javax.swing.JButton GoB;
    private javax.swing.JLabel NVersionL;
    private javax.swing.JLabel NewVersionL;
    private javax.swing.JLabel PriorityL;
    private javax.swing.JTextField URLT;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}
