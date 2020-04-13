package model;

public class MoveValidator {
    RuleSet ruleSet;

    public MoveValidator(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    public boolean validate(Field src, Field dst, Board board){
        for(Rule rule : ruleSet){
            if(!rule.isValid(src, dst, board)){
                System.out.println("Rule name: "+rule.getClass().getName());
                return false;
            }
        }
        return true;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }
}
