package com.chensoul.security.formlogin;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.security.test.context.TestSecurityContextHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SpringTestContextExtension implements BeforeEachCallback, AfterEachCallback {

  private static List<SpringTestContext> getContexts(Object test) throws IllegalAccessException {
    Field[] declaredFields = test.getClass().getDeclaredFields();
    List<SpringTestContext> result = new ArrayList<>();
    for (Field field : declaredFields) {
      if (SpringTestContext.class.isAssignableFrom(field.getType())) {
        result.add((SpringTestContext) field.get(test));
      }
    }
    return result;
  }

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    TestSecurityContextHolder.clearContext();
    getContexts(context.getRequiredTestInstance()).forEach((springTestContext) -> springTestContext.close());
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    Object testInstance = context.getRequiredTestInstance();
    getContexts(testInstance).forEach((springTestContext) -> springTestContext.setTest(testInstance));
  }

}
