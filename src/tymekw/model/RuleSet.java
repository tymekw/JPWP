package model;

import java.util.HashSet;

public abstract class RuleSet extends HashSet<Rule> {
    boolean isCapture;
    Field captured;
}
