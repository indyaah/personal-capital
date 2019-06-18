package pro.anuj.challenge.pc.simulator;

import java.util.stream.Stream;

public class SimulatorApp {

    private static final double INFLATION = 0.035d;
    private static final int NUMBER_OF_YEARS = 20;
    private static final int SIMULATION_COUNT = 10000;
    private static final EvalContext EVAL_CONTEXT = new EvalContext(SIMULATION_COUNT, NUMBER_OF_YEARS, INFLATION);

    public static void main(String[] args) {
        final Portfolio aggressive = new Portfolio(0.094324f, 0.015675f, 100000f, PortfolioType.AGGRESSIVE);
        final Portfolio conservative = new Portfolio(0.06189f, 0.063438f, 100000f, PortfolioType.VERY_CONSERVATIVE);

        Stream.of(aggressive, conservative).parallel()
            .map(Simulator::new)
            .map(simulator -> simulator.apply(EVAL_CONTEXT))
            .forEach(System.out::println);

    }

}
