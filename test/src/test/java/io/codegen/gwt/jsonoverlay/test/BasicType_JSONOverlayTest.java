package io.codegen.gwt.jsonoverlay.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import io.codegen.gwt.jsonoverlay.runtime.jre.JreJsonFactory;
import test.BasicType;
import test.overlay.BasicType_JSONOverlay;

public class BasicType_JSONOverlayTest {

    @Test
    public void test() {
        String json =  "{"
                + "\"string\":\"MyString\","
                + "\"int\":\"1\","
                + "}";
        JsonFactory factory = new JreJsonFactory();
        BasicType block = BasicType_JSONOverlay.parse(json, factory);
        assertEquals("MyString", block.getString());
        assertEquals(1, block.getInt());
    }

}
