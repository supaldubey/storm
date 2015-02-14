package in.cubestack.apps.android.lib.storm.core;

import java.util.HashMap;
import java.util.Map;




/**
 * A simple dao framework for Java based ORM
 * Copyright (c) 2011 Supal Dubey, supal.dubey@gmail.com
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class AliasGenerator {

	private int counter = 0;
	
	private Map<Class<?>, String> aliasMap = new HashMap<>();
	
	public String generateAlias(Class<?> clazz) {
		if(aliasMap.containsKey(clazz)) {
			return aliasMap.get(clazz);
		}
		
		String name = clazz.getSimpleName();
		
		if(name.length() > 5) {
			name = name.substring(0, 2) + name.substring(name.length() - 4,name.length()-1) + ++counter;
		} else {
			name = name + ++counter ;
		}
		aliasMap.put(clazz, name);
		return name;
	}
}
