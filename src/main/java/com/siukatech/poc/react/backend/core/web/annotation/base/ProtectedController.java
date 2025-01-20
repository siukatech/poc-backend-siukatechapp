package com.siukatech.poc.react.backend.core.web.annotation.base;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProtectedController {
    String REQUEST_MAPPING_URI_PREFIX = "/protected";
}