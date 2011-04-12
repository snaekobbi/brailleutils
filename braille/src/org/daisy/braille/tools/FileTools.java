/*
 * Braille Utils (C) 2010-2011 Daisy Consortium 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.daisy.braille.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FileTools {
	private final static Logger logger = Logger.getLogger(FileTools.class.getCanonicalName());
	
	public static void copy(InputStream is, OutputStream os) throws IOException {
		InputStream bis = new BufferedInputStream(is);
		OutputStream bos = new BufferedOutputStream(os);
		int b;
		while ((b = bis.read())!=-1) {
			bos.write(b);
		}
		bos.flush();
		bos.close();
		bis.close();
	}
	
	public static File[] listFiles(File dir, final String ext) {
		return dir.listFiles(new FilenameFilter(){
			//jvm1.6@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(ext);
			}});
	}
	
	public static URL[] toURL(File[] files) {
		ArrayList<URL> jars = new ArrayList<URL>();
		if (files!=null && files.length>0) {
			int i = 0;
			for (File f : files) {
				try {
					jars.add(f.toURI().toURL());
				} catch (MalformedURLException e) {
					logger.warning("Failed to convert " + f + " into an URL.");
				}
				i++;
			}
		}
		return jars.toArray(new URL[]{});
	}

}
