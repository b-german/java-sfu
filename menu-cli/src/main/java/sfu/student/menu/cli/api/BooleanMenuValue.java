package sfu.student.menu.cli.api;

public enum BooleanMenuValue implements MenuOption<BooleanMenuValue> {

  YES("1", "Да", true),
  NO("2", "Нет", false);

  private final String code;
  private final String text;

  private final boolean booleanValue;

  BooleanMenuValue(String code, String text, boolean booleanValue) {
    this.code = code;
    this.text = text;
    this.booleanValue = booleanValue;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getText() {
    return text;
  }

  public boolean getValue() {
    return booleanValue;
  }
}
