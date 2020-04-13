package model;

import java.util.LinkedList;
import java.util.List;

public class EnglishRuleSet extends RuleSet {

    EnglishRuleSet() {
        addRules();
    }

    private void addRules(){
        add(new ProperFieldRule());
        add(new PawnInTurnRole());
        add(new JumpIfPossibleRule());
       // add(new KingMoveRule());
        add(new JumpRule());
        add(new MoveDirectionRule());
    }
}
