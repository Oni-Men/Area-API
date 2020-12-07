package onim.en.area_api.command.executor;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ParamType {
  Name("name"),
  Message("message"),
  Decoration("decoration"),
  Area("area");

  private static final Map<String, ParamType> literalToType = Maps.newHashMap();
  static {
    for (ParamType type : ParamType.values()) {
      literalToType.put(type.getLiteral(), type);
    }
  }

  public static ParamType fromLiteral(String literal) {
    return literalToType.get(literal);
  }

  private final String literal;

  ParamType(String literal) {
    this.literal = literal;
  }

  public String getLiteral() {
    return this.literal;
  }
}