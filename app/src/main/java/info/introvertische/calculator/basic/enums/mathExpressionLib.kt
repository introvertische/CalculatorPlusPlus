package info.introvertische.calculator.basic.enums

enum class mathExpressionLib(val mathOperation: String) {

    BINARY_OPERATIONS("\\-?((\\d+\\.\\d+)|(\\d+))(\\+|\\-|\\*|\\:)\\-?((\\d+\\.\\d+)|(\\d+))"),
    PLUS("\\-?((\\d+\\.\\d+)|(\\d+))\\+\\-?((\\d+\\.\\d+)|(\\d+))"),
    MINUS("\\-?((\\d+\\.\\d+)|(\\d+))\\-\\-?((\\d+\\.\\d+)|(\\d+))"),
    MULTIPLICATION("\\-?((\\d+\\.\\d+)|(\\d+))\\×\\-?((\\d+\\.\\d+)|(\\d+))"),
    DIVISION("\\-?((\\d+\\.\\d+)|(\\d+))\\÷\\-?((\\d+\\.\\d+)|(\\d+))"),
    DEGREE("\\-?((\\d+\\.\\d+)|(\\d+))\\^\\-?((\\d+\\.\\d+)|(\\d+))"),
    SQUARE_ROOT("\\√((\\d+\\.\\d+)|(\\d+))"),
    CUBE_ROOT("\\∛((\\d+\\.\\d+)|(\\d+))"),
    SIN("sin((\\d+\\.\\d+)|(\\d+))"),
    ARCSIN("sin⁻¹((\\d+\\.\\d+)|(\\d+))"),
    COS("cos((\\d+\\.\\d+)|(\\d+))"),
    ARCCOS("cos⁻¹((\\d+\\.\\d+)|(\\d+))"),
    TG("tg((\\d+\\.\\d+)|(\\d+))"),
    ARCTG("tg⁻¹((\\d+\\.\\d+)|(\\d+))"),
    FACTORIAL("((\\d+\\.\\d+)|(\\d+))\\!"),
    MOD("\\-?((\\d+\\.\\d+)|(\\d+))mod\\-?((\\d+\\.\\d+)|(\\d+))"),
    MODULE("abs\\-?((\\d+\\.\\d+)|(\\d+))"),
    LN("ln((\\d+\\.\\d+)|(\\d+))"),
    LOG("log((\\d+\\.\\d+)|(\\d+))"),
    FINAL_RESULT("^\\-?\\+?((\\d+\\.\\d+)|(\\d+))$")
}