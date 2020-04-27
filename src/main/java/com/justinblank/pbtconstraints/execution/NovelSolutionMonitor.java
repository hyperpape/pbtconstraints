package com.justinblank.pbtconstraints.execution;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import org.chocosolver.sat.PropNogoods;
import org.chocosolver.sat.SatSolver;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;

public class NovelSolutionMonitor implements IMonitorSolution {

    private final PropNogoods propagator;
    private final Variable[] decisionVars;
    private final TIntList ps;

    public NovelSolutionMonitor(Variable... vars) {
        decisionVars = vars;
        propagator = vars[0].getModel().getNogoodStore().getPropNogoods();
        ps = new TIntArrayList();
    }

    @Override
    public void onSolution() {
        int n = decisionVars.length;
        for (var decisionVar : decisionVars) {
            ps.clear();
            if (decisionVar instanceof IntVar intVar) {
                ps.add(SatSolver.negated(propagator.Literal(intVar, intVar.getValue(), true)));
                propagator.addLearnt(ps.toArray());
            }
        }
    }
}

