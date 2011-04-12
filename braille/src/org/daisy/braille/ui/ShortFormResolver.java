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
package org.daisy.braille.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.daisy.factory.Factory;

/**
 * Provides shorter names for factory identifiers, to be used in command line user interfaces. 
 * @author Joel Håkansson
 *
 */
public class ShortFormResolver {
	private final HashMap<String, String> idents;
	private final HashMap<String, String> shorts;
	
	public ShortFormResolver(Collection<? extends Factory> obj) {
		this.idents = new HashMap<String, String>();
		this.shorts = new HashMap<String, String>();
		//analyze uniqueness short forms
		HashMap<String, Integer> uniqueIndex = new HashMap<String, Integer>();
		for (Factory f : obj) {
			String identifier = f.getIdentifier().toLowerCase();
			for (String p : identifier.split("\\.")) {
				Integer i = uniqueIndex.get(p);
				if (i!=null) {
					uniqueIndex.put(p, i+1);
				} else {
					uniqueIndex.put(p, 1);
				}
			}
		}
		//add short forms
		for (Factory f : obj) {
			String identifier = f.getIdentifier().toLowerCase();
			String[] s = identifier.split("\\.");
			Integer x = uniqueIndex.get(s[s.length-1]);
			assert x!=null;
			if (x==1) {
				idents.put(s[s.length-1], f.getIdentifier());
				shorts.put(f.getIdentifier(), s[s.length-1]);
			} else {
				//TODO: expand on this
				// Don't do anything
				idents.put(identifier, f.getIdentifier());
				shorts.put(f.getIdentifier(), identifier);
			}
		}
	}

	public List<String> getShortForms() {
		ArrayList<String> ret = new ArrayList<String>(idents.keySet());
		Collections.sort(ret);
		return ret;
	}
	
	public String getShortForm(String id) {
		return shorts.get(id);
	}
	
	public String resolve(String shortForm) {
		return idents.get(shortForm.toLowerCase());
	}
}