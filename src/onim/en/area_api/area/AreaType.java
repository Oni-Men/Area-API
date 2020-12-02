package onim.en.area_api.area;

import java.util.HashMap;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

public enum AreaType {
  RECTANGLE("rectangle", "rect"),
  CIRCLE("circle"),
  POLYGON("polygon", "poly");

  private static final HashMap<String, AreaType> labelToType = Maps.newHashMap();
  static {
    for (AreaType type : AreaType.values()) {
      labelToType.put(type.getLiteral(), type);
      for (String alias : type.getAliases()) {
        labelToType.put(alias, type);
      }
    }
  }

  @Nullable
  public static AreaType fromLiteral(String literal) {
    return labelToType.get(literal);
  }

  private final String literal;
  private final String[] aliases;

  AreaType(String literal, String... aliaes) {
    this.literal = literal;
    this.aliases = aliaes;
  }

  public String getLiteral() {
    return this.literal;
  }

  public String[] getAliases() {
    return this.aliases;
  }
}
