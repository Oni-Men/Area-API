package onim.en.area_api.command;

import java.util.HashMap;

import com.google.common.collect.Maps;

public enum CommandOperation {
  CREATE("create"),
  REMOVE("remove"),
  LIST("list"),
  SET("set"),
  ABOUT("about");

  private static HashMap<String, CommandOperation> literalToOperation;
  static {
    literalToOperation = Maps.newHashMap();
    for (CommandOperation operation : CommandOperation.values()) {
      literalToOperation.put(operation.getLiteral(), operation);
    }
  }

  public static CommandOperation fromLiteral(String literal) {
    return literalToOperation.get(literal);
  }

  private final String literal;

  CommandOperation(String literal) {
    this.literal = literal;
  }

  public String getLiteral() {
    return this.literal;
  }

}
