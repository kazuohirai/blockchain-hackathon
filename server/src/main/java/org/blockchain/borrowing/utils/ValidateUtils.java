package org.blockchain.borrowing.utils;

import org.apache.commons.lang3.Validate;

import java.util.Arrays;

public class ValidateUtils {

    public static void notNulls(Object o, Object... os) {
        for (Object i : Arrays.asList(o, os)) {
            Validate.notNull(i);
        }
    }
}
