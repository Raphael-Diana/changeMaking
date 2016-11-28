/**
 * @author raphael
 */

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

public class ChangeMaking {

    public void modelAndSolve(){
        Model model = new Model("changeMaking");

        // The different coin available
        //int[] change = {1, 2, 6, 12, 24, 48, 60};
        int[] change = {2, 24, 12, 48, 6, 60, 1};
        // The target amount of money
        int target = 96;

        //int[] change ={1,2,3,8,20,21};
        //int[] change ={8,20,3,21,1,2};
        //int target = 1558;

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
        //solver.showStatistics();
        solver.showSolutions();

        // Assigns the first non-instantiated variable to its lower bound.
        //solver.setSearch(Search.inputOrderLBSearch(vars));

        // Assigns the non-instantiated variable of smallest domain size to its upper bound.
        solver.setSearch(Search.minDomUBSearch(vars));

        //solver.setSearch(Search.randomSearch(vars, System.currentTimeMillis()));

        // Do restart on each solution
        //solver.setRestartOnSolutions();

        // Find only the first optimal solution
        //solver.findOptimalSolution(coinNumber, false);

        // Find all optimal solutions
        solver.findAllOptimalSolutions(coinNumber, false);

    }

    public static void main(String[] args) {
        new ChangeMaking().modelAndSolve();
    }

}
