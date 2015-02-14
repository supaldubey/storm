/**
 *
 */
package in.cubestack.apps.android.lib.storm.core;

import java.lang.reflect.Field;

import android.util.Log;


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
public class ReflectionUtil {
    public static void setField(Object object, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        try {
            Field field = doGetIt(fieldName, object.getClass());
            field.setAccessible(true);

            field.set(object, value);
            field.setAccessible(false);
        } catch (Exception ex) {
            Log.e("ERRORR", "Error while setting " + fieldName + "of " + object.getClass() + " with value " + value, ex);
        }
    }

    public static Object getFieldValue(Object object, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = doGetIt(fieldName, object.getClass());

        field.setAccessible(true);
        Object returnValue = (Object) field.get(object);
        field.setAccessible(false);
        return returnValue;
    }

    public static Object copyValues(Object sourceObject, Class<?> targetClass) throws Exception {
        Object targetValue = null;

        targetValue = (Object) targetClass.newInstance();
        for (Field field : sourceObject.getClass().getDeclaredFields()) {
            try {
                setField(targetValue, field.getName(), getFieldValue(sourceObject, field.getName()));
            } catch (NoSuchFieldException e) {
                //IgnoreLogging.logInfo(ReflectionUtil.class, "Ignored Field " + field.getName());
            }
        }

        return targetValue;
    }


    private static Field doGetIt(String name, Class<?> clazz) {
        Class<?> superClz = clazz;
        while (superClz.getSuperclass() != null) {
            for (Field field : superClz.getDeclaredFields()) {
                if (field.getName().equals(name)) {
                    return field;
                }
            }
            superClz = superClz.getSuperclass();
        }
        return null;
    }
}
