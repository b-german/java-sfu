package sfu.student.menu.cli.api;

public interface MenuOption<EnumClass extends Enum<?>> {

  String getCode();

  String getText();

  default EnumClass getEnumValue() {
    return (EnumClass) this;
  }

  default String asMenuOption() {
    return "%s) %s".formatted(getCode(), getText());
  }

}
