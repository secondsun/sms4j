package net.saga.console.emulator.sms.sms4j.test.util;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by summers on 7/17/17.
 */
public class ByteArrayConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        String[] bytesStrings = source.toString().split(",");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(bytesStrings.length);
        Arrays.asList(bytesStrings).forEach((byteString) -> {
            bytes.write((byte)Integer.parseInt(byteString.split("0x")[1].trim(), 16));
        });
        return bytes.toByteArray();
    }
}
