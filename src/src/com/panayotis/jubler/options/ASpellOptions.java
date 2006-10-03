/*
 * ASpellOptions.java
 *
 * Created on 16 Ιούλιος 2005, 4:17 μμ
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
import com.panayotis.jubler.options.OptionsIO;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;

import static com.panayotis.jubler.i18n.I18N._;/**
 *
 * @author  teras
 */
public class ASpellOptions extends ExtOptions {
    
    String language;
    Vector<String> dictionaries;
    
    /** Creates new form ASpellOptions */
    public ASpellOptions(String type, String name, String programname) {
        super(type, name, programname);
        initComponents();
        loadXtraOptions();
        
        add(Visuals, BorderLayout.SOUTH);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LangList = new javax.swing.JList();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText(_("Language to use"));
        add(jLabel1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setViewportView(LangList);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents
    
    protected void loadXtraOptions() {
        /* load dictionaries list from aspell */
        dictionaries = new Vector<String>();
        try {
            String [] cmd = {getExecFileName(), "dicts"};
            Process proc = Runtime.getRuntime().exec(cmd);
            BufferedReader get = new BufferedReader( new InputStreamReader(proc.getInputStream()));
            
            String d;
            while ( (d=get.readLine()) != null ) {
                dictionaries.add(d);
            }
        } catch (IOException e) {}
        
        Properties pr = OptionsIO.getPrefFile();
        language = pr.getProperty(type + "." + name + ".Language", "en");

        LangList.setListData(dictionaries);
        LangList.setSelectedValue(language, true);
    }
    
    
    
    public void saveOptions() {
        super.saveOptions();

        int which = LangList.getSelectedIndex();
        if ( which < 0 ) language = "";
        else language = LangList.getModel().getElementAt(which).toString();
        
        Properties pr = OptionsIO.getPrefFile();
        pr.setProperty(type + "." + name + ".Language", language);
        OptionsIO.savePrefFile(pr);
    }
    
    public void resetOptions() {
        super.resetOptions();
        LangList.setSelectedValue(language, true);
    }
    
    public String getLanguage() {
        return language;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList LangList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
