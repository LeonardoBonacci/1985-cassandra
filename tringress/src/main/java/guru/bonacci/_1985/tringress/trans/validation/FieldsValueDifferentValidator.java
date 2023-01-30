package guru.bonacci._1985.tringress.trans.validation;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldsValueDifferentValidator implements ConstraintValidator<FieldsValueDifferent, Object> {

  private String field1;
  private String field2;

  public void initialize(FieldsValueDifferent constraintAnnotation) {
    this.field1 = constraintAnnotation.field1();
    this.field2 = constraintAnnotation.field2();
  }

  public boolean isValid(Object value, ConstraintValidatorContext context) {
    String from = (String)new BeanWrapperImpl(value).getPropertyValue(field1);
    String to = (String)new BeanWrapperImpl(value).getPropertyValue(field2);

    // valid when different
    return !from.equals(to);
  }
}