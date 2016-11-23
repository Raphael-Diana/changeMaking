/**
 * @author raphael
 */
/**
 * Copyright (c) 2016, Ecole des Mines de Nantes
 * All rights reserved.
 */

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class ChangeMaking {

    public void modelAndSolve(){
        Model model = new Model("changeMaking");

        // The target amount of money
        int target = 29;
        // The different coin available
        int[] change = {1, 4, 5, 8, 11};

        // An array containing
        IntVar[] vars = new IntVar[change.length];

        // The number of each coin
        for (int i = 0; i < change.length; i++) {
            vars[i] = model.intVar("coin " + change[i], 0, target/change[i]);
        }

        // Number of coins
        IntVar coinNumber = model.intVar("Number of coins ", 1, target/change[0]);

        // Number of coin constraint
        model.sum(vars, "=", coinNumber).post();

        // Sum constraint
        model.scalar(vars, change, "=", target).post();



        Solver solver = model.getSolver();
        solver.showStatistics();
        solver.showSolutions();
        //solver.findSolution();
        //solver.findAllSolutions();
        solver.findAllOptimalSolutions(coinNumber, false);

    }

    public static void main(String[] args) {
        new ChangeMaking().modelAndSolve();
    }
}
