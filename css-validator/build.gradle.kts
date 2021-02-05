dependencies {
    // currently, jacoco is incapable of instrumenting this subject because
    // Method too large: org/w3c/css/parser/analyzer/CssParserTokenManager.jjMoveNfa_0 (II)I
    subject("nu.validator", "cssvalidator", Deps.cssValidatorVersion)
}
