/*
 * AbstractTextSubFormat.java
 *
 * Created on 22 Ιούνιος 2005, 3:17 πμ
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
package com.panayotis.jubler.subs.loader;

import com.panayotis.jubler.os.DEBUG;
import com.panayotis.jubler.subs.Subtitles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.panayotis.jubler.media.MediaFile;
import com.panayotis.jubler.subs.SubAttribs;
import com.panayotis.jubler.subs.SubEntry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Collection;

/**
 *
 * @author teras
 */
public abstract class AbstractGenericTextSubFormat extends SubFormat {

    protected static final String nl = "\\\n";
    protected static final String sp = "[ \\t]*";
    protected Subtitles subtitle_list;

    /* Initialization functions */
//    private void setFPS(float FPS) { this.FPS=FPS; }

    /* Saving functions */
    protected abstract void appendSubEntry(SubEntry sub, StringBuilder str);

    public Subtitles parse(String input, float FPS, File f) {
        try {
            if (!isSubtitleCompatible(input))
                return null;    // Not valid - test pattern does not match
            DEBUG.debug("Found file " + getExtendedName());
            subtitle_list = new Subtitles();
            input = initLoader(input);
            for (SubEntry entry : loadSubtitles(input))
                subtitle_list.add(entry);
            if (subtitle_list.isEmpty())
                return null;
            return subtitle_list;
        } catch (Exception e) {
            DEBUG.debug(e);
            return null;
        }
    }

    protected String initLoader(String input) {
        return input + "\n";
    }

    protected void cleanupLoader(Subtitles sub) {
    }

    public boolean produce(Subtitles subs, File outfile, MediaFile media) throws IOException {
        StringBuilder res = new StringBuilder();
        boolean is_convert = subs.isRequiredToConvert(SubEntry.class);
        if (is_convert)
            subs.convert(SubEntry.class, getClassLoader());//end if (is_convert)
        initSaver(subs, media, res);
        for (int i = 0; i < subs.size(); i++)
            appendSubEntry(subs.elementAt(i), res);
        cleanupSaver(res);

        /* Clean up leading \n characters */
        while (res.length() > 1 && res.charAt(res.length() - 1) == '\n' && res.charAt(res.length() - 2) == '\n')
            res.setLength(res.length() - 1);

        // encoder = Charset.forName(jub.prefs.getSaveEncoding()).newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        CharsetEncoder encoder = Charset.forName(ENCODING).newEncoder();

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile), encoder));
        out.write(res.toString().replace("\n", "\r\n"));
        out.close();
        return true;
    }

    protected void initSaver(Subtitles subs, MediaFile media, StringBuilder header) {
    }

    protected void cleanupSaver(StringBuilder footer) {
    }

    protected void updateAttributes(String input, Pattern title, Pattern author, Pattern source, Pattern comments) {
        Matcher m;
        String attrs[] = new String[4];

        m = title.matcher(input);
        if (m.find())
            attrs[0] = m.group(1).trim();

        m = author.matcher(input);
        if (m.find())
            attrs[1] = m.group(1).trim();

        m = source.matcher(input);
        if (m.find())
            attrs[2] = m.group(1).trim();

        m = comments.matcher(input);
        StringBuilder com_b = new StringBuilder();
        while (m.find())
            if (!(m.start() != 0 && input.charAt(m.start() - 1) != '\n'))
                com_b.append(m.group(1).trim()).append('\n');
        String com = com_b.toString().replace('|', '\n');
        if (com.length() > 0)
            attrs[3] = com.substring(0, com.length() - 1);

        for (int i = 0; i < attrs.length; i++)
            if (attrs[i] != null && attrs[i].equals(""))
                attrs[i] = null;

        subtitle_list.setAttribs(new SubAttribs(attrs[0], attrs[1], attrs[2], attrs[3]));
    }

    protected abstract boolean isSubtitleCompatible(String input);

    protected abstract Collection<SubEntry> loadSubtitles(String input);
}
