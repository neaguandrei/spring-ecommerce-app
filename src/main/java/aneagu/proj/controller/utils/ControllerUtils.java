package aneagu.proj.controller.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerUtils {

    public static UriComponents generateUri(Long id, String methodName, Class controllerClass) {
        return MvcUriComponentsBuilder.fromMethodName(controllerClass,
                methodName, id).build();
    }
}
