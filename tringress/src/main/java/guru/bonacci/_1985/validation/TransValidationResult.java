package guru.bonacci._1985.validation;

import lombok.Value;

@Value
public class TransValidationResult {
  
  private boolean isValid;
  private String errorMessage;
}