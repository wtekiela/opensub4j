package com.github.wtekiela.opensub4j.response;

import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@interface OpenSubtitlesApi {

    String fieldName();

}
