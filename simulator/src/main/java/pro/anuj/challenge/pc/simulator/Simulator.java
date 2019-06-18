package pro.anuj.challenge.pc.simulator;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.function.Function;

public class Simulator implements Function<EvalContext, SimulationResult> {

    private final Portfolio portfolio;
    private final NormalDistribution distribution;
    private final DescriptiveStatistics stats = new DescriptiveStatistics();

    Simulator(Portfolio portfolio) {
        this.portfolio = portfolio;
        this.distribution = new NormalDistribution(portfolio.getMeanReturn(), portfolio.getRisk());
    }

    @Override
    public SimulationResult apply(EvalContext evalContext) {
        for (int simulationCount = 0; simulationCount < evalContext.getSimulationCount(); simulationCount++) {
            double amount = portfolio.getAssetValue();

            for (int yearCount = 0; yearCount < evalContext.getNumberOfYears(); yearCount++) {
                double randomSample = 1 + distribution.sample();
                // calculate new amount with random sample
                amount = amount * randomSample;
                // calculate new amount after inflation
                amount = amount * 1 + evalContext.getInflation();

            }
            stats.addValue(amount);
        }
        return new SimulationResult(stats.getPercentile(10), stats.getPercentile(50), stats.getPercentile(90), portfolio.getType());
    }
}
