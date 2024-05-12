package sfu.student.validation;

import java.util.function.BiFunction;

public interface Validator<T> extends BiFunction<T, ValidationResult, ValidationResult> {

}
