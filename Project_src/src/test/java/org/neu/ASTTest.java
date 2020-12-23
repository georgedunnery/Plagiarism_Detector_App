package org.neu;

import org.junit.Test;
import org.neu.ast.CppTreeFactory;
import org.neu.ast.PythonTreeFactory;
import org.neu.ast.TreeFactory;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ASTTest {

  @Test(expected = NullPointerException.class)
  public void testNullCppFactory() {
    try {
      TreeFactory cppFactory = null;
      String cppStr = cppFactory.text("res/example.cpp");
    } catch (IOException e) {
      fail("Expected NullPointerException");
    }
  }

  @Test(expected = NullPointerException.class)
  public void testNullPythonFactory() {
    try {
      TreeFactory pythonFactory = null;
      String pyStr = pythonFactory.text("res/hanoi.py");
    } catch (IOException e) {
      fail("Expected NullPointerException");
    }
  }


  @Test
  public void testStructuralCpp() {
    try {
      TreeFactory cppFactory = new CppTreeFactory();
      String cppStr = cppFactory.getStructuralTree("res/example.cpp");
      String expected = " 8\n" +
              "\n" +
              " 44 133 84 85 88\n" +
              " 133 128 133 111 142 111 133 128 133 129\n" +
              " 44 133 100 134 90 134 129\n" +
              " 58 134 129\n" +
              " 89";
      assertEquals(expected, cppStr);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testStructuralPython() {
    try {
      TreeFactory cppFactory = new CppTreeFactory();
      String cppStr = cppFactory.getStructuralTree("res/hanoi.py");
      String expected = " 142\n" +
              " 133 133\n" +
              " 133 134\n" +
              " 133 134 91 133 8\n" +
              " 134 93 134 93 134\n" +
              " 142\n" +
              "\n" +
              " 8\n" +
              " 133 133\n" +
              "\n" +
              " 8\n" +
              " 133 100 86 142 123 142 123 142 87\n" +
              "\n" +
              " 8\n" +
              " 133 100 134\n" +
              " 133 100 134\n" +
              "\n" +
              " 133 133 84 133 123 133 123 133 123 133 123 133 85 127\n" +
              " 142\n" +
              " 133 127 133 133 133 133 123 133 130 133 133 123 133 123 4 133\n" +
              " 133 123 133 133 133 130 133 133 133 123 133 133 133 133 133\n" +
              " 133 133 133 133 133\n" +
              " 133 127 133 133 133 133 133 127 133 133 133 133\n" +
              " 133 133 130 133 133 133 133 133 123 4 133 133 133 133\n" +
              " 133 133 133 133 133 123 133 123 4 133 39 133 133 130 133 133 133\n" +
              " 133 133 133 133 133 133 123 4 133 133 133 133 133 133 133 133 133 133\n" +
              " 4 133 133 133 130 133 133 133 133 133 133 133 133 123 133 133\n" +
              " 133 15 133 133 133 91 133 133 133 133 133 133 123 133 133\n" +
              " 133 133 133 133 1\n" +
              " 133 127 133\n" +
              " 142\n" +
              " 42 133 102 134 127\n" +
              " 133 84 133 91 134 123 133 123 133 123 133 123 133 85\n" +
              " 133 130 133 84 133 123 133 123 133 85\n" +
              " 133 84 133 91 134 123 133 123 133 123 133 123 133 85\n" +
              "\n" +
              " 133 133 84 85 127\n" +
              "\n" +
              " 8\n" +
              " 133 100 134\n" +
              " 83 133 102 133 6 133 101 133 127\n" +
              " 72 127\n" +
              " 133 100 44 84 133 84 142 85 85\n" +
              "\n" +
              " 8\n" +
              " 8\n" +
              " 133 133 127\n" +
              " 133 84 142 85\n" +
              "\n" +
              " 8\n" +
              " 133 100 133 130 133 84 133 123 133 86 134 87 123 133 86 134 87 123 133 86 134 87 85\n" +
              "\n" +
              " 8\n" +
              " 133 84 133 123 133 86 134 87 123 133 86 134 87 123 133 86";
      assertEquals(expected, cppStr);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testExpectedStringForCppAST() {
    try {
      TreeFactory cppFactory = new CppTreeFactory();
      String cppStr = cppFactory.text("res/example.cpp");
      String expected = "(translationunit (declarationseq (declaration (functiondefinition " +
              "(declspecifierseq (declspecifier (typespecifier (trailingtypespecifier " +
              "(simpletypespecifier int))))) (declarator (ptrdeclarator (noptrdeclarator " +
              "(noptrdeclarator (declaratorid (idexpression (unqualifiedid main)))) " +
              "(parametersandqualifiers ( parameterdeclarationclause ))))) (functionbody " +
              "(compoundstatement { (statementseq (statementseq (statementseq (statement " +
              "(expressionstatement (expression (assignmentexpression (conditionalexpression " +
              "(logicalorexpression (logicalandexpression (inclusiveorexpression " +
              "(exclusiveorexpression (andexpression (equalityexpression (relationalexpression " +
              "(shiftexpression (shiftexpression (shiftexpression (additiveexpression " +
              "(multiplicativeexpression (pmexpression (castexpression (unaryexpression " +
              "(postfixexpression (primaryexpression (idexpression (qualifiedid " +
              "(nestednamespecifier (thetypename (classname std)) ::) (unqualifiedid cout))))))))))) " +
              "(shiftoperator <<) (additiveexpression (multiplicativeexpression (pmexpression " +
              "(castexpression (unaryexpression (postfixexpression (primaryexpression (literal " +
              "\"Hello World\"))))))))) (shiftoperator <<) (additiveexpression " +
              "(multiplicativeexpression (pmexpression (castexpression (unaryexpression " +
              "(postfixexpression (primaryexpression (idexpression (qualifiedid (nestednamespecifier " +
              "(thetypename (classname std)) ::) (unqualifiedid endl))))))))))))))))))))) ;))) " +
              "(statement (declarationstatement (blockdeclaration (simpledeclaration " +
              "(declspecifierseq (declspecifier (typespecifier (trailingtypespecifier (" +
              "simpletypespecifier int))))) (initdeclaratorlist (initdeclarator (declarator " +
              "(ptrdeclarator (noptrdeclarator (declaratorid (idexpression (unqualifiedid x)))))) (" +
              "initializer (braceorequalinitializer = (initializerclause (assignmentexpression " +
              "(conditionalexpression (logicalorexpression (logicalandexpression (" +
              "inclusiveorexpression (exclusiveorexpression (andexpression (equalityexpression " +
              "(relationalexpression (shiftexpression (additiveexpression (additiveexpression " +
              "(multiplicativeexpression (pmexpression (castexpression (unaryexpression " +
              "(postfixexpression (primaryexpression (literal 4)))))))) + (multiplicativeexpression " +
              "(pmexpression (castexpression (unaryexpression (postfixexpression (primaryexpression " +
              "(literal 3))))))))))))))))))))))) ;))))) (statement (jumpstatement return (expression " +
              "(assignmentexpression (conditionalexpression (logicalorexpression (logicalandexpression " +
              "(inclusiveorexpression (exclusiveorexpression (andexpression (equalityexpression " +
              "(relationalexpression (shiftexpression (additiveexpression (multiplicativeexpression " +
              "(pmexpression (castexpression (unaryexpression (postfixexpression (primaryexpression " +
              "(literal 0))))))))))))))))))) ;))) }))))) <EOF>)";

      assertEquals(expected, cppStr);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testExpectedStringForPythonAST() {
    try {
      TreeFactory pythonFactory = new PythonTreeFactory();
      String pythonStr = pythonFactory.text("res/hanoi.py");
      String expected = "(file_input (stmt (simple_stmt (small_stmt (expr_stmt " +
              "(testlist_star_expr (test (or_test (and_test (not_test (comparison (expr " +
              "(xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr " +
              "(atom \"\"\"\\r\\nGeorge Dunnery\\r\\nCS 5001\\r\\nHomework 5 - Programming " +
              "#1\\r\\n11/2/2018\\r\\n\"\"\")))))))))))))))))) \\r\\n)) (stmt (simple_stmt " +
              "(small_stmt (import_stmt (import_name import (dotted_as_names (dotted_as_name " +
              "(dotted_name hanoi_viz)))))) \\r\\n)) (stmt (simple_stmt (small_stmt (expr_stmt " +
              "(testlist_star_expr (test (or_test (and_test (not_test (comparison (expr " +
              "(xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr " +
              "(atom NAME)))))))))))))))) = (testlist_star_expr (test (or_test (and_test " +
              "(not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term " +
              "(factor (power (atom_expr (atom [ (testlist_comp (test (or_test (and_test (not_test " +
              "(comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power " +
              "(atom_expr (atom \"Cthulu\"))))))))))))))) , (test (or_test (and_test (not_test " +
              "(comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power " +
              "(atom_expr (atom \"David\"))))))))))))))) , (test (or_test (and_test (not_test " +
              "(comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power " +
              "(atom_expr (atom \"Goliath\")))))))))))))))) ])))))))))))))))))) \\r\\n)) (stmt " +
              "(simple_stmt (small_stmt (expr_stmt (testlist_star_expr (test (or_test (and_test " +
              "(not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term " +
              "(factor (power (atom_expr (atom MAX)))))))))))))))) = (testlist_star_expr (test " +
              "(or_test (and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr " +
              "(arith_expr (term (factor (power (atom_expr (atom 8)))))))))))))))))) \\r\\n)) " +
              "(stmt (simple_stmt (small_stmt (expr_stmt (testlist_star_expr (test (or_test " +
              "(and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr " +
              "(term (factor (power (atom_expr (atom MIN)))))))))))))))) = (testlist_star_expr " +
              "(test (or_test (and_test (not_test (comparison (expr (xor_expr (and_expr " +
              "(shift_expr (arith_expr (term (factor (power (atom_expr (atom 1)))))))))))))))))) " +
              "\\r\\n)) (stmt (compound_stmt (funcdef def move_tower (parameters ( (typedargslist " +
              "(tfpdef num_disks) , (tfpdef source) , (tfpdef target) , (tfpdef helper) , " +
              "(tfpdef towers)) )) : (suite         (stmt (simple_stmt (small_stmt (expr_stmt " +
              "(testlist_star_expr (test (or_test (and_test (not_test (comparison (expr (xor_expr " +
              "(and_expr (shift_expr (arith_expr (term (factor (power (atom_expr (atom \"\"\"\\r" +
              "\\n    Parameters: The number of disks, integer. The source, target, and helper" +
              "\\r\\n    towers, all as strings. Dictionary of towers, which keeps track of which" +
              "\\r\\n    rings are on which tower\\r\\n    Does: Recursively solves the hanoi " +
              "puzzle: calls the move_disk function\\r\\n    from hanoi_viz.py to move each disk, " +
              "and calls itself to manipulate\\r\\n    which tower is the source, helper, and " +
              "target for each step. The rings must\\r\\n    be moved one at a time, and each " +
              "ring may only be on top of larger rings\\r\\n    and below smaller ones. We only " +
              "care about moving disks that exist, so the\\r\\n    base case here is implicit - " +
              "when there are no disks left, we don't need\\r\\n    to move any disks!\\r\\n    " +
              "Returns: Nothing\\r\\n    \"\"\"))))))))))))))))))   )) (stmt (compound_stmt (" +
              "if_stmt if (test (or_test (and_test (not_test (comparison (expr (xor_expr (and_expr " +
              "(shift_expr (arith_expr (term (factor (power (atom_expr (atom num_disks)))))))))) " +
              "(comp_op >) (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor " +
              "(power (atom_expr (atom 0))))))))))))))) : (suite             (stmt (simple_stmt " +
              "(small_stmt (expr_stmt (testlist_star_expr (test (or_test (and_test (not_test " +
              "(comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power " +
              "(atom_expr (atom move_tower) (trailer ( (arglist (argument (test (or_test (and_test " +
              "(not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term " +
              "(factor (power (atom_expr (atom num_disks))))) - (term (factor (power (atom_expr " +
              "(atom 1)))))))))))))))) , (argument (test (or_test (and_test (not_test (comparison " +
              "(expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr " +
              "(atom source)))))))))))))))) , (argument (test (or_test (and_test (not_test " +
              "(comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power " +
              "(atom_expr (atom helper)))))))))))))))) , (argument (test (or_test (and_test " +
              "(not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term " +
              "(factor (power (atom_expr (atom target)))))))))))))))) , (argument (test (or_test " +
              "(and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr " +
              "(term (factor (power (atom_expr (atom towers))))))))))))))))) )))))))))))))))))))" +
              "   )) (stmt (simple_stmt (small_stmt (expr_stmt (testlist_star_expr (test (or_test " +
              "(and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr " +
              "(term (factor (power (atom_expr (atom hanoi_viz) (trailer . move_disk) (trailer ( " +
              "(arglist (argument (test (or_test (and_test (not_test (comparison (expr (xor_expr " +
              "(and_expr (shift_expr (arith_expr (term (factor (power (atom_expr (atom source)" +
              "))))))))))))))) , (argument (test (or_test (and_test (not_test (comparison (expr " +
              "(xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr " +
              "(atom target)))))))))))))))) , (argument (test (or_test (and_test (not_test " +
              "(comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power " +
              "(atom_expr (atom towers))))))))))))))))) )))))))))))))))))))   )) (stmt " +
              "(simple_stmt (small_stmt (expr_stmt (testlist_star_expr (test (or_test (and_test " +
              "(not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term " +
              "(factor (power (atom_expr (atom move_tower) (trailer ( (arglist (argument (test " +
              "(or_test (and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr " +
              "(arith_expr (term (factor (power (atom_expr (atom num_disks))))) - (term (factor " +
              "(power (atom_expr (atom 1)))))))))))))))) , (argument (test (or_test (and_test " +
              "(not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term " +
              "(factor (power (atom_expr (atom helper)))))))))))))))) , (argument (test (or_test (" +
              "and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr " +
              "(term (factor (power (atom_expr (atom target)))))))))))))))) , (argument (test " +
              "(or_test (and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr " +
              "(arith_expr (term (factor (power (atom_expr (atom source)))))))))))))))) , " +
              "(argument (test (or_test (and_test (not_test (comparison (expr (xor_expr (and_expr" +
              " (shift_expr (arith_expr (term (factor (power (atom_expr (atom towers)))))))))" +
              ")))))))) ))))))))))))))))))) \\r\\n)) \\n)))) \\n)))) (stmt (compound_stmt (" +
              "funcdef def main (parameters ( )) : (suite         (stmt (simple_stmt (small_stmt" +
              " (expr_stmt (testlist_star_expr (test (or_test (and_test (not_test (comparison (e" +
              "xpr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr (" +
              "atom num_disks)))))))))))))))) = (testlist_star_expr (test (or_test (and_test (no" +
              "t_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (fact" +
              "or (power (atom_expr (atom 0))))))))))))))))))   )) (stmt (compound_stmt (while_s" +
              "tmt while (test (or_test (and_test (not_test (comparison (expr (xor_expr (and_exp" +
              "r (shift_expr (arith_expr (term (factor (power (atom_expr (atom num_disks))))))))" +
              ")) (comp_op >) (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (" +
              "power (atom_expr (atom MAX))))))))))))) or (and_test (not_test (comparison (expr" +
              " (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr (at" +
              "om num_disks)))))))))) (comp_op <) (expr (xor_expr (and_expr (shift_expr (arith_" +
              "expr (term (factor (power (atom_expr (atom MIN))))))))))))))) : (suite           " +
              "  (stmt (compound_stmt (try_stmt try : (suite                 (stmt (simple_stm" +
              "t (small_stmt (expr_stmt (testlist_star_expr (test (or_test (and_test (not_test " +
              "(comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (po" +
              "wer (atom_expr (atom num_disks)))))))))))))))) = (testlist_star_expr (test (or_te" +
              "st (and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_" +
              "expr (term (factor (power (atom_expr (atom int) (trailer ( (arglist (argument (t" +
              "est (or_test (and_test (not_test (comparison (expr (xor_expr (and_expr (shift_exp" +
              "r (arith_expr (term (factor (power (atom_expr (atom input) (trailer ( (arglist (a" +
              "rgument (test (or_test (and_test (not_test (comparison (expr (xor_expr (and_expr " +
              "(shift_expr (arith_expr (term (factor (power (atom_expr (atom \"Please enter an i" +
              "nteger from 1 - 8:\\n\"))))))))))))))))) )))))))))))))))))) )))))))))))))))))))  " +
              " ))  ) (except_clause except (test (or_test (and_test (not_test (comparison (expr" +
              " (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr (a" +
              "tom ValueError)))))))))))))))) : (suite                 (stmt (simple_stmt (sma" +
              "ll_stmt (expr_stmt (testlist_star_expr (test (or_test (and_test (not_test (compar" +
              "ison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom" +
              "_expr (atom print) (trailer ( (arglist (argument (test (or_test (and_test (not_t" +
              "est (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (facto" +
              "r (power (atom_expr (atom \"Input is required to be an integer, please try again" +
              ".\"))))))))))))))))) )))))))))))))))))))   ))  ))))  )))) (stmt (simple_stmt (sma" +
              "ll_stmt (expr_stmt (testlist_star_expr (test (or_test (and_test (not_test (compa" +
              "rison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (at" +
              "om_expr (atom towers)))))))))))))))) = (testlist_star_expr (test (or_test (and_t" +
              "est (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (te" +
              "rm (factor (power (atom_expr (atom hanoi_viz) (trailer . initialize_towers) (t" +
              "railer ( (arglist (argument (test (or_test (and_test (not_test (comparison (expr" +
              " (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr " +
              "(atom num_disks)))))))))))))))) , (argument (test (or_test (and_test (not_test " +
              "(comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor " +
              "(power (atom_expr (atom NAME) (trailer [ (subscriptlist (subscript (test (or_test " +
              "(and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr " +
              "(term (factor (power (atom_expr (atom 0))))))))))))))))) ])))))))))))))))) , " +
              "(argument (test (or_test (and_test (not_test (comparison (expr (xor_expr (and_expr " +
              "(shift_expr (arith_expr (term (factor (power (atom_expr (atom NAME) (trailer [ " +
              "(subscriptlist (subscript (test (or_test (and_test (not_test (comparison (expr " +
              "(xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr " +
              "(atom 1))))))))))))))))) ])))))))))))))))) , (argument (test (or_test (and_test " +
              "(not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term " +
              "(factor (power (atom_expr (atom NAME) (trailer [ (subscriptlist (subscript (test " +
              "(or_test (and_test (not_test (comparison (expr (xor_expr (and_expr (shift_expr (" +
              "arith_expr (term (factor (power (atom_expr (atom 2))))))))))))))))) ]))))))))))))" +
              "))))) )))))))))))))))))))   )) (stmt (simple_stmt (small_stmt (expr_stmt (testlis" +
              "t_star_expr (test (or_test (and_test (not_test (comparison (expr (xor_expr (and_e" +
              "xpr (shift_expr (arith_expr (term (factor (power (atom_expr (atom move_tower) (tr" +
              "ailer ( (arglist (argument (test (or_test (and_test (not_test (comparison (expr" +
              " (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr (at" +
              "om num_disks)))))))))))))))) , (argument (test (or_test (and_test (not_test (com" +
              "parison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (" +
              "atom_expr (atom NAME) (trailer [ (subscriptlist (subscript (test (or_test (and_t" +
              "est (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (te" +
              "rm (factor (power (atom_expr (atom 0))))))))))))))))) ])))))))))))))))) , (argu" +
              "ment (test (or_test (and_test (not_test (comparison (expr (xor_expr (and_expr (s" +
              "hift_expr (arith_expr (term (factor (power (atom_expr (atom NAME) (trailer [ (su" +
              "bscriptlist (subscript (test (or_test (and_test (not_test (comparison (expr (xor_e" +
              "xpr (and_expr (shift_expr (arith_expr (term (factor (power (atom_expr (atom 1)))))" +
              ")))))))))))) ])))))))))))))))) , (argument (test (or_test (and_test (not_test (com" +
              "parison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (factor (power (at" +
              "om_expr (atom NAME) (trailer [ (subscriptlist (subscript (test (or_test (and_test" +
              " (not_test (comparison (expr (xor_expr (and_expr (shift_expr (arith_expr (term (" +
              "factor (power (atom_expr (atom 2))))))))))))))))) ])))))))))))))))) , (argument " +
              "(test (or_test (and_test (not_test (comparison (expr (xor_expr (and_expr (shift_" +
              "expr (arith_expr (term (factor (power (atom_expr (atom towers))))))))))))))))) ))" +
              "))))))))))))))))) \\r\\n)) \\n)))) (stmt (simple_stmt (small_stmt (expr_stmt (te" +
              "stlist_star_expr (test (or_test (and_test (not_test (comparison (expr (xor_expr " +
              "(and_expr (shift_expr (arith_expr (term (factor (power (atom_expr (atom main) (tr" +
              "ailer ( ))))))))))))))))))) \\r\\n)) <EOF>)";
      assertEquals(expected, pythonStr);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
