package guru.bonacci._1985.tringress.concurrent;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TripException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  public TripException(String message) {
    super(message);
  }
}