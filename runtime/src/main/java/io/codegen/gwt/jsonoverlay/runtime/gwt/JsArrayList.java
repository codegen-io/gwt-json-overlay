package io.codegen.gwt.jsonoverlay.runtime.gwt;

import java.util.AbstractList;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

public class JsArrayList<T> extends AbstractList<T> {

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Array")
    static final class JsArray<T> {
        public native void push(T item);
        public native T get(int index);
        public native int getLength();
    }

    private final JsArray<T> array;

    @SuppressWarnings("unchecked")
    public JsArrayList(Object[] values) {
        Object object = values;
        this.array = (JsArray<T>) object;
    }

    @Override
    public T get(int index) {
        return array.get(index);
    }

    @Override
    public int size() {
        return array.getLength();
    }

    @Override
    public boolean add(T e) {
        array.push(e);
        return true;
    }

}
